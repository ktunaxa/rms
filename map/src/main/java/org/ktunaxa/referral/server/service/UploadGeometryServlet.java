/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.server.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.geomajas.geometry.Crs;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.LayerException;
import org.geomajas.service.GeoService;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Servlet for uploading compressed shape files and sending the union geometry back as WKT.
 * 
 * @author Pieter De Graef
 */
public class UploadGeometryServlet extends HttpServlet {

	private static final long serialVersionUID = 100L;

	private static final int BUFFER = 2048;

	private WebApplicationContext context;

	private final Logger log = LoggerFactory.getLogger(UploadGeometryServlet.class);

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(req)) {
			List<String> tempFiles = new ArrayList<String>();
			byte[] fileContent = null;

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items;
			String formId = req.getParameter(KtunaxaConstant.FORM_ID);
			// remove some characters to prevent XSS
			formId = formId.replace("<", "");
			formId = formId.replace(">", "");
			formId = formId.replace("=", "");
			formId = formId.replace("&", "");
			try {
				items = upload.parseRequest(req);
				for (FileItem item : items) {
					if (!item.isFormField()) {
						fileContent = item.get();
						break;
					}
				}
				URL url = unzipShape(tempFiles, fileContent);
				ShapefileDataStore dataStore = new ShapefileDataStore(url);
				Geometry geometry = transform(getGeometry(dataStore), dataStore.getFeatureSource().getSchema()
						.getCoordinateReferenceSystem());

				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				out.println("<html>");
				out.println("<body>");
				out.println("<script type=\"text/javascript\">");
				out.println("if (parent.uploadComplete) parent.uploadComplete('" + formId + "','" + geometry.toText() +
						"');");
				out.println("</script>");
				cleanup(tempFiles);
			} catch (FileUploadException e) {
				resp.setStatus(400);
			}
		}
	}

	private void cleanup(List<String> tempFiles) {
		for (String tempFile : tempFiles) {
			File file = new File(tempFile);
			if (file.exists()) {
				file.delete();
			}
		}
		tempFiles.clear();
	}

	private Geometry transform(Geometry geometry, CoordinateReferenceSystem crs) throws IOException {
		GeoService geoService = context.getBean(GeoService.class);
		try {
			String sourceCrs = geoService.getCodeFromCrs(crs);
			try {
				return geoService.transform(geometry, sourceCrs, KtunaxaConstant.MAP_CRS);
			} catch (GeomajasException e) {
				throw new IOException(e.getMessage());
			}
		} catch (Exception e) {
			try {
				return geoService.transform(geometry, crs, geoService.getCrs2(KtunaxaConstant.MAP_CRS));
			} catch (GeomajasException e1) {
				throw new IOException(e.getMessage());
			}
		}
	}

	private Geometry getGeometry(ShapefileDataStore dataStore) throws IOException {
		SimpleFeatureIterator featureIterator = dataStore.getFeatureSource().getFeatures().features();
		Geometry geometry = null;
		while (featureIterator.hasNext()) {
			if (geometry == null) {
				geometry = (Geometry) featureIterator.next().getDefaultGeometry();
			} else {
				geometry = geometry.union((Geometry) featureIterator.next().getDefaultGeometry());
			}
		}
		return geometry;
	}

	private URL unzipShape(List<String> tempFiles, byte[] fileContent) throws IOException {
		String tempDir = System.getProperty("java.io.tmpdir");

		URL url = null;
		ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(fileContent));
		ZipEntry entry;
		while ((entry = zin.getNextEntry()) != null) {
			log.info("Extracting: " + entry);
			String name = tempDir + "/" + entry.getName();
			tempFiles.add(name);
			if (name.endsWith(".shp")) {
				url = new URL("file://" + name);
			}
			int count;
			byte[] data = new byte[BUFFER];
			// write the files to the disk
			checkLocation(name);
			FileOutputStream fos = new FileOutputStream(name);
			BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
			while ((count = zin.read(data, 0, BUFFER)) != -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
		}
		return url;
	}

	private void checkLocation(String name) {
		File file = new File(name);
		if (file != null && file.exists()) {
			file.delete();
		}
	}
}
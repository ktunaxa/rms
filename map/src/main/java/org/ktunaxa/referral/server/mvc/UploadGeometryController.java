/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.mvc;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.geomajas.global.GeomajasException;
import org.geomajas.service.GeoService;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Controller for uploading compressed shape files and sending the union geometry back as WKT.
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 */

@Controller("/upload/referral/geometry/**")
public class UploadGeometryController {
	
	@Autowired
	private GeoService geoService;

	private static final int BUFFER = 2048;

	private HashSet<String> tempFiles = new HashSet<String>();

	private final Logger log = LoggerFactory.getLogger(UploadGeometryController.class);

	@RequestMapping(value = "/upload/referral/geometry", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam(KtunaxaConstant.FORM_ID) String formId,
			@RequestParam("file") MultipartFile file, Model model) {

		UploadResponse response = new UploadResponse();
		response.addObject(KtunaxaConstant.FORM_ID, formId);
		try {
			URL url = unzipShape(file.getBytes());
			ShapefileDataStore dataStore = new ShapefileDataStore(url);
			Geometry geometry = transform(getGeometry(dataStore), dataStore.getFeatureSource().getSchema()
					.getCoordinateReferenceSystem());
			response.addObject(KtunaxaConstant.FORM_GEOMETRY, geometry.toText());
		} catch (Exception e) {
			log.error("Could not extract geometry", e);
			response.setException(e);
		}
		cleanup();
		model.addAttribute(UploadView.RESPONSE, response);
		return UploadView.NAME;
	}
	
	private void cleanup() {
		for (String tempFile : tempFiles) {
			deleteFileIfExists(tempFile);
		}
		tempFiles.clear();
	}

	private Geometry transform(Geometry geometry, CoordinateReferenceSystem crs) throws IOException {
		try {
			String sourceCrs = geoService.getCodeFromCrs(crs);
			// transform to layer CRS first
			Geometry layerGeom = geoService.transform(geometry, sourceCrs, KtunaxaConstant.LAYER_CRS);
			return geoService.transform(layerGeom, KtunaxaConstant.LAYER_CRS, KtunaxaConstant.MAP_CRS);
		} catch (GeomajasException ge) {
			throw new IOException(ge.getMessage(), ge);
		} catch (Exception e) {
			try {
				CoordinateReferenceSystem targetCrs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
				MathTransform transform = CRS.findMathTransform(crs, targetCrs, true);
				// have to use JTS transform when EPSG code is missing !
				Geometry layerGeom = JTS.transform(geometry, transform);
				return geoService.transform(layerGeom, KtunaxaConstant.LAYER_CRS, KtunaxaConstant.MAP_CRS);
			} catch (Exception e1) {
				throw new IOException(e1.getMessage());
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

	private URL unzipShape(byte[] fileContent) throws IOException {
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
			deleteFileIfExists(name);
			FileOutputStream fos = new FileOutputStream(name);
			BufferedOutputStream destination = new BufferedOutputStream(fos, BUFFER);
			while ((count = zin.read(data, 0, BUFFER)) != -1) {
				destination.write(data, 0, count);
			}
			destination.flush();
			destination.close();
		}
		if (url == null) {
			throw new IllegalArgumentException("Missing .shp file");
		}
 		return url;
	}

	private void deleteFileIfExists(String name) {
		File file = new File(name);
		if (file.exists()) {
			file.delete();
		}
	}


}

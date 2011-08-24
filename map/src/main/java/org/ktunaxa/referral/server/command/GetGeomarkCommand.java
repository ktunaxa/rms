/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.geomajas.command.Command;
import org.geomajas.service.DtoConverterService;
import org.geomajas.service.GeoService;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.ktunaxa.referral.server.FileUtil;
import org.ktunaxa.referral.server.command.dto.GetGeomarkRequest;
import org.ktunaxa.referral.server.command.dto.GetGeomarkResponse;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * Command to fetch the geometry from a Geomark URL.
 * 
 * @author Jan De Moerloose
 */
@Component
public class GetGeomarkCommand implements Command<GetGeomarkRequest, GetGeomarkResponse> {

	@Autowired
	private DtoConverterService converterService;

	@Autowired
	private GeoService geoService;

	private static final String GM_PREFIX = "gm-";

	public GetGeomarkResponse getEmptyCommandResponse() {
		return new GetGeomarkResponse();
	}

	public void execute(GetGeomarkRequest request, GetGeomarkResponse response) throws Exception {
		String geomarkUrl = request.getGeomark();
		String geomarkId = null;
		if (geomarkUrl.startsWith(GM_PREFIX)) {
			geomarkUrl = KtunaxaConstant.GEOMARK_BASE_URL + request.getGeomark();
		}
		if (geomarkUrl.contains(GM_PREFIX)) {
			int last = geomarkUrl.indexOf('/', geomarkUrl.lastIndexOf(GM_PREFIX));
			if (last > 0) {
				geomarkId = geomarkUrl.substring(geomarkUrl.lastIndexOf(GM_PREFIX) + 3, last);
				geomarkUrl = geomarkUrl.substring(0, last);
			} else {
				geomarkId = geomarkUrl.substring(geomarkUrl.lastIndexOf(GM_PREFIX) + 3);
			}
		}
		Geometry geometry;
		try {
			geometry = loadZippedShape(geomarkId, geomarkUrl + KtunaxaConstant.GEOMARK_SHAPE_REQUEST +
					KtunaxaConstant.LAYER_SRID);
		} catch (Exception e) {
			geometry = loadWkt(geomarkUrl + KtunaxaConstant.GEOMARK_WKT_REQUEST + KtunaxaConstant.LAYER_SRID);
		}
		geometry = geoService.transform(geometry, KtunaxaConstant.LAYER_CRS, KtunaxaConstant.MAP_CRS);
		response.setGeometry(converterService.toDto(geometry));
	}

	private Geometry loadWkt(String url) throws IOException, ParseException {
		StringWriter writer = new StringWriter();
		InputStream wktStream = new URL(url).openStream();
		try {
			IOUtils.copy(wktStream, writer);
		} finally {
			IOUtils.closeQuietly(wktStream);
		}
		GeometryFactory factory = new GeometryFactory(new PrecisionModel(), KtunaxaConstant.LAYER_SRID);
		WKTReader reader = new WKTReader(factory);
		return reader.read(writer.toString());
	}

	private Geometry loadZippedShape(String geomarkId, String urlStr) throws IOException {
		URL url = new URL(urlStr);
		String tempDir = System.getProperty("java.io.tmpdir");
		File zipFile = new File(tempDir + File.separator + geomarkId + ".zip");
		FileUtil.copyURLToFile(url, zipFile);
		File shpDir = new File(tempDir + File.separator + geomarkId);
		shpDir.mkdir();
		String[] names = FileUtil.unzip(zipFile, shpDir);
		for (String name : names) {
			if (name.endsWith(".shp")) {
				ShapefileDataStore dataStore = new ShapefileDataStore(new File(shpDir, name).toURI().toURL());
				return getGeometry(dataStore);
			}
		}
		throw new IOException("Missing .shp file in zip");
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

}

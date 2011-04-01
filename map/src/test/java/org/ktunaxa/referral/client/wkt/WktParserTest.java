package org.ktunaxa.referral.client.wkt;

import junit.framework.Assert;

import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.MultiPolygon;
import org.geomajas.gwt.client.spatial.geometry.Polygon;
import org.junit.Test;

public class WktParserTest {

	// Multipolygon with 2 polygons:
	private static final String MULTIPOLYGON1 = "MULTIPOLYGON (((1.2 2.3, 4.5 5.6, 7.8 8.9, 1.2 2.3)),"
			+ " ((10.2 20.3, 40.5 50.6, 70.8 80.9, 10.2 20.3)))";

	// Multipolygon with 1 polygon that has an interior ring...
	private static final String MULTIPOLYGON2 = "MULTIPOLYGON (((0.0 0.0, 0.0 100.0, 100.0 100.0, 100.0 0.0, 0.0 0.0),"
		+ " (10.2 20.3, 40.5 50.6, 70.8 80.9, 10.2 20.3)))";

	private WktParser parser;

	public WktParserTest() {
		parser = new WktParser(29611);
	}

	@Test
	public void testMultiPolygon() {
		Geometry geometry = parser.parse(MULTIPOLYGON1);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof MultiPolygon);
		MultiPolygon mp = (MultiPolygon) geometry;
		Assert.assertEquals(2, mp.getNumGeometries());

		geometry = parser.parse(MULTIPOLYGON2);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof MultiPolygon);
		mp = (MultiPolygon) geometry;
		Assert.assertEquals(1, mp.getNumGeometries());
		Polygon polygon = (Polygon)mp.getGeometryN(0);
		Assert.assertEquals(1, polygon.getNumInteriorRing());
	}
}
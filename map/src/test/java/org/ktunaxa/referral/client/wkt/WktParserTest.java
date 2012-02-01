/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.client.wkt;

import junit.framework.Assert;

import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.LineString;
import org.geomajas.gwt.client.spatial.geometry.MultiLineString;
import org.geomajas.gwt.client.spatial.geometry.MultiPoint;
import org.geomajas.gwt.client.spatial.geometry.MultiPolygon;
import org.geomajas.gwt.client.spatial.geometry.Point;
import org.geomajas.gwt.client.spatial.geometry.Polygon;
import org.junit.Test;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

public class WktParserTest {

	// Multipolygon with 2 polygons:
	private static final String MULTIPOLYGON1 = "MULTIPOLYGON (((1.2 2.3, 4.5 5.6, 7.8 8.9, 1.2 2.3)),"
			+ " ((10.2 20.3, 40.5 50.6, 70.8 80.9, 10.2 20.3)))";

	// Multipolygon with 1 polygon that has an interior ring...
	private static final String MULTIPOLYGON2 = "MULTIPOLYGON (((0.0 0.0, 0.0 100.0, 100.0 100.0, 100.0 0.0, 0.0 0.0),"
			+ " (10.2 20.3, 40.5 50.6, 70.8 80.9, 10.2 20.3)))";

	
	// Multilinestring with 1 linestring:
	private static final String MULTILINESTRING1 = "MULTILINESTRING ((1.2 2.3, 4.5 5.6, 7.8 8.9, 1.2 2.5))";

	private static final String POINT = "POINT (30 10)";
	private static final String LINESTRING = "LINESTRING (30 10, 10 30, 40 40)";
	private static final String POLYGON = "POLYGON ((30 10, 10 20, 20 40, 40 40, 30 10))";
	private static final String MULTIPOINT = "MULTIPOINT ((10 40), (40 30), (20 20), (30 10))";
	private static final String MULTILINESTRING = "MULTILINESTRING ((10 10, 20 20, 10 40),\n" + 
			"	(40 40, 30 30, 40 20, 30 10))";
	private static final String MULTIPOLYGON = "MULTIPOLYGON (((30 20, 10 40, 45 40, 30 20)),\n" + 
			"	((15 5, 40 10, 10 20, 5 10, 15 5)))";

	private WktParser parser;

	public WktParserTest() {
		parser = new WktParser(KtunaxaConstant.LAYER_SRID);
	}
	
	@Test
	public void testPoint() {
		Geometry geometry = parser.parse(POINT);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof Point);
	}

	@Test
	public void testLineString() {
		Geometry geometry = parser.parse(LINESTRING);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof LineString);
	}

	@Test
	public void testPolygon() {
		Geometry geometry = parser.parse(POLYGON);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof Polygon);
	}

	@Test
	public void testMultiPoint() {
		Geometry geometry = parser.parse(MULTIPOINT);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof MultiPoint);
	}

	@Test
	public void testMultiLineString() {
		Geometry geometry = parser.parse(MULTILINESTRING);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof MultiLineString);
	}

	@Test
	public void testMultiPolygon() {
		Geometry geometry = parser.parse(MULTIPOLYGON);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof MultiPolygon);
	}

	@Test
	public void testMultiPolygon2() {
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
		Polygon polygon = (Polygon) mp.getGeometryN(0);
		Assert.assertEquals(1, polygon.getNumInteriorRing());
	}

	@Test
	public void testMultiLineString2() {
		Geometry geometry = parser.parse(MULTILINESTRING1);
		Assert.assertNotNull(geometry);
		Assert.assertTrue(geometry instanceof MultiLineString);
		MultiLineString mp = (MultiLineString) geometry;
		Assert.assertEquals(1, mp.getNumGeometries());
	}

}
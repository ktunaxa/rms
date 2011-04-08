/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.wkt;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.geometry.Coordinate;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.GeometryFactory;
import org.geomajas.gwt.client.spatial.geometry.LineString;
import org.geomajas.gwt.client.spatial.geometry.LinearRing;
import org.geomajas.gwt.client.spatial.geometry.MultiLineString;
import org.geomajas.gwt.client.spatial.geometry.MultiPoint;
import org.geomajas.gwt.client.spatial.geometry.MultiPolygon;
import org.geomajas.gwt.client.spatial.geometry.Point;
import org.geomajas.gwt.client.spatial.geometry.Polygon;

/**
 * Parser for the Well Known Text (WKT) format. It parses to the Geomajas GWT client geometry model.
 * 
 * @author Pieter De Graef
 */
public class WktParser {

	private GeometryFactory factory;

	private String tempWkt;

	public WktParser(int srid) {
		factory = new GeometryFactory(srid, GeometryFactory.PARAM_DEFAULT_PRECISION);
	}

	public Geometry parse(String wkt) {
		if (wkt != null) {
			tempWkt = wkt;
			if (skip(org.geomajas.geometry.Geometry.MULTI_POLYGON.toUpperCase())) {
				return parseMultiPolygon();
			} else if (skip(org.geomajas.geometry.Geometry.POLYGON.toUpperCase())) {
				return parsePolygon();
			} else if (skip(org.geomajas.geometry.Geometry.MULTI_LINE_STRING.toUpperCase())) {
				return parseMultiLineString();
			} else if (skip(org.geomajas.geometry.Geometry.MULTI_POINT.toUpperCase())) {
				return parseMultiPoint();
			} else if (skip(org.geomajas.geometry.Geometry.LINE_STRING.toUpperCase())) {
				return parseLineString();
			} else if (skip(org.geomajas.geometry.Geometry.POINT.toUpperCase())) {
				return parsePoint();
			}
		}
		return null;
	}

	// Receives something like: (((x y, x y, ...)))
	private MultiPolygon parseMultiPolygon() {
		skip("(");
		List<Polygon> polygons = new ArrayList<Polygon>();
		while (!isAt(")")) {
			polygons.add(parsePolygon());
			skip(",");
		}
		skip(")");
		return factory.createMultiPolygon(polygons.toArray(new Polygon[polygons.size()]));
	}

	private MultiLineString parseMultiLineString() {
		skip("(");
		List<LineString> lineStrings = new ArrayList<LineString>();
		while (!isAt(")")) {
			lineStrings.add(parseLineString());
			skip(",");
		}
		skip(")");
		return factory.createMultiLineString(lineStrings.toArray(new LineString[] {}));
	}

	private MultiPoint parseMultiPoint() {
		skip("(");
		List<Point> points = new ArrayList<Point>();
		while (!isAt(")")) {
			points.add(parsePoint());
			skip(",");
		}
		skip(")");
		return factory.createMultiPoint(points.toArray(new Point[] {}));
	}

	// Receives something like: (((x y, x y, ...)))
	private Polygon parsePolygon() {
		skip("(");
		LinearRing exteriorRing = null;
		List<LinearRing> interiorRings = new ArrayList<LinearRing>();
		while (!isAt(")")) {
			if (exteriorRing == null) {
				exteriorRing = parseLinearRing();
			} else {
				interiorRings.add(parseLinearRing());
			}
			skip(",");
		}
		skip(")");
		return factory.createPolygon(exteriorRing, interiorRings.toArray(new LinearRing[interiorRings.size()]));
	}

	private LinearRing parseLinearRing() {
		skip("(");
		Coordinate[] coordinates = parseCoordinates();
		findAndSkip(")");
		return factory.createLinearRing(coordinates);
	}

	private LineString parseLineString() {
		skip("(");
		Coordinate[] coordinates = parseCoordinates();
		findAndSkip(")");
		return factory.createLineString(coordinates);
	}

	private Point parsePoint() {
		skip("(");
		Coordinate coordinate = parseCoordinate();
		findAndSkip(")");
		return factory.createPoint(coordinate);
	}

	private Coordinate parseCoordinate() {
		int pos = tempWkt.indexOf(')');
		String point = tempWkt.substring(0, pos);
		String[] values = point.split(" ");
		Coordinate coordinate = new Coordinate(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
		return coordinate;
	}

	private Coordinate[] parseCoordinates() {
		int pos = tempWkt.indexOf(')');
		String ring = tempWkt.substring(0, pos);
		String[] tokens = ring.split(",");
		Coordinate[] coordinates = new Coordinate[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String[] values = tokens[i].trim().split("\\s");
			coordinates[i] = new Coordinate(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
		}
		return coordinates;
	}

	private boolean isAt(String token) {
		tempWkt = tempWkt.trim();
		return tempWkt.startsWith(token);
	}

	private boolean skip(String token) {
		tempWkt = tempWkt.trim();
		if (tempWkt.startsWith(token)) {
			tempWkt = tempWkt.substring(token.length());
			return true;
		}
		return false;
	}

	private boolean findAndSkip(String token) {
		int pos = tempWkt.indexOf(token);
		if (pos != -1) {
			tempWkt = tempWkt.substring(pos + 1);
			return true;
		}
		return false;
	}

}
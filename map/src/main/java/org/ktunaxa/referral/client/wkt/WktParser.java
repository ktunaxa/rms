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
			tempWkt = wkt.substring(wkt.indexOf('('));
			if (wkt.startsWith(org.geomajas.geometry.Geometry.MULTI_POLYGON.toUpperCase())
					|| wkt.startsWith(org.geomajas.geometry.Geometry.MULTI_POLYGON)) {
				return parseMultiPolygon();
			} else if (wkt.startsWith(org.geomajas.geometry.Geometry.POLYGON.toUpperCase())
					|| wkt.startsWith(org.geomajas.geometry.Geometry.POLYGON)) {
				return parsePolygon();
			} else if (wkt.startsWith(org.geomajas.geometry.Geometry.MULTI_LINE_STRING.toUpperCase())
					|| wkt.startsWith(org.geomajas.geometry.Geometry.MULTI_LINE_STRING)) {
				return parseMultiLineString();
			} else if (wkt.startsWith(org.geomajas.geometry.Geometry.MULTI_POINT.toUpperCase())
					|| wkt.startsWith(org.geomajas.geometry.Geometry.MULTI_POINT)) {
				return parseMultiPoint();
			} else if (wkt.startsWith(org.geomajas.geometry.Geometry.LINE_STRING.toUpperCase())
					|| wkt.startsWith(org.geomajas.geometry.Geometry.LINE_STRING)) {
				return parseLineString();
			} else if (wkt.startsWith(org.geomajas.geometry.Geometry.POINT.toUpperCase())
					|| wkt.startsWith(org.geomajas.geometry.Geometry.POINT)) {
				return parsePoint();
			}
		}
		return null;
	}

	// Receives something like: (((x y, x y, ...)))
	private MultiPolygon parseMultiPolygon() {
		List<Polygon> polygons = new ArrayList<Polygon>();
		while (!tempWkt.startsWith(")")) {
			tempWkt = tempWkt.substring(tempWkt.indexOf("(") + 1);
			polygons.add(parsePolygon());
		}
		return factory.createMultiPolygon(polygons.toArray(new Polygon[] {}));
	}

	private MultiLineString parseMultiLineString() {
		return null;
	}

	private MultiPoint parseMultiPoint() {
		return null;
	}

	private Polygon parsePolygon() {
		LinearRing exteriorRing = null;
		List<LinearRing> interiorRings = new ArrayList<LinearRing>();
		while (!tempWkt.startsWith(")")) {
			tempWkt = tempWkt.substring(tempWkt.indexOf("(") + 1);
			if (exteriorRing == null) {
				exteriorRing = parseLinearRing();
			} else {
				interiorRings.add(parseLinearRing());
			}
		}
		tempWkt = tempWkt.substring(1);
		return factory.createPolygon(exteriorRing, interiorRings.toArray(new LinearRing[] {}));
	}

	private LinearRing parseLinearRing() {
		String ring = tempWkt.substring(1, tempWkt.indexOf(")"));
		tempWkt = tempWkt.substring(tempWkt.indexOf(")") + 1);
		String[] tokens = ring.split(",");
		Coordinate[] coordinates = new Coordinate[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String[] values = tokens[i].trim().split(" ");
			coordinates[i] = new Coordinate(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
		}
		return factory.createLinearRing(coordinates);
	}

	private LineString parseLineString() {
		String ring = tempWkt.substring(1, tempWkt.indexOf(")"));
		tempWkt = tempWkt.substring(tempWkt.indexOf(")") + 1);
		String[] tokens = ring.split(",");
		Coordinate[] coordinates = new Coordinate[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String[] values = tokens[i].trim().split(" ");
			coordinates[i] = new Coordinate(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
		}
		return factory.createLineString(coordinates);
	}

	// Receives something like: (((x y, x y, ...)))
	private Point parsePoint() {
		return null;
	}
}
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

package org.ktunaxa.referral.shapereader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.geomajas.internal.layer.VectorLayerServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.ktunaxa.referral.server.domain.ReferenceBase;
import org.ktunaxa.referral.server.domain.ReferenceBaseAttribute;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceValue;
import org.ktunaxa.referral.server.domain.ReferenceValueAttribute;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.PropertyName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.operation.valid.IsValidOp;
import com.vividsolutions.jts.operation.valid.TopologyValidationError;

/**
 * Implementation of the LayerPersistService, which does not specify transactional behavior. This service expects such
 * configurations to be done a level higher, spanning all methods.
 * 
 * @author Pieter De Graef
 */
@Component
public class LayerPersistServiceImpl implements LayerPersistService {

	private int srid = 26911;

	private final Logger log = LoggerFactory.getLogger(LayerPersistServiceImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ShapeReaderService shapeReaderService;

	/**
	 * Return the code for the SRID that all features/geometries should match.
	 * 
	 * @return The actual SRID code.
	 */
	public int getSrid() {
		return srid;
	}

	/**
	 * Clear all contents in the given reference layer from the database.
	 * 
	 * @param layer
	 *            The reference layer instance to clear.
	 */
	@SuppressWarnings("unchecked")
	public void clearLayer(ReferenceLayer layer) {
		int count = 0;
		if (layer.getType().isBaseLayer()) {
			// batch delete
			Session session = sessionFactory.getCurrentSession();
			session
					.createSQLQuery(
							"delete from reference_base_attribute where id in " +
							"(select attr.id from reference_base_attribute as attr, " +
							"reference_base as base where attr.reference_base_id=base.id and base.layer_id=:layerId)")
					.setLong("layerId", layer.getId()).executeUpdate();
			count = session
			.createSQLQuery(
					"delete from reference_base as base where base.layer_id=:layerId")
			.setLong("layerId", layer.getId()).executeUpdate();

			log.info(count + " base reference objects deleted");
		} else {
			// batch delete
			Session session = sessionFactory.getCurrentSession();
			session
					.createSQLQuery(
							"delete from reference_value_attribute where id in " +
							"(select attr.id from reference_value_attribute as attr, " +
							"reference_value as value where attr.reference_value_id=value.id and value.layer_id=:layerId)")
					.setLong("layerId", layer.getId()).executeUpdate();
			count = session
			.createSQLQuery(
					"delete from reference_value as value where value.layer_id=:layerId")
			.setLong("layerId", layer.getId()).executeUpdate();

		}
	}

	@SuppressWarnings("unchecked")
	private int deleteReferenceBase(ReferenceLayer layer, Class referenceClass) {

		Session session = sessionFactory.getCurrentSession();
		List features = session.createCriteria(referenceClass).add(Restrictions.eq("layer", layer)).setMaxResults(1000)
				.list();
		for (Object feature : features) {
			session.delete(feature);
		}
		session.flush();
		session.clear();
		return features.size();
	}

	/**
	 * Validate a single 'feature'. These features are part of a GeoTools DataStore (usually read from shape files). Use
	 * this validation method before actually persisting a feature.<br/>
	 * This method check for empty and invalid geometries only.
	 * 
	 * @param feature
	 *            The feature to validate.
	 * @throws IOException
	 *             If validation fails, an error is thrown. In this case no changes should be committed.
	 */
	public void validate(SimpleFeature feature) throws IOException {
		Geometry geometry = (Geometry) feature.getDefaultGeometry();
		if (geometry.isEmpty()) {
			throw new IOException("An empty geometry was found in the shape file. Feature ID=" + feature.getID());
		}
		if (!geometry.isValid()) {
			IsValidOp validOp = new IsValidOp(geometry);
			TopologyValidationError err = validOp.getValidationError();
			throw new IOException("An invalid geometry was found in the shape file: " + err.getMessage()
					+ ". Feature ID=" + feature.getID());
		}
		if (geometry.getSRID() == 0) {
			geometry.setSRID(getSrid()); // special case...
		} else if (geometry.getSRID() != getSrid()) {
			throw new IOException("Geometry has wrong coordinate system. SRID=" + geometry.getSRID());
		}
	}

	/**
	 * Convert a GeoTools feature to a ReferenceBase domain object.
	 * 
	 * @param layer
	 *            The base layer wherein the feature should lie.
	 * @param feature
	 *            The actual feature to convert.
	 * @return The converted feature as a ReferenceBase object.
	 */
	public ReferenceBase convertToBase(ReferenceLayer layer, SimpleFeature feature) {
		ReferenceBase base = new ReferenceBase();
		base.setLayer(layer);
		base.setGeometry((Geometry) feature.getDefaultGeometry());
		SimpleFeatureType type = feature.getFeatureType();
		Expression labelExpr = shapeReaderService.getLabelAttributeExpression(type.getTypeName());
		Expression styleExpr = shapeReaderService.getStyleAttributeExpression(type.getTypeName());
		Object temp = labelExpr.evaluate(feature);
		if (temp != null) {
			base.setLabel(temp.toString());
		}
		temp = styleExpr.evaluate(feature);
		if (temp != null) {
			base.setStyleCode(temp.toString());
		}

		List<ReferenceBaseAttribute> attributes = new ArrayList<ReferenceBaseAttribute>();
		for (AttributeDescriptor descr : feature.getFeatureType().getAttributeDescriptors()) {
			String key = descr.getLocalName();
			if (!isPropertyName(labelExpr, key) || isPropertyName(styleExpr, key)) {
				Object value = feature.getAttribute(key);
				if (value != null && !(value instanceof Geometry)) {
					ReferenceBaseAttribute attribute = new ReferenceBaseAttribute();
					attribute.setReference(base);
					attribute.setKey(key);
					attribute.setValue(value.toString());
					attributes.add(attribute);
				}
			}
		}
		base.setAttributes(attributes);
		return base;
	}

	private boolean isPropertyName(Expression expression, String propertyName) {
		if (expression instanceof PropertyName) {
			PropertyName prop = (PropertyName) expression;
			if (prop.getPropertyName().equals(propertyName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Convert a GeoTools feature to a ReferenceValue domain object.
	 * 
	 * @param layer
	 *            The value layer wherein the feature should lie.
	 * @param feature
	 *            The actual feature to convert.
	 * @return The converted feature as a ReferenceValue object.
	 */
	public ReferenceValue convertToValue(ReferenceLayer layer, SimpleFeature feature) {
		ReferenceValue value = new ReferenceValue();
		value.setLayer(layer);
		value.setGeometry((Geometry) feature.getDefaultGeometry());
		SimpleFeatureType type = feature.getFeatureType();
		Expression labelExpr = shapeReaderService.getLabelAttributeExpression(type.getTypeName());
		Expression styleExpr = shapeReaderService.getStyleAttributeExpression(type.getTypeName());
		Object temp = labelExpr.evaluate(feature);
		if (temp != null) {
			value.setLabel(temp.toString());
		}
		temp = styleExpr.evaluate(feature);
		if (temp != null) {
			value.setStyleCode(temp.toString());
		}

		List<ReferenceValueAttribute> attributes = new ArrayList<ReferenceValueAttribute>();
		for (AttributeDescriptor descr : feature.getFeatureType().getAttributeDescriptors()) {
			String key = descr.getLocalName();
			if (isPropertyName(labelExpr, key) || isPropertyName(styleExpr, key)) {
				Object attributeValue = feature.getAttribute(key);
				if (attributeValue != null && !(attributeValue instanceof Geometry)) {
					ReferenceValueAttribute attribute = new ReferenceValueAttribute();
					attribute.setReference(value);
					attribute.setKey(key);
					attribute.setValue(attributeValue.toString());
					attributes.add(attribute);
				}
			}
		}
		value.setAttributes(attributes);
		return value;
	}

	/**
	 * Persist a reference base object in the database.
	 * 
	 * @param reference
	 *            The object to save.
	 */
	public void persist(ReferenceBase reference) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(reference);
	}

	/**
	 * Persist a reference value object in the database.
	 * 
	 * @param reference
	 *            The object to save.
	 */
	public void persist(ReferenceValue reference) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(reference);
	}

	// ------------------------------------------------------------------------
	// Extra setters:
	// ------------------------------------------------------------------------

	/**
	 * Set a new value for the required SRID that all geometries must match.
	 * 
	 * @param srid
	 *            The new SRID value.
	 */
	public void setSrid(int srid) {
		this.srid = srid;
	}

	public void flushSession() {
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
	}
}
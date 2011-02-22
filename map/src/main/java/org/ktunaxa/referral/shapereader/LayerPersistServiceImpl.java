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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.ktunaxa.referral.server.domain.ReferenceBase;
import org.ktunaxa.referral.server.domain.ReferenceBaseAttribute;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceValue;
import org.ktunaxa.referral.server.domain.ReferenceValueAttribute;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.AttributeDescriptor;
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

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Clear all contents in the given reference layer from the database.
	 * 
	 * @param layer
	 *            The reference layer instance to clear.
	 */
	@SuppressWarnings("unchecked")
	public void clearLayer(ReferenceLayer layer) {
		Session session = sessionFactory.getCurrentSession();
		if (layer.getType().isBaseLayer()) {
			List<ReferenceBase> features = session.createCriteria(ReferenceBase.class)
					.add(Restrictions.eq("layer", layer)).list();
			for (ReferenceBase feature : features) {
				session.delete(feature);
			}
		} else {
			List<ReferenceValue> features = session.createCriteria(ReferenceBase.class)
					.add(Restrictions.eq("layer", layer)).list();
			for (ReferenceValue feature : features) {
				session.delete(feature);
			}
		}
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
		Object temp = feature.getAttribute("RMS_LABEL");
		if (temp != null) {
			base.setLabel(temp.toString());
		}
		temp = feature.getAttribute("RMS_STYLE");
		if (temp != null) {
			base.setStyleCode(temp.toString());
		}

		List<ReferenceBaseAttribute> attributes = new ArrayList<ReferenceBaseAttribute>();
		for (AttributeDescriptor descr : feature.getFeatureType().getAttributeDescriptors()) {
			String key = descr.getLocalName();
			if (!"RMS_LABEL".equalsIgnoreCase(key) && !"RMS_STYLE".equalsIgnoreCase(key)) {
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
		Object temp = feature.getAttribute("RMS_LABEL");
		if (temp != null) {
			value.setLabel(temp.toString());
		}
		temp = feature.getAttribute("RMS_STYLE");
		if (temp != null) {
			value.setStyleCode(temp.toString());
		}

		List<ReferenceValueAttribute> attributes = new ArrayList<ReferenceValueAttribute>();
		for (AttributeDescriptor descr : feature.getFeatureType().getAttributeDescriptors()) {
			String key = descr.getLocalName();
			if (!"RMS_LABEL".equalsIgnoreCase(key) && !"RMS_STYLE".equalsIgnoreCase(key)) {
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
}
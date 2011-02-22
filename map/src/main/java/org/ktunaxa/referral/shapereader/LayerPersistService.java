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

import org.ktunaxa.referral.server.domain.ReferenceBase;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceValue;
import org.opengis.feature.simple.SimpleFeature;

/**
 * <p>
 * Utility service that is used in the process of replacing the contents of a reference layer with new contents. The
 * methods within this service make that process easier. This service is used in the {@link ShapeReaderService} - which
 * is the main service responsible for uploading new shape file into the database.
 * </p>
 * <p>
 * Implementations of this service should not be 'transactional', as a single transaction should span the use of all the
 * methods herein (you don't want to commit after clearing the current contents only to find out that the shape file
 * contains some problems).
 * </p>
 * 
 * @author Pieter De Graef
 */
public interface LayerPersistService {

	/**
	 * Return the code for the SRID that all features/geometries should match.
	 * 
	 * @return The actual SRID code.
	 */
	int getSrid();

	/**
	 * Clear all contents in the given reference layer from the database.
	 * 
	 * @param layer
	 *            The reference layer instance to clear.
	 */
	void clearLayer(ReferenceLayer layer);

	/**
	 * Validate a single 'feature'. These features are part of a GeoTools DataStore (usually read from shape files). Use
	 * this validation method before actually persisting a feature.
	 * 
	 * @param feature
	 *            The feature to validate.
	 * @throws IOException
	 *             If validation fails, an error is thrown. In this case no changes should be committed.
	 */
	void validate(SimpleFeature feature) throws IOException;

	/**
	 * Convert a GeoTools feature to a ReferenceBase domain object.
	 * 
	 * @param layer
	 *            The base layer wherein the feature should lie.
	 * @param feature
	 *            The actual feature to convert.
	 * @return The converted feature as a ReferenceBase object.
	 */
	ReferenceBase convertToBase(ReferenceLayer layer, SimpleFeature feature);

	/**
	 * Convert a GeoTools feature to a ReferenceValue domain object.
	 * 
	 * @param layer
	 *            The value layer wherein the feature should lie.
	 * @param feature
	 *            The actual feature to convert.
	 * @return The converted feature as a ReferenceValue object.
	 */
	ReferenceValue convertToValue(ReferenceLayer layer, SimpleFeature feature);

	/**
	 * Persist a reference base object in the database.
	 * 
	 * @param reference
	 *            The object to save.
	 */
	void persist(ReferenceBase reference);

	/**
	 * Persist a reference value object in the database.
	 * 
	 * @param reference
	 *            The object to save.
	 */
	void persist(ReferenceValue reference);
}
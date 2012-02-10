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

package org.ktunaxa.referral.shapereader;

import java.io.IOException;

import org.ktunaxa.referral.server.domain.ReferenceBase;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceValue;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.expression.Expression;

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

	/**
	 * Flushes the in-memory session.
	 */
	void flushSession();

	/**
	 * Returns the expression to evaluate to obtain the label attribute of a feature.
	 *
	 * @param typeName type name
	 * @return the expression
	 */
	Expression getLabelAttributeExpression(String typeName);

	/**
	 * Returns the expression to evaluate to obtain the style attribute of a feature.
	 *
	 * @param typeName type name
	 * @return the expression
	 */
	Expression getStyleAttributeExpression(String typeName);

}
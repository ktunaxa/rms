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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.FeatureIterator;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.referencing.CRS;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ktunaxa.referral.server.domain.ReferenceBase;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceValue;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.PropertyName;
import org.opengis.referencing.FactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link ShapeReaderService} that uses a pre-configured
 * base path to search for shape files, and also uses the
 * {@link LayerPersistService} to actually persist shape files into the
 * database.
 * 
 * @author Pieter De Graef
 */
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
@Component("shapeReaderService")
public class ShapeReaderServiceImpl implements ShapeReaderService {

	@Autowired
	private LayerPersistService persistService;

	@Autowired
	private SessionFactory sessionFactory;

	private String basePath;

	private static final String STYLE_ATTRIBUTE = "RMS_STYLE";

	private static final String LABEL_ATTRIBUTE = "RMS_LABEL";

	private Map<String, String> styleAttributeMap = new HashMap<String, String>();

	private Map<String, String> labelAttributeMap = new HashMap<String, String>();

	
	private final Logger log = LoggerFactory.getLogger(ShapeReaderServiceImpl.class);

	// ------------------------------------------------------------------------
	// ShapeReaderService implementation:
	// ------------------------------------------------------------------------

	/**
	 * <p>
	 * Retrieve a list of available shape files to chose from. The idea is that
	 * in some location a list of shape files can be retrieved, of which we want
	 * to upload one.
	 * </p>
	 * <p>
	 * This implementation uses a "base path" (system folder) to look for shape
	 * files.
	 * </p>
	 * 
	 * @param subDirectory extra path element to indicate sub-package/directory
	 * @return Returns the full list of available shape files available on the
	 *         configured base path.
	 * @throws IOException
	 *             Thrown if something goes wrong while retrieving available
	 *             shape files.
	 */
	public File[] getAllFiles(String subDirectory) throws IOException {
		if (basePath.startsWith("classpath:")) {
			String fullPath = basePath.substring(10);
			if (subDirectory != null && subDirectory.trim().length() > 0) {
				fullPath = fullPath + "/" + subDirectory;
			}
			PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
			Resource[] resources = pmrpr.getResources(fullPath + File.separator + "*.shp");
			int length = resources.length;
			File[] files = new File[length];
			for (int i = 0; i < length; i++) {
				files[i] = resources[i].getFile();
			}
			return files;
		} else {
			String fullPath = basePath;
			if (subDirectory != null && subDirectory.trim().length() > 0) {
				fullPath = fullPath + File.separator + subDirectory;
			}
			File folder = new File(fullPath); // We don't have to check for folder==null.
			if (!folder.isDirectory()) {
				throw new IOException("Configured base path is not a directory: " + basePath + " translated to "
						+ fullPath);
			}
			return folder.listFiles(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					return name.endsWith(".shp");
				}
			});
		}

	}

	/**
	 * Actually read a shape file (.shp) and return it in the form of a GeoTools
	 * DataStore.
	 * 
	 * @param file
	 *            The chosen shape file to read. Must be a .shp file - no
	 *            compressed file!
	 * @return Returns the GeoTools DataStore representation of that shape file.
	 *         This DataStore can read the contents.
	 * @throws IOException
	 */
	public DataStore read(File file) throws IOException {
		ShapefileDataStoreFactory factory = new ShapefileDataStoreFactory();
		DataStore shpStore = factory.createDataStore(new URL("file://" + file.getAbsolutePath()));
		if (shpStore == null) {
			throw new IOException("Shape file could not be properly read.");
		}
		return shpStore;
	}

	/**
	 * Validate the given GeoTools DataStore to see if it's contents can be used
	 * as a reference layer (base or value). For validation to succeed, 2
	 * specific attributes need to be available:
	 * <ul>
	 * <li>RMS_LABEL</li>
	 * <li>RMS_STYLE</li>
	 * </ul>
	 * 
	 * @param dataStore
	 *            The DataStore to validate.
	 * @throws IOException
	 *             Thrown when the DataStore does not meet the requirements.
	 */
	public void validateFormat(DataStore dataStore) throws IOException {
		String[] typeNames = dataStore.getTypeNames();
		for (String typeName : typeNames) {
			SimpleFeatureType schema = dataStore.getSchema(typeName);
			Expression style = getStyleAttributeExpression(typeName);
			Expression label = getLabelAttributeExpression(typeName);
			if (style == Expression.NIL) {
				throw new IOException("Can't evaluate attribute expression");
			}
			if (label == Expression.NIL) {
				throw new IOException("Can't evaluate label expression");
			}
			if (!isValid(style, schema)) {
				throw new IOException("The attribute '" + style + "' is missing from the shape file definition.");
			}
			if (!isValid(label, schema)) {
				throw new IOException("The attribute '" + label + "' is missing from the shape file definition.");
			}
			Integer code;
			try {
				code = CRS.lookupEpsgCode(schema.getCoordinateReferenceSystem(), false);
			} catch (FactoryException fe) {
				throw new IOException("Could not look up EPSG code for coordinate reference system: "
								+ fe.getMessage(), fe);
			}
			if (code == null) {
				// Backup plan:
				String crsName = schema.getCoordinateReferenceSystem()
						.getName().getCode();
				if ("NAD_1983_UTM_Zone_11N".equalsIgnoreCase(crsName)) {
					code = KtunaxaConstant.LAYER_SRID;
				} else {
					throw new IOException( "Unknown coordinate reference system: " + crsName);
				}
			}
			if (code != persistService.getSrid()) {
				throw new IOException(
						"The shape files contains the wrong coordinate reference system. EPSG"
								+ persistService.getSrid() + " was expected.");
			}
		}
	}

	/**
	 * Get the full list of known reference layers (both base and value).
	 * 
	 * @return The full list of known reference layers (both base and value).
	 */
	@SuppressWarnings("unchecked")
	public List<ReferenceLayer> getAllLayers() {
		Session session = sessionFactory.getCurrentSession();
		return (List<ReferenceLayer>) session.createCriteria(
				ReferenceLayer.class).list();
	}

	/**
	 * <p>
	 * Persist the given DataStore as the new contents for the given layer into
	 * the database, replacing the old contents.
	 * </p>
	 * <p>
	 * This method make use of the {@link LayerPersistService} to do the actual
	 * work. It simply makes sure that everything happens within a single
	 * transaction.
	 * </p>
	 * 
	 * @param layer
	 *            The reference layer to replace the contents in.
	 * @param dataStore
	 *            The data source containing the new contents.
	 * @throws IOException
	 *             Thrown when any error occurs within the process. This should
	 *             automatically roll back the database to the original state.
	 */
	public void persist(ReferenceLayer layer, DataStore dataStore)
			throws IOException {
		persistService.clearLayer(layer);

		String[] typeNames = dataStore.getTypeNames();
		FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore
				.getFeatureSource(typeNames[0]);
		FeatureIterator<SimpleFeature> iterator = source.getFeatures().features();
		int i = 0;
		while (iterator.hasNext()) {
			SimpleFeature feature = iterator.next();
			persistService.validate(feature);

			if (layer.getType().isBaseLayer()) {
				ReferenceBase reference = persistService.convertToBase(layer, feature);
				persistService.persist(reference);
			} else {
				ReferenceValue reference = persistService.convertToValue(layer, feature);
				persistService.persist(reference);
			}
			if (++i % 50 == 0) {
				persistService.flushSession();
			}
			if (i % 1000 == 0) {
				log.info(i + " objects persisted.");
			}
		}
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * Set the folder that determines where this service should go looking for
	 * shape files.
	 * 
	 * @param basePath
	 *            The new shape file base directory.
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setStyleAttributeMap(Map<String, String> styleAttributeMap) {
		this.styleAttributeMap = styleAttributeMap;
	}

	public void setLabelAttributeMap(Map<String, String> labelAttributeMap) {
		this.labelAttributeMap = labelAttributeMap;
	}

	public Expression getLabelAttributeExpression(String typeName) {
		try {
			if (labelAttributeMap.containsKey(typeName)) {
				return CQL.toExpression(labelAttributeMap.get(typeName));
			} else {
				return CQL.toExpression(LABEL_ATTRIBUTE);
			}
		} catch (CQLException e) {
			return Expression.NIL;
		}
	}

	public Expression getStyleAttributeExpression(String typeName) {
		try {
			if (styleAttributeMap.containsKey(typeName)) {
				return CQL.toExpression(styleAttributeMap.get(typeName));
			} else {
				return CQL.toExpression(STYLE_ATTRIBUTE);
			}
		} catch (CQLException e) {
			return Expression.NIL;
		}
	}
	
	private boolean isValid(Expression expression, SimpleFeatureType schema) {
		// can only validate if property
		if (expression instanceof PropertyName) {
			PropertyName prop = (PropertyName) expression;
			return schema.getType(prop.getPropertyName()) != null;
		}
		return true;
	}
}
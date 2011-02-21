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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ktunaxa.referral.server.domain.ReferenceBase;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceValue;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link ShapeReaderService} that uses a pre-configured base path to search for shape files, and
 * also uses the {@link LayerPersistServiceImpl} to actually persist shape files into the database.
 * 
 * @author Pieter De Graef
 */
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class ShapeReaderServiceImpl implements ShapeReaderService {

	@Autowired
	private LayerPersistServiceImpl persistService;

	@Autowired
	private SessionFactory sessionFactory;

	private String basePath;

	// ------------------------------------------------------------------------
	// ShapeReaderService implementation:
	// ------------------------------------------------------------------------

	/**
	 * <p>
	 * Retrieve a list of available shape files to chose from. The idea is that in some location a list of shape files
	 * can be retrieved, of which we want to upload one.
	 * </p>
	 * <p>
	 * This implementation uses a "base path" (system folder) to look for shape files.
	 * </p>
	 * 
	 * @return Returns the full list of available shape files available on the configured base path.
	 * @throws IOException
	 *             Thrown if something goes wrong while retrieving available shape files.
	 */
	public File[] getAllFiles() throws IOException {
		String path = basePath;
		if (basePath.startsWith("classpath:")) {
			URL url = getClass().getResource("/");
			path = url.getFile() + basePath.substring(10);
		}

		File folder = new File(path); // We don't have to check for folder==null.
		if (!folder.isDirectory()) {
			throw new IOException("Configured base path is not a directory: " + basePath);
		}
		File[] files = folder.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".shp");
			}
		});
		return files;
	}

	/**
	 * Actually read a shape file (.shp) and return it in the form of a GeoTools DataStore.
	 * 
	 * @param file
	 *            The chosen shape file to read. Must be a .shp file - no compressed file!
	 * @return Returns the GeoTools DataStore representation of that shape file. This DataStore can read the contents.
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
	 * Validate the given GeoTools DataStore to see if it's contents can be used as a reference layer (base or value).
	 * For validation to succeed, 2 specific attributes need to be available:
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
			if (schema.getType("RMS_STYLE") == null) {
				throw new IOException("The attribute 'RMS_STYLE' is missing from the shape file definition.");
			}
			if (schema.getType("RMS_LABEL") == null) {
				throw new IOException("The attribute 'RMS_LABEL' is missing from the shape file definition.");
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
		return (List<ReferenceLayer>) session.createCriteria(ReferenceLayer.class).list();
	}

	/**
	 * <p>
	 * Persist the given DataStore as the new contents for the given layer into the database, replacing the old
	 * contents.
	 * </p>
	 * <p>
	 * This method make use of the {@link LayerPersistService} to do the actual work. It simply makes sure that
	 * everything happens within a single transaction.
	 * </p>
	 * 
	 * @param layer
	 *            The reference layer to replace the contents in.
	 * @param dataStore
	 *            The data source containing the new contents.
	 * @throws IOException
	 *             Thrown when any error occurs within the process. This should automatically roll back the database to
	 *             the original state.
	 */
	public void persist(ReferenceLayer layer, DataStore dataStore) throws IOException {
		persistService.clearLayer(layer);

		String[] typeNames = dataStore.getTypeNames();
		FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeNames[0]);
		Iterator<SimpleFeature> iterator = source.getFeatures().iterator();
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
		}
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * Get the current base path that determines where to look for shape files. Must be a folder!
	 * 
	 * @return The current base path that determines where to look for shape files.
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * Set the folder that determines where this service should go looking for shape files.
	 * 
	 * @param basePath
	 *            The new shape file base directory.
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
}
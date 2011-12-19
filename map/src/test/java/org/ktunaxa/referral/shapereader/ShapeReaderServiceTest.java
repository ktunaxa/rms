/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.shapereader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.geotools.data.DataStore;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test cases for the ShapeReaderService interface.
 * 
 * @author Pieter De Graef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
		"/org/ktunaxa/referral/server/HibernateContext.xml", "applicationContext.xml" })
public class ShapeReaderServiceTest {

	private static final int NR_SHAPES = 3;

	@Autowired
	private ShapeReaderService service;

	@Test
	public void testGetAllFiles() throws IOException {
		Assert.assertNotNull(service);
		File[] files = service.getAllFiles(null);
		Assert.assertNotNull(files);
		Assert.assertTrue(files.length == NR_SHAPES);
	}

	@Test
	public void testSubDirectory() throws IOException {
		Assert.assertNotNull(service);
		File[] files = service.getAllFiles("sub");
		Assert.assertNotNull(files);
		Assert.assertTrue(files.length == 1);
	}

	@Test
	public void testRead() throws IOException {
		Assert.assertNotNull(service);
		File[] files = service.getAllFiles(null);
		Assert.assertNotNull(files);
		Assert.assertTrue(files.length == NR_SHAPES);
		DataStore ds = service.read(files[0]);
		Assert.assertNotNull(ds);
		Assert.assertTrue(ds instanceof ShapefileDataStore);
	}

	@Test
	public void testValidateFormat() throws IOException {
		Assert.assertNotNull(service);
		File[] files = service.getAllFiles(null);
		Assert.assertNotNull(files);
		Assert.assertTrue(files.length == NR_SHAPES);
		service.validateFormat(service.read(getFile(files, "OK.shp")));
		try {
			service.validateFormat(service.read(getFile(files, "NoStyle.shp")));
			Assert.fail(); // should never get here.
		} catch (IOException e) {
			System.err.println(e.getMessage() + " ..... as expected.");
			Assert.assertTrue(true);
		}
		service.validateFormat(service.read(getFile(files, "Invalid.shp")));
	}

	private File getFile(File[] files, String nameSuffix) {
		for (File file : files) {
			if (file.getAbsolutePath().endsWith(nameSuffix)) {
				return file;
			}
		}
		return null;
	}

	@Test
	public void testGetAllLayers() throws IOException {
		Assert.assertNotNull(service);
		List<ReferenceLayer> layers = service.getAllLayers();
		Assert.assertNotNull(layers);
		Assert.assertTrue(layers.size() > 50);
	}

	@Test
	public void testPersist() throws IOException {
		Assert.assertNotNull(service);
		ReferenceLayer layer = service.getAllLayers().get(0);
		File[] files = service.getAllFiles(null);

		// invalid shapes should now be accepted, though with a  warning
		service.persist(layer, service.read(getFile(files, "Invalid.shp")));

		service.persist(layer, service.read(getFile(files, "OK.shp")));
	}
}
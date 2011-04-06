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
		File[] files = service.getAllFiles();
		Assert.assertNotNull(files);
		Assert.assertTrue(files.length == NR_SHAPES);
	}

	@Test
	public void testRead() throws IOException {
		Assert.assertNotNull(service);
		File[] files = service.getAllFiles();
		Assert.assertNotNull(files);
		Assert.assertTrue(files.length == NR_SHAPES);
		DataStore ds = service.read(files[0]);
		Assert.assertNotNull(ds);
		Assert.assertTrue(ds instanceof ShapefileDataStore);
	}

	@Test
	public void testValidateFormat() throws IOException {
		Assert.assertNotNull(service);
		File[] files = service.getAllFiles();
		Assert.assertNotNull(files);
		Assert.assertTrue(files.length == NR_SHAPES);
		try {
			service.validateFormat(service.read(files[0]));
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.fail(); // should never get here.
		}
		try {
			service.validateFormat(service.read(files[1]));
			Assert.fail(); // should never get here.
		} catch (IOException e) {
			System.err.println(e.getMessage() + " ..... as expected.");
			Assert.assertTrue(true);
		}
		try {
			service.validateFormat(service.read(files[2]));
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.fail(); // should never get here.
		}
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
		File[] files = service.getAllFiles();

		try {
			service.persist(layer, service.read(files[0]));
			Assert.fail(); // should never get here.
		} catch (IOException e) {
			System.err.println(e.getMessage() + " ..... as expected.");
			Assert.assertTrue(true);
		}
		try {
			service.persist(layer, service.read(files[2]));
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.fail(); // should never get here.
		}
	}
}
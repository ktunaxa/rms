package org.ktunaxa.referral.server.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test cases for the ReferenceLayerService interface.
 * 
 * @author Pieter De Graef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
		"/org/ktunaxa/referral/server/HibernateContext.xml" })
public class ReferenceLayerServiceTest {

	@Autowired
	private ReferenceLayerService service;

	@Test
	public void testFind() {
		Assert.assertNotNull(service);
		List<ReferenceLayer> layers = service.findLayers();
		Assert.assertNotNull(layers);
		Assert.assertTrue(layers.size() > 50);
		ReferenceLayer layer = layers.get(0);
		Assert.assertNotNull(layer);
		Assert.assertNotNull(layer.getName());
		Assert.assertNotNull(layer.getType());
		Assert.assertNotNull(layer.getType().getDescription());
	}
}
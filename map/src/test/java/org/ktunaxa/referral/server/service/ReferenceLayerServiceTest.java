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
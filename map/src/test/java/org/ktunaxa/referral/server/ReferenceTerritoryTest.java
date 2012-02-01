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

package org.ktunaxa.referral.server;

import org.geomajas.geometry.Bbox;
import org.geomajas.service.GeoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for the Ktunaxa territory constants.
 *
 * @author Joachim Van der Auwera
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
		"/org/ktunaxa/referral/server/HibernateContext.xml" })
public class ReferenceTerritoryTest {

	private static final double DELTA = 1e-6;

	@Autowired
	private GeoService geoService;

	@Test
	public void testKtunaxaTerritory() throws Exception {
		Bbox bbox = new Bbox(KtunaxaConstant.KTUNAXA_TERRITORY_MIN_X, KtunaxaConstant.KTUNAXA_TERRITORY_MIN_Y, 1, 1);
		bbox.setMaxX(KtunaxaConstant.KTUNAXA_TERRITORY_MAX_X);
		bbox.setMaxY(KtunaxaConstant.KTUNAXA_TERRITORY_MAX_Y);
		Bbox map = geoService.transform(bbox, KtunaxaConstant.LAYER_CRS, KtunaxaConstant.MAP_CRS);
		System.out.println(map.getX());
		System.out.println(map.getY());
		System.out.println(map.getMaxX());
		System.out.println(map.getMaxY());
		Bbox rev = geoService.transform(map, KtunaxaConstant.MAP_CRS, KtunaxaConstant.LAYER_CRS);
		System.out.println("--");
		System.out.println(rev.getX());
		System.out.println(rev.getY());
		System.out.println(rev.getMaxX());
		System.out.println(rev.getMaxY());
		Assert.assertEquals(KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MIN_X, map.getX(), DELTA);
		Assert.assertEquals(KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MIN_Y, map.getY(), DELTA);
		Assert.assertEquals(KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MAX_X, map.getMaxX(), DELTA);
		Assert.assertEquals(KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MAX_Y, map.getMaxY(), DELTA);
	}
}
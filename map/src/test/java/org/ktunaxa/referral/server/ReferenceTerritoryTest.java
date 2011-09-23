/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
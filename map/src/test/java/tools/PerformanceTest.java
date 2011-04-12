package tools;


import java.util.List;
import java.util.Random;

import org.geomajas.configuration.NamedStyleInfo;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.LayerException;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.layer.feature.attribute.AssociationAttribute;
import org.geomajas.security.SecurityManager;
import org.geomajas.service.FilterService;
import org.geomajas.service.GeoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vividsolutions.jts.geom.Envelope;

/**
 * Simple test to see if the Hibernate mapping is correct.
 * 
 * @author Pieter De Graef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
		"/org/ktunaxa/referral/server/HibernateContext.xml", "/org/ktunaxa/referral/server/layerReferenceBase.xml" })
public class PerformanceTest {

	@Autowired
	private VectorLayerService vectorLayerService;

	@Autowired
	private GeoService geoService;

	@Autowired
	private SecurityManager securityManager;

	@Autowired
	private FilterService filterService;

	@Autowired
	@Qualifier("referenceBaseStyleInfo")
	private NamedStyleInfo referenceBaseStyleInfo;

	@Before
	public void before() {
		securityManager.createSecurityContext(null);
	}

	@Test
	public void testGetFeatures() throws LayerException, GeomajasException {
		
		int nrFeatures = 0;
		int nrCoords = 0;
		Random rand = new Random();
		System.out.println("Starting...");
		for(int i = 0; i <= 100; i++){
			Filter bbox = filterService.createBboxFilter(geoService.getCrs2("EPSG:26911"), new Envelope(637470+rand.nextDouble(),
					637742+rand.nextDouble(), 5461219+rand.nextDouble(), 5465601+rand.nextDouble()), "geometry");
			List<InternalFeature> features = vectorLayerService.getFeatures("referenceBase",
					geoService.getCrs2("EPSG:26911"), bbox, referenceBaseStyleInfo, VectorLayerService.FEATURE_INCLUDE_ALL);
			nrFeatures += features.size();
			for (InternalFeature internalFeature : features) {
				nrCoords += internalFeature.getGeometry().getCoordinates().length;
			}
			System.out.println(nrFeatures+" features, "+nrCoords+" coordinates.");
		}
	}
}

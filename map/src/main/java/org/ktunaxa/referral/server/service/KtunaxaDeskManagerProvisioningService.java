package org.ktunaxa.referral.server.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.geomajas.geometry.Bbox;
import org.geomajas.geometry.Geometry;
import org.geomajas.geometry.service.GeometryService;
import org.geomajas.geometry.service.WktException;
import org.geomajas.global.GeomajasException;
import org.geomajas.plugin.deskmanager.domain.Blueprint;
import org.geomajas.plugin.deskmanager.domain.Geodesk;
import org.geomajas.plugin.deskmanager.domain.security.Territory;
import org.geomajas.plugin.deskmanager.domain.security.TerritoryCategory;
import org.geomajas.service.DtoConverterService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ktunaxa.referral.client.deskmanager.KtunaxaUserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KtunaxaDeskManagerProvisioningService {

	private static final int SRID = 3857;

	private static final String EPSG_3857 = "EPSG:3857";
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DtoConverterService dtoConverterService;

	private static ResourceBundle messages;
	
	static {
		initMessages();
	}

	public void createData() throws WktException, GeomajasException {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<TerritoryCategory> cats = session.createCriteria(TerritoryCategory.class).list();

		if (cats.isEmpty()) {

			// Create category
			TerritoryCategory cat = new TerritoryCategory();
			cat.setCategoryType(getMessage("testTerritoryCategoryType"));
			cat.setDescription(getMessage("testTerritoryCategoryDescription"));
			cat.setId("ALL");

			session.saveOrUpdate(cat);

			// Create group Admin
			Territory adminGroup = new Territory();
			adminGroup.setCode("ADMIN");
			adminGroup.setName(getMessage("adminGroupName"));
			adminGroup.setCrs(EPSG_3857);
			adminGroup.setCategory(cat);

			Geometry allGeom = GeometryService.toPolygon(Bbox.ALL);
			allGeom.setSrid(SRID);
			adminGroup.setGeometry(dtoConverterService.toInternal(allGeom));
			// adminGroup.setTerritory(dtoConverterService.toInternal(geom));

			session.saveOrUpdate(adminGroup);


			// Create an example blueprint.
			Blueprint bluePrint = new Blueprint();
			bluePrint.setActive(true);
			bluePrint.setCreationBy(getMessage("systemUsr"));
			bluePrint.setCreationDate(new Date());
			bluePrint.setTerritories(Arrays.asList(adminGroup));
			bluePrint.setLastEditBy(getMessage("systemUsr"));
			bluePrint.setLastEditDate(new Date());
			bluePrint.setLimitToCreatorTerritory(false);
			bluePrint.setLimitToUserTerritory(false);
			bluePrint.setGeodesksActive(true);
			bluePrint.setName(KtunaxaUserApplication.CLIENTAPPLICATION_NAME);
			bluePrint.setPublic(true);
			bluePrint.setUserApplicationKey(KtunaxaUserApplication.IDENTIFIER);
			
			session.saveOrUpdate(bluePrint);

			// Create ktunaxa geodesk.
			Geodesk geodesk = new Geodesk();
			geodesk.setActive(true);
			geodesk.setBlueprint(bluePrint);
			geodesk.setCreationBy(getMessage("systemUsr"));
			geodesk.setCreationDate(new Date());
			geodesk.setDeleted(false);
			geodesk.setLastEditBy(getMessage("systemUsr"));
			geodesk.setLastEditDate(new Date());
			geodesk.setOwner(adminGroup);
			geodesk.setLimitToCreatorTerritory(false);
			geodesk.setLimitToUserTerritory(false);
			geodesk.setName(KtunaxaUserApplication.CLIENTAPPLICATION_NAME);
			geodesk.setPublic(true);
			geodesk.setGeodeskId(KtunaxaConstant.APPLICATION);
			session.saveOrUpdate(geodesk);
			session.flush();
			session.close();
		}
	}

	private static void initMessages() {
		try {
			messages = ResourceBundle.getBundle("org/geomajas/plugin/deskmanager/i18n/ServiceMessages");

		} catch (MissingResourceException e) {
		}

	}

	private static String getMessage(String key) {
		return messages.getString(key);
	}
	
	@PostConstruct
	public void postConstruct() throws WktException, GeomajasException {
		createData();
	}


}

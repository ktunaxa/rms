package org.ktunaxa.referral.server.service;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.plugin.deskmanager.domain.security.Profile;
import org.geomajas.plugin.deskmanager.domain.security.Territory;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;
import org.geomajas.plugin.deskmanager.security.ProfileService;
import org.springframework.stereotype.Component;

@Component
public class ProfileServiceImpl implements ProfileService{

	@Override
	public List<Profile> getProfiles(String securityToken) {
		ArrayList<Profile> profiles = new ArrayList<Profile>();		
		Profile admin = new Profile();
		admin.setRole(Role.ADMINISTRATOR);
		admin.setId("admin");
		admin.setFirstName("admin");
		admin.setSurname("admin");
		admin.setTerritory(new Territory());
		admin.getTerritory().setId(0);
		profiles.add(admin);
		return profiles;
	}

}

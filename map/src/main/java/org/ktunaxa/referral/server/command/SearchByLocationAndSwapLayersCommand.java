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
package org.ktunaxa.referral.server.command;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.command.dto.SearchByLocationRequest;
import org.geomajas.command.dto.SearchByLocationResponse;
import org.geomajas.command.feature.SearchByLocationCommand;
import org.geomajas.geometry.Crs;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.LayerException;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.feature.Feature;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.service.DtoConverterService;
import org.geomajas.service.FilterService;
import org.geomajas.service.GeoService;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.opengis.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Swaps geotools version of the reference layers for the hibernate version to get the complex attributes. Hibernate
 * layers are made invisible on the client side and are now only used to obtain the attributes.
 * 
 * @author Jan De Moerloose
 * 
 */
public class SearchByLocationAndSwapLayersCommand extends SearchByLocationCommand {

	@Autowired
	private FilterService filterCreator;

	@Autowired
	private VectorLayerService layerService;

	@Autowired
	private GeoService geoService;

	@Autowired
	private DtoConverterService converter;

	@Override
	public void execute(SearchByLocationRequest request, SearchByLocationResponse response) throws Exception {
		super.execute(request, response);
		swapLayers(request, response, KtunaxaConstant.LAYER_REFERENCE_BASE_SERVER_ID,
				KtunaxaConstant.LAYER_REFERENCE_BASE_SERVER_HIB_ID);
		swapLayers(request, response, KtunaxaConstant.LAYER_REFERENCE_VALUE_SERVER_ID,
				KtunaxaConstant.LAYER_REFERENCE_VALUE_SERVER_HIB_ID);
	}

	private void swapLayers(SearchByLocationRequest request, SearchByLocationResponse response, String from, String to)
			throws LayerException, GeomajasException {
		if (response.getFeatureMap().containsKey(from)) {
			String[] ids = getIds(response.getFeatureMap().get(from));
			Crs crs = geoService.getCrs2(request.getCrs());
			Filter f = filterCreator.createFidFilter(ids);
			List<InternalFeature> temp = layerService.getFeatures(to, crs, f, null, request.getFeatureIncludes());
			List<Feature> features = new ArrayList<Feature>();
			for (InternalFeature feature : temp) {
				Feature dto = converter.toDto(feature);
				dto.setCrs(request.getCrs());
				features.add(dto);
			}
			response.getFeatureMap().remove(from);
			response.getFeatureMap().put(to, features);
		}
	}

	private String[] getIds(List<Feature> features) {
		String[] ids = new String[features.size()];
		for (int i = 0; i < ids.length; i++) {
			String id = features.get(i).getId();
			if (id.indexOf(".") >= 0) {
				id = id.substring(id.indexOf(".") + 1);
			}
			ids[i] = id;
		}
		return ids;
	}

}

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

package org.ktunaxa.referral.server.mvc;

import org.geomajas.plugin.reporting.mvc.ReportingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * Special controller for system reporting. Uses a direct db connection.
 *
 * @author Joachim Van der Auwera
 */
@Controller("/systemreport/**")
public class SystemReportController extends ReportingController {

	@Autowired
	@Qualifier("postgisDataSource")
	private DataSource databaseConnection;

	@RequestMapping(value = "/systemreport/{layerId}/{reportName}.{format}", method = RequestMethod.GET)
	public String reportFeatures(@PathVariable String reportName, @PathVariable String format,
			@PathVariable String layerId, @RequestParam(defaultValue = "EPSG:4326", required = false) String crs,
			@RequestParam(required = false) String filter, HttpServletRequest request, Model model) {
		String view = super.reportFeatures(reportName, format, layerId, crs, filter, request, model);
		model.addAttribute("datasource", databaseConnection);
		return view;
	}
}

/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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

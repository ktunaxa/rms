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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Jan De Moerloose
 *
 */
public class SystemService {

	private final Logger log = LoggerFactory.getLogger(SystemService.class);

	@Scheduled(fixedRate = 2000)
	public void showLog() {
		int mb = 1024 * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		log.info("##### Heap utilization statistics [MB] #####");

		// Print used memory
		log.info("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// Print free memory
		log.info("Free Memory:" + runtime.freeMemory() / mb);

		// Print total available memory
		log.info("Total Memory:" + runtime.totalMemory() / mb);

		// Print Maximum available memory
		log.info("Max Memory:" + runtime.maxMemory() / mb);
	}
}

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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.ComponentDefinition;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 
 * @author Jan De Moerloose
 *
 */
public class LogXmlContext extends XmlWebApplicationContext {

	@Override
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
		super.initBeanDefinitionReader(reader);
		reader.setEventListener(new LogReaderEventListener());
	}

	/**
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	class LogReaderEventListener extends EmptyReaderEventListener {

		private final Logger log = LoggerFactory.getLogger(LogReaderEventListener.class);

		@Override
		public void componentRegistered(ComponentDefinition componentDefinition) {

			log.info("Registered Component [" + componentDefinition.getName() + "]");
			for (BeanDefinition bd : componentDefinition.getBeanDefinitions()) {
				String name = bd.getBeanClassName();

				if (bd instanceof BeanComponentDefinition) {
					name = ((BeanComponentDefinition) bd).getBeanName();
				}
				log.info("Registered bean definition: [" + name + "]" + " from " + bd.getResourceDescription());
			}
		}

	}

}

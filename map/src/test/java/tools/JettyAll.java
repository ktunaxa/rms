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

package tools;

import java.net.MalformedURLException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyAll {

	public static void main(String[] args) throws Exception {
		Server server = startServer();
		server.join();
	}

	public static Server startServer() throws Exception {
		Server server = new Server();

		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8888);
		server.addConnector(connector);

		ContextHandlerCollection contexts = new ContextHandlerCollection();

		WebAppContext map = new MyWebAppContext();
		map.setContextPath("/map");
		map.setWar("src/main/webapp");


		WebAppContext probe = new MyWebAppContext();
		probe.setContextPath("/probe");
		probe.setWar("src/main/activiti/activiti-probe");
		probe.setResourceBase("src/main/activiti/activiti-probe");

		WebAppContext rest = new MyWebAppContext();
		rest.setContextPath("/activiti-rest");
		rest.setWar("src/main/activiti/activiti-rest");

		WebAppContext explorer = new MyWebAppContext();
		explorer.setContextPath("/bpm");
		explorer.setWar("src/main/activiti/activiti-explorer");

		contexts.addHandler(map);
//		contexts.addHandler(probe);
		contexts.addHandler(rest);
//		contexts.addHandler(explorer);
		
		server.setHandler(contexts);
		server.start();
		return server;
	}

	public static class MyWebAppContext extends WebAppContext {

		public MyWebAppContext() {
			setParentLoaderPriority(false);
		}

		@Override
		public Resource getResource(String uriInContext) throws MalformedURLException {
			if (uriInContext.startsWith("/classpath*:")) {
				return super.getResource("/" + uriInContext.substring("/classpath*:".length()));
			} else {
				return super.getResource(uriInContext);
			}
		}

	}

}

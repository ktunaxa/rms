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

import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.metaparadigm.jsonrpc.JSONSerializer;
import com.metaparadigm.jsonrpc.SerializerState;

/**
 * MVC view object that sends back javascript to call a preconfigured javascript function in response to a file upload.
 * This is necessary because the normal GWT RPC architecture can't be used.
 * 
 * @author Jan De Moerloose
 * 
 */
@Component(UploadView.NAME)
public class UploadView extends AbstractView {

	public static final String NAME = "UploadView";

	public static final String RESPONSE = "Upload.response";

	private JSONSerializer serializer = new JSONSerializer();

	/**
	 * Constructs a new view instance.
	 * 
	 * @throws Exception
	 *             thrown when JSON serializers could not be registered.
	 */
	public UploadView() throws Exception {
		serializer.registerDefaultSerializers();
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		response.getWriter().println("<html>");
		response.getWriter().println("<body>");
		response.getWriter().println("<script type=\"text/javascript\">");
		UploadResponse ur = (UploadResponse) model.get(RESPONSE);
		SerializerState state = new SerializerState();
		if (ur != null) {
			JSONObject result = (JSONObject) getSerializer().marshall(state, ur.getResultMap());
			if (ur.isSuccess()) {
				response.getWriter().println(
						MessageFormat.format(getSuccessScript(), result.getJSONObject("map").toString()));
			} else {
				response.getWriter().println(
						MessageFormat.format(getErrorScript(), result.getJSONObject("map").toString()));
			}
		} else {
			response.getWriter()
					.println(MessageFormat.format(getErrorScript(), getSerializer().toJSON("server error")));
		}
		response.getWriter().println("</script>");
	}

	protected JSONSerializer getSerializer() {
		return serializer;
	}

	private String getSuccessScript() {
		return "if (parent.uploadComplete) parent.uploadComplete({0});";
	}

	private String getErrorScript() {
		return "if (parent.uploadFailed) parent.uploadFailed({0});";
	}

}

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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.commons.io.IOUtils;
import org.ktunaxa.referral.server.service.CmisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * MVC view to download CMIS documents.
 * 
 * @author Jan De Moerloose
 * 
 */
@Component(InMemoryCmisDocumentView.NAME)
public class InMemoryCmisDocumentView extends AbstractView {

	public static final String NAME = "InMemoryCmisDocumentView";

	public static final String DOCUMENT_ID = "id";

	public static final String TYPE = "type";

	public static final String OPEN = "open";

	public static final String SAVE = "save";

	@Autowired
	private CmisService cmisService;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document doc = cmisService.getById((String) model.get(DOCUMENT_ID));
		response.setContentType(doc.getContentStreamMimeType());
		response.setContentLength((int) doc.getContentStreamLength());
		if (SAVE.equals((String) model.get(TYPE))) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + doc.getName() + "\"");
		}
		IOUtils.copy(doc.getContentStream().getStream(), response.getOutputStream());
	}
}

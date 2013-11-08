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
		response.setContentLength((int)doc.getContentStreamLength());
		if (SAVE.equals((String) model.get(TYPE))) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + doc.getName() + "\"");
		}
		IOUtils.copy(doc.getContentStream().getStream(), response.getOutputStream());
	}
}

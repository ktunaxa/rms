package org.ktunaxa.referral.server.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class InMemoryCmisController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(InMemoryCmisDocumentView.NAME);
		mv.addObject(InMemoryCmisDocumentView.DOCUMENT_ID, request.getParameter(InMemoryCmisDocumentView.DOCUMENT_ID));
		mv.addObject(InMemoryCmisDocumentView.TYPE, request.getParameter(InMemoryCmisDocumentView.TYPE));
		return mv;
	}

}

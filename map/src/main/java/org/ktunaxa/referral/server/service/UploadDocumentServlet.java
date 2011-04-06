/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.server.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet for uploading compressed shape files and sending the union geometry back as WKT.
 * 
 * @author Pieter De Graef
 */
public class UploadDocumentServlet extends HttpServlet {

	private static final long serialVersionUID = 100L;

	private final Logger log = LoggerFactory.getLogger(UploadDocumentServlet.class);

	private WebApplicationContext context;

	public void init() throws ServletException {
		super.init();
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(req)) {
			byte[] fileContent = null;
			String fileName = null;
			String mimeType = null;

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items;
			String formId = req.getParameter(KtunaxaConstant.FORM_ID);
			// remove some characters to prevent XSS
			formId = formId.replace("<", "");
			formId = formId.replace(">", "");
			formId = formId.replace("=", "");
			formId = formId.replace("&", "");
			try {
				PrintWriter out = resp.getWriter();

				items = upload.parseRequest(req);
				for (FileItem item : items) {
					if (!item.isFormField()) {
						fileContent = item.get();
						fileName = item.getName();
						mimeType = item.getContentType();
						break;
					}
				}
				CmisService cmisService = context.getBean(CmisService.class);
				Document document;
				try {
					document = cmisService.create(fileName, mimeType, new ByteArrayInputStream(fileContent));
				} catch (IOException ioe) {
					String msg = "Could not put document " + fileName + " in CMS.";
					log.error(msg, ioe);
					out.println(msg);
					return;
				}

				resp.setContentType("text/html");
				out.println("<html>");
				out.println("<body>");
				out.println("<script type=\"text/javascript\">");
				// TODO also send the CMIS key back to the client...
				out.println("if (parent.uploadComplete) parent.uploadComplete('" + formId + "','" + fileName + "');");
				out.println("</script>");
			} catch (FileUploadException e) {
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				resp.setStatus(400);
				out.println("<html>");
				out.println("<body>");
				out.println("<script type=\"text/javascript\">");
				// TODO also send the CMIS key back to the client...
				out.println("if (parent.uploadComplete) parent.uploadComplete('" + formId + "','error');");
				out.println("</script>");
			}
		}
	}
}
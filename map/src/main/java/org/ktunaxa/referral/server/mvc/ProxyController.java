package org.ktunaxa.referral.server.mvc;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/proxy/**")
public class ProxyController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/proxy")
	public final void proxyAjaxCall(@RequestParam(required = true, value = "url") String url,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		// URL needs to be url decoded
		url = URLDecoder.decode(url, "utf-8");

		HttpClient client = new HttpClient();
		try {

			HttpMethod method = null;

			// Split this according to the type of request
			if (request.getMethod().equals("GET")) {

				method = new GetMethod(url);
				NameValuePair[] pairs = new NameValuePair[request.getParameterMap().size()];
				int i =0;
				for (Object name : request.getParameterMap().keySet()) {
					pairs[i++] = new NameValuePair((String)name, request.getParameter((String)name));
				}
				method.setQueryString(pairs);

			} else if (request.getMethod().equals("POST")) {

				method = new PostMethod(url);

				// Set any eventual parameters that came with our original
				// request (POST params, for instance)
				Enumeration paramNames = request.getParameterNames();
				while (paramNames.hasMoreElements()) {

					String paramName = (String) paramNames.nextElement();
					((PostMethod) method).setParameter(paramName, request.getParameter(paramName));
				}

			} else {

				throw new NotImplementedException("This proxy only supports GET and POST methods.");
			}

			// Execute the method
			client.executeMethod(method);

			// Set the content type, as it comes from the server
			Header[] headers = method.getResponseHeaders();
			for (Header header : headers) {

				if ("Content-Type".equalsIgnoreCase(header.getName())) {

					response.setContentType(header.getValue());
				}
			}

			// Write the body, flush and close
			response.getOutputStream().write(method.getResponseBody());

		} catch (HttpException e) {

			// log.error("Oops, something went wrong in the HTTP proxy", null, e);
			response.getOutputStream().write(e.toString().getBytes("UTF-8"));
			throw e;

		} catch (IOException e) {

			e.printStackTrace();
			response.getOutputStream().write(e.toString().getBytes("UTF-8"));
			throw e;
		}
	}
}
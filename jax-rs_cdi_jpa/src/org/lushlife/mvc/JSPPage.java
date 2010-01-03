package org.lushlife.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

public class JSPPage implements StreamingOutput {
	static public final String MESSAGES = "messages";
	private String page;
	private Map<String, Object> requestContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	@Inject
	private Messages messages;

	protected JSPPage init(String page, Map<String, Object> requestContext) {
		this.page = page;
		this.requestContext = requestContext;
		return this;
	}

	public JSPPage bind(String name, Object obj) {
		requestContext.put(name, obj);
		return this;
	}

	public void write(OutputStream output) throws IOException,
			WebApplicationException {
		// copy request context to request attribute
		for (Entry<String, Object> entry : requestContext.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		// copy messages to request attribute.
		request.setAttribute("messages", messages);

		try {
			request.getRequestDispatcher(page).forward(request, response);
		} catch (ServletException e) {
			throw new WebApplicationException(e);
		}
	}

	public <T> JSPPage addValidationMessage(
			Set<ConstraintViolation<T>> constraints) {
		for (ConstraintViolation<T> u : constraints) {
			addMessage(u.getMessage());
		}
		return this;
	}

	public JSPPage addMessage(String message) {
		messages.addMessage(message);
		return this;
	}

}

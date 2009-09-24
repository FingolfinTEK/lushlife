package org.lushlife.kamikaze.servlet;

import java.io.IOException;

import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lushlife.kamikaze.Kamikaze;
import org.lushlife.kamikaze.context.Contexts;
import org.lushlife.kamikaze.context.ServletContexts;
import org.lushlife.kamikaze.event.RequestDestroyedLiteral;
import org.lushlife.kamikaze.event.RequestInitializedLiteral;

public class KamikazeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		ServletContexts.setResponse(arg1);
		BeanManager manager = Contexts.get(BeanManager.class);
		HttpServletEvent event = new HttpServletEvent(arg0, arg1);
		manager.fireEvent(event, new RequestInitializedLiteral());
		try {
			Kamikaze.getInjector().getInstance(KamikazeServletDispatcher.class)
					.service(arg0, arg1);
		} finally {
			manager.fireEvent(event, new RequestDestroyedLiteral());
		}
	}
}

package lushfile.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lushfile.core.LushLife;
import lushfile.core.controller.RequestAnalyzer;

public class LushServlet extends HttpServlet {
	private static final long serialVersionUID = -8708166997089268875L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LushLife.getInjector().getInstance(RequestAnalyzer.class).analyze();
	}

}

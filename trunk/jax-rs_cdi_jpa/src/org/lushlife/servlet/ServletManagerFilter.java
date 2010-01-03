package org.lushlife.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletManagerFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		try {
			ServletManager.REQUEST.set((HttpServletRequest) arg0);
			ServletManager.RESPONSE.set((HttpServletResponse) arg1);
			arg2.doFilter(arg0, arg1);
		} finally {
			ServletManager.REQUEST.set(null);
			ServletManager.RESPONSE.set(null);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}

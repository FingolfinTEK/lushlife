package org.lushlife.kamikaze.mvc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface KamikazeServletDispatcher
{
   public void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException;

}

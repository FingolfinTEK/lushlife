<%@page import="glassbottle2.el.NamingResolverMap"%>
<%@page import="org.jboss.webbeans.el.WebBeansELResolver"%>
<%@page import="org.jboss.webbeans.CurrentManager"%>
<%@page import="javax.enterprise.inject.spi.BeanManager"%>
<%@page import="javax.enterprise.inject.Current"%>
<%@page import="glassbottle2.extension.resources.ResourceManager"%>
<%@page import="application.lushup.model.Customer"%>
<%@page import="glassbottle2.GlassBottle"%>
<%@page import="glassbottle2.Injector"%>
<%
	Injector i = GlassBottle.getInjector();
%>
<%=i.getInstance(Customer.class).getId()%>
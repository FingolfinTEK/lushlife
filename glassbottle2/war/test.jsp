<%@page import="org.jboss.webbeans.CurrentManager"%>
<%@page import="javax.enterprise.inject.spi.BeanManager"%>
<%@page import="javax.enterprise.inject.Current"%>
<%@page import="glassbottle2.extension.resources.ResourceManager"%>
<%@page import="application.lushup.model.Customer"%>
<%@page import="glassbottle2.GlassBottle"%>
<%@page import="glassbottle2.Injector"%>
<%!
	@Current
	BeanManager beanManager;
%>

<%
	BeanManager manager = CurrentManager.rootManager();
	Injector i = GlassBottle.getInjector();
	ResourceManager r = i.getInstance(ResourceManager.class);
%>

<%=i.getInstance(Customer.class).getId()%>
<%=r.css("/yahoo/reset.css")%>

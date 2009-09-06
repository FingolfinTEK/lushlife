<%@page import="glassbottle2.Injector"%>
<%@page import="glassbottle2.GlassBottle"%>
<%@page import="glassbottle2.extension.resources.ResourceManager"%>
<%@page import="glassbottle2.extension.yahoo.YahooResource"%>
<%@page import="glassbottle2.extension.jquery.JQueryResource"%>
<%@page import="application.lushup.model.Customer"%>
<%@page import="glassbottle2.extension.jspquery.JSPQueryManager"%>
<%@page import="glassbottle2.extension.jspquery.tag.A"%>
<%@page import="application.lushup.resources.CustomerResource"%>
<html>
<%
	Injector injector = GlassBottle.getInjector();
	ResourceManager r = injector.getInstance(ResourceManager.class);
	JSPQueryManager j = injector.getInstance(JSPQueryManager.class);
%>

<head>

<%=r.css(YahooResource.class, "reset.css")%>
<%=r.js(JQueryResource.class, "jquery-1.3.2.js")//
					.encoding("utf-8")%>

<script type="text/javascript" src="/test.js"> </script>

</head>

<body>
<div>

<p>sample</p>

<p><%=j.$(Customer.class).getId()%></p>
<p><%=j.$(Customer.class).getCity()%></p>
<p><%=j.$(Customer.class).getLastName()%></p>


<%=j.$(A.class)
	.href(r.url(CustomerResource.class)
			.path(j.$(Customer.class).getId()))
	.id("tag_a")%>
	Sample Link
<%= j.end() %>

<table border="1">
	<tr>
		<td>TD #0</td>
		<td></td>
	</tr>
	<tr>
		<td>TD #2</td>
		<td> Good Point </td>
	</tr>
	<tr>
		<td></td>
		<td>TD#5</td>
	</tr>
</table>

</div>

</body>

</html>
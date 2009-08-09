<%@page import="glassbottle2.Injector"%>
<%@page import="glassbottle2.GlassBottle"%>
<%@page import="glassbottle2.extension.resources.ResourceManager"%>
<%@page import="glassbottle2.extension.yahoo.YahooResource"%>
<%@page import="glassbottle2.extension.jquery.JQueryResource"%>
<%@page import="application.lushup.model.Customer"%>
<%@page import="glassbottle2.extension.jspquery.JSPQueryManager"%><html>
<%
	Injector i = GlassBottle.getInjector();
	ResourceManager r = i.getInstance(ResourceManager.class);
	JSPQueryManager j = i.getInstance(JSPQueryManager.class);
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

<p><%=i.$(Customer.class).getId()%></p>
<p><%=i.$(Customer.class).getCity()%></p>
<p><%=i.$(Customer.class).getLastName()%></p>

<table border="1">
	<tr>
		<td>TD #0</td>
		<td></td>
	</tr>
	<tr>
		<td>TD #2</td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td>TD#5</td>
	</tr>
</table>

</div>

</body>

</html>
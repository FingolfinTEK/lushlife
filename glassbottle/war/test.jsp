<%@page import="static org.lushlife.kamikaze.Kamikaze.$"%>

<%@page import="org.lushlife.kamikaze.Injector"%>
<%@page
	import="org.lushlife.kamikaze.extension.jspquery.JSPQueryManager"%>
<%@page import="org.lushlife.kamikaze.extension.yahoo.YahooResource"%>
<%@page import="org.lushlife.kamikaze.extension.jquery.JQueryResource"%>
<%@page import="application.lushup.model.Customer"%>
<%@page import="org.lushlife.kamikaze.resources.ResourceManager"%>
<%@page import="org.lushlife.kamikaze.extension.jspquery.tag.A"%>
<%@page import="application.lushup.resources.CustomerResource"%><head>

<%
	ResourceManager r = $(ResourceManager.class);
%>


<%=r.css(YahooResource.class, "reset.css")%>
<%=r.js(JQueryResource.class, "jquery-1.3.2.js")%>

<script type="text/javascript" src="/test.js"> </script>

</head>

<body>
<div>

<p>sample</p>

<p><%=$(Customer.class).getId()%></p>
<p>first name <%=$(Customer.class).getFirstName()%></p>
<p><%=$(Customer.class).getLastName()%></p>


<a
	href="<%=r.url(CustomerResource.class)
	.path($(Customer.class).getId())%>">

Sample Link </a>

<table border="1">
	<tr>
		<td>TD #0</td>
		<td></td>
	</tr>
	<tr>
		<td>TD #2</td>
		<td>Good Point</td>
	</tr>
	<tr>
		<td></td>
		<td>TD#5</td>
	</tr>
</table>

</div>

</body>
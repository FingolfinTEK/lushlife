<%@page isELIgnored="false"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.net.URL"%>

<%@page import="example.model.User"%><html>
<head>
</head>
<body>
<a href="/rest/user/new">create new user</a>

<table border="1">
	<tr>
		<th>#</th>
		<th>id</th>
		<th>name</th>
	</tr>

	<%
		int i = 0;
		for (User user : (List<User>) request.getAttribute("users")) {
	%>
	<tr>
		<td><%=++i%></td>
		<td><%=user.getId()%></td>
		<td><%=user.getName()%></td>
	</tr>
	<%
		}
	%>

</table>


</body>
</html>
<%@page isELIgnored="false"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.net.URL"%>


<%@page import="example.model.User"%><html>
<head>
</head>
<body>

<form method="POST"><input type="text" name="name"
	value="${user.name}" /><br />
<input type="submit" value="create" /> 
${hidden.parameter}</form>

</body>
</html>
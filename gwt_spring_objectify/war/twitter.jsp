<%@page import="ex.gtwitter.share.TwitterLexer"%>
<%@page import="java.util.List"%>
<%@page import="twitter4j.Status"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="Shift_JIS">
<title>Insert title here</title>
</head>
<body>
	<ul>
	<%
		List<Status> status = (List<Status>) request.getAttribute("status");
		for(Status s:status){
	%>
		<li><pre> <%=TwitterLexer.twitterLink(s.getText()) %> 
		</pre></li>
	<%}%>
	</ul>
</body>
</html>
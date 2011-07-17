<%@page import="ex.gtwitter.share.TwitterLexer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!doctype>
<html>

<head>
<script type="text/javascript" language="javascript"
	src="helloworld/helloworld.nocache.js"></script>
<meta http-equiv="expires" content="0">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Hello World Sample</title>
</head>

<body>

	<ol>
		<li class="twitter-status">ssss #sample @jiro http://google.com</li>
		<li><%=TwitterLexer
					.twitterLink("ssss #sample @jiro http://google.com")%></li>
	</ol>

</body>
</html>

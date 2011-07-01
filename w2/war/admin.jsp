<%@ page contentType="text/html; charset=UTF-8"%>
<!doctype>
<html>

<head>
<meta http-equiv="expires" content="0">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Adminstrator Menu</title>

<script type="text/javascript" language="javascript"
	src="w2admin/w2admin.nocache.js"></script>

<link type="text/css" rel="stylesheet" href="w2.css">

</head>

<body>

	<%@include file="template/header.jsp"%>
	<nav>
		<h2>Golfer</h2>
		<ol>
			<li><a id="userAdd" href="#EditGolferPlace:">New</a>
			</li>
			<li><a id="userList" href="#ListGolferPlace:0:1000">Golfer
					List</a>
			</li>
		</ol>
	</nav>
	<article id="contents"></article>

	<%@include file="template/footer.jsp"%>

</body>
</html>

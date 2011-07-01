<%@ page contentType="text/html; charset=UTF-8"%>
<!doctype>
<html>

<head>
<script type="text/javascript" language="javascript"
	src="w2/w2.nocache.js"></script>
<link type="text/css" rel="stylesheet" href="w2.css">
<meta http-equiv="expires" content="0">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>WakuWaku Commissioner</title>

</head>


<body>
	<%@include file="template/header.jsp"%>
	<nav>
		<h2>Round</h2>
		<ol>
			<li><a href="#EditRoundPlace:">New</a></li>
			<li><a href="#ListRoundPlace:">Events</a></li>
		</ol>
		<h2>Course</h2>
		<ol>
			<li><a href="#EditCoursePlace:">New</a></li>
			<li><a href="#ListCoursePlace:0:1000">Course List</a></li>
		</ol>
	</nav>
	<article id="contents"></article>


	<%@include file="template/footer.jsp"%>

</body>
</html>

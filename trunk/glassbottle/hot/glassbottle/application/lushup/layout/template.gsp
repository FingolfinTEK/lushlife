<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> <% l.param('title')%> </title>

<% l.css('Yahoo','reset.css') %>
<% l.css('layout.css') %>
<% l.css('header.css') %>
<% l.css('footer.css') %>
<% l.css('main.css') %>
<% l.css('navi.css') %>

<% l.injectFrom("header") %>

</head>
<body class="composite">
<div id="header">
	<div class="xleft">
		 <% l.linkTo([value:'Home']) %>
		 <% l.linkTo([value:'Add Shop',view:"test"]) %>
	</div>
	<div class="xright">
		<% if(g.logined()){ %>
			${g.user.email} <a href="${g.userService.createLogoutURL(request.requestURI)}">Logout</a>
		<% } else {%>
			<a href="${g.userService.createLoginURL(request.requestURI)}">Login</a>
		<% } %>
	 </div>
</div>
<div id="main">

<div id="navi">
	<div id="navcolumn"><% l.injectFrom('navi') %>
</div>
</div>

<div id="contants">
	<% l.injectFrom("main_contents") %> 
</div>
</div>
<div id="footer">
	<div class="xright">
			powered by <a href="http://code.google.com/p/lushlife/"> Lushlife </a>
	</div>
</div>
</body>
</html>
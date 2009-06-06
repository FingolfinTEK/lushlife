<html>
	<head>
		<title> <%l.param("title")%> </title>
		<% l.loadScript('jquery')%>
		<% l.css('lushup.css') %>
	</head>
	
	<body>
	<div id="wrap">
		<div id="header">
			<div id="main_menu">
				main menu
			</div>
			<div id="sub_menu">
				sub menu
			</div>
		</div>
		
		<div id="contents">
		
			<div id="navigator">
				navigator
			</div>
	
			<div id="main_contents">
				<% l.injectFrom('main_contents') %>
			</div>
		</div>
		
		<div id="fotter">
			Lushup Application
		</div>
	</div>
	<body>
</html>
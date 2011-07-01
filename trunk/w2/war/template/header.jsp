<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%
	String requestUrl = request.getHeader("requestUrl");
	if (requestUrl == null) {
		requestUrl = request.getRequestURI();
	}
	UserService userservice = UserServiceFactory.getUserService();
	String email = userservice.getCurrentUser().getEmail();
	String loginUrl = userservice.createLoginURL(requestUrl);
	String logoutUrl = userservice.createLogoutURL(requestUrl);
%>
<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
	style="position: absolute; width: 0; height: 0; border: 0"></iframe>

<noscript>
	<div
		style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
		Your web browser must have JavaScript enabled in order for this
		application to display correctly.</div>
</noscript>

<header>
<ol class="menu">
	<li><a href="index.jsp">Top</a>
	</li>
	<li><a href="admin.jsp">Admin</a></li>
</ol>

<ol class="user_menu">
	<%
		if (userservice.isUserLoggedIn()) {
	%>
	<li><%=email%></li>
	<li><a href="<%=logoutUrl%>">logout</a> <%
 	} else {
 %>
	
	<li><a href="<%=loginUrl%>">login</a> <%
 	}
 %>
	
</ol>
</header>
<aside>
<h1>Waku Waku</h1>

<div id="message" class="message"></div>

</aside>
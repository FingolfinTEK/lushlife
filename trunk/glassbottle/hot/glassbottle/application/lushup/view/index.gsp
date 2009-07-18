<% l.template(
	[title:'日本語']){
%>
	<% l.injectTo('main_contents'){ %>
		<table>
			<tr>
				<th>id</th>
				<th>name</th>
			</tr>
		</table>
		<% l.form([id:'xxx']){ %>
			<% l.submit("lushup",[value:"invoke"]) %>
		<% } %>
		<% l.linkTo(action:'lushup'){ %>
			link lushup
		<% } %>
	<% } %>
<% } %>

<% l.template(
	[title:'Lushup']){
%>
	
	<% l.injectTo('main_contents'){ %>
	
		<table>
			<tr>
				<th>id</th>
				<th>name</th>
			</tr>
			<% for(shop in shops.shops){%>
			<tr>
				<td>${shop.id}</td>
				<td>${shop.name}</td>
			</tr>
			<% } %>
		</table>

	<% } %>
	
<% } %>

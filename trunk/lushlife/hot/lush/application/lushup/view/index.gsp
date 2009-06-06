<% l.template('maven_template.html',
	[title:'Lushup ']){%>
	<% l.injectTo('main_contents'){ %>
			<% l.linkTo('test.invoke',[params:[value:'xxx']]) %>
			<br />
			<% l.linkTo('wiki',[params:[value:'xxx']]){ %>
				<% l.image('test.bmp') %>
			<% } %>
	<% } %>
<% } %>
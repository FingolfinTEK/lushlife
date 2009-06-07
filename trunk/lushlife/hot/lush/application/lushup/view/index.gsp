<% l.template('maven_template.html',
	[title:'Lushup ']){%>
	<% l.injectTo('main_contents'){ %>
			<% l.linkTo('test.invoke',[params:[value:'xxx']]) %>
			<br />
			${testDto.value}
			<% l.linkTo('wiki',[params:[value:'xxx']]){ %>
				<% l.image('test.bmp') %>
			<% } %>
			
			<% l.form(){ %>
				<% l.submit("lushup") %>
			<% } %>
	<% } %>
<% } %>
<% l.template('Lush','template.gsp',
	[title:'タイトル']){%>
	<% l.injectTo('head'){%>
			<% l.css('test.css') %>
			<% l.javascript('test.js') %>
			<% l.loadScript('jquery') %>
			head
	<% } %>
	
	<% l.injectTo('body'){ %>
	${testDto.value}
			<% l.linkTo('test.invoke',[params:[value:'xxx']]) %>
			<% l.linkTo('wiki',[params:[value:'xxx']]){ %>
				<% l.image('test.bmp') %>
			<% } %>
	<% } %>
<% } %>
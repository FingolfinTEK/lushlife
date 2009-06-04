<%
 html.html{
 	html.head{
 	  html.title 'テストを書こう'
 	}
	html.body {
		html.p '文章2'
%> 
    <br />
    	色々かける
    	
    	<%l.form{ %>
    		<% l.input('xxxx') %>
    		
    		<input type="submit" />
    		 
    		<% l.submit('test.invoke',[value:'invoke']) %>
    		
    	<%} %>
<%
} 
}
%>

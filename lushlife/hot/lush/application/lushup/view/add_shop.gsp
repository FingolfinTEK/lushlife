<% l.template([title:"Add Shop"]){ %>
	<% l.injectTo('header'){ %>
		<% l.javascript('JQuery','jquery-1.3.2.js') %>
		
		<script language="javascript" type="text/javascript">
		    \$('form').submit(function(){  
			        \$(':submit', this).click(function() {  
			            return false;  
			        });  
			   });  
		</script>
		
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAVZ6tvcCf5BuWfwI63UrnhBQfe0EbulPtHDEuOLsZOnRWotS-yxTKYZdYMet69vv96VKA26mw9ALQXw"
            type="text/javascript">
    </script>
    <script type="text/javascript">
    	\$(function initialize() {
      		if (GBrowserIsCompatible()) {
        	var map = new GMap2(document.getElementById("map_canvas"));
       		 map.setCenter(new GLatLng(37.4419, -122.1419), 13);
      		}
    	})
    </script>
		
		
	<% } %>

	<% l.injectTo('navi'){ %>
	 <div id="map_canvas" style="width: 500px; height: 300px"></div>
	<% } %>

	<% l.injectTo('main_contents'){ %>
		<% l.form(){ %>
		<table>
			<tr>
				<td>name</td>
				<td><% l.input('shop.name',[value:shop.name]) %></td>
			</tr>
			<tr>
				<td>lat</td>
				<td><% l.input('shop.lat',[value:shop.lat]) %></td>
			</tr>
			<tr>
				<td>lng</td>
				<td><% l.input('shop.lng',[value:shop.lng]) %></td>
			</tr>
			<tr>
				<td>zoom</td>
				<td><% l.input('shop.zoom',[value:shop.zoom]) %></td>
			</tr>
		</table>
		<% l.submit("addShop.persist",[value:'追加']) %>
		<% } %>		
	<% }%>
	
<% } %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Vene listaus</title>
</head>
<body onkeydown="enterWasPressed(event)" class="bg">
<div class="container">
	<div class="header">
		<h1>Watercrafts</h1>
	</div>
	<div class="options">
		<input type="text" class="searchBar" id="searchWord" >
		<input type="button" class="button button2blue" value="Search" onclick="searchFor()">
		<input type="button" class="button button4green" value="Add new" onclick="goTo()">
	</div>
	<div class="tableDiv">		
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Brandmodel</th>
					<th>Length</th>
					<th>Width</th>
					<th>Price</th>
					<th>Settings</th>
				</tr>
			</thead>
			<tbody id="listing-tbody">						
			</tbody>			
		</table>
		<span id="erase"></span>
	</div>	
</div>
<script>
	document.getElementById("searchWord").focus();
	function goTo() {
		window.location.href = "lisaavene.jsp";
	}
	function enterWasPressed(event){
		if(event.keyCode==13){
			searchFor();
		}	
	}
	searchFor();
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Vene muutos</title>
</head>
<body onkeydown="enterWasPressed(event)" class="bg">
<div class="container">
	<div class="header">
		<h1>Edit watercraft</h1>
	</div>
	<div class="form">
		<form id="formInfo">
			<div class="formgroup">
				<label for="nimi">Name</label>
				<input type="text" class="formField" name="nimi" id="nimi">
			</div>
			<div class="formgroup">
				<label for="merkkimalli">Brandmodel</label>
				<input type="text" class="formField" name="merkkimalli" id="merkkimalli">
			</div>
			<div class="formgroup">
				<label for="pituus">Length</label>
				<input type="text" class="formField" name="pituus" id="pituus">
			</div>
			<div class="formgroup">
				<label for="leveys">Width</label>
				<input type="text" class="formField" name="leveys" id="leveys">
			</div>
			<div class="formgroup">
				<label for="hinta">Price</label>
				<input type="text" class="formField" name="hinta" id="hinta">
			</div>
			<input type="hidden" name="tunnus" id="tunnus" readonly>
			<div class="options">
				<input type="button" class="button button1red" value="Cancel" onclick="goTo()">
				<input type="button" class="button button4green" name="Save" id="save" value="Save" onclick="saveEdited()">
			</div>
			<span id="infofield"></span>
		</form>	
	</div>
</div>
<script>
	findSpecific();
	document.getElementById("nimi").focus();
	function goTo() {
		window.location.href = "listaaveneet.jsp";
	}
	function enterWasPressed(event){
		if(event.keyCode==13){
			saveEdited();
		}	
	}
</script>
</body>
</html>
/*
*/
// Get parameter from URL
function requestURLParam(sParam){
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split("&");
    for (let i=0; i<sURLVariables.length; i++){
        var sParameterName = sURLVariables[i].split("=");
        if(sParameterName[0] == sParam){
            return sParameterName[1];
        }
    }
}
// Convert form entries to JSON string
function formDataToJSON(data){
	var returnStr="{";
	for(let i=0; i<data.length; i++){		
		returnStr+="\"" +data[i].name + "\":\"" + data[i].value + "\",";
	}	
	returnStr = returnStr.substring(0, returnStr.length - 1);
	returnStr+="}";
	return returnStr;
}
// Clean unwanted characters (><;') from string
function cleanThisEntry(entry){
	let clean = entry.replace(/[><;'"]/g, "");
	return clean;
}
// Validate values in fields
function checkFormData(data){
	var returnString="";
	if(!data[0].value || !data[1].value || !data[2].value || !data[3].value|| !data[4].value){
		returnString = "Fill all fields";
	}else{
		for(let i=0; i<data.length; i++){
			if(data[i].name=="nimi" && data[i].value.length<2){
				returnString = "Invalid name";
				break;
			}else if(data[i].name=="merkkimalli" && data[i].value.length<2){			
				returnString = "Invalid brandmodel";
				break;
			}else if(data[i].name=="pituus" && data[i].value*1!=data[i].value){
				returnString = "Invalid length";
				break;
			}else if(data[i].name=="leveys" && data[i].value*1!=data[i].value){
				returnString = "Invalid width";
				break;
			}else if(data[i].name=="hinta" && data[i].value*1!=data[i].value){
				returnString = "Invalid price";
				break;
			}	
		}			
	}
	return returnString;
}
// Search 	 /veneet/listaus/{searchWord} GET  (spaces in response replaced with &nbsp)---------------
function searchFor(){
	fetch("veneet/listaus/"+ document.getElementById("searchWord").value,{method:'GET'})
        .then(response => response.json())
        .then(responseJson => {
			var htmlString="";
			for(let i=0; i<responseJson.boats.length; i++){
				htmlString+="<tr id='erase"+responseJson.boats[i].tunnus+"'>";
	        	htmlString+="<td>"+responseJson.boats[i].tunnus+"</td>";
	        	htmlString+="<td>"+responseJson.boats[i].nimi+"</td>";
	        	htmlString+="<td>"+responseJson.boats[i].merkkimalli+"</td>";
	        	htmlString+="<td>"+responseJson.boats[i].pituus+"</td>";
	        	htmlString+="<td>"+responseJson.boats[i].leveys+"</td>";
				htmlString+="<td>"+responseJson.boats[i].hinta+"</td>";
	        	htmlString+="<td><a class='button button3grey' href='muutavene.jsp?id="+responseJson.boats[i].tunnus+"'>Edit</a>";
	        	htmlString+="<span class='button button1red' onclick=deleteThis("+responseJson.boats[i].tunnus+",'"+responseJson.boats[i].nimi+"')>Delete</span></td>";
				htmlString+="</tr>";
			}
			document.getElementById("listing-tbody").innerHTML = htmlString;
		})
        .catch(error => {
          console.log(error);
     });
}
// Search with ID for editing	/veneet/edit/{id} GET  -------------------------------------------
function findSpecific(){
	var id = requestURLParam("id");
	fetch("veneet/edit/"+id,{method:'GET'})
	    .then(response => response.json())
	    .then(responseJson => {
				document.getElementById("nimi").value = responseJson.boats[0].nimi;
				document.getElementById("merkkimalli").value = responseJson.boats[0].merkkimalli;
				document.getElementById("pituus").value = responseJson.boats[0].pituus;
				document.getElementById("leveys").value = responseJson.boats[0].leveys;
				document.getElementById("hinta").value = responseJson.boats[0].hinta;
				document.getElementById("tunnus").value = responseJson.boats[0].tunnus;
		})
	    .catch(error => {
	      console.log(error);
	 });
}
// Save edited info		/veneet PUT  --------------------------------------------------------
function saveEdited(){
	var areValuesOk="";
	areValuesOk = checkFormData(document.getElementById("formInfo"));
	if(areValuesOk!=""){
		document.getElementById("infofield").innerHTML=areValuesOk;
		setTimeout(function(){ document.getElementById("infofield").innerHTML=""; }, 3000);
		return;
	}
	document.getElementById("nimi").value=cleanThisEntry(document.getElementById("nimi").value);	
	document.getElementById("merkkimalli").value=cleanThisEntry(document.getElementById("merkkimalli").value);			
	var formJsonString = formDataToJSON(document.getElementById("formInfo"));
	fetch("veneet",{method:'PUT', body:formJsonString})
        .then(response => response.json())
        .then(responseJson => {
			if(responseJson.response==0){
				document.getElementById("infofield").innerHTML="Something went wrong";
			}			
			window.location.href = "listaaveneet.jsp";
			window.alert("Updated");
		})
        .catch(error => {
          console.log(error);
        });
}
// Add new watercraft   /veneet POST	--------------------------------------------------------
function addWatercraft(){
	var areValuesOk="";
	areValuesOk = checkFormData(document.getElementById("formInfo"));
	if(areValuesOk!=""){
		document.getElementById("infofield").innerHTML=areValuesOk;
		setTimeout(function(){ document.getElementById("infofield").innerHTML=""; }, 3000);
		return;
	}
	document.getElementById("nimi").value=cleanThisEntry(document.getElementById("nimi").value);
	document.getElementById("merkkimalli").value=cleanThisEntry(document.getElementById("merkkimalli").value);
	var formJsonString = formDataToJSON(document.getElementById("formInfo"));
	fetch("veneet",{method:'POST', body:formJsonString})
        .then(response => response.json())
        .then(responseJson => {
			if(responseJson.response==1){
				document.getElementById("infofield").innerHTML="New watercraft added";
			}else if(responseJson.response==0){
				document.getElementById("infofield").innerHTML="Something went wrong";
			}
			setTimeout(function(){ document.getElementById("infofield").innerHTML=""; }, 3000);
		})
        .catch(error => {
          console.log(error);
        });
	document.getElementById("formInfo").reset();
}
// Delete watercract   /veneet/{id} DELETE	--------------------------------------------------------
function deleteThis(id,name){
	let space = name.replace(/&nbsp/g, " ");
	if(confirm("Confirm to delete following:\nID: "+id+"\nName: "+space)){
		fetch("veneet/"+id,{method:'DELETE'})
        .then(response => response.json())
        .then(responseJson => {
			if(responseJson.response==1){
				document.getElementById("erase"+id).style.backgroundColor="#9e0608";
			}else if(responseJson.response==0){
				document.getElementById("erase").innerHTML="Something went wrong";
			}
			setTimeout(function(){ searchFor(); }, 1000);
			;
		})
        .catch(error => {
          console.log(error);
        });
	}
}
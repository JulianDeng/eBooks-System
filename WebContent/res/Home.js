function validateRate(element){
 	var ok = false;
 	var checkboxs = element;
 	for(var i = 0, l = checkboxs.length; i < l; i++){
 		if(checkboxs[i].checked){
 			ok = true;
 			break;			
 		}
 	}
 	if(ok == false){
 		document.getElementById("msgRate").innerHTML = 'Please select at least one score!';
 	}
 	return ok;
}


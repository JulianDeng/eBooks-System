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

function addBookToCartAjax(address, bookID){
	var request = new XMLHttpRequest();
	var data="?boodAddToCart="+bookID;

	/* add your code here to grab all parameters from form*/
	request.open("GET", (address + data), true);
	request.onreadystatechange = function() {
		handlerAddBook(request);
	};
	request.send(null);
} 

function handlerAddBook(request){
	if ((request.readyState == 4) && (request.status == 200)){

	}
} 

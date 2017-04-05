function validate(){
	var ok;
	var ccn = document.getElementById("creditcard").value;
	
	ok = (ccn.length == 12 && /^\d+$/.test(ccn)) ? true : false;
	if(ok == false){
		alert("Credit card number must be 12 digits!!");
	}
	return ok;	
}
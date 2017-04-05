function doCartAjax(bid, quantity, address){

	 var request = new XMLHttpRequest();
	 var data=``;

	 /* add your code here to grab all parameters from form*/
	 data += bid;
	 data += "=";
	 data += quantity;
	 
	 /*=========================================*/
	 request.open("POST", address, true);
	 request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 request.onreadystatechange = function() {
	 handlerCart(request);
	 };
	 request.send(data);
} 

function handlerCart(request){
	 if ((request.readyState == 4) && (request.status == 200)){
	 var target = document.getElementById("totalPriceAjax");
	 target.innerHTML = request.responseText;
	 }
} 


function validate(bid, address){
	// ensure no negative quantity exists.
	var id = "book_" + bid;
	var quantity = document.getElementById(id).value;
	if(!(quantity.match(/[1-9][0-9]*/) && parseInt(quantity) > 0 && parseInt(quantity) < 100)){
		alert("quantity must be positive integer and less than 100!!!");
	}else{
		doCartAjax(bid, quantity, address); 
	}	

}
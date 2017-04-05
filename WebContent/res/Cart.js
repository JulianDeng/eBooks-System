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
	 var fields = request.responseText.split('~');	 
	 
	 var totalHTML = fields[0];
	 var subtotalTag = "subtotalAjax_" + fields[1];
	 var subtotal = fields[2];
	 
	 document.getElementById("totalPriceAjax").innerHTML = totalHTML;
	 document.getElementById(subtotalTag).innerHTML = subtotal; 
	 }
} 


function validate(bid, address){
	// ensure no negative quantity exists.
	var id = "book_" + bid;
	var quantity = document.getElementById(id).value;
	if(!(quantity.match(/[0-9]*/) && parseInt(quantity) >= 0 && parseInt(quantity) < 100)){
		alert("quantity must be positive integer and less than 100!!!");
	}else{
		doCartAjax(bid, quantity, address); 
	}	

}

function validatebtn(bid, address, operation){
	var id = "book_" + bid;
	var quantity = parseInt(document.getElementById(id).value);
	operation ? quantity++ : quantity--;
	if(isNaN(quantity) || quantity < 0 || quantity >= 100){
		alert("quantity must be positive integer and less than 100!!!");
	}else{
		document.getElementById(id).value = quantity;
		doCartAjax(bid, quantity, address); 
	}
}
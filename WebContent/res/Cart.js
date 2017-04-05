function doCartAjax(address){
	 var request = new XMLHttpRequest();
	 var data=``;

	 /* add your code here to grab all parameters from form*/
	 
	 var books = document.getElementsByClassName("bookitem");
	 for(var i = 0; i < books.length; i++){
		 var bid = books[i].getAttribute("name");
		 var quantity = books[i].value;
		 data += bid;
		 data += "=";
		 data += quantity;
		 if(i != books.length - 1){
			 data += "&"
		 }		 
	 }

	 /*=========================================*/
	 request.open("GET", (address + "?" + data), true);
	 request.onreadystatechange = function() {
	 handlerCart(request);
	 };
	 request.send(null);
} 

function handlerCart(request){
	 if ((request.readyState == 4) && (request.status == 200)){
	 var target = document.getElementById("totalPriceAjax");
	 target.innerHTML = request.responseText;
	 }
} 

function validate(){
	// ensure no negative quantity exists.
}
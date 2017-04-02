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

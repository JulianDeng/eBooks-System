function doCategoryAjax(address){
	 var request = new XMLHttpRequest();
	 var data=``;

	 /* add your code here to grab all parameters from form*/
	 request.open("GET", (address + "?" + data), true);
	 request.onreadystatechange = function() {
	 handlerCategory(request);
	 };
	 request.send(null);
} 

function handlerCategory(request){
	 if ((request.readyState == 4) && (request.status == 200)){
	 var target = document.getElementById("category");
	 target.innerHTML = request.responseText;
	 }
} 


function doBookViewAjax(address, bid){
	 var request = new XMLHttpRequest();
	 var data=``;

	 /* add your code here to grab all parameters from form*/
	 request.open("GET", (address + "?" + data), true);
	 request.onreadystatechange = function() {
	 handlerBookView(request);
	 };
	 request.send(null);
} 

function handlerBookView(request){
	 if ((request.readyState == 4) && (request.status == 200)){
	 var target = document.getElementById("bookView");
	 target.innerHTML = request.responseText;
	 }
} 
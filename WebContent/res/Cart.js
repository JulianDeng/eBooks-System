function doBookViewAjax(address){
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

function validate(){
	// ensure no negative quantity exists.
}
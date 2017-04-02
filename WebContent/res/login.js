
//due to eclipse bug, this function will submit and any query string will not pass to server at all
function check(address){
	var r1 = checkFirstName();
	var r2 = checkLastName();
	var r3 = checkUserName();
	var r4 = checkPassword();
	
	var request = new XMLHttpRequest();
	var data='';
	if(r1 && r2 && r3 && r4){
		return true;
	}else{
		return false;
	}
}


function checkFirstName(){
	var fname=document.getElementById("fname").value
	if(fname.length==0){
		var fnamelabel = document.getElementById("fn");
		fnamelabel.innerHTML = "<font color=\"red\" size=\"2\">first name can't be blank</font>";
		return false;
	}
	else{
		var fnamelabel = document.getElementById("fn");
		fnamelabel.innerHTML = "<font color=\"green\" size=\"2\">first name is ok</font>";
		return true;
	}
}

function checkLastName(){
	var lname=document.getElementById("lname").value
	if(lname.length==0){
		var lnamelabel = document.getElementById("ln");
		lnamelabel.innerHTML = "<font color=\"red\" size=\"2\">last name can't be blank</font>";
		return false;
	}
	else{
		var lnamelabel = document.getElementById("ln");
		lnamelabel.innerHTML = "<font color=\"green\" size=\"2\">last name is ok</font>";
		return true;
	}
	
}

function checkUserName(){
	var uname=document.getElementById("user").value
	if(uname.length==0){
		var unamelabel = document.getElementById("un");
		unamelabel.innerHTML = "<font color=\"red\" size=\"2\">user name can't be blank</font>";
		return false;
	}
	else{
		var unamelabel = document.getElementById("un");
		unamelabel.innerHTML = "<font color=\"green\" size=\"2\">user name is ok</font>";
		return true;
	}
}

function checkPassword(){
	var password1=document.getElementById("pswd1").value
	var password2=document.getElementById("pswd2").value
	var result1=false;
	var result2=false;
	if(password1.length < 10){
		var passwordlabel = document.getElementById("pwd");
		passwordlabel.innerHTML = "<font color=\"red\" size=\"2\">password must be at least 10 characters</font>";
		result1=false;
	}
	else if(password1.length > 20){
		var passwordlabel = document.getElementById("pwd");
		passwordlabel.innerHTML = "<font color=\"red\" size=\"2\">password must be at most 20 characters</font>";
		result1=false;
	}
	else{
		var passwordlabel = document.getElementById("pwd");
		passwordlabel.innerHTML = "<font color=\"green\" size=\"2\">password is ok</font>";
		result1=true;
	}
	
	if(password1 != password2){
		var passwordlabel = document.getElementById("pwdagain");
		passwordlabel.innerHTML = "<font color=\"red\" size=\"2\">password do not match</font>";
		result2=false;
	}
	else{
		var passwordlabel = document.getElementById("pwdagain");
		passwordlabel.innerHTML = "<font color=\"green\" size=\"2\">password matched</font>";
		result2=true;
	}
	return (result1 && result2);
}


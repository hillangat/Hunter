

var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

$("document").ready(function(){
	/*$( "form#hunterLoginForm" ).submit(function( event ) { 
		event.preventDefault(); 
		handleLogin(); 
	});*/
});


function handleLogin(){
	
	$("#hunterUserNameError").html(" ");
	$("#hunterPasswordError").html(" ");
	
	var userName = $("#hunterUserName").val();
	var password = $("#hunterPassword").val();
	console.log(userName + " : " + password);
	
	var isEmptyUserName = userName == null || userName.trim() === "" || userName === "undefined"; console.log("isEmptyUserName = " + isEmptyUserName);
	var isEmptypassword = password == null || password.trim() === "" || password === "undefined";console.log("isEmptypassword = " + isEmptypassword);
	
	if(isEmptypassword || isEmptyUserName){
		console.log("One is empty");
		if(isEmptypassword){
			$("#hunterPasswordError").html("Password is required.");
		}
		if(isEmptyUserName){
			$("#hunterUserNameError").html("User name is required.");
		}
		return;
	}else{
		$("#hunterUserNameError").html(" ");
		$("#hunterPasswordError").html(" ");
	}
	
	var url = baseUrl + "/login/login/home/json";
	
	/*$("#loginCover").animate({top: '250px', opacity: '0.0'},1000, function(){
		window.location.href = "http://localhost:8080/Hunter/hunter/login/after";
		kendoKipHelperInstance.popupWarning("Successfully logged in!", "Success");
	});*/
	
	
	console.log("Login url >> " + url);
	
	  $.ajax({
	    url: url,
	    type: 'POST',
	    data: JSON.stringify({"userName" : userName, "password" : password}), 
	    contentType: "application/json",
	    dataType : "json",
	}).done(function(data){
		var data_ = JSON.stringify(data);
		console.log(data_);
		var json = jQuery.parseJSON(data);
		console.log(json.status + "! " + json.MESSAGE);
	}).fail(function (data){
		var error = data.statusText + "(" + data.status + ")";
		console.log(error);
		alert( "Error : " + error );
	});
	  
}


function navigate(where){
	window.location.href = baseUrl + where;
}

function replaceAll(orginalString, regex, replaceWith){
	var processedOperand = orginalString;
	while(contains(processedOperand, regex)){
		processedOperand = processedOperand.replace(regex, replaceWith);
	}
	orginalString = processedOperand;
	return orginalString;
}

function contains(orginalString, regex){
	var index = orginalString.indexOf(regex);
	var constains_ = index != -1;
	return constains_;
}



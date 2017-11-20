
$(document).ready( function(){
	$('')
});

function validateAndSubmit() {
	
	$('#errors').text( '' );
	
	var 
	userName      = $("#username").val(),
	password      = $("#password").val(),
	userNameError = validateUserName(userName),
	passwordError = validatePassword(password);
	
	if ( userNameError ) {
		$('#errors').text( userNameError );
	}
	
	if ( passwordError ) {
		$('#errors').text( $('#errors').text() + ', ' + passwordError );
	}
	
}

function validateUserName( userName ) {
	if ( userName ) {
		return null;
	} else {
		return 'User name is required';
	}
}

function validatePassword( password ) {
	if ( password ) {
		return null;
	} else {
		return 'Password is required';
	}
}


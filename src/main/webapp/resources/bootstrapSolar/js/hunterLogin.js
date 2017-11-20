$(document).ready( function() {
	
	
	
});

function validateLoginForm() {
	
	var 
	userName = $('#userName').val(), 
	password = $('#password').val(),
	userNameErr = validateUserName( userName ),
	passwordErr = validatePassword( password ),
	credsValid  = true;

	if ( userNameErr ) {
		$('#userNameError').text( userNameErr );
		$('#userNameError').removeClass('hidden');
		credsValid = false;
	}
	
	if ( passwordErr ) {		
		$('#passwordError').text( passwordErr );
		$('#passwordError').removeClass('hidden');
		credsValid = false;
	}
	
	if ( credsValid ) {
		submitLogin();
	}
	
}

function submitLogin() {
	console.log( 'Submitting login...' );
}

function validateUserName( userName ){
	if ( !userName || userName.trim().length === 0 )
		return 'User name is required';
	if ( userName.length < 5 )
		return 'User name must be more than 5 characters';
	return null;
}

function validatePassword( password ){
	if ( !password || password.trim().length === 0 )
		return 'Password is required';
	if ( password.length < 5 )
		return 'Password must be more than 5 characters';
	return null;
}
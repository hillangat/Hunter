<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="//connect.facebook.net/en_US/all.js&appId=1188666857860363" type="text/javascript"></script>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<title>Post On Facebook</title>
</head>
<body>

<script>
  
 $("document").ready(function(){
	 $("#userAccessTokes").css({display:null}); 
	 $("#facebookMessage").html(""); 
 });
  
  var 
  globalMessage = "For all to be free all must be free!",
  userLoginTokens = null,
  userSelectedGroup = null;
  
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '1188666857860363',
      xfbml      : true,
      version    : 'v2.7'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
  
  function setUserToken(button_){
	  userLoginToken 	= $(button_).attr("value");
	  userSelectedGroup = $(button_).attr("id");
	  $("#facebookMessage").html("Set default group to ( <b>"+ $(button_).text() +"</b> )");
	  $("#setGroupToWorkOn").slideDown();
  }
  
  function login(){
	  console.log("Login in..."); 
	  FB.login(function (response){
		  console.log( JSON.stringify(response) );
		  console.log( response.status );
		  if( response.status === 'connected' ){
			  alert("You are connected :) ");
			  FB.api('/me/accounts', function(response){
				  
				  var 
				  data = response.data,
				  table = "<table>",
				  table_ = "</table>";
				  
				  if( data != null ){
					  for(var i=0; i<data.length; i++){
						  
						  var 
						  group = data[i],
						  tableStr = '<tr><td><button onClick="setUserToken(this)" value="access-token" id="data-group-id" onClick="setAccessToken()">data-group-name</button></td></tr>';
						  
						  tableStr = tableStr.replace("access-token", group.access_token);
						  tableStr = tableStr.replace("data-group-name", group.name);
						  tableStr = tableStr.replace("data-group-id", group.id);
						  table = table + tableStr;
						  	
					  }
					  table = table + table_;
					  $("#userAccessTokes").html(table);
				  }
			  });	  
		  }else if( response.status === 'not_authorized' ){
			  alert("You are not authorized!");
		  }else{
			  alert( "You are not connected to facebook ( "+ JSON.stringify(response) +" )" );
		  }
	  },{scope : 'publish_actions,user_managed_groups,manage_pages,publish_pages'});
  }
  
  function getLoginInfo(){
	  console.log("Getting login information.."); 
	  FB.api('/me','GET', {fields:"email,birthday,cover,education,gender,hometown,languages,location,political,religion,first_name, last_name, name, id"}, function(response){
		 alert( JSON.stringify(response) );
	  });
  }
  
  function postOnTimeline(message){
	  FB.api('/me/feed', 'post', {message:message}, function(response){
		  alert( JSON.stringify(response) );
	  });
  }
  
  function shareLink(){
	  var link = $("#linkToShare").val();
	  alert(link);
	  FB.api('/me/feed', 'post', {link:link}, function(response){
		  alert( JSON.stringify(response) );
	  });
  }
  
  function uploadPhoto(){
	  var link = $("#linkToShare").val();
	  FB.api('/me/photos', 'post', {url:link}, function(response){
		  if( response != null || response.status != null ){
			  alert( JSON.stringify(response) );			  
		  }else{
			  alert( JSON.stringify( response ));
		  }
	  });
  }
  
  function saveUserFBData(response){
	  
  }
  
  function getUserGroups(){
	  
  }
  
  function postToUserGroup(){
	  FB.api("/851797734921956/feed","POST",{message: "For all to be free, all must be free"}, function (response) {
	      if (response && !response.error) {
	    	  alert( JSON.stringify(response) );
	      }else{
	    	  alert( JSON.stringify(response) ); 
	      }
	    }
	);
  }
  
  function postToPhotoGroup(){
	  var link = $("#linkToShare").val();
	  FB.api("/851797734921956/photos","POST",{url: link}, function (response) {
	      if (response && !response.error) {
	    	  alert( JSON.stringify(response) );
	      }else{
	    	  alert( JSON.stringify(response) ); 
	      }
	    }
	);
  }
  
  function getGroupsDetails(){
	  FB.api('/me/accounts', function(response){
		  console.log( JSON.stringify(response) );
	  });
  }
  
  
  function postAsGroup(){
	  FB.api("/851797734921956/feed","POST",{message:"Tech Master Hunter is offically capable of posting to facebook!!!", access_token:"EAAMxMp92tUIBAAjzt17mZBCUrXwYpdIl2FfjyPkIlRr0f3LlBfnm25kJbztPv8ajymbsYJ1qQ5ZCIBgJice9FZC1cMRFln3MtKelklCk1h0DfBdQAuhDsVb0CtU82P37a22tjX7ZA4m5WQrf7ujmZCkEQOwR6RCAA8L6l6xoAieNOlmHbkp36"},function (response) {
	      if (response && !response.error) {
	    	  alert( JSON.stringify(response) );
	      }else{
	    	  alert( JSON.stringify(response) ); 
	      }
	    }
	);
  }
  
  function postPhotoAsGroup(){
	  var link = $("#linkToShare").val();
	  /* 
	  	The id here is not the id of the app, rather, it's the id of the group to which to post
	  	You can get the id using : http://wallflux.com/facebook_id/
	  	The access_token is important : it will show which app posted the image.
	  	Before you make the post. Get the access toke first for that user logged in and then use it.
	  	You can get and save the user_access token once the login is successful!
	  */
	  FB.api("/"+ userSelectedGroup +"/photos","POST",{url: link, access_token:userLoginToken},function (response) {
	      if (response && !response.error) {
	    	  alert( JSON.stringify(response) );
	      }else{
	    	  alert( JSON.stringify(response) ); 
	      }
	    }
	);
  }
  
  function postPhotoToAllGroups(){
	  
	  FB.api('/me/accounts', function(response){
		  
		  var 
		  data = response.data,
		  messagePad = $("#facebookMessage");
		  messagePad.html("Starting to post to facebook..."); 
		  
		  if( data != null ){
			  for(var i=0; i<data.length; i++){
				  
				  var 
				  group = data[i],
				  link = $("#linkToShare").val();
				  
				  $(messagePad).append("<br> Posting for group( "+ group.name +" )...");
				  FB.api("/"+ group.id +"/photos","POST",{url: link, access_token:group.access_token},function (response) {
				      if (response && !response.error) {
				    	  alert( JSON.stringify(response) );
				    	  $(messagePad).append("<br> Success!!");
				      }else{
				    	  $(messagePad).append("<br> Error!");
				      }
				    }
				);
				  
				  	
			  }
		  }
	  });	
	  
  }
  
  
</script>


<div id="hunterFacebookCover" >
	
	<div id="facebookStatus"></div>
	<div id="facebookLoginInfo"></div> 
	
	<button onClick="login()">Login</button>
	
<div id="setGroupToWorkOn" style="display:none;"  >
<button onClick="getLoginInfo()">Get Login Info</button>
	<button onClick="postOnTimeline('For all to be free, all must be free!')">Post On Time Line</button>
	<button onClick="uploadPhoto()">Share photo</button>
	<button onClick="postToUserGroup()">Post To Group</button>
	<button onClick="postToPhotoGroup()">Post Photo To Group</button>
	<br/>
	
	<table>
		<tr>
			<td><input id="linkToShare" placeholder="Enter Link" style="width:300px;" ></td>
			<td><button onClick="shareLink()">Share Link</button></td>
		</tr>
	</table>
	
	<button onClick="getGroupsDetails()" >Get Group Access Details</button>
	<button onClick="postAsGroup()" >Post As Group</button>
	<button onClick="postPhotoAsGroup()" >Post Photo As Groups</button>
	<button onClick="postPhotoToAllGroups()">Post Photo To All Group as As Group</button>

</div>

<br>
<br>

<div style="width:100%;" id="userAccessTokes" >
</div>
	
<div id="facebookMessage" style="font-size:15px;padding:10px;min-heigh:100px;z-index:1005;min-width:500px;width:100%;background-color:#E2FFFF;border:1px solid #8BB5B6;border-radius:4px;"  ></div>
</div>

</body>
</html>
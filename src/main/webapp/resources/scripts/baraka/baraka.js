
var asideClicked = false;
var rotateImageConent = '<img id="loginRotateIcon" style="border-radius:3px;margin-auto:0 auto;" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/refresh_baraka_weather_icon.png" width="14px" height="14px" />';

$("document").ready(function(){
	
	$("#barakaName").click(function(){
		window.location.href = "http://localhost:8080/Baraka/home";
	});
	
	$('#linksTable td').hide();
	$('#linksTable th').bind('click',pop);
	$('#searchInput').focus(function(){
		$('#searchDiv').slideToggle();
	});
	
	$('#searchInput').bind('focusout',searchFocusOut);
	$('#searchDiv').hide();
	$('#searchDiv').click(function(){if((this).not(':visible'))$(this).slideToggle(1000);});
	$('#popups').hide();
	$('#searchInput').keyup(function(){
		var val = $('#searchInput').val();
		searchHelper(val);
	}).keyup();
	
	$('#userIdentifier').click(function(){
		var userIdentifier = $(this).text();
		if(userIdentifier.trim() === 'Login')
			getAndDoOprtn($(this).text());
		else 
			getAndDoOprtn('My Account');
	});
	
	$("#editProfileButtonField").click(function(){
		$("#tab").fadeIn(function(){
			$(this).empty();
			$(this).fadeIn();
		});
	});
	
	$('.editProfileTableTd').bind('mouseover',editProfileMouseOver).bind('mouseout',editProfileMouseOut);
	$('.editProfileTableTd').bind('click',editProfileTableTdClickFunc);
	
	
	
	$( "form#editProfilePicUploadForm" ).submit(function( event ) {
		  event.preventDefault();
		  $("#editProfilePicButton").empty();
		  $("#editProfilePicButton").html(rotateImageConent);
		  var formData = new FormData($(this)[0]); 
		  var event = setInterval(rotateEvent,20);
		  var url = $("#editProfilePicUploadForm").attr('action');
		  console.log(url);
		  $.ajax({
		    url: url,
		    type: 'POST',
		    data: formData,
		    contentType: false,
		    processData: false
		}).done(function(data){
			var json = JSON.parse(data);
			$("#editProfilePicButton").text("Upload Picture");
			var url = json.imageUrl;
			$("#newProfileImageLoc").attr('src', 'http://localhost:8080/Baraka/static/resources/gfx/' + url);
			$("#editProfileTableTdProfilePic").attr('src', 'http://localhost:8080/Baraka/static/resources/gfx/' + url);
			clearInterval(event);
		}).fail(function (data){
			$("#editProfilePicButton").text("Upload Picture");
			clearInterval(event);
		});
	});
	
	$('.useAsShippingAddressButtonClass').mouseover(function(){
		$(this).css('background-color','#D6E5DC');
	}).mouseout(function(){
		$(this).css('background-color','');
	});
	
	$('.button').mouseover(function(){
		$(this).css('background-color','#D6E5DC');
	}).mouseout(function(){
		$(this).css('background-color','');
	});
	
	$( "form#editProfileInfoFormId" ).submit(function( event ) {
		  event.preventDefault();
		  
		  $("#editProfileInfoButtonPofile").html(rotateImageConent);
		  var event = setInterval(rotateEvent,20);
		  
		  var formData = {};
		  formData["firstName"] = $("#editProfileInfoID_firstName").val();
		  formData["lastName"] = $("#editProfileInfoID_lastName").val();
		  formData["userName"] = $("#editProfileInfoID_userName").val();
		  formData["email"] = $("#editProfileInfoID_email").val();
		  formData["phoneNumber"] = $("#editProfileInfoID_phoneNumber").val();
		  formData["password"] = $("#editProfileInfoID_password").val();
		  
		  var url = $("#editProfileInfoFormId").attr('action');
		  
		  console.log(url);
		  console.log(formData);
		  
		  $.ajax({
		    url: url,
		    type: 'POST',
		    data: formData
		}).done(function(data){
			clearInterval(event);
			var json = JSON.parse(data);
			var status = json.context.status;
			console.log(status);
			if(status === 'FAILED'){
				var errors = json.errors;
				$("#editProfileInfoID_firstName_error").text(errors.editProfileInfoID_firstName_error === 'undefined' ? "" : errors.editProfileInfoID_firstName_error );
				$("#editProfileInfoID_lastName_error").text(errors.editProfileInfoID_lastName_error === 'undefined' ? "" : errors.editProfileInfoID_lastName_error );
				$("#editProfileInfoID_userName_error").text(errors.editProfileInfoID_userName_error === 'undefined' ? "" : errors.editProfileInfoID_userName_error );
				$("#editProfileInfoID_email_error").text(errors.editProfileInfoID_email_error === 'undefined' ? "" : errors.editProfileInfoID_email_error );
				$("#editProfileInfoID_phoneNumber_error").text(errors.editProfileInfoID_phoneNumber_error === 'undefined' ? "" : errors.editProfileInfoID_phoneNumber_error );
				$("#editProfileInfoID_password_error").text(errors.editProfileInfoID_password_error === 'undefined' ? "" : errors.editProfileInfoID_password_error );
				$("#editProfileInfoButtonPofile").text("Submit Changes");
			}else{
				$("#editProfileInfoID_Edit_Area").fadeOut(function(){
					$(this).empty();
					var userDataArry = json.context.userData.split("=="); console.log(userDataArry);
					var stringHtml = '<h2 style="color:green;width:80%;margin-left:10%;text-align:center;" >Successfully updated information to :</h2>'+
						'<table id="completedStatusTable" style="width:30%;margin:0 auto;padding:10px;background-color: #D5EBE1;border-radius:5px;border:1px solid #C3E2D5;" >';
					for(var i=0; i<userDataArry.length; i++){
						var datum = userDataArry[i]; 
						console.log(datum);
						stringHtml += ('<tr><td style="border-bottom:1px solid #C4E4D7">'+ datum.split(".")[0] +'</td>');
						stringHtml += ('<td style="border-bottom:1px solid #C4E4D7">'+ datum.split(".")[1] +'</td></tr>');
					}
					stringHtml += '</table>';
					$(this).html(stringHtml);
					$(this).fadeIn("slow");
				});
			}
		}).fail(function (data){
			clearInterval(event);
			$("#editProfileInfoButtonPofile").text("Submit Changes");
		});
	});
	
	$('#goBackTableMainTr').click(function(){
		$("#goBackToProfilePageImageTd").html(rotateImageConent);
		$("#goBackText").text("Going back...");
		$("#goBackText").css('color','#5D6404');
		var event = setInterval(rotateEvent,20);
		$.ajax({
			  url: "http://localhost:8080/Baraka/signup/accountDelay", 
			  context: document.body,
			  type:"GET",
			}).done(function(data) {
				window.location.href = 'http://localhost:8080/Baraka/signup/accountDelay';
				clearInterval(event);	
			}).fail(function(data){
				clearInterval(event);
				$(this).html("<div style='color:red;'>Error!</div>");
		});
	});
	
	$('#goBackTableMainTrToEdit').click(function(){
		$("#goBackToProfilePageImageTdToEdit").html(rotateImageConent);
		$("#goBackTextToEdit").text("Editing again...");
		$("#goBackTextToEdit").css('color','#5D6404');
		var event = setInterval(rotateEvent,20);
		$.ajax({
			  url: "http://localhost:8080/Baraka/signup/stageEdit", 
			  context: document.body,
			  type:"GET",
			}).done(function(data) {
				window.location.href = 'http://localhost:8080/Baraka/signup/stageEdit';
				clearInterval(event);	
			}).fail(function(data){
				$(this).html("<div style='color:red;'>Error!</div>");
		});
	});
	
	
	$('#cart').click(function(){
		getAndDoOprtn('Cart');
	});
	
	$('#logo').click(function(){
		getAndDoOprtn('Logo');
	});
	
	$('#LoginButtonCheckOutInfo').click(function(){
		$("loginDecisionTableCheckOutInfo").remove();
		 $( "#loginDecisionTableCheckOutInfoMessage" ).fadeOut(function() {
				$(this).empty();
				$("#loginDivForCheckOut").css("border","0px solid black");
				var background = $("#loginDivForCheckOut").parent().css("background-color");
				$("#loginDivForCheckOut").css("background-color",background);
				$(this).fadeIn(500,"slow");
		 });
		 $("#loginDecisionTableCheckOutInfo").remove();
		 $("#loginTableCheckOutInfo").fadeOut(function(){
				$(this).removeClass("displayNone");
				$(this).fadeIn("slow");
		 });
		
	});
	
	setInterval("slideShow()", 3000);
	
	
	$('*').click(function(){
		
		if($(this).attr('id') == 'aside'){
			//
		}
		
	});
	
$('.chatRecord').click(function(){
	var chatId = $(this).attr('id');
	$( "#chatIdInHiddenChatForm" ).attr( "value", chatId);
	$("#chatRecordsDiv").css('padding-top','15%');
	$("#chatRecordsDiv").css('padding-left','47%');
	$("#chatRecordsDiv").html(rotateImageConent);
	$("#loginRotateIcon").css({width:20, height:20});
	var event = setInterval(rotateEvent,20);
	$("#chatRecordsDiv").fadeOut(2000, function(){
		$( "#hiddenChatForm" ).submit();
		clearInterval(event);
	});
});


 $('#handleChatMessage').click(function(){
	handleChatMessage();
 });
 
 
 $("form#messageForm").submit(function(){
	 //event.preventDefault();
	  var $form = 
		  $( this ),
		  subject = $form.find("input[name='subject']" ).val(),
		  body = $form.find( "input[name='body']" ).val(),
		  fromUserId = $form.find( "input[name='fromUserId']" ).val(),
		  toUserId = $form.find("input[name='toUserId']" ).val(),
		  type = $form.find( "input[name='type']" ).val(),
		  msgId = $form.find( "input[name='msgId']" ).val(),
		  status = $form.find("input[name='status']" ).val(),
		  photoUrl = $form.find( "input[name='photoUrl']" ).val(),
		  submittedTime = $form.find( "input[name='submittedTime']" ).val(),
		  msgStr = $form.find("input[name='msgStr']" ).val(),
		  photoWidth = $form.find( "input[name='photoWidth']" ).val(),
		  photoheight = $form.find( "input[name='photoheight']" ).val(),
		  chatId = $form.find( "input[name='chatId']" ).val(),
		  url = $form.attr( "action" );
	  
	 /* var formData = {};
	  formData["firstName"] = $("#editProfileInfoID_firstName").val();
	  formData["lastName"] = $("#editProfileInfoID_lastName").val();
	  formData["userName"] = $("#editProfileInfoID_userName").val();
	  formData["email"] = $("#editProfileInfoID_email").val();
	  formData["phoneNumber"] = $("#editProfileInfoID_phoneNumber").val();
	  formData["password"] = $("#editProfileInfoID_password").val();
	  formData["password"] = $("#editProfileInfoID_password").val();
	  formData["password"] = $("#editProfileInfoID_password").val();
	  formData["password"] = $("#editProfileInfoID_password").val();
	  formData["password"] = $("#editProfileInfoID_password").val();*/
	  
	  var posting = $.post( url, { subject:subject,body:body,fromUserId:fromUserId,toUserId:toUserId,type:type,msgId:msgId,status:status,photoUrl:photoUrl,submittedTime:submittedTime,msgStr:msgStr,photoWidth:photoWidth,photoheight:photoheight,chatId:chatId } );
	  posting.done(function( data ) {
		  var json = JSON.parse(data);
		  alert(json.length);
		  return;
	  }).fail(function(data){
		  clearInterval(inputEven);
	  });
 });
 
 
});


var editProfileTableClicked = false;

function editProfileTableTdClickFunc(){
	if(editProfileTableClicked === true)
		return;
	editProfileTableClicked = true;
	var text = $(this).text();
	var this_ = $(this);
	this_.empty();
	this_.html('Please wait....  ' + rotateImageConent);
	this_.css('color','#5D6404');
	unbindEditProfileTableTdAndCaptureCss();
	var event = setInterval(rotateEvent,20);
	var id = this_.attr('id');
	$.ajax({
		  url: "http://localhost:8080/Baraka/signup/getJsonDataForEditProfile/" + id, 
		  context: document.body,
		  type:"GET",
		}).done(function(data) {
			bindEditProfileTableTdAndReleaseCss();
			this_.text(text);
			clearInterval(event);
			var json = JSON.parse(data);
			if(id === 'editProfileInfoID'){
				if(json !== 'undefined'){
					clearAndOpenEditArea(id, json, "editProfileInfoFormId");
				}else{
					bindEditProfileTableTdAndReleaseCss();
					this_.html("<div style='color:red;'>" + json.context["message"] +"</div>");
				}
			}
			
			if(id === 'editProfilePicture'){
				$("#editProfileMainTable").hide();
				unhideAndFadeIn($("#editProfileInfoID_Edit_Area"));
				$('#editProfileInfoFormId').addClass('hidden');
				$('#editProfilePicMainTable').hide(function(){
					$(this).removeClass('hidden');
					$(this).fadeIn();
				});
			}
			
		}).fail(function(data){
			bindEditProfileTableTdAndReleaseCss();
			this_.html("<div style='color:red;'>Error occurred!</div>");
	});
	
}

function clearAndOpenEditArea(id, json, form){
	$("#editProfileMainTable").fadeOut(function(){
		this.remove();
		unhideAndFadeIn($("#editProfileInfoID_Edit_Area"));
	});
	if(id === "editProfileInfoID"){
		unhideAndFadeIn($("#editProfileInfoFormId"));
		var status = json.context["status"];
		if(status === 'undefined' || status == "FAILED"){
			emptyAreaForErrorMessage(json);
			return;
		}
		var userName = json.userJson.userName;
		var lastName = json.userJson.lastName;
		var firstName = json.userJson.firstName;
		var phoneNumber = json.userJson.phoneNumber;
		var password = json.userJson.password;
		var email = json.userJson.email;
		
		console.log(json.userJson.userName);
		console.log(json.userJson.lastName);
		console.log(json.userJson.firstName);
		console.log(json.userJson.phoneNumber);
		console.log(json.userJson.userName);
		console.log(json.userJson.email);
		
		$("#editProfileInfoID_userName").attr("value",userName);
		$("#editProfileInfoID_lastName").attr("value",lastName);
		$("#editProfileInfoID_firstName").attr("value",firstName);
		$("#editProfileInfoID_phoneNumber").attr("value",phoneNumber);
		$("#editProfileInfoID_password").attr("value",password);
		$("#editProfileInfoID_email").attr("value",email);
	}
	
}

function unhideAndFadeIn(victim){
	victim.removeClass('hidden');
	if(victim.attr('id') !== 'editProfileInfoID_Edit_Area'){
		victim.hide();
		victim.fadeIn("slow"); 
	}
}

function emptyAreaForErrorMessage (json){
	$("#editProfileInfoID_Edit_Area").empty();
	$("#editProfileInfoID_Edit_Area").html("<h3 class='errors' style='width:40%;margin-left:30%;text-align:center;' >"+ json.context.message +"</h3>");
}

function unbindEditProfileTableTdAndCaptureCss(){
	$('.editProfileTableTd').css('color','#729E85');
	$('.editProfileTableTd').unbind('mouseover',editProfileMouseOver);
	$('.editProfileTableTd').unbind('mouseover',editProfileMouseOut);
}

function bindEditProfileTableTdAndReleaseCss(){
	$('.editProfileTableTd').css('color','#000000');
	editProfileTableClicked = false;
	$('.editProfileTableTd').bind('mouseover',editProfileMouseOver);
	$('.editProfileTableTd').bind('mouseover',editProfileMouseOut);
}


function editProfileMouseOver(){
	$(this).css('background-color','#BFDACE');
	$(this).css('border','1px solid #A9CABC');
	$(this).css('border-radius','3px');
	$(this).css('height','40px;');
}

function editProfileMouseOut(){
	$(this).css('background-color','#D5EBE1');
	$(this).css('height','40px;');
	$(this).css('border','');
	$(this).css('border-radius','');
}

function searchFocusOut(){
	
	$('#closeWindow').click(function(){
			$('#searchDiv > div').remove();
			$('#searchDiv').hide();
	;});
	var searchDiv = $('#searchDiv');
	if(searchDiv.is(':visible')){
		searchDiv.css('color','#000');
	}
}

function callAdminActionHandler(id){

	var formData = {id:id}; //Array 
	 
	$.ajax({
	    url : "/admin/action/user",
	    type: "POST",
	    data : formData,
	    success: function(data, textStatus, jqXHR)
	    {
	        //alert(data);
	    },
	    error: function (jqXHR, textStatus, errorThrown)
	    {
	    	//alert(errorThrown + " " + jqXHR +  " " + textStatus);
	    }
	});
}

function searchHelper(searchText){
	
	
	$.ajax({
		
		  url: "http://localhost:8080/Baraka/search/match/str/" + searchText, 
		  context: document.body,
		  type:"GET",
		  
		}).done(function(data) {
			
			if(data.length === 0){
				var div = "The item searched could not be found!";
				$('#searchDiv > div').remove();
				$('#searchDiv > table').remove();
				$("<div id='searchErrorDiv'></div>").html(div).appendTo('#searchDiv');
				setRecordTableCss(100);
				return;
			}
				$('#searchDiv > div').remove();
				$('#searchDiv > table').remove();
				$('#recordTable').remove();
				$("<div id=\"recordTable\"><div>").html(data).appendTo('#searchDiv');
				setRecordTableCss($('#recordTable').css('height'));
				
				
		}).fail(function(data){
		
				var div = "An error occurred!";
				$('#searchDiv > div').remove();
				$('#searchDiv > table').remove();
				$("<div id='searchErrorDiv'></div>").html(div).appendTo('#searchDiv');
				setRecordTableCss(100);
				return;
	});
	
}

function setRecordTableCss(searchDivHeight){
	
	$('#recordTable tr:odd').css('background-color','#C1D4C9');
	$('#recordTable tr:even').css('background-color','#B0DAC2');
	$('#searchDiv th').css('background-color','#6F907C');
	$('#searchDiv th').css('color','white');
	$('#searchDiv td').css('text-align','center');
	$('#searchDiv').css('height',searchDivHeight);
	
	$('#searchErrorDiv').css('text-align','center');
	$('#searchErrorDiv').css('color','#AE6265');
	$('#searchErrorDiv').css('width','40%');
	$('#searchErrorDiv').css('font-size','20px');
	$('#searchErrorDiv').css('background-color','#D2E9E8');
	$('#searchErrorDiv').css('border-radius','4px');
	$('#searchErrorDiv').css('height','40px');
	$('#searchErrorDiv').css('padding','50px');
	$('#searchErrorDiv').css('margin-left','30%');
	$('#searchErrorDiv').css('border','2px solid #5DA5A3;');
	
	//style='text-align:center;color:#F60000;font-size:20px;
}

function cleanPopup(exp){
	$(exp).empty();
}

function pop() {
	
	cleanPopup('#popups'); 
	
	var index = $(this).parent().children().index($(this));
	var str = "";
	$('#linksTable tr td:nth-child(' + (index + 1) + ')').each(function(){
		str += $(this).text() + ',';
	});
	str = str.substring(0, str.length-1); //remove the last comma. 
	publish(str.split(","),index);
	$('#popups').fadeIn("slow");
}

function publish(links,index){
	
	var div = "<div class='popupdivs'>",div2 = "</div>";
	for(var i=0; i<links.length; i++ ){
		if (links[i].trim().length > 0) {
			var whole = div + links[i] + div2;
			$('#popups').append(whole);
		}
	}
	
	$('#popups > div').click(function(){
		getAndDoOprtn($(this).text());
	}); 
	
	var x = ((index * 150)) + 180;
	x += 'px';
	$('#popups').css('margin-left',x);
}


function getAndDoOprtn(text){
	
	if(text.trim() === 'Sign In' || text.trim() === 'Login'){
		window.location.href = "/Baraka/login/main";
	}if(text.trim() === 'Logo'){
		window.location.href = "/Baraka/";
	}if(text.trim() === 'Watches'){
		window.location.href = "/Baraka/java";
	}else if(text.trim() === 'Shop'){
		window.location.href = "/Baraka/commodity/shop";
	}else if(text.trim() === 'Sign Up'){
		window.location.href = "/Baraka/signup/register";
	}else if(text.trim() === 'Home'){
		window.location.href = "/Baraka/";
	}else if(text.trim() === 'Login'){
		window.location.href = "/Baraka/login/main";
	}else if(text.trim() === 'Cart'){
		window.location.href = "/Baraka/commodity/cart";
	}else if(text.trim() === 'Admin' || text.trim() === 'admin'){
		window.location.href = "/Baraka/admin";
	}else if(text.trim() === 'Insert Commodity'){
		window.location.href = "/Baraka/commodity";
	}else if(text.trim() === 'My Account'){
		window.location.href = "/Baraka/signup/account";
	}else if (text.trim() ==='Fancy Admin'){
		window.location.href = "/Baraka/admin/fancy";
	}
}

function signIn(){
	
	alert("In sign in......");
	
 }

function slideShow() {

	var currentPic = $('#slideShow div.active');
	var nextPic = currentPic.next();

	if (nextPic.length === 0) {
		nextPic = $('#slideShow div:first');
	}
	currentPic.removeClass('active').addClass('prev');
	nextPic.css({
		opacity : 0.0
	}).addClass('active').animate({
		opacity : 1.0
	}, 3000, function() {
		currentPic.removeClass('prev');
	});

}

function loadPics(watches,slideType){
	
	var currentPic = $('#slideShow div.active');
	var nextPic = currentPic.next();

	if (nextPic.length === 0) {
		nextPic = $('#slideShow div:first');
	}
	currentPic.removeClass('active').addClass('prev');
	nextPic.css({
		opacity : 0.0
	}).addClass('active').animate({
		opacity : 1.0
	}, 3000, function() {
		currentPic.removeClass('prev');
	});
	
}
	
	
function handleChatMessage(){
	
	var message = $('#chatTextArea').val();
	
	if(message == null || message.trim === ''){
		alert('Your message is empty. Think something up :');
		return;
	}
	
	 $("form#messageForm").submit(function(){
		 event.preventDefault();
		  var $form = 
			  $( this ),
			  subject = $form.find("input[name='subject']" ).val(),
			  body = $form.find( "input[name='body']" ).val(),
			  fromUserId = $form.find( "input[name='fromUserId']" ).val(),
			  toUserId = $form.find("input[name='toUserId']" ).val(),
			  type = $form.find( "input[name='type']" ).val(),
			  msgId = $form.find( "input[name='msgId']" ).val(),
			  status = $form.find("input[name='status']" ).val(),
			  photoUrl = $form.find( "input[name='photoUrl']" ).val(),
			  submittedTime = $form.find( "input[name='submittedTime']" ).val(),
			  msgStr = $form.find("input[name='msgStr']" ).val(),
			  photoWidth = $form.find( "input[name='photoWidth']" ).val(),
			  photoheight = $form.find( "input[name='photoheight']" ).val(),
			  chatId = $form.find( "input[name='chatId']" ).val(),
			  url = $form.attr( "action" );
		  
		  var posting = $.post( url, { subject:subject,body:body,fromUserId:fromUserId,toUserId:toUserId,type:type,msgId:msgId,status:status,photoUrl:photoUrl,submittedTime:submittedTime,msgStr:msgStr,photoWidth:photoWidth,photoheight:photoheight,chatId:chatId } );
		  posting.done(function( data ) {
			  var json = JSON.parse(data);
			  alert(json.length);
			  return;
		  }).fail(function(data){
			  clearInterval(inputEven);
		  });
	 });
	 
}

function handleAjaxLogin(){
	
	var event = setInterval(rotateEvent, 20);
	getLoginResults(event);
	
	
}

function getLoginResults(inputEven){
	
	$( "#loginCheckOutInfoForm" ).submit(function( event ) {
		  event.preventDefault();
		  var $form = $( this ),username = $form.find( "input[name='username']" ).val(), password = $form.find( "input[name='password']" ).val(),purpose = $form.find( "input[name='purpose']" ).val(), url = $form.attr( "action" );
		  var posting = $.post( url, { username: username, password:password, purpose:purpose } );
		  posting.done(function( data ) {
			  var json = JSON.parse(data);
			  if(json["status"] === 'false'){
				  $("#loadUserByLoginInfo_userName_error").text( json["userNameError"] );
				  $("#loadUserByLoginInfo_password_error").text( json["passwordError"] );
				  clearInterval(inputEven);
			  }else if(json["status"] === 'true'){
				  var rotate = '<img id="loginRotateIcon" style="border-radius:3px;margin-auto:0 auto;" alt="No Image" src="http://localhost:8080/Baraka/static/resources/gfx/refresh_baraka_weather_icon.png" width="18px" height="18px" />';
				  $("#subContainer").fadeOut(function(){
					  $(this).html('<h1 style="margin-top:200px;text-align:center;" >Checking addresses...   ' + rotate +'</h1>');
					  $(this).fadeIn(30);
				  });
				  var event = setInterval(rotateEvent, 20);
				  window.location.href = "http://localhost:8080/Baraka/commodity/reloadExistentAddresses";
				  clearInterval(event);
			  }
			  return;
		  }).fail(function(data){
			  clearInterval(inputEven);
		  });
		  
		  
		});
}

function getSessionUserAddresses(toUpdate){
	$.ajax({
		  url: "http://localhost:8080/Baraka/commodity/getSessionUserAddresses", 
		  context: document.body,
		  type:"POST",
		}).done(function(data) {
			$(toUpdate).text("Success");			
		}).fail(function(data){
			$(toUpdate).text("<span class='errors'>An error occurred!</span>");
	});
}

var degrees = 0;

function loadSessionUserAddresses(publishingDiv, url){
	$.ajax({
		  url: url, 
		  type:"POST"
	}).done(function(data) {
			
			var json = JSON.parse(data);
			var count = json["count"];
			
			if(count > 0){
			  
				var html = "<div>";
				html += "<table>";
				for(var i=0; i < count; i++){
				  html += "<tr>";
				  html += ("<tr><td>" + json["address" + i].street + "</td></tr>");
				  html += ("<tr><td>" + json["address" + i].city + "</td></tr>");	
				  html += ("<tr><td>" + json["address" + i].state + "</td></tr>");
				  html += ("<tr><td>" + json["address" + i].country + "</td></tr>");
				  html += ("<tr><td>" + json["address" + i].zipCode + "</td></tr>");
				  html += ("<tr><td>" + json["address" + i].type + "</td></tr>");
				  html += "</tr>";
				  html += "<tr><td>.............</td></tr>";
				}
			  
			  html += "</table>";
			  html += "</div>";
			  
			  $(publishingDiv).html(html);
			  $(publishingDiv).css("font-size","10px");
			  $(publishingDiv).html();
			  
		 }else {
			  publishingDiv.html("<div style='color:red;font-size:16px;'>No addresses found!</div>");
		 }
	});
}


function loadChatPartners(publishingDiv, url){
	$.ajax({
		  url: url, 
		  type:"POST",
		  contentType: "application/json"
	}).done(function(data) {
			
			var json = JSON.parse(data);
			var count = json.count;
			
			var html = "<div>";
			html += "<table>"; 
			
			if(count !== 'undefined' && count != null && count > 0){
				for(var i=0; i<count;i++){
					var img = json.users[0]["user" + i][0].imageUrl;
					var id = json.users[0]["user" + i][0].id;
					html += "<tr><td id='" + id + "' onClick='loadChatsForMyPartners("+ id + ")' ><img src='http://localhost:8080/Baraka/static/resources/gfx/" + img+ "' width='50px' height='50px' alt='No IMG' >" + "</td></tr>";
					html += "<tr><td>" + json.users[0]["user" + i][0].firstName + " " + json.users[0]["user" + i][0].lastName + "</td></tr>";
					html += "<tr><td></td></tr>";
				}
			}else{
				$(publishingDiv).html("<div style='color:red;font-size:16px;'>No chat partners found!</div>");
			}
		
			html += "</table>";
			html += "</div>";
			console.log(html);
		  
			$(publishingDiv).html(html);
			$(publishingDiv).css("font-size","10px");
			$(publishingDiv).html();
			  
	});
}

function loadChatsForMyPartners(id){
	window.location.href = "http://localhost:8080/Baraka/chat/goToChatsForAjax/" + id;
}


function rotateEvent(){
	rotateIconForLogin();
	if(degrees <= 360){
		degrees = degrees + 33;
	}else{
		degrees = 0;
	}
}

function rotateIconForLogin(){
	$("#loginRotateIcon").css('-moz-transform', 'rotate(' + degrees + 'deg)');
	$("#loginRotateIcon").css('-moz-transform-origin', '50% 50%');
	$("#loginRotateIcon").css('-webkit-transform', 'rotate(' + degrees + 'deg)');
	$("#loginRotateIcon").css('-webkit-transform-origin', '50% 50%');
	$("#loginRotateIcon").css('-o-transform', 'rotate(' + degrees + 'deg)');
	$("#loginRotateIcon").css('-o-transform-origin', '50% 50%');
	$("#loginRotateIcon").css('-ms-transform', 'rotate(' + degrees + 'deg)');
	$("#loginRotateIcon").css('-ms-transform-origin', '50% 50%');
}


function scrollToBottom(div){
   if (div.scrollTop < div.scrollHeight - div.clientHeight)
        div.scrollTop += 10; // move down
   alert("scrolled!");
}

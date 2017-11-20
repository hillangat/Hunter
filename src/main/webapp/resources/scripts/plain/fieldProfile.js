
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

var userData = null,
	rotateEvent = null,
	regionContainer = {"country":null,"county":null,"constituency":null,"ward":null, "level":null},
	tickImg = function(){
		var url = getBaseURL("Hunter/static/resources/images/tick.png"),
		image = '<img  src="'+ url +'" width="30px" height="30px" class="progressIconImg" data-name="successIconImg" >';
		return image;
	},
	xImg = function(){
		var url = getBaseURL("Hunter/static/resources/images/x-button.png"),
		image = '<img  src="'+ url +'" width="30px" height="30px" class="progressIconImg" data-name="successIconImg" >';
		return image;
	},
	loadUserEditData = false,
	selUserData = null,
	dataTable = null,
	isFileSelected = false,
	secondsTakenInterval = null,
	currSecs = 0,
	isProfileUploaded = false,
	profileData = {};

var progressSpinner = "<span id='progressSpinnerHolderSpan' style='width:100%;font-size:30px;color:#004B0A;' ><img src='" + baseUrl + "static/resources/images/refreshing_spinner_new.gif' style='border-radius:50%' width='50' height='50'  /></span>";

$("document").ready(function(){
	//loadUserProfileData();
	//loadContactsData();
	//bindProgressFunctions();
	launchProgressPopup();
});

function launchProgressPopup(){
	$(".popup-inner").empty();
	$('[data-popup="popup-1"]').fadeIn(200, function(){
		$(".popup-inner").append("<div id='hunterFieldPopupContainer' ></div>"); 
		var html = $("#progressTemplateContainer div[data-name='loadProfileAndReceivers']").html();
		$("#hunterFieldPopupContainer").append(html);
		var progressBar = $("#hunterFieldPopupContainer div[data-name='progressbar']");
		var progressLabel = $("#hunterFieldPopupContainer div[data-name='progressBarLabel']");
		var secondsTaken = $("#hunterFieldPopupContainer span[data-name='numberOfSecondsTaken']");
		secondsTakenInterval = setInterval(function(){
			currSecs++;
			$(secondsTaken).html(currSecs + " seconds"); 
		}, 1000);
		launchProgressHandler(progressBar, progressLabel, 'Completed!');
		updateProgressHandler(progressBar, 5, 'Loading Data...');
		loadUserProfileData(progressBar);
	});
}

function loadUserProfileData(progressBar){
	var r = $.Deferred();
	
	loadLogout();
	
	var url = getBaseURL("Hunter/rawReceiver/action/read/rawReceiverUser");
	$.ajax({
		url: url,
	    data : null,
	    method: "POST",
	    dataType : "json", 
	    contentType : "application/json"
	}).done(function(data) {
		
		if(data != null){
			
			data = JSON.stringify(data);
			data = $.parseJSON(data);
			
			var fullName = data["fullname"];
			
			$("#fieldUserTotalContacts").	text( data["all"] 		=== 'undefined' ? "" : data["all"] 		);
			$("#fieldUserCountry").			text( data["country"] 	=== 'undefined' ? "" : data["country"] 	);
			$("#fieldUserCounty").			text( data["county"] 	=== 'undefined' ? "" : data["county"] 	);
			$("#fieldUserVerifiedContacts").text( data["verified"] 	=== 'undefined' ? "" : data["verified"] );
			$("#fieldUserWard").			text( data["ward"] 		=== 'undefined' ? "" : data["ward"] 	);
			$("#fieldUserPhone").			text( data["phone"] 	=== 'undefined' ? "" : data["phone"] 	);
			$("#fieldUserEmail").			text( data["email"] 	=== 'undefined' ? "" : data["email"] 	);
			$("#fieldUserConstituency").	text( data["cons"] 		=== 'undefined' ? "" : data["cons"] 	);
			$("#fieldUserFullName").		html( fullName );
			$("#fieldUserTotalPayout").		text( data["compensation"] === 'undefined' ? "0.00" : data["compensation"] );
			
			profileData = data;
			
			$("#hunterFieldPopupContainer td[data-name='profileProgressIcon']").html( tickImg() );
			setTimeout(function(){
				$("#hunterFieldPopupContainer tr[data-name='loadContactsDataDisplay']").css({"display":""});
				updateProgressHandler(progressBar, 35, 'Completed!');
				loadContactsData(progressBar);
			}, 200);
			
		}else{
			notifyError("Failed to obtain your profile data");
			updateProgressHandler(progressBar, 0, 'No data found!');
			$("#hunterFieldPopupContainer td[data-name='profileProgressIcon']").html( xImg() );
		}
		
	 }).fail(function(data) {
		 var message = data.statusText + " (" + data.status + "). Please contact Production Support.";
		 notifyError(message);
		 updateProgressHandler(progressBar, 0, 'No data found!');
		 $("#hunterFieldPopupContainer td[data-name='profileProgressIcon']").html( xImg() );
		 closeHunterPopup1();
	 });
	
	return r;
}

function loadContactsData(progressBar){

	$("#hunterFieldPopupContainer div[data-name='progressBarLabel']").text( "Loading Data..." );
	var r = $.Deferred();
	
	dataTable = $('#fieldProfileDataTable').DataTable( {
		 "ajax": {
	        "dataType": 'json',
	        "contentType": "application/json",
	        "type": "GET",
	        "url":baseUrl + "rawReceiver/action/raw/getUsersContacts",
	        "dataSrc": function (json) {
	        	var json = $.parseJSON(json);
	        	return json["data"];
	         }
		 },
		 "serverSide": false,
	     "scrollCollapse": true,
	     "paging":         true,
	     "displayLength": 15,
	     "dom": '<"toolbar">frtip',
	     "aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
	     "initComplete": function( settings, json ) {
	    	  secondsTakenInterval = null;
	    	    if(json != null && json.length > 0){
	    	    	$("#hunterFieldPopupContainer td[data-name='contactsProgressIcon']").html( tickImg() );
	    	    	$("#hunterFieldPopupContainer div[data-name='progressBarLabel']").text( "Completed!" );
	    	    }else{
	    	    	$("#hunterFieldPopupContainer td[data-name='contactsProgressIcon']").html( xImg() );
	    	    	notifyError("And error occurred trying to load your contacts!");
	    	    }
	    	    $("#hunterFieldPopupContainer div[data-name='progressBarLabel']").text( "Completed!" );
	    	    updateProgressHandler(progressBar, 30, 'Completed!');
	    	 	setTimeout(function(){
	    	 		closeHunterPopup1();
	    	 	},200);
	      },
		 "columns": [
           { "data" : "rawReceiverId" },
           { "data" : "receiverContact" },
           { "data" : "firstName" },
           { "data" : "lastName" },
           { "data" : "countryName" },
           { "data" : "countyName" },
           { "data" : "consName" }, 
           { "data" : "consWardName" },
           { "data" : "verified" },
           { "data" : "edit" },
           { "data" : "delete", "width": "0.7em" }
       ],
       columnDefs : [
             {
           	targets : 8,
           	"mRender" : function(data,type,full,meta){
           		if(data +'' === 'true'){
           			return '<center><img src="' + baseUrl + 'static/resources/images/tick.png" width="15px" height="15px" style="border:1px solid rgb(14,65,68);border-radius:50%;"  /></center>';
           		}else{
           			return '<center><img src="' + baseUrl + 'static/resources/images/cross-icon-img.png" width="15px" height="15px" style="border:1px solid rgb(14,65,68);border-radius:50%;"  /></center>';
           		}
           	}
           },
           {
           	targets : 9,
           	"mRender" : function(data,type,full,meta){
           		var id = full["rawReceiverId"];
           		return '<center><a  data-popup-open="popup-1" href="#" value="'+ id + '_EditContact"  class="ui-btn ui-corner-all ui-icon-edit ui-btn-icon-notext" class="shadowAllConersGray"   style="width:20px;height:20px;padding:0px;border:1px solid white;margin:0;" ></a></center>';
           	}
           },
           {
           	targets : 10,
           	"mRender" : function(data,type,full,meta){
           		var id = full["rawReceiverId"];
           		return '<center><a data-popup-open="popup-1" href="#" value="'+ id + '_DeleteContact"  class="ui-btn ui-corner-all ui-icon-delete ui-btn-icon-notext" class="shadowAllConersGray" style="width:20px;height:20px;padding:0px;border:1px solid white;margin:0;" ></a></center>';
           	}
           }
             
        ]
       
   });
	
	$("#fieldProfileDataTable_filter input").attr("placeholder","Search Contact");
	$("#fieldProfileDataTable_filter input").css({"background-color":"#DFF2F5"});
	$("#fieldProfileDataTable_filter").css({"width":"100%", "margin-bottom":"1px"});
	$("#fieldProfileDataTable_filter").prepend('<span style="float:left;width:200px;margin-left:-3.5%;margin-top:-8px;" ><button onmouseover="onmouseenterCloseA(this)" onmouseout="onmouseoutCloseA(this)" class="ui-btn ui-icon-arrow-d ui-btn-icon-left" onClick="downloadImportTemplate()" id="downloadImportTempButt" classs="highlightborder"  style="width:150px;border-radius:4px;background-color:#DFF2F5;color:green;height:45px;margin-bottom:-3px;" ><span>Template</span></button></span>');
	$("#fieldProfileDataTable_filter").prepend('<span style="float:left;width:200px;margin-left:-3.5%;margin-top:-8px;" ><button onmouseover="onmouseenterCloseA(this)" onmouseout="onmouseoutCloseA(this)" class="ui-btn ui-icon-arrow-u ui-btn-icon-left" onClick="launchImportStage()" id="importNewContactButt" classs="highlightborder"  style="width:150px;border-radius:4px;background-color:#DFF2F5;color:green;height:45px;margin-bottom:-3px;" ><span>Import</span></button></span>');
	$("#fieldProfileDataTable_filter").prepend('<span style="float:left;width:200px;margin-left:3.5%;margin-top:-8px;" ><button onmouseover="onmouseenterCloseA(this)" onmouseout="onmouseoutCloseA(this)" class="ui-btn ui-icon-plus ui-btn-icon-left" onClick="populatePopupForParams(\'0_NewContact\')" id="createNewContactButt"  style="width:150px;border-radius:4px;background-color:#DFF2F5;color:green;height:45px;margin-bottom:-3px;" ><span>New</span></button></span>');
	bindDataTableActionButtons();
	$('#fieldProfileDataTable').on( 'draw.dt', function () {
		bindDataTableActionButtons();
	} );
	
	return r;
}

function getBaseURL(loc) {
    return location.protocol + "//" + location.hostname +
       (location.port && ":" + location.port) + "/" + loc;
}


function getQuotedParam(param){
	return "'" + param +"'";
}

function defineAffirmButt(text, onClick, jClass){
	var affirm = '<center><a href="#" onmouseover="onmouseenterCloseA(this)" onmouseout="onmouseoutCloseA(this)" id="defineAffirmButt" onClick="'+ onClick +'" class="'+ jClass +'" style="background-color:#DFF2F5;min-width:200px;margin:10px;border:1px solid #93CADB;border-radius:5px;color:#0E4244;" >'+ text +'</a></center>';
	$("#affirmPopupAction").html(affirm);
}

function getDataTableJson(pId){
	var contacts = $('#fieldProfileDataTable').DataTable().rows().data();
	if( contacts != null && contacts.length != 0 ){
		for(var i=0; i<contacts.length; i++){
			var contact = contacts[i];
			var id = contact["rawReceiverId"];
			if(id == pId){
				return contact;
			}
		}
	}
}

function launchImportStage(){
	$(".popup-inner").empty();
	$(".popup-inner").html("<span style='width:10%;margin-left:45%;'>"+ progressSpinner +"&nbsp;&nbsp;&nbsp <span style='font-size:17px;' >Loading data...</span>");
	$('[data-popup="popup-1"]').fadeIn(200, function(){
		$(".popup-inner").empty();
		$(".popup-inner").append("<div id='hunterFieldPopupContainer' ></div>"); 
		var html = $("#importNewContainerTmplt").html();
		$("#hunterFieldPopupContainer").append(html);
		$("#importNewContainerSubButt *").css({"color":"red"});
		$("#hunterFieldPopupContainer button[id='importNewContainerSubButt']").mouseenter(function(){
			$(this).css({"border":"1px solid #62A1A5", "background-color":"#ACE1E5"});
		}); 
		$("#hunterFieldPopupContainer button[id='importNewContainerSubButt']").mouseleave(function(){
			$(this).css({"border":"1px solid #93CADB","background-color":"#DFF2F5"});
		});
		$("#hunterFieldPopupContainer a").mouseenter(function(){
			$(this).css({"border":"1px solid #62A1A5", "background-color":"#ACE1E5"});
		}); 
		$("#hunterFieldPopupContainer a:first-child").mouseleave(function(){
			$(this).css({"border":"1px solid #93CADB","background-color":"#DFF2F5"});
		});
		
		$("#hunterFieldPopupContainer form[data-name='importNewContactForm']").on('submit', function(){
			closeImportStageAndOpenRefreshSec();
		});
		
	});
}

function downloadImportTemplate(){
	var url = getBaseURL("Hunter/rawReceiver/action/download/template");
	window.open(url);
}


function readURL(input, target) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $( target ).attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function onmouseoutCloseA(a){
	$(a).css({"border":"1px solid #93CADB","background-color":"##DFF2F5"});
}

function onmouseenterCloseA(a){
	$(a).css({"border":"1px solid #62A1A5", "background-color":"#8AC6CA"});
}

function populatePopupForParams(param){
	
	var params = param.split("_"); 
	var func = params[1];
	var jClass =  "";
	
	$("#affirmPopupAction").empty();
	
	if(func === 'ImportContact'){
		alert("returning..."); 
		return;
	}else if(func == 'DeleteContact'){
		var html = "<h4 id='hunterNotificationMsg'  style='color:#C91414;text-align:center;' >Are you sure you want to delete this record?</h4>";
		$("#hunterFieldPopupContainer").append(html);
		jClass="ui-btn ui-icon-delete ui-btn-icon-left";
		defineAffirmButt("Delete","deleteContactAndRefresh('"+ params[0] +"')", jClass );
	}else if(func == 'EditContact'){
		var html = $("#hunterFieldEditTemplateContainer").html();
		$("#hunterFieldPopupContainer").append(html);
		jClass="ui-btn ui-icon-check ui-btn-icon-left";
		defineAffirmButt("Save","getEditedValuesAndSave('"+ params[0] +"')", jClass );
		loadValuesForContactId(params[0]);
		bindRegionsDrops();
	}else if(func == 'NewContact'){
		$(".popup-inner").empty();
		$(".popup-inner").html("<span style='width:10%;margin-left:45%;'>"+ progressSpinner +"&nbsp;&nbsp;&nbsp Loading data...</span>");
		var html = $("#hunterFieldEditTemplateContainer").html();
		$('[data-popup="popup-1"]').fadeIn(200, function(){
			$(".popup-inner").empty();
			$(".popup-inner").append("<div id='hunterFieldPopupContainer' ></div>"); 
			$("#hunterFieldPopupContainer").append(html);
			jClass="ui-btn ui-icon-check ui-btn-icon-left";
			defineAffirmButt("Submit","getEditedValuesAndSave('"+ params[0] +"')", jClass );
			var butts = $("#hunterFieldPopupButts").html();
			$("#hunterFieldPopupContainer").append(butts);
			bindRegionsDrops();
			onChangeCountry(null, null);
			return;
		});
	}else if( func = 'EditUserProfile' ){
		$(".popup-inner").empty();
		$(".popup-inner").html("<span style='width:10%;margin-left:45%;'>"+ progressSpinner +"&nbsp;&nbsp;&nbsp Loading data...</span>");
		var html = $("#hunterFieldEditTemplateContainer").html();
		$('[data-popup="popup-1"]').fadeIn(200, function(){
			$(".popup-inner").empty();
			$(".popup-inner").append("<div id='hunterFieldPopupContainer' ></div>"); 
			$("#hunterFieldPopupContainer").append(html);
			jClass="ui-btn ui-icon-check ui-btn-icon-left";
			defineAffirmButt("Submit","getEditedProfileValuesAndSave()", jClass );
			var butts = $("#hunterFieldPopupButts").html();
			$("#hunterFieldPopupContainer").append(butts);
			bindRegionsDrops();
			
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='contactType']  td:nth-child(1)").text("First Name*");
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='contactType']  td:nth-child(2)").html('<input data-name="firstName" class="hunterInputText" placeholder="Enter First Name"  type="text" />');
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='contact']  td:nth-child(1)").text("Last Name*");	
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='contact']  td:nth-child(2)").html('<input data-name="lastName" class="hunterInputText" placeholder="Enter Last Name"  type="text" />');
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='firstName']  td:nth-child(1)").text("Phone Number*");
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='firstName']  td:nth-child(2)").html('<input data-name="phoneNumber" class="hunterInputText" placeholder="Enter Phone Number"  type="text" />');
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='lastName']  td:nth-child(1)").text("Email");
			$("#hunterFieldPopupContainer tbody:nth-child(1) tr[data-tr='lastName']  td:nth-child(2)").html('<input data-name="email" class="hunterInputText" placeholder="Enter Email"  type="text" />');
			
			var userProfile = $("#hunterEditUserProfileCont").html();
			$("#hunterFieldPopupContainer").prepend(userProfile);
			
			$("#hunterFieldPopupContainer input[data-name='firstName']").attr("value", profileData["firstname"]);
			$("#hunterFieldPopupContainer input[data-name='lastName']").attr("value", profileData["lastname"]);
			$("#hunterFieldPopupContainer input[data-name='phoneNumber']").attr("value", profileData["phone"]);
			$("#hunterFieldPopupContainer input[data-name='email']").attr("value", profileData["email"]); 
			
			selUserData 				= {"countyId":null,"constituencyId":null,"consWardId":null};
			selUserData["countyId"] 		= profileData["countyid"];
			selUserData["constituencyId"]	= profileData["consid"];
			selUserData["consWardId"]		= profileData["wardid"];
			
			onChangeCountry(1, selUserData);
			
			return;
		});
	}
	
	
	var butts = $("#hunterFieldPopupButts").html();
	$("#hunterFieldPopupContainer").append(butts);
}

function clearDefaultRegionData(){
	regionContainer = null;
	regionContainer = {"country":null,"county":null,"constituency":null,"ward":null, "level":null};
}

function setValToSelect(selector, value, text){
	$(selector).val(value);
	$(selector).text(text);
}

function setValToInput(selector, value, text){
	
	if(value == null || text== null){
		return;
	}
	
	var curVal = $(selector).val();
	var curTxt = $(selector).text();
	
	//retain the option selected but avoid duplicates
	if(curVal != value && text != curTxt){
		$(selector).parent().append('<option value="'+ curVal +'">'+ curTxt +'</option>');
	}
	
	$(selector).val(value);
	$(selector).text(text);
}

function getRegionDataAndUpdate(selector, url, isEdit, contact){
	
	var countyId = null, 
		consId = null,
		consWardId = null;
	
	if( url.indexOf("counties") != -1 && isEdit){
		selUserData = contact;
	}
	
	if(isEdit){
		countyId = selUserData["countyId"];
		consId = selUserData["constituencyId"];
		consWardId = selUserData["consWardId"];
	}
	
	var r = $.Deferred();
	$(selector).empty();
	
	var 
	isCounties = false, 
	isConstituencies = false, 
	isWards = false;
	
	var valName = null;
	var txtName = null;
	
	if( url.indexOf("counties") != -1 ){
		txtName = "countyName";
		valName = "countyId";
		isCounties = true;
	}else if( url.indexOf("constituencies") != -1 ){
		txtName = "consName";
		valName = "consId";
		isConstituencies = true;
	}else if( url.indexOf("constituencyWards") != -1 ){
		txtName = "consWardame";
		valName = "consWardId";
		isWards = true;
	}
	
	$.ajax({
		url: url,
	    data : null,
	    method: "POST",
	    dataType : "json", 
	    contentType : "application/json"
	}).done(function(data) {
		
		data = $.parseJSON(data);
		
		if(data != null){
			$(selector).empty();
			for(var i=0; i<data.length;i++){
				var region = data[i];
				var value = region[valName], text = region[txtName]; 
				$(selector).append('<option value="'+ value +'">'+ text +'</option>');
			}
			
			if( isCounties && !isEdit ){
				onChangeCounty(null, null);
			}else if( isCounties && isEdit ){
				console.log("Swapping for :::::::::::::::::::::::::::::: " + countyId + " : " + selUserData["countyName"] );
				swapForEdit(selector, countyId, selUserData["countyName"]);
				console.log("Loading constituencies for county : " + selUserData["countyName"] );
				onChangeCounty(countyId, consId);
			}else if( isConstituencies && !isEdit ){
				var val = data[0][valName];
				onChangeConstituency(null, null);
			}else if( isConstituencies && isEdit ){
				console.log(JSON.stringify(selUserData));
				console.log("isConstituencies && isEdit");
				console.log("Swapping for :::::::::::::::::::::::::::::: " + consId + " : " + selUserData["consName"] );
				swapForEdit(selector, consId, selUserData["consName"]);
				console.log("Loading wards for constituency : " + selUserData["consName"] );
				onChangeConstituency(consId, consWardId );
			}else if( isWards && isEdit){
				swapForEdit(selector, consWardId, selUserData["consWardName"]);
			}
		}
		
		
	 }).fail(function(data) {
		 notifyError(data.statusText + " (" + data.status + "). Please contact Production Support.");
	 });
	
	return r;
}

function swapForEdit(selector, value, text){
	
	console.log("InParams -------------- " + text + " - " + value);
	
	var selects  = $( selector ).children(), 
		selValue = $( selector + " option:selected" ).val(),
		selText  = $( selector + " option:selected" ).text(),
		index    = 0;
	
	console.log(selText + " - " + selValue);
	
	if( selects != null && selects !== 'undefined' && selects.length != 0 ){
		for(var i=0; i<selects.length; i++){
			var select = selects[i];
			var value_ = $(select).attr("value"); 
			var text_ = $(select).text();
			index = i;
			var output = text_ + " - " + value_ + " index("+ index +") ";
			console.log(output);
			if(value == value_){
				 /* if the index is zero, it's the selected value. Do nothing! */ 
				console.log("Found the record to swap :::::: " + value_ + "   :   " + text_);
				if(index != 0){
					$(select).attr("value", selValue);
					$(select).text(selText);
					$( selector + " option:selected" ).attr("value", value_);
					$( selector + " option:selected" ).text(text_);
					$( selector + " option:nth-child(0)" ).attr("value", value_);
					$( selector + " option:nth-child(0)" ).text(text_);
				}
				break;
			}
		}
	}
}

function bindRegionsDrops(){
	$("#hunterFieldPopupContainer select[data-name='countryInput']").bind("change", function(){ onChangeCountry(null, null); });
	$("#hunterFieldPopupContainer select[data-name='countyInput']").bind("change", function(){ onChangeCounty(null, null); });
	$("#hunterFieldPopupContainer select[data-name='consInput']").bind("change", function(){ onChangeConstituency(null, null); });
}

function updateFileSelected(){
	isFileSelected = true;
}

function submitImportNewContactForm(){
	if(isFileSelected){
		$("#importNewContactForm").submit();
		notifySuccess("Your file is getting uploaded..."); 
		isFileSelected = false;
	}else{
		notifyError("Please select file to upload first."); 
	}
}

/*----------------------------------------------------------------------------------*/

function getSelCountry(){
	return $("#hunterFieldPopupContainer select[data-name='countryInput'] option:selected").val();
}
function getSelCounty(){
	return $("#hunterFieldPopupContainer select[data-name='countyInput'] option:selected").val();;
}
function getSelCons(){
	return $("#hunterFieldPopupContainer select[data-name='consInput'] option:selected").val();
}
function getSelWard(){
	return $("#hunterFieldPopupContainer select[data-name='wardInput'] option:selected").val();
}



function onChangeCountry(selCountry_, contactData){
	
	var r = $.Deferred();
	var isEdit = selCountry_ != null;
	
	$("#hunterFieldPopupContainer select[data-name='countyInput']").empty();
	$("#hunterFieldPopupContainer select[data-name='consInput']").empty();
	$("#hunterFieldPopupContainer select[data-name='wardInput']").empty();
	
	var selCountry = getSelCountry();
	if(selCountry_ != null){
		selCountry = selCountry_;
	}
	
	if(selCountry == null || selCountry === 'undefined' ){
		selCountry = 1; /* Load Kenya by default */
	}
	
	var url = getBaseURL("Hunter/region/action/new/counties/read/" + selCountry);
	getRegionDataAndUpdate("#hunterFieldPopupContainer select[data-name='countyInput']", url, isEdit, contactData);
	
	return r;
}
function onChangeCounty(value, selCons){
	var r = $.Deferred();
	var isEdit = value != null;
	$("#hunterFieldPopupContainer select[data-name='consInput']").empty();
	$("#hunterFieldPopupContainer select[data-name='wardInput']").empty();
	var selCon = getSelCounty();
	if(value != null && value !== 'undefined'){
		selCon = value;
	}
	var url = getBaseURL("Hunter/region/action/new/constituencies/read/" + selCon);
	getRegionDataAndUpdate("#hunterFieldPopupContainer select[data-name='consInput']", url, isEdit, null);
	return r;
}
function onChangeConstituency(value, selWard){
	var r = $.Deferred();
	var isEdit = value != null;
	$("#hunterFieldPopupContainer select[data-name='wardInput']").empty();
	var selCons = getSelCons();
	if(value != null && value !== 'undefined'){
		selCons = value;
	}
	var url = getBaseURL("Hunter/region/action/new/constituencyWards/read/" + selCons);
	getRegionDataAndUpdate("#hunterFieldPopupContainer select[data-name='wardInput']", url, isEdit, null);
	return r;
}


/*----------------------------------------------------------------------------------*/


/*function loadValuesForContactId(contactId){
	var contact = getDataTableJson(contactId);
	selUserRowData = contact;
	loadUserEditData = true;
	onChangeCountry();
}*/

function loadValuesForContactId(contactId){
	
	//called for new contact
	if(contactId == null){
		onChangeCountry(null, null);
		return;
	}
	
	var contact = getDataTableJson(contactId);
	loadUserEditData = true;
	
	console.log("Loaddding for edit.................................");
	console.log(JSON.stringify(contact));
	
	if(contact != null){
		
		var contact_ = contact["receiverContact"];
		var firstName = contact["firstName"];
		var lastName = contact["lastName"];
		
		$("#hunterFieldPopupContainer input[data-name='contactInput']").attr("value",contact_);
		$("#hunterFieldPopupContainer input[data-name='firstName']").attr("value",firstName);
		$("#hunterFieldPopupContainer input[data-name='lastName']").attr("value",lastName);
		
		var countryId = contact["countryId"];
		onChangeCountry(countryId, contact);
		
	}
}

function execTimedFunc(time, funct, data){
	var dataStr = "";
	if(data != null && data.length > 0){
		for(var i=0; i<data.length;i++){
			var datum = data[i];
			dataStr += "'" + datum + "',"; 
		}
		if( dataStr.trim().length > 1 && dataStr.slice(-1) === "," ){
			dataStr = dataStr.substring(0, dataStr.length - 1);
		}
	}
	setTimeout(function(){ 
		var code = funct + "("+ dataStr +")";
		var func = new Function(code);
		func();
	}, time);
}

function testFunction(number,string){
	alert(number + " " + string);
}

function removeProgressSpinner(){
	$("#progressSpinnerHolderSpan").fadeOut(100);
}

function putProgressSpinnerOn(ele,message){
	$(ele).html(progressSpinner + "&nbsp;&nbsp;&nbsp;<span style='font-size:22px;font-weigth:bolder;' >"+ message +"</span>");
}

function deleteContactAndRefresh(conId){
	
	$("#affirmPopupAction").attr("disabled","disabled"); 
	
	putProgressSpinnerOn($("#hunterNotificationMsg"),"Deleting...");
	var userId = {"rawReceiverId" : conId};
	userId = JSON.stringify(userId);
	
	closeHunterPopup1();
	launchRefreshPopup();
	
	$.ajax({
		url: getBaseURL("Hunter/rawReceiver/action/delete/rawReceiver"), 
	    data : userId,
	    method: "POST",
	    dataType : "json", 
	    contentType : "application/json"
	}).done(function(data) {
		data = $.parseJSON(data);
		var message='',status='';
		for(key in data){
			if(key == 'message'){
				message = data[key];
			}else{
				status = data[key];
			}
		}
		if(status == 'Failed')
			status = 'error';
		notifyAndRefresh(message, status);
	 }).fail(function(data) {
		 var message = data.statusText + " (" + data.status + "). Please contact Production Support.";
		 notifyError(message);
	 });
}

function disableActionButtonsInPopup(exception){

}

function removeSpinnerAndCloseWithMesssage(message){
	alert(message);
	$("#hunterNotificationMsg").html(message);
}

function validateEmpty(value){
	return !(value == null || value === 'undefined' || value.trim().length == 0);
}

function validateLength(value, length){
	if(validateEmpty(value)){
		return value.length <= length && value.length >= 1 ;
	}
	return false;
}

function validateEqual(value, compare){
	if(validateEmpty(value)){
		return value == compare;
	}
	return false;
}

function updateInnerTable(selector, message){
	var tr = "<tr class='fieldErrorMessageTr' ><td style='width:90%;' ></td><td style='width:90%;' ><span style='color:red;font-size:12px;' >"+ message +"<span></td></tr>";
	$(selector).after(tr);
}

function getSelector(nextNumb){
	return "#hunterFieldPopupContainer table tr:nth-child(" + nextNumb + ")"; 
}

function hasSpecialCharacters(value){
    var pattern = new RegExp(/[~`!@#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/); 
    if (pattern.test(value)) {
        return false;
    }
   return true;
}

function hasSpeclCharacters(field, name){
	if(!hasSpecialCharacters(field)){
		return name + " cannot contain special  characters!"; 
	}
	return null;
}

function validateFirstOrLastName(name, alias){
	if( name != null && name.trim().length > 0 ){
		if(!hasSpecialCharacters(name)){
			return alias + " can only container numbers and letters.";
		}else if( name != null && name.trim().length > 0 && hasSpecialCharacters(name) && name.trim().length > 50){
			return alias + " cannot exceed 50 characters";
		}
	}else if( name == null || ( name != null && name.trim().length == 0 ) ){
		return alias + " is required!";
	}
	return null;
}

function setValidAndNotify(valid,namMsg){
	if( namMsg != null ){
		notifyError(namMsg);
		return false;
	}
	return valid  == true;
}

function validateEmail(email){
	var re = /\S+@\S+\.\S+/;
	var validEmail =  re.test(email);
	if(!validEmail){
		return "Email is invalid.";
	}
	return null;
}

function getEditedProfileValuesAndSave(){
	
	var firstName 	= $("#hunterFieldPopupContainer input[data-name='firstName']").val(), 
		lastName 	= $("#hunterFieldPopupContainer input[data-name='lastName']").val(),
		phoneNum 	= $("#hunterFieldPopupContainer input[data-name='phoneNumber']").val(),
		emailAdd 	= $("#hunterFieldPopupContainer input[data-name='email']").val(),
		country 	= $("#hunterFieldPopupContainer select[data-name='countryInput']").val(),
		county 		= $("#hunterFieldPopupContainer select[data-name='countyInput']").val(),
		cons 		= $("#hunterFieldPopupContainer select[data-name='consInput']").val(),
		ward 		= $("#hunterFieldPopupContainer select[data-name='wardInput']").val(),
		valid 		= true,
		profileData = {};

	
	var namMsg = validateFirstOrLastName(firstName, "First name");
	valid = setValidAndNotify(valid,namMsg);
	
	namMsg = validateFirstOrLastName(lastName, "Last name");
	valid = setValidAndNotify(valid,namMsg);
	
	namMsg = validateFirstOrLastName(phoneNum, "Phone number");
	valid = setValidAndNotify(valid,namMsg);
		
	namMsg = validateEmail(emailAdd);
	valid = setValidAndNotify(valid,namMsg);
	
	namMsg = isNaN(country) ? "Invalid country Id" : null;
	valid = setValidAndNotify(valid,namMsg);
	
	namMsg = isNaN(county) ? "Invalid county Id" : null;
	valid = setValidAndNotify(valid,namMsg);
	
	namMsg = isNaN(cons) ? "Invalid cons Id" : null;
	valid = setValidAndNotify(valid,namMsg);
	
	namMsg = isNaN(ward) ? "Invalid ward Id" : null;
	valid = setValidAndNotify(valid,namMsg);
	
	
	if(!valid) return;
	
	
	profileData["firstName"] = firstName;
	profileData["lastName"]  = lastName;
	profileData["phoneNum"]  = phoneNum;
	profileData["emailAdd"]  = emailAdd;
	profileData["country"] 	 = country;
	profileData["county"] 	 = county;
	profileData["cons"] 	 = cons;
	profileData["ward"] 	 = ward;
	
	profileData = JSON.stringify(profileData);
	
	notifySuccess("Saving profile data...");
	
	$.ajax({
		url: getBaseURL("Hunter/rawReceiver/action/read/rawProfData"), 
	    data : profileData,
	    method: "POST",
	    dataType : "json", 
	    contentType : "application/json"
	}).done(function(data) {
		data = $.parseJSON(data);
		var message='',status='';
		for(key in data){
			if(key == 'message'){
				message = data[key];
			}else{
				status = data[key];
			}
		}
		if(status == 'Success'){
			notifySuccess(message);
		}else{
			notifyError(message);
		}
		if( isProfileUploaded ){
			uploadProfilePhoto();
		}
		getRawReceiverUsers();
		closeHunterPopup1();
	 }).fail(function(data) {
		 var message = data.statusText + " (" + data.status + "). Please contact Production Support.";
		 notifyError(message);
		 closeHunterPopup1();
	 });
	
	
}

function uploadProfilePhoto(){
	notifySuccess("Uploading profile photo..."); 
	$("#hunterFieldPopupContainer form[data-name='uploadProfilePhotoForm'] button[name='submitEditProfilePhoto']").click();
	closeHunterPopup1();
	launchRefreshPopup();
}

function closeImportStageAndOpenRefreshSec(){
	$("#hunterFieldPopupContainer form[data-name='importNewContactForm']").fadeOut(200,function(){
		$("#hunterFieldPopupContainer table[id='importContainerRefreshTable']").fadeIn(300);
	});
}

function getEditedValuesAndSave(id){
	
	$(".fieldErrorMessageTr").remove();
	
	var conactType 	= 	$("#hunterFieldPopupContainer select[data-name='contactTypeInput'] option:selected").val(),
		contact 	= 	$("#hunterFieldPopupContainer input[data-name='contactInput']").val(),
		firstName 	= 	$("#hunterFieldPopupContainer input[data-name='firstName']").val(),
		lastName 	= 	$("#hunterFieldPopupContainer input[data-name='lastName']").val(),
		country 	= 	$("#hunterFieldPopupContainer select[data-name='countryInput'] option:selected").val(),
		county 		= 	$("#hunterFieldPopupContainer select[data-name='countyInput'] option:selected").val(),
		cons 		= 	$("#hunterFieldPopupContainer select[data-name='consInput'] option:selected").val(),
		ward 		= 	$("#hunterFieldPopupContainer select[data-name='wardInput'] option:selected").val(),
	    json		= {};
	
	console.log("::::::::::::::::::::::::::::::::::::::::::: Ward in edit mode : " + ward );
		
		var valid		= true;
		var nextNumb = 1;	
		
		if( !(validateEqual(conactType, "text") || validateEqual(conactType, "email"))){
			valid = false;
			updateInnerTable( getSelector(nextNumb), "Type must be either email or text" );
			nextNumb++;
		}
		nextNumb++;
		
		if( !validateLength(contact, 13) || isNaN(contact) || !isFinite(contact) || contact.indexOf(".") != -1  || !hasSpecialCharacters(firstName)){ 
			valid = false;
			updateInnerTable( getSelector(nextNumb), "Contact is invalid" );
			nextNumb++;
		}
		nextNumb++;
		
		if( !validateLength(firstName, 50) || !hasSpecialCharacters(firstName) ){ 
			valid = false;
			updateInnerTable( getSelector(nextNumb), "First name must be only numbers and letters between 1-50 characters" );
			nextNumb++;
		}
		nextNumb++;
		
		if( lastName != null && lastName.trim().length > 0 ){
			if(!hasSpecialCharacters(lastName)){
				valid = false;
				updateInnerTable( getSelector(nextNumb), "Last name must contain only letters and numbers" );
				nextNumb++;
			}else if( lastName != null && lastName.trim().length > 0 && hasSpecialCharacters(lastName) && lastName.trim().length > 50){
				valid = false;
				updateInnerTable( getSelector(nextNumb), "Last name cannot exceed 50 characters" );
				nextNumb++;
			}
		}
		nextNumb++;
		
		
		//var table = $('#fieldProfileDataTable').dataTable();
		//console.log(table.fnGetData());
		
		if(!valid){
			notifyError("You entered incorrect data. Please correct it.");
			return;
		}
		
		
		json["id"] 			= id;
		json["conactType"] 	= conactType;
		json["contact"] 	= contact;
		json["firstName"] 	= firstName;
		json["lastName"] 	= lastName;
		json["country"] 	= country;
		json["county"] 		= county;
		json["cons"] 		= cons;
		json["ward"] 		= ward;
		
		json = JSON.stringify(json);
		
		closeHunterPopup1();
		launchRefreshPopup();
		
		$.ajax({
			url: getBaseURL("Hunter/rawReceiver/action/create/rawReceiver"), 
		    data : json,
		    method: "POST",
		    dataType : "json", 
		    contentType : "application/json"
		}).done(function(data) {
			data = $.parseJSON(data);
			var message='',status='';
			for(key in data){
				if(key == 'message'){
					message = data[key];
				}else{
					status = data[key];
				}
			}
			if(status == 'Failed')
				status = 'error';
			notifyAndRefresh(message, status);
		 }).fail(function(data) {
			 var message = data.statusText + " (" + data.status + "). Please contact Production Support.";
			 notifyError(message);
		 });
				
}

function refreshDataTable(){
	setTimeout( function () {
		dataTable.ajax.reload();
	},1000);
}

function notifyAndRefresh(message, type){
	if(type === 'success' ){
		notifySuccess(message);
	}else if(type === 'error'){
		notifyError(message);
	}else if(type === 'warn'){
		notifyWarn(message);
	}else{
		$.notify(message, type);
	}
	notifySuccess("Please wait. Refreshing...");
	setTimeout( function () {
		dataTable.ajax.reload( function ( json ) {
		    closeHunterPopup1();
		} );
	},1500);
}

function launchRefreshPopup(){
	$(".popup-inner").empty();
	$('[data-popup="popup-1"]').fadeIn(100, function(){
		$(".popup-inner").append("<div id='hunterFieldPopupContainer' ></div>"); 
		var html = $("#refreshPopupContainer").html();
		$("#hunterFieldPopupContainer").append(html);
	});
}

function ajaxPostDataForJsonResponseWthCllbck(data, contentType, dataType, method, url, callAfter,callException){
	$.ajax({
		url: url,
	    data : data,
	    method: method,
	    dataType : dataType, 
	    contentType : contentType
	}).done(function(data) {
		var code = callAfter + "('"+ JSON.stringify(data) +"')"; 
		var func = new Function(code);
		func();
	 }).fail(function(data) {
		 var code = callException + "()";   
		 var func = new Function(code);
		 func();
		 notifyError(message, type)(data.statusText + " (" + data.status + "). Please contact Production Support.", "Error");
	 });
}

function notifySuccess(message){
	$.notify(message, "success");
}

function notifyError(message){
	$.notify(message, "error");
}

function notifyWarn(message){
	$.notify(message, "warn");
}

function loadPopup(param){
	$("#hunterFieldPopupContainer").remove();
	$(".popup-inner").append("<div id='hunterFieldPopupContainer' ></div>"); 
	$("#hunterFieldPopupContainer").empty();
	populatePopupForParams(param);
};

function closeHunterPopup1(){
	$('[data-popup="popup-1"]').fadeOut(350,function(){
		$("#hunterFieldPopupContainer").empty();
	});
}

function bindDataTableActionButtons(){
	 //----- OPEN
    $('[data-popup-open]').on('click', function(e)  {
        var targeted_popup_class = jQuery(this).attr('data-popup-open');
        $('[data-popup="' + targeted_popup_class + '"]').fadeIn(800);
        loadPopup($(this).attr("value"));
        e.preventDefault();
    });
 
    //----- CLOSE
    $('[data-popup-close]').on('click', function(e)  {
        var targeted_popup_class = jQuery(this).attr('data-popup-close');
        $('[data-popup="' + targeted_popup_class + '"]').fadeOut(800);
 
        e.preventDefault();
    });
}

function getRawReceiverUsers(){

	var url = getBaseURL("Hunter/rawReceiver/action/read/rawReceiverUser");
	$.ajax({
		url: url,
	    data : null,
	    method: "POST",
	    dataType : "json", 
	    contentType : "application/json"
	}).done(function(data) {
		
		if(data != null){
			
			data = JSON.stringify(data);
			data = $.parseJSON(data);
			
			var fullName = data["fullname"];
			
			$("#fieldUserTotalContacts").	text( data["all"] 		=== 'undefined' ? "" : data["all"] 		);
			$("#fieldUserCountry").			text( data["country"] 	=== 'undefined' ? "" : data["country"] 	);
			$("#fieldUserCounty").			text( data["county"] 	=== 'undefined' ? "" : data["county"] 	);
			$("#fieldUserVerifiedContacts").text( data["verified"] 	=== 'undefined' ? "" : data["verified"] );
			$("#fieldUserWard").			text( data["ward"] 		=== 'undefined' ? "" : data["ward"] 	);
			$("#fieldUserPhone").			text( data["phone"] 	=== 'undefined' ? "" : data["phone"] 	);
			$("#fieldUserEmail").			text( data["email"] 	=== 'undefined' ? "" : data["email"] 	);
			$("#fieldUserConstituency").	text( data["cons"] 		=== 'undefined' ? "" : data["cons"] 	);
			$("#fieldUserFullName").		html( fullName );
			$("#fieldUserTotalPayout").		text( data["compensation"] === 'undefined' ? "" : data["compensation"] );
			
			profileData = data;
			
		}else{
			notifyError("Failed to obtain your profile data");
		}
		
	 }).fail(function(data) {
		 notifyError(data.statusText + " (" + data.status + "). Please contact Production Support.", "Error");
	 });
}



function loadLogout(){
	var local = getBaseURL("Hunter/hunter/login/logout");
	$("#logoutId").attr("href", local);
}

function updateProgressHandler(progressbar, newPercentage, text){
	var val = progressbar.progressbar( "value" ) || 0;
	if(newPercentage == 0 ){
		progressbar.progressbar( "value", 0 );
		return;
	}
	var counter = progressbar.progressbar( "value" ),
		interval = setInterval(function(){
			if(counter != val + newPercentage){
				counter = counter + 1;
				progressbar.progressbar( "value", val + counter );
			}else{
				interval = null;
			}
		}, 25);
}

function launchProgressHandler(progressbar, progressLabel, text){
	
	progressbar.progressbar({
		value: false,
		change: function() {
			progressLabel.text( progressbar.progressbar( "value" ) + "%" );
		},
		complete: function() {
			progressLabel.text( text );
		}
	});
	
	return progressbar;

	/*
	
	function progress() {
		var val = progressbar.progressbar( "value" ) || 0;
	
		progressbar.progressbar( "value", val + 2 );
	
		if ( val < 99 ) {
			setTimeout( progress, 80 );
		}
	}

	setTimeout( progress, 2000 );
	
	*/
	
}



registerNavigation("Groups", "Social Groups");

var kendoKipHelperInstance = new kendoKipHelper();
kendoKipHelperInstance.init();

var SocialGroupModel = kendo.data.Model.define({
	id:"groupId",
	fields : {
		"groupId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"externalId" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"groupName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"groupDescription" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"socialType" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"hunterOwned" : {
			type : "boolean", validation : {required : true},editable:true, defaultValue:false
		},
		"acquired" : {
			type : "boolean", validation : {required : true},editable:true, defaultValue:false
		},
		"active" : {
			type : "boolean", validation : {required : true},editable:true, defaultValue:false
		},
		"status" : {
			type : "string", validation : {required : true},editable:false, defaultValue:'Draft'
		},
		"suspended" : {
			type : "boolean", validation : {required : true},editable:true, defaultValue:false
		},
		"suspensionDescription" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"regionId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:null
		},
		"regionName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"regionPopulation" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"regionDesc" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"regionCountryName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"regionCountyName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"regionConsName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"regionWardName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"regionCoordinates" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"regionAssignedTo" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"verified" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"socialAppId" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"socialAppName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"cretDate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"createdBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"lastUpdate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		}
	},
	getGroupDeleteTemplate : function(){
		
		var currentStatus = this.get("status");
		if(currentStatus === 'Approved'){
			return "";
		}
		
		var 
		groupName = this.get("groupName"),
		id 		  = this.get("groupId"),
		idString  = '"'+ id +'"',
		message   = '"<u><b>'+ groupName +'</u></b> will be deleted. <br/> Are you sure?"',
		onClick   = 'kendoKipHelperInstance.showOKToDeleteItem('+ idString +','+ message +',"SocialGroupsVM.deleteSelSocialGroup")',
		button 	  = kendoKipHelperInstance.createSimpleHunterButton("close",null, onClick);
		return button;
	},
	getGroupEditTemplate : function(){
		
		var currentStatus = this.get("status");
		if(currentStatus === 'Approved'){
			return "";
		}
		
		var groupId = this.get("groupId");
		var button = kendoKipHelperInstance.createSimpleHunterButton("pencil",null, "SocialGroupsVM.showSocialGroupTemplatePopup(\""+ groupId +"\")");
		return button;
	},
	getSocialGroupStatusTemplate : function(){
		var currentStatus = this.get("status");
		if(currentStatus === 'Approved'){
			return "<center><span style='color:#00B655;' >Approved</center></span>";
		}
		var keyStr = this.get("groupId") + ":::" + currentStatus;
		var html = "<center><span style='color:blue;'><a style='cursor:pointer' onClick='SocialGroupsVM.showPopupToChangeGroupStatus(\""+ keyStr +"\")' >"+ currentStatus +"</a></span></center>";
		return html;
	}
});


function getSocialGroupToolBarTemplate(){
	return '<button onClick="SocialGroupsVM.showSocialGroupTemplatePopup(\'newSocialGroup\')" class="k-button" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-i-plus"></span>New Social Group</button>';
}

var SocialGroupsVM = kendo.observable({
	
	isEverVisible 	: true,
	SocialGroupsDS_ : null,
	socialRegions 	: null,
	selSocialGroupTemp : null,
	socialAppsData  : null,
	selSocialGroup	: {
		"groupId"			:0,		"externalId"		:null,	"groupName"			:null, "existent"				:false,
		"groupDescription"	:null,	"socialType"		:null,	"hunterOwned"		:true, "acquiredFromFullName"	:null,
		"acquired"			:false,	"active"			:false,	"suspended"			:false,	"suspensionDescription"	:null,
		"regionId"			:null,	"regionName"		:null,	"regionPopulation"	:0,		"regionDesc"			:null,
		"regionCountryName"	:null,	"regionCountyName"	:null,	"regionCoordinates"	:null,	"regionAssignedTo"		:null,
		"verified"			:false,	"cretDate"			:null,	"createdBy"			:null,	"lastUpdate"			:null,	
		"lastUpdatedBy"		:null,	"socialAppId"		:null,	"socialAppName"		:null
	},
	resetSocialGroupValues : function(){
		this.set("selSocialGroup",this.get("selSocialGroupTemp")); 
	},
	setBooleans : function(){
		
		var  
		group = SocialGroupsVM.get("selSocialGroup"),
		key	  = null,
		value = null;
		
		var 
		keys 	= ["acquired","active","suspended","existent","hunterOwned", "verified"],
		lenght 	= keys.length;
		
		for (var i = 0; i < lenght; i++) {
			key 	= keys[i]; 
			value	= group[key];
			console.log(key + " : " + value);
			$("#socialGroupValidateForm input[data-ref-name = '"+ key +"']").prop("checked", value);
		}
	},
	deleteSelSocialGroup : function(groupdId){
		var 
		data = JSON.stringify({"groupdId":groupdId}),
		url  = HunterConstants.getHunterBaseURL("social/action/groups/destroy");
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "SocialGroupsVM.afterDeletingSocialGroup");
	},
	afterDeletingSocialGroup : function(data){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var dataJson = jQuery.parseJSON(data);
		var status = dataJson["status"]; 
		var message = dataJson["message"];
		if(status != null && message != null){
			if(status !== "Failed"){
				kendoKipHelperInstance.showSuccessNotification(status + " : " + message);
				$("#socialGroupsGrid").data("kendoGrid").dataSource.read();
			}else{
				kendoKipHelperInstance.showErrorNotification(status + " : " + message);
			}
		}
		this.resetSocialGroupValues();
	},
	setSocialGroupBooleans : function(this_){
		
		var 
		key 	= $(this_).attr("data-ref-name"),
		checked = $(this_).prop("checked");
		selSocialGroup =  SocialGroupsVM.get("selSocialGroup"); 
		selSocialGroup[key] = checked;
		
		SocialGroupsVM.set("selSocialGroup", selSocialGroup );
		
	},
	beforeInit : function(){
		this.set("SocialGroupsDS_",SocialGroupsDS);
		this.set("selSocialGroupTemp",this.get("selSocialGroup")); 
		console.log("Getting ready to load receiver groups...");
	},
	init : function(){
		this.beforeInit();
		console.log("Loading social groups...");
		kendo.bind($("#socialGroupsVM"), SocialGroupsVM);
		console.log("Finished initializing social groups VM!");
		this.afterInit();
	},
	afterInit : function(){
		console.log("Successfully finished loading social groups!");
		this.loadSocialRegions();
		this.loadSocialApps();
	},
	loadSocialRegions : function(){
		var url = HunterConstants.getHunterBaseURL("social/action/regions/readSelVal");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "SocialGroupsVM.afterGettingSocialRegions");
	},
	afterGettingSocialRegions : function(data){
		data = $.parseJSON(data);
		SocialGroupsVM.set("socialRegions", data);
	},
	loadSocialApps : function(){
		var url = HunterConstants.getHunterBaseURL("social/action/apps/dropdowns");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "SocialGroupsVM.afterGettingSocialApps");
	},
	afterGettingSocialApps : function(data){
		data = $.parseJSON(data);
		SocialGroupsVM.set("socialAppsData", data);
	},
	showPopupToChangeGroupStatus : function(keyStr){
		
		console.log("Key String >> " + keyStr);
		
		var components = keyStr.split(":::");
		var id = components[0];
		var currentStatus = components[1];
		var includePreview = false;
		var step = "";
		
		if(currentStatus == "Draft"){
			step = "<span><b><u>Review</b></u></span>";
			includePreview = true;
		}else if(currentStatus == "Review"){
			step = "<span><b><u>Approved</b></u></span>";
			includePreview = false;
		}else if(currentStatus == "Approved"){
			step = "<span><b><u>Review</b></u></span>";
			includePreview = true;
		}
		
		var message = "Please click status button to move Task to <b><u>" + step + " </b></u> or back to <b><u>Draft</b></u><br/><br/> ";
		
		var preTab = "<table style='with:50%;margin-left:20%;' ><tr>";
		var toDraft = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);'  class='k-button' onClick=SocialGroupsVM.updateStatusForSelectedTask('Draft:::"+ id +"') >Draft</button></td>";
		var toReview = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class='k-button' onClick=SocialGroupsVM.updateStatusForSelectedTask('Review:::"+ id +"') >Review</button></td>";
		var toApproved = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class='k-button' onClick=SocialGroupsVM.updateStatusForSelectedTask('Approved:::"+ id +"') >Approved</button></td>";
		var cancel = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class='k-button' onClick=kendoKipHelperInstance.closeHelperKendoWindow()>Cancel</button></td>";
		var postTab = "</tr></table>";
		
		var reviewOrApproved = "";
		
		if(includePreview){
			reviewOrApproved = toReview;
		}else{
			reviewOrApproved = toApproved;
		}
		
		var finalStr = message + preTab + toDraft + reviewOrApproved + cancel + postTab;
		
		kendoKipHelperInstance.showPopupWithNoButtons("Change status!", finalStr);
	},
	updateStatusForSelectedTask : function(keyStr){
		
		var url = HunterConstants.getHunterBaseURL("social/action/groups/changeStatus");
		var components = keyStr.split(":::");
		var toStatus = components[0];
		var id = components[1];
		var data = {"groupId" : id, "toStatus" : toStatus}; 
		data = JSON.stringify(data);
		console.log("Current status = " + toStatus);
		console.log("Task Id = " + id);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "SocialGroupsVM.afterChangeSocialStatus");
		kendoKipHelperInstance.closeHelperKendoWindow();
	},
	afterChangeSocialStatus : function(data){
		data = jQuery.parseJSON(data);
		var status = data["status"];
		var message = data["message"];
		if(status == "Failed"){
			message = replaceAll(message, ",", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error updating Task!","<span style='color:red;' >" + message + "</span>");
		}else if(status === "Success"){ 
			kendoKipHelperInstance.popupWarning("Successfully updated task!", "Success");
			$("#socialGroupsGrid").data("kendoGrid").dataSource.read();
		}
	},
	showSocialGroupTemplatePopup : function(argument){
		
		SocialGroupsVM.set("selGroupId", argument === 'newSocialGroup' ?  null : argument );
		
		if( argument != "newSocialGroup" )  {
			var bean = $("#socialGroupsGrid").data("kendoGrid").dataSource.get(argument);
			SocialGroupsVM.set("selSocialGroup", bean);
		}else{
			this.resetSocialGroupValues();
		}
		
		var html = $("#SocialGroupPopupTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(html, "Social Group");
		
		
		/* 
		 * For some reason, data-bind was not picking up the updates to the check boxes 
		 * hence manual update here 
		 */
		if( argument != "newSocialGroup" ){
			this.setBooleans();
		}
		
		kendo.bind($("#SocialGroupPopupCover"), SocialGroupsVM);
		
		
		$("#groupSocialType").kendoDropDownList({
			dataSource		:HunterConstants.SOCIAL_TYPES_ARRAY,
			dataTextField 	: "value",
			valuePrimitive : "true",
			dataValueField 	: "text",
			optionLabel 	: "Select Social Type",
		});
		
		$("#groupSocialRegion").kendoComboBox({
             dataTextField	: "text",
             dataValueField	: "value",
             valuePrimitive : "true",
             placeholder	: "Select Social Region",
             dataSource		: SocialGroupsVM.get("socialRegions")
        });
		
		$("#socialAppId").kendoComboBox({
            dataTextField	: "text",
            dataValueField	: "value",
            valuePrimitive 	: "true",
            placeholder		: "Select Social App",
            dataSource	   	: SocialGroupsVM.get("socialAppsData"),
            change	   		: function(){
            	var value = this.text();
            	SocialGroupsVM.get("socialAppsData").socialAppName = value;
            }
       });
		
		$("#groupReceiversCount").kendoNumericTextBox({
			 value 		: 1,
			 min		: 0,
			 decimals	: false,
			 max 		: 1000000,
			 decimals 	: 0
		 }).data("kendoNumericTextBox"); 
		
		
		this.setBooleans();
		
		
	},
	afterCreateNewSocialGroup : function(data){
		kendoKipHelperInstance.closeWindowWithOnClose();
		var dataJson = jQuery.parseJSON(data);
		var status = dataJson["status"]; 
		var message = dataJson["message"];
		if(status != null && message != null){
			if(status !== "Failed"){
				kendoKipHelperInstance.showSuccessNotification(status + " : " + message);
				$("#socialGroupsGrid").data("kendoGrid").dataSource.read();
			}else{
				kendoKipHelperInstance.showErrorNotification(status + " : " + message);
			}
		}
		this.resetSocialGroupValues();
	},
	createNewSocialGroup : function(){
		var 
		url 	= HunterConstants.getHunterBaseURL("social/action/groups/create"),
		after 	= "SocialGroupsVM.afterCreateNewSocialGroup",
		
		valuesStr = JSON.stringify( SocialGroupsVM.get("selSocialGroup") );
		console.log( valuesStr );
		kendo.ui.progress($("#SocialGroupPopupCover"), true);
		
		setTimeout(function(){
			kendoKipHelperInstance.ajaxPostData(valuesStr, "application/json", "json", "POST", url , after );
		}, 1500);
	},
	validateAndUpdateGroup : function(){
		
		var validator = $("#socialGroupValidateForm").kendoValidator({
			messages:{
				custom : function(input){
					var name	= input.attr("name");
					console.log( name );
					if( name == "groupSocialType" || name == "groupSocialRegion" ){
		        		   return "Group field is required." ;
		        	   } 
				},
				length : function(input){
					var name = $(input).attr("name");
		             if ( name === "groupName" ) {
		            	 return "Group name is required and is less than 50 characters.";
		             }else if ( name === "groupDescription" ) {
		            	 return "Group description is required and is less than 100 characters.";
		             }else if ( name == "suspensionDescription" ){
		            	 return "Suspension description is required and is less than 2000 characters.";
		             }else if ( name == "groupAcquiredFrom" ){
		            	 return "Acquired from name cannot must be less than 50 characters.";
		             }
				},
				specialCharacters : function(input){
					return "Only alphanumeric,comma,colon,semi colon and period are allowed!";
				},
				required : "Required field!"
			},
			rules: {
	           custom: function(input) {
	        	   
	        	   var 
	        	    value 	= input.val(), 
	        	    name	= input.attr("name");
	        	   
	        	   if( name == "groupSocialType" || name == "groupSocialRegion"  ){
	        		   return !( value == null || value === "undefined" || value.trim().length == 0 ); 
	        	   } 

	        	   return true;
	           },
	           length : function(input){
	        	   	
	        	    var 
	        	    value 	= input.val(), 
	        	    name	= input.attr("name"); 
		        	
		            if ( name === "groupName" || name === "groupDescription" ) { 
		            	return !( value == null || value.trim().length == 0 || value.trim().length >= 50 ) ;
		            }else if( name === "suspensionDescription" ){
		            	return !( value.length > 2000 );
		            }else if( name === "groupAcquiredFrom" ){
		            	return !( value.length > 50 );
		            }
		            
		            return true;
	           },
	           specialCharacters : function(input){
	        	   
	        	   var 
	        	   name = $(input).attr("name"),
	        	   value = input.val(),
	        	   pass =  new RegExp(/^[a-zA-Z0-9. ,:;]+$/).test( value ),
	        	   objctv =  new RegExp(/^[a-zA-Z0-9. '",:;]+$/).test( value );
	        	   
				  if( name == "groupName" || name == "groupDescription"){ 
					return pass;
				  }else if( name === "groupAcquiredFrom" || name == "suspensionDescription" ){
					  if( value == null || value === "undefined" || value.trim() === "" ){
						  return true;
					  }
					  return objctv;
				  }
					
				  return true;
	           }
	         }
		}).data("kendoValidator"); 
		
		var isValid = validator.validate();
		
		if( isValid ){
			this.createNewSocialGroup();
		}
		
	}
});





var SocialGroupsDS = new kendo.data.DataSource({
	height:650,
	  transport: {
	    read:  {
	      url: HunterConstants.getHunterBaseURL("social/action/groups/read"), 
	      dataType: "json",
	      contentType:"application/json",
	      method: "POST"
	    },
	    create: {
	        url: HunterConstants.getHunterBaseURL("social/action/groups/create"),
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    update: {
	        url: HunterConstants.getHunterBaseURL("social/action/groups/edit"),
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    destroy: {
	        url: HunterConstants.getHunterBaseURL("social/action/groups/destroy"),
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    parameterMap: function(options, operation) {
	          if (operation == "read") {
	        	  console.log("Reading data...");
	          }else if(operation == "destroy"){ 
	        	  var json = kendo.stringify(options);
	        	  var id = json.id;
	        	  console.log("id >> " + id);
	        	  return json;
	          }else if(operation == 'create' || operation == 'update'){
	        	  console.log(JSON.stringify(options.operation)); 
	        	  var JSON_ = jQuery.parseJSON(JSON.stringify(options));
	        	  console.log(JSON.stringify(JSON_));
	        	  delete JSON_['edit'];
	        	  delete JSON_['delete'];
	        	  delete JSON_['download'];
	        	  delete JSON_['importButton'];
	        	  var json = kendo.stringify(JSON_);
	        	  console.log(json);
	        	  return json;
	          }
	    }
	  },
	  schema: {
	    	model:SocialGroupModel
	  },
	  error: function (e) {
	        kendoKipHelperInstance.popupWarning(e.status, e.errorThrown, "Error");
	  },
	  requestStart: function(e) {
	        var type = e.type;
	        var message = "";
	        if(type === 'read')
	        	return;
	        if(type === 'update')
	        	message = "Updating record...";
	        if(type === 'destroy')
	        	message = "Deleting record...";
	        if(type === 'create')
	        	message = "Creating record...";
	        kendoKipHelperInstance.popupWarning(message, "Success");
	  },
	  requestEnd: function(e) {
	        var response = e.response;
	        var type = e.type;
	        var message = null;
	        /*if(type === 'read')
	        	if(response !== 'undefined' && response != null){
	        		message = "Successfully obtained ( "+ response.length +" ) records!";
	        	}else{
	        		return;
	        	}*/
	        if(type === 'update')
	        	message = "Successfully updated record!";
	        if(type === 'destroy')
	        	message = "Successfully deleted record!";
	        if(type === 'create')
	        	message = "Successfully created record!";
	        
	        if(message != null){
	        	kendoKipHelperInstance.popupWarning(message, "Success");
	        }
	  },
	  pageSize:1000
});


$("document").ready(function(){
	SocialGroupsVM.init();
});


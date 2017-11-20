
registerNavigation("Groups", "Receiver Groups");

var kendoKipHelperInstance = new kendoKipHelper();
kendoKipHelperInstance.init();

var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

var ReceiverGroupModel = kendo.data.Model.define({
	id:"groupId",
	fields : {
		"groupId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"receiverCount" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"ownerUserName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"firstName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"lastName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"groupName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"receiverType" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"groupDesc" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
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
		},
		"delete" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"edit" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"importButton" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"download" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		}
	},
	getGroupDeleteTemplate : function(){
		var groupName = this.get("groupName");
		var id = this.get("groupId");
		var idString = '"'+ id +'",';
		var message = '"<u><b>'+ groupName +'</u></b> will be deleted. <br/> Are you sure?"';
		var html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKCancelToDeleteReceiverGroup('+  idString + message+ ')');
		return html;
	},
	getGroupEditTemplate : function(){
		var html = kendoKipHelperInstance.createContextEditButton(false);
		return html;
	},
	groupEditPopupTemplate : function(){
		var html = $("#groupEditPopupTemplate").html();
		return html;
	},
	getReceiverImportButton : function(){
		var groupId = this.get("groupId");
		//var html = "<center><span class='k-icon k-i-seek-n'></span><a style='color:#0038D2;cursor:pointer' onClick='receiverGroupVM.openImportSection(\""+ groupId +"\")'>import</a></center>";
		var button = kendoKipHelperInstance.createSimpleHunterButton("seek-n",null, "receiverGroupVM.openKendoImportSection(\""+ groupId +"\")");
		return button;
	},
	getImportFilesTemplate : function(){
		var groupId = this.get("groupId");
		//var html = "<center><span class='k-icon k-i-seek-s'></span><a style='color:#0038D2;cursor:pointer' onClick='receiverGroupVM.ajaxPostGetImportData(\""+ groupId +"\")'>download</a></center>";
		//var b1 = kendoKipHelperInstance.createKendoSmallIconTagTemplate("close", 'receiverGroupVM.ajaxPostGetImportData("' + groupId +'")', null);
		var button = kendoKipHelperInstance.createSimpleHunterButton("seek-s",null, 'receiverGroupVM.ajaxPostGetImportData("' + groupId +'")');
		return button;
	},
	getViewReceiversButton : function(){
		var params = "RECEIVER_GROUP_ID::" + this.get("groupId") + "::" + this.get("receiverType"),
			button = kendoKipHelperInstance.createSimpleHunterButton("search",null, 'receiverGroupVM.displayContactsForGroupId("' + params +'")');
		return button;
	}
});

function getGroupEditTemplate(){
	var html = kendoKipHelperInstance.createContextEditButton(false);
	return html;
}

var ReceiverGroupDS = new kendo.data.DataSource({
	height:650,
	  transport: {
	    read:  {
	      url: baseUrl + "messageReceiver/action/group/read",
	      dataType: "json",
	      contentType:"application/json",
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "messageReceiver/action/group/create",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            //kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    update: {
	        url: baseUrl + "messageReceiver/action/group/update",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    destroy: {
	        url: baseUrl + "messageReceiver/action/group/destroy",
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
	    	model:ReceiverGroupModel
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

var receiverGroupVM = kendo.observable({
	
	isEverVisible : true,
	receiverGroupDS_ : null,
	importInitialized : false,
	clientDetailsData : false,
	selGroupId : null,
	
	beforeInit : function(){
		this.set("receiverGroupDS_",ReceiverGroupDS);
		console.log("Getting ready to load receiver groups...");
	},
	init : function(){
		this.beforeInit();
		console.log("Loading receiver groups...");
		kendo.bind($("#receiverGroupGrid"), receiverGroupVM);
		console.log("Finished initializing receiver groups VM!");
		this.afterInit();
		var html = kendoKipHelperInstance.createSimpleTable(null,null);
	},
	afterInit : function(){
		this.populateClientDetailsData();
		console.log("Successfully finished loading receiver groups!");
	},
	populateClientDetailsData : function(){
		var url = baseUrl + "/hunteruser/action/client/getAllClientsDetails";
		kendoKipHelperInstance.ajaxPostDataForJsonResponse("{}", "application/json", "json", "POST",url,"receiverGroupVM.afterPopulateClientDetailsData");
	},
	afterPopulateClientDetailsData : function(data){
		var json = jQuery.parseJSON(data);
		receiverGroupVM.set("clientDetailsData", json);
		console.log("Successfully populated client details data!!"); 
	},
	deleteSelectedGroup : function(id){
		kendoKipHelperInstance.closeHelperKendoWindow(); 
		console.log("Group Id : " + id); 
		var model = this.get("receiverGroupDS_").get(id);
		model = JSON.stringify(model);
		var url = baseUrl + "/messageReceiver/action/group/destroy";
		kendoKipHelperInstance.ajaxPostData(model, "application/json", "json", "POST",url,"receiverGroupVM.callAfter");
	},
	callAfter : function(data){
		var failed = false;
		if(data != null){
			data = jQuery.parseJSON(data);
			var status = data["status"]; 
			failed = status === HunterConstants.STATUS_FAILED;
			var message = data["message"];
			message = "<div style='with:98%;margin-left:1%;color:red;' >"+ status + " : " + message +"</div>";
			kendoKipHelperInstance.showSimplePopup("Failed To Delete", message);
			//kendoKipHelperInstance.showErrorOrSuccessMsg(status, message);
		}
		if(!failed){
			this.get("receiverGroupDS_").read();
		}
	},
	editorButtonEdit : function(container, options){
		var html = kendoKipHelperInstance.createDisabledContextEditButton(false);
		console.log(html);
		$(html).appendTo(container);
	},
	openKendoImportSection : function(groupId){
		kendo.destroy( $("#receiverGroupInput"));
		kendo.destroy($("#importSectionTemplateCover"));
		$("#importSectionTemplateCover").empty();
		var content = $("#importSectionTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Upload file");
		var url = baseUrl + "/messageReceiver/action/group/import/receiverGroupReceivers/" + groupId;
		 $("#receiverGroupInput").kendoUpload({
		        async: { 
		            saveUrl: url,
		            removeUrl: "remove",
		            autoUpload: false,
		            multiple: true,
		            error : onError
		        }
		  });
	},
	openImportSection : function(groupId){
		kendo.destroy( $("#receiverGroupInput"));
		$("#kendoUploadContainer").empty();
		$("#kendoUploadContainer").html('<input type="file" name="select import file" id="receiverGroupInput" />');
		var url = baseUrl + "/messageReceiver/action/group/import/receiverGroupReceivers/" + groupId;
		 $("#receiverGroupInput").kendoUpload({
		        async: { 
		            saveUrl: url,
		            removeUrl: "remove",
		            autoUpload: false,
		            multiple: false,
		            error : onError
		        }
		  });
		 $("#importSectionTemplateCover").css(
				 {
					 'position':'fixed', 
					 'z-index':'999999999',
					 'with':'80%',
					 'margin-left':'45%',
					 'display':''
				 }
		 );
		 $("#importSectionTemplateCover").animate(
			{'margin-top' : '-450px'},500
		  );
		 
	},
	ajaxPostGetImportData : function(groupId){
		this.set("selGroupId", groupId);
		var data = {"groupId" : groupId};
		data = JSON.stringify(data);
		var url = baseUrl + "/messageReceiver/action/group/getImportDetails";
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST",url,"receiverGroupVM.afterAjaxPostGetImportData");
	},
	afterAjaxPostGetImportData : function(data){
		var json = $.parseJSON(data);
		var status = json[HunterConstants.STATUS_STRING];
		if(status != null){
			kendoKipHelperInstance.showErrorNotification(status  + " : " + json[HunterConstants.MESSAGE_STRING]);
		}else{
			var dataHtml = this.fetchImportFilesData(json);
			//this.openDownloadSection(dataHtml);
			kendoKipHelperInstance.showPercentMeasuredOKCancelTitledPopup(dataHtml, "Import Files Data", "35", "35");
			$("#importFilesTrHeader td").css({
				'height':'20px',
				'background-color':'#233F40',
				'border':'1px solid #233F40',
				'border-radius':'2px',
				'padding':'5px',
				'font-weight':'bolder',
				'color' : 'white'
			});
		}
	},
	fetchImportFilesData : function(data){
		var firstHeader = '<div><table style="width:100%">';
		var header = '<tr id="importFilesTrHeader" ><td></td><td>Created By</td><td>File Name</td><td>Creation Date</td><td>Status</td></tr>';
		var body = firstHeader + header;
		for(var i=0; i<data.length; i++){
			var datum = data[i];
			var importId = datum["importId"];
			var numbering = "<td style='color:red;font-weight:bolder;font-size:12px;' >"+ (i+1) +"</td>";
			var fullName = "<td>"+ datum["fullName"] +"</td>";
			var URL = this.getLinkUrl(importId);
			var fileName = "<td><a target='_blank' href='"+ URL +"' ><u><span style='cursor:pointer;' >"+ datum["originalFileName"] +"<span></u></a></td>"; 
			var creationDate = "<td>"+ datum["creationDate"] +"</td>";
			var status = "<td>" + this.getStatusStyle(datum["status"]) + "</td>";
			var tr = "<tr>"+ numbering + fullName + fileName + creationDate + status +"</tr>";
			body += tr;
		}
		var lastHeader = "</table></div>";
		body += lastHeader;
		var button = kendoKipHelperInstance.createSimpleHunterButton("close","Close", "kendoKipHelperInstance.closeMeasuredWindow()");
		body += "<table style='with:400px;margin-left:40%;tex-align:center;margin-top:10px;' ><tr><td>"+ button +"</td></tr></table>";
		return body;
	},
	openDownloadSection : function(dataHtml){
		$("#downloadImportFilesTableContainer").empty();
		$("#downloadImportFilesTableContainer").append(dataHtml);
		$("#importFilesTrHeader").css({
			'background-color':'#233F40',
			'color':'white',
			'padding':'10px'
		});
		$("#importFilesTrHeader td").css({
			'height':'20px',
			'background-color':'#233F40',
			'border':'1px solid #233F40',
			'border-radius':'2px',
			'padding':'5px',
			'font-weight':'bolder'
		});
		 $("#downloadImportSectionTemplateCover").css({'position':'fixed','z-index':'999999999','with':'80%','margin-left':'32%','display':'' });
		 $("#downloadImportSectionTemplateCover").animate(
			{'margin-top' : '-300px'},1000
		  );
	},
	getStatusStyle : function(status){
		if(status == "Success"){ 
			return "<span style='color:green;' >Success</span>";
		}else{
			return "<span style='color:red;' >Failed</span>";
		}
	},
	getLinkUrl : function(importId){
		var extension = null;
		var url = baseUrl + "/messageReceiver/action/group/";
		if(importId != null && importId == "allReceivers"){
			var groupId = this.get("selGroupId");
			extension = "downloadAllReceivers/" + groupId;
		}else{
			extension = "downloadGroupImportBean/" + importId;
		}
		url = url + extension;
		return url;
	},
	closeDownloadSection : function(){
		$("#downloadImportSectionTemplateCover").fadeOut(100);
	},
	closeImportSection : function(){
		$("#importSectionTemplateCover").fadeOut(100);
		ReceiverGroupDS.read();
	},
	prepareStageToShowContacts : function(params){
		var content = $("#selRegionMessgReceiverGridTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Receivers of regions params : " + params);
		$("#selRegionMessgReceiverGridContainer").css({"height":"500px"});
		kendo.ui.progress($("#contactsSpinner"), true);
		setTimeout(function(){
			kendoKipHelperInstance.showReceiversGridForRegionOrGroup(params);
		}, 800);
	},
	displayContactsForGroupId : function(params){
		console.log( params );
		this.prepareStageToShowContacts(params);
	}
	
});

function editorButtonEdit(container, options){
	var update = kendoKipHelperInstance.createEditButtonsEditMode(false);
	container.append(update);
}

function onError(e) {
	var err = e.XMLHttpRequest.responseText;
    alert(err);
}

function receiverTypeEditor(container, options){
     var input = $('<input name="' + options.field + '" required="required" />');
     input.appendTo(container);
     input.kendoDropDownList({
         dataTextField: "text",
         dataValueField: "value",
         valuePrimitive: true,
         dataSource: HunterConstants.RECEIVER_TYP_ARRAY,
         index: 0,
     });
}

function userNameEditor(container, options){
    var input = $('<input name="' + options.field + '" required="required" />');
    input.appendTo(container);
    input.kendoDropDownList({
        dataTextField: "clientText",
        dataValueField: "userName",
        valuePrimitive: true,
        dataSource: receiverGroupVM.get("clientDetailsData"), 
        index: 0,
    });
}








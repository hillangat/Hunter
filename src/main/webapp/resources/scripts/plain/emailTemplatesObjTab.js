
registerNavigation("Admin Main", "Email Template Object");
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

var codeMirrorEditor = null;

var EmailTemplateObjModel = kendo.data.Model.define({
	id:"templateId",
	fields : {
		"templateId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
		},
		"templateName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"templateDescription" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"status" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"Draft"
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
	getDelTemplate : function(){
		
		var currentStatus = this.get("status");
		if( currentStatus !== 'Draft' )
			return "";	
		
		var id = this.get("templateId"),
			shortName = this.get("templateName"),
			idString = '"'+ id +'",',
			message = '"<u><b>'+ shortName +'</u></b> will be deleted. <br/> Are you sure?"', 
			html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKToDeleteItem('+  idString + message + ',"EmailTemplateObjVM.deleteEmailTemplateObj"' +  ')');

		return html;
	},
	getEditTemplate : function(){
		
		var currentStatus = this.get("status");
		if( currentStatus !== 'Draft' )
			return "";	
		
		return kendoKipHelperInstance.createContextEditButton(false);
	},
	getXMLEditorButton : function(){
		var templateId = this.get("templateId"); 
		var button = kendoKipHelperInstance.createSimpleHunterButton("custom",null, "EmailTemplateObjVM.launchXMLEditor(\""+ templateId +"\")");
		return button;
	},
	getViewTemplate : function(){
		var templateId = this.get("templateId"); 
		var button = kendoKipHelperInstance.createSimpleHunterButton("search",null, "EmailTemplateObjVM.launchViewTemplate(\""+ templateId +"\")");
		return button;
	},
	getMetadataXMLTemplate : function(){
		var templateId = this.get("templateId"); 
		var button = kendoKipHelperInstance.createSimpleHunterButton("note",null, "EmailTemplateObjVM.launchMetadataXMLEditor(\""+ templateId +"\")");
		return button;
	},
	getStatusTemplate : function(){
		var currentStatus = this.get("status");
		if(currentStatus === 'Approved'){
			return "<center><span style='color:#00B655;' >Approved</center></span>";
		}
		var keyStr = this.get("templateId") + ":::" + currentStatus;
		var html = "<center><span style='color:blue;'><a style='cursor:pointer' onClick='EmailTemplateObjVM.showPopupToChangeTemplateStatus(\""+ keyStr +"\")' >"+ currentStatus +"</a></span></center>";
		return html;
	}
});
	
var EmailTemplateObjDS = new kendo.data.DataSource({
	  transport: {
	    read:  {
	      url: baseUrl + "admin/action/emailTemplateObj/read",
	      dataType: "json",
	      contentType:"application/json",
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "admin/action/emailTemplateObj/create",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    destroy: {
	        url: baseUrl + "admin/action/emailTemplateObj/delete",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    update: {
	        url: baseUrl + "admin/action/emailTemplateObj/update",
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
	          }else{
	        	  var json = kendo.stringify(options); 
	        	  return json;
	          }
	    }
	  },
	  schema: {
	    	model:EmailTemplateObjModel
	  },
	  error: function (e) {
	        kendoKipHelperInstance.popupWarning(e.status, e.errorThrown, "Error");
	  },
	  requestStart: function(e) {
	        var type = e.type;
	        var message = null;
	        if(type === 'read')
	        	return;
	        if(type === 'update')
	        	message = "Updating record...";
	        if(type === 'destroy')
	        	message = "Deleting record...";
	        if(type === 'create')
	        	message = "Creating record...";
	        
	        if(message != null){
	        	kendoKipHelperInstance.popupWarning(message, "Success");
	        }
	        
	  },
	  requestEnd: function(e) {
	        //var response = e.response;
	        var type = e.type;
	        var message = null;
	        if(type === 'update')
	        	message = "Successfully updated record!";
	        if(type === 'destroy')
	        	message = "Successfully deleted record!";
	       /* if(type === 'create')
	        	message = "Successfully created record!";*/
	        if(message != null){
	        	kendoKipHelperInstance.popupWarning(message, "Success");
	        }
	  },
	  pageSize:100
});

var EmailTemplateObjVM = kendo.observable({
	
	emailTemplateObjDS : EmailTemplateObjDS,
	isEverVisible : true,
	currEditedTemplateId : null,
	codeMirrorEditor : null,
	operationType : null,
	
	beforeInit : function(){
		console.log("Before initializing email template VM..."); 
	},
	init : function(){
		console.log("Initializing email template VM...");
		kendo.bind($("#emailTemplateObjContainer"), EmailTemplateObjVM);
	},
	afterInit : function(){
		console.log("Finishing up initializing email template VM...");
	},
	deleteEmailTemplateObj : function(id){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var data = {"templateId" : id};
		data = JSON.stringify(data);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", HunterConstants.getHunterBaseURL("admin/action/emailTemplateObj/delete")  , "EmailTemplateObjVM.afterDeleteEmailTemplateObj");
	},
	afterDeleteEmailTemplateObj : function(data){
		data = $.parseJSON(data);
    	if(data != null){
    		var status = data.status;
    		var message = replaceAll(data.message, ",", "<br/>");
    		if( status != null && HunterConstants.STATUS_SUCCESS === status ){
    			EmailTemplateObjVM.get("emailTemplateObjDS").read(); 
    			kendoKipHelperInstance.showSuccessNotification("Successfully deleted email template", "<span style='color:green' >"+ message +"</span>");
    		}else{
    			kendoKipHelperInstance.showSimplePopup("Error deleting email template", "<span style='color:red' >"+ message +"</span>");
    		}
    	}
	},
	launchXMLEditor : function(templateId){
		EmailTemplateObjVM.set("operationType", "Template");
		EmailTemplateObjVM.set("currEditedTemplateId", templateId);
		var content = $("#xmlEditorTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Template XML Editor");
		this.disableSaveHTMLButtonForDraf(templateId);
		kendo.ui.progress( $("#xmlEditorTemplateContainer") , true);
		setTimeout(function(){
			var url = HunterConstants.getHunterBaseURL("admin/action/emailTemplateObj/loadTemplates/" + templateId);
			$( "#xmlEditorTemplateText" ).load( url , function(response, status, xhr) {
	    		kendo.ui.progress( $("#xmlEditorTemplateContainer") , false);
	    		if ( status == "error" ) {
      			    var msg = "Error ( "+ xhr.status + " :  " + xhr.statusText +" ) : ";
      			    EmailTemplateObjVM.setTitleMessage(false, msg );
      			 }else{
      				var tskGridDs = EmailTemplateObjVM.get("emailTemplateObjDS");
      				var name = tskGridDs.get(templateId).get("templateName");
      				EmailTemplateObjVM.setTitleMessage(true,name );
      			 }
	    		codeMirrorEditor = CodeMirror.fromTextArea(document.getElementById("xmlEditorTemplateText"), {
	    	        mode: "text/html",
	    	        height:"100%",
	    	        width:"100%",
	    	        "overflow-y":"scroll",
	    	        lineNumbers: true
	    	      });
	    		CodeMirror.commands["selectAll"](codeMirrorEditor);
	    		 var totalLines = codeMirrorEditor.lineCount();
	    		 var totalChars = codeMirrorEditor.getTextArea().value.length;
	    		 codeMirrorEditor.autoFormatRange({line:0, ch:0}, {line:totalLines, ch:totalChars});
	    		 $("div[class='CodeMirror cm-s-default']").css({
	    			 "height":"100%","width":"100%","word-break":"break-all"
	    		 });
	    		 $("div[class='CodeMirror-code']").css({
	    			 "height":"100%","width":"100%","overflow":"auto","word-break":"break-all"
	    		 });
	    		 $("div[class='CodeMirror-sizer']").css({
	    			 "height":"100%","width":"100%","overflow":"auto","word-break":"break-all"
	    		 });
	    	});
		}, 500);
	},
	setTitleMessage : function(isSuccess, message){
		var color = isSuccess ? "green" : "red";
		var messageStr = "<span style='color:"+ color +";'>"+ message +"</span>";
		kendoKipHelperInstance.setValueToWithOnCloseTitle(messageStr);
	},
	saveHtmlValue : function(){
		
		var operationType = EmailTemplateObjVM.get("operationType"),
			actionName = null;
		
		if( operationType === 'Meta' ){
			actionName = "saveTemplateMetaDataFile";
		}else if( operationType === 'Template' ){
			actionName = "saveTemplateFile";
		}else{
			kendoKipHelperInstance.showErrorNotification( "No operation found to invoke on 'Save'!!" );
			return;
		}
		
		kendo.ui.progress( $("#xmlEditorTemplateContainer") , true);
		var templateId = EmailTemplateObjVM.get("currEditedTemplateId");
		codeMirrorEditor.save();
        
		var content = codeMirrorEditor.getValue(),
        	data = JSON.stringify( {"content":content} ),
        	url = HunterConstants.getHunterBaseURL("admin/action/emailTemplateObj/"+ actionName +"/" + templateId );
		
		setTimeout(function(){
	        kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "EmailTemplateObjVM.afterSavingTemplateFile");
		}, 1000);
		
		
	},
	afterSavingTemplateFile : function(data){
		
		kendo.ui.progress( $("#xmlEditorTemplateContainer") , false);
		kendoKipHelperInstance.closeWindowWithOnClose();
		
		
		data = jQuery.parseJSON(data);
		var status = data["status"];
		var message = data["message"];
		if(status == "Failed"){
			message = replaceAll(message, ",", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error updating Task!","<span style='color:red;' >" + message + "</span>");
		}else if(status === "Success"){ 
			kendoKipHelperInstance.popupWarning("Successfully updated task!", "Success");
			var tskGridDs = EmailTemplateObjVM.get("emailTemplateObjDS");
			tskGridDs.read();
		}
	},
	getSelectedRange :function() {
        return { from: codeMirrorEditor.getCursor(true), to: codeMirrorEditor.getCursor(false) };
    },
    autoFormatSelection: function() {
    	alert( "Formating..." );
        var range = getSelectedRange();
        alert( range );
        codeMirrorEditor.autoFormatRange(range.from, range.to);
    },
    /* We are reusing task email preview here. 
     * Please don't let the naming confuse you. 
     * */
    launchViewTemplate : function(templateId){
    	var url = HunterConstants.getHunterBaseURL("admin/action/emailTemplateObj/getTemplatePreview/" + templateId),
    		windowCover = $("#taskEmailPreviewWindowTemplate").html();
    	var objName = $("#emailTemplateObjGrid").data("kendoGrid").dataSource.get( templateId ).get("templateName");
    	kendoKipHelperInstance.showWindowWithOnClose(windowCover, objName);
    	EmailTemplateObjVM.setTitleMessage(true, objName);
    	setTimeout(function(){
    		$( "#taskEmailPreviewContent" ).load( url, function(response, status, xhr) {
      		  if ( status == "error" ) {
      			    var msg = "Error loading email for selected task : ";
      			    $( "#taskEmailPreviewContent" ).html( "<h2 style='margin-top:35%;color:red;' >" + msg  + "   ( " + xhr.status + " :  " + xhr.statusText + " )</h2>");
      			  }
      	});
    	}, 800);   	
    },
    launchMetadataXMLEditor : function(templateId){
    	EmailTemplateObjVM.set("operationType", "Meta");
    	EmailTemplateObjVM.set("currEditedTemplateId", templateId);
		var content = $("#xmlEditorTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Template XML Editor");
		this.disableSaveHTMLButtonForDraf(templateId);
		kendo.ui.progress( $("#xmlEditorTemplateContainer") , true);
		setTimeout(function(){
			var url = HunterConstants.getHunterBaseURL("admin/action/emailTemplateObj/loadTemplateMetaData/" + templateId);
			$( "#xmlEditorTemplateText" ).load( url , function(response, status, xhr) {
	    		kendo.ui.progress( $("#xmlEditorTemplateContainer") , false);
	    		if ( status == "error" ) {
      			    var msg = "Error ( "+ xhr.status + " :  " + xhr.statusText +" ) : ";
      			    EmailTemplateObjVM.setTitleMessage(false, msg );
      			 }else{
      				var tskGridDs = EmailTemplateObjVM.get("emailTemplateObjDS");
      				var name = tskGridDs.get(templateId).get("templateName");
      				EmailTemplateObjVM.setTitleMessage(true,name );
      			 }
	    		codeMirrorEditor = CodeMirror.fromTextArea(document.getElementById("xmlEditorTemplateText"), {
	    			mode: {name: "xmlpure"},
	    	        height:"100%",
	    	        width:"100%",
	    	        "overflow-y":"scroll",
	    	        lineNumbers: true
	    	      });
	    		CodeMirror.commands["selectAll"](codeMirrorEditor);
	    		 var totalLines = codeMirrorEditor.lineCount();
	    		 var totalChars = codeMirrorEditor.getTextArea().value.length;
	    		 codeMirrorEditor.autoFormatRange({line:0, ch:0}, {line:totalLines, ch:totalChars});
	    		 $("div[class='CodeMirror cm-s-default']").css({
	    			 "height":"100%","width":"100%","word-break":"break-all"
	    		 });
	    		 $("div[class='CodeMirror-code']").css({
	    			 "height":"100%","width":"100%","overflow":"auto","word-break":"break-all"
	    		 });
	    		 $("div[class='CodeMirror-sizer']").css({
	    			 "height":"100%","width":"100%","overflow":"auto","word-break":"break-all"
	    		 });
	    	});
		}, 500);
    },
    showPopupToChangeTemplateStatus : function(keyStr){
    	
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
		var toDraft = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);'  class='k-button' onClick=EmailTemplateObjVM.updateStatusForSelectedTemplate('Draft:::"+ id +"') >Draft</button></td>";
		var toReview = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class='k-button' onClick=EmailTemplateObjVM.updateStatusForSelectedTemplate('Review:::"+ id +"') >Review</button></td>";
		var toApproved = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class='k-button' onClick=EmailTemplateObjVM.updateStatusForSelectedTemplate('Approved:::"+ id +"') >Approved</button></td>";
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
    updateStatusForSelectedTemplate : function(keyStr){
		var url = HunterConstants.getHunterBaseURL("admin/action/emailTemplateObj/changeStatus");
		var components = keyStr.split(":::");
		var toStatus = components[0];
		var id = components[1];
		var data = {"templateId" : id, "toStatus" : toStatus}; 
		data = JSON.stringify(data);
		console.log("Current status = " + toStatus);
		console.log("Template Id = " + id);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "EmailTemplateObjVM.afterChangeTemplateStatus");
		kendoKipHelperInstance.closeHelperKendoWindow();
	},
	afterChangeTemplateStatus : function(data){
		data = jQuery.parseJSON(data);
		var status = data["status"];
		var message = data["message"];
		if(status == "Failed"){
			message = replaceAll(message, ",", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error updating Task!","<span style='color:red;' >" + message + "</span>");
		}else if(status === "Success"){ 
			kendoKipHelperInstance.popupWarning("Successfully updated task!", "Success");
			var templatesDS = this.get("emailTemplateObjDS");
			templatesDS.read();
		}
	},
	disableSaveHTMLButtonForDraf : function(templateId){
		var status = this.get("emailTemplateObjDS").get(templateId).get("status");
		$("#saveHTMLValue").prop("disabled", status === 'Draft' ? false : "disabled" ); 
		this.disableTextArea( status !== 'Draft' );
	},
	disableTextArea : function(disable){
		if( codeMirrorEditor != null  ){
			codeMirrorEditor.setOption("readOnly", disable);
		}
	}
});

$("document").ready(function(){
	EmailTemplateObjVM.init();
});








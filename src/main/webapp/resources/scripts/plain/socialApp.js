

var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";
registerNavigation("Groups", "Social Apps");

var kendoKipHelperInstance = new kendoKipHelper();
kendoKipHelperInstance.init();


var SocialAppModel = kendo.data.Model.define({
	id:"appId",
	fields : {
		"appId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
		},
		"appName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"appDesc" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"extrnlId" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"extrnalPassCode" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"socialType" : {
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
		}
	},
	getSocialAppEditTemplate : function(){
		var html = kendoKipHelperInstance.createContextEditButton(false);
		return html;
	},
	getSocialAppDelTemplate : function(){
		var 
		id = this.get("appId"),
		appName = this.get("appName"),
		idString = '"'+ id +'",',
		message = '"<u><b>'+ appName +'</u></b> will be deleted. <br/> Are you sure?"', 
		html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKToDeleteItem('+  idString + message + ',"SocialAppVM.deleteSocialApp"' +  ')');
		return html;
	},
	getConfigsButtTemplate : function(){
		var appId = this.get("appId"); 
		var button = kendoKipHelperInstance.createSimpleHunterButton("note",null, "SocialAppVM.showPopupToEditConfigs(\""+ appId +"\")");
		return button;
	}
});


var SocialAppDS = new kendo.data.DataSource({
	  transport: {
	    read:  {
	      url: baseUrl + "social/action/apps/read",
	      dataType: "json",
	      contentType:"application/json",
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "social/action/apps/create",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    destroy: {
	        url: baseUrl + "social/action/apps/delete",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    update: {
	        url: baseUrl + "social/action/apps/update",
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
	    	model:SocialAppModel
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
	        var response = e.response;
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


var SocialAppVM = kendo.observable({
	
	currEditedTemplateId : null,
	SocialAppDS_ : SocialAppDS,
	isEverVisible : true,
	socialTypeSource : [{"value":"Facebook", "text":"Facebook"},{"value":"Twitter", "text":"Twitter"} ],
	
	beforeInit : function(){
		console.log("Before initializing user role VM..."); 
	},
	init : function(){
		console.log("Initializing user role VM...");
		kendo.bind($("#hunterSocialAppContainer"), SocialAppVM);
	},
	afterInit : function(){
		console.log("Finishing up initializing user role VM...");
	},
	deleteSocialApp : function(id){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var data = {"appId" : id};
		data = JSON.stringify(data);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", HunterConstants.getHunterBaseURL("social/action/apps/delete")  , "SocialAppVM.afterDeleteSocialApp");
	},
	afterDeleteSocialApp : function(data){
		data = $.parseJSON(data);
    	if(data != null){
    		var status = data.status;
    		var message = replaceAll(data.message, ",", "<br/>");
    		if( status != null && HunterConstants.STATUS_SUCCESS === status ){
    			kendoKipHelperInstance.showSuccessNotification(message);
    			SocialAppVM.get("SocialAppDS_").read(); 
    		}else{
    			kendoKipHelperInstance.showSimplePopup("Error Deleting Social App","<span style='color:red;'>"+ message +"</span>");
    		}
    	}
	},
	showPopupToEditConfigs : function(appId){
		SocialAppVM.set("currEditedTemplateId", appId);
		var content = $("#xmlEditorTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Template XML Editor");
		kendo.ui.progress( $("#xmlEditorTemplateContainer") , true);
		setTimeout(function(){
			var url = HunterConstants.getHunterBaseURL("social/action/apps/loadConfigs/" + appId);
			$( "#xmlEditorTemplateText" ).load( url , function(response, status, xhr) {
	    		kendo.ui.progress( $("#xmlEditorTemplateContainer") , false);
	    		if ( status == "error" ) {
      			    var msg = "Error ( "+ xhr.status + " :  " + xhr.statusText +" ) : ";
      			    SocialAppVM.setTitleMessage(false, msg );
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
	saveHtmlValue : function(){
		kendo.ui.progress( $("#xmlEditorTemplateContainer") , true);
		var appId = SocialAppVM.get("currEditedTemplateId");
		codeMirrorEditor.save();
		var content = codeMirrorEditor.getValue(),
        	data = JSON.stringify( {"content":content} ),
        	url = HunterConstants.getHunterBaseURL("social/action/apps/saveConfigs/" + appId );
		setTimeout(function(){
	        kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "SocialAppVM.afterSavingTemplateFile");
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
			var socialAppDs = SocialAppVM.get("SocialAppDS_");
			socialAppDs.read();
		}
	},
});

function getSocialAppPopupTemplate(){
	return $("#socialAppEditPopupTemplate").html();
}

function socialTypeEditor(container, options){
	var dataSource = [{"value":"Facebook", "text":"Facebook"},{"value":"Facebook", "text":"Facebook"} ];
	$('<input required name="' + options.field + '"/>')
    .appendTo(container)
    .kendoDropDownList({
        autoBind: false,
        dataTextField: "text",
        dataValueField: "value",
        optionLabel : "Select Social Type",
        dataSource: dataSource
    });
}

$("document").ready(function(){
	SocialAppVM.init();
});








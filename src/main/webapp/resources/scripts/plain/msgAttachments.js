
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

var MessageAttchementModel = kendo.data.Model.define({
	id:"beanId",
	fields : {
		"beanId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
		},
		"beanName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"originalFileName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"cid" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"beanDesc" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"fileFormat" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"fileWidth" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
		},
		"fileHeight" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
		},
		"fileSize" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
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
	geMsgAttchmentDelTemplate:function(){
		var id = this.get("beanId"),
			shortName = this.get("beanName"),
			idString = '"'+ id +'",',
			message = '"<u><b>'+ shortName +'</u></b> will be deleted. <br/> Are you sure?"', 
			html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKToDeleteItem('+  idString + message + ',"MessageAttachmentsVM.deleteMessageAttchment"' +  ')');
		return html;
	}
});

function getMessageAttachmentToolBar(){
	return '<button onClick="MessageAttachmentsVM.showPopupToCreateNewAttachment()" style="border-radius:3px;height:30px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-add"></span>Create New Attachment</button>';
}

var MessageAttachmentDS = new kendo.data.DataSource({
	  transport: {
	    read:  {
	      url: baseUrl + "admin/action/messageAttachments/read",
	      dataType: "json",
	      contentType:"application/json",
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "admin/action/messageAttachments/create",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    destroy: {
	        url: baseUrl + "admin/action/messageAttachments/delete",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    update: {
	        url: baseUrl + "admin/action/messageAttachments/edit",
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
	    	model:MessageAttchementModel
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

var MessageAttachmentsVM = kendo.observable({
	
	messageAttachmentDS : MessageAttachmentDS,
	isEverVisible : true,
	
	beforeInit : function(){
		console.log("Before initializing user role VM..."); 
	},
	init : function(){
		console.log("Initializing user role VM...");
		kendo.bind($("#messageAttachmentContainer"), MessageAttachmentsVM);
	},
	afterInit : function(){
		console.log("Finishing up initializing user role VM...");
	},
	deleteMessageAttchment : function(id){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var data = {"attachmentId" : id};
		data = JSON.stringify(data);
		console.log("Attempting to delete message attachment..."); 
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", HunterConstants.getHunterBaseURL("admin/action/messageAttachments/delete")  , "MessageAttachmentsVM.afterDeletingMessageAttchment");
	},
	afterDeletingMessageAttchment : function(data){
		data = $.parseJSON(data);
    	if(data != null){
    		var status = data.status;
    		if( status != null && HunterConstants.STATUS_SUCCESS === status ){
    			UserRoleVM.get("userRoleDS").read(); 
    			kendoKipHelperInstance.showSimplePopup("Successfully Deleted User Role", "<span style='color:green' >"+ data.message +"</span>");
    			var grid = $("#messageAttachmentGrid").data("kendoGrid");
    			grid.dataSource.read();
    		}else{
    			kendoKipHelperInstance.showSimplePopup("Error Deleting User Role", "<span style='color:red' >"+ data.message +"</span>");
    		}
    	}
	},
	showPopupToCreateNewAttachment : function(){
		var html = $("#newMessageAttachmentPopupTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(html, "New Message Attachment");
		$("#messageAttachmentFiles").kendoUpload({
			 saveUrl: "../messageAttachments/create",
			 multiple: false,
			 autoUpload: false,
			 select : function(e){
				var files = e.files;
	            var acceptedFiles = [".jpg", ".jpeg", ".png", ".gif"];
	            var isAcceptedImageFormat = ($.inArray(files[0].extension, acceptedFiles)) != -1;
	            if (!isAcceptedImageFormat) {
                   e.preventDefault();
                   kendoKipHelperInstance.showErrorNotification("Image must be jpeg, png or gif");
                } 
			 }
		});
	},
	onSelectAttachmentFile : function(e){
		alert("Selected!!"); 
	}
});

$("document").ready(function(){
	MessageAttachmentsVM.init();
});








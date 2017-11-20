
var kendoKipHelperInstance = null;
registerNavigation("My Hunter", "Hunter Clients"); 
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

var hunterClientsModel = kendo.data.Model.define({
	id:"clientId",
	fields : {
		"clientId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:null
		},
		"firstName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"lastName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"email" : {
			type : "string", validation : {required : false},editable:true, defaultValue:null
		},
		"receiver" : {
			type : "boolean", validation : {required : true},editable:true, defaultValue:null
		},
		"budget" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0.00
		},
		"createdDate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"createdBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"updatedBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"updatedOn" : {
			type : "string", validation : {required : false},editable:false, defaultValue:null
		}
	},
	getEditTemplate : function(){
		return kendoKipHelperInstance.createContextEditButton(false);
	},
	getDeleteTemplate : function(){
		var id = this.get("clientId");
		var fName = this.get("firstName");
		var lName = this.get("lastName");
		var whole = id + "," + fName + "," + lName;
		return kendoKipHelperInstance.createDeleteButton(false, 'hunterClientVM.showPopupToDeleteClient("'+whole+'")');
	}
});


var hunterClientVM = kendo.observable({
	
	isEverVisible : true,
	
	beforeInit : function(){
		console.log("Preparing to initialize hunter clients VM..."); 
	},
	init : function(){
		console.log("Initializing hunter clients VM...");
		this.beforeInit();
		kendo.bind($("#hunterClientsTab"),hunterClientVM);
		this.afterInit();
	},
	afterInit : function(){
		kendoKipHelperInstance = new kendoKipHelper();
		kendoKipHelperInstance.init();
		//this.createUserTabstrips();
		console.log("Finished initialization of hunter clients VM!!"); 
	},
	showPopupToDeleteClient : function(this_){
		var split = this_.split(","),
			id = split[0],
			firstName = split[1],
			lastName = split[2],
			name = firstName + " " + lastName,
			content = "<span style='color:red;' ><b>" + name + "</b> will be deleted.<br/> Are you sure?</span><br/>";
		kendoKipHelperInstance.showOKToDeleteItem(id,content,"hunterClientVM.deleteHunterClient");
	},
	deleteHunterClient : function(id){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var userId = this.get("selUserId");
		var data = {"userId" : userId, "userRoleId":id};
		data = JSON.stringify(data);
		var url = baseUrl + "/admin/action/clients/delete";
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterClientVM.afterDeleteHunterClient");
	},
	afterDeleteHunterClient : function(data){
		data = $.parseJSON(data);
		var status = data[HunterConstants.STATUS_STRING];
		var message = data[HunterConstants.MESSAGE_STRING];
		if(status != HunterConstants.STATUS_SUCCESS){
			message = replaceAll(message, "|", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error deleting user role!","<span style='color:red;' >" + message + "</span>");
		}else{
			kendoKipHelperInstance.showSuccessNotification(message);
		}
	},
	hunterClientsDS : new kendo.data.DataSource({
		  transport: {
		    read:  {
		      url: baseUrl + "admin/action/clients/read",
		      dataType: "json",
		      contentType:"application/json",
		      method: "POST"
		    },
		    create: {
		        url: baseUrl + "admin/action/clients/create",
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST"
		    },
		    update: {
		        url: baseUrl + "admin/action/clients/update",
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST"
		    },
		    destroy: {
		        url: baseUrl + "admin/action/clients/destroy",
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
		    	model:hunterClientsModel
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
		        
		       /* if(message != null){
		        	kendoKipHelperInstance.popupWarning(message, "Success");
		        }*/
		        
		  },
		  requestEnd: function(e) {
		        var response = e.response;
		        var type = e.type;
		        var message = "";
		        //if(type === 'read')
		        	/*if(response !== 'undefined' && response != null){
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
		        
		  },
		  pageSize:1000
	})
	
});

$("document").ready(function(){
	hunterClientVM.init();
});
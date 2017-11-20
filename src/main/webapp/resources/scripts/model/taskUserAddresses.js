var hunterUserAddressesModel = kendo.data.Model.define({
	id:"id",
	fields : {
		"id" : {
			type : "number", validation : {required : true},editable:false, defaultValue:null
		},
		"country" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"state" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"aptNo" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"city" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"zip" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"type" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"userId" : {
			type : "string", validation : {required : false},editable:false, defaultValue:null
		},
	}
});
var hunterUserAddressesVM = kendo.observable({
	
	isEverVisible_ : true,
	
	beforeInit : function(){
		console.log("Getting ready to initialize task user addresses VM...");
	},
	init : function(){
		console.log("Initializing task user addresses VM...");
		this.beforeInit();
		kendo.bind($("#taskUserAddressesVM"), hunterUserAddressesVM);
		this.afterInit();
	}, 
	afterInit : function(){
		console.log("Successfully initialized task user addresses VM!!");
	},
	hunterUserAddressesDS : new kendo.data.DataSource({
		  transport: {
		    read:  {
		      url: function(){
		    	  var userId = hunterAdminClientUserVM.get("selUserId"); 
		    	  if(userId == null) userId = "0";
		    	  var url = "../action/user/addresses/read/" + userId;
		    	  return url;
		      },
		      dataType: "json",
		      contentType:"application/json",
		      method: "POST"
		    },
		    create: {
		    	 url: function(){
			    	  var userId = hunterAdminClientUserVM.get("selUserId"); 
			    	  if(userId == null) userId = "0";
			    	  var url = "../action/user/addresses/create/" + userId;
			    	  return url;
			      },
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST",
		        success: function(result) {
		            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
		         }
		    },
		    update: {
		    	 url: function(){
			    	  var userId = hunterAdminClientUserVM.get("selUserId"); 
			    	  if(userId == null) userId = "0";
			    	  var url = "../action/user/addresses/update/" + userId;
			    	  return url;
			      },
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST"
		    },
		    destroy: {
		    	 url: function(){
			    	  var userId = hunterAdminClientUserVM.get("selUserId"); 
			    	  if(userId == null) userId = "0";
			    	  var url = "../action/user/addresses/destroy/" + userId;
			    	  return url;
			      },
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
		    	model:hunterUserAddressesModel
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
		        
		        kendoKipHelperInstance.popupWarning(message, "Success");
		  },
		  pageSize:1000
	})
	
});


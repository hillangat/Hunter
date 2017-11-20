
var 
kendoKipHelperInstance = new kendoKipHelper();
kendoKipHelperInstance.init();

var hunterWindow;

var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

registerNavigation("Hunter", "Tasks"); 


var SocialGroupModel = kendo.data.Model.define({
    id: "groupId", 
    fields: {
    	"groupId" 	: { type: "number" },
        "groupName"	: { type: "string" },
        "status"	: { type: "string" },
        "regionName": { type: "string" },
        "socialType": { type: "string" }
    },
    getRemoveButton : function(){
    	return kendoKipHelperInstance.createDeleteButton(false,"hunterAdminClientUserVM.onClickRemoveSocialGrp("+ this.get("groupId") +")"); 
    }
});

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
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"firstName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"lastName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"groupName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"receiverType" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"groupDesc" : {
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
	getTaskGroupDeleteTemplate : function(){
		var groupName = this.get("groupName");
		var id = this.get("groupId");
		var idString = '"'+ id +'",';
		var message = '"<u><b>'+ groupName +'</u></b> will be deleted. <br/> Are you sure?"';
		var html = kendoKipHelperInstance.createDeleteButton(false, 'hunterAdminClientUserVM.showPopupToDeleteTaskGroup('+  idString + message+ ')');
		return html;
	},
	getViewTaskGroupContactsTemplate : function(){
			var idString = '"' + this.get("groupId") + '"',
			html = kendoKipHelperInstance.createSimpleHunterButton('search',null, 'hunterAdminClientUserVM.displayContactsForGroupId('+ idString +')');
		return html;
	}
});

var ReceiverGroupDS = new kendo.data.DataSource({
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
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
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
	          }else{
	        	  var json = kendo.stringify(options); 
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
	  pageSize:1000
});

var hunterClientUserModel = kendo.data.Model.define({
	id:"clientId",
	fields : {
		"userId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"clientTotalBudget" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"lastName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"middleName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"email" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"phoneNumber" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"userType" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"userName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"password" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"clientTotalBudget" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"receiver" : {
			type : "boolean", validation : {required : true},editable:false, defaultValue:"0"
		},
		"tasks" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"cretDate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"createdBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"lastUpdate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"0"
		}
	}
});


var taskRegionsModel = kendo.data.Model.define({
		id : "regionId",
		fields : {
			"regionId" : {
				type : "number", validation : {required : true},editable:false, defaultValue:0
			},
			"country" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"state" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"hasState" : {
				type : "boolean", validation : {required : true},editable:false, defaultValue:null
			},
			"county" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"constituency" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"city" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"ward" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"village" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"currentLevel" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"borderLongLats" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"currentLevel" : {
				type : "string", validation : {required : true},editable:false, defaultValue:null
			},
			"receiverCount" : {
				type : "count", validation : {required : true},editable:false, defaultValue:0
			}
		},
		getRegionsDeleteTemplate : function(){
			
			var id = this.get("regionId");
			var country = this.get("county");
			var county = this.get("country");
			var cons = this.get("constituency");
			var consWard = this.get("ward");
			
			var regionName = country + '/' + county + "/" + cons  + "/" + consWard;
			
			var idString = '"'+ id +'",';
			var message = '"<u><b>'+ regionName +'</u></b> will be deleted. <br/> Are you sure?"';
			var html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKCancelToDeleteTaskRegion('+  idString + message+ ')');
			return html;
		},
		getRegionViewContactTemplate : function(){
			
			var countryName = this.get("country") == null ? "NULL" : this.get("country"),
				countyName = this.get("county") == null ? "NULL" : this.get("county"),
				consName = this.get("constituency") == null ? "NULL" : this.get("constituency"),
				wardName = this.get("ward") == null ? "NULL" : this.get("ward");
			
			var params = countryName + "::" + countyName + "::" + consName + "::" + wardName;
				
			var idString = '"' + params + '"',
				html = kendoKipHelperInstance.createSimpleHunterButton('search',null, 'hunterAdminClientUserVM.displayContactsForRegionId('+ idString +')');
			return html;
		}
});


var hunterUserModel = kendo.data.Model.define({
	id:"userId",
	fields : {
		"userId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"firstName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"lastName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"middleName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"email" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"phoneNumber" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"userType" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"userName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"cretDate" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"createdBy" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"lastUpdate" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		}
	},
	getHunterUserDeleteButton : function(){
		  var id = this.get("id");
		  var firstName = this.get("firstName");
		  var lastName = this.get("lastName");
		  var idString = '"'+ id +'",';
		  var message = '"<b>' + firstName + ' ' + lastName + '</b> will be deleted.<br/> Are you sure?"';
		  var html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKCancelToDeleteHunterUser('+  idString + message+ ')',"Delete");
		  return html;
	},
	getEditTemplate : function(){
		return kendoKipHelperInstance.createContextEditButton(false);
	}/*,
	getClientTemplate : function(){
		var id = this.get("userId");
		return "<center><a cursor='ponter' style='color:blue;cursor:pointer'  id='"+ id +"' onClick='hunterAdminClientUserVM.loadSelUserTasksDetails(\""+ id +"\")'  >Client</a></center>";
	}*/
});

var hunterClientDS = new kendo.data.DataSource({
	  transport: {
	    read:  {
	      url: baseUrl + "client/action/read",
	      dataType: "json",
	      contentType:"application/json",
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "client/action/create",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    update: {
	        url: baseUrl + "client/action/update",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    destroy: {
	        url: baseUrl + "client/action/destroy",
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
	    	model:hunterClientUserModel
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
	        if(type === 'read')
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
});

var serviceProviderDS = new kendo.data.DataSource({
	  pageSize: 20,
	  sort: { field: "cretDate", dir: "desc" },
	  transport: {
	    read:  {
	      url: baseUrl + "message/action/providers/read",
	      dataType: "json",
	      accept: "application/json",
	      contentType:"application/json; charset=utf-8",
	      processdata : false,
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "message/action/providers/create",
	        dataType: "json",
	        Accept: "application/json",
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning(JSON.stringify(result), "Success");
	         }
	    },
	    update: {
	        url: baseUrl + "message/action/providers/update",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    destroy: {
	        url: baseUrl + "message/action/providers/destroy",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    parameterMap: function(options, operation) {
	    	console.log("Operation >> " + operation);
	          if (operation == "read") {
	        	  kendoKipHelperInstance.popupWarning("Reading data!");
	          }else {
	        	 var json = JSON.stringify(options);
	        	 return json;
	          }
	    }
	  },
	  schema: {
	    	model:{ 
	    		id:"providerId",
	    		fields : {
	    			"providerId" : {
	    				type : "number", validation : {required : true},editable:false, defaultValue:"0"
	    			},
	    			"providerName" : {
	    				type : "string", validation : {required : true},editable:false, defaultValue:null
	    			},
	    			"cstPrAudMsg" : {
	    				type : "number", validation : {required : true},editable:false, defaultValue:0
	    			},
	    			"cstPrTxtMsg" : {
	    				type : "number", validation : {required : true},editable:false, defaultValue:0
	    			}
	    		}
	    	}
	  },
	  error: function (e) {
	        kendoKipHelperInstance.popupWarning(e.errorThrown, "Success");
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
	        var message = null ;
	        if(type === 'read'){
	        	/*if(response !== 'undefined' && response != null){
	        		message = "Successfully obtained ( "+ response.length +" ) records!";
	        	}else{
	        		return;
	        	}*/
	        	hunterAdminClientUserVM.set("selUserId", null);
	        }
	        
	        if(type === 'update')
	        	message = "Successfully updated record!";
	        if(type === 'destroy')
	        	message = "Successfully deleted record!";
	        if(type === 'create')
	        	message = "Successfully created record!";
	        
	        if(message != null){
	        	kendoKipHelperInstance.popupWarning(message, "Success");
	        }
	        
	  }
});


var hunterUserDS = new kendo.data.DataSource({
	  pageSize: 20,
	  sort: { field: "cretDate", dir: "desc" },
	  transport: {
	    read:  {
	      url: baseUrl + "hunteruser/action/read/post",
	      dataType: "json",
	      accept: "application/json",
	      contentType:"application/json; charset=utf-8",
	      processdata : false,
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "hunteruser/action/create",
	        dataType: "json",
	        Accept: "application/json",
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning(JSON.stringify(result), "Success");
	         }
	    },
	    update: {
	        url: baseUrl + "hunteruser/action/update",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    destroy: {
	        url: baseUrl + "hunteruser/action/destroy",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    parameterMap: function(options, operation) {
	    	console.log("Operation >> " + operation);
	          if (operation == "read") {
	        	  //kendoKipHelperInstance.popupWarning("Reading data!");
	          }else if(operation === "destroy"){ 
	        	  var json = kendo.stringify(options);
	        	 /* 
	        	  	This is important!! 
	        	 	If the selected user is deleted, the selected user must be cleared from observable. 
	        	 */
	        	  var id = jQuery.parseJSON(json)["userId"];
	        	  var selUserId = hunterAdminClientUserVM.get("selUserId");
	        	  console.log("Selected User Id >> " + selUserId);
	        	  if(id !== "undefined" && "hunter_model_check_box_class_" + id == selUserId + "" ){ // this needed to be converted to string
	        		  console.log("Deleted selected user. Clearing selected user!");
	        		  hunterAdminClientUserVM.set("selUserId", null);
	        	  }else{
	        		  console.log("id >> " + id);
	        	  }
	        	  return json;
	          }else{ 
	        	 var json = JSON.stringify(options);
	        	 return json;
	          }
	    }
	  },
	  schema: {
	    	model:hunterUserModel
	  },
	  error: function (e) {
	        alert(e.errorThrown);
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
	        if(type === 'read'){
	        	/*if(response !== 'undefined' && response != null){
	        		message = "Successfully obtained ( "+ response.length +" ) records!";
	        	}else{
	        		return;
	        	}*/
	        	hunterAdminClientUserVM.set("selUserId", null);
	        }
	        
	        if(type === 'update')
	        	message = "Successfully updated record!";
	        if(type === 'destroy')
	        	message = "Successfully deleted record!";
	        if(type === 'create')
	        	message = "Successfully created record!";
	        
	        if(message != null){
	        	kendoKipHelperInstance.popupWarning(message, "Success");
	        }
	        
	  }
});

var hunterTaskModel = kendo.data.Model.define({
	id:"taskId",
	fields : {
		"taskId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"taskType" : {
			type : "object", validation : {required : true},editable:true, defaultValue:null
		},
		"tskMsgType" : {
			type : "object", validation : {required : true},editable:true, defaultValue:null
		},
		"taskName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"taskObjective" : {
			type : "string", editable:true, defaultValue:null,
				validation: {
                required: { message: "Task objective is required!" },
                validateTaskObjective: function (input) {
                    if (input.attr("data-bind") == "value:taskObjective") { 
                        if (input.val().length > 100) {
                            input.attr("data-validateTaskObjective-msg", "Task objective cannot be more than 100 characters");
                            return false;
                        }
                        else
                            return true;
                    }
                    return true;
                }
            }
		},
		"description" : {
			type : "string" ,editable:true, defaultValue:null,
			validation: {
                required: { message: "Task description is required!" },
                validateTaskDescription: function (input) {
                    if (input.attr("data-bind") == "value:description") { 
                        if (input.val().length > 50) {
                            input.attr("data-validateTaskDescription-msg", "Task description cannot be more than 50 characters");
                            return false;
                        }
                        else
                            return true;
                    }
                    return true;
                }
            }
		},
		"taskName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"processedBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"processedOn" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"taskBudget" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"desiredReceiverCount" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"availableReceiverCount" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"confirmedReceiverCount" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"taskCost" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"recurrentTask" : {
			type : "boolean", validation : {required : true},editable:true, defaultValue:false
		},
		"taskDateline" : {
			type : "string", validation : {required : false},editable:false, defaultValue:new Date()
		},
		"taskLifeStatus" : {
			type : "string", validation : {required : false},editable:true, defaultValue:"Draft"
		},
		"taskDeliveryStatus" : {
			type : "string", validation : {required : false},editable:true, defaultValue:"Conceptual"
		},
		"taskApproved" : {
			type : "boolean", validation : {required : false},editable:true, defaultValue:false
		},
		"taskApprover" : {
			type : "string", validation : {required : false},editable:true, defaultValue:null
		},
		"gateWayClient" : {
			type : "object", validation : {required : false},editable:true, defaultValue:null
		},
		"cretDate" : {
			type : "string", validation : {required : false},editable:true, defaultValue:new Date()
		},
		"createdBy" : {
			type : "string", validation : {required : false},editable:true, defaultValue:null
		},
		"lastUpdate" : {
			type : "string", validation : {required : false},editable:true, defaultValue:new Date()
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : false},editable:true, defaultValue:null
		},
		"taskMessage" : {
			type : "object", validation : {required : false},editable:true, defaultValue:null
		},
		"taskRegions" : {
			type : "object", validation : {required : false},editable:true, defaultValue:null
		}
	},
	getTaskLoadDetailsTemplate : function(){
		var id = this.get("taskId");
		  var idString = '"'+ id +'"';
		  var onClick = 'hunterAdminClientUserVM.showDetails('+  idString + ')';
		var html = kendoKipHelperInstance.createSearchButton(false, onClick);
		return html;
	},
	getEditTaskTemplate : function(){
		
		var 
		taskLifeStatus 	= this.get("taskLifeStatus"),
		taskId 			= this.get("taskId"),
		actionName 		= "editTask",
		idString		= null,
		html			= null;
		
		if(taskLifeStatus !== "Draft"){
			return "";
		}else{
			idString = "\""+ actionName + ":" + taskId +"\"";
			html = kendoKipHelperInstance.createSimpleHunterButton("pencil",null, "hunterAdminClientUserVM.launchTaskEditView("+ idString +")" );
			return html; 
		}		
	},
	getCloneTaskTemplate : function(){
		var taskLifeStatus = this.get("taskLifeStatus");
		if(taskLifeStatus === "Draft" || taskLifeStatus === "Review"){
			return "";
		}else{
			var id = this.get("taskId");
			var idString = "\""+ id +"\"";
			var html = kendoKipHelperInstance.createSimpleHunterButton("restore",null, "hunterAdminClientUserVM.cloneSelTask("+ idString +")" );
			return html;
		}
	},
	getTaskDeleteTemplate : function(){
		var lifeStatus = this.get("taskLifeStatus");
		if(lifeStatus !== "Draft"){
			return "";
		}
		var id = this.get("taskId");
		var taskName = this.get("taskName");
		var idString = '"'+ id +'",';
		var message = '"<b>' + taskName  + '</b> will be deleted.<br/> Are you sure?"';
		var html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKCancelToDeleteHunterTask('+  idString + message+ ')');
		return html;
	},
	getTaskHistoryTemplate : function(){
		var taskId = this.get("taskId");
		var html = kendoKipHelperInstance.createSimpleHunterButton("seek-s",null,'hunterAdminClientUserVM.downloadTaskHistory("'+ taskId +'")');
		return html;
	},
	getTaskProcessTemplate : function(){
		var lifeSts = this.get("taskLifeStatus");
		var delStatus = this.get("taskDeliveryStatus");
		var action = "hunterAdminClientUserVM.showPopupForProcessTask";
		var iconName = "arrow-e";
		if(delStatus == 'Pending' ){
			return "<center><span style='color:rgb(134,17,17);font-weight:bolder;'><span class='k-icon k-i-clock' ></span></span></center>";
		}
		if(lifeSts != "Approved" && lifeSts != "Processed" ){
			return "";
			//return kendoKipHelperInstance.createDisabledContextEditButton(false);
		}else if(lifeSts === "Processed"){
			action = "hunterAdminClientUserVM.showTaskProcessJobDetails";
			iconName = "insert-m";
		}
		var id = this.get("taskId");
		var idString = "\""+ id +"\"";
		var html = kendoKipHelperInstance.createSimpleHunterButton(iconName,null, action +"("+ idString +")" );
		return html;
	},
	getTaskEditStatusTemplate : function(){
		var currentStatus = this.get("taskLifeStatus");
		if(currentStatus === 'Processed'){
			return "<center><span style='color:#00B655;' >Processed</center></span>";
		}else if(currentStatus === 'Pending'){
			return "<center><span style='color:#00B655;' >Pending</center></span>";
		}
		var keyStr = this.get("taskId") + ":::" + currentStatus;
		var html = "<center><span style='color:blue;'><a style='cursor:pointer' onClick='hunterAdminClientUserVM.showPopupToChangeTaskStatus(\""+ keyStr +"\")' >"+ currentStatus +"</a></span></center>";
		return html;
	}, 
	getTaskRegionTemplate : function(){
		var id = this.get("taskId"); 
		var html = kendoKipHelperInstance.createSimpleHunterButton("group",null, "hunterAdminClientUserVM.loadTaskRegionView("+ id +")" );
		return html;
	},
	getTaskMessageTemplate : function(){
		var id = this.get("taskId"); 	
		var html = kendoKipHelperInstance.createSimpleHunterButton("folder-up",null, "hunterAdminClientUserVM.loadTaskMessageView("+ id +")" );
		return html;
	},
	getProgressTemplate : function(){
		var dStatus = this.get("taskDeliveryStatus");
		var color = '';
		if(dStatus === 'Processed'){
			color = '00B655';
		}else if(dStatus === 'Conceptual'){
			color = 'A200FF';
		}else{
			color = 'FF0000';
		}
		return "<center><a style='color:#"+ color +"; cursor:pointer'>"+ dStatus +"</a></center>";
	}
});

function newOwnersNames (){
	return hunterAdminClientUserVM.get("newUsersDetailsData"); 
}

var hunterAdminClientUserVM = kendo.observable({
	
	userGrid : null,
	selUserId : null,
	clientBean : {},
	draftClientBean : {},
	wasClientUpdated : false,
	isTaskOpen : false,
	hunterClientEditValues : {},
	hunterTaskDS : null,
	taskTypeDsData : ["Polytical", "Educational", "Corporate", "Testing"],
	hunterClientTaskGrid : null,
	tabStripWidget : null,
	taskTypeDs : null,
	taskProcessManager : null,
	socialProcessManager : null,
	
	taskRegionsOpen : true,
	taskGroupsOpen : false,
	taskGroupDropdownData : [],
	taskGroupDropDownSelVal : null,
	
	taskGroupsTotalReceivers : 0,
	taskAllCombinedReceivers : null,
	
	
	isEverVisible : true,
	isNeverVisible : false,
	isEverEnabled : true,
	isNeverEnabled : false,
	
	taskMsgSendDate : null,
	taskMsgTypeVal : null,
	desiredReceivers : null,
	actualReceivers : null, 
	confirmedReceivers : null,
	tskMsgId : null, 
	tskMsgDelStatus : null,
	tskMsgLifeStatus: null,
	tskMstTxt : null,
	tskMsgCretDate : null,
	tskMsgCretBy : null,
	tskMsgLstUpdate : "05/12/2045",
	tskMsgLstUpdatedBy : null,
	tskMsgProvider : null,
	tskMsgOwner : null,
	deleteCurMsgFlag : false,
	hunterTaskHistoryGrid : null,
	createTextMessageManager : null,
	createSocialMessageManager : null,
	fbProcessorInstance : null,
	
	
	
	onChangeTaskMsgSendDate : function(e){
		console.log(this.get("taskMsgSendDate")); 
	},
	onChangeTaskMsgStatus : function(e){
		console.log("Changed message status too : " + this.get("tskMsgLifeStatus"));
	},
	onChangeServiceProvider : function(e){
		console.log(this.get("providerId"));
	},
	
	beforeInit : function(){
		console.log("before init...");
		console.log("Preparing to initialized hunterAdminClientUserVM...");
		this.prepareTabStrips();
	},
	init : function(){
		console.log("Initializing hunterAdminClientUserVM...");
		this.beforeInit();
		kendo.bind($("body"), hunterAdminClientUserVM);
		this.afterInit();	
		this.initRegionVM();
	},
	afterInit : function(){
		console.log("after init...");
		this.createUserGrid();
		this.createTaskTypesDS();
		this.loadExistentEmailTemplates();
		console.log("hunterAdminClientUserVM successfully initialized!!");
		this.sclMsgInit();
	},
	newGetHunterUserCheckBox : function(userId){
		alert(userId);
	},
	taskHistoryDS : new kendo.data.DataSource({
	  batch: true,
	  height:400,
	  transport: {
	    read:  {
	      url: function(){
	    	  var taskId = hunterAdminClientUserVM.get("selTaskId");
	    	  var url = baseUrl + "task/action/task/history/getForTask/" + taskId;
	    	  return url;
	      },
	      dataType: "json",
	      method: "POST"
	    }
	  },
	  schema: {
	    model: { 
			id: "historyId",
	    		fields : {
	    			"historyId" : {type : "number", defaultValue:0, editable : false},
	    			"taskId" : {type : "number", defaultValue:0, editable : false},
	    			"evenName" : {type : "string", defaultValue:null, editable : false},
	    			"eventStatus" : {type : "string", defaultValue:null, editable : false},
	    			"eventMessage" : {type : "string", defaultValue:null, editable : false},
	    			"eventUser" : {type : "string", defaultValue:null, editable : false},
	    			"eventDate" : {type : "string", defaultValue:null, format:"yyyy-MM-dd HH:mm:ss", editable : false},
	    		}, 
	    		getEventStatusTemplate : function(){
	    			var status = this.get("eventStatus");
	    			var color = status == 'Failed' ? 'red' : 'green';
	    			var template = "<span style='color:"+ color +"' >"+ status +"</span>";
	    			return template;
	    		}
			}
	  }
	}),
	destroyTaskHistroyGrid : function(){
		var exstGrid = this.get("hunterTaskHistoryGrid");
		if(exstGrid != null){
			exstGrid.destroy();
			$("#taskHistoryPopupGrid").empty();
			$("#taskHistoryPopupGrid").html("");
		}
	},
	displayPopupForTaskHistoryGrid : function(taskId){
		var r = $.Deferred();
		this.set("selTaskId",taskId);
		var content = "<div id='taskHistoryPopupGrid'  style='width:1200px;height:700px;background-color:#EBF7FF;border:1px solid #B6D2E2;border-radius:4px;' ></div>";
		kendoKipHelperInstance.showWindowWithOnClose(content, "Hunter Task History");
		console.log("Finished displaying kendo window!!");
		return r;
	},
	createTaskHistoryGrid : function(){
		$("#taskHistoryPopupGrid").closest(".k-window").css({"top":"30%","left":"20%"});
		var ds = hunterAdminClientUserVM.get("taskHistoryDS");
		var hunterTaskHistoryGrid = $("#taskHistoryPopupGrid").kendoGrid({
			dataSource : ds,
			toolbar : kendo.template($("#taskHistoryToolBarTemplate").html()),
			height : 350,
			pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            sortable: true,
            columns: [
               { field: "historyId", title: "ID", width: 30 },
               { field: "taskId", title: "Task Id", width: 60 },
               { field: "evenName", title: "Event", width: 80},
               { field: "eventStatus", title: "Event Status", width: 60, template : "#=getEventStatusTemplate()#" },
               { field: "eventMessage", title: "Event Message", width: 250 },
               { field: "eventUser", title: "User", width: 60 },
               { field: "eventDate", title: "Event Date", width: 85 },
            ]
		}).data("kendoGrid"); 
		this.set("hunterTaskHistoryGrid", hunterTaskHistoryGrid);
		ds.read();
		console.log("Done creating task grid!!");
	},
	downloadTaskHistory : function(taskId){
		this.displayPopupForTaskHistoryGrid(taskId).done(hunterAdminClientUserVM.createTaskHistoryGrid());
	},
	prepareTabStrips : function(){
		console.log("Preparing client tab strips...");
		var tabStripWidget = $("#hunterUserDetailsStrip").kendoTabStrip({
	         animation:  {
	             open: {
	                 effects: "fadeIn"
	             }
	         },
	         dataSpriteCssClass: "spriteCssClass"
	     }).data("kendoTabStrip");
		this.set("tabStripWidget", tabStripWidget); 
		console.log("Successfully prepared client tab strips!!");
		
		// hide message and region tab contents. 
		//this.slideUpTaskTabs();
	}/*,
	slideUpTaskTabs : function(){
		$("#taskRegionStrip").slideUp();
		$("#taskMessageStrip").slideUp();
	}*/,
	showTabStripNumber : function(n){
		var tabstrip = this.get("tabStripWidget"); 
		tabstrip.select(n);
	},
	createTaskTypesDS : function(){
		var taskTypeDS = new kendo.data.DataSource({
			data : HunterConstants.TASK_TYPES_ARRAY
		}); 
		this.set("taskTypeDs", taskTypeDS);
		console.log("successfully created task types DS !!"); 
	},
	deleteCurrentTaskMsg : function(){
		kendoKipHelperInstance.closeHelperKendoWindow();
		this.clearCurrentTskMsg();
		this.set("deleteCurMsgFlag", true);
		var taskId = this.get("selTaskId"); 
		console.log("Deleting task for task Id : " + taskId); 
		this.loadNewTaskMessageView(taskId); 
		this.set("deleteCurMsgFlag", false);
		kendoKipHelperInstance.popupWarning("Deleted Message Successfully!!", "Success");
	},
	showPopupForDeleteCurrentTskMsg : function(){
		var iconName = "close" ,text = "Delete", onClick = "hunterAdminClientUserVM.deleteCurrentTaskMsg()";
		var deleteButton = kendoKipHelperInstance.createSimpleHunterButton(iconName,text,onClick);
		onClick = "kendoKipHelperInstance.closeHelperKendoWindow()";
		text = "Cancel";
		iconName = "cancel";
		var cancelButton = kendoKipHelperInstance.createSimpleHunterButton(iconName,text,onClick);
		var warningTxt = "This message will be deleteda and replaced with default message. Are you sure?";
		var warnButtons = "<table style='width:60%;margin-left:20%;' ><tr><td>" + deleteButton + "</td><td>" + cancelButton + "</td></table></tr>";
		var content = warningTxt + "</br>" + warnButtons;
		kendoKipHelperInstance.showPopupWithNoButtons("Delete Message", content);
	},
	deleteSelectedUser : function(id){
		var grid = this.get("userGrid");
		var mDS = grid.dataSource;
		var model = mDS.get(id);
		mDS.remove(model);
		mDS.sync();
		kendoKipHelperInstance.popupWarning("Successfully deleted!", "Success"); 
		kendoKipHelperInstance.closeHelperKendoWindow();
	},
	deleteSelectedTask  : function(id){
		console.log("deleting selected task >> " + id);
		var taskDs = this.get("hunterClientTaskGrid").dataSource;
		var model = taskDs.get(id);
		taskDs.remove(model);
		taskDs.sync();
		kendoKipHelperInstance.closeHelperKendoWindow();
	},
	loadSelUserDetails : function(userId){
		
		var checkBox = $("#hunterTaskClientUser_"+userId);
		var checked_ = $(checkBox).prop("checked"); 
		if(!checked_){
			$(checkBox).prop("checked",true);
			//return since it's the same grid
			return;
		}
		
		//uncheck all others first
		var data = this.get("userGrid").dataSource.view();
		var len = data.length;
		for(var i=0; i<len; i++){
			var model = data[i];
			var userId_ = model["userId"];
			if(userId != userId_){
				var id_ = "hunterTaskClientUser_" + userId_;
				$("#"+id_).prop("checked", false);
			}else{
				hunterAdminClientUserVM.set("selUser",model);
			}
		}
		this.set("selUserId", userId);
		
		var isRegionViewOpen = this.get("isTaskRegionOpen");
		var isMsgViewOpen = this.get("isMsgViewOpen");
		var isEmailSectionOpen = this.get("isEmailSectionOpen");
		
		console.log("isRegionViewOpen("+ isRegionViewOpen +")," + "isMsgViewOpen("+ isMsgViewOpen +")," + "isEmailSectionOpen("+ isEmailSectionOpen +")," );
		
		if(isRegionViewOpen){
			this.canceltEditRegionAndClose();
			this.set("isTaskRegionOpen",false);
			console.log("Successfull closed task region view !!!"); 
		}else if(isMsgViewOpen){
			this.canceltEditMessageAndClose();
			this.set("isMsgViewOpen",false);
			console.log("Successfull closed message region view !!!");
		}else if(isEmailSectionOpen){
			this.closeEmailTemplateSection();
			this.set("isEmailSectionOpen", false);
			console.log("Successfull closed email message view !!!");
		}
		
		/*var userId = this.get("selUserId"); 
		if(userId == null){
			kendoKipHelperInstance.showErrorNotification("Please select user first!!");
			return;
		}*/
		var userIdData = {"userId":userId};
		$.ajax({
			url: baseUrl + "client/action/getClientForUserId",
		      data : userIdData,
		      method: "POST"
		}).done(function(data) {
			var json = jQuery.parseJSON(data);
			hunterAdminClientUserVM.updateClientDetails(json);
			hunterAdminClientUserVM.refreshHunterTaskDS(json);
			hunterAdminClientUserVM.createTasksGrid(json);
			kendoKipHelperInstance.popupWarning("Successfully loaded user!", "Success");
		 }).fail(function(data) {
			 var json = jQuery.parseJSON(data);
			 console.log("Failed to load user details!!!!!!!");
			 console.log(json);
			 kendoKipHelperInstance.popupWarning(data.statusText + " (" + json.status + ")", "Error");
		 });
		
		
		// After loading user details, force tabstrip to show the loaded tab.
		this.showTabStripNumber(0);
		
	},
	loadSelUserDetailsAndCollapseGrid : function(){
		var userId = this.get("selUserId");
		if(userId == null){
			kendoKipHelperInstance.showSimplePopup("Please select user.", "No user is selected. Please select user first.<br/>");
			return;
		}
		
		// update name when grid is closed.
		var userId_ = userId.replace("hunter_model_check_box_class_","");
		var model = this.get("userGrid").dataSource.get(userId_);
		var fullName = model.get("firstName") + "&nbsp;&nbsp;&nbsp;" + model.get("lastName"); 
		$("#selectedUserNameSpan").html(fullName);
		
		// then load user details in the bottom pane.
		this.loadSelUserDetails(userId);
		
		$("#hunterUserGrid").slideUp(800, function(){
			$("#expandGridButtonDiv").removeClass("hidden");
		});
	},
	expandHunterUserGrid : function(){
		$("#expandGridButtonDiv").addClass("hidden");
		$("#hunterUserGrid").slideDown(500, function(){
		});
	},
	loadSelUserTasksDetails : function(id){
		var clientBean = this.get("clientBean"); 
		var clientId = clientBean["clientId"];
		if(clientBean == null || clientId != id ){
			kendoKipHelperInstance.popupWarning("Please load this user first!","Error"); 
			return;
		}
		var html = $("#clientDetailsTemplateDiv").html();
		var template = kendo.template(html);
		var contents = template(clientBean);
		kendoKipHelperInstance.showBackUpPercentMeasuredOKCancelTitledPopup(contents, "Task Details", "35", "25");
		$("#clientTotalBudget").kendoNumericTextBox({
		    downArrowText: "Less"
		});
	},
	ajaxSaveClientDetails : function(clientDetails){
		console.log("making ajax request to update client >> " + JSON.stringify(clientDetails));
		$.ajax({
			url: baseUrl + "client/action/editHunterClient",
		      data : clientDetails,
		      method: "POST"
			}).done(function(data) {
				var json = jQuery.parseJSON(data);
				if(json.status !== 'Success'){
					kendoKipHelperInstance.popupWarning(json.status + "! " + json.message, "Error");
				}else{
					kendoKipHelperInstance.popupWarning(json.message, "Success");
				}
			 }).fail(function(data) {
				 var json = jQuery.parseJSON(data);
				 kendoKipHelperInstance.popupWarning(data.statusText + " (" + json.status + ")", "Error");
			 });
		
	},
	saveDraftClientChanges : function(){
		
		var value = $("#clientTotalBudget").val();
		var checked = $("#receiver").prop("checked"); 
		var clientBean = this.get("clientBean"); 
		
		clientBean["clientTotalBudgetValue"] = value;
		clientBean["receiverValue"] = checked;
		clientBean["clientTotalBudget"] = this.getClientBudgetHtmlWithValue(value);
		clientBean["receiver"] = this.getCheckBoxWithValue(checked ? 'checked="checked" ' : '');
		console.log("client bean after changes >>> " + JSON.stringify(clientBean));
		
		var lastClientBean = {"clientTotalBudget" : value, "receiver" : checked, "clientId" :  clientBean["clientId"]};
		this.ajaxSaveClientDetails(lastClientBean);
		
		kendoKipHelperInstance.closeMeasuredWindowBackUp();
		
	},
	updateClientDetails : function(json){
		this.set("tskMsgOwner", json.user.userName);
		var clientBean = this.get("clientBean");
		var checked = json.receiver ? 'checked="checked" ' : '';
		clientBean["clientId"] = json.clientId;
		clientBean["clientTotalBudget"] = this.getClientBudgetHtmlWithValue(json.clientTotalBudget);
		clientBean["clientTotalBudgetValue"] = json.clientTotalBudget;
		clientBean["receiver"] = this.getCheckBoxWithValue(checked);
		clientBean["receiverValue"] = json.receiver;
		clientBean["cretDate"] = json.cretDate;
		clientBean["createdBy"] = json.createdBy;
		clientBean["lastUpdate"] = json.lastUpdate;
		clientBean["lastUpdatedBy"] = json.lastUpdatedBy;
		this.setClientBean(clientBean);
	},
	getClientBudgetHtmlWithValue : function(value){
		return '<input id="clientTotalBudget" value="'+ value +'" />';
	},
	getCheckBoxWithValue : function(checked){
		return '<input id="receiver" type="checkbox"  '+ checked +' />';
	},
	setClientBean : function(clientBean){
		this.set("clientBean", clientBean);
	},
	refreshHunterTaskDS : function(json){
		//var tasks = json.tasks;
		var clientId = json.clientId;
		console.log("Creating dataSource for client Id >> " + clientId);
		var hunterTaskDS_ = new kendo.data.DataSource({
			  pageSize: 20,
			  transport: {
				    read:  {
				      url: baseUrl + "task/action/read/getTasksForClientId/" + clientId,
				      dataType: "json",
				      method: "POST"
				    },
				    create: {
				        url: baseUrl + "task/action/create/createTaskForClientId",
				        dataType: "json", 
				        contentType:"application/json",
				        processData : false,
				        method:"POST",
				        success: function(result) {
				            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
				         }
				    },
				    update: {
				        url: baseUrl + "task/action/update/updateTaskForClientId",
				        dataType: "json", 
				        contentType:"application/json",
				        method:"POST"
				    },
				    destroy: {
				        url: baseUrl + "task/action/destroy/destroyTaskForClientId",
				        dataType: "json", 
				        contentType:"application/json",
				        method:"POST"
				    },
				    parameterMap: function(options, operation) {
				          if (operation == "read") {
				        	  console.log("Reading data...");
				          }else if(operation === "destroy" || operation === "update" || operation === "create"){ 
				        	  var json1 = kendo.stringify(options); 
				        	  json = jQuery.parseJSON(json1);
				        	  if(operation === "create"){
				        		  json["clientId"] = clientId;
				        		  console.log(JSON.stringify(json)); 
				        		  var tskMsgType = options.tskMsgType["msgTypVal"];
				        		  console.log("Task Message Type value !! >> " + tskMsgType);
				        		  json["tskMsgType"] = tskMsgType;
				        		  var taskType = options.taskType["value"];
				        		  console.log("Task type value !! >> " + taskType);
				        		  json["taskType"] = taskType;
				        		  var gateWayClient = options.gateWayClient["value"];
				        		  console.log("gateWayClient>>>>>> " + gateWayClient); 
				        		  console.log("Task gateWayClient value !! >> " + gateWayClient);
				        		  json["gateWayClient"] = gateWayClient;
				        	  }
				        	  json1 = JSON.stringify(json);
				        	  console.log("JSON for update, create, destroy >> " + json1); 
				        	  return json1;
				          }else{
				        	  kendoKipHelperInstance.popupWarning("Bad operation >> " + operation,"Error");
				          }
				    }
			  },
			  sort: { field: "cretDate", dir: "desc" },
			  schema: {
			    	model:hunterTaskModel
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
			       // var response = e.response;
			        var type = e.type;
			        var message = null;
			        if(type === 'read')
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
			      
			        if(message != null){
			        	kendoKipHelperInstance.popupWarning(message, "Success");
			        }
			        
			  }
		});
		this.set("hunterTaskDS", hunterTaskDS_);
	},
	destroyHunterClientTaskGrid : function(){
		$("#noDataH3ForHunterClientDetails").remove();
		var taskGrid = this.get("hunterClientTaskGrid");
		if(taskGrid == null){
			console.log("No tasks grid widget found. Returning..."); 
			return;
		}
		taskGrid.destroy();
		$("#selectedUserClientTasks").empty();
		console.log("Successfully destroyed tasks grid widget!!"); 
	},
	showPopupToChangeTaskStatus : function(keyStr){
		
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
		var toDraft = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);'  class='k-button' onClick=hunterAdminClientUserVM.updateStatusForSelectedTask('Draft:::"+ id +"') >Draft</button></td>";
		var toReview = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class='k-button' onClick=hunterAdminClientUserVM.updateStatusForSelectedTask('Review:::"+ id +"') >Review</button></td>";
		var toApproved = "<td><button style='background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class='k-button' onClick=hunterAdminClientUserVM.updateStatusForSelectedTask('Approved:::"+ id +"') >Approved</button></td>";
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
		var url = baseUrl + "task/action/task/changeStatus";
		var components = keyStr.split(":::");
		var toStatus = components[0];
		var id = components[1];
		var data = {"taskId" : id, "toStatus" : toStatus}; 
		data = JSON.stringify(data);
		console.log("Current status = " + toStatus);
		console.log("Task Id = " + id);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterChangeTaskStatus");
		kendoKipHelperInstance.closeHelperKendoWindow();
	},
	afterChangeTaskStatus : function(data){
		data = jQuery.parseJSON(data);
		var status = data["status"];
		var message = data["message"];
		if(status == "Failed"){
			message = replaceAll(message, ",", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error updating Task!","<span style='color:red;' >" + message + "</span>");
		}else if(status === "Success"){ 
			kendoKipHelperInstance.popupWarning("Successfully updated task!", "Success");
			var tskGridDs = this.get("hunterClientTaskGrid").dataSource;
			tskGridDs.read();
		}
	},
	fetchUniqueCountForTaskId : function(taskId){
		var url = baseUrl + "messageReceiver/action/taskReceivers/getAllCounts/" + taskId;
		kendoKipHelperInstance.ajaxPostData(null, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterFetchUniqueCountForTaskId");
	},
	afterFetchUniqueCountForTaskId : function(data){
		var json = $.parseJSON(data);
		setTimeout(function(){
			kendo.ui.progress($("#tskRgnCntSummryIconLoader"), false);
			$("#tskRgnCntSummryIconLoader").remove();
			$("#loadingCountText").remove();
			$("#tskRgnCntSummryDataResults").toggle();
			$("#tskRgnCntGroupCount").html(json.groupCount);
			$("#tskRgnCntGroupNumber").html(json.groupNumber);
			$("#tskRgnCntRegionCount").html(json.regionCount);
			$("#tskRgnCntRegionNumber").html(json.regionsNumber);
			$("#tskTotalRcvrCount").html("<span style='color:red;' >" + json.totalCount + "</span>"); 
			var lifeStatus = hunterAdminClientUserVM.getSelectedTaskBean().get("taskLifeStatus");
			if(lifeStatus != 'Draft' ){
				$("#taskRegionEditButton").attr("disabled","disabled");
			}
		 }, 1200);
	},
	showPopupForTaskRegionsDetails : function(){
		var html = $("#tskRgnCntSummry").html();
		kendoKipHelperInstance.showWindowWithOnClose(html,"Task Region and Receiver Data");
		kendo.ui.progress($("#tskRgnCntSummryIconLoader"), true);
	},
	loadTaskRegionView: function(id){
		this.set("selTaskId",id);
		this.showPopupForTaskRegionsDetails(null);
		this.loadGroupDropdown(id);
		this.fetchUniqueCountForTaskId(id);
	},
	loadNewTaskRegionView : function(taskId){
		hunterAdminClientUserVM.set("selTaskId", taskId);
		hunterAdminClientUserVM.getTotalReceivers();
		var taskRegionsDs = hunterAdminClientUserVM.get("selTaskRegionsDS");
		taskRegionsDs.read();
		hunterAdminClientUserVM.opendTaskRegionView();
		this.set("isTaskRegionOpen", true);
		$("#taskRegionsButton").css("background-color","rgb(155,255,229)");
		// update counts of receivers.
		hunterAdminClientUserVM.getTaskGroupsTotalReceivers();
		hunterAdminClientUserVM.getAllReceiversForTaskReceivers();
		
	},
	loadEdiTaskRegionView : function(){
		var taskId = this.get("selTaskId"); 
		this.loadNewTaskRegionView(taskId);
	},
	closePopupAndLoadEdiTaskRegionView : function(){
		kendoKipHelperInstance.closeWindowWithOnClose();
		this.loadEdiTaskRegionView();
	},
	loadDetailsTaskRegionView : function(taskId){
		kendoKipHelperInstance.closeMeasuredWindow();
		$("#taskGridHolder").hide(1000, function(){
			$("#taskRegionStrip").slideDown(500);
		});
	},
	saveCurrentEditRegion : function(){
		var selTaskId = this.get("selTaskId"); 
		console.log("Adding task regions. Task Id = " + selTaskId);
		var selCountry = this.get("selCountry");
		var selState = this.get("selState");
		var selCounty = this.get("selCounty");
		var selConstituency = this.get("selConstituency");
		var selConstituencyWard = this.get("selConstituencyWard");
		var model = {"selCountry":selCountry, "selState":selState, "selCounty":selCounty, "selConstituency" : selConstituency, "selConstituencyWard" : selConstituencyWard, "selTaskId" : selTaskId };
		var json =  JSON.stringify(model);
		kendoKipHelperInstance.ajaxPostData(json, "application/json", "json", "POST", baseUrl+"/region/action/task/regions/addTotask", "hunterAdminClientUserVM.afterSaveCurrentEditRegion"); 
		console.log("Successfully added regions to task!!!");
	},
	afterSaveCurrentEditRegion : function(data){
		var ds = hunterAdminClientUserVM.get("selTaskRegionsDS");
		if(ds != null){
			ds.read();
		}
		console.log(data);
		data = jQuery.parseJSON(data);
		var status = data[HunterConstants.STATUS_STRING];
		var message = data[HunterConstants.MESSAGE_STRING];
		var count = data["count"];
		var groupCount = data["groupCount"]; 

		if(count != null && groupCount != null){
			hunterAdminClientUserVM.set("taskUniqueReceivers",count);
			hunterAdminClientUserVM.set("taskGroupsTotalReceivers", groupCount);
			hunterAdminClientUserVM.set("taskAllCombinedReceivers",(count + groupCount));
			hunterAdminClientUserVM.getTaskGroupsTotalReceivers();
		}
		kendoKipHelperInstance.showErrorOrSuccessMsg(status, message);
	},
	canceltEditRegionAndClose : function(){
		/* If the groups section is open, it should close. When user comes back, region should be default. */
		this.showTaskRegionsPart(); 
		this.closeTaskRegionView();
	},
	showTaskRegionsPart : function(){
		var isAlreadyOpen = this.get("taskRegionsOpen");
		if(isAlreadyOpen){
			console.log("Regions part is already open. Returning..."); 
			return;
		}else{
			$("#taskGroupsCoverDiv" ).slideUp(300,function(){
				$("#taskRegionsCoverDiv").slideDown(300,function(){
				});
			});
		}
		$("#taskRegionsButton").css("background-color","rgb(155,255,229)");
		$("#taskGroupsButton").css("background-color","rgb(212,239,249)");
		this.set("taskRegionsOpen", true);
		this.set("taskGroupsOpen",false);
		var grid = $("#selTaskRegionsGrid").data("kendoGrid");
		if(grid != null){
			console.log("Found the grid. Refreshing...");
			grid.refresh();
		}
		//this.getAllReceiversForTaskReceivers();
		this.getTaskGroupsTotalReceivers(); // read the total receivers.
	},
	showTaskGroupsPart : function(){
		//var selTaskId = this.get("selTaskId");
		var isAlreadyOpen = this.get("taskGroupsOpen");
		if(isAlreadyOpen){
			console.log("Groups part is already open. Returning..."); 
			return;
		}else{
			$("#taskRegionsCoverDiv").slideUp(300,function(){
				$("#taskGroupsCoverDiv").slideDown(300,function(){
				});
			});
		}
		$("#taskGroupsButton").css("background-color","rgb(155,255,229)");
		$("#taskRegionsButton").css("background-color","rgb(212,239,249)");
		this.get("receiverGroupDS").read(); // refresh to load groups for selected task.
		this.getTaskGroupsTotalReceivers(); // read the total receivers.
		this.set("taskGroupsOpen",true);
		this.set("taskRegionsOpen", false);
		this.getAllReceiversForTaskReceivers();
	},
	closeTaskRegionView : function(){
		this.set("isTaskRegionOpen", false); // this is important!!
		$("#taskRegionStrip").slideUp(500, function(){
			$("#hunterUserDetailsStrip").slideDown(500);
		});
	},
	opendTaskRegionView : function(taskId){
		$("#hunterUserDetailsStrip").slideUp(500, function(){
			hunterAdminClientUserVM.showTabStripNumber(2);
			$("#taskRegionStrip").slideDown(500);
		});
		hunterAdminClientUserVM.getAllReceiversForTaskReceivers();
	},
	showPopupForProcessedTaskTxtMsg : function(id){
		var r = $.Deferred();
		var html = $("#processedTaskTxtMessageViewTemplateContainer").html();
		kendoKipHelperInstance.showWindowWithOnClose(html,"Task Process Progress");
		kendo.ui.progress($("#processedTaskTxtMsgLoadIcon"), true);
		setTimeout(function(){ 
			 var url = baseUrl + "message/action/tskMsg/getPrcssTxtMssgeDtls/" + id;
			 kendoKipHelperInstance.ajaxPostDataForJsonResponseWthCllbck(null, "application/json", "json", "POST", url , "hunterAdminClientUserVM.afterFetchTaskTxtMsgDetails", "kendoKipHelperInstance.closeWindowWithOnClose");
		 }, 500);
		return r;
	},
	afterFetchTaskTxtMsgDetails : function(data){
		
		try {
			data = $.parseJSON(data);
			kendo.ui.progress($("#processedTaskTxtMsgLoadIcon"), false);
			$("#processedTaskTxtMsgLoadDiv").remove();
			$("#processedTaskTxtMsgLoadIcon").remove();
			$("#processedTaskTxtMsgDet2").css({"display":""}); 
			$("#processedTaskTxtMsgDet3").css({"display":""});
			$("#processedTaskTxtMsgDet1").css({"display":""});
			var tds = $("#processedTaskTxtMessageViewTemplate table td");
			for(var i=0; i<tds.length;i++){
				var tdId = $(tds[i]).prop("id"); 
				for(key in data){
					if(key === tdId){
						var val = data[key];
						$("#" + tdId).html(val); 
					}
				}
			}
		}
		catch(err) {
			console.log(data);
		    var message = err.message;
		    kendo.ui.progress($("#processedTaskTxtMsgLoadIcon"), false);
		    $("#processedTaskTxtMsgLoadIcon").remove();
		    $("#processedTaskTxtMsgLoadDiv").html("<span style='color:red;font-size:17px;' > And error occurred while loading data :( </br>Message : "+ message +"</span>" +
		    	"<br/></br>" + '<button onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);width:200px;margin-left:60px;margin-top:90px;" class="k-button" >Close</button>'
		    );
		}
	},
	loadTaskMessageView: function(id){
		this.set("selTaskId", id);
		var taskBean = this.getSelectedTaskBean();
		var lifeStatus = taskBean.get("taskLifeStatus");
		var msgType = taskBean.get("tskMsgType");
		
		if(lifeStatus != "Draft" && lifeStatus != "Approved" && lifeStatus != "Review" &&  (msgType.toLowerCase() == "text") ){
			hunterAdminClientUserVM.showPopupForProcessedTaskTxtMsg(id);
			return;
		}else if((lifeStatus == "Draft" || lifeStatus == "Approved" || lifeStatus == "Review") &&  (msgType.toLowerCase() == "text") ){
			this.get("createTextMessageManager").execute(); 
			return;
		}
		
		if( lifeStatus != "Draft"  &&  (msgType.toLowerCase() == "email") ){
			this.launchTaskEmailPreview();
			return;
		}
		
		if(lifeStatus != "Draft" && lifeStatus != "Approved" && lifeStatus != "Review" &&  (msgType.toLowerCase() == "social") ){
			hunterAdminClientUserVM.showPopupForProcessedTaskSocialMsg(id);
			return;
		}else if((lifeStatus == "Draft" || lifeStatus == "Approved" || lifeStatus == "Review") &&  (msgType.toLowerCase() == "social") ){
			hunterAdminClientUserVM.showPopupForSocialMessage();
			return;
		}
		
		hunterAdminClientUserVM.loadNewTaskMessageView(id);
		this.loadMessageAttachmentsContainer();
		
		/*var msg = this.get("hunterClientTaskGrid").dataSource.get(id).get("taskMessage"); 
		var msgTxt = msg != null ? msg["msgText"]  : "<span style='color:#F51A1A;' > No message found for this task! <br/> Would you like to create one? </span>";  
		console.log("Message Text >> " + msgTxt);
		var okButton = "<td>" + kendoKipHelperInstance.createSimpleHunterButton("tick","OK", 'kendoKipHelperInstance.closeHelperKendoWindow()');
		var div = "<div style='height:80px;width:98%;margin-left:1%;border-radius:5px;padding:5px;'  class='k-block k-info-colored'>"+ msgTxt +"</div>" + "</td>";
		if(msg == null){
			var createButton = "<td>" + kendoKipHelperInstance.createSimpleHunterButton("plus","Create", 'hunterAdminClientUserVM.loadNewTaskMessageView("'+ id +'")') + "</td>";
			var creatButtTable = div + "<br/><table style='width:50%;margin-left:25%;' ></tr>" + createButton + okButton + "</tr></table>";
			kendoKipHelperInstance.showPopupWithNoButtons("Task Message", creatButtTable);
			return;
		}*/
	},
	loadEdiTaskMessageView : function(taskId){
		console.log("loading edit view for message id >> " + taskId);
		kendoKipHelperInstance.closeHelperKendoWindow();
		this.loadNewTaskMessageView(taskId);
	},
	loadDetailsTaskMessageView : function(taskId){
		console.log("loading details view for message id >> " + taskId);
		kendoKipHelperInstance.closeHelperKendoWindow();
		this.loadNewTaskMessageView(taskId);
	},
	saveCurrentEditMessageAndClose : function(){
		kendoKipHelperInstance.popupWarning("Successfully saved the changes.", "Success");
		//var sendDate = $("#selTaskMsgSendDatePicker").val();
		this.getMessageDetails();
		this.closeEditMessageView();
		// refresh the grid when you close taskMsgDetails.
		var userId = this.get("selUserId");
		this.loadSelUserDetails(userId);
	},
	saveCurrentEditMessage : function(){
		this.getMessageDetails();
		kendoKipHelperInstance.popupWarning("Successfully saved the changes.", "Success");
	},
	closeEditMessageView : function(){
		$("#taskMessageStrip").slideUp(500, function(){
			$("#hunterUserDetailsStrip").slideDown(500);
		});
		this.set("isMsgViewOpen", false);
	},
	opendEditMessageView : function(){
		$("#hunterUserDetailsStrip").slideUp(500,function(){
			$("#taskMessageStrip").slideDown(500);
		});
		this.set("isMsgViewOpen", true);
	},
	canceltEditMessageAndClose : function(){
		this.closeEditMessageView();
		var userId = this.get("selUserId");
		this.loadSelUserDetails(userId);
	},
	loadNewTaskEmailMessageView : function(task){
		$("#hunterUserDetailsStrip").slideUp(500,function(){
			$("#emailEditTemplateCover").slideDown(500,function(){
				hunterAdminClientUserVM.set("isEmailSectionOpen", true);
			});
		});
		var message = task.get("taskMessage");
		hunterAdminClientUserVM.setEmailMsgvalues(message);
	},
	loadNewTaskMessageView : function(taskId){
		
		console.log("loading create New Task Message view for task id >> " + taskId);
		hunterAdminClientUserVM.set("selTaskId", taskId);
		var task = this.get("hunterClientTaskGrid").dataSource.get(taskId);
		
		if(task.get("tskMsgType") === 'Email' ){
			console.log("Loading email section...");
			this.loadNewTaskEmailMessageView(task);
			return;
		}else{
			console.log("Message is not email. Loading ordinary message section...");
		}
		
		var msg = task.get("taskMessage");
		var delTskMsg = this.get("deleteCurMsgFlag");
		console.log("Deleted message ?  " + delTskMsg );
		// set it to null to it can load default message.
		msg = delTskMsg ? msg = null : msg; 
		this.get("createTextMessageManager").execute();  
	},
	clearCurrentTskMsg : function(){
		hunterAdminClientUserVM.set("taskMsgSendDate", null); 
		hunterAdminClientUserVM.set("taskMsgTypeVal",  null);
		hunterAdminClientUserVM.set("desiredReceivers",  null);
		hunterAdminClientUserVM.set("actualReceivers",  null);
		hunterAdminClientUserVM.set("confirmedReceivers",  null);
		hunterAdminClientUserVM.set("tskMsgId",  null);
		hunterAdminClientUserVM.set("tskMsgDelStatus",  null);
		hunterAdminClientUserVM.set("tskMsgLifeStatus",  null);
		hunterAdminClientUserVM.set("tskMstTxt",  null);
		hunterAdminClientUserVM.set("tskMsgCretDate",  null);
		hunterAdminClientUserVM.set("tskMsgCretBy",  null);
		hunterAdminClientUserVM.set("tskMsgLstUpdate",  null);
		hunterAdminClientUserVM.set("tskMsgLstUpdatedBy",  null);
		hunterAdminClientUserVM.set("tskMsgProvider",  null);
		hunterAdminClientUserVM.set("tskMsgOwner",  null);
	},
	updateTaskMsgFields : function(data){
		console.log("Date before : " + data["msgSendDate"]); 
		var date = kendo.parseDate(data["msgSendDate"]);
		console.log("Converted date " + date);
		hunterAdminClientUserVM.set("taskMsgSendDate", new Date(data["msgSendDate"])); 
		hunterAdminClientUserVM.set("taskMsgTypeVal", data["msgTaskType"]);
		hunterAdminClientUserVM.set("desiredReceivers", data["desiredReceivers"]);
		hunterAdminClientUserVM.set("actualReceivers", data["actualReceivers"]);
		hunterAdminClientUserVM.set("confirmedReceivers", data["confirmedReceivers"]);
		hunterAdminClientUserVM.set("tskMsgId", data["msgId"]);
		hunterAdminClientUserVM.set("tskMsgDelStatus", data["msgDeliveryStatus"]);
		hunterAdminClientUserVM.set("tskMsgLifeStatus", data["msgLifeStatus"]);
		hunterAdminClientUserVM.set("tskMstTxt", data["msgText"]);
		hunterAdminClientUserVM.set("tskMsgCretDate", data["cretDate"]);
		hunterAdminClientUserVM.set("tskMsgCretBy", data["createdBy"]);
		hunterAdminClientUserVM.set("tskMsgLstUpdate", data["lastUpdate"]);
		hunterAdminClientUserVM.set("tskMsgLstUpdatedBy", data["lastUpdatedBy"]);
		hunterAdminClientUserVM.set("tskMsgProvider", data["provider"]);
		hunterAdminClientUserVM.set("tskMsgOwner", data["msgOwner"]);
		
		var statusDropdownList = $("#tskMsgStatusDropdownList").data("kendoDropDownList");
		statusDropdownList.value(data["msgLifeStatus"]["statusValue"]);
		statusDropdownList.refresh();
		
		var statusDropdownList = $("#msgMsgType").data("kendoDropDownList");
		statusDropdownList.value(data["msgTaskType"]);
		statusDropdownList.refresh();
		
	},
	getMessageDetails : function(){
		var clientBean = this.get("clientBean"); 
		console.log("Client Bean > " + JSON.stringify(clientBean));
		var msgDetails = {
			"msgSendDate" : hunterAdminClientUserVM.get("taskMsgSendDate"),
			"msgTaskType" : hunterAdminClientUserVM.get("taskMsgTypeVal") != null ? hunterAdminClientUserVM.get("taskMsgTypeVal")["value"] : null, 
			"desiredReceivers" : hunterAdminClientUserVM.get("desiredReceivers"),
			"actualReceivers" : hunterAdminClientUserVM.get("actualReceivers"),
			"confirmedReceivers" : hunterAdminClientUserVM.get("confirmedReceivers"),
			"msgId" : hunterAdminClientUserVM.get("tskMsgId"),
			"msgDeliveryStatus" : hunterAdminClientUserVM.get("tskMsgDelStatus"),
			"msgLifeStatus" : hunterAdminClientUserVM.get("tskMsgLifeStatus"),
			"msgText" : hunterAdminClientUserVM.get("tskMstTxt"),
			"text" : hunterAdminClientUserVM.get("tskMstTxt"),
			"cretDate" : hunterAdminClientUserVM.get("tskMsgCretDate"),
			"createdBy" : hunterAdminClientUserVM.get("tskMsgCretBy"),
			"lastUpdate" : hunterAdminClientUserVM.get("tskMsgLstUpdate"),
			"lastUpdatedBy" : hunterAdminClientUserVM.get("tskMsgLstUpdatedBy"),
			"provider" : hunterAdminClientUserVM.get("tskMsgProvider"),
			"msgOwner" : hunterAdminClientUserVM.get("tskMsgOwner")
		};
		var data = JSON.stringify(msgDetails);
		console.log("Successfully obtained message details :  \n " + data);
		var taskId = hunterAdminClientUserVM.get("selTaskId"); 
		console.log("Selected task Id : " + taskId); 

		$.ajax({
			url: baseUrl + "message/action/tskMsg/create/" + taskId,
		      data : data,
		      method: "POST",
		      contentType : "applicaiton/json",
		      dataType : "json"
		}).done(function(data) {
			//var json = jQuery.parseJSON(data);
			//alert(json);
		 }).fail(function(data) {
			 var json = jQuery.parseJSON(data);
			 console.log("Failed to process task !!!!!!!");
			 console.log(json);
			 kendoKipHelperInstance.popupWarning(data.statusText + " (" + json.status + ")", "Error");
		 });
		
	},
	displayProcessJobWindow : function(){
		var r = $.Deferred();
		var container = $("#taskProcessJobDetailsTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(container,"Task Process Progress");
		return r;
	},
	afterFetchingTaskProcessJobResults : function(data){
		if(data != null){
			data = $.parseJSON(data);
			if(data.length != 0){
				$("#taskProcessWorkerContainer").html("");
				for(var i=0; i<data.length;i++){
					var job = data[i];
					var workers = job["workerJsons"]; 
					var template = kendo.template($("#taskProcessJobWorkerTemplate").html());
					template = template(workers);
					$("#taskProcessWorkerContainer").append(template); 
				}
			}else{
				$("#taskProcessJobLoadingIcon").html("No process data found for Task!");
				$("#taskProcessJobLoadingIcon").css({"color":"red"}); 
			}
		}else{
			$("#taskProcessJobLoadingIcon").html("No process data found for Task!"); 
			$("#taskProcessJobLoadingIcon").css({"color":"red"}); 
		}
		kendo.ui.progress($("#taskProcessJobLoadingIcon"), false);
	},
	showTaskProcessJobDetails : function(taskId){
		this.displayProcessJobWindow();
		$("#taskProcessWorkerContainer").closest(".k-window").css({top: 55,left: 450});
		$("#taskProcessWorkerContainer").html($("#taskProcessJobNoDataFoundTemplate").html());
		 kendo.ui.progress($("#taskProcessJobLoadingIcon"), true);
		 setTimeout(function(){ 
			 var url = baseUrl + "task/action/task/process/results/" + taskId;
			 kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url , "hunterAdminClientUserVM.afterFetchingTaskProcessJobResults");
		 }, 800);
	},
	showPopupForProcessTask : function(taskId){
		this.set("selTaskId", taskId);
		var desc = this.get("hunterClientTaskGrid").dataSource.get(taskId).get("description");
		var html = $("#processTaskProgressPopupTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(html,"Task Process Progress");
		$("#taskProcessPopupTaskName").html(desc);
	},
	showPopupForProcessedTaskSocialMsg : function(taskId){
		
		alert("This method has not been implemented yet!!!!!!!!!!!!!!!!!!");
		
		this.set("selTaskId", taskId);
		var desc = this.get("hunterClientTaskGrid").dataSource.get(taskId).get("description");
		/*
		var model = this.get("hunterClientTaskGrid").dataSource.get(taskId);
		var data  = JSON.stringify(model);
		var url = baseUrl + "task/action/processTask/" + taskId;
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(data, "application/json", "json", "POST", url , "hunterAdminClientUserVM.afterProcessTask");*/
		var html = $("#processTaskProgressPopupTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(html,"Task Process Progress");
		$("#taskProcessPopupTaskName").html(desc);
	},
	afterProcessTask : function(data){
		var json = jQuery.parseJSON(data);
		var status = json.status;
		if(status == "Failed"){
			var errors = json.errors;
			errors = replaceAll(errors, ",", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error Processing Task!","<span style='color:red;' >" + errors + "</span>");
		}else if(status == null || status === ""){ 
			kendoKipHelperInstance.showSimplePopup("Success!!","<span style='color:green;' >Successfully processed Task!</span>");
		}
		var ds = hunterAdminClientUserVM.get("hunterClientTaskGrid").dataSource;
		ds.read();
	},
	unhideProcessTaskSection : function(){
		
		var
		taskName = this.getSelectedTaskBean().get("taskName"), 
		taskType = hunterAdminClientUserVM.getSelTaskMsgType();
		taskId	 = this.getSelectedTaskId();
		
		if( taskType === 'Social' ){
			hunterAdminClientUserVM.get("socialProcessManager").execute(taskId, taskType, taskName);
			return;
		} 
		
		$("#taskProcessPrompPopup").fadeOut(50);
		$("#processTaskProgressPopup").closest(".k-window").animate({"top": "20%","left": "38%"},200,function(){
			$("#processTaskProgressPopup").toggle(800,function(){
				kendo.ui.progress($("#taskProcessProgressSpinner"), true);
				var html = $("#processTaskProgressPopupTemplate").html();
				$(".k-window-action k-link").css("visibility", "hidden");
				var taskProcessManager_ = hunterAdminClientUserVM.get("taskProcessManager");
				taskProcessManager_.execute();
			});
		});
	},
	closeProcessWindowAndRefresh : function(){
		kendoKipHelperInstance.closeWindowWithOnClose();
		this.get("hunterClientTaskGrid").dataSource.read();
	},
	performTaskNumericKendoConversions : function(){
		$("#taskTaskBudgetNumericInput").kendoNumericTextBox({
		    downArrowText: "Less"
		});
		$("#taskTaskCostNumericInput").kendoNumericTextBox({
		    downArrowText: "Less"
		});
		$("#desiredReceiverCountInput").kendoNumericTextBox({
		    downArrowText: "Less"
		});
	},
	createKendoWidgetsForTaskCloning : function(){
		$("#cloneTaskNewUser").kendoDropDownList({
            dataTextField: "fullName",
            dataValueField: "userName",
            dataPrimitive : true,
            dataSource: newOwnersNames()
		});
	},
	submitCloneRequest : function(){
		
		$(".cloneTaskFieldsErrors").remove();
		
		var taskName = $("#cloneTaskPTaskName").val();
		var taskDesc = $("#cloneTaskPTaskDesc").val();
		var userId = $("#cloneTaskNewUser").val();
		
		var taskNameMsg = null;
		var invalid = false;
		
		if(taskName == null || taskName.trim() === ''){
			taskNameMsg = "Task name is required!";
			invalid = true;
		}else if(taskName.length > 50){
			taskNameMsg = "Task name cannot be more than 50 characters";
			invalid = true;
		}
		
		if(taskNameMsg != null && taskNameMsg !== 'null'){ 
			$("#cloneTaskNameTr").after("<tr style='display:none;height:30px;' class='cloneTaskFieldsErrors' ><td></td><td style='background-color:#FCA59B;border-radius:3px;border:1px solid #B2584E;padding-left:3%;max-width: 220px;' >"+ taskNameMsg +"</td></tr>");
		}
		
		var taskDescMsg = null;
		
		if(taskDesc == null || taskDesc.trim() === ''){
			taskDescMsg = "Task description is required!";
			invalid = true;
		}else if(taskDesc.length > 100){
			taskDescMsg = "Task description cannot be more than 100 characters";
			invalid = true;
		}
		
		if(taskDescMsg != null && taskDescMsg !== 'null'){ 
			$("#cloneTaskDescTr").after("<tr style='display:none;height:30px;' class='cloneTaskFieldsErrors' ><td></td><td style='background-color:#FCA59B;border-radius:3px;border:1px solid #B2584E;padding-left:3%;max-width: 220px;' >"+ taskDescMsg +"</td></tr>");
		}
		
		if(invalid){
			$(".cloneTaskFieldsErrors").slideDown(200);
			return;
		}
		
		var taskId = this.get("selTaskId");
		var data = {"newOwner" : userId, "taskName" : taskName,"taskDescription" : taskDesc, "taskId" : taskId};
		data = JSON.stringify(data);
		var url = baseUrl + "task/action/task/clone";
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url , "hunterAdminClientUserVM.afterCloneTask");
	},
	afterCloneTask : function(data){
		data = jQuery.parseJSON(data);
		var status = data["status"]; 
		var message = data["message"];
		var duplicate = data["duplicate"];
		console.log("Duplicate : " + duplicate);
		
		if(duplicate != null && duplicate === 'true'){
			$("#cloneTaskDescTr").after("<tr style='display:none;height:30px;' class='cloneTaskFieldsErrors' ><td></td><td style='background-color:#FCA59B;border-radius:3px;border:1px solid #B2584E;padding-left:3%;max-width: 220px;' >"+ message +"</td></tr>");
			$(".cloneTaskFieldsErrors").slideDown(200);
			return;
		}
		
		kendoKipHelperInstance.showErrorOrSuccessMsg(status,message);
		kendoKipHelperInstance.closeWindowWithOnClose();
	},
	newUsersDetailsData : null,
	processCloning : function(){
		var name =  $("#startTaskClongingButton").text();
		if(name === 'Yes'){
			$(".k-widget k-tooltip k-tooltip-validation k-invalid-msg").css({"background-color" : "#FF9191"});
			$("#startTaskClongingLabel").text("Submit");
		}else{
			$("#startTaskClongingButton").unbind().bind("click", hunterAdminClientUserVM.submitCloneRequest());
			return;
		}
		var progBar = null;
		$(".cloneTaskHiddenFields").toggle(600, function(){
			progBar = $("#cloneTaskProgressBar").kendoProgressBar({
                type: "percent",
                animation: {
                    duration: 600
                }
            }).data("kendoProgressBar");
			progBar.value(20);
		});
		$(".cloneTaskWarningMessage").toggle();
		this.createKendoWidgetsForTaskCloning();
	},
	loadCloneTaskNewUserData : function(data){
		if(data != null && data === "LOAD_CLONE_USERS"){
			var url = baseUrl + "hunteruser/action/client/getAllClientsDetails";
			kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url , "hunterAdminClientUserVM.loadCloneTaskNewUserData");
		}else{
			data = $.parseJSON(data);
			hunterAdminClientUserVM.set("newUsersDetailsData", data);
		}
	},
	cloneSelTask : function(taskId){
		this.loadCloneTaskNewUserData("LOAD_CLONE_USERS");
		this.set("selTaskId", taskId);
		var container = "<div id='cloneTaskPopupContainer' style='width:400px;min-height:100px;' >"+  $("#cloneTaskPopupContainer").html() +"</div>";
		kendoKipHelperInstance.showWindowWithOnClose(container,"Task Process Progress");
		var taskName = hunterAdminClientUserVM.getSelectedTaskBean().get("taskName");
		$("#cloneTaskNameLabel").text(taskName);
	},
	createNewTask : function(){

		var 
		userId 	= this.get("selUserId"),
		url 	= HunterConstants.getHunterBaseURL("task/action/create/createTaskForCilentIdNew"),
		after 	= "hunterAdminClientUserVM.afterCreatingNewTask",
		taskId 	= hunterAdminClientUserVM.get("selTaskId"), 
		
		values 	= {
			
			"taskName" 		: $("#editTaskTemplateForm input[id='editTaskTemplateTaskName']").val(),
			"description"	: $("#editTaskTemplateForm input[id='editTaskTemplateTaskDescription']").val(),
			"taskType" 		: $("#editTaskTemplateForm input[id='editTaskTemplateTskType']").val(),
			"tskMsgType"	: $("#editTaskTemplateForm input[id='editTaskTemplateTskMsgType']").val(),
			"taskObjective"	: $("#editTaskTemplateForm textarea[id='editTaskTemplateTaskObjective']").val(),
			"recurrentTask"	: $("#editTaskTemplateForm input[id='editTaskTemplateRecurrentTask']").prop("checked"),
			"taskBudget"	: $("#editTaskTemplateForm input[id='editTaskTemplateBudget']").val(),
			"gateWayClient" : $("#editTaskTemplateForm input[id='editTaskTemplateTaskClient']").val(),
			
			"clientId"		: userId,
			"taskId"		: taskId == null || taskId === 'undefined' ? 0 : taskId, 
			"taskCost" 		: 0,
			
			"desiredReceiverCount"		: $("#editTaskTemplateForm input[id='editTaskTemplateDesiredReceivers']").val(),
		},
		
		valuesStr = JSON.stringify(values);
		kendo.ui.progress($("#editTaskTemplateForm"), true);
		
		setTimeout(function(){
			kendoKipHelperInstance.ajaxPostData(valuesStr, "application/json", "json", "POST", url , after );
		}, 1500);
		
	},
	afterCreatingNewTask : function(data){
		var dataStr = data+"";
		var dataJson = jQuery.parseJSON(dataStr);
		var status = dataJson["status"]; 
		var message = dataJson["message"];
		if(status != null && message != null){
			if(status !== "Failed"){
				kendoKipHelperInstance.popupWarning(status + " : " + message, "Success");
			}else{
				kendoKipHelperInstance.showErrorNotification(status + " : " + message);
			}
		}	
		this.closeProcessWindowAndRefresh();
	},
	getAndValidateNewTaskValues:function(){
		var validator = $("#editTaskTemplateForm").kendoValidator({
			messages:{
				custom : function(input){
					var name	= input.attr("name");
					console.log( name );
					if( name == "editTaskTemplateTskMsgType" || name == "editTaskTemplateTaskClient" || name == "editTaskTemplateTskType" ){
		        		   return "Task field is required." ;
		        	   } 
				},
				length : function(input){
					var name = $(input).attr("name");
		             if ( name === "editTaskTemplateTaskName" ) {
		            	 return "Task name is required and is less than 50 characters.";
		             }else if ( name === "editTaskTemplateTaskDescription" ) {
		            	 return "Task description is required and is less than 100 characters.";
		             }else if ( name == "editTaskTemplateTaskObjective" ){
		            	 return "Task objective is required and is less than 2000 characters.";
		             }
				},
				specialCharacters : function(input){
					return "Only alphanumeric, comma, colon, semi colon and period are allowed!";
				},
				required : "Required field!"
			},
			rules: {
	           custom: function(input) {
	        	   
	        	   var 
	        	    value 	= input.val(), 
	        	    name	= input.attr("name");
	        	   
	        	   if( name == "editTaskTemplateTskMsgType" || name == "editTaskTemplateTaskClient" || name == "editTaskTemplateTskType" ){
	        		   return !( value == null || value === "undefined" || value.trim().length == 0 ); 
	        	   } 

	        	   return true;
	           },
	           length : function(input){
	        	   	
	        	    var 
	        	    value 	= input.val(), 
	        	    name	= input.attr("name"); 
		        	
		            if ( name === "editTaskTemplateTaskName" || name === "editTaskTemplateTaskDescription" ) {
		            	return !( value == null || value.trim().length == 0 || value.trim().length >= 50 ) ;
		            }else if( name === "editTaskTemplateTaskObjective" ){
		            	return !( value.length == 0 || value.length > 2000 );
		            }
		            
		            return true;
	           },
	           specialCharacters : function(input){
	        	   
	        	   var 
	        	   name = $(input).attr("name"),
	        	   value = input.val(),
	        	   pass =  new RegExp(/^[a-zA-Z0-9. ,:;]+$/).test( value ),
	        	   objctv =  new RegExp(/^[a-zA-Z0-9. '",:;]+$/).test( value );
	        	   
				  if( name == "editTaskTemplateTaskName" || name == "editTaskTemplateTaskDescription"){
					return pass;
				  }else if( name == "editTaskTemplateTaskObjective"  ){
					  return objctv;
				  }
					
				  return true;
	           }
	         }
		}).data("kendoValidator"); 
		
		var isValid = validator.validate();
		
		if( isValid ){
			this.createNewTask();
		}
		
	},
	
	newTaskTypeSelected : "Email",
	modifiedClientsForMsgType : "Email",
	
	launchTaskEditView : function(action){
		
		var 
		actionName 	= action.split(":")[0],
		taskId 		= action.split(":")[1],
		html 		= $("#editTaskNewTemplate").html(),
		selTaskBean = null;
		
		if( taskId !== "undefined" ){
			this.set("selTaskId",taskId);
			selTaskBean = hunterAdminClientUserVM.getSelectedTaskBean();
		}else{
			this.set("selTaskId",null);
		}
		
		console.log(actionName + " : " + taskId);
		
		kendoKipHelperInstance.showWindowWithOnClose(html, "Task Details");
		$("#editTaskTemplateForm td").css({"height":"1.4em"}); 
		
		var tskTypeDropdownList = $("#editTaskTemplateTskType").kendoDropDownList({
			value : hunterAdminClientUserVM.newTaskTypeSelected,
			dataSource:HunterConstants.TASK_TYPES_ARRAY,
			dataTextField : "text",
			optionLabel : "Select Task Type",
			dataValueField : "value"
		}).data("kendoDropDownList");
		
		var applicableClients = [{"text" : "Hunter Email", "value" : "Hunter Email"}];
		
		var tskMsgTypeDropdown = $("#editTaskTemplateTskMsgType").kendoDropDownList({
			dataSource:HunterConstants.TASK_MSG_TYP_ARRAY,
			dataTextField : "msgTypText",
			dataValueField : "msgTypVal",
			optionLabel : "Select Task Message Type",
			change:function(e){
				
				var value = this.value(),
					clients = null;
				
				if( value === "Email" ){
					clients = 
					[
					 	{"text" : "Hunter Email", "value" : "Hunter Email"}
				    ];
				}else if( value === "Text"){
					clients = 
						[
						 	{"text" : "CM", "value" : "CM"},
						 	{"text" : "Ozeki", "value" : "OZEKI"},
						 	{"text" : "Safaricom", "value" : "SAFARICOM"}
					    ];
				}else if( value === "Voice Mail"){
					clients = 
					[
					 	{"text" : "CM", "value" : "CM"},
					 	{"text" : "Ozeki", "value" : "OZEKI"},
					 	{"text" : "Safaricom", "value" : "SAFARICOM"}
				    ];
				}else if( value === "Social"){
					clients = 
					[
					 	{"text" : "Hunter Social", "value" : "Hunter Social"}
				    ];
				}
				
				$("#editTaskTemplateTaskClient").data("kendoDropDownList").setDataSource(clients); 
			}
		}).data("kendoDropDownList");

		var taskClientDropdownList = $("#editTaskTemplateTaskClient").kendoDropDownList({
			dataSource: {
			    data: applicableClients
			},
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : "Select Task Client",
			change:function(){
				
			}
		}).data("kendoDropDownList"); 
		
		 var budgetNumeric = $("#editTaskTemplateBudget").kendoNumericTextBox({
			 value : 0.00,
			 min : 0.00,
			 max : 2500000,
		 }).data("kendoNumericTextBox"); 
		 
		 
		 var desiredRcvrsNumeric = $("#editTaskTemplateDesiredReceivers").kendoNumericTextBox({
			 value : 1,
			 min: 0,
			 decimals: false,
			 max : 1000000,
		 }).data("kendoNumericTextBox"); 
		 
		 
		 if(actionName === 'editTask' ){
			 
			$("#editTaskTemplateForm input[id='editTaskTemplateTaskName']").val( selTaskBean.get("taskName") ); 
			$("#editTaskTemplateForm input[id='editTaskTemplateTaskDescription']").val( selTaskBean.get("description") );
			$("#editTaskTemplateForm input[id='editTaskTemplateRecurrentTask']").prop("checked", selTaskBean.get("recurrentTask")); 
			$("#editTaskTemplateForm textarea[id='editTaskTemplateTaskObjective']").val( selTaskBean.get("taskObjective") ); 
			tskMsgTypeDropdown.value( selTaskBean.get("tskMsgType") ); 
			tskTypeDropdownList.value( selTaskBean.get("taskType") );
			taskClientDropdownList.value( selTaskBean.get("gateWayClient") );
			budgetNumeric.value( selTaskBean.get("taskBudget") );
			desiredRcvrsNumeric.value( selTaskBean.get("desiredRcvrsNumeric") );
			
	     }
		
		
	},
	getApplicableClientsForMsgType : function(){
		var msgTyp = hunterAdminClientUserVM.get("newTaskTypeSelected");
		if( msgTyp === "Email" ){
			return 
			[
			 	{"text" : "Hunter Email", "value" : "Hunter Email"}
		    ];
		}else if( msgTyp === "Text"){
			return 
			[
			 	{"text" : "CM", "value" : "CM"},
			 	{"text" : "Ozeki", "value" : "OZEKI"},
			 	{"text" : "Safaricom", "value" : "SAFARICOM"}
		    ];
		}else if( msgTyp === "Voice Mail"){
			return 
			[
			 	{"text" : "CM", "value" : "CM"},
			 	{"text" : "Ozeki", "value" : "OZEKI"},
			 	{"text" : "Safaricom", "value" : "SAFARICOM"}
		    ];
		}else if( msgTyp === "Social Message"){
			return 
			[
			 	{"text" : "Hunter Social", "value" : "Hunter Social"}
		    ];
		}
	},
	createTasksGrid : function(json){
		
		//numericize input
		this.performTaskNumericKendoConversions();
		
		// destroy pre-existing widgets, first. 
		this.destroyHunterClientTaskGrid();
		console.log("Creating user grid..");
		var tasksDS_ = this.get("hunterTaskDS");
		
		var hunterClientTaskGrid_ = $("#selectedUserClientTasks").kendoGrid({
			dataSource : tasksDS_,
			toolbar : kendo.template($("#hunterClientTasksToolBar").html()),
			pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            height: 350,
            sortable: true,
            columns: [
               { field: "taskId", title: "ID", width: 30 },
               { field: "taskName", title: "Name", width: 150 },
               { field: "taskType", title: "Task Type", width: 80},
               { field: "gateWayClient", title: "Client", width: 90 },
               { field: "tskMsgType", title: "Type", width: 80 },
               { field: "cretDate", title: "Created On", width: 120 },
               { field: "createdBy", title: "Created By", width: 120 },
               { field: "lastUpdate", title: "Last Motified", width: 120 },
               { field: "updatedBy", title: "Motified By", width: 90 },
               { field: "taskDeliveryStatus", title: "Progress", width: 80 , template : "#=getProgressTemplate()#" },
               { field: "taskLifeStatus", title: "Status", width: 70, template : "#=getTaskEditStatusTemplate()#"},
               { field: "message", title: "Message", width: 70 , template : "#=getTaskMessageTemplate()#" },
               { field: "taskRegion", title: "Region", width: 60,template : "#=getTaskRegionTemplate()#"  },
               { field: "loadDetails", title : "Details", width:60, template : "#=getTaskLoadDetailsTemplate()#"},
               { "name": "taskHistory", title : "History", width:60, template : "#=getTaskHistoryTemplate()#"},
               { "name": "cloneTask", title : "clone", width:60, template : "#=getCloneTaskTemplate()#"},
               { "name": "editTask", title : "Edit", width:60, template : "#=getEditTaskTemplate()#"}, 
               { "name": "Delete", "title": "Delete","width": 60, template : "#=getTaskDeleteTemplate()#", filterable: false,resizable: false  },
               { "name": "Process", "title": "Process","width": 60, template : "#=getTaskProcessTemplate()#", filterable: false,resizable: false  }
               
            ]
		}).data("kendoGrid"); 
		this.set("hunterClientTaskGrid", hunterClientTaskGrid_);  
		console.log("Successfully created hunter client task grid!!!"); 
		this.prepareTaskGridPopupTemplate();
		
		//this.slideUpTaskTabs();
	},
	prepareTaskGridPopupTemplate : function(){
		var dataSource = hunterAdminClientUserVM.get("taskTypeDs");
		 $("#taskTypeInput").kendoDropDownList({
	            dataTextField: "text",
	            dataValueField: "value",
	            dataSource: dataSource.data // bind it to the brands array
	        });
		 console.log("Preparing task type input!!");
	},
	showDetails : function(id) {
		var model = this.get("hunterClientTaskGrid").dataSource.get(id);
        var detailsTemplate = kendo.template($("#hunterClientTasksPopupViewTemplate").html());
        var content = detailsTemplate(model);
        var div = "<div id='hunterClientTasksPopupViewTemplate_' style='width:600px;min-height:500px;' ></div>";
        kendoKipHelperInstance.showWindowWithOnClose(div, "Task Details");
        $("#hunterClientTasksPopupViewTemplate_").html(content);
    },
	createUserGrid : function(){
		console.log("Creating user grid..");
		var userGrid = $("#hunterUserGrid").kendoGrid({
			dataSource : hunterUserDS,
			pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            height: 300,
            sortable: true,
            editable: {
				mode: "popup",
				template: kendo.template($("#hunterUserEditTemplate").html())
            },
            columns: [
               { field: "", width:'40px',title : '', 'template' : '<input id=hunterTaskClientUser_#=userId# type=checkbox onClick=hunterAdminClientUserVM.loadSelUserDetails(#=userId#)></input>'}, 
               { field: "firstName",title: "First Name" }, 
               { field: "lastName", title: "Last Name" }, 
               { field: "email", title: "Email", width: 300  }, 
               { field: "userName", title: "User Name" },
               { field: "phoneNumber", title: "Phone Number" }, 
	           { field: "cretDate", title: "Created On" }, 
	           { field: "createdBy", title: "Created By" }, 
	           { field: "lastUpdate", title: "Last Updated On" }, 
	           { field: "lastUpdatedBy", title: "Updated By", width: 150 }
	           /* { field: "client", title: "Client", width: 60, template : "#=getClientTemplate()#" }, */
	           /*{ field: "editTemplate", title: "Edit", width: 60, template : "#=getEditTemplate()#" },*/
	           /*{ "name": "Delete", "title": "Delete","width": 60, template : "#=getHunterUserDeleteButton()#", filterable: false,resizable: false  }*/
	           
            ]
		}).data("kendoGrid"); 
		this.set("userGrid", userGrid);  
		console.log("Successfully created hunter user!!!"); 
	},
	
	/* This is purely for task groups */
	receiverGroupDS : new kendo.data.DataSource({
		  transport: {
		    read:  {
		      url: function(){
		    	  var base = baseUrl + "task/action/tskGrp/read/";
		    	  var taskid = hunterAdminClientUserVM.get("selTaskId");
		    	  if(taskid == null){
		    		  taskid = 0;
		    	  }
		    	  var url = base + taskid;
		    	  console.log("Receiver group read url for task : " + url);
		    	  return url;
		      },
		      dataType: "json",
		      contentType:"application/json",
		      method: "POST"
		    },
		    create: {
		        url : function(){
			    	  var base = baseUrl + "task/action/tskGrp/create/";
			    	  var taskid = hunterAdminClientUserVM.get("selTaskId");
			    	  var url = base + taskid;
			    	  console.log("Receiver group create url for task : " + url);
			    	  return url;
			      },
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST",
		        success: function(result) {
		            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
		         }
		    },
		    destroy: {
		    	 url : function(){
			    	  var base = baseUrl + "task/action/tskGrp/destroy/";
			    	  var taskid = hunterAdminClientUserVM.get("selTaskId");
			    	  var url = base + taskid;
			    	  console.log("Receiver group destroy url for task : " + url);
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
		    	model:ReceiverGroupModel
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
		        if(type === 'read')
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
		        
		        if(message != null){
		        	kendoKipHelperInstance.popupWarning(message, "Success");
		        }
		  },
		  pageSize:1000
	}),
	addGroupToTask : function(){
		var taskId = this.get("selTaskId");
		var selTaskGroup = this.get("taskGroupDropDownSelVal");
		var data = [{"taskId" : taskId, "groupId" : selTaskGroup}];
		var url = baseUrl + "task/action/tskGrp/create";
		data = JSON.stringify(data);
		console.log("Loading group dropdown data...");
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url , "hunterAdminClientUserVM.afterAddingGroupToTask");
	},
	afterAddingGroupToTask : function(data){
		var dataStr = data+"";
		var dataJson = jQuery.parseJSON(dataStr);
		var status = dataJson["status"]; 
		var message = dataJson["Message"];
		var groupReceiverCount = dataJson["groupReceiverCount"];
		if(status != null && message != null){
			if(status !== "Failed"){
				kendoKipHelperInstance.popupWarning(status + " : " + message, "Success");
				var tskGrpDs = this.get("receiverGroupDS");
				tskGrpDs.read();
				hunterAdminClientUserVM.set("taskGroupsTotalReceivers", groupReceiverCount);
			}else{
				kendoKipHelperInstance.showErrorNotification(status + " : " + message);
			}
		}		
	},
	loadGroupDropdown : function(taskId){
		var url = baseUrl + "messageReceiver/action/group/dropDown/" + taskId;
		console.log("Loading group dropdown data...");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url , "hunterAdminClientUserVM.afterLoadingGroupDropdown");
		console.log("Successfully loaded group dropdown data!!");
	},
	afterLoadingGroupDropdown : function(data){
		var json = jQuery.parseJSON(data);
		console.log(JSON.stringify(json));
		hunterAdminClientUserVM.set("taskGroupDropdownData",json);
	},
	showPopupToDeleteTaskGroup : function(id, message){
		kendoKipHelperInstance.showOKToDeleteItem(id, message,"hunterAdminClientUserVM.deleteSelTaskGroup");
	},
	deleteSelTaskGroup : function(id){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var taskId = this.get("selTaskId");
		var data = [{"taskId" : taskId, "groupId" : id}];
		data = JSON.stringify(data);
		$.ajax({
			url: baseUrl + "task/action/tskGrp/destroy",
		    method: "POST",
		    data : data,
		    dataType : "json", 
		    contentType : "application/json"
		}).done(function(data) {
			var dataStr = data+"";
			var dataJson = jQuery.parseJSON(dataStr);
			var status = dataJson["status"]; console.log(status);
			var message = dataJson["Message"];console.log(message); 
			var groupReceiverCount = dataJson["groupReceiverCount"];console.log(groupReceiverCount);
			if(status != null && message != null){
				kendoKipHelperInstance.popupWarning(status + " : " + message, "Success");
				if(status !== "Failed"){
					hunterAdminClientUserVM.set("taskGroupsTotalReceivers", groupReceiverCount);
					hunterAdminClientUserVM.getAllReceiversForTaskReceivers();
				}
			}else{
				kendoKipHelperInstance.popupWarning("<span style='color:yellow;' >Finished process. Status unknown!</span>", "Success");
			}	
		 }).fail(function(data) {
			 var json = jQuery.parseJSON(data);
			 kendoKipHelperInstance.popupWarning("Failed to load task receiver count ! " + data.statusText + " (" + json.status + ")", "Error");
		 });
		var tskGrpDs = this.get("receiverGroupDS");
		tskGrpDs.read();
		hunterAdminClientUserVM.getAllReceiversForTaskReceivers();
		
	},
	
	/* This is purely for region VM */
	
	isCountryDropdownEnabled : true, 
	isCountyDropdownEnabled : true,
	isStateDropdownEnabled : false,
	isConstituencyDropdownEnabled : true,
	isConstituencyWardDropdownEnabled : true,
	
	isCountryDropdownVisible : true, 
	isCountyDropdownVisible : true,
	isStateDropdownVisible : false,
	isConstituencyDropdownVisible : true,
	isConstituencyWardDropdownVisible : true,
	
	selCountry : "UNSELECTED", 
	selState : "UNSELECTED", 
	selCounty : "UNSELECTED", 
	selConstituency : "UNSELECTED", 
	selConstituencyWard : "UNSELECTED", 
	hasState : "UNSELECTED",
	
	beforeInitRegionVM : function(){
		console.log("Before initializing task region VM..");
	},
	initRegionVM : function(){
		this.beforeInitRegionVM();
		console.log("initializing task region VM..");
		this.afterInitRegionVM();
	},
	afterInitRegionVM : function(){
		console.log("Successfully finished initializing task region VM!!");
		this.get("countryDS").read();
		this.get("countyDS").read();
		this.get("constituencyDS").read();
		this.get("constituencyWardDS").read();
		//this.refreshTaskRegionDS();
	},
	countryDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: baseUrl + "region/action/countries/read",
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading countries...");
	     }
	}),
	countyDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: function(){
      	    	  var url =  baseUrl + "region/action/counties/read/" + hunterAdminClientUserVM.get("selCountry");
      	    	  console.log("CountyDS URL >> " + url);
      	    	  return url;
      	      }, 
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading counties...");
	     }
	}),
	constituencyDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: function(){
      	    	  var url = baseUrl + "region/action/constituencies/read/" + hunterAdminClientUserVM.get("selCounty");
      	    	  console.log("ConstituencyDS URL >> " + url);
      	    	  return url;
      	      },
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading constituencies...");
	     }
	}),
	constituencyWardDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: function(){
      	    	  var url = baseUrl + "region/action/constituencyWards/read/" + hunterAdminClientUserVM.get("selConstituency");
      	    	  console.log("ConstituencyWardDS URL >> " + url);
    	    	  return url;
      	      },
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading constituencywards...");
	     }
	}),
	onChangeCountry : function(e){
		
		hunterAdminClientUserVM.resetValues(hunterAdminClientUserVM.get("selCountry"),"UNSELECTED", "UNSELECTED", "UNSELECTED"); 
		hunterAdminClientUserVM.set("isCountyDropdownEnabled", true);
		
		hunterAdminClientUserVM.get("countyDS").read();
		hunterAdminClientUserVM.get("constituencyDS").read();
		hunterAdminClientUserVM.get("constituencyWardDS").read();
		
	},
	onChangeCounty : function(){
		hunterAdminClientUserVM.resetValues(hunterAdminClientUserVM.get("selCountry"),hunterAdminClientUserVM.get("selCounty"), "UNSELECTED", "UNSELECTED");
		hunterAdminClientUserVM.set("isConstituencyDropdownEnabled", true);
		console.log("Selected county >> " + hunterAdminClientUserVM.get("selCounty"));		
		
		hunterAdminClientUserVM.get("constituencyDS").read();
		hunterAdminClientUserVM.get("constituencyWardDS").read();
	},
	onChangeConstituency : function(){
		hunterAdminClientUserVM.resetValues(hunterAdminClientUserVM.get("selCountry"),hunterAdminClientUserVM.get("selCounty"), hunterAdminClientUserVM.get("selConstituency"), "UNSELECTED"); 
		hunterAdminClientUserVM.set("isConstituencyWardDropdownEnabled", true);
		console.log("Selected constituency >> " + this.get("selConstituency")); 
		
		hunterAdminClientUserVM.get("constituencyWardDS").read();
	},
	onChangeConsituencyWard : function(e){
		hunterAdminClientUserVM.resetValues(hunterAdminClientUserVM.get("selCountry"),hunterAdminClientUserVM.get("selCounty"), hunterAdminClientUserVM.get("selConstituency"), hunterAdminClientUserVM.get("selConstituencyWard"));
		console.log("selConstituencyWard >> " + hunterAdminClientUserVM.get("selConstituencyWard")); 
	},
	resetValues : function(selCountry, selCounty, selConstituency, selConstituencyWard){
		console.log("Resetting values ... ("+ selCountry + "," + selCounty  + "," + selConstituency + "," + selConstituencyWard + ")");
		hunterAdminClientUserVM.set("selCountry",selCountry);
		hunterAdminClientUserVM.set("selCounty",selCounty);
		hunterAdminClientUserVM.set("selConstituency",selConstituency);
		hunterAdminClientUserVM.set("selConstituencyWard",selConstituencyWard);
	},
	onDataConsWardBound : function(e){
		
	},
	getSelectedTaskBean : function(){
		var selTaskId = this.get("selTaskId"); 
		var task = this.get("hunterClientTaskGrid").dataSource.get(selTaskId);
		return task;
	},
	getSelectedTaskBeanMsg : function(){
		return this.getSelectedTaskBean().get("taskMessage");
	},
	
	
	/*::::::::::::::::::::    This is purely for selected task regions grid       :::::::::::::::::::::::::::::*/
	
	selTaskId : null,
	taskUniqueReceivers : 0,
	isTaskRegionOpen : false,
	isMsgViewOpen : false,
	
	selRegionContactsPageCount : 200,
	selRegionContactsPageNumber : 100,
	selRgnCntctsData : [],
	
	prepareStageToShowContacts : function(params){
		var content = $("#selRegionMessgReceiverGridTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Receivers of regions params : " + params);
		$("#selRegionMessgReceiverGridContainer").css({"height":"500px"});
		kendo.ui.progress($("#contactsSpinner"), true);
		setTimeout(function(){
			kendoKipHelperInstance.showReceiversGridForRegionOrGroup(params);
		}, 800);
	},
	getSelTaskMsgType : function(){
		var taskBean = hunterAdminClientUserVM.getSelectedTaskBean(),
		msgType = taskBean.get("tskMsgType");
		return msgType;
	},
	displayContactsForGroupId : function(groupId){
		var msgType = this.getSelTaskMsgType();
			params = "RECEIVER_GROUP_ID::" + groupId + "::" + msgType;
		console.log( params );
		this.prepareStageToShowContacts(params);
	},
	displayContactsForRegionId : function(params ){
		params = params + "::" + this.getSelTaskMsgType();
		console.log( params );
		this.prepareStageToShowContacts(params);
	},
	getSelRegionPageSize : function(){
		var current = hunterAdminClientUserVM.get("selRegionContactsPageCount");
		if( current == null || current == 0 ){
			return 100;
		}
		return current;
	},
	getSelectedTaskId : function(){
		var taskId = hunterAdminClientUserVM.get("selTaskId");
		if(taskId == null)
			taskId = "null";
		return taskId;
	},
	selTaskRegionsDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        refreshable:true,
        height:200,
        transport: {
	       	read:  {
	      	      url: function(){
	      	    	  var url = baseUrl + "region/action/task/regions/read/" + hunterAdminClientUserVM.getSelectedTaskId();
	      	    	  console.log("Selected task region URL >> " + url);
	    	    	  return url;
	      	      },
	      	      dataType: "json",
	      	      method: "POST"
	       	 },
	       	 destroy : {
	       		url : function(){
	       			var taskId = hunterAdminClientUserVM.getSelectedTaskId();
	       			if(taskId == null) tskId = "0";
	       			var url = baseUrl + "region/action/task/regions/delete/" + taskId;
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
		        	  return json;
		          }else{
		        	  var json = kendo.stringify(options); 
		        	  return json;
		          }
		    }
        },
        requestEnd: function(e) {
	        var type = e.type;
	        if(type === 'destroy'){
	        	hunterAdminClientUserVM.getTotalReceivers();
	        	hunterAdminClientUserVM.getAllReceiversForTaskReceivers();
	        }
	  },
  	  	schema: {
  	    	model:taskRegionsModel
  	  	},
  	  	aggregate: [
  	  	  { field: "receiverCount", aggregate: "sum" }
        ],
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading constituencywards...");
	     }
	}),
	onSaveSelTaskRegion : function(){
		alert("Saved task region!!"); 
	},
	deleteSelectedTaskRegion : function(regionId){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var taskId = this.get("selTaskId");
		var url = baseUrl+"/region/action/task/regions/delete/requestBody";
		var data = {"regionId" : regionId, "taskId" : taskId};
		data = JSON.stringify(data);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url , "hunterAdminClientUserVM.afterDeleteSelectedTaskRegion");
	},
	afterDeleteSelectedTaskRegion : function(data){
		data = jQuery.parseJSON(data);
		if(data != null){
			var status = data[HunterConstants.STATUS_STRING];
			var message = data[HunterConstants.MESSAGE_STRING];
			var count = data["regionCount"]; 
			if(count != null){
				hunterAdminClientUserVM.set("taskUniqueReceivers", count);
			}
			kendoKipHelperInstance.popupWarning(status + " : " + message, "Success"); 
		}else{
			kendoKipHelperInstance.popupWarning("<span style='color:yellow;' >Finished process. Status unknown!</span>", "Success");
		}
		var ds = hunterAdminClientUserVM.get("selTaskRegionsDS");
		ds.read();
		hunterAdminClientUserVM.getAllReceiversForTaskReceivers();
	},
	getTotalReceivers : function(){
		var taskId = this.get("selTaskId");
		if(taskId == null) taskId = 0;
		console.log("Loading unique count for task Id : " + taskId);
		$.ajax({
			url: baseUrl + "region/action/task/regions/receivers/uniqueCount/" + taskId,
			dataType : "json",
			method: "POST"
		}).done(function(data) {
			hunterAdminClientUserVM.set("taskUniqueReceivers", data);
			$("#taskUniqueReceiversSpan").text(data);
			console.log("Successfully loaded unique count for task Id : " + taskId + ". Count ( " + data + " )");
		 }).fail(function(data) {
			 var json = jQuery.parseJSON(data);
			 kendoKipHelperInstance.popupWarning("Failed to load task receiver count ! " + data.statusText + " (" + json.status + ")", "Error");
		 });
	},
	getTaskGroupsTotalReceivers : function(){
		var taskId = this.get("selTaskId");
		var ds = this.get("hunterClientTaskGrid").dataSource;
		var model = ds.get(taskId);
		var groups = model.get("taskGroups");
		var count = 0;
		if(groups != null && groups !== "undefined"){
			for(var i=0; i<groups.length; i++){
				var thisGroup = groups[i];
				var count_ = thisGroup["receiverCount"];
				count += count_;
			}
		}
		console.log("Total receivers for groups : " + count);
		this.set("taskGroupsTotalReceivers", count); 
	},
	getAllReceiversForTaskReceivers : function(){
		console.log("Updating combined receiver counts...");
		 this.getTotalReceivers();
		 var regionsCount = this.get("taskUniqueReceivers"); 
		 var groupsCount = this.get("taskGroupsTotalReceivers");
		 var total = regionsCount + groupsCount;
		 console.log("combined receivers : " + total);
		 this.set("taskAllCombinedReceivers",total);
		 console.log("Finished updating combined receiver counts!!"); 
	}, 
	
	
	
	
	
	
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::   This is only for email editing   :::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	
	
	
	
	
	taskEmailHtml : null,
	isEmailSectionOpen : null,
	isParamsSectionOpen : true,
	currentParamButtonText : "Show Parameters",
	emailTemplateName : null,
	emailMsgCretDate : '',
	emailMsgCretBy : '',
	emailMsgLstUpdate : '',
	emailMsgLstUpdatedBy : '',
	emailSubject : '',
	
	
	hasAttachment : true,
	isAttachmentPopupOpen : false,
	tskEmailMsgStatus : null,
	taskEmailMsgSendDate : null,
	msgDeliveryStatus : null,
	
	existentTemplateNames : [],
	messageAttachmentNameToBeEdited : null,
	currentTemplateIndx : -1,
	
	getNextTemplateName : function(){
    	var current = this.get("currentTemplateIndx");
    	if( (current + 1) >= this.get("existentTemplateNames").length){  
    		current = -1;
    	}
    	current += 1;
    	this.set("currentTemplateIndx", current);
    	var name = this.get("existentTemplateNames")[current];
    	return name;
    },
    getPrevTemplateName : function(){
    	var current = this.get("currentTemplateIndx");
    	if( (current - 1) <= 0){  
    		current = (this.get("existentTemplateNames").length);
    	}
    	current -= 1;
    	this.set("currentTemplateIndx", current);
    	var name = this.get("existentTemplateNames")[current];
    	return name;
    },
	loadExistentEmailTemplates : function(){
		var url = baseUrl + "message/action/tskMsg/email/getAllTemplateNames";
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST",url,"hunterAdminClientUserVM.afterFetchingAllTemplateNames" );
	},
	afterFetchingAllTemplateNames : function(data){
		var json = $.parseJSON(data);
		for(var i=0; i<json.length;i++){
			hunterAdminClientUserVM.get("existentTemplateNames").push(json[i]);
		}
		if( hunterAdminClientUserVM.get("existentTemplateNames").length == 0 ){
			kendoKipHelperInstance.showErrorOrSuccessMsg(HunterConstants.STATUS_FAILED, "No email templates found!");
		}
	},
	onChangeTaskEmailMsgSendDate : function(e){
		
	},
	saveEmailMessageConfigurations : function(){
		console.log("Saving email message configurations..."); 
		this.saveCurrentEditorContent();
	},
	templatedHtml : function(){
		return this.get("taskEmailHtml"); 
	},
	onChangeTaskEmailHtml: function() {
		
    },
    saveCurrentEditorContent : function(){
    	if(this.getWordCount() >= 4000 || this.getWordCount() == 0 ){
    		kendoKipHelperInstance.showErrorOrSuccessMsg("Error", "Email cannot be 0 or more than 4000 characters!");
    		return;
    	}
    	if(!this.validateSubject()){
    		kendoKipHelperInstance.showErrorOrSuccessMsg("Error", "Task subject is required.");
    		return;
    	}
    	if(this.get("emailTemplateName") == null || this.get("emailTemplateName").trim().length == 0){
    		kendoKipHelperInstance.showErrorOrSuccessMsg("Error", "Please select email template name before saving!");
    		return;
    	}
    	console.log("Saving contents... \n " + kendo.htmlEncode(this.get("taskEmailHtml") ));
    	var data = {
    			"msgDeliveryStatus" : this.get("msgDeliveryStatus"),
    			"tskId" : this.get("selTaskId"),
    			"emailTemplateName" : this.get("emailTemplateName"),
    			"hasAttachment" : this.get("hasAttachment"),
    			"tskEmailMsgStatus" : this.get("tskEmailMsgStatus"),
    			"taskEmailMsgSendDate" : this.get("taskEmailMsgSendDate"),
    			"taskEmailHtml" : this.get("taskEmailHtml"),
    			"emailSubject" : this.get("emailSubject")
    	};
    	data = JSON.stringify(data);
    	var url = baseUrl + "message/action/tskMsg/email/createOrUpdate";
    	kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST",url,"hunterAdminClientUserVM.afterSaveEmailMsgConfigs" );
    },
    afterSaveEmailMsgConfigs : function(data){
    	kendoKipHelperInstance.showErrorOrSuccessMsg("Success", "Configurations Saved!");
    	this.reloadMessageAttachmentContainer();
    },
    clearCurrentEditorContent : function(){
    	this.set("taskEmailHtml", "");
    	this.set("templatedHtml", "");
    },
    viewCurrentEditorContent : function(){
    	var isParamsSectionOpen = this.get("isParamsSectionOpen"); 
    	if(isParamsSectionOpen){
    		this.showEmailTemplateProcessed();
    	}
    	$("#emailTemplateProcessed").html(this.get("taskEmailHtml")); 
    },
    forwardTemplateMove : function(){
    	var templateName = this.getNextTemplateName();
    	this.loadForName(templateName);
    },
    loadForName : function(templateName){
    	this.updateTemplateNameTitle(templateName);
    	templateName = templateName.replace(/ /g, '+');
    	var url = baseUrl + "message/action/tskMsg/email/getEmailTemplateForName/" + templateName;
    	$( "#allHunterEmailTemplates" ).load( url , function(data) {
    		console.log("...............................Loaded..................................................");
    		var replaced = hunterAdminClientUserVM.sanitizeString(data);
    		var html = $( "#allHunterEmailTemplates" ).html();
    		$("#templateContentContainer").html(replaced);
    	});
    },
    sanitizeString : function(string){
    	var replaced = string.replace(/\\"/g, ':::_:::'); 
		replaced = replaced.replace(/"/g, '');
		replaced = replaced.replace(/:::_:::/g, '"');
		return replaced;
    },
    backwardTemplateMove : function(){
    	var templateName = this.getPrevTemplateName();
    	this.loadForName(templateName);
    },
    updateTemplateNameTitle : function(templateName){
    	$("#measuredWindowBackUp_wnd_title").html("<span style='text-align:center;font-weight:bolder;with:100%;' >" + templateName + "</span>");
    },
    displayAllCurrentTemplates : function(){
    	var templateName = this.getNextTemplateName();
    	if( templateName == null ){
    		kendoKipHelperInstance.showErrorOrSuccessMsg(HunterConstants.STATUS_FAILED, "Template is null!!"); 
    		return;
    	}
    	templateName = templateName.replace(/ /g, '+');
    	var url = baseUrl + "message/action/tskMsg/email/getEmailTemplateForName/" + templateName;
    	$( "#allHunterEmailTemplates" ).load( url , function() {
    		console.log("...............................Loaded..................................................");
    		var contents = $("#allHunterEmailTemplates").html();
    		var replaced = hunterAdminClientUserVM.sanitizeString(contents);
        	var part1 = "<div id='templateContentContainer' style='height:85%;with:96%;margin-left:1%;border:1px solid #A6CADE;border-radius:4px;overflow-y:scroll;padding:10px;'  >";
        	var part2 = 
        		"</div><table id='templatesInnerTable' style='with:40%;margin:0 auto;table-layout: fixed;' ><tr><td><a href='#' onClick='hunterAdminClientUserVM.backwardTemplateMove()' class='ui-btn ui-corner-all ui-icon-arrow-l ui-btn-icon-notext'></a></td><td>"+
        		"<button class='k-button' style=''background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' onClick='hunterAdminClientUserVM.selectEmailTemplate()' ><span class='k-icon k-i-tick'></span>Select</button></td>"+
        		"<td><button class='k-button' style=''background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' onClick='hunterAdminClientUserVM.closeTemplatesWindow()' ><span class='k-icon k-i-close'></span>Close</button></td>"+
        		"<td><a href='#' class='ui-btn ui-corner-all ui-icon-arrow-r ui-btn-icon-notext' onClick='hunterAdminClientUserVM.forwardTemplateMove()' ></a></td></tr></table>";
        	var html = $( "#allHunterEmailTemplates" ).html();
        	kendoKipHelperInstance.showBackUpPercentMeasuredOKCancelTitledPopup(part1+replaced+part2, templateName, "40", "60");
        	hunterAdminClientUserVM.updateTemplateNameTitle(templateName);
    	});
    },
    launchTaskEmailPreview : function(){
    	var taskId = this.get("selTaskId"),
    		url = HunterConstants.getHunterBaseURL("message/action/tskMsg/getReadyEmailBody/" + taskId),
    		windowCover = $("#taskEmailPreviewWindowTemplate").html();
    	kendoKipHelperInstance.showWindowWithOnClose(windowCover, "Delete Email Confirmation");
    	setTimeout(function(){
    		$( "#taskEmailPreviewContent" ).load( url, function(response, status, xhr) {
      		  if ( status == "error" ) {
      			    var msg = "Error loading email for selected task : ";
      			    $( "#taskEmailPreviewContent" ).html( "<h2 style='margin-top:35%;color:red;' >" + msg  + "   ( " + xhr.status + " :  " + xhr.statusText + " )</h2>");
      			  }
      	});
    	}, 800);   	
    },
    selectEmailTemplate : function(){
    	var current = this.get("currentTemplateIndx");
    	var name = this.get("existentTemplateNames")[current];
    	hunterAdminClientUserVM.set("emailTemplateName",name);
    	kendoKipHelperInstance.showErrorOrSuccessMsg("Success", "Successfully save template!");
    	hunterAdminClientUserVM.closeTemplatesWindow();
    },
    showPopupToDeleteEmail : function(){
    	var messageId = 1;
    	var message = "<span style='color:red' >Are you sure you want to delete the email?</span>";
    	var okButton = kendoKipHelperInstance.createSimpleHunterButton('tick','OK', 'hunterAdminClientUserVM.deleteSelectedEmailMsg("' + messageId +'")');
    	var cancelButton = kendoKipHelperInstance.createSimpleHunterButton('cancel','Cancel', 'kendoKipHelperInstance.closeHelperKendoWindow()');
    	var tableBut = "<table style='with:60%;margin-left:25%;' ><tr><td>" + okButton +"</td><td>" + cancelButton + "</td></tr></table>";
    	var content = "<br/>" + message + "<br/><br/>" + tableBut;
    	kendoKipHelperInstance.showPopupWithNoButtons("Delete Email Confirmation", content);
    },
    reloadMessageAttachmentContainer : function(){
    	$("#messageAttachmentsContainer").html("<img src='"+ baseUrl +"static/resources/images/refreshing_spinner_new.gif' style='margin-left:47%;margin-top:10%;' width='50px' height='50px'   />");
    	setTimeout(function(){
    		hunterAdminClientUserVM.loadMessageAttachmentsContainer();
    	}, 1500);
    },
    afterGettingAttachmentNames : function(data){
		$("#messageAttachmentsContainer").html("");
		data = data.substring(1,data.length-1);
		data = data.split(","); 
		if( data == "NO DATA" ){
			$("#messageAttachmentsContainer").html("<h3 style='color:red;margin-top:15px;' width='100%' ><center>Template has no attachments</center></h3>");
			return;
		}
		for(var i=0; i<data.length;i++){
			var attchment = data[i].split("||"), 
				path = attchment[1],
				key = attchment[0],
				json = {"attachmentName":key,"attachmentLocation" : path},
				template = kendo.template($("#hunterMessageAttachmentTemplate").html()),
				html = template(json);
			console.log(JSON.stringify(json));
			$("#messageAttachmentsContainer").append(html);
  	  	}
    },
    loadMessageAttachmentsContainer : function(){
    	var message = this.getSelectedTaskBeanMsg();
    	if( message != null ){
    		var url = HunterConstants.getHunterBaseURL("message/action/tskMsg/getMessageAttachmentsNamesString");
    		var data = {"msgId" : message["msgId"]};
    		kendoKipHelperInstance.ajaxPostDataForJsonResponse(JSON.stringify(data), "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterGettingAttachmentNames");
    	}else{
    		$("#messageAttachmentsContainer").html("<h3 style='color:red;margin-top:15px;' width='100%' ><center>No email message configured yet.</center></h3>");
    	}
    },
    afterLoadMessageAttachmentsContainer : function(data){
    	if( data == null || JSON.stringify(data) == "{}" ){
    		$("#messageAttachmentsContainer").html("<h3 style='color:red;margin-top:15px;' width='100%' ><center>Template has no attachments!</center></h3>");
		}else{
			//{"SonkoMbuviHorizontalPhoto":"Sonko Mbuvi Top Photo"}
			data = $.parseJSON(JSON.stringify(data));
			if( data.length == 0 || data == null || data === 'undefined' ){
				this.exeptionLoadingMsgAttchmentCntnr();
			}
			$("#messageAttachmentsContainer").html(""); 
			for(var key in data){
				var value = data[key];
					json = {"attachmentName":value,"attachmentLocation" : "<span style='color:red;'>No location configured</span>"},
					template = kendo.template($("#hunterMessageAttachmentTemplate").html()),
	    			html = template(json);
	    			console.log(JSON.stringify(json));
	    			$("#messageAttachmentsContainer").append(html);
			}	
		}
    },
    exeptionLoadingMsgAttchmentCntnr : function(){
    	$("#messageAttachmentsContainer").html("<h3 style='color:red;margin-top:15px;' width='100%' ><center>Error occurred loading attachments!</center></h3>");
    },
    loadMessageAttachmentMappings : function(){
    	var message = this.getSelectedTaskBeanMsg();
    	var emailTemplateName = message.emailTemplateName;
    	kendoKipHelperInstance.ajaxPostDataWithCall(null, "application/json", "json", "POST", HunterConstants.getHunterBaseURL('/message/action/tskMsg/attachments/' + emailTemplateName), "hunterAdminClientUserVM.afterLoadMessageAttachmentsContainer", "hunterAdminClientUserVM.exeptionLoadingMsgAttchmentCntnr");
    },
    showPopupToEditMessageAttachments : function(button){
    	
    	this.set("messageAttachmentNameToBeEdited", $(button).closest("td").text().trim() );
    	
    	if( !this.get("isAttachmentPopupOpen") ){
    		this.set("isAttachmentPopupOpen", true);
    		var popup = "<div id='hunterMessageAttachmentsPopup' style='background-color:#D9F3F7;border-radius:5px;margin-left:70%;margin-top:60%;position:absolute;width:10px;height:2px;border:1px solid #81BAC3;margin-top:1px;'><div>";
        	$("body").append(popup);
        	$("#hunterMessageAttachmentsPopup").animate({
        	    height: "+=278",
        	    width: "+=700",
        	    "margin-left":"60%",
        	    "margin-top":"-19%"
        	  }, 300, function() {
        		var content = $("#hunterAttachmentPopupTemplate").html();
        		$("#hunterMessageAttachmentsPopup").html(content);
        		kendoKipHelperInstance.ajaxPostDataWithCall(null, "application/json", "json", "POST", HunterConstants.getHunterBaseURL('/message/action/tskMsg/attachmentsRecords'), "hunterAdminClientUserVM.afterFetchingAllHunterAttachments", "hunterAdminClientUserVM.exeptionFetchingAllHunterAttachments");
        	  });
    	}
    },
    afterFetchingAllHunterAttachments : function(data){
    	if( data != null && data.length > 0 ){
    		$("#messageAttachmentsMappingsTable").removeClass("hidden");
    		$("#messageAttachmentsMappingsDiv img:first-child").addClass("hidden"); 
    		for(var i=data.length-1; i>=0;i--){
    			var attachment = data[i];
    			$("#messageAttachmentsMappingsTable").append("<tr><td onClick='hunterAdminClientUserVM.setAttachmentToMsgAttchment("+ attachment["beanId"] +")' class='messageAttachmentsRecord'>"+ attachment["beanName"] +" ( "+ attachment["originalFileName"] +"; "+ attachment["cretDate"] +" ; "+ attachment["fileFormat"] +" )<td></tr>");
    		}
    	}else{
    		$("#messageAttachmentsMappingsDiv").html("<h3 style='color:red;margin-top:15px;' width='100%' ><center>Error occurred loading attachments!</center></h3>");
    	}
    },
    exeptionFetchingAllHunterAttachments : function(){
    	$("#messageAttachmentsMappingsDiv").html("<h3 style='color:red;margin-top:15px;' width='100%' ><center>Error occurred loading attachments!</center></h3>");
    },
    setAttachmentToMsgAttchment : function(attchmentId){
    	this.closeMessageAttchmntPopup();
    	var selAttachmentName = this.get("messageAttachmentNameToBeEdited");
    	var json = {"taskId" : this.getSelectedTaskBean().get("taskId"), "attchmentId" : attchmentId, "templateAttachmentName" : selAttachmentName};
    	kendoKipHelperInstance.ajaxPostData(JSON.stringify(json), "application/json", "json", "POST", HunterConstants.getHunterBaseURL('/message/action/tskMsg/setAttachmentToMsgAttchment'), "hunterAdminClientUserVM.afterSettingAttachmentToMsgAttachment");
    },
    afterSettingAttachmentToMsgAttachment : function(data){
    	this.reloadMessageAttachmentContainer();
    },
    closeMessageAttchmntPopup : function(){
    	$("#hunterMessageAttachmentsPopup").slideToggle( 200, function() {
    	    $(this).remove();
    	    hunterAdminClientUserVM.set("isAttachmentPopupOpen", false);
    	  });
    },
    deleteSelectedEmailMsg : function(msgId){
    	kendoKipHelperInstance.closeHelperKendoWindow();
    	var url = baseUrl + "message/action/tskMsg/email/deleteEmail/" + this.get("selTaskId");
    	kendoKipHelperInstance.ajaxPostData(null, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterDeletingEmailMsg");
    },
    afterDeletingEmailMsg : function(data){
    	data = $.parseJSON(data);
    	if(data != null){
    		var status = data.status;
    		var message = data.message;
    		kendoKipHelperInstance.showErrorOrSuccessMsg(status, message);
    	}
    },
    closeTemplatesWindow : function(){
    	$("#templatesInnerTable").remove();
    	$("#templateContentContainer").remove();
    	this.set("currentTemplateIndx", -1);
    	kendoKipHelperInstance.closeMeasuredWindowBackUp();
    },
    closeEmailTemplateSection : function(){
    	this.closeMessageAttchmntPopup();
    	$("#emailEditTemplateCover").slideUp(500,function(){
    		$("#hunterUserDetailsStrip").slideDown(500,function(){
    			hunterAdminClientUserVM.set("isEmailSectionOpen", false);
    			hunterAdminClientUserVM.clearEmailConfigurations();
    			var ds = hunterAdminClientUserVM.get("selTaskRegionsDS");
    			ds.read();
    		});
		});
    }, 
    setEmailMsgvalues : function(message){
    	if(message != null){
    		this.set("taskEmailHtml", message.msgText);
    		this.set("emailMsgCretDate", message.cretDate);
    		this.set("emailMsgLstUpdate", message.lastUpdate);
    		this.set("emailMsgCretBy", message.createdBy);
    		this.set("emailMsgLstUpdatedBy", message.lastUpdatedBy);
    		this.set("taskEmailMsgSendDate", message.msgSendDate);
    		this.set("tskEmailMsgStatus", message.msgLifeStatus);
    		this.set("hasAttachment", message.hasAttachment);
    		this.set("emailSubject", message.eSubject);
    		this.set("emailTemplateName",message.emailTemplateName); 
    	}
    },
    clearEmailConfigurations : function(){
    	hunterAdminClientUserVM.set("taskEmailHtml", null);
    	hunterAdminClientUserVM.set("emailMsgCretDate", null);
    	hunterAdminClientUserVM.set("emailMsgLstUpdate", null);
    	hunterAdminClientUserVM.set("emailMsgCretBy", null);
    	hunterAdminClientUserVM.set("lastUpdatedBy", null);
    	hunterAdminClientUserVM.set("taskEmailMsgSendDate", null);
    	hunterAdminClientUserVM.set("tskEmailMsgStatus", null);
    },
    showEmailTemplateProcessed : function(){
    	hunterAdminClientUserVM.set("currentParamButtonText", "Show Parameters");
    	$("#emailTemplateParamsEditor").slideUp(500,function(){
    		$("#emailTemplateProcessed").slideDown(500,function(){
    			hunterAdminClientUserVM.set("isParamsSectionOpen", false);
    		});
		});
    },
    refreshElemeAttributes : function(){
    	var msgId = this.getSelectedTaskBean().get("taskMessage")["msgId"]; 
    	var url = baseUrl + "message/action/email/getRefreshValues/" + msgId;
    	kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterGettingRefreshValues");
    },
    afterGettingRefreshValues : function(data){
    	data = $.parseJSON(data);
    	hunterAdminClientUserVM.set("emailMsgCretDate", data.CRETDATE);
    	hunterAdminClientUserVM.set("emailMsgLstUpdate", data.LASTUPDATE);
    	hunterAdminClientUserVM.set("emailMsgCretBy", data.CREATEDBY);
    	hunterAdminClientUserVM.set("lastUpdatedBy", data.LASTUPDATEDBY);
    	hunterAdminClientUserVM.set("taskEmailMsgSendDate", data.MSGSENDDATE);
    	hunterAdminClientUserVM.set("tskEmailMsgStatus", data.MSGLIFESTATUS);
    	hunterAdminClientUserVM.set("hasAttachment", data.HASATTACHMENT == 'true');
    	hunterAdminClientUserVM.set("emailSubject", data.E_SUBJECT);
    	hunterAdminClientUserVM.set("emailTemplateName",data.EML_TMPLT_NAM);
    },
    showEmailParameters : function(){
    	var isParamsSectionOpen = this.get("isParamsSectionOpen"); 
    	if(isParamsSectionOpen){
    		this.showEmailTemplateProcessed();
    	}else{
    		console.log("param section is closed. Opening it..."); 
    		hunterAdminClientUserVM.set("currentParamButtonText", "Close Parameters");
    		$("#emailTemplateProcessed").slideUp(500,function(){
        		$("#emailTemplateParamsEditor").slideDown(500,function(){
        			hunterAdminClientUserVM.set("isParamsSectionOpen", true);
        		});
    		});
    	}
    },
    getWordCount : function(){
    	var html = this.get("taskEmailHtml");
    	if(html == null){
    		$("#styledTickOK").css({"display":"none"});
    		$("#styledTickCancel").css({"display":""});
    		return "0";
    	}
    	var len = html.length;
    	if(len >=  4000){
    		$("#styledTickCancel").css({"display":""});
    		$("#styledTickOK").css({"display":"none"});
    		$("#wordCountSpan").css({"color":"red"});
    	}else{
    		$("#styledTickOK").css({"display":""}); 
    		$("#styledTickCancel").css({"display":"none"}); 
    		$("#wordCountSpan").css({"color":"green"});
    	}
    	return len;
    },
    
    validateSubject : function(){
    	var subject = this.get("emailSubject");
    	return subject != null && subject.length > 0;
    },
    
    
    
    
    
    
    
    
    
    /* :::::::::::::::::::::::::::::::::        THIS PARRT IS FOR SOCIAL MESSAGE ONLY          ::::::::::::::::::::::::::::::::::::::::: */
    
    currentProcessJobId : null,
    backupRemoteURL : null,
    isVisible : true,
    isSocialMsgPrevOpen : true,
	selMsgSocialGroupDS : new kendo.data.DataSource({
		schema: {
		    model: SocialGroupModel
		},
		transport : {
			read:  {
				 url: function(){
			    	  var 
			    	  selMsgId = hunterAdminClientUserVM.get("selSocialMsgId") == null ? "0" : hunterAdminClientUserVM.get("selSocialMsgId")+"",
			    	  url = HunterConstants.getHunterBaseURL("social/action/msg/selGroups/read/" + selMsgId);
			    	  console.log( url );
			    	  return url;
			      },
			      data : function(){
			    	if( hunterAdminClientUserVM != null ){
			    		var array = hunterAdminClientUserVM.get("selSocialMsgBean").hunterSocialGroupsIds;
			    		 return {"groupIds" : JSON.stringify(array)};
			    	}
			    	return null;
			      },
			      dataType: "json",
			      contentType:"application/json",
			      method: "POST"
			}
		}
	}),
	isVisible : true,
	
	selSocialMsgBean	: 
	{
		externalId			 : null,
		socialMsgId 		  : null, 
		mediaType			  : null,
		socialPostType		  : null,
		hunterSocialGroupsIds : [],
		description			  : null,
		socialPost			  : null,
		originalFileFormat 	  : null,
		socialMediaId		  : null,
		defaultSocialAppId	  : null,
		remoteURL			  : null,
		useRemoteMedia		  : null
	},
	
	medialTypes		: HunterConstants.SOCIAL_MEDIA_TYPS_ARRAY,
	socialPostTypes : HunterConstants.SOCIAL_POST_ARRAY,
	
	selectedSocialGroups 	  : [],
	socialApps				  : [],
	postActionTypes			  : 
		   [
		      {	text:"Post To Group", 	 value:"Post To Group"},
	          {	text:"Post As Group", 	 value:"Post As Group"},
	          {	text:"Post To Timeline", value:"Post To Timeline"}
	       ],
	availableSocialGroupsGrid : null,
	
	showPopupForSocialMessage : function(){
		
		this.resetSocialMessage();
		
		var 
		taskId 	= this.get("selTaskId"),
		task 	= this.getSelectedTaskBean(),
		message = task.get("taskMessage"),
		content = $("#createSocialMessagePopupTemplate").html(),
		groupIds = hunterAdminClientUserVM.extrctSelSclMsgSclGrpsIds(message);

		this.get("selSocialMsgBean").socialMsgId  = taskId;
		this.set("selSocialMsgId",taskId);
		
		if( message != null ){
			var sclMsgTemp = 
			{
				externalId			  : message.externalId,
				socialMsgId 		  : message.socialMsgId, 
				mediaType			  : message.mediaType,
				socialPostType		  : message.socialPostType,
				hunterSocialGroupsIds : groupIds,
				description			  : message.description,
				socialPost			  : message.socialPost,
				originalFileFormat 	  : message.originalFileFormat,
				socialMediaId		  : message.socialMediaId,
				socialPostAction	  : message.socialPostAction,
				useRemoteMedia		  : message.useRemoteMedia,
				remoteURL			  : message.socialMedia != null ? message.socialMedia.remoteURL : null,
				defaultSocialAppId	  : message == null ? null : message.defaultSocialApp == null ? null : message.defaultSocialApp.appId
			};
			this.set("selSocialMsgBean", sclMsgTemp);
		}
		
		kendoKipHelperInstance.showWindowWithOnClose(content, "Social Message");
		kendo.bind($("#createNewSocialMessageContainer"), hunterAdminClientUserVM);
		this.loadSocialMessageMedia();
		
	},
	saveRemoteURLAndPreview : function(){
		var 
		selBean = this.get("selSocialMsgBean"),
		isRemote = selBean.useRemoteMedia;
		url = this.get("selSocialMsgBean").remoteURL;
		if( url == null || url.trim() === '' ){
			kendoKipHelperInstance.showErrorNotification('Remote url is required!!');
			$("#createMediaFromURL").css({'background-color':'#FFC0CD'}); 
			return;
		}else{
			$("#createMediaFromURL").css({'background-color':'#FFFFFF'});
		}
		
		if( isRemote && url !== this.get("backupRemoteURL") ){
			alert( "It's different, will set useRemoteURL " );
			selBean.useRemoteMedia 	= true;
		}
		
		this.prepareStageForTemplate("crtsSclMsgPreviewImage");
		$("#createSclMsgImgPreviewImg").attr('src', url);
	},
	loadSocialMessageMedia : function(){
		var url = HunterConstants.getHunterBaseURL("social/action/msg/getSclMsgMediaURL/" + this.get("selSocialMsgId") );
		kendoKipHelperInstance.ajaxPostData(null, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterLoadingSocialMessageMedia");
	},
	afterLoadingSocialMessageMedia : function(data){
		
		data = $.parseJSON(data);
		
		var 
		src     = data["src"],
		status  = data["status"],
		message = data["message"];
		
		if( status == null || ( status === 'Failed' && message === 'No Image Found' ) ){
			this.sclMsgLoadMediaDecide();
			this.set("isSocialMsgPrevOpen", false);
		}else{
			this.get("selSocialMsgBean").remoteURL = src;
			this.sclMsgLoadImagePreview();
			this.set("isSocialMsgPrevOpen", true);
		}
		
	},
	cleanSclMsgStagingArea : function(){
		var stage = $("#sclMsgSocialMediaStagingArea");
		kendo.destroy(stage);
		stage.html('');
	},
	sclMsgLoadMediaDecide : function(){
		this.prepareStageForTemplate("crtsSclMsgDecideTemplate");
	},
	sclMsgLoadImagePreview : function(){
		var url = this.get("selSocialMsgBean").remoteURL;
		if( url == null || url.trim() === '' ){
			kendoKipHelperInstance.showErrorNotification('No image created. Please create!');
			this.sclMsgLoadMediaDecide();
		}else{
			this.prepareStageForTemplate("crtsSclMsgPreviewImage");
			$("#createSclMsgImgPreviewImg").attr("src", url);
		}
	},
	prepareStageForTemplate : function(tempName){
		this.cleanSclMsgStagingArea();
		var html = $("#"+tempName).html();
		$("#sclMsgSocialMediaStagingArea").html( html );
		kendo.bind( $("#sclMsgSocialMediaStagingArea"), hunterAdminClientUserVM );
	},
	extrctSelSclMsgSclGrpsIds : function(messageBean){
		
		if( messageBean == null ) return [];
		
		var 
		sclGrpJson 		= messageBean.hunterSocialGroups,
		sclGrpIdsArray 	= [];
		if( sclGrpJson != null && sclGrpJson.length > 0 ){
			var len = sclGrpJson.length;
			for(var i=0; i<len ; i++){
				var 
				socialGroup = sclGrpJson[i],
				groupId = socialGroup["groupId"];
				sclGrpIdsArray.push(groupId);
			}
		}
		hunterAdminClientUserVM.set("selectedSocialGroups", sclGrpIdsArray);
		return sclGrpIdsArray;
	},
	submitSocialMsgChanges : function(){
		kendo.ui.progress($("#createNewSocialMessageContainer"), true);
		setTimeout(function(){
			var bean = hunterAdminClientUserVM.get("selSocialMsgBean");
			bean.socialMsgId = hunterAdminClientUserVM.get("selTaskId");
			var
			data = JSON.stringify(bean);
			url  = HunterConstants.getHunterBaseURL("message/action/tskMsg/social/createOrUpdateSocialMsg");
			kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterSubmittingSocialMsgChanges");
		}, 1000);
	},
	afterSubmittingSocialMsgChanges : function(data){
		this.closeCreateSocialMsgWindow();
		data = jQuery.parseJSON(data);
		var status = data["status"];
		var message = data["message"];
		if(status == "Failed"){
			message = replaceAll(message, ",", "</br>");  
			kendoKipHelperInstance.showErrorNotification(message);
		}else if(status === "Success"){ 
			kendoKipHelperInstance.showSuccessNotification(message);
			var ds = hunterAdminClientUserVM.get("hunterClientTaskGrid").dataSource;
			ds.read();
		}
	},
	addSelSclGrpsToSclMsg : function(){
		this.get("selSocialMsgBean").hunterSocialGroupsIds = this.get("selectedSocialGroups"); 
		this.closeAvailableSocialGroups();
	},
	submitSelSclGrps : function(){
		alert(JSON.stringify(addedGroups));
	},
	resetSocialMessage : function(){
		var sclMsgReset = 
		{
			externalId			 : null,
			socialMsgId 		  : null, 
			mediaType			  : null,
			socialPostType		  : null,
			hunterSocialGroupsIds : [],
			description			  : null,
			socialPost			  : null,
			originalFileFormat 	  : null,
			socialMediaId		  : null,
			defaultSocialAppId	  : null,
			socialPostAction	  : null,
			remoteURL			  : null,
			useRemoteMedia		  : false
		};
		this.set("selSocialMsgBean", sclMsgReset);
	},
	addToSelectedSocialGroups : function(groupId){
		var 
		array 	  = hunterAdminClientUserVM.get("selectedSocialGroups"),
		tempArray = [];
		found	  = false;
		
		if( array.length == 0 ){
			tempArray.push(groupId);
		}else{
			for(var i=0; i<array.length; i++){
				var id = array[i];
				if( id+"" !== groupId+"" ){
					tempArray.push(id);
				}else{
					found = true;
				}
			}
			if( !found ){
				tempArray.push(groupId);
			}
		}
		hunterAdminClientUserVM.set("selectedSocialGroups",  tempArray );
	},
	sclMsgInit : function(){
		this.sclMsgAfterInit();
	},
	sclMsgAfterInit : function(){
		this.loadSocialApps();
	},
	loadSocialApps : function(){
		var url = HunterConstants.getHunterBaseURL("social/action/apps/dropdowns");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterGettingSocialApps");
	},
	afterGettingSocialApps : function(data){
		data = $.parseJSON(data);
		hunterAdminClientUserVM.set("socialApps", data);
	},
	onMouseOverAddSocialGroup : function(){
		$("#createSocialMsgAddSocialGroups").css({"background-color":"#CEECF2"});
	},
	onMouseOutAddSocialGroup : function(){
		$("#createSocialMsgAddSocialGroups").css({"background-color":"#D9F4F9"});
	},
	closeAvailableSocialGroups : function(){
		$("#selSocialGroupGrid").removeClass("hidden");
		$("#sclMsgAvailableSclGrps").addClass("hidden");
		this.get("selMsgSocialGroupDS").read();
	},
	onClickRemoveSocialGrp : function(id){
		var delete_ = confirm("Are you sure you want to delete?");
		var selGroups = hunterAdminClientUserVM.get("selSocialMsgBean").hunterSocialGroupsIds;
		var index = selGroups.indexOf(id);
		selGroups.splice( index, 1 );
		if( delete_ ){
			var 
			data = JSON.stringify({"groupId":id, "messageId" : hunterAdminClientUserVM.get("selSocialMsgId")});
			url	 = HunterConstants.getHunterBaseURL("social/action/msg/removeSocialGrp");
			kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterRemovingSocialGroupFromMsg");
		}else{
			console.log("Exiting...");
		}
	},
	afterRemovingSocialGroupFromMsg : function(data){
		data = jQuery.parseJSON(data);
		var status = data["status"];
		var message = data["message"];
		if(status == "Failed"){
			message = replaceAll(message, ",", "</br>");  
			alert(message);
		}else if(status === "Success"){
			console.log(message);
			$("#selSocialGroupGrid").data("kendoGrid").dataSource.read();
			$("#sclMsgAvailableSclGrps").data("kendoGrid").dataSource.read();
		}
	},
	showAvailableSocialGroups : function(){
		
		$("#selSocialGroupGrid").addClass("hidden");
		$("#sclMsgAvailableSclGrps").removeClass("hidden");
		
		var grid_ = this.get("availableSocialGroupsGrid");
		
		if( grid_ != null ){
			kendo.destroy($("#sclMsgavailableGrpsGrid"));
			$("#sclMsgavailableGrpsGrid").html("");
		}
		
		
		
		var grid = $("#sclMsgavailableGrpsGrid").kendoGrid({
			dataSource : new kendo.data.DataSource({
				schema: {
				    model: SocialGroupModel
				},
				transport : {
					read:  {
					      url: function(){
					    	  selMsgId = hunterAdminClientUserVM.get("selSocialMsgId") == null ? "0" : hunterAdminClientUserVM.get("selSocialMsgId")+"",
					    	  url = HunterConstants.getHunterBaseURL("social/action/msg/availGroups/read/" + selMsgId);
					    	  console.log( url );
					    	  return url;
					      },
					      data : function(){
					    	if( hunterAdminClientUserVM != null ){
					    		var array = hunterAdminClientUserVM.get("selSocialMsgBean").hunterSocialGroupsIds;
					    		 return {"groupIds" : JSON.stringify(array)};
					    	}
					    	return null;
					      },
					      dataType: "json",
					      contentType:"application/json",
					      method: "POST"
					}
				}
			}),
			toolbar : kendo.template($("#availSclGrpsToolBar").html()),
			height: 380,
			pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            columns: 
                [
					 { 
						 'field':'','title':'',
	            		  headerTemplate: '<label>Select</label>', 
	            		  template: '<center><input onClick="hunterAdminClientUserVM.addToSelectedSocialGroups(#=groupId#)" type="checkbox"  /></center>', 'width':'70px' 
					 },
	                {
	                    field: "groupName",
	                    title: "Group Name"
	                },
	                {
	                    field: "status",
	                    title: "Status"
	                }, 
	                {
	                    field: "socialType",
	                    title: "Social Type"
	                }
                ]
		}).data("kendoGrid"); 
		this.set("availableSocialGroupsGrid", grid);
	},
	onSelectSocialMsgUpload : function(){
		console.log("Social msg upload selected");
	},
	onCompleteSocialMsgUpload : function(){
		console.log("Completed upload!"); 
		hunterAdminClientUserVM.get("selSocialMsgBean").useRemoteMedia = false;
	},
	onErrorSocialMsgUpload : function(e){
		console.log("Error occurred!");
		hunterAdminClientUserVM.get("selSocialMsgBean").useRemoteMedia = false;
	},
	onSuccessSocialMsgUpload : function(e){
		console.log("Success upload!");
		this.get("selSocialMsgBean").useRemoteMedia = false;
		this.loadSocialMessageMedia();
	},
	deleteSelectedImg : function(){
		var 
		selMsg = this.getSelectedTaskId(),
		url	   = HunterConstants.getHunterBaseURL("social/action/msg/deleteImg"),
		data   = JSON.stringify({"selMsg":selMsg});
		kendo.ui.progress($("#createNewSocialMessageContainer"), true);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterDeletingSelImg");
	},
	afterDeletingSelImg : function(data){
		kendo.ui.progress($("#createNewSocialMessageContainer"), false);
		data = jQuery.parseJSON(data);
		var status = data["status"];
		var message = data["message"];
		if(status == "Failed"){
			message = replaceAll(message, ",", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error deleting selected image!","<span style='color:red;' >" + message + "</span>");
		}else if(status === "Success"){ 
			kendoKipHelperInstance.showSuccessNotification("Successfully deleted selected media!");
			hunterAdminClientUserVM.loadSocialMessageMedia();
			var tskGridDs = this.get("hunterClientTaskGrid").dataSource;
			tskGridDs.read();
		}
	},
	closeCreateSclMediaOptions : function(){
		$('#createImageFromURLDiv').addClass('hidden');
		$('#socialPostUploadImageDiv').addClass('hidden');
		$("#createSclMsgImgPreview").removeClass('hidden');
		$("#dcdHwTCrtSclMsgMda").addClass('hidden');
	},
	openCreateSclmediaOption : function(){
		this.sclMsgLoadMediaDecide();
	},
	openOrCloseSocialMsgPrev : function(){
		var isOpen = this.get("isSocialMsgPrevOpen");
		if( isOpen ){
			this.set("isSocialMsgPrevOpen", false);
			this.sclMsgLoadMediaDecide();
			$("#imageSceneLable").text("Preview Image");
		}else{
			this.sclMsgLoadImagePreview();
			this.set("isSocialMsgPrevOpen", true);
			$("#imageSceneLable").text("Upload Image");
			
		}
	},
	showHowToCreateMedia : function(this_){
		var valName = $(this_).attr('data-value');
		if( valName === 'uploadImage' ){
			this.prepareStageForTemplate("crtsSclMsgUploadTemplate");
		}else{
			var backupURL = this.get("selSocialMsgBean").remoteURL;
			this.set("backupRemoteURL",backupURL);
			this.prepareStageForTemplate("crtsSclMsgFromURLTemplate");
		}
	},
	readURL : function (input) {
		var 
		target=$("#createSclMsgImgPreviewImg");
		
		if (input.files && input.files[0]) {
	        var reader = new FileReader();
	        reader.onload = function (e) {
	            $( target ).attr('src', e.target.result);
	        };
	        reader.readAsDataURL(input.files[0]);
	    }
	},
	closeCreateSocialMsgWindow : function(){
		this.resetSocialMessage();
		kendoKipHelperInstance.closeWindowWithOnClose();
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
});




function getSocialMsToolBarTemplate(){
	return '<button onClick="hunterAdminClientUserVM.showAvailableSocialGroups()" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-plus" ></span>Add Social Group</button>';
}



 
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";
kendoKipHelperInstance = new kendoKipHelper();
kendoKipHelperInstance.init();

var hunterUserAddressesModel = kendo.data.Model.define({
	id:"id",
	fields : {
		"id" : {
			type : "number", validation : {required : true},editable:false, defaultValue:null
		},
		"country" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"state" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"aptNo" : {
			type : "string", validation : {required : false},editable:true, defaultValue:null
		},
		"city" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"zip" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"type" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"userId" : {
			type : "string", validation : {required : false},editable:false, defaultValue:null
		},
	}
});

function getUserDataToolBarTemplate(){
	var html = $("#userDetailsToolBarTemplate").html();
	return html;
}

function getHunterAdminUserGridPopupTemplate(){
	var html = $("#hunterAdminUserGridPopupTemplate").html();
	return html;
}

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
		"userName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"password" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"cretDate" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
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
	getUserDataToolBarTemplate : function(){
		var html = $("#userDetailsToolBarTemplate").html();
		return html;
	},
	getToolBarTemplate : function(){
		return "<button class='k-button' >Click</button>";
	},
	getEditTemplate : function(){
		return kendoKipHelperInstance.createContextEditButton(false);
	},
	getDeleteTemplate : function(){
		var id = this.get("userId");
		var fName = this.get("firstName");
		var lName = this.get("lastName");
		var whole = id + "," + fName + "," + lName;
		return kendoKipHelperInstance.createDeleteButton(false, 'hunterAdminClientUserVM.showPopupToDeleteUser("'+whole+'")');
	}
});

function getToolBarTemplate(){
	return '<button class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210); >Click</button>';
}

function getUserRoleDeleteTemplate(){
	return '<center><a onclick="hunterAdminClientUserVM.showPopupToDeleteUserRole(this)" class="k-button" style="min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210)"><span class="k-icon k-i-close"></span></a></center>';
}

function getToolBarTemplate(){
	return '<button class="k-button" onClick="hunterAdminClientUserVM.showPopupToAddRole()"   style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-add"></span>Add Role</button>';
}

var hunterAdminClientUserVM = kendo.observable({
	
	isEverVisible : true,
	isNeverVisible : false,
	isEverEnabled : true,
	isNeverDisabled : false,
	selUserId : null,
	hunterUserTabstrip : null,
	selUser : {},
	selUserRoles : [],
	userRoles : [],
	dropdownListCounter : 0,
	currDropdownListName : null,
	
	/* ................................... */
	
	beforeInit : function(){
		console.log("Preparing to initialize hunter admin VM..."); 
	},
	init : function(){
		console.log("Initializing hunter admin VM...");
		this.beforeInit();
		kendo.bind($("#hunterUserVVM"),hunterAdminClientUserVM);
		this.afterInit();
	},
	afterInit : function(){
		//this.createUserTabstrips();
		this.populateUserRoles();
		console.log("Finished initialization of hunter admin VM!!"); 
	},
	
	/* ................................... */
	showPopupToDeleteUserRole : function(this_){
		var dataItem = $(this_).closest("tr");
		var id = $(dataItem).find("td:first").text();
		var name = $(dataItem).find("td:nth-child(2)").text();
		var content = "<span style='color:red;' ><b>" + name + "</b> will be deleted.<br/> Are you sure?</span><br/>";
		kendoKipHelperInstance.showOKToDeleteItem(id,content,"hunterAdminClientUserVM.deleteUserRoleFromUser");
	},
	deleteUserRoleFromUser : function(id){
		kendoKipHelperInstance.closeHelperKendoWindow();
		var userId = this.get("selUserId");
		var data = {"userId" : userId, "userRoleId":id};
		data = JSON.stringify(data);
		var url = baseUrl + "/admin/action/user/roles/remove";
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterDeletingUserRoleFromUser");
	},
	afterDeletingUserRoleFromUser : function(data){
		data = $.parseJSON(data);
		var status = data[HunterConstants.STATUS_STRING];
		var message = data[HunterConstants.MESSAGE_STRING];
		if(status != HunterConstants.STATUS_SUCCESS){
			message = replaceAll(message, "|", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error deleting user role!","<span style='color:red;' >" + message + "</span>");
		}else{
			kendoKipHelperInstance.showSuccessNotification(message);
		}
		this.fetchSelUserRoles();
	},
	showPopupToAddRole : function(){
		var userId = this.get("selUserId");
		if(userId == null || userId === 'null'){
			kendoKipHelperInstance.showErrorNotification("No user is selected!");
			return;
		}
		var name = "dropdownListCounter_" + (this.get("dropdownListCounter")+1);
		this.set("currDropdownListName", name);
		var template = kendo.template($("#userRoleDropdownListTemplate").html());
		var data = { "kendoDropdownListName": name };
		var content = template(data);
		kendoKipHelperInstance.showPopupWithNoButtons("Add Role", content);
		$("#" + name).kendoComboBox({
			 dataTextField: "roleShortName",
             dataValueField: "roleId",
             dataSource: hunterAdminClientUserVM.get("userRoles"),
             index: 0
		});
		
	},
	destroyUserRoleDropdown : function(){
		var name = this.get("currDropdownListName"); 
		var ele = $("#" + name);
		kendo.destroy(ele);
		$(ele).html(""); 
		$(ele).empty(); 
		$(ele).remove();
	},
	addRoleToSelUserAndClose : function(){
		var name = this.get("currDropdownListName"); 
		var ele = $("#" + name);
		var value = ele.val();
		this.destroyUserRoleDropdown();
		kendoKipHelperInstance.closeHelperKendoWindow();
		var data = {"userId" : this.get("selUserId"), "userRoleId" : value};
		data = JSON.stringify(data);
		console.log(data);
		var url = baseUrl + "/admin/action/user/roles/add";
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterAddingRoleToUser");
	},
	afterAddingRoleToUser : function(data){
		console.log(data);
		data = $.parseJSON(data);
		var status = data[HunterConstants.STATUS_STRING];
		var message = data[HunterConstants.MESSAGE_STRING];
		if(status != HunterConstants.STATUS_SUCCESS){
			message = replaceAll(message, "|", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error Adding Role","<span style='color:red;' >" + message + "</span>");
		}else{
			kendoKipHelperInstance.showSuccessNotification(message);
		}
		this.fetchSelUserRoles();
	},
	destroyAndCloseWindow : function(){
		this.destroyUserRoleDropdown();
		kendoKipHelperInstance.closeHelperKendoWindow();
	},
	fetchSelUserRoles : function(){
		var selUserId = this.get("selUserId"); 
		selUserId = selUserId == null ? "0" : selUserId;
		var url = baseUrl + "/admin/action/user/roles/get/" + selUserId;
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterFetchingSelectedUserRoles");
	},
	afterFetchingSelectedUserRoles : function(data){
		if(data != null && data !== 'null'){
			data = $.parseJSON(data);
		}else{
			data = []; 
		}
		hunterAdminClientUserVM.set("selUserRoles",data); 
		console.log("ppppppppppppppppppp" + JSON.stringify(data));
	},
	getToolBarTemplate : function(){
		return "<button class='k-button' >Click</button>";
	},
	getHunterAdminUserGridPopupTemplate : function(){
		var html = $("#hunterAdminUserGridPopupTemplate").html();
		return html;
	},
	populateUserRoles : function(){
		var url = baseUrl + "/admin/action/user/roles/getAll";
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterFetchingAllUserRoles");
	},
	afterFetchingAllUserRoles : function(data){
		data = $.parseJSON(data);
		hunterAdminClientUserVM.set("userRoles",data);
	},
	createUserTabstrips : function(){
		 var hunterUserTabstrip_ = $("#hunterUserTabStrip").kendoTabStrip({
             animation: { 
            	 open: { effects: "fadeIn"} 
             },
             contentUrls: [
                           '../action/templates/userDetailsTab',
                           '../action/templates/userAddressesTab',
                           '../action/templates/userRolesTab'
                       ]
         }).data("kendoTabStrip");
		 this.set("hunterUserTabstrip", hunterUserTabstrip_);
		 hunterUserTabstrip_.select(0);
	},
	correctChecks : function(userId){
		var data = this.get("hunterUserDS").view();
		var len = data.length;
		for(var i=0; i<len; i++){
			var model = data[i];
			var userId_ = model["userId"];
			if(userId != userId_){
				var id_ = "hunterAdminUser_" + userId_;
				$("#"+id_).prop("checked", false);
			}else{
				console.log(model);
				hunterAdminClientUserVM.set("selUser",model);
			}
		}
		this.set("selUserId", userId);
		this.fetchSelUserRoles();
		this.get("hunterUserAddressesDS").read();
	},
	showPopupToDeleteUser : function(whole){
		var parts = whole.split(","); 
		var message = "<span style='font-weight:bolder;' > " + parts[1] + " " + parts[2] + "</span> will be deleted. <br/>Are you sure?<br/>";
		kendoKipHelperInstance.showOKToDeleteItem( parts[0],message,'hunterAdminClientUserVM.ajaxDeleteSelectedUser');
	},
	ajaxDeleteSelectedUser : function(id){
		console.log(id);
		kendoKipHelperInstance.closeHelperKendoWindow();
		var url = baseUrl + "/admin/action/user/destroy";
		var data = {"userId" : id};
		data = JSON.stringify(data);
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "hunterAdminClientUserVM.afterDeletingSelectedUser");
	},
	afterDeletingSelectedUser : function(data){
		data = $.parseJSON(data);
		var status = data[HunterConstants.STATUS_STRING];
		var message = data[HunterConstants.MESSAGE_STRING];
		if(status != HunterConstants.STATUS_SUCCESS){
			message = replaceAll(message, "|", "</br>");  
			kendoKipHelperInstance.showSimplePopup("Error deleting user!","<span style='color:red;' >" + message + "</span>");
		}else{
			kendoKipHelperInstance.showSuccessNotification(message);
		}
		this.get("hunterUserDS").read();
	},
	hunterUserDS : new kendo.data.DataSource({
		  transport: {
		    read:  {
		      url: baseUrl + "admin/action/user/read",
		      dataType: "json",
		      contentType:"application/json",
		      method: "POST"
		    },
		    create: {
		        url: baseUrl + "admin/action/user/create",
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST"
		    },
		    update: {
		        url: baseUrl + "admin/action/user/update",
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST"
		    },
		    destroy: {
		        url: baseUrl + "admin/action/user/destroy",
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
		    	model:hunterUserModel
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
	}),
	hunterUserAddressesDS : new kendo.data.DataSource({
		  transport: {
		    read:  {
		      url: function(){
		    	  var userId = hunterAdminClientUserVM.get("selUserId"); 
		    	  if(userId == null) userId = "0";
		    	  var url = HunterConstants.getHunterBaseURL("admin/action/user/addresses/read/") + userId;
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
			    	  var url = HunterConstants.getHunterBaseURL("admin/action/user/addresses/create/") + userId;
			    	  return url;
			      },
		        dataType: "json", 
		        contentType:"application/json",
		        method:"POST"
		    },
		    update: {
		    	 url: function(){
			    	  var userId = hunterAdminClientUserVM.get("selUserId"); 
			    	  if(userId == null) userId = "0";
			    	  var url = HunterConstants.getHunterBaseURL("admin/action/user/addresses/update/") + userId;
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
			    	  var url = HunterConstants.getHunterBaseURL("admin/action/user/addresses/destroy/") + userId;
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
		        
		  },
		  pageSize:1000
	})
	
	
});



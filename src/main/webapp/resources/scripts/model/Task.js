var hunterTaskModel = kendo.data.Model.define({
	id:"taskId",
	fields : {
		"taskId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"taskType" : {
			type : "object", validation : {required : true},editable:true, defaultValue:null
		},
		"taskName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"taskObjective" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"description" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"taskName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"taskBudget" : {
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
		"taskRegion" : {
			type : "object", validation : {required : false},editable:true, defaultValue:null
		},
		"taskReceivers" : {
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
		return kendoKipHelperInstance.createContextEditButton(false);
	},
	getTaskDeleteTemplate : function(){
		var id = this.get("taskId");
		var taskName = this.get("taskName");
		var idString = '"'+ id +'",';
		var message = '"<b>' + taskName  + '</b> will be deleted.<br/> Are you sure?"';
		var html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKCancelToDeleteHunterTask('+  idString + message+ ')');
		return html;
	},
	getTaskProcessTemplate : function(){
		var id = this.get("taskId");
		var idString = "\""+ id +"\"";
		var html = kendoKipHelperInstance.createSimpleHunterButton("arrow-e",null, "hunterAdminClientUserVM.showPopupForProcessTask("+ idString +")" );
		return html;
	},
	getTaskEditStatusTemplate : function(){
		var currentStatus = this.get("taskLifeStatus");
		var keyStr = this.get("taskId") + ":::" + currentStatus;
		var html = "<center><span style='color:blue;'><a style='cursor:pointer' onClick='hunterAdminClientUserVM.showPopupToChangeTaskStatus(\""+ keyStr +"\")' >"+ currentStatus +"</a></span></center>";
		return html;
	}, 
	getTaskRegionTemplate : function(){
		var region = this.get("taskRegion");
		var id = this.get("taskId"); 
		if(region == null)
			return "<center><a style='color:blue; cursor:pointer' onClick='hunterAdminClientUserVM.loadTaskRegionView(\""+ id +"\")'  >Create</a></center>";
		var level = region["currentLevel"];
		return "<center><a style='color:blue; cursor:pointer' onClick='hunterAdminClientUserVM.loadTaskRegionView(\""+ id +"\")'  >"+ level +"</a></center>";
	},
	getTaskMessageTemplate : function(){
		var id = this.get("taskId"); 
		return "<center><a style='color:blue; cursor:pointer' onClick='hunterAdminClientUserVM.loadTaskMessageView(\""+ id +"\")'  >Message</a></center>";
	},
	getProgressTemplate : function(){
		var dStatus = this.get("taskDeliveryStatus"); 
		return "<center><a style='color:blue; cursor:pointer' onClick='hunterAdminClientUserVM.loadTaskMessageView(\""+ dStatus +"\")'  >"+ dStatus +"</a></center>";
	}
});
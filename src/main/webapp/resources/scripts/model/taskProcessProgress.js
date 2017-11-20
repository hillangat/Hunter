var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

$("document").ready(function(){
	
	var TaskProcessManager = kendo.Class.extend({
		selTaskId : null,
		validateProcessWidget : null,
		processTaskProgBar : null,
		endPointActionName : "process",
		urlMapping : {
			"validate" : "/task/action/task/process/validate",
			"process" : "/task/action/task/process/process"
		},
		defaultRequestParam : {
			url: null,
		    data : null,
		    method: "POST",
		    dataType : "json", 
		    contentType : "application/json"
		},
		createProcessBar : function(eleId){
			var progBar = $("#"+ eleId).kendoProgressBar({
                type: "percent",
                animation: {
                    duration: 300
                }
            }).data("kendoProgressBar");
			return progBar;
		},
		replaceCommasInMsg : function(message){
			var split = replaceAll(message, ",", "<br/>");
			return split;
		},
		styleForError : function(ele, content){
			ele = $("#"+ele);
			ele.html("");
			content = "<span style='color:red;' >" + content + "</span>";
			this.setIntervalForHtmlUpdate(ele, content, 500);
		},
		styleForSuccess : function(ele, content){
			ele = $("#"+ele);
			ele.html("");
			content = "<span style='color:green;' >" + content + "</span>";
			this.setIntervalForHtmlUpdate(ele, content, 500);
		},
		setIntervalForHtmlUpdate : function(ele, content, duration){
			if(duration == null || duration == 0){
				duration = 500;
			}
			ele = $(ele);
			ele.css({"display":"none"});
			var interval = null;
			interval = setInterval(function() {
				ele.html(content);
				ele.slideDown(duration);
				clearInterval(interval);
				interval = null;
			}, duration);
		},
		afterAjaxFunction : function(type,data){
			data = $.parseJSON(data);
			if(type == 'validate'){
				if(data.status != null && data.status == 'Success'){
					var task = hunterAdminClientUserVM.getSelectedTaskBean();
					task.set("taskDeliveryStatus", "Pending"); 
					var msg = 'Task passed validations <img src="' + baseUrl + 'static/resources/images/tick.png" width="15px" height="15px"   />';
					this.styleForSuccess("taskValidationResults", msg);
					this.validateProcessWidget.value(100);
					$("#taskProcessValidationTd").html('Validating &nbsp;&nbsp;<img src="' + baseUrl + 'static/resources/images/tick.png" width="15px" height="15px"  />');
					this.processTask();
				}else{
					$("#taskProcessValidationTd").html('Validating &nbsp;&nbsp;<img src="' + baseUrl + 'static/resources/images/x-button.png" width="15px" height="15px"  />');
					var msg = this.replaceCommasInMsg(data.message);
					this.styleForError("taskValidationResults", msg);
					this.validateProcessWidget.value(100);
					this.processTaskProgBar.value(0);
					this.endPointActionName = type;
				}
			}else if(type == "process"){
				this.processTaskProgBar.value(100);
				if(data.status != null && data.status == 'Success'){
					var msg = 'Task successfully submitted for processing <img src="' + baseUrl + 'static/resources/images/tick.png" width="15px" height="15px"   />';
					this.styleForSuccess("processTaskProgressMessages", msg);
					$("#taskProcessProcessTd").html('Processing <img src="' + baseUrl + 'static/resources/images/tick.png" width="15px" height="15px"  />');
				}else{
					$("#taskProcessProcessTd").html('Processing <img src="' + baseUrl + 'static/resources/images/x-button.png" width="15px" height="15px"  />');
					var msg = this.replaceCommasInMsg(data.message);
					this.styleForError("taskValidationResults", msg);
				}
			}
			// if it is the last process, remove spinner and show close button.
			if(this.endPointActionName === type){
				$("#closeTaskProcessingWindowTable").fadeIn(500);
				this.removeKendoSpinner();
			}
		},
		ajaxProcessTask : function(params){
			var r = $.Deferred();
			var type = params["type"];
			var ajaxParams = $.parseJSON(JSON.stringify(this.defaultRequestParam));
			ajaxParams["data"] = JSON.stringify({"selTaskId":hunterAdminClientUserVM.get("selTaskId")});
			ajaxParams["url"] = baseUrl +this.urlMapping[type];
			var this_ = this;
			$.ajax(ajaxParams).done(function(data) {
				this_.afterAjaxFunction(type, data);
			 }).fail(function(data) {
				 kendoKipHelperInstance.popupWarning(data.statusText + " (" + data.status + "). Please contact Production Support.", "Error");
			 });
			return r;
		},
		validateTaskProcess : function(){
			var r = $.Deferred();
			console.log("Validating task processing...");  
			 var valProgBar = this.createProcessBar("validationProgressBar") ;
			 valProgBar.value(0);
             this.validateProcessWidget = valProgBar;
             valProgBar.value(10);
             var params = {"type" : "validate", "selTaskId" : this.selTaskId, "progBar" : valProgBar};
             this.ajaxProcessTask(params);
			return r;
		},
		processTask : function(){
			var r = $.Deferred();
			var processTaskProgBar = this.processTaskProgBar;
            var params = {"type" : "process", "selTaskId" : this.selTaskId, "progBar" :  processTaskProgBar};
            this.ajaxProcessTask(params);
			return r;
		},
		createWidgets : function(){
			var processTaskProgBar = this.createProcessBar("processTaskProgressBar") ;
			processTaskProgBar.value(0);
            this.processTaskProgBar = processTaskProgBar;
            processTaskProgBar.value(30);
		},
		killTaskProcessManager : function(){
			var r = $.Deferred();
			console.log("Finished processing, killing task process manager..."); 
			return r;
		},
		removeKendoSpinner : function(){
			var r = $.Deferred();
			 setTimeout(function(){ 
				 kendo.ui.progress($("#taskProcessProgressSpinner"), false);
			 }, 400);
			return r;
		},
		execute : function(){
			this.createWidgets();
			this.validateTaskProcess();
		}
	});
	hunterAdminClientUserVM.set("taskProcessManager", new TaskProcessManager());
});



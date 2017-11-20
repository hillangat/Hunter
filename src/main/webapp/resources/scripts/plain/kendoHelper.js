
console.log("loading kendKipHelper file");

var kendoKipHelper = kendo.Class.extend({
	
	kendoWindow : null,
	measuredWindowBackUp : null,
	measuredWindow : null, 
	kendoInitTitle : "Kendo Window",
	notification : null,
	newNotification : null,
	kendoWindowWithOnClose : null,
	
	beforeInit : function(){
		console.log("Setting up kendoHelper...");
		this.loadTemplate();
		this.initKendoWindow();
		this.initMeasuredWindow();
		this.initNotification();
	},
	loadTemplate : function(){
		$.get('/Hunter/static/resources/uiTemplates/kendoHelperTemplate.html', function(templates) {
			/* Some times it gets added multiple times for some reason. Remove existent before adding again. */
			$('#kendoHelperTemplateCover').remove();
			$('body').append(templates);
		});
	},
	init : function(){
		this.beforeInit();
		this.afterInit();
	},
	createCheckBox : function(id, clazz, onChange, booleanChecked, booleanDisabled){
		var checked = booleanChecked ? "checked='checked'" : "";
		var disabled = booleanDisabled ? "disabled='disabled'" : "";
		var input =  "<input type='checkbox' id='"+ id +"' href='#' class='"+ clazz +"' onChange='"+ onChange +"' "+ checked +" "+ disabled +" />";
		return input;
	},
	afterInit : function(){
		this.initWindowWithOnClose();
		this.initNewNotification();
		console.log("KendoHelper helper initialized successfully!!");
	},
	setValueToWithOnCloseTitle : function(value){
		$("#kendoWindowWithOnCloseFunc_wnd_title").html(value);
	},
	initNewNotification : function(){
		var notificationElement = $("#newNotification"); 
		var instance = this;
		if( notificationElement.data("kendoNotification") == null){
			instance.newNotification = notificationElement.kendoNotification({
	            position: {
	                pinned: true,
	                top: 30,
	                right: 30
	            },
	            autoHideAfter: 5000,
	            stacking: "down",
	            templates: [{
	                type: "info",
	                template: $("#emailTemplate").html()
	            }, {
	                type: "error",
	                template: $("#errorTemplate").html()
	            }, {
	                type: "upload-success",
	                template: $("#successTemplate").html()
	            }]

	        }).data("kendoNotification");
		}
		var newNotitication_ = this.newNotification;
		$("document").one("kendo:pageUnload", function(){ 
			if ( newNotitication_ ) {
				newNotitication_.hide(); 
			} 
		});
	},
	showEmailNotification : function(title_, message_){
		var this_not = this.newNotification;
		if(this_not == null) 
			this.initNewNotification();
		this.newNotification.show({
             message: message_
         }, "info");
	},
	showErrorNotification : function(message_){
		var this_not = this.newNotification;
		if(this_not == null) 
			this.initNewNotification();
		this.newNotification.show({
            message: message_
        }, "error");
	},
	showSuccessNotification : function(message_){
		var this_not = this.newNotification;
		if(this_not == null) 
			this.initNewNotification();
		this.newNotification.show({
             message: message_
         }, "upload-success");
	},
	showErrorOrSuccessMsg : function(status, message){
		if(status != null  && message != null){
			message = status + " : " + message;
			if(status === 'Success'){ 
				this.showSuccessNotification(message);
			}else{
				this.showErrorNotification(message);
			}
		}else{
			this.showErrorNotification("Application error ocurred!");
		}
	},
	initKendoWindow : function(){
		this.kendoWindow = $("#kendoWindow").kendoWindow({
 	        title: kendoKipHelper.kendoInitTitle,
 	        modal: true,
 	        visible: false,
 	        resizable: false,
 	        width: 300
     }).data("kendoWindow");
    	if(this.kendoWindow != null)
    		this.centerKendoWindow();
	},
	notifyForSucess : function(message){
		this.initNotification();
		this.notification.show({
            message: message
        }, "upload-success");
	},
	notifyForError : function(message){
		this.initNotification();
		this.notification.show({
             title: "Error!",
             message: message
         }, "error");
	},
	initNotification : function(){
		this.notification = $("#kendoNotificationDiv").kendoNotification({
            position: {
                pinned: true,
                top: 30,
                right: 30
            },
            autoHideAfter: 3000,
            stacking: "down",
            button: true,
            templates: [{
                type: "info",
                template: $("#emailTemplate").html()
            }, {
                type: "error",
                template: $("#errorTemplate").html()
            }, {
                type: "upload-success",
                template: $("#successTemplate").html()
            }]

        }).data("kendoNotification");
	},
	initMeasuredWindow : function(){
		this.measuredWindow = $("#measuredWindow").kendoWindow({
			title: kendoKipHelper.kendoInitTitle,
 	        modal: true,
 	        visible: false,
 	        resizable: false,
 	        width: "300", 
 	        height:"300", 
 	        resizable: false
		});
	},
	centerKendoWindow : function(){
    	this.kendoWindow.center(); 
    },
    centerMeasuredKendoWindow : function(){
    	/*this.measuredWindow.center(); */
    },
    closeHelperKendoWindow : function(){
    	this.kendoWindow.close();
    },
    closeMeasuredWindow : function(){
    	this.measuredWindow.close();
    },
    closeMeasuredWindowBackUp : function(){
    	this.measuredWindowBackUp.close();
    },
	getCloseHelperKendoWindowStr : function(){
		return "kendoKipHelperInstance.closeHelperKendoWindow()";
	},
	spinnerWindow : null,
	initSpinnerWindow : function(){
		this.spinnerWindow = $("#spinnerWindow").kendoWindow({
 	        title: "Hunter Loading...",
 	        modal: true,
 	        visible: false,
 	        resizable: false,
 	        width: 300
     }).data("kendoWindow");
    	if(this.spinnerWindow != null)
    		this.initSpinnerWindow();
	},
	showSpinnerOnSimpleWindow : function(){
		if(this.spinnerWindow == null){
			this.initSpinnerWindow();
		}
		var OKButton = this.createKendoSmallIconTagTemplateNoIcon("kendoKipHelperInstance.closeHelperKendoWindow()", "<span style='padding-right:10px;padding-left:10px;' >Cancel</span>", false);
		this.spinnerWindow.content("<div style='width:200px;height:150px;'></div>" + OKButton);
		this.spinnerWindow.center();
		this.spinnerWindow.open();
		kendo.ui.progress(this.spinnerWindow.element, true);
	},
	showSimplePopup : function(title, content){
		var OKButton = this.createKendoSmallIconTagTemplateNoIcon("kendoKipHelperInstance.closeHelperKendoWindow()", "<span style='padding-right:10px;padding-left:10px;' >OK</span>", false);
		if(this.kendoWindow == null){
			this.initKendoWindow();
		}
		this.kendoWindow.title = title;
		this.kendoWindow.content(content + "<br/><br/> " + OKButton);
		this.kendoWindow.center();
		this.kendoWindow.open();
	},
	showPopupWithNoButtons : function(title, content){
		this.initKendoWindow();
		this.kendoWindow.title = title;
		this.kendoWindow.content(content);
		this.kendoWindow.center();
		this.kendoWindow.open();
	},
	createKendoSmallIconTagTemplate : function(_kendoUiImgName, _toBeCalledScript,buttonName){
		var buttonNameStr = '';
		if(buttonName != null)
			buttonNameStr = buttonName;
		var scriptStr = "<center><a onclick='"+ _toBeCalledScript + "' class='k-button' style='padding:5px;'><span class='k-icon k-i-"+_kendoUiImgName+"'></span>"+ buttonNameStr +"</a></center>";
		return scriptStr;
	},
	createKendoSmallIconTagTemplateNoIcon : function(_toBeCalledScript,buttonName){
		var buttonNameStr = '';
		if(buttonName != null)
			buttonNameStr = buttonName;
		var scriptStr = "<center><a onclick='"+ _toBeCalledScript + "' class='k-button' style='padding:5px;'>"+ buttonNameStr +"</a></center>";
		return scriptStr;
	},
	createHidableIconTagTemplate : function(_kendoUiImgName, _toBeCalledScript,buttonName, isHide){
		var buttonNameStr = '';
		if(buttonName != null)
			buttonNameStr = buttonName;
		var display;
		if(isHide)
			display = "display:none";
		else 
			display = "";
		var string = "<center><a onclick='"+ _toBeCalledScript + "' class='k-button' style='padding:5px;"+ display +";'><span class='k-icon k-i-"+_kendoUiImgName+"'></span>"+ buttonNameStr +"</a></center>";
		return string;
	},
	showPopupToDeleteAddress : function(id, message){
		  this.kendoWindow.title("Delete Item Class Or Group");
		  this.kendoWindow.content(message);
		  this.kendoWindow.open();
	 },
	 closeWindowWithOnClose : function(){
		 this.kendoWindowWithOnClose.close();
		 $(".k-overlay").remove();
	 },
	 initWindowWithOnClose : function(){
		 var window = $("#kendoWindowWithOnCloseFunc").kendoWindow({
			  pinned : true,
			  resizable: false,
			  modal: true,
			  animation: {
			    close: {
			      effects: "fade:out",
			      duration: 400
			    },
			  }
		  }).data("kendoWindow");
		 this.kendoWindowWithOnClose = window;
	 },
	 showWindowWithOnClose : function(content, title){
		 if( this.kendoWindowWithOnClose == null ){
			 this.initWindowWithOnClose();
		 }
		this.kendoWindowWithOnClose.title = title;
		this.kendoWindowWithOnClose.content(content);
		this.kendoWindowWithOnClose.center();
		this.kendoWindowWithOnClose.open();
	 },
	 showPercentMeasuredOKCancelTitledPopup : function(content, title, width, height){
		 
		var window = $("#measuredWindow").kendoWindow({
			  title : title,
			  pinned : true,
			  resizable: false,
			  modal: true,
			  animation: {
			    close: {
			      effects: "fade:out",
			      duration: 400
			    },
			    open: {
			    	effects: "slideIn:down fadeIn",
	                duration: 800
				}
			  }
		  }).data("kendoWindow");
		
		this.measuredWindow = window;
		this.measuredWindow.content(content);
		this.measuredWindow.center();
		this.measuredWindow.open();
		
	 },
	 showBackUpPercentMeasuredOKCancelTitledPopup : function(content, title, width, height){
		var window = $("#measuredWindowBackUp").kendoWindow({
			  title : title,
			  width : width + "%",
			  height : height + "%",
			  pinned : true,
			  resizable: false,
			  modal:true
		  }).data("kendoWindow");
		this.measuredWindowBackUp = window;
		this.measuredWindowBackUp.content(content);
		this.measuredWindowBackUp.center();
		this.measuredWindowBackUp.open();
	 },
	 showOKCancelToDeleteItemGroup : function(id, message){
		  var b1 = this.createKendoSmallIconTagTemplate("close", "itemGroupVM.deleteSelectedItemGroup(" + id +")", "Delete");
		  var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
		  var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
		  this.kendoWindow.content(message + butTable);
		  this.kendoWindow.open();
	 },
	 showOKCancelToDeleteTaskRegion : function(id, message){
		 this.initKendoWindow();
		  var b1 = this.createKendoSmallIconTagTemplate("close", "hunterAdminClientUserVM.deleteSelectedTaskRegion(" + id +")", "Delete");
		  var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
		  var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
		  this.kendoWindow.content(message + butTable);
		  this.kendoWindow.open();
	 },
	 showOKCancelToDeleteReceiverGroup : function(id, message){
		  this.initKendoWindow();
		  var b1 = this.createKendoSmallIconTagTemplate("close", "receiverGroupVM.deleteSelectedGroup(" + id +")", "Delete");
		  var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
		  var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
		  this.kendoWindow.content(message + butTable);
		  this.kendoWindow.open();
	 },
	 showOKToDeleteItem : function(id,message,methodName){
		 this.initKendoWindow();
		  var b1 = this.createKendoSmallIconTagTemplate("close", methodName + "(" + id +")", "Delete");
		  var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
		  var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
		  this.kendoWindow.content(message + butTable);
		  this.kendoWindow.open();
	 },
	 showOKCancelToDeleteHunterUser : function(id, message){
		 if(this.kendoWindow == null)
			  this.initKendoWindow();
		  var b1 = this.createKendoSmallIconTagTemplate("close", "hunterAdminClientUserVM.deleteSelectedUser(" + id +")", "Delete");
		  var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
		  var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
		  this.kendoWindow.content(message + butTable);
		  this.kendoWindow.open();
	 },
	 showOKCancelToDeleteHunterTask : function(id, message){
		 if(this.kendoWindow == null)
			  this.initKendoWindow();
		  var b1 = this.createKendoSmallIconTagTemplate("close", "hunterAdminClientUserVM.deleteSelectedTask(" + id +")", "Delete");
		  var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
		  var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
		  this.kendoWindow.content(message + butTable);
		  this.kendoWindow.open();
	 },
	 showOKCancelToSaveAs : function(id, message){
		  if(this.kendoWindow == null)
			  this.initKendoWindow();
		  var b1 = this.createKendoSmallIconTagTemplate("tick", "itemGroupVM.performSaveOperation(" + id +")", "Save As");
		  var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
		  var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
		  this.kendoWindow.content(message + butTable);
		  this.kendoWindow.open();
	 },
	 showOKCancelToDeleteWorkflowConfig : function(configId){
		 
		 	var grid = $("#programTestMeasureGrid").data("kendoGrid");
			var model = grid.dataSource.get(configId);
			var objType = model.objType;
			var shortPath = model.shortPath;
			shortPath = shortPath === "N" ? "None short Path" : "Short Path";
			console.log("Object Type >> " + objType + " shortPath >> " + shortPath);
			var message = '"<b>' + shortPath + " " + objType + '</b>  will be deleted. <br/> Are you sure?"';
			 
			var b1 = this.createKendoSmallIconTagTemplate("close", 'workflowConfigVM.deleteSelectedWorkflowConfig("' + configId +'")', "Delete");
			var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
			var butTable = "<br/> <table style='width:60%;margin-left:20%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
			this.initKendoWindow();
			this.kendoWindow.content(message + butTable);
			this.kendoWindow.open();
	 },
	 createContextEditButton : function(hasText){
		 if(hasText){
			 return '<center><a class="k-button k-button-icontext k-grid-edit" href="#"  style="min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);">&nbsp;&nbsp;<span class="k-icon k-edit">Edit</span></a></center>';
		 }else{
			 return '<center><a class="k-button k-button-icontext k-grid-edit" href="#"  style="min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);">&nbsp;&nbsp;<span class="k-icon k-edit"></span></a></center>';
		 }
	 },
	 createDisabledContextEditButton : function(hasText){
		 if(hasText){
			 return '<center><a class="k-button" href="#" style="min-width:28px;background-color:rgb(165,255,204);border : 1px solid rgb(120,186,210);"><span class="k-icon k-edit"></span>Span</a></center>';
		 }else{
			 return '<center><a class="k-button" href="#" style="min-width:28px;background-color:rgb(165,255,204);border : 1px solid rgb(120,186,210);"><span class="k-icon k-edit"></span></a></center>';
		 }
		 
	 },
	 createEditButtonsEditMode : function(hasText){
		 var both = '<a class="k-button k-button-icontext k-primary k-grid-update" href="#" style="min-width:28px;background-color:rgb(165,255,204);border : 1px solid rgb(120,186,210);" >&nbsp;&nbsp;<span class="k-icon k-update"></span></a>'+
				 '<a class="k-button k-button-icontext k-grid-cancel" href="#" style="min-width:28px;background-color:rgb(165,255,204);border : 1px solid rgb(120,186,210);" >&nbsp;&nbsp;<span class="k-icon k-cancel"></span></a>';
		 return both;
	 },
	 createUpdateButtonsEditMode : function(hasText){
		 var update = '<a class="k-button k-button-icontext k-primary k-grid-update" href="#" style="min-width:28px;background-color:rgb(165,255,204);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-update"></span></a>';
		 return update;
	 },
	 createCancelButtonsEditMode : function(hasText){
		 var cancel = '<a class="k-button k-button-icontext k-grid-cancel" href="#" style="min-width:28px;background-color:rgb(165,255,204);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-cancel"></span></a>';
		 return cancel;
	 },
	 createSearchButton : function(hasText, onClick){
		 if(hasText){
			 return "<center><a onClick='" + onClick +"' class='k-button' href='#'  style='min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210)'><span class='k-icon k-i-search'>Search</span></a></center>";
		 }else{
			 return "<center><a onClick='" + onClick +"' class='k-button' href='#'  style='min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210)'><span class='k-icon k-i-search'></span></a></center>";
		 }
	 },
	 createDeleteButton : function(hasText, onClick){
		 if(hasText){
			 return "<center><a onclick='"+ onClick +"' class='k-button' style='min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210)'><span class='k-icon k-i-close'></span>Delete</a></center>";
		 }else{
			 return "<center><a onclick='"+ onClick +"' class='k-button' style='min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210)'><span class='k-icon k-i-close'></span></a></center>";
		 }
	 },
	 createSimpleHunterButton : function(iconName,text, onClick){
		 if(text != null){
			 return "<center><a onclick='"+ onClick +"' class='k-button' style='min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210)'><span class='k-icon k-i-"+ iconName +"'></span>"+ text +"</a></center>";
		 }else{
			 return "<center><a onclick='"+ onClick +"' class='k-button' style='min-width:28px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210)'><span class='k-icon k-i-"+ iconName +"'></span></a></center>";
		 }
	 },
	 addKendoOverLay : function(){
		var html = '<div id="hunterKendoOverLay"  style="display: block; z-index: 10004; opacity: 0.5;" class="k-overlay"></div>';
		$("body").append(html);
	 },
	 removeKendoOverLay : function(){
		$("#hunterKendoOverLay").remove();
	 },
	 createSimpleTable : function(headers, content){
		 if(headers == null || content == null || headers.length == 0 || content.length == 0){
			 headers = {"Number1" : "Number One","Number2" : "Number Two"};
			 content = [{"Number1" : "1","Number2" : "2"},{"Number1" : "3","Number2" : "3"}];
		 };
		 var defCss = "width:40%;margin-left:30%;";
		 var t1 = "<table>", t2 = "</table>";tr1 = "<tr>", tr2 = "</tr>",td1 = "<td>",td2 = "</td>", th1="<th>", th2="</th>";
		 var headerHtml = "<thead>" + tr1;
		 for(var i=0; i<headers.length;i++){
			 headerHtml += th1 + headers[i] + th2;
		 }
		 headerHtml += tr2 + "</thead>";
		 var bodyHtml = "<tbody>";
		 for(var j=0; j<content.length;j++){
			 var data = content[j];
			 var dataTr = tr1;
			 for(var i=0; i<data.length;i++){
				 dataTr += td1 + data[i] + td2;
			 }
			 dataTr += tr2;
			 dataTr = dataTr=='' ? '' : dataTr + tr2;
			 bodyHtml += dataTr;
		 }
		 bodyHtml = t1 + headerHtml + bodyHtml + "</tbody>" + t2;
		 return bodyHtml;
	 },
	 showOKCancelToAssociateWorkflowConfig : function(configId, groupId, message){
		 	var params = configId + '","' + groupId;
			var b1 = this.createKendoSmallIconTagTemplate("close", 'workflowConfigVM.updateDSAndSyncForAssociateTree("' + params + '")', "Associate");
			var b2 = this.createKendoSmallIconTagTemplate("cancel", "kendoKipHelperInstance.closeHelperKendoWindow()", "Cancel");
			var butTable = "<br/> <table style='width:80%;margin-left:10%;'><tr><td>" + b1 + "</td><td>" + b2 + "</tr></table>";
			this.initKendoWindow();
			this.kendoWindow.content(message + butTable);
			this.kendoWindow.open();
	 },
	popupWarning : function(message, msgTyp){
		//this.showSuccessNotification(message);
		this.showErrorOrSuccessMsg(msgTyp, message);
		//this.showEmailNotification("Hey", message);
	},
	constainsJavascriptString : function(main,sub){
		var index = main.indexOf(sub);
		console.log("index found >> " + index);
		var contains = index == -1 ? false : true;
		return contains;
	},
	showErrorOrSuccessFromData : function(data_){
			var json = JSON.parse(JSON.stringify(data_));
			alert(json["status"]); 
		  var data = jQuery.parseJSON(JSON.stringify(data_));
		  var status = JSON.stringify(data[0]), 
			  message = JSON.stringify(data[0]);
		
		alert(status + " : " + message);
		
		if(status != null && status === HunterConstants.STATUS_SUCCESS){
			this.showSuccessNotification(message != null ? message : "Successfully peformed operation!");
		}else{
			this.showErrorNotification(message != null ? message : "Failed! Application error occurred!");
		}
	},
	ajaxLoadEleWithContents : function(elementId, url){
		console.log("loading content from URL(" + url +")"); 
		$.get(url, function(contents) {
			  $('#' + elementId).append(contents);
			  console.log("Successfully loaded contents from URL(" + url +")");
		});
	},
	/* 
	 * Not, this method calls your method 'callAfter'. 
	 * 'callAfter' must accept the data from the server.
	 * If the communitcation fails, the method will just show error message.
	 * Please overload the method for other failure processing needs. 
	 * Please don't modify this method!
	 * */
	ajaxPostData : function(data, contentType, dataType, method, url, callAfter){
		$.ajax({
			url: url,
		    data : data,
		    method: method,
		    dataType : dataType, 
		    contentType : contentType
		}).done(function(data) {
			var code = callAfter + "('"+ data +"')";
			var func = new Function(code);
			func();
		 }).fail(function(data) {
			 kendoKipHelperInstance.popupWarning(data.statusText + " (" + data.status + "). Please contact Production Support.", "Error");
		 });
	},
	ajaxPostDataWithCall : function(data, contentType, dataType, method, url, callAfter, call){
		$.ajax({
			url: url,
		    data : data,
		    method: method,
		    dataType : dataType, 
		    contentType : contentType
		}).done(function(data) {
			var code = callAfter + "("+ JSON.stringify(data) +")";
			var func = new Function(code);
			func();
		 }).fail(function(data) {
			 var code = call + "()";   
			 var func = new Function(code);
			 func();
			 kendoKipHelperInstance.popupWarning(data.statusText + " (" + data.status + "). Please contact Production Support.", "Error");
		 });
	},
	/* 
	 * This method is recommended over the 'ajaxPostData' for json array responses
	 */
	ajaxPostDataForJsonResponse : function(data, contentType, dataType, method, url, callAfter){
		$.ajax({
			url: url,
		    data : data,
		    method: method,
		    dataType : dataType, 
		    contentType : contentType
		}).done(function(data) {
			var code = callAfter + "('"+ JSON.stringify(data) +"')";
			var func = new Function(code);
			func();
		 }).fail(function(data) {
			 kendoKipHelperInstance.popupWarning(data.statusText + " (" + data.status + "). Please contact Production Support.", "Error");
		 });
	},
	
	/* 
	 * This method is recommended over the 'ajaxPostData' for json array responses
	 */
	ajaxPostDataForJsonResponseWthCllbck : function(data, contentType, dataType, method, url, callAfter,callException){
		$.ajax({
			url: url,
		    data : data,
		    method: method,
		    dataType : dataType, 
		    contentType : contentType
		}).done(function(data) {
			var code = callAfter + "('"+ JSON.stringify(data) +"')";
			var func = new Function(code);
			func();
		 }).fail(function(data) {
			 var code = callException + "()";
			 var func = new Function(code);
			 func();
			 kendoKipHelperInstance.popupWarning(data.statusText + " (" + data.status + "). Please contact Production Support.", "Error");
		 });
	},
	execTimedFunc : function (time, funct, data){
		var dataStr = "";
		if(data != null && data.length > 0){
			for(var i=0; i<data.length;i++){
				var datum = data[i];
				dataStr += "'" + datum + "',"; 
			}
			if( dataStr.trim().length > 1 && dataStr.slice(-1) === "," ){
				dataStr = dataStr.substring(0, dataStr.length - 1);
			}
		}
		setTimeout(function(){ 
			var code = funct + "("+ dataStr +")";
			var func = new Function(code);
			func();
		}, time);
	},
	confirmAndDoFunction : function(message, funcName){
		var confirmed = confirm( message );
		if( confirmed ){
			var func = new Function(funcName+"()"); 
			func();
		}
	},
	showReceiversGridForRegionOrGroup : function(params ){
		kendo.destroy( $("#selRegionMessgReceiverGrid") );
		$("#selRegionMessgReceiverGrid").html('');
		$("#selRegionMessgReceiverGrid").kendoGrid({
			 pageable: {
			    pageSize	 : 100,
			    previousNext : true,
			    refresh		 : true,
			    itemsPerPage : "Contacts Per Page"
			  },
			  columns : [
		           	{"field":"index", "title":"Index", "width":"80px"}, 
		           	{"field":"contact", "title":"Contact","width":"300px"}
		           ],
	        dataSource: {
	        	transport: {
	                read: {
	                	data : {
	                	   regParams : params
	                	},
	                	type:"json",
	                	method:"POST",
	                	url: HunterConstants.getHunterBaseURL("region/action/task/regions/receivers/getPageReceivers")
	                }
	            },
	            requestStart: function(e) {
	                var type = e.type;
	                var message = null;
	                if(type === 'read'){
	                	return;
	                }
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
	            pageSize: 100,
	            serverPaging: true,
	            schema: {
	                data: 'data',
	                total: 'total',
	                errors: 'errors'
	            }
	        }
	        
	    });
		$("#selRegionMessgReceiverGrid").after('<button onClick="kendoKipHelperInstance.closeWindowWithOnClose()" class="k-button" style="width:200px;margin-left:38%;margin-top:10px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-i-close"></span>Close</button>');
	}
});



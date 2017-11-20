var 
newTxtMsgManagerInstance = null,
createTxtMsgOwnersData 	 = null;
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

$("document").ready(function(){
	
	var CreateTextMessageManager = kendo.Class.extend({
		
		sProvidersWidget 	: null,
		messageStatusWidget : null,
		msgOwnersWidget 	: null,
		ownersData 			: {},
		
		execute : function(){
			 this.initialize();
			 this.loadTaskMessageData();
		},
		initialize : function(){
			if(createTxtMsgOwnersData == null || createTxtMsgOwnersData.length == 0)
				this.loadUsersData();
			this.showPopup();
			this.style();
			this.createWidgets();
			this.bindCharacterCount();
			this.bindProgressIcon(true);	
		},
		loadTaskMessageData : function(){
			var taskBean = hunterAdminClientUserVM.getSelectedTaskBean();
			var message = taskBean.get("taskMessage");
			if(message != null){
				var str = kendo.stringify(message);
				$.parseJSON(str);
				this.afterLoadingTaskMessageData(kendo.stringify(message));
			}else{
				var url = baseUrl + "/task/action/tskMsg/getDefault/" + taskBean.get("taskId");
				var call = "newTxtMsgManagerInstance.afterLoadingTaskMessageData";
				kendoKipHelperInstance.ajaxPostData(null, "application/json", "json", "POST", url , call);
			}
		},
		afterLoadingTaskMessageData : function(message){
			message = $.parseJSON(message);
			var provider = (message["provider"]["providerId"]) + "";
			$("#newTextMsgSProvider").data("kendoDropDownList").value(provider);
			var txtStr = message["msgText"];
			 if( txtStr != null ){
				txtStr = txtStr.replace(/&apos;/g,"'"),
				txtStr = txtStr.replace(/&quot;/g,'"');
			 }
			$("#newTxtMsgTxtArea").val( txtStr );
			var owner = this.getOwnerValueForUserName(message["msgOwner"]);
			$("#newTxtMsgOwnerInput").data("kendoDropDownList").value(owner);
			$("#newTxtMsgMsgStatus").data("kendoDropDownList").value(message["msgLifeStatus"]);
			newTxtMsgManagerInstance.updateCharCount();
			newTxtMsgManagerInstance.bindProgressIcon(false);
		},
		getOwnerValueForUserName : function(userName_){
			var clientId = null;
			if(userName_ != null){
				for(var i=0; i < createTxtMsgOwnersData.length;i++){
					var owner = createTxtMsgOwnersData[i];
					var userName = owner["userName"];
					if(userName_ == userName){
						clientId = owner["clientId"];
						break;
					}
				}
			}
			if(clientId == null){
				clientId = "15"; //admin
			}
			return clientId;
		},
		getOwnerUserNameForValue : function(value){
			var userName_ = null;
			if(value != null){
				for(var i=0; i < createTxtMsgOwnersData.length;i++){
					var owner = createTxtMsgOwnersData[i];
					var userName = owner["userName"];
					var clientId = owner["clientId"];
					if(clientId == value){
						userName_ = userName;
						break;
					}
				}
			}
			if(userName_ == null){
				clientId = "admin"; //admin
			}
			return userName_;
		},
		showPopup : function(){
			var template = $("#createNewTextMessageTemplate").html();
			kendoKipHelperInstance.showWindowWithOnClose(template,"New Task Process");
		},
		style : function(){
			$("#newTxtMsgViewTable td").css({"height":"22px","font-size":"15px",
				"font-family":"Courier New, Courier,Sans Serif,Times New Roman,Serif",
				"border-bottom":"1px solid #D7ECF6",
			});
		},
		createWidgets : function(){
			
			this.destroyWidgets();
			
			var sProvidersWidget = $("#newTextMsgSProvider").kendoDropDownList({
				 dataTextField: "providerName",
                 dataValueField: "providerId",
                 dataSource: HunterConstants.SERVICE_PROVIDER_DATA
			});
			
			var messageStatusWidget = $("#newTxtMsgMsgStatus").kendoDropDownList({
				 dataTextField: "statusText",
				 dataValueField: "statusValue",
				 dataSource: HunterConstants.HUNTER_LIFE_STATUSES
			});
			
			var msgOwnersWidget = $("#newTxtMsgOwnerInput").kendoDropDownList({
				 dataTextField: "fullName",
				 dataValueField: "clientId",
				 dataSource: createTxtMsgOwnersData
			});
			
						
			this.sProvidersWidget = sProvidersWidget;
			this.messageStatusWidget = messageStatusWidget;
			this.msgOwnersWidget = msgOwnersWidget;
		},
		destroyWidgets : function(){
			
			kendo.destroy($("#newTextMsgSProvider"));
			kendo.destroy($("#newTxtMsgMsgStatus"));
			kendo.destroy($("#newTxtMsgMsgStatus"));
			
			this.msgOwnersWidget = null;
			this.sProvidersWidget = null;
			this.messageStatusWidget = null;
		},
		loadUsersData : function(){
			var call = "newTxtMsgManagerInstance.afterLoadingOwners";
			var url = baseUrl + "/hunteruser/action/client/getAllClientsDetails";
			kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url , call);
		},
		afterLoadingOwners : function(data){
			createTxtMsgOwnersData = $.parseJSON(data);
		},
		bindCharacterCount : function(){
			$("#newTxtMsgTxtArea").attr("onkeyup", "newTxtMsgManagerInstance.updateCharCount()");
			$("#newTxtMsgTxtArea").attr("onkeydown", "newTxtMsgManagerInstance.updateCharCount()");
		},
		updateCharCount : function(){
			var count = $("#newTxtMsgTxtArea").val().length;
			var color = '';
			var imgName = '';
			if(count > 256){
				color = 'red';
				imgName = 'x-button.png';
			}else{
				color = 'green';
				imgName = 'tick.png';
			}
			var html = '<span style="18px;color:'+ color +';font-weight:bold;" >'+ count +'</span>&nbsp;<img src="'+ baseUrl +'/static/resources/images/'+ imgName +'" width="12px" height="12px"   />';
			$("#newTxtMsgCharCount").html(html);
			this.validateTxtMsg();
		},
		validateTxtMsg : function(){
			var message = null;
			var count = $("#newTxtMsgTxtArea").val().length;
			if(count == 0){
				message = "Text message cannot be blank";
			}else if(count > 512){
				message = "Text message is longer than 512 charaters.";
			}
			$("#newTxtMsgTxtErrMsgs").text(""); 
			if(message != null)
				$("#newTxtMsgTxtErrMsgs").text(message);
			return message;
		},
		bindProgressIcon : function(boolean){
			if(boolean){
				$("#newTxtMsgTxtErrMsgs").text("Pleast wait. Loading data...");
			}else{
				$("#newTxtMsgTxtErrMsgs").text(""); 
			}
			kendo.ui.progress($("#newTxtMsgTxtErrMsgs"), boolean);
		},
		validateAndSubmitTxtMsgData : function(){
			
			var errorMsg = this.validateTxtMsg();
			
			if(errorMsg == null){
				var msgText = $("#newTxtMsgTxtArea").val();
				if( msgText != null ){
					msgText = msgText.replace(/\'/g,'&apos;');
					msgText = msgText.replace(/\"/g,'&quot;');
				}
				this.bindProgressIcon(true);
				var 
				providerId 	= $("#newTextMsgSProvider").data("kendoDropDownList").value(),
				msgOwner 	= $("#newTxtMsgOwnerInput").data("kendoDropDownList").value(),
				msgSts 		= $("#newTxtMsgMsgStatus").data("kendoDropDownList").value(),
				taskBean 	= hunterAdminClientUserVM.getSelectedTaskBean(),
				taskId 		= taskBean.get("taskId"), 
				userName 	= this.getOwnerUserNameForValue(msgOwner),
				data 		= { "providerId":providerId,"msgText":msgText,"msgOwner":userName,"msgSts":msgSts,"taskId":taskId }, 
				url 		= baseUrl + "/message/action/tskMsg/updateTxtMsg",
				call 		= "newTxtMsgManagerInstance.afterSubmittingTxtMsgData",
				callExc		= "newTxtMsgManagerInstance.closeTextMsgWindow";
				data = JSON.stringify(data);
				
				kendoKipHelperInstance.ajaxPostDataWithCall(data, "application/json", "json", "POST", url , call,callExc);
			}
		},
		afterSubmittingTxtMsgData : function(data){
			data = $.parseJSON(data);
			var status 	= data[HunterConstants.STATUS_STRING], 
				message = data[HunterConstants.MESSAGE_STRING];
			kendoKipHelperInstance.showErrorOrSuccessMsg(status,message);
			this.bindProgressIcon(false);
			if(HunterConstants.STATUS_SUCCESS == status){
				kendoKipHelperInstance.closeWindowWithOnClose();
				$(".k-overlay").remove(); // some times this was not getting removed intermittently.
				var ds = hunterAdminClientUserVM.get("hunterClientTaskGrid").dataSource;
				ds.read();
			}
		},
		closeTextMsgWindow : function(){
			kendoKipHelperInstance.closeWindowWithOnClose();
		}
		
	});
	newTxtMsgManagerInstance = new CreateTextMessageManager();
	newTxtMsgManagerInstance.loadUsersData();
	hunterAdminClientUserVM.set("createTextMessageManager", newTxtMsgManagerInstance);
	
});
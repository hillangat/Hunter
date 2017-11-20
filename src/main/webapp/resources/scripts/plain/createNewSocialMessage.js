var 
newSocialMsgManagerInstance = null,
createTxtMsgOwnersData 	 = null;

$("document").ready(function(){
	
	var CreateSocialMessageManager = kendo.Class.extend({
		
		socialMediasWidget : null,
		socialPostsTypesWidget : null,
		sclMsgUploadPostWidget : null,
		ownersData 			: {},
		
		
		
		execute : function(){
			this.showPopup();
			this.initialize();
			this.loadTaskMessageData();
		},
		initialize : function(){
			if(createTxtMsgOwnersData == null || createTxtMsgOwnersData.length == 0)
				this.loadUsersData();
			this.showPopup();
			this.createWidgets();
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
				new kendoKipHelper().ajaxPostData(null, "application/json", "json", "POST", url , call);
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
			newSocialMsgManagerInstance.updateCharCount();
			newSocialMsgManagerInstance.bindProgressIcon(false);
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
			var template = $("#createNewSocialMessageTemplate").html();
			kendoKipHelperInstance.showWindowWithOnClose(template,"New Task Process");
		},
		createWidgets : function(){
			
			this.destroyWidgets();
			
			var 
			socialMediasWidget = $("#createSocialMsgMedaiType").kendoDropDownList({
				 dataTextField: "text",
                 dataValueField: "value",
                 dataSource: HunterConstants.SOCIAL_MEDIA_TYPS_ARRAY
			}),
			socialPostsTypesWidget = $("#createSocialMsgPostType").kendoDropDownList({
				 dataTextField: "text",
				 dataValueField: "value",
				 dataSource: HunterConstants.SOCIAL_MEDIA_TYPS_ARRAY
			}),
			sclMsgUploadPostWidget = $("#createSclMsgUploadPost").kendoUpload({
				 autoUpload: true
			});
			
			this.socialMediasWidget 	= socialMediasWidget;
			this.socialPostsTypesWidget = socialPostsTypesWidget;
			this.sclMsgUploadPostWidget = sclMsgUploadPostWidget; 
			
		},
		destroyWidgets : function(){
			
			kendo.destroy($("#createSocialMsgMedaiType"));
			kendo.destroy($("#createSocialMsgPostType"));
			
			this.socialMediasWidget = null;
			this.socialPostsTypesWidget = null;
			
		},
		loadUsersData : function(){
			
			var 
			call = "newTxtMsgManagerInstance.afterLoadingOwners",
			url = baseUrl + "/hunteruser/action/client/getAllClientsDetails";
			
			new kendoKipHelper().ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url , call);
		},
		afterLoadingOwners : function(data){
			createTxtMsgOwnersData = $.parseJSON(data);
		},
		bindCharacterCount : function(){
			$("#newSocialMsgTxtArea").attr("onkeyup", "newTxtMsgManagerInstance.updateCharCount()");
			$("#newSocialMsgTxtArea").attr("onkeydown", "newTxtMsgManagerInstance.updateCharCount()");
		},
		updateCharCount : function(){
			var count = $("#newSocialMsgTxtArea").val().length;
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
			var count = $("#newSocialMsgTxtArea").val().length;
			if(count == 0){
				message = "Text message cannot be blank";
			}else if(count > 512){
				message = "Text message is longer than 512 charaters.";
			}
			$("#newSocialMsgTxtErrMsgs").text(""); 
			if(message != null)
				$("#newSocialMsgTxtErrMsgs").text(message);
			return message;
		},
		bindProgressIcon : function(boolean){
			if(boolean){
				$("#newSocialMsgTxtErrMsgs").text("Pleast wait. Loading data...");
			}else{
				$("#newSocialMsgTxtErrMsgs").text(""); 
			}
			kendo.ui.progress($("#newSocialMsgTxtErrMsgs"), boolean);
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
				
				new kendoKipHelper().ajaxPostDataWithCall(data, "application/json", "json", "POST", url , call,callExc);
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
	newSocialMsgManagerInstance = new CreateSocialMessageManager();
	newSocialMsgManagerInstance.loadUsersData();
	hunterAdminClientUserVM.set("createSocialMessageManager", newSocialMsgManagerInstance);
	
});
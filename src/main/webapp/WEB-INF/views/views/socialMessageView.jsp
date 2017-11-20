<%@include file="../common/HeaderFile.jsp"%>

<style>

.expandFromOrigin
{
        -webkit-transform: scale(1.3);
        -ms-transform: scale(1.3);
        transform: scale(1.3);
}


</style>

<script type="text/javascript"> 

	var vm = null;
	
	function getSocialMsToolBarTemplate(){
		return '<button onClick="vm.showAvailableSocialGroups()" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-plus" ></span>Add Social Group</button>';
	}

	$("document").ready(function(){
		
		var kendoKipHelperInstance  = new kendoKipHelper();
		kendoKipHelperInstance.init();
		
		var 
		SocialGroupModel = kendo.data.Model.define({
		    id: "groupId", 
		    fields: {
		    	"groupId" 	: { type: "number" },
		        "groupName"	: { type: "string" },
		        "regionName": { type: "string" },
		        "socialType": { type: "string" }
		    },
		    getRemoveButton : function(){
		    	return kendoKipHelperInstance.createDeleteButton(false,"vm.onClickRemoveSocialGrp("+ this.get("groupId") +")"); 
		    }
		}),
		selSocialGroupDS = new kendo.data.DataSource({
			schema: {
			    model: SocialGroupModel
			},
			transport : {
				read:  {
					 url: function(){
				    	  var 
				    	  selMsgId = vm.get("selSocialMsgId") == null ? "0" : vm.get("selSocialMsgId")+"",
				    	  url = HunterConstants.getHunterBaseURL("social/action/msg/selGroups/read/" + selMsgId);
				    	  console.log( url );
				    	  return url;
				      },
				      dataType: "json",
				      contentType:"application/json",
				      method: "POST"
				}
			}
		});
		
		
		vm = kendo.observable({
			
			isVisible : true,
			
			selSocialMsgBean	: 
			{
				messageId : null, 
				dAppId : null,
				socialType:null,
				mediaType:null,
				postType:null,
				addedGroups:[], 
				socialPost:null 
			},
			
			createSocialMsgSocialType: null,
			createSocialMsgMediaType : null,
			createSocialMsgPostType : null,
			
			selSocialMsgId	: 1,
			socialTypes 	: HunterConstants.SOCIAL_TYPES_ARRAY,
			medialTypes		: HunterConstants.SOCIAL_MEDIA_TYPS_ARRAY,
			socialPostTypes : HunterConstants.SOCIAL_POST_ARRAY,
			
			selMsgSocialGroupDS 	: null,
			selectedSocialGroups 	: [],
			socialApps		: [],
			availableSocialGroupsGrid : null,
			
			submitSelSclGrps : function(){
				
			},
			addToSelectedSocialGroups : function(groupId){
				vm.get("selSocialMsgBean").addedGroups.push(groupId);
				alert(JSON.stringify(vm.get("selSocialMsgBean")));
			},
			beforeInit : function(){
				this.set("selMsgSocialGroupDS", selSocialGroupDS);
			},
			init : function(){
				this.beforeInit();
				kendo.bind($("#createNewSocialMessageContainer"), vm);
				this.afterInit();
			},
			afterInit : function(){
				$("#createSocialMsgAddSocialGroups").bind('mouseover', vm.onMouseOverAddSocialGroup);
				$("#createSocialMsgAddSocialGroups").bind('mouseleave', vm.onMouseOutAddSocialGroup);
				$("#selSocialGroupGrid").data("kendoGrid").dataSource.read();
				this.loadSocialApps();
			},
			loadSocialApps : function(){
				var url = HunterConstants.getHunterBaseURL("social/action/apps/dropdowns");
				kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "vm.afterGettingSocialApps");
			},
			afterGettingSocialApps : function(data){
				data = $.parseJSON(data);
				vm.set("socialApps", data);
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
			},
			onClickRemoveSocialGrp : function(id){
				var delete_ = confirm("Are you sure you want to delete?");
				if( delete_ ){
					console.log("Deleting...");
				}else{
					console.log("Exiting...");
				}
			},
			showAvailableSocialGroups : function(){
				
				$("#selSocialGroupGrid").addClass("hidden");
				$("#sclMsgAvailableSclGrps").removeClass("hidden");
				
				var grid_ = this.get("availableSocialGroupsGrid");
				
				if( grid_ != null ){
					grid_.dataSource.read();
					return;
				}
				
				
				
				var grid = $("#sclMsgavailableGrpsGrid").kendoGrid({
					dataSource : new kendo.data.DataSource({
						schema: {
						    model: SocialGroupModel
						},
						transport : {
							read:  {
							      url: function(){
							    	  selMsgId = vm.get("selSocialMsgId") == null ? "0" : vm.get("selSocialMsgId")+"",
							    	  url = HunterConstants.getHunterBaseURL("social/action/msg/availGroups/read/" + selMsgId);
							    	  console.log( url );
							    	  return url;
							      },
							      dataType: "json",
							      contentType:"application/json",
							      method: "POST"
							}
						}
					}),
					toolbar : kendo.template($("#availSclGrpsToolBar").html()),
					height: 400,
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
			            		  template: '<center><input onClick="vm.addToSelectedSocialGroups(#=groupId#)" type="checkbox"  /></center>', 'width':'70px' 
							 },
			                {
			                    field: "groupName",
			                    title: "Group Name"
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
			},
			onErrorSocialMsgUpload : function(e){
				console.log("Error occurred!");
			},
			onSuccessSocialMsgUpload : function(e){
				console.log("Success upload!");
				this.closeSclMsgFormTable();
			},
			closeSclMsgPreview : function(){
				$("#createSclMsgImgPreview").fadeOut("fast",function(){
					setTimeout(function(){
						$("#soclPostUploadFileFormsTable").fadeIn("fast"); 
					}, 1000); 
				});
			},
			closeSclMsgFormTable : function(){
				$("#soclPostUploadFileFormsTable").fadeOut("fast",function(){
					setTimeout(function(){
						$("#createSclMsgImgPreview").fadeIn("fast"); 
					}, 1000); 
				});
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
			}
		});
		vm.init();
	});
</script>




<div id="createNewSocialMessageContainer" class="k-block k-info-colored" style="width: 1200px;height: 750px; margin: 0 auto; padding: 4px; background-color: #d7eaf0;border-radius: 4px; border: 1px solid #afcad2;">
	<div id="processedTaskSocialMsg" style="position:relative;width: 91%; min-height: 670px; color: #0a2c2e; font-size: 13px; padding-left: 10px; background-color: #e6f7ff; margin-left: 4%; margin-top: 30px; border-radius: 4px; border: 1px solid #afcad2;">
		<table style="width:98%;margin-left:1%;table-layout:fixed;height:98%;">
		  <tr>
			<td style="width:100%;min-height:480px;" >
				<table style="width:100%;font-size:14px;">
					<tr>
						<td style="width:50%;">
							<table style="width:100%;" id="createSocialMsgFieldsTable">
								<tr>
									<td>Description : </td>
									<td><input id="createSocialMsgDescription" class="k-textbox" style="width:100%;background-color:#EBF6FC;" /></td>
								</tr>
								<tr>
									<td>Default Social App : </td>
									<td>
										<input 
								   			id = "createSocialMsgDfltSocialApp"
								   			data-role="dropdownlist"
				                   			data-auto-bind="false"
				                   			data-value-primitive="true"
				                   			data-text-field="text"
				                   			data-value-field="value"
				                   			data-option-label="Select Social App"
				                   			data-bind="value: createSocialMsgSocialType, source: socialApps"
											style="width:100%;"
				                        />	
									</td>
								</tr>
								<tr>
									<td>Social Type : </td>
									<td>
										<input 
								   			id = "createSocialMsgSocialType"
								   			data-role="dropdownlist"
				                   			data-auto-bind="false"
				                   			data-value-primitive="true"
				                   			data-text-field="text"
				                   			data-value-field="value"
				                   			data-option-label="Select Social Type"
				                   			data-bind="value: createSocialMsgSocialType, source: socialTypes"
											style="width:100%;"
				                        />	
									</td>
								</tr>
								<tr>
									<td>Media Type : </td>
									<td>
										<input 
								   			id = "createSocialMsgMedaiType"
								   			data-role="dropdownlist"
				                   			data-auto-bind="false"
				                   			data-value-primitive="true"
				                   			data-text-field="text"
				                   			data-value-field="value"
				                   			data-option-label="Select Media Type"
				                   			data-bind="value: createSocialMsgMediaType, source: medialTypes"
											style="width:100%;"
				                        />	
									</td>
								</tr>
								<tr>
									<td>Social Post Type : </td>
									<td>
										<input 
								   			id = "createSocialMsgPostType"
								   			data-role="dropdownlist"
				                   			data-auto-bind="false"
				                   			data-value-primitive="true"
				                   			data-text-field="text"
				                   			data-value-field="value"
				                   			data-option-label="Select Media Type"
				                   			data-bind="value: createSocialMsgMediaType, source: socialPostTypes"
											style="width:100%;"
				                        />	
									</td>
								</tr>
							</table>
						</td>
						<td style="width:50%;">
							<table style="width:100%;">
								<tr>
									<td style="width:100%;height:33px;text-align:center;text-decoration:underline;margin: 0 auto;" ><b>Social Post</b></td>
								</tr>
								<tr>
									<td style="border-bottom:1px solid #e6f7ff;height:3em;"><textarea id="createSocialMsgPost" cols="1" rows="4" class="k-textbox" style="width:100%;height:138px;;padding:10px;resize: none;background-color:#EBF6FC;" ></textarea></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		  </tr>
 		  <tr>
			<td style="width:100%;min-height:480px;" >
			  <table style="width:100%;min-height:480px;" >
					<tr style="width:100%;">
						<td style="width:48%;height:395px;border-spacing: 2%;border-radius:5px;" >
							<div style="height:90%;width:100%;" >
                                <table id="soclPostUploadFileFormsTable" style="table-layout: fixed;height:100%;width:100%;" >
									<tr>
										<td style="height:90%;" >
											<form id="soclPostUploadFileForms"  method="post" action="/kendo-ui/upload/submit" >
		                                        <div class="demo-section k-content">
		                                           <input name="files"
		                                           	   id="socialMsgUploadImgInput" 
		                                           	   onChange="vm.readURL(this)"
									                   type="file"
									                   data-role="upload"
									                   data-multiple="false"
									                   data-async="{ saveUrl: 'http://localhost:8080/Hunter/social/action/upload/profilePhoto', removeUrl: 'remove', autoUpload: true }"
									                   data-bind="visible: isVisible,
									                              enabled: isEnabled,
									                              events: { 
									                              	select	 : onSelectSocialMsgUpload,
									                              	complete : onCompleteSocialMsgUpload,
		               			 									error	 : onErrorSocialMsgUpload,
		               			 									success	 : onSuccessSocialMsgUpload
									                              }" 
									                   >
		                                        </div>
		                                   </form>
										</td>
									</tr>
									<tr>
										<td style="height:10%;text-align:center;" >
											<button onClick="vm.closeSclMsgFormTable()" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" > <span class="k-icon k-i-seek-n"></span>Upload New Message</button>
										</td>
									</tr>
								</table>
							<div id="createSclMsgImgPreview" class="hidden" style="width:100%;height:100%;border-radius:5px;" >
								<table style="table-layout: fixed;height:100%;width:100%;" >
									<tr>
										<td style="height:90%;" >
											<div style="width:100%;height:100%;border-radius:5px;">
												<img id="createSclMsgImgPreviewImg" alt="No Image" src="http://ashutosh.argusacademy.com/kerala2.jpg" style="max-width: 100%;max-height: 100%;border-radius:5px;" >
											</div>
										</td>
									</tr>
									<tr>
										<td style="height:10%;text-align:center;" >
											<button onClick="vm.closeSclMsgPreview()" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" > <span class="k-icon k-i-seek-n"></span>Upload New Message</button>
										</td>
									</tr>
								</table>
							</div>
							</div>
						</td>
						<td style="width:1%;" ></td>
						<td style="width:49%;height:395px;border-spacing: 2%;border-radius:5px;" >
							<table style="height:100%;width:100%;">
								<tr>
									<td style="height:94%;width:100%;" >
										<div style="width:100%;height:100%;">
											 <div
											 	 id="selSocialGroupGrid"
											 	 data-role="grid"
								                 data-editable="false"
								                 data-pageable="true"
								                 data-refreshable="true"
								                 data-toolbar= '[{"template": "#=getSocialMsToolBarTemplate()#"}]'
								                 data-height="400px"
								                 data-columns="[
								                 				 { 'field': 'groupId', title:'ID', 'width':'50px' },
								                                 { 'field': 'groupName', title:'Group Name'},
								                                 { 'field': 'socialType', title:'Social Type'},
								                                 { 'field': 'regionName', title:'Region Name'},
								                                 { 'field': '', title : 'Remove', template : '#=getRemoveButton()#', 'width':'70px'  },
								                              ]"
								                 data-bind="source: selMsgSocialGroupDS,
								                            events: {
								                              save: onSave
								                            }"
								                 style="font-size: 12px;" 
								                 >
								              </div>
								              <div id='sclMsgAvailableSclGrps' class="hidden" style='width:100%;border-radius:5px;' >
													<div id="sclMsgavailableGrpsGrid" style="min-height:400px;" >
													</div>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
			  </table>
			</td>
		  </tr>
		</table>
		<table style="width:40%;margin-left:30%;margin-top:1%;table-layout:fixed;" >
			<tbody>
				<tr>
					<td><button onClick="newTxtMsgManagerInstance.validateAndSubmitTxtMsgData()" class="k-button" style="background-color: #d4eff9; border: 1px solid #78bad2; width: 200px;"><span class="k-icon k-i-tick" ></span>Save</button></td>
					<td><button onClick="newTxtMsgManagerInstance.closeTextMsgWindow()" class="k-button" style="background-color: #d4eff9; border: 1px solid #78bad2; width: 200px;"><span class="k-icon k-i-cancel" ></span>lose</button></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>











<%@include file="../common/pageCloser.jsp"%>
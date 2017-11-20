<%@include file="../common/HeaderFile.jsp"%>

<script src="<c:url value='/static/resources/scripts/model/hunterAdminClientHuntler.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/model/taskProcessProgress.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/plain/createNewTextMessage.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/plain/createNewSocialMessage.js'/>"></script>



<script src="<c:url value='/static/resources/scripts/plain/processSocialMsg.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/plain/hunterFacebookProcessor.js'/>"></script>

<%-- <script src="<c:url value='/static/resources/scripts/model/regionVM.js'/>"></script> --%>

<div id="hunterUserGrid" style="width:98%;margin-left:1%;border : 1px solid : #E2EFF2;border-radius:4px;margin-top:0%;margin-bottom:0.5%;" >

</div>

<div id="expandGridButtonDiv" class="hidden" style="margin-bottom:2px;margin-left:1%;width:20%;" class="k-header"  >
	<table style="width:65%;">
		<tr>
			<td><span id="selectedUserNameSpan" style="background-color:#E1F1FD;border:1px solid #BCD6EB;padding:7px;border-radius:4px;" >Kip Langat</span></td>
			<td><button onClick="hunterAdminClientUserVM.expandHunterUserGrid()" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" >Show Grid</button></td>
		</tr>
	</table>
</div>

	<div id="hunterUserDetailsStrip" style="width:98%;margin-left:1%;margin-top:0%;">
		<ul style="margin-left:40%">
			<li class="k-state-active"  >Task Management</li>
		</ul>
		<div style="border:1px solid white;" id="taskGridHolder" >
      		
      		<div id="selectedUserClientTasks" style="width:100%;height:100%; border : 1px solid #9CD0E6; border-radius:4px;background=color:#FAFEFF;" >
      			<h3 id="noDataH3ForHunterClientDetails"  style="color:#620B01;padding-left:2%;">No tasks loaded.</h3>
      		</div>
      		
      		
         </div>
         
	</div>
         
         
           <div id="taskMessageStrip" style="width:98%;margin-left:1%;height:400px; border : 1px solid #9CD0E6; border-radius:4px;background:color:#FAFEFF;display:none;padding-bottom:10px;padding-top:10px;">
           			<table style="width:20%;margin-left:1%;" >
           				<tr>
           					<td>
           						<button onClick='hunterAdminClientUserVM.showPopupForDeleteCurrentTskMsg()' style="background-color:rgb(239,255,231);color:red;border : 1px solid rgb(233,124,124);" class="k-button" >Delete Message</button>
           					</td>
           				</tr>
           			</table>
           		<div id="selTaskMessageEditMode" class="k-block k-info-colored" style="width:98%; height:80%;margin-left:1%;border-radius:5px;border:1px solid #CFECEE;padding-top:10px;" >
           			<table style="float:left;width:40%;color:#0A2C2E;font-size:13px;padding-left:10px;" >
           				<tr>
           					<td style="with:40%;" >Send Date</td>
           					<td style="width:60%;">
           						<input 	data-role="datepicker"
           							   	id="selTaskMsgSendDatePicker"
           								name ="selTaskMsgSendDatePicker"
           								data-parse-formats="['yyyy-MM-dd HH:mm:ss']"
			                   	   		data-bind="visible: isEverVisible,
			                              enabled: isEverVisible,
			                              value: taskMsgSendDate,
			                              events: { change: onChangeTaskMsgSendDate }" 
			                              style="width: 180px"/>
           					</td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Message Task Type</td>
           					<td style="width:60%;">
           						<input 
           							type="text" 
           							id="msgMsgType" 
									name="msgMsgType" 
									data-value-primitive="true"
					                data-bind="value:taskMsgTypeVal,enabled: isNeverEnabled,"
					                data-value-field="msgTypVal"
					                data-text-field="msgTypText"
					                data-source="HunterConstants.TASK_MSG_TYP_ARRAY"
					                data-role="dropdownlist" 
					                style="width: 180px"/>
           					</td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Desired Receivers</td>
           					<td style="width:60%;">
           						 <input 
           						 		name="msgDesiredReceivers"
           						 		name="msgDesireddReceivers" 
           						 		data-role="numerictextbox"
					                    data-min="0"
					                    data-max="1000000"
					                    data-bind="visible: isEverVisible,
					                              enabled: isEverEnabled,
					                              value: desiredReceivers,
					                              events: { change: onChange }"
					                   style="width: 180px">
           					</td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Actual Receivers</td>
           					<td style="width:60%;">
           						 <input 
           						 		name="msgActualReceivers"
           						 		name="msgActualdReceivers" 
           						 		data-role="numerictextbox"
					                    data-min="0"
					                    data-max="1000000"
					                    data-bind="visible: isEverVisible,
					                              enabled: isEverEnabled,
					                              value: actualReceivers,
					                              events: { change: onChange }"
					                   style="width: 180px">
           					</td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Confirmed Receivers</td>
           					<td style="width:60%;">
           						 <input 
           						 		name="msgConfirmedReceivers"
           						 		name="msgConfirmedReceivers" 
           						 		data-role="numerictextbox"
					                    data-min="0"
					                    data-max="1000000"
					                    data-bind="visible: isEverVisible,
					                              enabled: isEverEnabled,
					                              value: confirmedReceivers,
					                              events: { change: onChange }"
					                   style="width: 180px">
           					</td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Service Provider</td>
           					<td style="width:60%;">
           						<input 
           							type="text" 
           							id="taskMessageProvider" 
									name="taskMessageProvider" 
					                data-bind="value:tskMsgProvider"
					                data-value-primitive="true"
					                data-value-field="providerId"
					                data-text-field="providerName"
					                data-source="HunterConstants.SERVICE_PROVIDER_DATA"
					                data-role="dropdownlist" 
					                style="width: 180px"/>
           					</td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Message Owner</td>
           					<td style="width:60%;">
           					<span class="k-input k-textbox" data-bind="text : tskMsgOwner" style="width: 180px;padding-left:5px;height:30px;" ></span></td>
           				</tr>
           			</table>
           			
           			<table style="float:left;margin-left:10px;color:#0A2C2E;font-size:13px;padding-left:10px;"  >
           				<tr>
           					<td style="with:40%;" >Message Status</td>
							<td>
					            <input 
           							type="text" 
           							id="tskMsgStatusDropdownList" 
									name="tskMsgStatusDropdownList" 
					                data-bind="value:tskMsgLifeStatus"
					                data-value-primitive="true"
					                data-value-field="statusValue"
					                data-text-field="statusText"
					                data-source="HunterConstants.HUNTER_LIFE_STATUSES"
					                data-role="dropdownlist" 
					                style="width: 180px"/>
							</td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Created On</td>
           					<td style="width:60%;"><span class="k-input k-textbox" data-bind="text : tskMsgCretDate"  style="width: 180px;padding-left:5px;height:30px;" ></span></td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Created By</td>
           					<td style="width:60%;"><span class="k-input k-textbox" data-bind="text : tskMsgCretBy" style="width: 180px;padding-left:5px;height:30px;" ></span></td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Modified On</td>
           					<td style="width:60%;"><span class="k-input k-textbox" data-bind="text : tskMsgLstUpdate" style="width: 180px;padding-left:5px;height:30px;" ></span></td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Modified By</td>
           					<td style="width:60%;"><span class="k-input k-textbox" data-bind="text : tskMsgLstUpdatedBy" style="width: 180px;padding-left:5px;height:30px;" ></span></td>
           				</tr>
           				<tr>
           					<td style="with:40%;" >Message Text</td>
           					<td style="width:60%"><textarea data-bind="value : tskMstTxt" style="background-color:#F2F9FF;border:1px solid #81A6AE;border-radius:4px;resize: none;" rows="5" cols="21" id="taskInfoTaskObjective" ></textarea></td>
           				</tr>
           			</table>
           		</div>
           			<table style="width:40%;margin-left:30%;margin-top:10px;margin-bottom:10px;" >
           				<tr>
           					<td style="width:100%;">
             				<button onClick='hunterAdminClientUserVM.saveCurrentEditMessageAndClose()' style="width:130px;margin-left:15%;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" >Save And Close</button>
             				<button onClick='hunterAdminClientUserVM.saveCurrentEditMessage()' style="width:100px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" >Save</button>
             				<button onClick='hunterAdminClientUserVM.canceltEditMessageAndClose()' style="width:100px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" >Cancel</button>
           					</td>
           				</tr>
           			</table>
      		</div>
             
             
             
             <div id="taskRegionStrip" style="width:98%;margin-left:1%;margin-top:2px;height:400px; background-color:#FAFEFF;display:none;" >
           	<table id="regionGroupsTable" style="with:40%;margin-left:0px;margin-bottom:2px;"  >
           		<tr>
           			<td><button onClick="hunterAdminClientUserVM.showTaskRegionsPart()" id="taskRegionsButton"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Receiver Regions</button></td>
			    	<td><button onClick="hunterAdminClientUserVM.showTaskGroupsPart()"  id="taskGroupsButton" class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Receiver Groups</button></td>
			    	<td><button onClick="hunterAdminClientUserVM.canceltEditRegionAndClose()"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Client Tasks</button></td>
           		</tr>
           	</table>
             <div style="with:100%;height:100%;" id="taskRegionsCoverDiv"  >
				<div id="taskRegionViewCover" style="width:22%;height:300px;float:left;border:1px solid #C7EAFF;padding:25px;border-radius:4px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;" >

					<table style="width:80%;">
						<tr>
							<td>Country :</td>
							<td>
								 <input id="countryDropDownList"
								 	   data-role="dropdownlist"
					                   data-auto-bind="true"
					                   data-value-primitive="true"
					                   data-text-field="countryName"
					                   data-value-field="countryId"
					                   placeholder = "Select Country"
					                   data-bind="value: selCountry,
					                              source: countryDS,
					                              visible: isCountryDropdownVisible,
					                              enabled: isCountryDropdownEnabled,
					                              events: {
					                                change: onChangeCountry
					                              }"
					            />
							</td>
						</tr>
						<tr>
							<td>County :</td>
							<td> 
							
								<input id="countyDropDownList"
								 	   data-role="dropdownlist"
					                   data-auto-bind="true"
					                   data-value-primitive="true"
					                   data-text-field="countyName"
					                   data-value-field="countyId"
					                   data-optionlable = "Select County"
					                   data-bind="value: selCounty,
					                              source: countyDS,
					                              visible: isCountyDropdownVisible,
					                              enabled: isCountyDropdownEnabled,
					                              events: {
					                                change: onChangeCounty
					                              }"
					            />
							</td>
						</tr>
						<tr>
							<td>Constituency :</td>
							<td>
								<input id="constituencyDropDownList"
								 	   data-role="dropdownlist"
					                   data-auto-bind="true"
					                   data-value-primitive="true"
					                   data-text-field="constituencyName"
					                   data-value-field="constituencyId"
					                   data-optionlable = "Select Consituency"
					                   data-bind="value: selConstituency,
					                              source: constituencyDS,
					                              visible: isConstituencyDropdownEnabled,
					                              enabled: isConstituencyDropdownVisible,
					                              events: {
					                                change: onChangeConstituency
					                              }"
					            />
							</td>
							
							
						</tr>
						<tr>
							<td>ConstituencyWard :</td>
							<td>
								<input id="constituencyWardDropDownList"
								 	   data-role="dropdownlist"
					                   data-auto-bind="true"
					                   data-value-primitive="true"
					                   data-text-field="constituencyWardName"
					                   data-value-field="constituencyWardId"
					                   data-optionLabel = "Select Ward"
					                   data-bind="value: selConstituencyWard,
					                              source: constituencyWardDS,
					                              visible: isConstituencyWardDropdownEnabled,
					                              enabled: isConstituencyWardDropdownVisible,
					                              events: {
					                                change: onChangeConsituencyWard,
					                                dataBound:onDataConsWardBound
					                              }"
					            />
							</td>
						
						
						
						
						</tr>
					</table> 
					<span style="margin:10px;" ></span>
					<table style="margin:0 auto;">
						<tr>
							<!-- <td><button onClick="hunterAdminClientUserVM.saveCurrentEditRegionAndClose()"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Save And Close</button></td> -->
							<td><button onClick="hunterAdminClientUserVM.saveCurrentEditRegion()"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Add Region</button></td>
						</tr>
					</table>
					
					<table id="taskRegionGroupCounts2" style="with:100%;background-color:#DAFDFF;border-radius:3px;border : 1px solid #A7DCDF;padding:5px;height:30px;margin-top:35px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;" >
						<tr>
							<td>Receivers From Regions : </td>
							<td><span id="taskRegionReceiversForGroupsSpan2" data-bind="text: taskUniqueReceivers" style="margin-right:43%;color:red;" ></span></td>
						</tr>
						<tr>
							<td>Receivers From Groups : </td>
							<td><span id="taskGroupsReceiversForGroupsSpan2" data-bind="text: taskGroupsTotalReceivers" style="margin-right:43%;color:red;" ></span></td>
						</tr><tr>
							<td style="text-align: right;" >Total Receivers: </td>
							<td style="font-weight: bolder;" ><span id="taskTotalReceiversSpan" data-bind="text: taskAllCombinedReceivers" style="margin-right:43%;color:red;" ></span></td>
						</tr>
					</table>
					
					</div>
				<div id="taskRegionViewCover2" style="width:70%;height:300px;float:right;border:1px solid #C7EAFF;padding:25px;border-radius:4px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;" >
				<div 
					style="height:300px;"
					data-role="grid"
					id="selTaskRegionsGrid"
             				data-scrollable="true"
             				data-height="280"
              				data-editable="false"
              				data-pageable="{
              						refresh:true
              					}"
              				data-columns="[
              						{ 'field': 'regionId', title : 'Region ID','width':'90px'  },
                              		{ 'field': 'country', title : 'Country'  },
                              		{ 'field': 'county', title : 'County' },
                              		{ 'field': 'constituency', title : 'Constituency'  },
                              		{ 'field': 'ward', title : 'Ward'  },
                              		{ 'field': 'receiverCount', title : 'Receivers','width':'90px'},
                              		{'field' : 'Delete', 'title':'Contacts','width':'75px','template' : '#=getRegionViewContactTemplate()#'},
                              		{'field' : 'Delete', 'title':'Delete','width':'75px','template' : '#=getRegionsDeleteTemplate()#'}
                           		]"
              				data-bind="source: selTaskRegionsDS,
                         			visible: isEverVisible,
                         			events: { save: onSaveSelTaskRegion}"
                         			
                         			
				>
				</div>
				<div 
					class="totals" 
					style=" margin-top:2px; font-size:15px;color:#004ADF; 
					font-weight:bold; width:30%;
					float:right; text-align: right;
					padding:4px; border-radius:4px;" >	
					<span>Total Receivers: </span><span id="taskUniqueReceiversSpan" data-bind="text: taskUniqueReceivers" style="margin-right:43%;color:red;" ></span>
			 	</div>
			</div>
		</div>
		<div  id="taskGroupsCoverDiv"  style="with:100%;height:100%;display:none;"  >
				<div style="width:12%;height:300px;float:left;border:1px solid #C7EAFF;padding:25px;border-radius:4px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;" >
					<table style="with:100%;;position:relative;" >
						<tr  style="with:100%;" >
							<td  style="with:100%;">
								<input id="taskGroupsDropdownList"
								 	   data-role="dropdownlist"
					                   data-auto-bind="true"
					                   data-value-primitive="true"
					                   data-text-field="text"
					                   data-value-field="groupId"
					                   data-optionLabel = "Select Group"
					                   style="with:100%;"
					                   data-bind="value: taskGroupDropDownSelVal,
					                              source: taskGroupDropdownData,
					                              visible: isEverVisible,
					                              enabled: isEverEnabled
					                              "
					            />
							</td>
						</tr>
						<tr>
							<td><center><button onClick="hunterAdminClientUserVM.addGroupToTask()"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Add Group</button></center></td>
						</tr>
					</table>
					<table id="taskRegionGroupCounts" style="with:100%;background-color:#DAFDFF;border-radius:3px;border : 1px solid #A7DCDF;padding:5px;height:30px;margin-top:35px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;" >
						<tr>
							<td>Receivers From Regions : </td>
							<td><span id="taskRegionReceiversForGroupsSpan" data-bind="text: taskUniqueReceivers" style="margin-right:43%;color:red;" ></span></td>
						</tr>
						<tr>
							<td>Receivers From Groups : </td>
							<td><span id="taskGroupsReceiversForGroupsSpan" data-bind="text: taskGroupsTotalReceivers" style="margin-right:43%;color:red;" ></span></td>
						</tr><tr>
							<td style="text-align: right;" >Total Receivers: </td>
							<td style="font-weight: bolder;" ><span id="taskTotalReceiversSpan" data-bind="text: taskAllCombinedReceivers" style="margin-right:43%;color:red;" ></span></td>
						</tr>
					</table>
				</div>
				<div  style="width:80%;height:300px;float:right;border:1px solid #C7EAFF;padding:25px;border-radius:4px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;"  >
					<div 
						id="receiverGroupGrid"
						data-role="grid"
						data-height="280"
						data-refreshable="true"
						data-sortable="true"
						data-filterable="true"
						data-scrollable="true"
						data-editable='false'
						data-pageable="{
								refresh:true,
								pageSize:100
							}"
						data-columns="[
							{ 'field': 'groupId', title : 'ID', 'width':'65px'},
							{ 'field': 'groupName', title : 'Group Name'  },
							{ 'field': 'groupDesc', title : 'Group Description'  },
							{ 'field': 'receiverType', title : 'Receiver Type'  },
				       		{ 'field': 'receiverCount', title : 'Receiver Count'  },
				       		{ 'field': 'firstName', title : 'Owner First Name'  },
				       		{ 'field': 'lastName', title : 'Owner Last Name'  },
				       		{'field' : '', 'title':'Contacts', 'width' : '95px','template' : '#=getViewTaskGroupContactsTemplate()#'},
				       		{'field' : 'delete', 'title':'Delete', 'width' : '95px','template' : '#=getTaskGroupDeleteTemplate()#'}
				   		]"
						data-bind="source: receiverGroupDS, visible: isEverVisible"
					>
			 	</div>
			 		<div 
					class="totals" 
					style=" margin-top:2px; font-size:15px;color:#004ADF; 
					font-weight:bold; width:30%;
					float:right; text-align: right;
					padding:4px; border-radius:4px;" >	
					<span>Total Receivers: </span><span id="taskUniqueReceiversSpan" data-bind="text: taskGroupsTotalReceivers" style="margin-right:43%;color:red;" ></span>
				</div>
			</div>
		</div>
    </div>
    
    
    <div id='emailEditTemplateCover'  style="width:98%;margin-left:1%;display:none;" >
		<table style="width:100%;table-layout:fixed;border:1px solid #B8D5EB;border-radius:5px;">
			<tr style="height:100%">
				<td style="width:50%;height:400px">
					<div style="width:100%;height:400px;border-radius:5px">
						<textarea style="height:100%;width:100%;position:relative" 
							data-role='editor' 
							data-bind="value : taskEmailHtml, visible : isEverVisible, events : { change : onChangeTaskEmailHtml }"
							data-tools=
								"[
								    'bold',
									'italic',
									'underline',
									'strikethrough',
									'superscript',
									'subscript',
									'justifyCenter',
									'justifyLeft',
									'justifyRight',
									'justifyFull',
									'insertUnorderedList',
									'insertOrderedList',
									'indent',
									'outdent',
									'createLink',
									'unlink',
									'insertImage',
									'insertFile',
									'insertHtml',
									'fontName',
									'fontNameInherit',
									'fontSize',
									'fontSizeInherit',
									'formatBlock',
									'formatting',
									'style',
									'viewHtml',
									'emptyFolder',
									'uploadFile',
									'orderBy',
									'orderBySize',
									'orderByName',
									'invalidFileType',
									'deleteFile',
									'overwriteFile',
									'directoryNotFound',
									'imageWebAddress',
									'imageAltText',
									'fileWebAddress',
									'fileTitle',
									'linkWebAddress',
									'linkText',
									'linkToolTip',
									'linkOpenInNewWindow',
									'dialogInsert',
									'dialogUpdate',
									'dialogCancel',
									'dialogCancel',
									'createTable',
									'addColumnLeft',
									'addColumnRight',
									'addRowAbove',
									'addRowBelow',
									'deleteRow',
									'deleteColumn'
                                   
                                   ]"
						></textarea>
					</div>
				</td>
				<td style="width:50%;height:400px">
					<div id="emailTemplateProcessed" data-bind="text : templatedHtml"  style="display:none;overflow-y : scroll;border-radius:5px;background-color:#E6FCFF;height:390px;padding:5px;">
						
					</div>
					<div id="emailTemplateParamsEditor"  style="overflow-y : scroll;border-radius:5px;background-color:#E6FCFF;height:390px;padding:5px;">
						<h2 style='align:center;' >Email Message Configurations</h2>
				        <table style='width:100%;table-layout:fixed;' >
				        	<tr style='with:100%;' >
				        		<td style='with:36%;'>
				        			<table style='min-with:50px;table-layout:fixed;' class='innerEmailConfigTables' >
				        				<tr>
				        					<td>Has Attachment</td>
				        					<td>
				        						<input type="checkbox" id="hasAttachment" class="k-checkbox" data-bind="checked: hasAttachment">
				          						<label class="k-checkbox-label" for="hasAttachment"></label>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>
				        						Selected Template
				        					</td>
				        					<td>
				        						<span data-bind='text:emailTemplateName' style='color:blue;' ></span>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>Email Subject</td>
				        					<td>
				        						<input 
				           							type="textbox" 
				           							class="k-textbox"
				           							id="taskEmailSubject" 
													name="taskEmailSubject"
									                data-bind="value:emailSubject"
									                style="width: 180px" />
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>Email Status</td>
				        					<td>
				        						<input 
				           							type="text" 
				           							id="tskEmailMsgStatus" 
													name="tskEmailMsgStatusDropdownList" 
									                data-bind="value:tskEmailMsgStatus"
									                data-value-primitive="true"
									                data-value-field="statusValue"
									                data-text-field="statusText"
									                data-value-primitive="true"
									                data-source="HunterConstants.HUNTER_LIFE_STATUSES"
									                data-role="dropdownlist" 
									                style="width: 180px"/>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>
				        						Email Send Date
				        					</td>
				        					<td>
				        						<input 	data-role="datetimepicker"
			           							   	id="selTaskEmailMsgSendDatePicker"
			           								name ="selTaskEmailMsgSendDatePicker"
			           								data-format='yyyy-MM-dd HH:mm:ss'
						                   	   		data-bind="visible: isEverVisible,
						                              enabled: isEverVisible,
						                              value: taskEmailMsgSendDate,
						                              events: { change: onChangeTaskEmailMsgSendDate }" 
						                              style="width: 180px"/>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>
				        						Created On
				        					</td>
				        					<td>
				        						<span data-bind='text:emailMsgCretDate' ></span>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>
				        						Created By
				        					</td>
				        					<td>
				        						<span data-bind='text:emailMsgCretBy' ></span>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>
				        						Updated On
				        					</td>
				        					<td>
				        						<span data-bind='text:emailMsgLstUpdate' ></span>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>
				        						Updated By
				        					</td>
				        					<td>
				        						<span data-bind='text:emailMsgLstUpdatedBy' ></span>
				        					</td>
				        				</tr>
				        				<tr>
				        					<td>
				        						Word Count
				        					</td>
				        					<td>
				        						<span data-bind='text:getWordCount' id='wordCountSpan' style='font-size:13px;font-weight:bolder' ></span>
				        						<span id='styledTickOK' class='k-icon k-i-tick' style='display:none;'  ></span>
				        						<span id='styledTickCancel' class='k-icon k-i-close' style='display:none;' ></span>
				        					</td>
				        				</tr>
				        			</table>
				        			<br/>
				        			<button class='k-button' style="background-color:rgb(212,239,249);width:80%;border : 1px solid rgb(120,186,210);" data-bind='events : {click:saveEmailMessageConfigurations}'  ><span class="k-icon k-i-tick"></span>Save Configurations</button>
				        		</td>
				        		<td style='width:64%;' id='hunterMessageAttachmentsTdContainer' >
				        			<div style='width:100%;' >
				        				<table id='hunterMessageAttachmentTable' style='table-layout: fixed;width:100%;'>
				        					<tr>
				        						<td style='margin-bottom:10px;height:30px;border-radius:3px;font-size: 20px;font-weight: bolder;width:100%;text-align: center;'>
				        							Message Attachments<br/>
				        						</td>
				        					</tr>
				        					<tr>
				        						<td>
					        						<div style='height:280px;overflow-y:scroll;width:100%;' id="messageAttachmentsContainer">
					        								<img src="http://localhost:8080/Hunter/static/resources/images/refreshing_spinner_new.gif" style='margin-left:47%;margin-top:10%;' width="50px" height="50px"   />
					        						</div>
				        						</td>
				        					</tr>
				        				</table>
				        			</div>
				        		</td>
				        	</tr>
				        </table>
					</div>
				</td>
			</tr>
		</table>
		<div style="border:1px solid #ccd9e4;border-radius:5px;vertical-align:middle;padding:15px;margin-top:2px;" class='k-header' >
			<button class='k-button' data-bind="events: { click : viewCurrentEditorContent }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"  ><span class="k-icon k-i-search"></span>View</button>
			<button class='k-button' data-bind="events: { click : saveCurrentEditorContent }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"  ><span class="k-icon k-i-tick"></span> Save</button>
			<button class='k-button' data-bind="events: { click : clearCurrentEditorContent }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-cancel"></span>Clear</button>
			<button class='k-button' data-bind="events: { click : displayAllCurrentTemplates }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-funnel "></span>Templates</button>
			<button class='k-button' data-bind="events: { click : launchTaskEmailPreview }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-funnel "></span>Preview</button>
			<button class='k-button' data-bind="events: { click : showEmailParameters }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-restore "></span><span data-bind="text : currentParamButtonText" ></span></button>
			<button class='k-button' data-bind="events: { click : showPopupToDeleteEmail }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-funnel "></span>Delete Email</button>
			<button class='k-button' data-bind="events: { click : showMessageAttachmentMappings }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"  ><span class="k-icon k-i-search"></span>Attachments</button>
			<button class='k-button' data-bind="events: { click : refreshElemeAttributes }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-refresh"></span>Refresh</button>
			<button class='k-button' data-bind="events: { click : closeEmailTemplateSection }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-arrow-w"></span>Close</button>
		</div>
	</div>
	




<style type="text/css">
		.k-grid .k-grid-content {
		 min-height: 100px;
		 max-height: 280px;
		}
	
		.fieldlist {
            margin: 0 0 -1em;
            padding: 0;
        }

        .fieldlist li {
            list-style: none;
            padding-bottom: 1em;
        }
        
        .innerEmailConfigTables td{
        	with: 50%;
        }
        
        .innerEmailConfigTables tr{
        	with: 100%;
        }
        
	 .sunny, .cloudy, .rainy {
                    display: block;
                    margin: 30px auto 10px;
                    width: 128px;
                    height: 400px;
                }

                .cloudy{
                    background-position: -128px 0;
                }

                .rainy{
                    background-position: -256px 0;
                }
                
                #taskGroupsCoverDiv table {
                	with:100%;
                }

                .weather {
                    margin: 0 auto 30px;
                    text-align: center;
                }

                #tabstrip h2 {
                    font-weight: lighter;
                    font-size: 5em;
                    line-height: 1;
                    padding: 0 0 0 30px;
                    margin: 0;
                }

                #tabstrip h2 span {
                    background: none;
                    padding-left: 5px;
                    font-size: .3em;
                    vertical-align: top;
                }

                #tabstrip p {
                    margin: 0;
                    padding: 0;
                }
                
                #hunterUserClientDetailsTable td{
                	border-bottom:1px solid #E8F8FA;
                }
                
				#hunterUserClientTaskDetailsTable td { 
					border-bottom:1px solid #E8F8FA; 
				}
				#hunterUserClientTaskRegionTable td { 
					border-bottom:1px solid #E8F8FA; 
				}
				#hunterUserClientTaskMessageDetailsTable td { 
					border-bottom:1px solid #E8F8FA; 
				}
                #hunterClientTasksPopupViewTemplateTable tr td:first-child {
                	font-size: 1.05em;
                	width:20%;
                	color:#01081C;
                	padding-right:5px;
                	text-align: right;
                	padding-right:2px;
                	text-shadow: 2px 2px #BFD2E5;
                }
                
                #hunterClientTasksPopupViewTemplateTable tr td:nth-child(2) {
                	width:50%;
                	max-width:100px;
                	text-shadow: 1px 1px #BFDBFF;
                	color:#0A2FA9;
                	font-family: Courier New, Courier, Sans Serif;
                }
                
                #hunterClientTasksPopupViewTemplateTable tr td{
                	height:20px;
                }

		.tasProcessJobResultDiv{
		     float:left;
		     margin-left:2%;
		     margin-top:2%;
		     width:47%;
		     background-color : #E4F4F9;
		     height:340px;
		     border-radius:4px;
		     border : 1px solid #AFCAD2;
		  }
		 .prcssJbDtlsTblLbl tr td:first-child{
		    color:#0C2941;
		    font-size:16px;
		    width:40%;
		    text-align:left;
		  }
		.prcssJbDtlsTblLbl tr td:nth-child(2){
		    width:60%;
		    padding-left:3px;
		    text-align:left;
		    font-size:14px;
		    color:#000B18;
		    font-family : courier new, courier, sans serif, new times roman;
		  }
		.prcssJbDtlsTblLbl td{
		  padding:4px;
		  border-bottom:1px solid #D3E4E9;
		}



</style>



















	<!--  :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::      Below Here are all templates         :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: -->













<script type="text/x-kendo-template" id="hunterMessageAttachmentTemplate">
<div style='width:98%;background-color: \\#D0F6F6;border-radius:5px;border:1px solid \\#B9EBEB;margin-bottom:10px;' >
	<table style='table-layout: fixed;width:100%;' >
		<tr>
			<td style='font-size:15px;font-weight: bolder;padding:5px;'>
				<button class='k-button' style="background-color:rgb(212,239,249);width:35px;border : 1px solid rgb(120,186,210);" onClick="hunterAdminClientUserVM.showPopupToEditMessageAttachments(this)"  ><span class="k-icon k-i-pencil"></span></button>
				#=attachmentName#							        									
			</td>
		</tr>
		<tr>
			<td style='font-size:14px;padding:5px;'><span style='color:red;' >#=attachmentLocation#</span></td>
		</tr>
	</table>
</div>
</script>


<script type="text/x-kendo-template" id="hunterAttachmentPopupTemplate">
	<table style='table-layout: fixed;width:100%;' >
		<tr>
			<td>
				<div style='width:100%;min-height: 220px;height: 220px;overflow-y:scroll;' id="messageAttachmentsMappingsDiv">
					<img src='http://localhost:8080/Hunter/static/resources/images/refreshing_spinner_new.gif' style='margin-left:47%;margin-top:13%;' width='50px' height='50px'   />
					<table class='hidden' id="messageAttachmentsMappingsTable" style='table-layout:fixed;width:90%;margin-left:5%;margin-top:4%;' >
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<button class='messageAttachmentsRecord' onClick="hunterAdminClientUserVM.closeMessageAttchmntPopup()" style="border-radius:3px;margin-left:20%;height:30px;width:60%;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-cancel"></span>Close</button>
			</td>
		</tr>
	</table>
</script>

<script type="text/x-kendo-template" id="hunterUserToolBar">
<div class="toolbar" >
	<button data-command="create" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button k-button-icontext k-grid-add"><span class="k-icon k-add"></span>New</button>
</div>
</script>

<script type="text/x-kendo-template" id="hunterClientTasksToolBar">
<div class="toolbar" >
	<button data-value-name="newTask" onClick="hunterAdminClientUserVM.launchTaskEditView('newTask')"  style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button"><span class="k-icon k-add"></span>Create New Task</button>
</div>
</script>

<script type="text/x-kendo-template" id="taskHistoryToolBarTemplate">
<div class="toolbar" >
	<button onClick="kendoKipHelperInstance.closeWindowWithOnClose()" class="k-button" style="float:right;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-i-close"></span>Close</button>
</div>
</script>

<script type="text/javascript">
	$("document").ready(function(){
		hunterAdminClientUserVM.init();
	});
</script>


<div id="hunterUserEditTemplate" style="display:none;">
	<div class="k-edit-form-container">
		<div class="k-edit-label">
			<label for="firstName">First Name</label>
		</div>
		<div data-container-for="firstName" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="firstName"
				required="required" data-bind="value:firstName">
		</div>
		<div class="k-edit-label">
			<label for="lastName">Last Name</label>
		</div>
		<div data-container-for="lastName" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="lastName"
				required="required" data-bind="value:lastName">
		</div>
		<div class="k-edit-label">
			<label for="middleName">Middle Name</label>
		</div>
		<div data-container-for="middleName" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="middleName"
				required="required" data-bind="value:middleName">
		</div>
		<div class="k-edit-label">
			<label for="email">Email</label>
		</div>
		<div data-container-for="email" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="email"
				required="required" data-bind="value:email">
		</div>
		<div class="k-edit-label">
			<label for="phoneNumber">Phone Number</label>
		</div>
		<div data-container-for="phoneNumber" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="phoneNumber"
				required="required" data-bind="value:phoneNumber">
		</div>
		<div class="k-edit-label">
			<label for="userType">User Type</label>
		</div>
		<div data-container-for="userType" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="userType"
				required="required" data-bind="value:userType">
		</div>
		<div class="k-edit-label">
			<label for="userName">User Name</label>
		</div>
		<div data-container-for="userName" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="userName"
				required="required" data-bind="value:userName">
		</div>
		<div class="k-edit-label">
			<label for="userName">Password</label>
		</div>
		<div data-container-for="password" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="password"
				required="required" data-bind="value:password">
		</div>
	</div>
</div>


<div id="hunterClientTasksPopupViewTemplate" style="display:none;">
	<div class="k-header" style="border:1px solid rgb(187,207,230); border-radius:4px;padding:10px;height:96%;width:96%;align:center;"> 
        <table id="hunterClientTasksPopupViewTemplateTable" style="width:100%;height:90%;border : 1px solid rgb(218,239,242); border-radius:4px;color:\\#1D7BD8;table-layout:fixed">
        	<tr>
        		<td>Task ID : </td>
        		<td>#= taskId #</td>
        	</tr>
        	<tr>
        		<td>Task Type:</td>
        		<td>#= taskType #</td>
        	</tr>
        	<tr>
        		<td>Task Name:</td>
        		<td>#= taskName #</td>
        	</tr>
        	<tr>
        		<td>Task Objective:</td>
        		<td style="word-wrap: break-word;" >
        			<div style="width: 400px;overflow: hidden;">
				    	#= taskObjective #
				  	</div>
        		</td>
        	</tr>
        	<tr>
        		<td>Task Description:</td>
        		<td  style="word-wrap: break-word;"  >#= description #</td>
        	</tr>
        	<tr>
        		<td>Task Budget:</td>
        		<td>#= taskBudget #</td>
        	</tr>
        	<tr>
        		<td>Task Cost:</td>
        		<td>#= taskCost #</td>
        	</tr>
        	<tr>
        		<td>Task Recurrent :</td>
        		<td>#= recurrentTask #</td>
        	</tr>
        	<tr>
        		<td>Task Date Line:</td>
        		<td>#= taskDateline #</td>
        	</tr>
        	<tr>
        		<td>Task Life Status:</td>
        		<td>#= taskLifeStatus #</td>
        	</tr>
        	<tr>
        		<td>Task Delivery Status:</td>
        		<td>#= taskDeliveryStatus #</td>
        	</tr>
        	<tr>
        		<td >Task Approved:</td>
        		<td>#= taskApproved #</td>
        	</tr>
        	<tr>
        		<td >Task Approver:</td>
        		<td>#= taskApprover #</td>
        	</tr>
        	<tr>
        		<td>Created On:</td>
        		<td>#= cretDate #</td>
        	</tr>
        	<tr>
        		<td>Created By:</td>
        		<td>#= createdBy #</td>
        	</tr>
        	<tr>
        		<td >Modified On:</td>
        		<td>#= lastUpdate #</td>
        	</tr>
        	<tr>
        		<td >Modified By:</td>
        		<td>#= lastUpdatedBy #</td>
        	</tr>
        	<tr>
        		<td >Task Message:</td>
        		<td style="word-wrap: break-word;" >
        			#if(data.taskMessage != null){#
        			   #= taskMessage.text #
        			#}else{#
        				No Message!
        			#}#
        			
        		</td>
        	</tr>
        	<tr>
        		<td >Task Region:</td>
        		<td>
        			#if(data.taskRegion != null){#
        			   #= taskRegion.country #
        			#}else{#
        				No Region!
        			#}#
        		</td>
        	</tr>
        </table>
        <div style="text-align: center;margin-top:20px;"><button class="k-button" onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style='background-color:rgb(212,239,249);width:15%;'>OK</button></div>
        </div>
    </div>
    
    <div id="clientDetailsTemplateDiv"  style="display:none;"> 
    	<div id="clientDetailsContainer"  style="border:1px solid rgb(187,207,230); border-radius:4px;padding:10px;" class="k-header">
    			<table style="width:100%;">
    				<tr>
    					<td>Client ID: </td>
    					<td><span id="hunterClientCreatedOnTd" >#=clientId#</span></td>
    				</tr>
    				<tr>
    					<td>Total Budget: </td>
    					<td style="height:20px;" ><span>Ksh </span><span style="height:20px;" >#=clientTotalBudget#</span></td>
    				</tr>
    				<tr>
    					<td>Is Receiver</td>
    					<td>#=receiver#</td>
    				</tr>
    				<tr>
    					<td>Created On</td>
    					<td><span id="hunterClientCreatedOnTd" >#=cretDate#</span></td>
    				</tr>
    				<tr>
    					<td>Created By</td>
    					<td id="createdBy" >#=createdBy#</td>
    				</tr>
    				<tr>
    					<td>Last Update</td>
    					<td id="updatedOn" >#=lastUpdate#</td>
    				</tr>
    				<tr>
    					<td>Updated By</td>
    					<td id="updatedBy" >#=lastUpdatedBy#</td>
    				</tr>
    			</table>
    			<table style="width:30%;margin-left:35%;">
    				<tr>
    					<td><button onClick="hunterAdminClientUserVM.saveDraftClientChanges()"  class="k-button" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" >Update</button></td>
    					<td><button onClick="kendoKipHelperInstance.closeMeasuredWindowBackUp()"  class="k-button" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" >Cancel</button></td>
    				</tr>
    			</table>
    	</div>
    </div>

<script type="text/x-kendo-template" id="processTaskProgressPopupTemplate">
<div id="processTaskProgressPopup" style='min-width:500px;min-height:200px;background-color:#F9FFFF;border:1px solid #B6D2E2;border-radius:4px;display:none;' >
	<div id="taskProcessValidationContainer">
 		<table style="margin:4px;table-layout:fixed;padding:3px;margin-left:10px;" >
			<tr>
				<td id="taskProcessValidationTd" >Validating : </td>
				<td><div id="validationProgressBar"></div></td>
			</tr>
			<tr>	
				<td id="validationResultsLabel" ></td>
				<td id="validationResultsMessages" style="max-width:50px;" ><div id="taskValidationResults"></div></td>
			</tr>
			<tr>
				<td  id="taskProcessProcessTd"  >Processing : </td>
				<td><div id="processTaskProgressBar"></div></td>
			</tr>
			<tr>	
				<td id="processTaskProgressResults" ></td>
				<td id="processTaskProgressResultsMsgs" style="max-width:50px" ><div id="processTaskProgressMessages"></div></td>
			</tr>
		</table>
	</div>
    <br/>
	<table id="closeTaskProcessingWindowTable" style="width: 0 auto;margin-left:40%;display:none;" >
		<tr>
			<td><button onClick="hunterAdminClientUserVM.closeProcessWindowAndRefresh()"  class="k-button" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);width:100px;" >Close</button></td>
		</tr>
	</table>
 <div id="taskProcessProgressSpinner" >
  </div>	
</div>
<div style="width:350px;min-height:150px;background-color:#F9FFFF;border:1px solid #B6D2E2;border-radius:4px;" id="taskProcessPrompPopup"  >
	<div style="width:80%;margin-left:10%;margin-top:20px;">
		<b style="color:red;" >Task processing is an irreversible process</b><br/>
		This action will process task (<span style="font-weight:bold;" id="taskProcessPopupTaskName"></span>)<br/>
		Are you sure?  
	</div>
	<br/>
	<table style="width:40%;margin-left:23%;" >
		<tr>
			<td><td><button onClick="hunterAdminClientUserVM.unhideProcessTaskSection()"  class="k-button" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);width:100px;" >Process</button></td></td>
			<td><td><button onClick="kendoKipHelperInstance.closeWindowWithOnClose()"  class="k-button" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);width:100px;" >Cancel</button></td></td>
		</tr>
	</table>
</div>
</script>

<script type="text/x-kendo-template" id="cloneTaskPopupContainer">
	<div style="width:100%;" >
		<p>
			<div class="cloneTaskHiddenFields" style="display:none;" >
				<table style="margin-left:5%;" id="cloneTaskHiddenFieldsTable" >
					<tr id='cloneTaskNameTr' >
				       	<td>Task Name</td>
					 	<td>
							<input type="textbox" placeholder="Enter Task Name..." class="k-textbox" id="cloneTaskPTaskName" style="width: 220px" />
				        </td>
				    </tr>
					<tr  id='cloneTaskDescTr' >
				       	<td>Task Description</td>
					 	<td>
							 <textarea type="textarea" class="k-textbox" placeholder="Enter Task Description..." id="cloneTaskPTaskDesc" style="width: 220px" ></textarea>
				        </td>
				    </tr>
					<tr>
						<td>New User</td>
						<td>
							<input type="text" class="k-textbox" id="cloneTaskNewUser" style="width: 220px" />
						</td>
					</tr>
				</table>
				<div id="cloneTaskProgressBar" style="display:none;"  >
					Processing Task...
				</div>
			</div>
		</p>
		<span class='cloneTaskWarningMessage' style="width:200px;" >A copy of <span id="cloneTaskNameLabel" style="font-weight:bold;font-size:14px;text-decoration: underline;" ></span> will be created.<br/>Are you sure?</span>
		<table style="width:30%;margin-left:25%;margin-bottom:10px;" >
			<tr>
				<td><button id="startTaskClongingButton"  onClick="hunterAdminClientUserVM.processCloning()"  class="k-button" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);width:100px;" ><span class='k-icon k-i-tick' ></span><span id='startTaskClongingLabel' >Yes</span></button></td>
				<td><button onClick="kendoKipHelperInstance.closeWindowWithOnClose()"  class="k-button" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);width:100px;" ><span class='k-icon k-i-cancel' ></span>Cancel</button></td>
			</tr>
		</table>
	</div>
</script>

<div id='allHunterEmailTemplates' style='display:none;' >
</div>




<script type="text/x-kendo-template" id="taskProcessJobDetailsTemplate">
<div style='width:1100px;max-height:850px;' >
<div id="taskProcessWorkerContainer"  style="width:96%;margin-left:2%;padding:4px;background-color:rgb(215,234,240);max-height:735px;min-height:380px;border-radius:4px;border:1px solid rgb(175,202,210);overflow-y:scroll;" >
  
</div>
</div>
<button class="k-button"  onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style="margin-bottom:7px;margin-top:10px;margin-left:47%;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-close" ></span>Close</button>
</script>




<script type="text/x-kendo-template" id="taskProcessJobWorkerTemplate">

#for(var i=0; i<data.length;i++){#
<div class="tasProcessJobResultDiv" >
   <table style="width:90%;margin-left:5%;margin-top:2%;border-radius:3px;" class="prcssJbDtlsTblLbl"  >
     <tr>
         <td style="color:red;font-size:17px;font-weight:bold;" >#=data[i].workerNo#</td>
		 <td></td>
      </tr> 
	 <tr>
         <td>Worker Name : </td>
         <td>#=data[i].workerName#</td>
      </tr>
      <tr style="border-bottom:1px solid red;">
         <td>Worker Status : </td>
         <td>
			 #if(data[i].workerStatus == 'Failed'){#
            	<span style="color:red;" >#:data[i].workerStatus#</span>
        	#}else{#
           	 	<span style="color:green;" >#:data[i].workerStatus#</span>
        	#}#
		 </td>
      </tr>
      <tr>
         <td>Response Code : </td>
         <td>#=data[i].responseCode#</td>
      </tr>
      <tr>
         <td>Response Text : </td>
         <td>#=data[i].responseText#</td>
      </tr>
      <tr>
         <td>Error Type : </td>
         <td>#=data[i].errorType#</td>
      </tr>
      <tr>
         <td>Error Text: </td>
         <td>#=data[i].errorText#</td>
      </tr>
      <tr>
         <td>Process Duration : </td>
         <td>#=data[i].duration# milliseconds</td>
      </tr>
      <tr>
         <td>Connection Status : </td>
		<td>
			 #if(data[i].cnnStatus == 'Failed'){#
            	<span style="color:red;" >#:data[i].cnnStatus#</span>
        	#}else{#
           	 	<span style="color:green;" >#:data[i].cnnStatus#</span>
        	#}#
		 </td>
      </tr>
      <tr>
         <td>Message Count : </td>
         <td>#=data[i].msgCount#</td>
      </tr>
    </table>
 </div>
 <div class="tasProcessJobResultDiv" >
 	<div style="word-wrap: break-word;width:88%;margin:5%;margin-bottom:3%;height:85%;overflow-y:scroll;font-size:13px;border-radius:3px;" >
		#=data[i].msgIds#
 	</div>
 </div>
#}#

</script>


<script type="text/x-kendo-template" id="taskProcessJobNoDataFoundTemplate">
	<div id="taskProcessJobLoadingIcon"  style="font-size:18px;color:black;text-align:center;background-color:#;margin-top:15%;"> Loading Process Results..... </div>
</script>


<script type="text/x-kendo-template" id="tskRgnCntSummry">
	<div id="tskRgnCntSummryContainer"  style="font-size:18px;width:530px;height:180px;border-radius:4px;border:1px solid rgb(175,202,210)">
		<h3 id="loadingCountText" style="width:98%;text-align:center;" >Loading Counts...</h3>
		<div id="tskRgnCntSummryIconLoader" ></div>
		<div id="tskRgnCntSummryDataResults" style="display:none;" >
			<table style="table-layout:fixed;width:92%;margin-left:4%;margin-top:15px;border-radius:5px;" >
				<tr>
					<td style="background-color:#254B5B;color:white;text-align:center;" >Receiver Type</td>
					<td style="background-color:#254B5B;color:white;text-align:center;">Type Number</td>
					<td style="background-color:#254B5B;color:white;text-align:center;">Receiver Counts</td>
				</tr>
				<tr>
					<td style="border-bottom:1px solid #CAE8F5;" >Task Groups</td>
					<td style="text-align:center;border-bottom:1px solid #CAE8F5;" id="tskRgnCntGroupNumber">2</td>
					<td style="text-align:center;border-bottom:1px solid #CAE8F5;" id="tskRgnCntGroupCount" >52123</td>
				</tr>
				<tr>
					<td style="border-bottom:1px solid #CAE8F5;">Task Regions</td>
					<td style="text-align:center;border-bottom:1px solid #CAE8F5;"  id="tskRgnCntRegionNumber" >5</td>
					<td style="text-align:center;border-bottom:1px solid #CAE8F5;"  id="tskRgnCntRegionCount" >3656</td>
				</tr>
				<tr>
					<td style="border-bottom:1px solid #CAE8F5;"></td>
					<td style="text-align:center;border-bottom:1px solid #CAE8F5;"  id="tskTotalRcvrCountName" >Total Receivers : </td>
					<td style="text-align:center;border-bottom:1px solid #CAE8F5;font-weight:bolder;"  id="tskTotalRcvrCount" >3656</td>
				</tr>
			</table>
			<table style="width:34%;margin-left:33%;" >
				<tr>
					<td><button id="taskRegionEditButton"  class="k-button"  onClick="hunterAdminClientUserVM.closePopupAndLoadEdiTaskRegionView()" style="margin-bottom:7px;margin-top:10px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-pencil" ></span>Edit</button></td>
					<td><button class="k-button"  onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style="margin-bottom:7px;margin-top:10px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-cancel" ></span>Close</button></td>
				</tr>
			</table>
	    </div>
    </div>
</script>


<script type="text/javascript">

var processJobData = 
	
	[{"processJobId":49,"genStatus":null,"genDuration":-1056,"startDate":"2016-03-29 22:32:44","workerJsons":[{"responseCode":"200","duration":"1056","processJobId":49,"taskId":139,"msgIds":"106750,106780,106823,106772,106843,106806,106830,106844,106751,106809,106766,106781,106792,106791,106810,106848,106752,106847,106799,106827,106777,106841,106821,106776,106832,106842,106785,106779,106814,106788,106775,106769,106794,106761,106838,106818,106764,106770,106839,106765,106786,106760,106813,106802,106808,106816,106763,106817,106796,106789,106807,106815,106783,106825,106835,106829,106834,106849,106773,106820,106756,106762,106778,106826,106836,106831,106840,106790,106824,106768,106801,106767,106804,106845,106822,106795,106798,106805,106754,106753,106757,106771,106759,106819,106846,106833,106782,106793,106774,106812,106758,106828,106784,106800,106787,106811,106837,106755,106803,106797","workerNo":0,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-8-8","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1056","processJobId":49,"taskId":139,"msgIds":"106413,106405,106411,106436,106444,106368,106415,106371,106419,106387,106374,106369,106430,106412,106376,106373,106364,106361,106440,106418,106433,106360,106423,106439,106431,106437,106401,106355,106384,106378,106404,106421,106441,106353,106375,106393,106443,106370,106388,106399,106372,106402,106424,106382,106446,106392,106434,106426,106449,106425,106432,106438,106428,106367,106389,106427,106414,106417,106435,106385,106383,106395,106350,106429,106381,106357,106397,106359,106352,106442,106400,106354,106366,106445,106420,106447,106351,106358,106403,106448,106407,106406,106380,106396,106356,106398,106379,106363,106365,106390,106409,106362,106394,106410,106386,106391,106408,106377,106416,106422","workerNo":1,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-4-4","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1056","processJobId":49,"taskId":139,"msgIds":"106858,106935,106910,106933,106891,106853,106908,106897,106859,106852,106909,106896,106926,106876,106917,106941,106873,106903,106930,106928,106884,106911,106938,106942,106921,106868,106862,106883,106855,106881,106894,106946,106901,106892,106915,106850,106866,106856,106904,106905,106885,106922,106872,106895,106863,106940,106890,106860,106898,106947,106857,106887,106907,106877,106874,106914,106949,106920,106912,106900,106944,106886,106880,106869,106906,106919,106861,106939,106888,106913,106916,106924,106899,106937,106851,106931,106875,106925,106936,106867,106889,106927,106864,106918,106943,106870,106865,106934,106929,106879,106948,106893,106945,106932,106882,106923,106902,106854,106878,106871","workerNo":2,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-9-9","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1056","processJobId":49,"taskId":139,"msgIds":"107011,106968,107031,106959,107009,107024,107010,106983,106981,106974,107043,106998,107017,107034,106988,106970,107039,106986,106991,107023,106995,107020,106964,107026,107008,106965,106954,107021,107030,107025,106994,106969,106985,107016,107041,107028,107042,106957,106967,107033,106989,106990,106987,107032,106955,106993,106962,106963,106992,106996,107044,106960,107006,107027,107004,107019,107003,106999,107005,107001,107015,107002,106975,106961,106977,107013,106950,106980,106979,106953,107038,106951,106978,107022,107029,106952,106971,107018,106956,107046,106982,106973,107035,106997,107036,106972,106984,107049,106966,107045,106976,106958,107037,107040,107000,107014,107007,107012,107048,107047","workerNo":3,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-10-10","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1129","processJobId":49,"taskId":139,"msgIds":"106267,106268,106270,106259,106264,106285,106312,106289,106338,106308,106334,106258,106272,106251,106313,106269,106250,106319,106262,106329,106260,106284,106314,106278,106288,106287,106307,106306,106345,106318,106255,106282,106342,106275,106317,106309,106343,106340,106337,106323,106281,106346,106277,106311,106332,106315,106276,106321,106298,106291,106303,106325,106336,106292,106279,106283,106305,106261,106271,106304,106295,106316,106293,106290,106320,106347,106280,106328,106254,106327,106333,106349,106294,106253,106252,106300,106331,106257,106263,106286,106274,106330,106266,106256,106299,106301,106339,106324,106348,106310,106341,106302,106335,106344,106273,106265,106322,106326,106296,106297","workerNo":4,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-3-3","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1128","processJobId":49,"taskId":139,"msgIds":"106095,106094,106066,106149,106139,106124,106073,106082,106128,106054,106140,106051,106084,106113,106074,106114,106078,106071,106101,106110,106117,106063,106083,106138,106120,106081,106107,106069,106105,106143,106096,106115,106106,106067,106131,106116,106119,106052,106093,106057,106097,106102,106077,106145,106127,106144,106141,106055,106058,106135,106076,106129,106089,106092,106126,106085,106109,106072,106080,106121,106122,106079,106056,106118,106134,106142,106098,106064,106112,106108,106090,106091,106130,106099,106075,106103,106136,106053,106132,106061,106068,106060,106087,106088,106146,106070,106062,106086,106147,106137,106123,106125,106104,106148,106059,106050,106065,106100,106111,106133","workerNo":5,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-1-1","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1179","processJobId":49,"taskId":139,"msgIds":"106245,106182,106187,106212,106238,106214,106179,106201,106181,106184,106163,106183,106200,106170,106189,106169,106247,106194,106209,106228,106175,106233,106229,106205,106164,106165,106227,106188,106172,106178,106150,106210,106196,106192,106180,106221,106158,106156,106220,106215,106159,106243,106217,106223,106226,106241,106198,106168,106222,106166,106218,106242,106213,106248,106244,106235,106240,106176,106237,106151,106224,106236,106162,106177,106152,106186,106195,106216,106208,106190,106219,106225,106204,106193,106155,106234,106246,106174,106199,106202,106157,106153,106206,106171,106185,106197,106211,106230,106161,106191,106160,106249,106203,106231,106239,106167,106207,106173,106232,106154","workerNo":6,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-2-2","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1128","processJobId":49,"taskId":139,"msgIds":"106593,106561,106635,106578,106556,106598,106619,106636,106629,106632,106611,106572,106638,106603,106592,106563,106557,106623,106550,106639,106596,106569,106562,106597,106618,106560,106630,106574,106581,106631,106617,106609,106594,106624,106604,106577,106606,106591,106580,106582,106625,106637,106584,106613,106616,106576,106587,106607,106589,106599,106647,106552,106570,106634,106646,106612,106640,106610,106553,106641,106585,106601,106605,106558,106622,106600,106621,106633,106566,106568,106583,106567,106644,106626,106565,106627,106645,106575,106586,106608,106614,106571,106642,106573,106649,106648,106620,106615,106551,106579,106588,106555,106602,106595,106559,106564,106643,106554,106628,106590","workerNo":7,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-6-6","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1181","processJobId":49,"taskId":139,"msgIds":"106506,106504,106516,106520,106512,106461,106458,106498,106544,106540,106489,106510,106453,106522,106537,106484,106531,106513,106503,106534,106508,106487,106494,106452,106526,106455,106466,106528,106462,106493,106469,106485,106536,106533,106496,106543,106451,106524,106525,106491,106488,106545,106521,106549,106479,106499,106501,106517,106538,106477,106541,106497,106500,106460,106478,106535,106450,106502,106459,106530,106474,106548,106480,106482,106542,106468,106457,106490,106454,106529,106509,106486,106507,106481,106515,106527,106475,106539,106465,106472,106547,106463,106464,106456,106495,106519,106546,106492,106532,106467,106471,106470,106511,106514,106483,106523,106518,106476,106473,106505","workerNo":8,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-5-5","errorText":"Error: ERROR Unknown error"},{"responseCode":"200","duration":"1184","processJobId":49,"taskId":139,"msgIds":"106688,106669,106665,106695,106742,106680,106748,106690,106691,106672,106683,106661,106687,106682,106702,106740,106650,106712,106651,106728,106703,106708,106715,106693,106707,106720,106706,106700,106724,106736,106663,106686,106701,106662,106664,106679,106749,106722,106653,106660,106730,106659,106747,106734,106652,106678,106657,106697,106713,106718,106746,106668,106743,106714,106655,106711,106744,106671,106699,106685,106725,106727,106739,106733,106737,106732,106676,106694,106710,106717,106709,106677,106723,106696,106716,106719,106673,106656,106689,106667,106681,106726,106675,106721,106729,106674,106658,106705,106738,106745,106692,106670,106698,106704,106731,106654,106666,106741,106735,106684","workerNo":9,"workerStatus":"Failed","responseText":"Error: ERROR Unknown error","errorType":"response","cnnStatus":"Success","msgCount":100,"workerName":"pool-1-thread-7-7","errorText":"Error: ERROR Unknown error"}],"totalMsgs":1000,"endDate":"2016-03-29 22:32:44","numberOfWorkers":"10","clientName":"CM"}];
	
	;

</script>




<script src="//connect.facebook.net/en_US/all.js&appId=1188666857860363" type="text/javascript"></script>

</body>
</html>


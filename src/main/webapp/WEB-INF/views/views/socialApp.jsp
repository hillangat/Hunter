<%@include file="../common/HeaderFile.jsp"%>

<script src="/Hunter/static/resources/scripts/plain/socialApp.js"></script>

<div id="hunterSocialAppContainer" >

	<div 
			style="height:450px;"
			data-role="grid"
			data-height="800"
			id="hunterSocialAppGrid" data-scrollable="true"
	        data-editable='{"mode": "popup", "template": "#=getSocialAppPopupTemplate()#"  }'
	        data-toolbar='["create"]'
	        data-pageable="{
	         	refresh:true
	         }"
			data-columns="[
	         		{ 'field': 'appId', title : 'App ID', 'width':'70px'   },
	         		{ 'field': 'appName', title : 'App Name'  },
	         		{ 'field': 'appDesc', title : 'App Description'  },
	         		{ 'field': 'extrnlId', title : 'External ID'  },
	         		{ 'field': 'extrnalPassCode', title : 'External Pas	scode'  },
	         		{ 'field': 'socialType', editor: socialTypeEditor, title : 'Social Type'  },
	         		{ 'field': 'createdBy', title : 'Created By'  },
	         		{ 'field': 'lastUpdatedBy', title : 'Last Updated By'  },
	         		{ 'field': 'cretDate', title : 'Created On'  },
	         		{ 'field': 'lastUpdate', title : 'Updated On'  },
	         		{ 'field': '', title : 'Configs', template : '#=getConfigsButtTemplate()#', 'width':'90px'  },
	         		{ 'field': '', title : 'Edit', template : '#=getSocialAppEditTemplate()#', 'width':'70px'  },
	         		{ 'field': '', title : 'Delete', template : '#=getSocialAppDelTemplate()#', 'width':'70px'  }, 
	      		]"
	       data-bind="source:SocialAppDS_, visible:isEverVisible"
		>
		</div>


</div>



<script type="text/x-kendo-template" id="xmlEditorTemplate">
	<div style='width:900px;height:550px;border:1px solid rgb(211,225,226); border-radius:5px;' id='xmlEditorTemplateContainer' >
		<textarea id="xmlEditorTemplateText" style='height:550px;overflow-x:scroll;overflow-y:scroll;word-wrap: break-word;overflow-x:auto;padding:5px;font-family:Courier New,Courier,consolas;font-size:14px;border:1px solid white;max-height:536px;max-width:900px;'  draggable="false" contenteditable="true" ></textarea>
	</div>
	<div style='width:900px;height:50px;border:1px solid rgb(195,221,223);background-color:rgb(215,243,243);border-radius:5px;margin-top:4px;'>
		<table style='height:30px;margin:2px;table-layout: fixed;width:36%;margin-left:32%;margin-top:10px;'>
			<tr>
				<td><button id="saveHTMLValue" onClick="SocialAppVM.saveHtmlValue()" style="width:150px;float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-tick"></span>Save</button></td>
				<td><button onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style="width:150px;float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-cancel"></span>Close</button></td>
			</tr>
		</table>
	</div>
</script>



<script id="socialAppEditPopupTemplate" type="text/x-kendo-template"  >
	<div class="k-edit-label">
	<label for="appId">App ID</label>
	</div>
	<div class="k-edit-field">1</div>
	<div class="k-edit-label">
	<label for="appName">App Name*</label>
	</div>
	<div data-container-for="appName" class="k-edit-field">
	<input class="k-input k-textbox" name="appName" required="required"
		data-bind="value:appName" type="text">
	</div>
	<div class="k-edit-label">
	<label for="appDesc">App Description*</label>
	</div>
	<div data-container-for="appDesc" class="k-edit-field">
	<input class="k-input k-textbox" name="appDesc" required="required"
		data-bind="value:appDesc" type="text">
	</div>
	<div class="k-edit-label">
	<label for="extrnlId">External ID*</label>
	</div>
	<div data-container-for="extrnlId" class="k-edit-field">
	<input class="k-input k-textbox" name="extrnlId" required="required"
		data-bind="value:extrnlId" type="text">
	</div>
	<div class="k-edit-label">
	<label for="extrnalPassCode">External Pas scode*</label>
	</div>
	<div data-container-for="extrnalPassCode" class="k-edit-field">
	<input class="k-input k-textbox" name="extrnalPassCode"
		required="required" data-bind="value:extrnalPassCode" type="text">
	</div>
	<div class="k-edit-label">
	<label for="socialType">Social Type*</label>
	</div>
	<div data-container-for="socialType" class="k-edit-field">
	<span style="" title="" class="k-widget k-dropdown k-header" unselectable="on" role="listbox" aria-haspopup="true" aria-expanded="false" tabindex="0" aria-owns="" aria-disabled="false" aria-readonly="false">
		<span unselectable="on" class="k-dropdown-wrap k-state-default">
			<span unselectable="on" class="k-input">Select Social Type</span>
			<span unselectable="on" class="k-select">
				<span unselectable="on" class="k-icon k-i-arrow-s">select</span>
			</span>
		</span>
		<input required="true" name="socialType" data-role="dropdownlist" style="display: none;" data-bind="value:socialType, source:socialTypeSource" 
			 data-value-primitive="true", data-text-field="text" data-value-field="value" data-option-label="Select Social Type" >
	</span>	
	</div>
</script>





















<%@include file="../common/pageCloser.jsp"%>
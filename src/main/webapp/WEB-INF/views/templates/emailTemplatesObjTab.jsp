
<script src="/Hunter/static/resources/scripts/plain/emailTemplatesObjTab.js"></script>

<div id="emailTemplateObjContainer" >

	<div 
			style="height:450px;"
			data-role="grid"
			id="emailTemplateObjGrid" data-scrollable="true"
	        data-editable="{'mode' : 'popup', 'template' : kendo.template($('\\#emailTemplateObjPopupTemplate').html())}" 
	        data-toolbar='["create"]'
	        data-pageable="{
	         	refresh:true
	         }"
			data-columns="[
	         		{ 'field': 'templateId', title : 'ID', 'width':'70px'   },
	         		{ 'field': 'templateName', title : 'Template Name'  },
	         		{ 'field': 'templateDescription', title : 'Template Description'  },
	         		{ 'field': 'cretDate', title : 'Created On'  },
	         		{ 'field': 'createdBy', title : 'Created By'  },
	         		{ 'field': 'lastUpdate', title : 'Updated On'  },
	         		{ 'field': '', title : 'Status', template : '#=getStatusTemplate()#', 'width':'90px'  },
	         		{ 'field': '', title : 'Template', template : '#=getMetadataXMLTemplate()#', 'width':'90px'  },
	         		{ 'field': '', title : 'HTML', template : '#=getXMLEditorButton()#', 'width':'70px'  },
	         		{ 'field': '', title : 'Preview', template : '#=getViewTemplate()#', 'width':'70px'  },
	         		{ 'field': '', title : 'Edit', template : '#=getEditTemplate()#', 'width':'70px'  },
	         		{ 'field': '', title : 'Delete', template : '#=getDelTemplate()#', 'width':'70px'  }	  
	      		]"
	       data-bind="source:emailTemplateObjDS, visible:isEverVisible"
		>
		</div>

</div>

<script type="text/x-kendo-template" id="emailTemplateObjPopupTemplate">
		<div class="k-edit-form-container">
		<div class="k-edit-label">
			<label for="templateName">Template Name</label>
		</div>
		<div data-container-for="templateName" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="templateName"
				required="required" data-bind="value:templateName">
		</div>
		<div class="k-edit-label">
			<label for="templateDescription">Template Description</label>
		</div>
		<div data-container-for="templateDescription" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="templateDescription"
				 data-bind="value:templateDescription">
		</div>
</script>


<script type="text/x-kendo-template" id="kendoSpinnerTemplate">
<div id='kendoSpinnerTemplate' class='hidden' >
	<div id="kendoSpinner" style="width: 100%; height: 100%; top: 0px; left: 0px;" class="k-loading-mask"><span class="k-loading-text">Loading...</span><div class="k-loading-image"></div><div class="k-loading-color"></div></div>
</div>
</script>

<script type="text/x-kendo-template" id="xmlEditorTemplate">
	<div style='width:900px;height:550px;border:1px solid rgb(211,225,226); border-radius:5px;' id='xmlEditorTemplateContainer' >
		<textarea id="xmlEditorTemplateText" style='height:550px;overflow-x:scroll;overflow-y:scroll;word-wrap: break-word;overflow-x:auto;padding:5px;font-family:Courier New,Courier,consolas;font-size:14px;border:1px solid white;max-height:536px;max-width:900px;'  draggable="false" contenteditable="true" ></textarea>
	</div>
	<div style='width:900px;height:50px;border:1px solid rgb(195,221,223);background-color:rgb(215,243,243);border-radius:5px;margin-top:4px;'>
		<table style='height:30px;margin:2px;table-layout: fixed;width:36%;margin-left:32%;margin-top:10px;'>
			<tr>
				<td><button id="saveHTMLValue" onClick="EmailTemplateObjVM.saveHtmlValue()" style="width:150px;float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-tick"></span>Save</button></td>
				<td><button onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style="width:150px;float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-cancel"></span>Close</button></td>
			</tr>
		</table>
	</div>
</script>

</div>
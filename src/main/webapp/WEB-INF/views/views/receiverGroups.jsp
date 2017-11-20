<%@include file="../common/HeaderFile.jsp"%>

<script src="<c:url value='/static/resources/scripts/model/receiverGroup.js'/>"></script>

<div id="receiverGroupVM" >
	<div 
		id="receiverGroupGrid"
		data-role="grid"
		data-height="800"
		data-refreshable="true"
		data-sortable="true"
		data-editable="inline"
		data-filterable="true"
		data-scrollable="true"
		data-pageable="{
				refresh:true
			}"
		data-toolbar='["create"]'
		data-columns="[
			{ 'field': 'groupId', title : 'ID', 'width':'60px'},
			{ 'field': 'groupName', title : 'Group Name'  },
			{ 'field': 'groupDesc', title : 'Group Description'  },
			{ 'field': 'receiverType', title : 'Type', editor : receiverTypeEditor, 'width' : '90px'  },
       		{ 'field': 'receiverCount', title : 'Receivers','width':'100px'  },
       		{ 'field': 'ownerUserName', title : 'Client', editor : userNameEditor    },
       		{ 'field': 'firstName', title : 'First Name'  },
       		{ 'field': 'lastName', title : 'Last Name'  },
       		{'field' : 'createdBy', 'title':'Created By'},
       		{'field' : 'lastUpdate', 'title':'Last Upadate'},
       		{'field' : 'lastUpdatedBy', 'title':'Updated By'},
       		{'field' : '', 'title':'Receivers','editable' : 'false', 'width' : '90px','template' : '#=getViewReceiversButton()#'},
       		{'field' : 'importButton', 'title':'Import','editable' : false, 'width' : '90px','template' : '#=getReceiverImportButton()#'},
       		{'field' : 'download', 'title':'Excel','editable' : false, 'width' : '90px','template' : '#=getImportFilesTemplate()#'},
       		{'field' : 'edit', 'title':'Edit','editable' : false, 'width' : '110px','template' : '#=getGroupEditTemplate()#','editor' : editorButtonEdit},
       		{'field' : 'delete', 'title':'Delete', 'width' : '100px','template' : '#=getGroupDeleteTemplate()#'}
   		]"
		data-bind="source: receiverGroupDS_, visible: isEverVisible"
	>
	</div>
	

</div>

<div id="importSectionTemplateCover" style='width:300px;position:relative;padding:10px;border:1px solid #9DB6C9;border-radius:4px;display:none;background-color:#EFF7FA;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #C7E5FC;' >
	<div id="kendoUploadContainer"> 
		<input type="file" name="select import file" id="receiverGroupInput" />
	</div>
	<div id='closeButtonDiv'   onClick='receiverGroupVM.closeImportSection()' style='width:40%;cursor:pointer;margin-left:30%;margin-top:20px;text-align: center;background-color:#F2D6D6;border:1px solid #BD9A9A;font-size:12px;padding:4px;border-radius:4px;' >
		<span class="k-icon k-i-cancel"></span>Close
	</div>
</div>

<div id="downloadImportSectionTemplateCover" style='width:36%;min-height:100px;position:relative;padding:10px;border:1px solid #9DB6C9;border-radius:4px;display:none;background-color:#EFF7FA;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #C7E5FC;' >
	<div id="downloadImportFilesTableContainer"> 
		
	</div>
	<div id='closeDownloadSectButt'   onClick='receiverGroupVM.closeDownloadSection()' style='width:40%;cursor:pointer;margin-left:30%;margin-top:20px;text-align: center;background-color:#F2D6D6;border:1px solid #BD9A9A;font-size:12px;padding:4px;border-radius:4px;' >
		<span class="k-icon k-i-cancel"></span>Close
	</div>
</div>

<script type="text/x-kendo" id="groupEditPopupTemplate">
	<div style='color:red' >Hey</div>
</script>


<script type="text/javascript">
	$("document").ready(function(){
		receiverGroupVM.init();
	});
</script>


<script type="text/x-kendo-template" id="openKendoImportSectionTemplate">
	<div style='height:300px;width:400px;'id="openKendoImportSection" >
		<input type="file" name="select import file" id="receiverGroupInput" />
	</div>
</script>


</body>
</html>

<style>
     #fieldlist {
         margin: 0 0 -2em;
         padding: 0;
     }

     #fieldlist li {
         list-style: none;
         padding-bottom: 2em;
     }

     #fieldlist label {
         padding-bottom: 1em;
         font-weight: bold;
         text-transform: uppercase;
         font-size: 12px;
         color: #0A1C29;
     }

     #fieldlist input {
         width: 100%;
         height:35px;
     }
 </style>


<script type="text/x-kendo-template" id='newMessageAttachmentPopupTemplate'>
	<div id='newMessageAttachmentPopup' style='width:400px;min-height:300px;height:300px;padding:10px;'>
	<form method="post" action="../messageAttachments/create" enctype='multipart/form-data' >
          <div class="demo-section k-content">
			 <ul id="fieldlist">
                    <li>
                        <label for="attachmentName">Attachment Name : </label>
                        <input id="attachmentName" validationMessage="Attachment name is required(50 characters or less)" required="required"   name="attachmentName" class="k-textbox"/>
                    </li>
					<li>
                        <label for="attachmentDesc">Attachment Description : </label>
                        <input id="attachmentDesc" validationMessage="Attachment name is required(50 characters or less)" required="required"  name="attachmentDesc" class="k-textbox" />
                    </li>
			 </ul>
			 <br/>			
			  <input name="messageAttachmentFiles" id="messageAttachmentFiles" type="file" /><br/>
				<table style='width:60%;margin-left:15%;' >
					<tr>
						<td><button type="submit" style="width:120px;;border-radius:3px;height:30px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-i-tick"></span>Submit</button>
						<td><button type='button' onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style="width:120px;border-radius:3px;height:30px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-cancel"></span>Cancel</button></td>
					</tr>
			  	</table>
          </div>
      </form>
</div>
</script>


<div id="messageAttachmentContainer" >
	<div 
			style="height:450px;"
			data-role="grid"
			id="messageAttachmentGrid" data-scrollable="true"
	        data-editable="{'mode' : 'popup'}"
	        data-resizable="true" 
	        data-toolbar = "[{'template' : '#=getMessageAttachmentToolBar()#'}]"
	        data-pageable="{
	         	refresh:true
	         }"
			data-columns="[
	         		{ 'field': 'beanName', title : 'Name',    },
	         		<!-- { 'field': 'beanDesc', title : 'Description'  }, -->
	         		{ 'field': 'originalFileName', title : 'Original Name'  },
	         		{ 'field': 'cid', title : 'CID'  },
	         		{ 'field': 'createdBy', title : 'Created By', 'width':'150px'  },
	         		<!-- { 'field': 'lastUpdatedBy', title : 'Updated By', 'width':'150px'  }, -->
	         		{ 'field': 'cretDate', title : 'Created On', 'width':'150px'  },
	         		<!-- { 'field': 'lastUpdate', title : 'Updated On', 'width':'150px'  }, -->
	         		{ 'field': 'fileFormat', title : 'Format', 'width':'80px'  },
	         		{ 'field': 'fileWidth', title : 'Width', 'width':'80px'  },
	         		{ 'field': 'fileHeight', title : 'Height', 'width':'80px'  },
	         		{ 'field': 'fileSize', title : 'Size', 'width':'80px'  },
	         		{ 'field': 'Delete', title : 'Delete', 'width':'70px', 'template' : '#=geMsgAttchmentDelTemplate()#'  }	  
	      		]"
	       data-bind="source:messageAttachmentDS, visible:isEverVisible"
		></div>
</div>


<script src="/Hunter/static/resources/scripts/plain/msgAttachments.js"></script>
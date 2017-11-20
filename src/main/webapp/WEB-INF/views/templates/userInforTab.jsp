
<script type="text/javascript" src="/Hunter/static/resources/scripts/plain/adminHome.js"></script>

<div id="hunterUserVVM" style="width:98%;min-height:780px;margin-left:1%;background-color: #EFF8FA;border:1px solid #D3E7EB;border-radius:4px;margin-top:1%;margin-bottom:1%;" >

<script type="text/x-kendo-template" id="hunterUserToolBar">
<div class="toolbar" >
	<button data-command="create" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button k-button-icontext k-grid-add"><span class="k-icon k-add"></span>New</button>
	<button id="loadHunterUserDetails" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="item-group k-button" style="width:150px" onClick="hunterAdminClientUserVM.loadSelUserDetails()"><span class='k-icon k-i-search' ></span>Load</button>
</div>
</script>
<script type='text/x-kendo-template' id='userDetailsToolBarTemplate' >
	<button class="hunter-tool-bar-butt" style="background-color:rgb(212,239,249);border : 1px solid rgb(185,204,210);"   type="button" class="k-button" value="Load User" >Load User</button>
</script>
<script type='text/x-kendo-template' id='hunterAdminUserGridPopupTemplate' >
	<div class="k-edit-form-container">
			<div class="k-edit-label">
				<label for="firstName">First Name</label>
			</div>
			<div data-container-for="firstName" class="k-edit-field">
				<input data-bind="value:firstName" required="required"
					name="firstName" class="k-input k-textbox" type="text">
			</div>
			<div class="k-edit-label">
				<label for="lastName">Last Name</label>
			</div>
			<div data-container-for="lastName" class="k-edit-field">
				<input data-bind="value:lastName" required="required"
					name="lastName" class="k-input k-textbox" type="text">
			</div>
			<div class="k-edit-label">
				<label for="middleName">Middle Name</label>
			</div>
			<div data-container-for="middleName" class="k-edit-field">
				<input data-bind="value:middleName" required="required"
					name="middleName" class="k-input k-textbox" type="text">
			</div>
			<div class="k-edit-label">
				<label for="email">Email</label>
			</div>
			<div data-container-for="email" class="k-edit-field">
				<input data-bind="value:email" required="required" name="email"
					class="k-input k-textbox" type="text">
			</div>
			<div class="k-edit-label">
				<label for="userName">User Name</label>
			</div>
			<div data-container-for="userName" class="k-edit-field">
				<input data-bind="value:userName" required="required"
					name="userName" class="k-input k-textbox" type="text">
			</div>
			<div class="k-edit-label">
				<label for="password">Password</label>
			</div>
			<div data-container-for="password" class="k-edit-field">
				<input data-bind="value:password" required="required"
					name="password" class="k-input k-textbox" type="password">
			</div>
		</div>
</script>

<style>
	.hunterForm  li {
		 list-style: none;
         padding-bottom: 0.8em;
	}
	
	.hunterForm label {
                font-weight: bold;
                font-size: 12px;
                color: #000000;
                with:40%;
            }
	
</style>

<div id="hunterUserGridCover" style="width:96%;height:350px;margin-left:2%;margin-right:2%;margin-top:2%;margin-bottom:0.5%;background-color: #F9FEFF;border:1px solid #D3E7EB;border-radius:4px;" >

	<div 
					style="height:350px;"
					data-role="grid"
					id="hunterUserGrid" data-scrollable="true"
              				data-editable="{'mode' : 'popup', 'template' : kendo.template($('\\#hunterAdminUserGridPopupTemplate').html()) }" 
              				data-toolbar='["create"]'
              				data-pageable="{
              						refresh:true
              					}"
              				data-columns="[
              						{field : '', width:'40px',title : '', 'template' : '<input id=hunterAdminUser_#=userId# type=checkbox onChange=hunterAdminClientUserVM.correctChecks(#=userId#)></input>' },
                              		{ 'field': 'firstName', title : 'First Name'  },
                              		{ 'field': 'lastName', title : 'Last Name'  },
                              		{ 'field': 'middleName', title : 'Middle Name'  },
                              		{ 'field': 'email', title : 'Email'  },
                              		{ 'field': 'userName', title : 'User Name' },
                              		{ 'field': 'cretDate', title : 'Created On'  },
                              		{ 'field': 'createdBy', title : 'Created By'  },
                              		{ 'field': 'lastUpdate', title : 'Updated On'  },
                              		{ 'field': 'lastUpdatedBy', title : 'Updated By' },
                              		{ 'field': '', title : 'Edit', template : '#=getEditTemplate()#', 'width':'70px'  },
                              		{ 'field': '', title : 'Delete', template : '#=getDeleteTemplate()#', 'width':'70px'  },
                           		]"
              				data-bind="source:hunterUserDS, visible:isEverVisible"
				>
	</div>


</div>
<div id="hunterUserDetailsGrid" data-role="tabstrip" style="width:96%;height:350px;	margin-left:2%;margin-right:2%;margin-top:0.5%;border:1px solid #D3E7EB;border-radius:4px;" >

<div id="hunterUserTabStrip" data-role="tabstrip" >
	 <ul style='margin-left:40%;'>
         <li >User Addresses</li>
         <li class="k-state-active" >User Roles</li>
     </ul>
	 <div style="height:300px;" >
	 	
	 	<div 
					style="height:295px;"
					data-role="grid"
					id="hunterUserAddressesGrid"
             				data-scrollable="true"
              				data-editable="popup"
              				data-pageable="{refresh:'true'}"
              				data-toolbar = "['create']"
              				data-columns="[
                              		{ 'field': 'country', title : 'Country'  },
                              		{ 'field': 'state', title : 'State'  },
                              		{ 'field': 'aptNo', title : 'Apartment'  },
                              		{ 'field': 'city', title : 'City' },
                              		{ 'field': 'zip', title : 'Zip Code' },
                              		{ 'field': 'type', title : 'Address Type' }
                           		]"
              				data-bind="source:hunterUserAddressesDS, visible:isEverVisible"
				>
	</div>
	 	
	 	
	 </div>
	<div style="height:300px;" >
	
		<div 
					style="height:295px;"
					data-role="grid"
					id="hunterUserRolesGrid"
             				data-scrollable="true"
              				data-editable="popup"
              				data-pageable="true"
              				data-toolbar = "[{'template' : '#=getToolBarTemplate()#'}]"
              				data-columns="[
              						{'field' : 'roleId', title : 'Role Id', width:'60px' },
                              		{ 'field': 'roleShortName', title : 'Role Name', width:'100px'  },
                              		{ 'field': 'roleLevel', title : 'Role Level', width:'50px'  },
                              		{ 'field': 'roleDesc', title : 'Role Description', width:'250px'  },
                              		{ 'name': 'delete', title : 'Remove Role', width:'40px', template : '#=getUserRoleDeleteTemplate()#'  }
                           		]"
              				data-bind="source:selUserRoles, visible:isEverVisible"
				>
	     </div>
	
	</div>
</div>
	
	
<script type="text/x-kendo-template" id="userRoleDropdownListTemplate" >
<div id='addRoleToUserPopup' style='width:100%;height:100%;'  >
	<br/>
	<table style='width: 100%;'>
		<tr>
			<td><input style='width: 100%;' id='#=kendoDropdownListName#' type='k-textbox' placeHolder="Select Role..." ></td>
		</tr>
	</table>
	<br/>
	<table style='width:70%;margin-left:15%;' >
		<tr>
			<td><a onclick="hunterAdminClientUserVM.addRoleToSelUserAndClose()" class="k-button" style="padding:5px;"><span style="padding-right:10px;padding-left:10px;">Add Role</span></a></td>
			<td><a onclick="hunterAdminClientUserVM.destroyAndCloseWindow()" class="k-button" style="padding:5px;"><span style="padding-right:10px;padding-left:10px;">Cancel</span></a></td>
		</tr>
	</table>
</div>	
</script>
	

</div>






















	<script type="text/javascript">
	$("document").ready(function(){
		hunterAdminClientUserVM.init();
	});
</script>

</div>
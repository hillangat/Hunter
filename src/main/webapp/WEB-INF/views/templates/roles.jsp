
<script src="/Hunter/static/resources/scripts/plain/roles.js"></script>

<div id="hunterRoleCoreConfigContainer" >

	<div 
			style="height:450px;"
			data-role="grid"
			id="hunterUserRoleGridGrid" data-scrollable="true"
	        data-editable="{'mode' : 'popup'}" 
	        data-toolbar='["create"]'
	        data-pageable="{
	         	refresh:true
	         }"
			data-columns="[
	         		{ 'field': 'roleId', title : 'ID', 'width':'70px'   },
	         		{ 'field': 'roleName', title : 'Role Name'  },
	         		{ 'field': 'roleShortName', title : 'Short Name'  },
	         		{ 'field': 'roleDesc', title : 'Role Description'  },
	         		{ 'field': 'roleLevel', title : 'Role Level','width':'100px'  },
	         		{ 'field': '', title : 'Edit', template : '#=getUserRoleDelTemplate()#', 'width':'70px'  }	  
	      		]"
	       data-bind="source:userRoleDS, visible:isEverVisible"
		>
		</div>

</div>


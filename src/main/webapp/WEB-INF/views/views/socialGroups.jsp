<%@include file="../common/HeaderFile.jsp"%>

<script src="<c:url value='/static/resources/scripts/plain/kendoHelper.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/plain/socialGroups.js'/>"></script>


<div id="socialGroupsVM" >
	<div 
		id="socialGroupsGrid"
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
		data-toolbar = "[{'template' : '#=getSocialGroupToolBarTemplate()#'}]"
		data-columns="[
			{ 'field' : 'groupId', 		title : 'ID', 'width':'60px'		},
			{ 'field' : 'groupName', 	title : 'Group Name'  				},
			{ 'field' : 'regionName', 	title : 'Region Name'	     		},
			{ 'field' : 'socialAppName', 	title : 'Default App'	     		},
			{ 'field' : 'socialType', 	title : 'Type' 		},
			{ 'field' : 'active',		title : 'Active','width':'100px' 	},
       		{ 'field' : 'regionCountryName', 	title : 'Country' 			},
       		{ 'field' : 'regionCountyName', 	title : 'County'  			},
       		{ 'field' : 'regionConsName', 	title : 'Constituency'			},
       		{ 'field' : 'regionWardName', 	title : 'Ward'  				},
       		{ 'field' : 'cretDate', 	title : 'Created On'  				},
       		{ 'field' : 'createdBy', 	title : 'Created By'				},
       		{'field'  : 'status', editable:'false', 'title':'Status', 'width' : '100px','template' : '#=getSocialGroupStatusTemplate()#'},
       		{'field'  : 'edit', editable:'false', 'title':'Edit', 'width' : '100px','template' : '#=getGroupEditTemplate()#'},
       		{'field'  : 'delete', editable:'false', 'title':'Delete', 'width' : '100px','template' : '#=getGroupDeleteTemplate()#'}
   		]"
		data-bind="source: SocialGroupsDS_, visible: isEverVisible"
	>
	</div>
	

</div>




















</body>
</html>
<%@include file="../common/HeaderFile.jsp"%>

<script src="/Hunter/static/resources/scripts/plain/socialRegions.js"></script>

<div id=hunterSocialRegionsContainer >

	<div 
			style="height:450px;"
			data-role="grid"
			data-height="800"
			id="hunterSocialRegionsGrid" 
			data-scrollable="true"
	        data-pageable="{
	         	refresh:true
	         }"
			data-columns="[
	         		{ 'field': 'regionId', 		title : 'ID', 'width':'70px'   },
	         		{ 'field': 'regionName', 	title : 'Region Name'  },
	         		{ 'field': 'assignedTo', 	title : 'Assigned To'  },
	         		{ 'field': 'countryName', 	title : 'Country'  },
	         		{ 'field': 'countyName', 	title : 'County'  },
	         		{ 'field': 'consName', 		title : 'Constituency'  },
	         		{ 'field': 'wardName', 		title : 'Ward Name'   },
	         		{ 'field': 'cretDate', 		title : 'Created On'  },
	         		{ 'field': 'createdBy', 	title : 'Created By'  },
					{ 'field': 'lastUpdatedBy', title : 'Updated By'  },
	         		{ 'field': 'lastUpdate', 	title : 'Updated On'  },
	         		
	         		{ 'field': '', title : 'Edit', template : '#=getSocialRegionsUpdateTemplate()#', 'width':'70px'  },
	         		{ 'field': '', title : 'Delete', template : '#=getSocialRegionsDelTemplate()#', 'width':'70px'  }
	         			  
	      		]"
	       data-bind="source:SocialRegionsDS_, visible:isEverVisible"
	       data-toolbar='[ { "template" : "<button data-value-name=\"newSocialRegion\" onClick=\"SocialRegionsVM.showPopupForNewSocialRegion(0)\"  style=\"background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);\" class=\"k-button\"><span class=\"k-icon k-add\"></span>Create New Region</button>" } ]'
		>
		</div>

</div>




<%@include file="../common/pageCloser.jsp"%>

























<div style="with:100%;min-height:250px;width:100%;" id="taskUserAddressesVM"  >

<div 
					style="height:350px;"
					data-role="grid"
					id="hunterUserAddressesGrid"
             				data-scrollable="true"
              				data-editable="false"
              				data-pageable="{
              						refresh:true
              					}"
              				data-toolbar = "['create']"
              				data-editable = "inline"
              				data-columns="[
                              		{ 'field': 'country', title : 'Country'  },
                              		{ 'field': 'state', title : 'State'  },
                              		{ 'field': 'aptNo', title : 'Apartment'  },
                              		{ 'field': 'city', title : 'City' },
                              		{ 'field': 'zip', title : 'Zip Code' },
                              		{ 'field': 'type', title : 'Address Type' }
                           		]"
              				data-bind="source:hunterUserAddressesDS, visible:isEverVisible_"
				>
	</div>


</div>
<script src="http://localhost:8080/Hunter/static/resources/scripts/model/taskUserAddresses.js"></script>
<script type="text/javascript">
$("document").ready(function(){
	hunterUserAddressesVM.init();
});
</script>
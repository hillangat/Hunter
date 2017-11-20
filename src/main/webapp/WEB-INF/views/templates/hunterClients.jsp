

<div style="height:90%;width:98%;min-height:500px;margin:1%;" id="hunterClientsTab" >
	
	
	<div 
		style="height:295px;"
		data-role="grid"
		id="hunterClientsTabGrid"
        data-scrollable="true"
        data-editable="{'mode' : 'popup', 'template' : kendo.template($('\\#hunterClientTemplate').html()) }"
        data-pageable="true"
        data-toolbar='["create"]'
        data-columns="[
        	{'field' : 'clientId', title : 'Client Id',width:'120px' },
        	{ 'field': 'firstName', title : 'First Name'  },
            { 'field': 'lastName', title : 'Last Name'  },
            { 'field': 'email', title : 'Email'  },
            { 'field': 'receiver', title : 'Is Receiver', width:'100px'  },
            { 'field': 'budget', title : 'Budget', width:'120px'  },
            { 'field': 'createdDate', title : 'Created Date'  },
            { 'field': 'createdBy', title : 'Created By', width:'200px'  },
            { 'field': 'updatedBy', title : 'Updated By', width:'250px'  },
            { 'field': 'updatedOn', title : 'Updated On'  },
            { 'field': '', title : 'Edit', template : '#=getEditTemplate()#', 'width':'70px'  },
			{ 'field': '', title : 'Delete', template : '#=getDeleteTemplate()#', 'width':'70px'  }
           ]"
		data-bind="source:hunterClientsDS, visible:isEverVisible"
	>
   </div>

<!-- data-toolbar = "[{'template' : '#=getToolBarTemplate()#'}]" -->


</div>


<script src="/Hunter/static/resources/scripts/plain/hunterClients.js"></script>


<script type="text/x-kendo-template" id='hunterClientTemplate' >
<div class="k-edit-form-container">
	<div class="k-edit-label">
		<label for="clientId">Client Id</label>
	</div>
	<div class="k-edit-field">8</div>
	<div class="k-edit-label">
		<label for="firstName">First Name</label>
	</div>
	<div class="k-edit-field">Mathagei</div>
	<div class="k-edit-label">
		<label for="lastName">Last Name</label>
	</div>
	<div class="k-edit-field">Mathagei</div>
	<div class="k-edit-label">
		<label for="email">Email</label>
	</div>
	<div class="k-edit-field">hillangat@gmail.com</div>
	<div class="k-edit-label">
		<label for="receiver">Is Receiver</label>
	</div>
	<div data-container-for="receiver" class="k-edit-field">
		<input data-bind="checked:receiver" data-type="boolean"
			required="required" name="receiver" type="checkbox">
	</div>
	<div class="k-edit-label">
		<label for="budget">Budget</label>
	</div>
	<div data-container-for="budget" class="k-edit-field">
		<span class="k-widget k-numerictextbox" style=""><span
			class="k-numeric-wrap k-state-default"><input
				aria-readonly="false" aria-disabled="false" title=""
				style="display: inline;" tabindex="0"
				class="k-formatted-value k-input" type="text"><input
				aria-readonly="false" aria-disabled="false" aria-valuenow="450000"
				class="k-input" style="display: none;" role="spinbutton"
				data-role="numerictextbox" data-bind="value:budget"
				data-type="number" required="required" name="budget" type="text"><span
				class="k-select"><span unselectable="on" class="k-link"><span
						unselectable="on" class="k-icon k-i-arrow-n"
						title="Increase value">Increase value</span></span><span
					unselectable="on" class="k-link"><span unselectable="on"
						class="k-icon k-i-arrow-s" title="Decrease value">Decrease
							value</span></span></span></span></span><span style="display: none;" data-for="budget"
			class="k-invalid-msg"></span>
	</div>
	<div class="k-edit-label">
		<label for="createdDate">Created Date</label>
	</div>
	<div class="k-edit-field">2015-09-14 10:09:26</div>
	<div class="k-edit-label">
		<label for="createdBy">Created By</label>
	</div>
	<div class="k-edit-field">hlangat01</div>
	<div class="k-edit-label">
		<label for="updatedBy">Updated By</label>
	</div>
	<div class="k-edit-field">hunterAdmin</div>
	<div class="k-edit-label">
		<label for="updatedOn">Updated On</label>
	</div>
	<div class="k-edit-field">2016-01-18 03:01:38</div>
</div>
</script>


<script type="text/x-kendo-template" id="hunterClientsToolBarTemplate">
<div class="toolbar" >
	<button data-command="create" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button k-button-icontext k-grid-add"><span class="k-icon k-add"></span>New</button>
</div>
</script>

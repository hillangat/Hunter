<%@include file="../common/HeaderFile.jsp"%>

<script src="<c:url value='/static/resources/scripts/model/regionVM.js'/>"></script>

<div id="taskRegionViewCover" style="width:22%;height:200px;float:left;border:1px solid #C7EAFF;padding:25px;border-radius:4px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;" >

<table style="width:80%; border-collapse:separate;border-spacing:1.02em;">
	<tr>
		<td>Country :</td>
		<td>
			 <input id="countryDropDownList"
			 	   data-role="dropdownlist"
                   data-auto-bind="true"
                   data-value-primitive="true"
                   data-text-field="countryName"
                   data-value-field="countryId"
                   placeholder = "Select Country"
                   data-bind="value: selCountry,
                              source: countryDS,
                              visible: isCountryDropdownVisible,
                              enabled: isCountryDropdownEnabled,
                              events: {
                                change: onChangeCountry,
                              }"
            />
		</td>
	</tr>
	<tr>
		<td>County :</td>
		<td> 
		
			<input id="countyDropDownList"
			 	   data-role="dropdownlist"
                   data-auto-bind="true"
                   data-value-primitive="true"
                   data-text-field="countyName"
                   data-value-field="countyId"
                   data-optionlable = "Select County"
                   data-bind="value: selCounty,
                              source: countyDS,
                              visible: isCountyDropdownVisible,
                              enabled: isCountyDropdownEnabled,
                              events: {
                                change: onChangeCounty,
                              }"
            />
		</td>
	</tr>
	<tr>
		<td>Constituency :</td>
		<td>
			<input id="constituencyDropDownList"
			 	   data-role="dropdownlist"
                   data-auto-bind="true"
                   data-value-primitive="true"
                   data-text-field="constituencyName"
                   data-value-field="constituencyId"
                   data-optionlable = "Select Consituency"
                   data-bind="value: selConstituency,
                              source: constituencyDS,
                              visible: isConstituencyDropdownEnabled,
                              enabled: isConstituencyDropdownVisible,
                              events: {
                                change: onChangeConstituency,
                              }"
            />
		</td>
		
		
	</tr>
	<tr>
		<td>ConstituencyWard :</td>
		<td>
			<input id="constituencyWardDropDownList"
			 	   data-role="dropdownlist"
                   data-auto-bind="true"
                   data-value-primitive="true"
                   data-text-field="constituencyWardName"
                   data-value-field="constituencyWardId"
                   data-optionLabel = "Select Consituency"
                   data-bind="value: selConstituencyWard,
                              source: constituencyWardDS,
                              visible: isConstituencyWardDropdownEnabled,
                              enabled: isConstituencyWardDropdownVisible,
                              events: {
                                change: onChangeConsituencyWard,
                              }"
            />
		</td>
	
	
	
	
	</tr>
</table> 
<span style="margin:10px;" ></span>
<table style="margin:0 auto;">
	<tr>
		<td><button onClick="hunterAdminClientUserVM.saveCurrentEditRegionAndClose()"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Save And Close</button></td>
		<td><button onClick="hunterAdminClientUserVM.saveCurrentEditRegion()"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Save</button></td>
    	<td><button onClick="hunterAdminClientUserVM.canceltEditRegionAndClose()"  class="k-button" style="background-color:rgb(212,239,249);width:100%;border : 1px solid rgb(120,186,210);" >Cancel</button></td>
	</tr>
</table>

</div>

<div id="taskRegionViewCover2" style="width:70%;height:200px;float:right;border:1px solid #C7EAFF;padding:25px;border-radius:4px;-webkit-box-shadow: 0 0 5px 2px #E2F3FF; -moz-box-shadow: 0 0 5px 2px #E2F3FF; box-shadow: 0 0 5px 2px #E2F3FF;" >

</div>



</body>
</html>
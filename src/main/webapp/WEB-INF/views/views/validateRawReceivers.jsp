<%@include file="../common/HeaderFile.jsp"%>

<script src="/Hunter/static/resources/scripts/plain/validateRawReceivers.js"></script>

<style>
	#validateRawReceiversContainer{
		border : 1px solid #98CDDA;
		min-height : 500px;
		border-radius:5px;
		width : 96%;
		margin-left:2%;	
		background-color:#EAF2F5;	
	}
</style>

<div id="validateRawReceiversContainer" style='font-size:12px;'>

<div id="validateRawReceiverRequestParams"  style="border-bottom:10px;" >
	<table style="width:50%;margin:1%;font-size:12px;" >
		<tr>
			<td><input data-ref-name="dateRange" type="checkbox" data-bind="checked: isDateChecked" onChange="ValidateRawReceiverVM.unSelectDefaultDateCheckBox(this)" /></td>
			<td style="font-weight: bold;">Date Range&nbsp;&nbsp;&nbsp;&nbsp;: </td>
			<td>
				<table>
					<tr>
						<td style="width:210px" >
							<span>
								<input id="fromDatePicker" data-role="datepicker" data-format="yyyy-MM-dd" data-bind="visible: isVisible, enabled: isDateChecked, value: selDateFrom" style="width: 209px;">
							</span>
						</td>
						<td style="width:250px" >
							<span>
								<input id="toDatePicker" data-role="datepicker" data-format="yyyy-MM-dd" data-bind="visible: isVisible, enabled: isDateChecked, value: selDateTo" style="width: 209px;">
							</span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><input  data-ref-name="rawUser"  type="checkbox" data-bind="checked: isUserChecked" onChange="ValidateRawReceiverVM.unSelectDefaultDateCheckBox(this)" /></td>
			<td style="font-weight: bold;">Created By&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: </td>
			<td style="padding:0px;" >
				 <input 
				   id = "rawUserDropdownList"
				   data-role="dropdownlist"
                   data-auto-bind="false"
                   data-value-primitive="true"
                   data-text-field="userFullName"
                   data-value-field="userId"
                   data-option-label="Select Created By"
                   data-bind="value: checkedUserId,
                              source: rawReceiverUsers,
                              visible: isVisible,
                              enabled: isUserChecked"
                              style="width:73%;"
                                />
			</td>
		</tr>
		<tr>
			<td><input  data-ref-name="regionData"  type="checkbox" data-bind="checked: isRegionChecked" onChange="ValidateRawReceiverVM.unSelectDefaultDateCheckBox(this)" /></td>
			<td style="font-weight: bold;">From Region&nbsp;&nbsp;: </td>
			<td>
				<table style="width:100%;">
					<tr>
						<td  style="width:150px" >
							<input 
							   id = "countriesDropdownList"
							   data-role="dropdownlist"
			                   data-auto-bind="false"
			                   data-value-primitive="true"
			                   data-option-label="Select Country..."
			                   data-text-field="regionName"
			                   data-value-field="regionId"
			                   data-bind="value: selCountry,
			                              source: countries,
			                              visible: isVisible,
			                              enabled: isRegionChecked, 
			                              events : {change:onChangeCountry}"
			                              style="width:150px;"
			                                />
						</td>
						<td   style="width:150px" >
							<input 
							   id = "countiesDropdownList"
							   data-role="dropdownlist"
			                   data-auto-bind="false"
			                   data-option-label="Select County..."
			                   data-value-primitive="true"
			                   data-text-field="regionName"
			                   data-value-field="regionId"
			                   data-bind="value: selCounty,
			                              source: counties,
			                              visible: isVisible,
			                              enabled: isRegionChecked, 
			                              events : {change:onChangeCounty}"
			                              style="width:150px;"
			                                />
						</td>
						<td   style="width:150px" >
							<input
							   placeholder="Select Constituency" 
							   id = "constituencyDropdownList"
							   data-role="dropdownlist"
			                   data-auto-bind="false"
			                   data-option-label="Select Constituency..."
			                   data-value-primitive="true"
			                   data-text-field="regionName"
			                   data-value-field="regionId"
			                   data-bind="value: selConstituency,
			                              source: constituencies,
			                              visible: isVisible,
			                              enabled: isRegionChecked, 
			                              events : {change:onChangeConstituency"
			                              style="width:150px;"
			                                />
						</td>
						<td >
							<input 
							   id = "wardsDropdownList"
							   data-role="dropdownlist"
			                   data-auto-bind="false"
			                   data-value-primitive="true"
			                   data-option-label="Select Ward..."
			                   data-text-field="regionName"
			                   data-value-field="regionId"
			                   data-bind="value: selWard,
			                              source: wards,
			                              visible: isVisible,
			                              enabled: isRegionChecked, 
			                              events : {change:onChangeWard}"
			                              style="width:150px;"
			                                />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><input  data-ref-name="defaultDate"  type="checkbox" data-bind="checked: isDefaultDateSelected" onChange="ValidateRawReceiverVM.unSelectAllOthersCheckBoxes(this)" /></td>
			<td style="font-weight: bold;">Recent Receivers&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<table>
					<tr>
						<td>
							<input onClick="ValidateRawReceiverVM.changeNameOfCertButton(this)" data-bind="attr:{name:radioGroup}, checked:selCertifiedStatus" value="N" type="radio" name="uncertifiedReceivers" id="uncertifiedReceivers" class="k-radio">
		              		<label class="k-radio-label" for="uncertifiedReceivers">Uncertified</label>
						</td>
						<td>
							<input onClick="ValidateRawReceiverVM.changeNameOfCertButton(this)"  data-bind="attr:{name:radioGroup}, checked:selCertifiedStatus" value="Y" type="radio" name="certifiedReceivers" id="certifiedReceivers" class="k-radio">
		              		<label class="k-radio-label" for="certifiedReceivers">Certified</label>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div id="validateRawReceiverGrid" style="height: 600px;" ></div>


</div>




















</body>
</html>
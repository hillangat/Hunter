<%@include file="../common/HeaderFile.jsp"%>

<style>
 .editTextBoxNumeric : {
 	border : 1px solid #A2D3DB;
 }
</style>

<script src="<c:url value='/static/resources/scripts/plain/kendoHelper.js'/>"></script>
<script src="<c:url value='http://localhost:8080/Hunter/static/resources/scripts/model/regionHierarchy.js'/>"></script>

<div style="width:98%;border:1px solid #CADDE9;margin-left:1%;background-color:#E1F2FC;padding:10px;border-radius:5px;" id="regionHierarchyTreeListVMContainer"  >

<div id="countriesDropdownList" >
	<table style="margin-left:3%;margin-top:20px;">
		<tr>
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
	                              events: {
	                                change: onChangeCountry,
	                              }"
	            />
			</td>
		</tr>
	</table>
</div>

<div id="regionHierarchyTreeList"  style="width:93%;margin-left:3%;margin-bottom:2%;"  >
	
</div>

<div id="uploadReceiversContainer" style="margin-top:3%;display:none; with:200px; margin:0 auto;" >
<input name="files"
    type="file"
    placeholder = "Select File"
    data-role="upload"
    data-multiple="false"
    data-async="{ saveUrl: 'http://localhost:8080/Hunter/messageReceiver/action/import/receivers/post/save', 
    			  removeUrl: 'http://localhost:8080/Hunter/messageReceiver/action/import/receivers/post/save', 
    			  autoUpload: true 
    			}"
    data-bind="visible: isUploadReceiversVisible,
               enabled: isUploadReceiversEnabled,
               events: { select: onSelect,
               			 complete : onComplete,
               			 error: onError
               			}"
  >

<p style="text-align: left;margin-top:10px;" >
	<button style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" onClick="RegionHierarchyVM.closeUploadDivAndShowGrid()" class="k-button k-button-icontext k-grid-add"><span class="k-icon k-i-arrowhead-w"></span>Go Back</button>
</p>
</div>

<script type="text/javascript">
	$("document").ready(function(){
		$("#uploadReceiversContainer").css("width", "400px");
		$("#uploadReceiversContainer").css("float", "left");
	});
</script>

</div>

	

</body>

<script type="text/x-kendo-template" id="regionHierarchyToolBarTemplate">
<div class="toolbar" >
	<button style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" onClick="RegionHierarchyVM.openSectionToUploadReceivers()" class="k-button k-button-icontext k-grid-add"><span class="k-icon k-i-seek-n"></span>Receivers</button>
</div>
</script>

<script type="text/x-kendo-template" id="regionHierarchyToolBarTemplateNew">
<div id='regionHierarchyKendoUpload'  style='width:350px;height:auto;background-color:#EBF7FF;border:1px solid #B6D2E2;border-radius:4px;' >
 <div style="width:90%;margin-left:5%;margin-top:15px;padding:5px;" >
	<form method="post">
        <div class="demo-section k-content">
            <input name="hunterHierarchyFiles" 
				id="hunterHierarchyFiles" 
				type="file"  
				placeholder = "Select File" />
        </div>
    </form>
	<table style="width:30%;margin-left:35%;margin-top:10px;" >
		<tr>
			<td><button onClick="kendoKipHelperInstance.closeWindowWithOnClose()" class="k-button" style="width:100%;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-i-close"></span>Close</button></td>
		</tr>
	<table>
 </div>
</div>
</script>

<script type="text/x-kendo-template" id="regionHierarchyToolBarTemplate">
<input id="countryDropDownList"
	data-role="dropdownlist"
	data-auto-bind="true"
	data-value-primitive="true"
	data-text-field="countryName"
	data-value-field="countryId"
	placeholder = "Select Country"
	data-bind="value: selCountry,
		source: countryDS,
		events: {
		change: onChangeCountry,
	}"
</script>

<script type="text/x-kendo-template" id="regionHierarchyEditTemplate">
<div id="regionHierarchyEditContainer" >
	<table id="regionHierarchyEditData"  style="table-layout:fixed;width:400px;" >
		<tr>
			<td style="width:40%;" >Region Name</td>
			<td style="width:60%;" ><input id="regionName" name="regionName"  value="#=name#"  class="k-textbox"  style="width: 220px;border:1px solid rgb(162,211,219);border-radius:4px;height:30px;" /></td>
		</tr>
		<tr>
			<td style="width:40%;" >Population</td>
			<td style="width:60%;" ><input id="population" name="population"  type="number" value="#=population#" min="0"  max="10000000" style="width: 217px;border:1px solid rgb(162,211,219);border-radius:4px;height:25px;" /></td>
		</tr>
		<tr>
			<td style="width:40%;" >Code</td>
			<td style="width:60%;" ><input id="regionCode" name="regionCode"  value="#=regionCode#"  class="k-textbox"  style="width: 220px;border:1px solid rgb(162,211,219);border-radius:4px;height:30px;" /></td>
		</tr>
	</table>
	<br/>
	<table style="table-layout:fixed;width:40%; margin-left:30%;" >
		<tr>
			<td><button onClick="RegionHierarchyVM.editSelEditRegion(#=regionId#)" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-tick"></span>Update</button></td>
			<td><button onClick="kendoKipHelperInstance.closeWindowWithOnClose()" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" class="k-button" ><span class="k-icon k-i-cancel"></span>Cancel</button></td>
		</tr>
	</table>
	<div id='editRegionHierIconHolder' style="display:none;width:100%;text-align:center;"><span id="editRegionHierIconHolderSpan" style="color:rgb(10,135,5);text-align:center;width:100%;font-size:18px;font-weight:bold;">Saving changes...</span></div>
</div>
</script>

<script type="text/javascript">
	$("document").ready(function(){
		RegionHierarchyVM.init();
	});	
</script>

</html>

<style>
	.hunterCachHeader{
		border-bottom:1px solid #D2E3EE;
		background-color: #E3EDF3;
		height: 35px;
		padding-left:10px;
		border-radius:3px;
		border:1px solid #CDDCE5;
		margin:0px;
		font-weight: bolder;
		font-size: 18px;
	}
	.hunterCachRecord{
		border:1px solid #DCEAF2;
		padding-left:10px;
		height: 30px;
		font-size: 16px;
	}
	#hunterCachFunctionsTable tr td[class='hunterCachRecord']:nth-child(2){ 
		text-align: center;
	}
</style>

<div style="height:90%;width:98%;min-height:500px;margin:1%;" id="hunterMainCoreConfigTab" >
	
	<div id="hunterCacheFunctions" style='height:45%;width:91%;margin-left:1%;position:fixed;margin:1%;padding:20px;border-radius:5px;' >
		
		<table id='hunterCachFunctionsTable' style='width:40%;margin-left:30%;border-collapse:collapse;border-radius:5px;background-color:#F3F9FC;' >
			<tr>
				<td class='hunterCachHeader' >CACHE NAME</td>
				<td class='hunterCachHeader' style='width:40px;' ><center><input type='checkbox' data-visible='true' id='checkAllCheckBoxes'></center></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >All XML Services</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='allXMLService'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Query XML</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='queryXML'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >UI Message XML</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='uiMsgXML'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Client Configuration</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='clientConfigs'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Response Configuration</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='responseConfigs'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Email Templates</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='emailTemplates'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Email Template Beans</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='emailTemplateBeans'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Email Configurations</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='emailConfigs'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Task Process Templates</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='taskProcessTemplates'></td>
			</tr>
			<tr>
				<td class='hunterCachRecord' >Existing Email Template Names</td>
				<td class='hunterCachRecord' ><input type='checkbox' class='kendoCheckBox' data-name='existingTemplateNames'></td>
			</tr>
		</table>
		<button onClick="onClickHunterCacheButton()" id='hunterCacheTabButton' style='margin-top:3px;width:40%;font-size:18px;font-weight: bolder;margin-left:30%;float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);' class="k-button" > Refresh Cache </button>
	</div>
     
</div>


<script src="/Hunter/static/resources/scripts/plain/hunterCacheTab.js"></script>

<%@include file="../common/HeaderFile.jsp"%>

<script src="<c:url value='/static/resources/scripts/plain/kendoHelper.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/model/hunterAdminClientHuntler.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/model/taskProcessProgress.js'/>"></script>

<div id="processedTaskTxtMessageViewTemplate" class="k-block k-info-colored" style="width:900px;margin:0 auto;padding:4px;background-color:rgb(215,234,240);height:375px;border-radius:4px;border:1px solid rgb(175,202,210);">
	<div id="processedTaskTxtMsgDet1"  style="float:left;width:55%;min-height:298px;color:#0A2C2E;font-size:13px;padding-left:10px;background-color:#E6F7FF;margin-left:2.5%;margin-top:16px;border-radius:4px;border:1px solid rgb(175,202,210);" >
		<table id="taskTxtMsgViewTable"  style="table-layout:fixed;width:90%;margin-left:5%;margin-top:4%;" >
			<tr>
				<td>Send Date</td>
				<td>12/31/1969</td>
			</tr>
			<tr>
				<td>Task Type</td>
				<td>12/31/1969</td>
			</tr>
			<tr>
				<td>Service Provider</td>
				<td>Safaricom</td>
			</tr>
			<tr>
				<td>Message Owner</td>
				<td>Hunter Admin</td>
			</tr>
			<tr>
				<td>Message Status</td>
				<td>Processed</td>
			</tr>
			<tr>
				<td>Created On</td>
				<td>12/31/1969</td>
			</tr>
			<tr>
				<td>Created By</td>
				<td>Hunter Admin</td>
			</tr>
			<tr>
				<td>Modified On</td>
				<td>12/31/1969</td>
			</tr>
			<tr>
				<td>Modified By</td>
				<td>Hunter Admin</td>
			</tr>
		</table>
	</div>
	<div  id="processedTaskTxtMsgDet2" style="float:left;width:35%;min-height:298px;color:#0A2C2E;font-size:13px;padding-left:10px;background-color:#E6F7FF;margin-left:2.5%;margin-top:16px;border-radius:4px;border:1px solid rgb(175,202,210);" >
		<table id="taskTxtMsgTextTable"  style="table-layout:fixed;width:90%;margin-left:5%;margin-top:4%;" >
			<tr>
				<td style="height:170px;border-bottom:2px solid #CFE4EF;font-size:15px;" >
					Hon. Uhuru Kenyatta is coming to Chesoen on
					Saturday 2nd. Please come welcome him. He has promised to
					build a dispensary for us.<br/>
					From: Uhuru Kenyatta
				</td>
			</tr>	
			<tr>
				<td  style="height:50px;"  >
					<table id="taskTxtMsgTextTable"  style="table-layout:fixed;width:96%;margin-left:0%;margin-top:4%;">
						<tr>
							<td style="height:20px;font-weight:bold;" >Character Count :</td>
							<td>20</td>
						</tr>
						<tr>
							<td style="height:20px;font-weight:bold;">Cost Per Message :</td>
							<td>Ksh : 2.00	</td>
						</tr>
						<tr>
							<td style="height:20px;font-weight:bold;">Total Receivers :</td>
							<td>100,000</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<div style="width:94%;min-height:25px;padding-left:10px;margin-top:310px;" >
		<table>
			<tr>
				<td><button onClick="hunterAdminClientUserVM.expandHunterUserGrid()" style="float:left;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);width:200px;margin-left:320px;" class="k-button" >Close</button></td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
	$("document").ready(function(){
		//kendo.ui.progress($("#processedTaskTxtMsgLoadIcon"), true);
	});
</script>


</body>
</html>
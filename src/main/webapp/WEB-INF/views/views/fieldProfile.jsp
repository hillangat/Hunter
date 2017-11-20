<%@include file="../common/JSTL_tags.jsp"%>
<!DOCTYPE html>
<html style="font-size:12px;" >
<head>


<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value='/static/resources/scripts/plain/fieldProfile.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/plain/notify.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/static/resources/css/fieldProfile.css'/>" >
<script src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.bootstrap.css" />
 
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<script src="<c:url value='/static/resources/scripts/plain/jqueryForm.js'/>"></script>
<script src="<c:url value='/static/resources/scripts/plain/jQueryValidate.js'/>"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<title>Hunter Officer Profile</title>
</head>

<div id="fieldProfileContainer" style="margin-top:1%;font-size:0.9em;"  > 

<div style="height:20%;width:100%;text-align: center;" >
<span style="margin-right:-1050px;margin-top:-10%;" ><a style="color:white;" id="logoutId" href="//hunter/login/logout" >Logout</a></span>
<table style="table-layout: fixed;width:98%;margin-left:1%;" >
	<tr>
		<td  style="width:34%;">
			<div class="profileDetDivs" >
				<table style="font-size:16px;" class="profileDetTables"  >
					<tr>
						<td>Total Contacts  </td>
						<td id="fieldUserTotalContacts" ></td>
					</tr>
					<tr>
						<td>Verified Contacts  </td>
						<td id="fieldUserVerifiedContacts" ></td>
					</tr>
					<tr>
						<td>Total Payout  </td>
						<td>Ksh <span id="fieldUserTotalPayout" ></span></td>
					</tr>
					<tr>
						<td>Available Payout  </td>
						<td> Ksh <span id="fieldUserAvailablePayout" >0.00</span></td>
					</tr>
					<tr>
						<td>Email </td>
						<td  id="fieldUserEmail"  ></td>
					</tr>
				</table>
			</div>
		</td>
		<td  style="width:28%;" >
			<div class="profileDetDivs" style="padding-bottom:10px;" >
				<table style="table-layout: fixed; margin: 0 auto;" >
					<tr>
						<td>
							<img
								id="fielProfilePic" class="fielProfilePicClass" 
								src="https://ikonmania.files.wordpress.com/2014/03/profile-pictures209.jpg" 
								alt="Profile Pic" 
								width="100" 
								height="100"
								style="margin:0 auto;"  
							>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td>
										<span href="#" style="font-size: 18px;text-shadow: 2px 2px #009AB2;" id="fieldUserFullName">Kip Langat</span>
									</td>
									<td><center><img width="30px" onclick="populatePopupForParams('0_EditUserProfile')" height="30px" style="border-radis:50%;cursor:pointer" src="http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/black-white-pearls-icons-business/078108-black-white-pearl-icon-business-pencil7-sc49.png"  /></center></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</td>
		<td style="width:34%;">
			<div class="profileDetDivs" style="width:100%;"  >
				<table style="font-size:16px;height: 100%;" class="profileDetTables"  >
					<tr>
						<td>Phone </td>
						<td  id="fieldUserPhone"  ></td>
					</tr>
					<tr>
						<td>Country </td>
						<td  id="fieldUserCountry"  ></td>
					</tr>
					<tr>
						<td>County </td>
						<td  id="fieldUserCounty"  ></td>
					</tr>
					<tr>
						<td>Constituency </td>
						<td  id="fieldUserConstituency"  ></td>
					</tr>
					<tr>
						<td>Ward </td>
						<td  id="fieldUserWard"  ></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
</div>


<div id="fieldProfileRawReceiversDiv" style="position:absolute;max-height: 80%;width:98%;">

<table id="fieldProfileDataTable" class="display" style="color:black;border-radius:4px;width:96%;" >
	<thead>
            <tr>
                <th>ID</th>
                <th>Contact</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Country</th>
                <th>County</th>
                <th>Constituency</th>
                <th>Ward</th>
                <th>Verfied</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
        </thead>
</table>


</div>





</div>
















<div id="hunterFieldTemplatesContainer" style="display:none;" >

	<div id="hunterFieldPopupButts" >
		<table data-name="popup-action-button" style="width:70%;margin-left:15%;" >
			<tr>
				<td id="affirmPopupAction" ></td>
				<td><center><a onmouseover="onmouseenterCloseA(this)" onmouseout="onmouseoutCloseA(this)" id='closeHunterPopup' href="#" onClick="closeHunterPopup1()" class="ui-btn ui-icon-forbidden ui-btn-icon-left" style="background-color:#DFF2F5;min-width:200px;border:1px solid #93CADB;border-radius:5px;color:#0E4244;" >Close</a></center></td> 
			</tr>
		</table>
	</div>
	<div id="hunterFieldEditTemplateContainer" >
		<table style="width:80%;margin-top:30px;margin-bottom:30px;margin-left:10%;table-layout: fixed;font-size: 13px;color:#11484E;font-weight: bold;" >
			<tr data-tr='contactType' >
				<td>Contact Type</td>
				<td>
					<select data-name="contactTypeInput"  class="hunterInputText" style="width:90%;" >
					  <option value="text">Phone Number</option>
					  <option value="email">Email Address</option>
					</select>
				</td>
			</tr>
			<tr data-tr='contact' >
				<td>Contact*</td>
				<td><input data-name="contactInput" class='hunterInputText' placeholder="Enter Contact"  type="text"  /> </td> 
			</tr>
			<tr data-tr='firstName' >
				<td>First Name*</td>
				<td><input data-name="firstName" class='hunterInputText' placeholder="Enter First Name"  type="text"  /> </td>
			</tr>
			<tr data-tr='lastName' >
				<td>Last Name</td>
				<td><input data-name="lastName" class='hunterInputText' placeholder="Enter Last Name"  type="text"  /> </td>
			</tr>
			<tr>
				<td>Country*</td>
				<td>
					<select data-name="countryInput" class="hunterInputText" style="width:90%;" >
					  <option value="1">Kenya</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>County*</td>
				<td>
					<select data-name="countyInput" class="hunterInputText" style="width:90%;" >
					</select>
				</td>
			</tr>
			<tr>
				<td>Constituency*</td>
				<td>
					<select data-name="consInput" class="hunterInputText" style="width:90%;" >
					</select>
				</td>
			</tr>
			<tr>
				<td>Ward*</td>
				<td>
					<select data-name="wardInput" class="hunterInputText" style="width:90%;" >
					</select>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="notificationContainer" >
		<div id="hunterFieldNotification" >
			
		</div>
	</div>
	
	<div id="importNewContainerTmplt" >
		<div data-name="importNewContainer"  style="width:80%;margin-left:10%;" >
			<table id="importContainerRefreshTable" style="display:none;width: 30%;margin-left:35%;font-size: 22px;" >
				<tr>
					<td>Refreshing...</td>
					<td style="width:15%;height: 40px;text-align: left;"  data-name='contactsProgressIcon'><span data-name="" ><img  src="<c:url value='/static/resources/images/refreshing_spinner_new.gif'/>" width="30px" height="30px" data-name="progressIconImg" ></span></td>
				</tr>
			</table>
			<form data-name="importNewContactForm" action="${pageContext.request.contextPath}/rawReceiver/action/rawReceiver/import/rawReceiver" method="POST" style="width:98%;margin-left:1%;"  enctype="multipart/form-data"  >
				<h3 style="width:80%;margin-left:10%;" >Please upload your file below.</h3>
				<h5 style="width:80%;margin-left:10%;color:brown;margin-bottom:30px;" >Note : Only .xlsx and .xls files are allowed.</h5> 
				<input style="width:80%;margin-left:10%;margin-bottom:30px;border:1px solid #84BDC6;padding:15px;border-radius:5px;" onChange="updateFileSelected" id="importNewContactFile"  name="importNewContactFile" type="file" >
				<table style="table-layout: fixed;width:100%;margin-bottom:30px;" >
					<tr>
						<td><button id="importNewContainerSubButt" type="submit" class="ui-btn ui-icon-carat-u ui-btn-icon-left" style="width:90%;margin-left:5%;border-radius:5px;color:#0089FF;background-color:#DFF2F5;border:1px solid #93CADB;" >Submit</button></td>
						<td><a  id="importNewContainerCloseButt" style="width:90%;margin-left:5%;border-radius:5px;background-color:#DFF2F5;border:1px solid #93CADB;color:#0089FF;" href="#" onClick="closeHunterPopup1()" class="ui-btn ui-icon-forbidden ui-btn-icon-left">Close</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<div id="progressTemplateContainer" >
		<span data-name="" ><img  src="<c:url value='/static/resources/images/refreshing_spinner_gif.gif'/>" width="15px" height="15px" data-name="progressIconImg" ></span>
		<span data-name="" ><img  src="<c:url value='/static/resources/images/tick.png'/>" width="15px" height="15px" class="progressIconImg" data-name="successIconImg" ></span>
		<span data-name="" ><img  src="<c:url value='/static/resources/images/tick.png'/>" width="15px" height="15px" class="progressIconImg" data-name="failedIconImg" ></span>
		<div data-name='loadProfileAndReceivers' >
			<span style="width:10%;margin-left: 45%;text-align: center;" data-name="numberOfSecondsTaken" >0 seconds</span> 
			<table style="width:60%;margin-left:20%;padding:20px;">
				<tr><td>
					<table style="width:100%;table-layout: fixed;font-size:20px;" >
						<tr>
							<td style="width:30%;height: 40px;border-bottom:1px solid #A6D3D7;" >Loading Profile</td>
							<td style="width:15%;height: 40px;border-bottom:1px solid #A6D3D7;text-align: left;text-align: left;"  data-name='profileProgressIcon'><span data-name="" ><img  src="<c:url value='/static/resources/images/refreshing_spinner_new.gif'/>" width="30px" height="30px" data-name="progressIconImg" ></span></td>
							<!-- <td style="width:30%;height: 40px;"  data-name='profileDataError' style='color:red;' ></td> -->
						</tr>
						<tr style="display:display;" data-name='loadContactsDataDisplay' >
							<td style="width:30%;height: 40px;" >Loading Contacts</td>
							<td style="width:15%;height: 40px;text-align: left;"  data-name='contactsProgressIcon'><span data-name="" ><img  src="<c:url value='/static/resources/images/refreshing_spinner_new.gif'/>" width="30px" height="30px" data-name="progressIconImg" ></span></td>
							<!-- <td style="width:30%;height: 40px;"  data-name='contactsDataError'  style='color:red;'></td> -->
						</tr>
					</table>
				</td></tr>
				<tr><td style="height: 50px;" >
					<div style="position:relative;width:90%;margin-left:5%;" data-name="progressbar"><div style="width:40%;margin-left:-15%;font-size:14px;" data-name="progressBarLabel" class="progress-label">Completed!</div></div>
				</td></tr>
			</table>
		</div>
	</div>
	
	<div id="refreshPopupContainer" >
		<table style="width: 30%;margin-left:35%;font-size: 22px;" >
			<tr>
				<td>Refreshing...</td>
				<td style="width:15%;height: 40px;text-align: left;"  data-name='contactsProgressIcon'><span data-name="" ><img  src="<c:url value='/static/resources/images/refreshing_spinner_new.gif'/>" width="30px" height="30px" data-name="progressIconImg" ></span></td>
			</tr>
		</table>
	</div>
	
	
</div>

<div class="popup" data-popup="popup-1">
    <div class="popup-inner">
        <a id="popup-close-button" class="popup-close" data-popup-close="popup-1" href="#">x</a>
    </div>
</div>

<div id="hunterEditUserProfileCont" style="display:none;" > 
	<div id="hunterEditUserProfileDiv"  style="width:60%;margin-left:20%;" >
		<table style="width:100%;margin-top:30px;margin-bottom:30px;table-layout: fixed;font-size: 16px;color:#11484E;font-weight: bold;" >
			<tr>
				<td style="text-align: center;">
					<img 
						id="fielProfilePic_" class="fielProfilePicClass"
						data-name='editProfileDataPhotoPreview' 
						src="https://ikonmania.files.wordpress.com/2014/03/profile-pictures209.jpg" 
						alt="Profile Pic" 
						width="100" 
						height="100"
						style="margin:0 auto;"  
					>
				</td>
			</tr>
			<tr>
				<td  style="text-align: center;" >
					<form  accept-charset="utf-8" data-name='uploadProfilePhotoForm' action="${pageContext.request.contextPath}/rawReceiver/action/upload/profilePhoto" enctype="multipart/form-data" method="POST" style="margin-top:20px;width:98%;margin-left:1%;" >
  						<table style="width:100%;">
  							<tr>
  								<td><input onChange="onChangeProfilePhoto(this)" name="editProfilePhotoInput" type="file" style="width:98%;border:1px solid #1DB6C7;border-radius:3px;" ></td>
  								<td><a onClick='resetEditProfilePhoto1(this)' href="#" class="ui-btn ui-corner-all ui-icon-delete ui-btn-icon-notext"></a></td>
  							</tr>
  							<tr  style="display:none;"><td><button name="submitEditProfilePhoto" type="submit"  ></button></td></tr>
  						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
</div>


<div id="hunterMessageTemplateContainer" >
	<div id="hunterMessageTemplate" >
		
	</div>
</div>


<script type="text/javascript">
	function resetEditProfilePhoto1(preview){
		var input = $("#hunterFieldPopupContainer form input[name='editProfilePhotoInput']"),
			profPic = $( fielProfilePic ),
			profUrl = $( profPic ).attr('src'), 
			target = $("#hunterFieldPopupContainer img[data-name='editProfileDataPhotoPreview']");
		$( input ).val(''); 
		$( target ).attr("src", profUrl);
		isProfileUploaded = false;
	}
	function onChangeProfilePhoto(input){
		isProfileUploaded = true;
		var target = $("#hunterFieldPopupContainer img[data-name='editProfileDataPhotoPreview']");
		readURL(input, target);
	}
</script>


</body>
</html>
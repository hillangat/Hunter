<%@include file="//WEB-INF/views/common/JSTL_tags.jsp"%>

<!DOCTYPE html>
<html style="font-size:13px;" >
<head>
<meta charset="utf-8">

<link rel="stylesheet" href="http://cdn.kendostatic.com/2015.3.930/styles/kendo.common.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2015.3.930/styles/kendo.default.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2015.3.930/styles/kendo.dataviz.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2015.3.930/styles/kendo.dataviz.default.min.css">

<link rel="stylesheet" href="http://localhost:8080/Hunter/static/resources/kendo/kendo.custom.css">

<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://cdn.kendostatic.com/2015.3.930/js/kendo.all.min.js"></script>


<%-- <script src="<c:url value='/static/resources/scripts/fancyScript.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/static/resources/css/fancyCss.css'/>">
 --%>
 <script src="<c:url value='/static/resources/scripts/plain/kendoHelper.js'/>"></script>
 <script src="<c:url value='/static/resources/scripts/plain/hunter.js'/>"></script>
 <script src="<c:url value='/static/resources/scripts/plain/hunterConstants.js'/>"></script>
 <link rel="stylesheet" href="<c:url value='/static/resources/css/hunter.css'/>">
 <link rel="stylesheet" href="<c:url value='/static/resources/css/kendoGen.css'/>">
 
  <style>
 	.errorBlock{
 		color:red;
 		width:28.5%;
 		margin-left:34%;
 		font-size:17px;
 		border:1px solid #769E8B;
 		border-radius:4px;
 		margin-top:-7%;
 		position:fixed;
 		padding:10px;
 	}
 	.button{
 		
 	}
 	#spinnerTable{
 		font-size:16px;
 		margin-top:20%;
 		margin-left:auto;
 		margin-right:auto;
 	}
 </style>
 
 <script type="text/javascript">
    var 
    baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/",
    spinnerUrl = baseUrl + "static/resources/images/refreshing_spinner_new.gif";
 	$("document").ready(function(){
 		$("#loginSpinnerImg").attr('src', spinnerUrl);
 		$("#hunterLoginForm").bind("submit", function(){
 			$(".errorBlock").css({'display':'none'});
 			$("#loginCover").css({'display':'none'});
 			$("#spinnerTable").css({'display':''}); 
 		});
 	});
 	
 </script>
 
 </head>
 <body>
 
 <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
		<div class="errorBlock" >
			Failed To Login!<br/>
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
 
<div id="loginCover" >
	
	<form id="hunterLoginForm" method="POST" action="<c:url value='${pageContext.request.contextPath}/j_spring_security_check'/>" >
		<sec:csrfInput />
		<table>
			<tr>
				<td>User Name : </td>
				<td><input class="hunterInput" placeHolder="Enter user name..." id="hunterUserName" name="j_username" type="text"/></td>
				<td id="hunterUserNameError" class="errors" ></td>
			</tr>
			<tr>
				<td>Password   : </td>
				<td><input class="hunterInput" placeHolder="Enter Password..." id="hunterPassword" name="j_password" type="password"/></td>
				<td id="hunterPasswordError" class="errors" ></td>
			</tr>
		</table>
		<br/>
		<table>
			<tr>
				<td> </td>
				<td><button class="button" name="submit" type="submit" name="hunterLoginPassword">Login</button></td> 
			</tr>
		</table>
	</form>
	
</div>

	<table id="spinnerTable" style="display:none;" >
		<tr>
			<td style="width:60px;text-align:center;" ><img id="loginSpinnerImg" width="30px" height="30px" src=""></td>
		</tr>
		<tr>
			<td style="font-size:16px;width:100px;text-align:center;" >Please wait...</td>
		</tr>
	</table>


</body>
</html>

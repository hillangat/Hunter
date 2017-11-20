<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successfully Logged Out</title>

<style type="text/css">
	.logoutBox{
		color:green;
		width:40%;
		margin-left:30%;
		background-color : #F3F9FC;
		border:1px solid #91B1C1;
		margin-top:20%;
		border-radius:4px;
		height:4%;
		text-align: center;
	}
</style>

</head>
<body>


<div class="logoutBox" >
	
	<h2 style="color:red;">You session has expired!</h2>
	<h3 style="color:blue;" >Please click <a  style="color:red;" href="${pageContext.request.contextPath}/">here</a> to log back in.</h3>
	
</div>



</body>
</html>
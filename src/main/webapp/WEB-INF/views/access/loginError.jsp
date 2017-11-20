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
 <script src="<c:url value='/static/resources/scripts/plain/hunter.js'/>"></script>
 <script src="<c:url value='/static/resources/scripts/plain/hunterConstants.js'/>"></script>
 <link rel="stylesheet" href="<c:url value='/static/resources/css/hunter.css'/>">
 <link rel="stylesheet" href="<c:url value='/static/resources/css/kendoGen.css'/>">
 
 </head>
 <body>

<div id="loginCover">
	
	<h3 style="color:red;" >You are not logged in. Please login and try again.</h3>
	<a href="/">Please login</a> 
	
</div>

</body>
</html>

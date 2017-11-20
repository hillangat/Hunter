
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tech Masters Hunter Home</title>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<style>
	
	body{
		width:98%;
		margin-left:1%;
		background-color:#F9FEFF;
	}

	#hunterCircularHomeWidget{
		background-size: cover;
	}
	
	#hunterCircularHomeWidgetImg{
		user-select: none;
        -moz-user-select: none;
        -khtml-user-select: none;
        -webkit-user-select: none;
        -o-user-select: none;
	}
	
</style>

<script type="text/javascript">

	$("document").ready(function(){
		setUrl();
		setNavUrl();
		rotate();
	});
	
	function rotate(){
		
		var interval = null;
	    var counter = 0;
	    var $this = $("#hunterCircularHomeWidgetImg");
	    clearInterval(interval);
	 
	    interval = setInterval(function(){
	    	if( counter == -360 ) counter = 0;
	            counter -= 1;
	            $this.css({
	                MozTransform: 'rotate(-' + -counter + 'deg)',
	                WebkitTransform: 'rotate(' + -counter + 'deg)',
	                transform: 'rotate(' + -counter + 'deg)'
	            });
	    }, 20);
	}
	
	function setNavUrl(){
		var url = location.protocol + "//" + location.hostname +
	       (location.port && ":" + location.port) + "/" + "Hunter/hunter/tasks/home";
		$("#hunterNavA").prop("href", url);
	}
	
	function setUrl(){
		var url = location.protocol + "//" + location.hostname +
	       (location.port && ":" + location.port) + "/" + "Hunter/static/resources/images/Earth-icon.png";
		$("#hunterCircularHomeWidgetImg").prop("src", url);
	}

</script>

</head>
<body>

<h2 style='100%;text-align:center;margin : 0 auto;margin-top:10%;color:green;font-size:22px;'>Sucess</h2>

<div id="hunterCircularHomeWidget" style="width:200px;height:200px;margin : 0  auto;margin-top:5%; border:1px solid #BCDEEC; background-color:#E5F5FC;border-radius:100%;" >
	<img id='hunterCircularHomeWidgetImg' src="http://www.happydaycatering.com/wp-content/uploads/2012/03/Earth-icon.png" style="width:200px;height:200px;" >
</div>

<h2 style='100%;text-align:center;margin : 0 auto;margin-top:10px;	text-shadow: 1px 1px gray'><a id="hunterNavA" style="text-decoration:none;" href="" >Tech Masters Services</a><sup>@</sup></h2>




<a href="http://localhost:8080/Hunter/jquery/home" style="margin-top:60%;">JQuery Home</a> 

</body>
</html> 
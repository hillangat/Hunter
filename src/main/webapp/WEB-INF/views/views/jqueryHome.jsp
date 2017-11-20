<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hunter Tasks</title>



<script type="text/javascript" src="<c:url value='/static/resources/fancybox/lib/jquery-1.10.1.min.js'/>"></script>
<!-- Add mousewheel plugin (this is optional) -->
<script type="text/javascript" src="<c:url value='/static/resources/fancybox/lib/jquery.mousewheel-3.0.6.pack.js'/>"></script>
<!-- Add fancyBox main JS and CSS files -->
<script type="text/javascript" src="<c:url value='/static/resources/fancybox/source/jquery.fancybox.js?v=2.1.5'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/resources/fancybox/source/jquery.fancybox.css?v=2.1.5'/>" media="screen" />
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css" href="<c:url value='/static/resources/fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.5'/>" />
<script type="text/javascript" src="<c:url value='/static/resources/fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.5'/>"></script>
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css" href="<c:url value='/static/resources/fancybox/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7'/>" />
<script type="text/javascript" src="<c:url value='/static/resources/fancybox/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7'/>"></script>
<!-- Add Media helper (this is optional) -->
<script type="text/javascript" src="<c:url value='/static/resources/fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.6'/>"></script>



<!--  For videos -->
<script src="http://www.youtube.com/player_api"></script>


</head>
<body>

<h1>This is the home page for jquery stuff!!</h1>

<a class="fancybox" href="http://fancyapps.com/fancybox/demo/1_b.jpg"><img src="http://fancyapps.com/fancybox/demo/1_s.jpg" alt=""/></a>
<a class="fancybox" href="http://fancyapps.com/fancybox/demo/2_b.jpg"><img src="http://fancyapps.com/fancybox/demo/2_s.jpg" alt=""/></a>



<script type="text/javascript">
$(".fancybox")
.attr('rel', 'gallery')
.fancybox({
    padding : 0
});


//Launch fancyBox on first element
$(".fancybox").eq(0).trigger('click');
</script>

</body>
</html>
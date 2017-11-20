<%@include file="../common/JSTL_tags.jsp"%>
<!DOCTYPE html>
<html style="font-size:13px;" >
<head>


<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="<c:url value='/static/resources/scripts/plain/fieldProfileRegions.js'/>"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

<title>Hunter Officer Profile</title>

</head>



<body>






<div id="hunterFieldPopupContainer" > 
	
	<table>
		<tr>
		<td>Country</td>
		<td>
			<select data-name="countryInput" class="hunterInputText" style="width:90%;" >
			  <option value="Bomet">Bomet</option>
			  <option value="Kisii">Kisii</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>County</td>
		<td>
			<select data-name="countyInput" class="hunterInputText" style="width:90%;" >
			  <option value="Bomet">Bomet</option>
			  <option value="Kisii">Kisii</option>
			</select>
		</td>
	</tr>
	</table>
		
	
</div>














</body>
</html>
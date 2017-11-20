<%@include file="../common/HeaderFile.jsp"%>

<script type="text/javascript"> 
registerNavigation("My Hunter", "My Account");
</script>

<script src="<c:url value='/static/resources/scripts/plain/kendoHelper.js'/>"></script>

<style>
	.Row{
	    display: table;
	    width: 100%; /*Optional*/
	    table-layout: fixed; /*Optional*/
	    border-spacing: 3px; /*Optional*/
	    min-height:800px;
		}
	.Column{
	    display: table-cell;
	    background-color: #ECF7F9; /*Optional*/
	    min-height:800px;
	    border-radius:5px;
	    border : 1px solid #D6EAEE;	
	}
	.Column:hover{
		background-color: #E7F2F5; /*Optional*/
	}
</style>

<div class="Row">
    <div class="Column" ></div>
    <div class="Column"></div>
    <div class="Column"></div>
</div>

</body>
</html>
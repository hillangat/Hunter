<div style="with:100%;min-height:250px;" id="hunterUserDetailsTab" >
	
	<input data-bind="value:userDetailsJson.firstName" type="text" class="k-textbox" />

</div>
<script src="http://localhost:8080/Hunter/static/resources/scripts/model/hunterUserDetails.js"></script>
<script type="text/javascript">
$("document").ready(function(){
	hunterUserDetailsVM.init(); 
});	
</script>
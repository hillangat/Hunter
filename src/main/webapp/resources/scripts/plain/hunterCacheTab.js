
var selCacheData = {};
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

$("document").ready(function(){
	
	$(".kendoCheckBox").bind("change", function(){
		var cacheName = $(this).attr('data-name');
		if( selCacheData[cacheName] == null ){
			selCacheData[cacheName] = cacheName;
		}else{
			delete selCacheData[cacheName];
		}
		$("#checkAllCheckBoxes").prop("checked", false);
	});
	
	$("#checkAllCheckBoxes").bind("click", function(){
		var checked = $(this).prop("checked");
		$(".kendoCheckBox").prop("checked", checked);
		if( checked ){
			$( ".kendoCheckBox" ).each(function( index ) {
				var dataName = $(this).attr('data-name');
				selCacheData[dataName] = dataName;
			});
		}else{
			selCacheData = {};
		}
	});
	
});

function placeProgressIcon(){
	var iconStage = "<div id='hunterCacheProgIcon' style='width:200px;height:200px;margin-top:-15%;position:relative;margin-left:45%;background-color:#F3F9FC;'></div>";
	$("#hunterCacheFunctions").append(iconStage);
	$("#hunterCacheProgIcon").html('<img src="' + baseUrl + 'static/resources/images/refreshing_spinner_new.gif" width="100px" height="100px" style="margin-left:50px;margin-top:50px;"   />');
	$("#hunterCacheProgIcon").append("<h2 style='text-align:center;with:200%;color:#4B557C;' >Refreshing...</h2>");
	disableButtonsAndInputs();
}

function removeProgressIcon(){
	$("#hunterCacheProgIcon").fadeOut(2000,function(){
		$(this).remove();
		enableButtonsAndInputs();
	});
}

function disableButtonsAndInputs(){
	$("#hunterCacheTabButton").attr('disabled','disabled');
	$("#hunterCachFunctionsTable tr td input").attr('disabled','disabled');
	$("#hunterCacheFunctions td").css({"opacity" : "0.2"});
}

function enableButtonsAndInputs(){
	$("#hunterCacheTabButton").removeAttr( "disabled" );
	$("#hunterCachFunctionsTable tr td input").removeAttr( "disabled" );
	$("#hunterCacheFunctions td").css({"opacity" : "0.99999999999"});
}

function onClickHunterCacheButton(){
	try{
		var len = Object.keys(selCacheData).length;
		if( len != 0 ){ 
			placeProgressIcon();
			setTimeout(function(){
				var url = HunterConstants.getHunterBaseURL("/cache/action/refreshCaches");
				kendoKipHelperInstance.ajaxPostData(JSON.stringify(selCacheData), "application/json", "json", "POST", url, "afterRefreshingHunterCache");
			}, 1500);
		}else{
			kendoKipHelperInstance.showErrorNotification("Please select at least one cache name!");
		}
	}catch(error){
		kendoKipHelperInstance.showErrorNotification("An error occurred : " + error.message);
	}
}

function afterRefreshingHunterCache(data){
	try{
		
		data = $.parseJSON(data);
		var status = data.status,
			message = data.message;
		if( status != null && status === HunterConstants.STATUS_SUCCESS ){
			$("#hunterCacheProgIcon").html("<h2 style='margin-top:30%;margin-left:0 auto;color:green;'>"+ message +"</h2>");
			//kendoKipHelperInstance.showSuccessNotification(message);
		}else{
			$("#hunterCacheProgIcon").html("<h2 style='margin-top:30%;margin-left:0 auto;color:red;'>"+ message +"</h2>");
			//kendoKipHelperInstance.showErrorNotification(message);
		}
		setTimeout(removeProgressIcon(), 3000);
		
	}catch(error){
		$("#hunterCacheProgIcon").html("<h2 style='margin-top:30%;margin-left:0 auto;color:red;'>"+ error.message +"</h2>");
		/*kendoKipHelperInstance.showErrorNotification("An error occurred : " + error.message);*/
		setTimeout(removeProgressIcon(), 3000);
	}
	
}





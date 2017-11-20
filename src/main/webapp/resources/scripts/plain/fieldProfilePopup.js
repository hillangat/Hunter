
var FieldPopup  = null;

$("document").ready(function(){
	

	FieldPopup = {
			
		tempId : null,
		data : null,
		
		
		popupContainer : function(){
			var html = $("<div id='hunterFieldDefaultTemplate' ><h1>Hey</h1></div>");
			return html;
		},  
		getReadyTemplate : function(tempId, data){
			var html = $("#" + tempId);
		},
		showPopup : function(tempId){
			$("body").append($(this.popupContainer()));
			$("#hunterFieldDefaultTemplate").css({"display":""}); 
			//this.driveCenter();
		},
		hidePopup : function(){
			
		},
		driveCenter : function(){
			$("#hunterFieldDefaultTemplate").animate({
				"margin-left":"40%",
				"margin-top":"200px",
				"min-width":"20%",
				"min-height":"15%"
			},500);
		},
		getAnimateCss : function(){
			var css =  {
				"margin-left":"40%",
				"margin-top":"200px",
				"min-width":"20%",
				"min-height":"15%"
			};
		}
		
	};
	
	
	
	
});
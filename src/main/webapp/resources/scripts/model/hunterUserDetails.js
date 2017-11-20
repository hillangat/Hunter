var hunterUserDetailsVM = kendo.observable({
	
	userDetailsJson : {"firstName":"Kip"},
	
	
	beforeInit : function(){
		console.log("Getting ready to initialize hunder details VM...");
	},
	init : function(){
		this.beforeInit();
		console.log("Initializing hunder details VM...");
		kendo.bind($("#hunterUserDetailsTab"), hunterUserDetailsVM);
		this.afterInit();
	},
	afterInit : function(){
		console.log("Finishing initializing hunder details VM...");
	},
	showFirstName : function(){
		var fName = this.get("firstName");
		console.log(fName);
	}
	
});
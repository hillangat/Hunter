
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

var taskRegionVM = kendo.observable({
	
	isCountryDropdownEnabled : true, 
	isCountyDropdownEnabled : true,
	isStateDropdownEnabled : false,
	isConstituencyDropdownEnabled : true,
	isConstituencyWardDropdownEnabled : true,
	
	isCountryDropdownVisible : true, 
	isCountyDropdownVisible : true,
	isStateDropdownVisible : false,
	isConstituencyDropdownVisible : true,
	isConstituencyWardDropdownVisible : true,
	
	selCountry : "UNSELECTED", 
	selState : "UNSELECTED", 
	selCounty : "UNSELECTED", 
	selConstituency : "UNSELECTED", 
	selConstituencyWard : "UNSELECTED", 
	hasState : "UNSELECTED",
	
	beforeInit : function(){
		console.log("Before initializing task region VM..");
	},
	init : function(){
		this.beforeInit();
		kendo.bind($("#taskRegionViewCover"), taskRegionVM);
		console.log("initializing task region VM..");
		this.afterInit();
	},
	afterInit : function(){
		console.log("Successfully finished initializing task region VM!!");
		this.get("countryDS").read();
		this.get("countyDS").read();
		this.get("constituencyDS").read();
		this.get("constituencyWardDS").read();
	},
	countryDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: baseUrl + "region/action/countries/read",
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading countries...");
	     }
	}),
	countyDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: function(){
      	    	  var url =  baseUrl + "region/action/counties/read/" + taskRegionVM.get("selCountry");
      	    	  console.log("CountyDS URL >> " + url);
      	    	  return url;
      	      }, 
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading counties...");
	     }
	}),
	constituencyDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: function(){
      	    	  var url = baseUrl + "region/action/constituencies/read/" + taskRegionVM.get("selCounty");
      	    	  console.log("ConstituencyDS URL >> " + url);
      	    	  return url;
      	      },
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading constituencies...");
	     }
	}),
	constituencyWardDS : new kendo.data.DataSource({
        type: "json",
        serverFiltering: true,
        transport: {
       	 read:  {
      	      url: function(){
      	    	  var url = baseUrl + "region/action/constituencyWards/read/" + taskRegionVM.get("selConstituency");
      	    	  console.log("ConstituencyWardDS URL >> " + url);
    	    	  return url;
      	      },
      	      dataType: "json",
      	      method: "POST"
       	 }
        },
        requestStart: function(e) {
	        var type = e.type;
	        if(type === 'read')
	        	console.log("reading constituencywards...");
	     }
	}),
	onChangeCountry : function(e){
		
		taskRegionVM.resetValues(taskRegionVM.get("selCountry"),"UNSELECTED", "UNSELECTED", "UNSELECTED"); 
		taskRegionVM.set("isCountyDropdownEnabled", true);
		
		taskRegionVM.get("countyDS").read();
		taskRegionVM.get("constituencyDS").read();
		taskRegionVM.get("constituencyWardDS").read();
		
	},
	onChangeCounty : function(){
		taskRegionVM.resetValues(taskRegionVM.get("selCountry"),taskRegionVM.get("selCounty"), "UNSELECTED", "UNSELECTED");
		taskRegionVM.set("isConstituencyDropdownEnabled", true);
		console.log("Selected county >> " + taskRegionVM.get("selCounty"));		
		
		taskRegionVM.get("constituencyDS").read();
		taskRegionVM.get("constituencyWardDS").read();
	},
	onChangeConstituency : function(){
		taskRegionVM.resetValues(taskRegionVM.get("selCountry"),taskRegionVM.get("selCounty"), taskRegionVM.get("selConstituency"), "UNSELECTED"); 
		taskRegionVM.set("isConstituencyWardDropdownEnabled", true);
		console.log("Selected constituency >> " + this.get("selConstituency")); 
		
		taskRegionVM.get("constituencyWardDS").read();
	},
	onChangeConsituencyWard : function(){
		taskRegionVM.resetValues(taskRegionVM.get("selCountry"),taskRegionVM.get("selCounty"), taskRegionVM.get("selConstituency"), taskRegionVM.get("selConstituencyWard"));
		console.log("selConstituency >> " + taskRegionVM.get("selConstituencyWard")); 
	},
	resetValues : function(selCountry, selCounty, selConstituency, selConstituencyWard){
		console.log("Resetting values ... ("+ selCountry + "," + selCounty  + "," + selConstituency + "," + selConstituencyWard + ")");
		taskRegionVM.set("selCountry",selCountry);
		taskRegionVM.set("selCounty",selCounty);
		taskRegionVM.set("selConstituency",selConstituency);
		taskRegionVM.set("selConstituencyWard",selConstituencyWard);
	}
	
});

$("document").ready(function(){
	taskRegionVM.init();
});
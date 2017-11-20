
registerNavigation("Regions", "Social Regions");
var kendoKipHelperInstance = new kendoKipHelper();

var SocialRegionModel = kendo.data.Model.define({
	id:"regionId",
	fields : {
		"regionId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
		},
		"regionName" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"population" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"regionDesc" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"countryId" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"countyId" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"consId" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"wardId" : {
			type : "number", validation : {required : true},editable:true, defaultValue:0
		},
		"coordinates" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"assignedTo" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"countryName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"countyName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"consName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"wardName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"cretDate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"createdBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"lastUpdate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		}
	},
	getSocialRegionsDelTemplate : function(){
		
		var id = this.get("regionId"),
			shortName = this.get("regionName"),
			idString = '"'+ id +'",',
			message = '"<u><b>'+ shortName +'</u></b> will be deleted. <br/> Are you sure?"', 
			html = kendoKipHelperInstance.createDeleteButton(false, 'kendoKipHelperInstance.showOKToDeleteItem('+  idString + message + ',"SocialRegionsVM.deleteSocialRegion"' +  ')');

		return html;
	},
	getSocialRegionsUpdateTemplate : function(){
		var 
		id = this.get("regionId"),
		html = kendoKipHelperInstance.createSimpleHunterButton ("pencil",null, "SocialRegionsVM.showPopupForNewSocialRegion("+ id +")") ;
		return html;
	}
});






var SocialRegionsVM = kendo.observable({
	
	isVisible : true,
	isEnabled : true,
	
	assignedToData   : null,
	currentFetchType : null,
	
	regionActionMode : 'create',
	
	countries  		: [],
	counties   		: [],
	constituencies	: [],
	wards      		: [{regionId : 1,regionName:"Chesoen"},{regionId : 2,regionName:"Kapkoros"}],
	
	SocialRegionsDS_ 	: null,
	isEverVisible 		: true,
	selSocialRegionId 	: 0,
	selSocialRegionTemp : null,
	selSocialRegion	: {
		"regionId"			:0,		"regionName"		:null,	"regionDesc"		:null, "population"			:0,
		"countryId"			:0,		"countyId"			:0,		"consId"			:0, 	"wardId"			:0,
		"countryName"		:null,	"countyName"		:null,	"consName"			:null,	"wardName"			:null,
		"coordinates"		:null,	"assignedTo"		:null,	"cretDate"			:null,	"createdBy"			:null,
		"lastUpdate"		:null,	"lastUpdatedBy"		:null
	},
	resetSocialRegionValues : function(){
		this.set("selSocialRegion",
			{
				"regionId"			:0,		"regionName"		:null,	"regionDesc"		:null, "population"			:0,
				"countryId"			:0,		"countyId"			:0,		"consId"			:0, 	"wardId"			:0,
				"countryName"		:null,	"countyName"		:null,	"consName"			:null,	"wardName"			:null,
				"coordinates"		:null,	"assignedTo"		:null,	"cretDate"			:null,	"createdBy"			:null,
				"lastUpdate"		:null,	"lastUpdatedBy"		:null
			} 
		); 
	},
	beforeInit : function(){
		console.log("Before initializing social regions VM...");
		this.set("SocialRegionsDS_",SocialRegionsDS);
		this.resetSocialRegionValues();
	},
	init : function(){
		console.log("Initializing social regions VM...");
		this.beforeInit();
		kendo.bind($("#hunterSocialRegionsContainer"), SocialRegionsVM);
		this.afterInit();
	},
	afterInit : function(){
		console.log("Finishing up initializing social regions VM...");
		this.loadAssignableUsers();
		this.set("currentFetchType", "Country"); 
		var reqData = {"regionLevel":"Country", "forRegionId" : 0};
		this.fetchRegionData( reqData );
	},
	loadAssignableUsers : function(){
		console.log("Loading users...");
		var url = HunterConstants.getHunterBaseURL("social/action/region/getAssignableHunterUsers");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "SocialRegionsVM.afterLoadAssignableUsers");
	},
	afterLoadAssignableUsers : function(data){
		console.log(JSON.stringify( data ));
		data = $.parseJSON(data);
		SocialRegionsVM.set("assignedToData",data);
	},
	deleteSocialRegion : function(id){
		
		kendoKipHelperInstance.closeHelperKendoWindow();
		
		var 
		data = {"regionId" : id},
		url = HunterConstants.getHunterBaseURL("social/action/regions/delete");
		data = JSON.stringify(data);
		
		kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url , "SocialRegionsVM.afterDeletingSocialRegion");
	},
	afterDeletingSocialRegion : function(data){
		data = $.parseJSON(data);
    	if(data != null){
    		var status = data.status;
    		var message = replaceAll(data.message, ",", "<br/>");
    		if( status != null && HunterConstants.STATUS_SUCCESS === status ){
    			kendoKipHelperInstance.showSuccessNotification( message );
    			SocialRegionsVM.get("SocialRegionsDS_").read(); 
    		}else{
    			kendoKipHelperInstance.showSimplePopup("Error Deleting User Role", "<span style='color:red' >"+ message +"</span>");
    		}
    	}
	},
	displayPopup : function(){
		var html = $("#socialRegionsPopupTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(html, "New Social Region");
		kendo.bind($("#socialRegionPopupCover"), SocialRegionsVM);
		console.log("................... done displaying..............."); 
	},
	showPopupForNewSocialRegion : function(argument){
		
		this.resetSocialRegionValues();
		
		var 
		argStr = (argument +  "").trim(),
		selSocialRegion_ = null;
		
		SocialRegionsVM.set("selSocialRegionId", argStr === "0" ?  0 : argument ); 
		
		if( argStr !== "0" )  {
			console.log("Populating values to region shell..."); 
			selSocialRegion_ = $("#hunterSocialRegionsGrid").data("kendoGrid").dataSource.get(argument);
			SocialRegionsVM.set("selSocialRegion", selSocialRegion_);
			this.set("regionActionMode", "update"); 
		}else{
			this.set("regionActionMode", "create");
		}
		
		var dfd = $.Deferred();
		dfd.done(SocialRegionsVM.onChangeCountry(),SocialRegionsVM.onChangeCounty(), SocialRegionsVM.onChangeConsituency(),SocialRegionsVM.displayPopup());
	},
	onChangeCountry : function(){
		
		var countryId = this.get("selSocialRegion").countryId;
		this.set("currentFetchType", "County"); 
		var reqData = {"regionLevel":"County", "forRegionId" : countryId};
		
		this.set("counties", []);
		this.set("constituencies", []);
		this.set("wards", []);
		
		this.fetchRegionData( reqData );
		
		if( this.get("regionActionMode") == 'create' ){
			this.get("selSocialRegion").countyId = null;
			this.get("selSocialRegion").consId = null;
			this.get("selSocialRegion").wardId = null;
		}
		return true;
	},
	onChangeCounty : function(){
		var regionId = this.get("selSocialRegion").countyId;
		this.set("currentFetchType", "Constituency"); 
		var reqData = {"regionLevel":"Constituency", "forRegionId" : regionId};
		
		this.set("constituencies", []);
		this.set("wards", []);
		
		this.fetchRegionData( reqData );
		
		if( this.get("regionActionMode") == 'create' ){
			this.get("selSocialRegion").consId = null;
			this.get("selSocialRegion").wardId = null;
		}
		return true;
	},
	onChangeConsituency : function(){
		var regionId = this.get("selSocialRegion").consId;
		this.set("currentFetchType", "Ward"); 
		var reqData = {"regionLevel":"Ward", "forRegionId" : regionId};
		
		this.set("wards", []);
		
		SocialRegionsVM.fetchRegionData( reqData );
		
		if( this.get("regionActionMode") == 'create' ){
			this.get("selSocialRegion").wardId = null;
		}
		return true;
	},
	onChangeWard : function(){
		  console.log( JSON.stringify( this.get("selSocialRegion") ) );
	},
	onChangeRegionPopulation : function(){
		
	},
	onChangeRegionPopulation : function(){
		
	},
	onChangeRegionCoordinates : function(){
		
	},
	fetchRegionData : function(reqData){
		reqData["currentFetchType"] = "currentFetchType";
		var url = HunterConstants.getHunterBaseURL("admin/action/raw/validate/getRegionDataForDropdowns");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(JSON.stringify(reqData), "application/json", "json", "POST", url, "SocialRegionsVM.afterFetchRegionData");
	},
	afterFetchRegionData  : function(data){
		
		data = $.parseJSON(data);
		
		var type = null, i = 0, region = null, regionName = null, newData = [];
		
		for(i=0; i<data.length; i++){
			region = data[i];
			regionId = region["regionId"];
			regionName = region["regionName"];
			if( regionName.indexOf("currentFetchType") != -1 ){
				type = regionName.split("=")[1];
			}else{
				newData.push(region);
			}
		}
		data = newData;
		
		if( type === 'Country' ){
			SocialRegionsVM.set("countries", data);
		}else if( type === 'County' ){
			SocialRegionsVM.set("counties", data);
		}else if( type === 'Constituency' ){
			SocialRegionsVM.set("constituencies", data);
		}else if( type === 'Ward' ){
			SocialRegionsVM.set("wards", data);
		}
		this.set("currentFetchType", null);
	},
	closeSocialRegionPopup : function(){
		$("#socialRegionsPopupForm").submit(function(e){ e.preventDefault(); });
		kendoKipHelperInstance.closeWindowWithOnClose();
	},
	validateAndSaveSocialRegion : function(){
		
		$("#socialRegionsPopupForm").submit(function(e){ e.preventDefault(); });
		
		var validator = $("#socialRegionsPopupForm").kendoValidator({
			messages:{
				custom : function(input){
					var name	= input.attr("name");
					console.log( name );
					if( name == "regionName" || name == "regionDesc" ){
		        		   return "Region field is required." ;
		        	   } 
				},
				length : function(input){
					var name = $(input).attr("name");
		             if ( name === "regionName" ) {
		            	 return "Region name is required and is less than 50 characters.";
		             }else if ( name === "regionDesc" ) {
		            	 return "Region description is required and is less than 100 characters";
		             }else if ( name == "regionCountry" ){
		            	 return "Country is required";
		             }else if ( name === "regionCounty" ){
		            	 return "County is required";
		             }else if ( name === "regionCoordinates" ){
		            	 return "Coordinates cannot be longer than 4000 characters";
		             }
				},
				specialCharacters : function(input){
					return "Only alphanumeric, comma, colon, semi colon and period are allowed!";
				},
				required : "Required field!"
			},
			rules: {
	           custom: function(input) {
	        	   
	        	   var 
	        	    value 	= input.val(), 
	        	    name	= input.attr("name");
	        	   
	        	   if( name == "regionName" || name == "regionDesc"  ){
	        		   return !( value == null || value === "undefined" || value.trim().length == 0 ); 
	        	   } 

	        	   return true;
	           },
	           length : function(input){
	        	   	
	        	    var 
	        	    value 	= input.val(), 
	        	    name	= input.attr("name"); 
		        	
		            if ( name === "regionName" || name === "regionDesc" ) {
		            	return !( value == null || value.trim().length == 0 || value.trim().length >= 50 ) ;
		            }else if( name === "regionCountry" || name === "regionCounty" ){
		            	return !( value + '' === '0' || value.length == 0 ); 
		            }else if ( name === "regionCoordinates" ) {
		            	value = ( value+ "" ).trim();
		            	var len = value.length;
		            	return len < 4000;
		            }
		            
		            return true;
	           },
	           specialCharacters : function(input){
	        	   
	        	   var 
	        	   name = $(input).attr("name"),
	        	   value = input.val(),
	        	   pass =  new RegExp(/^[a-zA-Z0-9. ,:;]+$/).test( value ),
	        	   objctv =  new RegExp(/^[a-zA-Z0-9. '",:;]+$/).test( value );
	        	   
				  if( name == "regionName" || name == "regionDesc"){
					return pass;
				  }else if( name == "regionCoordinates"  ){
					  if( value.trim() === '' ){
						 return true;
					  }
					  return objctv;
				  }
					
				  return true;
	           }
	         }
		}).data("kendoValidator"); 
		
		var isValid = validator.validate();
		
		if( isValid ){
			var 
			data 	= JSON.stringify( this.get("selSocialRegion") ),
			url 	= HunterConstants.getHunterBaseURL("social/action/regions/createOrUpdate"),
			after 	= "SocialRegionsVM.afterCreateSocialRegion";
			kendo.ui.progress($("#socialRegionPopupCover"), true);
			setTimeout(function(){
				kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url , after );
			}, 1500);
			return;
		}else{
			return ;
		}
	},
	afterCreateSocialRegion : function(data){
		kendoKipHelperInstance.closeWindowWithOnClose();
		data = $.parseJSON(data);
    	if(data != null){
    		var status = data.status;
    		var message = replaceAll(data.message, ",", "<br/>");
    		if( status != null && HunterConstants.STATUS_SUCCESS === status ){
    			SocialRegionsVM.get("SocialRegionsDS_").read(); 
    			kendoKipHelperInstance.showSuccessNotification( message );
    		}else{
    			kendoKipHelperInstance.showSimplePopup("Error creating social region : <br/>", "<span style='color:red' >"+ message +"</span>");
    		}
    	}
	}
});





var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";



var SocialRegionsDS = new kendo.data.DataSource({
	  transport: {
	    read:  {
	      url: baseUrl + "social/action/regions/read",
	      dataType: "json",
	      contentType:"application/json",
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "social/action/regions/createOrUpdate",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    destroy: {
	        url: baseUrl + "social/action/regions/destroy",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    update: {
	        url: baseUrl + "social/action/regions/createOrUpdate",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    parameterMap: function(options, operation) {
	          if (operation == "read") {
	        	  console.log("Reading data...");
	          }else if(operation == "destroy"){ 
	        	  var json = kendo.stringify(options);
	        	  var id = json.id;
	        	  console.log("id >> " + id);
	        	  return json;
	          }else{
	        	  var json = kendo.stringify(options); 
	        	  return json;
	          }
	    }
	  },
	  schema: {
	    	model:SocialRegionModel
	  },
	  error: function (e) {
	        kendoKipHelperInstance.popupWarning(e.status, e.errorThrown, "Error");
	  },
	  requestStart: function(e) {
	        var type = e.type;
	        var message = null;
	        if(type === 'read')
	        	return;
	        if(type === 'update')
	        	message = "Updating record...";
	        if(type === 'destroy')
	        	message = "Deleting record...";
	        if(type === 'create')
	        	message = "Creating record...";
	        
	        if(message != null){
	        	kendoKipHelperInstance.popupWarning(message, "Success");
	        }
	        
	  },
	  requestEnd: function(e) {
	        var response = e.response;
	        var type = e.type;
	        var message = null;
	        if(type === 'update')
	        	message = "Successfully updated record!";
	        if(type === 'destroy')
	        	message = "Successfully deleted record!";
	       /* if(type === 'create')
	        	message = "Successfully created record!";*/
	        if(message != null){
	        	kendoKipHelperInstance.popupWarning(message, "Success");
	        }
	  },
	  pageSize:100
});


$("document").ready(function(){
	kendoKipHelperInstance.init();
	SocialRegionsVM.init();
});








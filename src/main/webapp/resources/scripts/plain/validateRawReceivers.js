
registerNavigation("Groups", "Validate Raw Receivers");

var kendoKipHelperInstance;

var RawReceiverModel = kendo.data.Model.define({
	id:"rawReceiverId",
	fields : {
		"rawReceiverId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:0
		},
		"receiverContact" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"selected" : {
			type : "boolean",editable:true, defaultValue:false
		},
		"receiverType" : {
			type : "string", validation : {required : true},editable:true, defaultValue:null
		},
		"firstName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:"Draft"
		},
		"lastName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
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
		"consWardName" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"village" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"verified" : {
			type : "boolean", validation : {required : true},editable:false, defaultValue:false
		},
		"verifiedBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"createdBy" : {
			type : "boolean", validation : {required : true},editable:false, defaultValue:false
		},
		"lastUpdate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"cretDate" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : true},editable:false, defaultValue:null
		}
	},
	getContentTemplate : function(){
		var verified = this.get("verified"); 
		return "<center><span>"+ ( this.get("receiverContact") ) +"</span>"+ ( verified ? "<span class='k-icon k-i-tick'></span>" : "" )+ "</center>";
	}
});

function getToolBarTemplate(){
	return "<button class='k-button' >Click</button>";
}


var ValidateRawReceiverVM = kendo.observable({
	
	selCertifiedStatus : "N",
	isDefaultDateSelected : true,
	isDateChecked : false,
	selDateFrom : null,
	selDateTo : null,
	isUserChecked : false,
	checkedUserId : null,
	isRegionChecked : false,
	isVisible : true,
	isEnabled : true,
	rawReceiverUsers :[],
	currentFetchType : [],
	countries : [],
	counties : [],
	constituencies : [],
	wards : [],
	selCountry : null,
	selCounty : null,
	selConstituency : null,
	selWard : null,
	rawReceiversGrid : null,
	changeNameOfCertButton : function(radio){
		
		var name 	=  $(radio).attr("id"),
			certify = name === 'certifiedReceivers';  
		
		if( certify ){
			$("#certifySelSpan").text( "Uncertify Selected" );
			$("#certifyButtIcon").removeClass("k-icon k-i-tick");
			$("#certifyButtIcon").addClass("k-icon k-i-close");
			ValidateRawReceiverVM.set("selCertifiedStatus", "Y");
		}else{
			$("#certifySelSpan").text( "Certify Selected" );
			$("#certifyButtIcon").removeClass("k-icon k-i-close");
			$("#certifyButtIcon").addClass("k-icon k-i-tick");
			ValidateRawReceiverVM.set("selCertifiedStatus", "N");
		}
		
		ValidateRawReceiverVM.get("rawReceiversGrid").dataSource.read();
	},
	unSelectAllOthersCheckBoxes : function(this_){
		var checked = $(this_).prop("checked"); 
		if( checked ){
			ValidateRawReceiverVM.set("isDateChecked", false);
			ValidateRawReceiverVM.set("isUserChecked", false);
			ValidateRawReceiverVM.set("isRegionChecked", false);
		}else{
			if( !ValidateRawReceiverVM.get("isDateChecked") && !ValidateRawReceiverVM.get("isUserChecked") && !ValidateRawReceiverVM.get("isRegionChecked") ){
				ValidateRawReceiverVM.set("isDefaultDateSelected", true);
				$( this_ ).prop("checked", true); 
			}
			ValidateRawReceiverVM.get("rawReceiversGrid").dataSource.read();
		}
	},
	unSelectDefaultDateCheckBox : function(this_){
		
		var 
		checked 	= $(this_).prop("checked"),
		dataRefName = $(this_).attr("data-ref-name");
		
		if( dataRefName === 'dateRange' ) 	ValidateRawReceiverVM.set("isDateChecked", checked);
		if( dataRefName === 'regionData' ) 	ValidateRawReceiverVM.set("isRegionChecked", checked);
		if( dataRefName === 'rawUser' ) 	ValidateRawReceiverVM.set("isUserChecked", checked);
		
		if( checked ){
			ValidateRawReceiverVM.set("isDefaultDateSelected", false);
		}else{
			if( dataRefName === 'dateRange' && !ValidateRawReceiverVM.get("isUserChecked") && !ValidateRawReceiverVM.get("isRegionChecked") ){
				ValidateRawReceiverVM.set("isDefaultDateSelected", true);
			}
			if( dataRefName === 'rawUser' && !ValidateRawReceiverVM.get("isDateChecked") && !ValidateRawReceiverVM.get("isRegionChecked") ){
				ValidateRawReceiverVM.set("isDefaultDateSelected", true);
			}
			if( dataRefName === 'regionData' && !ValidateRawReceiverVM.get("isUserChecked") && !ValidateRawReceiverVM.get("isDateChecked") ){
				ValidateRawReceiverVM.set("isDefaultDateSelected", true);
			}
		}
	},
	gridReqParams : function(){
		var params = {
			"isDateChecked"			: ValidateRawReceiverVM.get("isDateChecked"),
			"isRegionChecked"		: ValidateRawReceiverVM.get("isRegionChecked"),
			"isUserChecked"			: ValidateRawReceiverVM.get("isUserChecked"),
			"selDateFrom"			: kendo.toString(ValidateRawReceiverVM.get("selDateFrom"), "yyyy-MM-dd"),
			"selDateTo"				: kendo.toString(ValidateRawReceiverVM.get("selDateTo"), "yyyy-MM-dd"),
			"checkedUserId"			: ValidateRawReceiverVM.get("checkedUserId"),
			"selCountry"			: ValidateRawReceiverVM.get("selCountry"),
			"selCounty"				: ValidateRawReceiverVM.get("selCounty"),
			"selConstituency"		: ValidateRawReceiverVM.get("selConstituency"),
			"selWard"				: ValidateRawReceiverVM.get("selWard"),
			"isDefaultDateSelected"	: ValidateRawReceiverVM.get("isDefaultDateSelected") ,
			"selCertifiedStatus" 	: ValidateRawReceiverVM.get("selCertifiedStatus")
		};
		console.log( "gridReqParams" + JSON.stringify( params ) );
		return params;
	},
	fetchCountries : function(){
		var reqData = {"regionLevel":"Country", "forRegionId" : 0};
		this.set("currentFetchType", "Country"); 
		this.fetchRegionData( reqData );
	},
	onChangeCountry : function(){
		var reqData = {"regionLevel":"County", "forRegionId" : ValidateRawReceiverVM.get("selCountry")}; 
		this.set("currentFetchType", "County"); 
		this.fetchRegionData( reqData ); 
		this.set("counties", []);
		this.set("selCounty", null);
		this.set("constituencies", []);
		this.set("selConstituency", null);
		this.set("wards", []);
		this.set("selWard", null);
	},
	onChangeCounty : function(){
		var reqData = {"regionLevel":"Constituency", "forRegionId" : ValidateRawReceiverVM.get("selCounty")}; 
		this.set("currentFetchType", "Constituency"); 
		this.fetchRegionData( reqData );
		this.set("constituencies", []);
		this.set("selConstituency", null);
		this.set("wards", []);
		this.set("selWard", null);
	},
	onChangeConstituency : function(){
		var reqData = {"regionLevel":"Ward", "forRegionId" : ValidateRawReceiverVM.get("selConstituency")};
		this.set("currentFetchType", "Ward"); 
		this.fetchRegionData( reqData );
		this.set("wards", []);
		this.set("selWard", null);
	},
	onChangeWard : function(){
		/* Try finding what you wanna play with here :) */
	},
	fetchAvailableUsers : function(){
		var url = HunterConstants.getHunterBaseURL("admin/action/raw/validate/getAvailableRawReceiverUsers");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(null, "application/json", "json", "POST", url, "ValidateRawReceiverVM.afterFetchAvailableUsers");
	},
	afterFetchAvailableUsers : function(data){
		data = $.parseJSON(data);
		ValidateRawReceiverVM.set("rawReceiverUsers",data);
	},
	fetchRegionData : function(reqData){
		var url = HunterConstants.getHunterBaseURL("admin/action/raw/validate/getRegionDataForDropdowns");
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(JSON.stringify(reqData), "application/json", "json", "POST", url, "ValidateRawReceiverVM.afterFetchRegionData");
	},
	afterFetchRegionData  : function(data){
		data = $.parseJSON(data);
		var type = ValidateRawReceiverVM.get("currentFetchType");
		if( type === 'Country' ){
			ValidateRawReceiverVM.set("countries", data);
		}else if( type === 'County' ){
			ValidateRawReceiverVM.set("counties", data);
		}else if( type === 'Constituency' ){
			ValidateRawReceiverVM.set("constituencies", data);
		}else if( type === 'Ward' ){
			ValidateRawReceiverVM.set("wards", data);
		}
		ValidateRawReceiverVM.set("currentFetchType", null);
	},
	beforeInit : function(){
		kendoKipHelperInstance =  new kendoKipHelper();
		kendoKipHelperInstance.init();
	},
	init : function(){
		console.log("Initializing validate raw receiver VM...");
		this.beforeInit();
		kendo.bind($("#validateRawReceiversContainer"), ValidateRawReceiverVM);
		this.afterInit();
		console.log("Done initializing validate raw receiver VM!!");
	},
	afterInit : function(){
		this.createValidateReceiverGrid();
		this.setDefaultStartAndEndDate();
		this.setFirstRawUser();
		this.fetchAvailableUsers();
		this.fetchCountries(); // load countries dropdown
	},
	setDefaultStartAndEndDate : function(){
		var date = new Date(),
		year = date.getFullYear(),
		month = date.getMonth() + 1,
		day = date.getDay();
		var from =  ( year - 1 )  + "-" + month + "-" + day,
			to =  year + "-" + month + "-" + day;
		ValidateRawReceiverVM.set("selDateFrom", from );
		ValidateRawReceiverVM.set("selDateTo", to);
		
		var fromPicker = $('#fromDatePicker').data("kendoDatePicker"), 
		  	toPicker = $('#toDatePicker').data("kendoDatePicker");
		
		fromPicker.value( from );
		toPicker.value( to );
		
	},
	setFirstRawUser : function(){
		var dList = $("#rawUserDropdownList").data("kendoDropDownList");
		dList.select(0); 
	},
	getSelReceiversPrams : function(){
		return {"getMode":"date", "from":"2015-12-01", "to":"2016-08-01"} ;
	},
	validateOnFilter : function(){
		
		console.log("Running filter...");
		
		if( this.get("isDateChecked") ){
			if( this.get("selDateFrom") == null ||  this.get("selDateFrom") === '' ){
				 kendoKipHelperInstance.showErrorNotification("Please select from date or uncheck date field.");
				 return;
			}else if( this.get("selDateTo") == null ||  this.get("selDateTo") === '' ){
				 kendoKipHelperInstance.showErrorNotification("Please select to date or uncheck date field.");
				 return;
			}
		}
		
		if( this.get("isUserChecked") ){
			if( this.get("checkedUserId") == null ||  this.get("checkedUserId") === '' ){
				 kendoKipHelperInstance.showErrorNotification("Please select created by or uncheck created by field.");
				 return;
			}
		}
		
		if( this.get("isRegionChecked") ){
			
			var selCountry 		= this.get("selCountry"),
				selCounty 		= this.get("selCounty"),
				selConstituency = this.get("selConstituency"),
				selWard 		= this.get("selWard"),
				isAllNull 		= selCountry == null && selCounty == null && selConstituency == null && selWard == null,
				isAllEmpty 		= false;
			
			if( isAllNull || isAllEmpty ){
				kendoKipHelperInstance.showErrorNotification("Please select region or uncheck region field.");
				return;
			}
		}
		
		ValidateRawReceiverVM.get("rawReceiversGrid").dataSource.read();
		
	},
	createValidateReceiverGrid : function(){
		kendo.destroy( $("#validateRawReceiverGrid") );
		$("#validateRawReceiverGrid").html('');
		var rawReceiversGrid = $("#validateRawReceiverGrid").kendoGrid({
			toolbar : kendo.template(
					'<button onClick="ValidateRawReceiverVM.validateOnFilter()" class="k-button" style="margin-top:2px;float:left;z-index:1005;width:150px;height:38px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span class="k-icon k-i-refresh"></span>Filter Receivers</button>' +
					'<button id="certifySelRcvrsButton" onClick="ValidateRawReceiverVM.showPopupToConfirmCertify()" class="k-button" style="margin-top:2px;float:left;z-index:1005;width:150px;height:38px;background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);" ><span id="certifyButtIcon" class="k-icon k-i-tick"></span><span id="certifySelSpan">Certify Selected<span></button>'
			),
			 pageable: {
			    pageSize: 100,
			    previousNext: true,
			    refresh: true,
			    itemsPerPage: "Contacts Per Page"
			  },
			  columns : [
			            { 'field':'','title':'',
			            	headerTemplate: '<label><center><input id="selectAll" type="checkbox" /></center></label>', 
			            	template: '<center><input rawReceiverId="#=rawReceiverId#" onClick="ValidateRawReceiverVM.bindSelectedReceiverId(this)" type="checkbox" #=selected ? "checked=checked" : "" # /></center>', 'width':'70px' },
			            { 'field': 'receiverContact',template:"#=getContentTemplate()#", title : 'Contact','width':'150px'  },
                  		{ 'field': 'receiverType', title : 'Type'  },
                  		{ 'field': 'firstName', title : 'First Name' },
                  		{ 'field': 'lastName', title : 'Last Name'  },
                  		{ 'field': 'countryName', title : 'Country'  },
                  		{ 'field': 'countyName', title : 'County' },
                  		{ 'field': 'consName', title : 'Constituency'  },
                  		{ 'field': 'consWardName', title : 'Ward' },
                  		{ 'field': 'verified', title : 'Verified'  },
                  		{ 'field': 'createdBy', title : 'Created By'  },
                  		{ 'field': 'cretDate', title : 'Created On'  }/*,
                  		{ 'field': 'verifiedBy', title : 'Verified By' }*/
		           ],
	        dataSource: {
	        	transport: {
	                read: {
	                	data : function(){
	                		var data_ = ValidateRawReceiverVM.gridReqParams();
	                		console.log(data_);
	                		return data_;
	                	},
	                	type:"json",
	                	method:"POST",
	                	url: HunterConstants.getHunterBaseURL("rawReceiver/action/raw/getRawReceiversForValidation")
	                }
	            },
	            requestStart: function(e) {
	                var type = e.type;
	                var message = null;
	                if(type === 'read'){
	                	return;
	                }
	                if(type === 'update')
	                	message = "Updating record...";
	                if(type === 'destroy')
	                	message = "Deleting record...";
	                if(type === 'create')
	                	message = "Creating record...";
	                
	                if(message != null){
	                	kendoKipHelperInstance.popupWarning(message, "Success");
	                }
	            },requestEnd : function(e){
	            	$("#selectAll").prop("checked", false);
	            },
	            pageSize: 100,
	            serverPaging: true,
	            schema: {
	                data: 'data',
	                total: 'total',
	                errors: 'errors',
	                model : RawReceiverModel
	            }
	        }
	    }).data("kendoGrid"); 
		ValidateRawReceiverVM.set("rawReceiversGrid", rawReceiversGrid);
	},
	bindSelectAll : function(){
		$("#selectAll").bind('click', function (e) {

	        var $cb = $(this);
	        var checked = $cb.is(':checked');
	        var grid = $('#validateRawReceiverGrid').data('kendoGrid');
	        grid.table.find("tr").find("td:last input").attr("checked", checked);

	        //now making all the rows value to 1 or 0
	        var items = $("#validateRawReceiverGrid").data("kendoGrid").dataSource.data();
	        for (var i = 0; i < items.length; i++) {
	            var item = items[i];
	            item.set('selected', checked);

	           // var row = grid.tbody.find(">tr:not(.k-grouping-row)").eq(0);
	           // grid.select(row);

	        }

	        if (!checked)
	        {
	            $("#validateRawReceiverGrid").data("kendoGrid").clearSelection();
	        }
	        
	    });
	},
	showPopupToConfirmCertify : function(){

		var 
		items = $("#validateRawReceiverGrid").data("kendoGrid").dataSource.data();
		count = 0;
		
        for (var i = 0; i < items.length; i++) {
        	var 
            item = items[i],
            selected = item.get("selected"); 
        	if( selected ) count++;
        }
        if( count == 0 ){
        	kendoKipHelperInstance.showSimplePopup("Hunter Validation : No Receiver Selected", "<span style='color:red;font-size:14px;'>No receiver is selected! Please select at least one</span>" );
        	return;
        }
		
        var 
        html = $("#cnfrmCrtfyRwRcvrPopup").html(),
		template = kendo.template(html),
		certified = ValidateRawReceiverVM.get("selCertifiedStatus") == 'Y',
		message  = certified ? "uncertified" : "certified",
		template2 = template({dataCount:count, message : message, color : certified ? "rgb(145,5,0)" : "green"  });
        
		kendoKipHelperInstance.showWindowWithOnClose(template2,"Hunter Confirmation");
	},
	submitSelectedReceiverIds : function(){
		
		kendoKipHelperInstance.closeWindowWithOnClose();
		
		var 
		items = $("#validateRawReceiverGrid").data("kendoGrid").dataSource.data(),
		selectedIds = {},
		counter = 0;
        for (var i = 0; i < items.length; i++) {
        	var 
            item = items[i],
            id = item.get("rawReceiverId"),
            selected = item.get("selected"); 
        	if( selected ){
        		selectedIds[counter] =  id;
        		counter++;
        	}
        }
        if( Object.keys(selectedIds).length == 0 ){
        	kendoKipHelperInstance.showErrorNotification( "No receiver is selected. Please select at least one" );
        	return;
        }
        
        var
        data = JSON.stringify( selectedIds ),
        url = HunterConstants.getHunterBaseURL("/rawReceiver/action/raw/validate/certifySelectedReceivers");
        kendoKipHelperInstance.ajaxPostData(data, "application/json", "json", "POST", url, "ValidateRawReceiverVM.afterSubmittingSelReceiverIds");
	},
	afterSubmittingSelReceiverIds : function(data){
		data = $.parseJSON(data);
		var status = data[HunterConstants.STATUS_STRING],
			message = data[HunterConstants.MESSAGE_STRING];
		if( status === HunterConstants.STATUS_SUCCESS ){
			kendoKipHelperInstance.showSuccessNotification( message );
		}else{
			kendoKipHelperInstance.showErrorNotification( message );
		}
		$("#validateRawReceiverGrid").data("kendoGrid").dataSource.read();
	},
	bindSelectedReceiverId : function(checkBox){
		var rawReceiverId = $(checkBox).attr("rawReceiverId"),
			selected = $(checkBox).prop("checked"),
			items = $("#validateRawReceiverGrid").data("kendoGrid").dataSource.data();
        for (var i = 0; i < items.length; i++) {
        	var 
            item = items[i],
            id = item.get("rawReceiverId");
        	if( id == rawReceiverId ){
        		item.set("selected", selected);
        		break;
        	}
        } 
	}
	
	
	
});


$("document").ready(function(){
	ValidateRawReceiverVM.init();
	ValidateRawReceiverVM.bindSelectAll();
});
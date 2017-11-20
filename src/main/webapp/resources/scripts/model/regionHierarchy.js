
registerNavigation("Regions", "Region Hierarchies");

var kendoWindow;
var kendoKipHelperInstance;
var baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";

var RegionHierarchyModel = kendo.data.TreeListModel.define({
	id:"id",
	expanded : false,
	fields : {
		"id" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"beanId" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"parent" : {
			type : "number", validation : {required : false},editable:false,nullable : true
		},
		"genParent" : {
			type : "number", validation : {required : false},editable:false,nullable : true
		},
		"name" : {
			type : "string",editable:true, validation: { required: false }
		},
		"population" : {
			type : "number", validation : {required : false},editable:false
		},
		"hunterPopuplation" : {
			type : "number", validation : {required : false},editable:false
		},
		"mapDots" : {
			type : "string", validation : {required : false},editable:false
		},
		"levelType" : {
			type : "string", validation : {required : false},editable:false
		},
		"regionCode" : {
			type : "string", validation : {required : false},editable:false
		},
		"city" : {
			type : "string", validation : {required : false},editable:false
		},
		"hasState" : {
			type : "boolean", validation : {required : false},editable:false
		},
		"cretDate" : {
			type : "date", validation : {required : false},editable:false
		},
		"createdBy" : {
			type : "string", validation : {required : false},editable:false
		},
		"lastUpdate" : {
			type : "date", validation : {required : false},editable:false
		},
		"lastUpdatedBy" : {
			type : "string", validation : {required : false},editable:false
		},
		"parentId": { 
			field: "genParent", nullable: true
		}
	},
	getEditHierarchyTemplate : function(){
		var id = this.get("id"); 
		var editButton = kendoKipHelperInstance.createSimpleHunterButton('pencil',null, 'RegionHierarchyVM.showPopupToEditRegion("' + id +'")');
		return editButton;
	},
	getRegionReceiversTemplate : function(){
		var levelType = this.get("levelType"),
			beanId = this.get("beanId");
			
			var params = beanId + "::" + levelType;
				
			var idString = '"' + params + '"',
				html = kendoKipHelperInstance.createSimpleHunterButton('search',null, 'RegionHierarchyVM.displayContactsForRegion('+ idString +')');
			return html;
	}
});

function getSelCountry(){
	if(RegionHierarchyVM == null){
		console.log("Current selected country >> ");
		return "Kenya";
	}else{
		var selCountry = RegionHierarchyVM.get("selCountry");
		selCountry = selCountry == null ? "/Kenya" : "/" + selCountry;
		console.log("Current Selected country >> " + selCountry);
		return selCountry;
	}
}

var RegionHierarchyTreeListDS = new kendo.data.TreeListDataSource({
	  
	transport: {
	    read:  {
	      url: function(){
	    	  var url =  baseUrl + "region/action/regions/hierarchies/action/read/post" + getSelCountry();
	    	  console.log("URL >> " + url);
	    	  return url;
	      },
	      dataType: "json",
	      method: "POST"
	    },
	    create: {
	        url: baseUrl + "region/action/regions/hierarchies/action/create",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    update: {
	        url: baseUrl + "region/action/regions/hierarchies/action/update",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    destroy: {
	        url: baseUrl + "region/action/regions/hierarchies/action/destroy",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    parameterMap: function(options, operation) {
	          if (operation == "read") {
	        	  console.log("Reading data...");
	          }else if(operation === "destroy" || operation === "update" || operation === "create"){ 
	        	  var json1 = kendo.stringify(options);
	        	  console.log(json1);
	        	  return json1;
	          }else{
	        	  kendoKipHelperInstance.popupWarning("Bad operation >> " + operation,"Error");
	          }
	    }
	  },
	  schema: {
	    	model:RegionHierarchyModel
	  },
	  error: function (e) {
	        kendoKipHelperInstance.popupWarning(e.status, e.errorThrown, "Error");
	  },
	  pageSize:2000
});

var RegionHierarchyVM = kendo.observable({
	
	treeList : null,
	selCountry : null,
	navDir : null,
	isUploadReceiversEnabled: true,
    isUploadReceiversVisible: true,
    receiverUploadErrors : false,
    regionTreeList : null,
	
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
	
	beforeInit : function(){
		kendoKipHelperInstance = new kendoKipHelper();
		kendoKipHelperInstance.init();
		console.log("Preparing to initialized RegionHierarchyVM...");
	},
	init : function(){
		console.log("Initializing RegionHierarchyVM...");
		this.beforeInit();
		kendo.bind($("#regionHierarchyTreeListVMContainer"), RegionHierarchyVM);
		this.createKendoTreeList();
		this.afterInit();
	},
	afterInit : function(){
		console.log("RegionHierarchyVM successfully initialized!!");
	},
	displayContactsForRegion : function(params){
		var countryId = $("#countryDropDownList").data("kendoDropDownList").value();
		params = "REGION_HIERARCHY::" + countryId + "::" + params;
		var content = $("#selRegionMessgReceiverGridTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Region Hierarchy receivers of regions params : " + params);
		$("#selRegionMessgReceiverGridContainer").css({"height":"500px"});
		kendo.ui.progress($("#contactsSpinner"), true);
		setTimeout(function(){
			kendoKipHelperInstance.showReceiversGridForRegionOrGroup(params);
		}, 800);
	},
	showPopupToEditRegion : function(id){
		var model = this.get("regionTreeList").dataSource.get(id);
		model = $.parseJSON(kendo.stringify(model));
		console.log(model);
		model["regionId"] = id;
		var html = $("#regionHierarchyEditTemplate").html();
		var template = kendo.template(html);
		var contents = template(model);
		var html = $("#regionHierarchyEditTemplate").html();
		kendoKipHelperInstance.showWindowWithOnClose(contents, "Edit Hunter Region");
	},
	validateFields : function(key,val){
		
		console.log("key="+key + " , val=" + val);
		
		var invalid = key === 'regionName' && ( val == null || val === '' || val.trim().length < 1 || val.trim().length > 50 );
		if(invalid){
			RegionHierarchyVM.appendError(key, "Region name cannot be empty and must be between 50 characters");
			return false;
		}
		
		invalid = key == "population" && ( val === '' || isNaN(val) || val.indexOf(".") != -1 || val < 0 );
		if(invalid){
			RegionHierarchyVM.appendError(key,"Population must be a positive integer");
			return false;
		}
		
		invalid = key == "regionCode" && ( val === '' || isNaN(val) || val.indexOf(".") != -1 || val < 0 || val.trim().length != 3);
		if(invalid){
			RegionHierarchyVM.appendError(key,"Region code must be a positive three-digit integer");
			return false;
		}
		
		return true;
	},
	appendError : function(key,message){
		console.log("Appening for : " + key);
		$("#"+key).closest("tr").after("<tr><td style='width:40%;' ></td><td style='color:red;' ><span class='hunterRegionErrors'  >"+ message +"</span></td></tr>");
	},
	editSelEditRegion : function(regionId){
		$(".hunterRegionErrors").closest('tr').remove();
		var inputs = $("#regionHierarchyEditData input");
		var valid = true;
		var data = {};
		for(var i=0; i<inputs.length;i++){
			var input = inputs[i];
			var key = $(input).attr("id");
			var val = $(input).val();
			var valid_ = RegionHierarchyVM.validateFields(key,val);
			if(!valid_){
				valid = false;
			}
			data[key] = val;
		}
		if(!valid) {
			return;
		}
		
		var model = this.get("regionTreeList").dataSource.get(regionId);
		var beanId = model.get("beanId");
		var levelType = model.get("levelType"); 
		
		data["beanId"] = beanId;
		data["levelType"] = levelType;
		data["regionId"] = regionId;
		
		data = JSON.stringify(data);
		var url = baseUrl + "/region/action/hierarchies/edit";
		$("#editRegionHierIconHolder").css({"display" : ""}); 
		kendo.ui.progress($("#editRegionHierIconHolder"), true);
		kendoKipHelperInstance.ajaxPostDataForJsonResponse(data, "application/json", "json", "POST", url , "RegionHierarchyVM.afterEditSelRegion");
	},
	afterEditSelRegion : function(data){
		data = $.parseJSON(data);
		kendoKipHelperInstance.closeWindowWithOnClose();
		$("#editRegionHierIconHolderSpan").text("Refreshing data..."); 
		this.get("regionTreeList").dataSource.read();
		setTimeout(function(){
			kendo.ui.progress($("#editRegionHierIconHolder"), false);
		},1000);
		$("#editRegionHierIconHolder").css({"display" : "none"}); 
		kendoKipHelperInstance.showSuccessNotification("Successfully saved the changes!");
	},
	onChangeCountry : function(){
		console.log("Selected country >> " + selCountry); 
	},
	displayUploadWindow : function(){
		var r = $.Deferred();
		var content = $("#regionHierarchyToolBarTemplateNew").html();
		kendoKipHelperInstance.showWindowWithOnClose(content, "Task Hierarchy Upload");
		return r;
	},
	getFileInfo : function(e){
		return $.map(e.files, function(file) {
            var info = file.name;
            // File size is not available in all browsers
            if (file.size > 0) {
                info  += " (" + Math.ceil(file.size / 1024) + " KB)";
            }
            return info;
        }).join(", ");
	},
	onKendoUploadError : function(e){
		var message = "Error while " + e.operation + "ing : " + RegionHierarchyVM.getFileInfo(e);
		kendoKipHelperInstance.showErrorNotification(message);
		 /*var err = e.XMLHttpRequest.responseText;
	        alert(err);*/
	},
	onSuccessUploadFile : function(e){
		kendoKipHelperInstance.showSuccessNotification("Successfully uploaded file!");
	},
	openSectionToUploadReceivers : function(){
		this.displayUploadWindow();
		$("#hunterHierarchyFiles").kendoUpload({
			async:{ 
				saveUrl: baseUrl + 'messageReceiver/action/import/receivers/post/save', 
  			  	removeUrl: baseUrl + 'messageReceiver/action/import/receivers/post/save', 
  			  	autoUpload: true 
  			},
  			 error : RegionHierarchyVM.onKendoUploadError,
  			 success : RegionHierarchyVM.onSuccessUploadFile
		});
	},
	cancelUploadPopup : function(){
		kendo.destroy($("#receiverFilesInput"));
		kendoKipHelperInstance.closeMeasuredWindow();
	},
	onSelect: function(e) {
        var message = $.map(e.files, function(file) { return file.name; }).join(", ");
        console.log("event :: select (" + message + ")");
        RegionHierarchyVM.set("receiverUploadErrors", false);
    },
    onError : function(){
    	RegionHierarchyVM.set("receiverUploadErrors", true);
    },
    onComplete : function(){
    	var isError = RegionHierarchyVM.get("receiverUploadErrors");
    	if(isError){
    		kendoKipHelperInstance.popupWarning("Error!!", "Error");
    	}else{
    		kendoKipHelperInstance.popupWarning("Uploaded successfully!!", "Error");
    	}
    },
    closeUploadDivAndShowGrid : function(){
    	$("#uploadReceiversContainer").slideUp(1000, function(){
        	$("#regionHierarchyTreeList").slideDown(1000);
        });
    },
	createKendoTreeList : function(){
		console.log("Creating RegionHierarchyTreeList list...");
		var regionTreeList_ = $("#regionHierarchyTreeList").kendoTreeList({
			toolbar : kendo.template($("#regionHierarchyToolBarTemplate").html()),
			dataSource : RegionHierarchyTreeListDS,
			height: 700,
			 messages: {
				 loading: "Loading region hierarchies..."
			 },
			 filterable: {
			     extra: false
			 },
			 pageable :false,
			 editable: {
					mode: "popup"
	         },
	         columns: [	
						{ field:'name', title:'Name', filterable: true },
						{ field:'population', title:'Population', filterable: true,width:140 },
						{ field:'hunterPopuplation', title:'Hunter Population', width:140, filterable: true },
						{ field:'city', title:'City',  filterable: true, width:200 },
						{ field:'levelType', title:'Type', filterable: true,width:110  },
						{ field:'regionCode', title:'Code', filterable: true,width:110 },
						{ field:'hasState', title:'Has State',  filterable: true,width:110},
						{ field:'receivers', title:'Receivers',  filterable: true,width:110 },
						{ field: "viewReceivers", title : "Receivers", width:80, template : "#=getRegionReceiversTemplate()#"},
						{ field: "editTask", title : "Edit", width:80, template : "#=getEditHierarchyTemplate()#"}
	         ],
	          editable: "inline"
		}).data("kendoTreeList");
		this.set("regionTreeList", regionTreeList_);
		console.log("Successfully created RegionHierarchyTreeList");
	}
});


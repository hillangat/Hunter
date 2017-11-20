var 
kendoWindow,
kendoKipHelperInstance,
baseUrl = location.protocol + "//" + location.hostname + (location.port && ":" + location.port) +  "/Hunter/";


var ItemGroupModel = kendo.data.TreeListModel.define({
	id:"id",
	expanded : true,
	fields : {
		"id" : {
			type : "number", validation : {required : true},editable:false, defaultValue:"0"
		},
		"parent" : {
			type : "number", validation : {required : false},editable:false
		},
		"name" : {
			type : "string", 
			editable:true, 
			validation: {
                required: { message: "Group name is required!" },
                validateGroupName: function (input) {
                    if (input.attr("data-bind") == "value:name") { 
                        if (input.val().length > 5) {
                            input.attr("data-validateGroupName-msg", "Name can only have a maximum of 10 characters.");
                            return false;
                        }
                        else
                            return true;
                    }
                    return true;
                }
            }
		},
		"description" : {
			type : "string",  
			editable:true,
			validation: {
                required: { message: "Group description is required!" },
                validateGroupDescription: function (input) {
                    if (input.attr("data-bind") == "value:description") { 
                        if (input.val().length > 5) {
                            input.attr("data-validateGroupDescription-msg", "Group description cannot have more than 5 characters");
                            return false;
                        }
                        else
                            return true;
                    }
                    return true;
                }
            }
		},
		"statText" : {
			type : "string", editable:true
		},
		
		"groupCode" : {
			type : "string", editable:false, defaultValue:"C" 
		},
		"parentId": { field: "parent", nullable: true }
	},
	getItemGroupDeleteTemplate : function(){
		  var id = this.get("id");
		  var name = this.get("name");
		  var idString = '"'+ id +'",';
		  var code = this.get("groupCode");
		  var message = "";
		  if(code === "C"){
			  message = '"<u><b>' + name + '</b></u> will be deleted. <br/> Are you sure?"';
		  }else if(code === "G"){
			  message = '"<u><b>' + name + '</b></u> and <u>all its children</u> will be deleted. <br/> Are you sure?"';
		  }
		  var html = kendoKipHelperInstance.createKendoSmallIconTagTemplate("close", 'kendoKipHelperInstance.showOKCancelToDeleteItemGroup('+  idString + message+ ')',"Delete");
		  return html;
	 },
	 getItemGroupIdIcon : function(){
		 var code = this.get("groupCode");
		 var id = this.get("id");
		 var parent = this.get("parentId");
		  if(code.trim() === "G" && parent == null )
			  return "<span >" + '<img src="' + baseUrl + 'static/resources/images/itemGroup.png" alt="'+ code +'" height="10" width="10">&nbsp;' + id + '</span>';
		  else if(code.trim() === "C"){
			  return "<span >" + '<img src="' + baseUrl + 'static/resources/images/itemClass.png" alt="'+ code +'" height="10" width="10">&nbsp;' + id + '</span>';
		  }else if(code.trim() === "G" && parent != null) {
			  return "<span >" + '<img src="' + baseUrl + 'static/resources/images/itemGroup.png" alt="'+ code +'" height="10" width="10">&nbsp;' + id + '</span>';
		  }
	 },
	 getAddChildClasTemplate : function(){
		 var id = this.get("id");
		  var code = this.get("groupCode");
		  if(code.trim() === "G"){
			  var html = kendoKipHelperInstance.createHidableIconTagTemplate("close", 'itemGroupVM.addClassToGivenId('+  id +')',"Add Class", false);
		  	  return html;  
		  }else{
			  var html = kendoKipHelperInstance.createHidableIconTagTemplate("close", 'itemGroupVM.addClassToGivenId('+  id +')',"Add Class", true);
			  return html;
		  }
	 },
	 getNewAddChildClasTemplate : function(){
		 var code = this.get("groupCode");
		  if(code.trim() === "G"){
			 return '<button data-command="createchild" class="k-button k-button-icontext k-grid-add"><span class="k-icon k-add"></span>Add Child</button>';  
		  }else{
			  return '<button data-command="createchild" style="display:none;" class="k-button k-button-icontext k-grid-add"><span class="k-icon k-add" disabled></span>Add Child</button>';
		  }
	 }
});



var itemGroupTreeListDS = new kendo.data.TreeListDataSource({
	  transport: {
	    read:  {
	      url: "http://localhost:8080/Kendo/itemGroup/action/read",
	      dataType: "json",
	      method: "POST"
	    },
	    create: {
	        url: "http://localhost:8080/Kendo/itemGroup/action/create",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST",
	        success: function(result) {
	            kendoKipHelperInstance.popupWarning('', JSON.stringify(result), "Success");
	         }
	    },
	    update: {
	        url: "http://localhost:8080/Kendo/itemGroup/action/update",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    destroy: {
	        url: "http://localhost:8080/Kendo/itemGroup/action/destroy",
	        dataType: "json", 
	        contentType:"application/json",
	        method:"POST"
	    },
	    parameterMap: function(options, operation) {
	          if (operation == "read") {
	        	  console.log("Reading data...");
	          }else if(operation === "destroy" || operation === "update" || operation === "create"){ 
	        	  var json1 = kendo.stringify(options); 
	        	  json = jQuery.parseJSON(json1);
	        	  var pId = json["parent"];
	        	  var code = json["groupCode"];
	        	  if(pId == null && code === "C"){
	        		  json["groupCode"] = "G";
	        	  }
	        	  var hasChildren = json["hasChildren"]; 
	        	  delete json["hasChildren"];
		          if(hasChildren === true){
		        	 console.log("has children... >> " + jQuery.parseJSON(json1) ); 
		          }else {
		        	  var parent = json["parent"];
		        	  delete json["parentId"];
		        	  if(parent === "undefined"){
		        		  json["parent"] = null;
		        	  }
		          }
		          json = JSON.stringify(json);
	        	  return json;
	          }else{
	        	  kendoKipHelperInstance.popupWarning("Bad operation >> " + operation,"Error");
	          }
	    }
	  },
	  schema: {
	    	model:ItemGroupModel
	  },
	  error: function (e) {
	        kendoKipHelperInstance.popupWarning(e.status, e.errorThrown, "Error");
	  },
	  requestStart: function(e) {
	        var type = e.type;
	        var message = "";
	        if(type === 'read')
	        	return;
	        if(type === 'update')
	        	message = "Updating record...";
	        if(type === 'destroy')
	        	message = "Deleting record...";
	        if(type === 'create')
	        	message = "Creating record...";
	        kendoKipHelperInstance.popupWarning(message, "Success");
	  },
	  requestEnd: function(e) {
	        var response = e.response;
	        var type = e.type;
	        var message = "";
	        if(type === 'read')
	        	if(response !== 'undefined' && response != null){
	        		message = "Successfully obtained ( "+ response.length +" ) records!";
	        	}else{
	        		return;
	        	}
	        if(type === 'update')
	        	message = "Successfully updated record!";
	        if(type === 'destroy')
	        	message = "Successfully deleted record!";
	        if(type === 'create')
	        	message = "Successfully created record!";
	        
	        kendoKipHelperInstance.popupWarning(message, "Success");
	  },
	  pageSize:1000
});

var itemGroupVM = kendo.observable({
	
	itemGroupModel : null,
	treeList : null,
	
	beforeInit : function(){
		kendoKipHelperInstance = new kendoKipHelper();
		kendoKipHelperInstance.init();
		console.log("Preparing to initialized itemGroupVM...");
	},
	init : function(){
		console.log("Initializing itemGroupVM...");
		this.beforeInit();
		this.createKendoTreeList();
		kendo.bind($("#itemGroupVMBindingDiv"),itemGroupVM);
		this.afterInit();
	},
	afterInit : function(){
		$("#files").kendoUpload();
		console.log("ItemGroupVM successfully initialized!!");
	},
	downloadToExcel : function(){
		window.location.href = "http://localhost:8080/Kendo/itemGroup/action/download/workbook";
	},
	updloadExcelData : function(){
		var html = $("#uploadFilesDivCover").html();
		kendoKipHelperInstance.showPercentMeasuredOKCancelTitledPopup(html, "Upload Template", 23.5, 18 ); 
	},
	collapseAllGroups : function(){
		var treeList_ = $("#itmGroupTreeList").data("kendoTreeList");
		var rows = $("tr.k-treelist-group", treeList_.tbody);
		$.each(rows, function(idx, row) {
			treeList_.collapse(row);
		});
	},
	expandAllGroups : function(){
		var treeList_ = $("#itmGroupTreeList").data("kendoTreeList");
		var rows = $("tr.k-treelist-group", treeList_.tbody);
		$.each(rows, function(idx, row) {
			treeList_.expand(row);
		});
	},
	deleteSelectedItemGroup : function(id){
		var treeList = $("#itmGroupTreeList").data("kendoTreeList");
		var treeListDS = treeList.dataSource;
		var selObj = treeListDS.get(id);
		var code = selObj.get("groupCode");
		if(code ===  "G"){
			var expanded = selObj.expanded;
			if(expanded != null && !expanded){
				kendoKipHelperInstance.popupWarning("Parent must be expanded before deleting!", "Error");
				kendoKipHelperInstance.closeHelperKendoWindow();
				return;
			}
			var parentId =  selObj.get("id"); 
			while(this.getNumberOfChildrenForParent(parentId, treeListDS) > 0){
				this.runRemoveLoop(parentId, treeListDS);
			}
		}
		treeListDS.remove(selObj);
		treeListDS.sync();
		kendoKipHelperInstance.closeHelperKendoWindow();
	},
	getNumberOfChildrenForParent : function(id , ds){
		if(id == null || ds == null ) return 0;
		var len = ds.total();
		var numberOfChildren = 0;
		for(var i=0; i<len; i++){
			var objAt = ds.at(i);
			if(objAt != null && objAt !== "undefined"){
				if(objAt.get("hasChildren") === false && objAt.get("parentId") != null && objAt.get("parentId") === id){
					numberOfChildren++;
				}
			}
		}
		return numberOfChildren;
	},
	runRemoveLoop : function(parentId, treeListDS){
		for(var i=0; i<treeListDS.total(); i++){
			var objAt = treeListDS.at(i);
			if(objAt != null && objAt !== "undefined"){
				if(objAt.get("hasChildren") === false && objAt.get("parentId") != null && objAt.get("parentId") === parentId){
					treeListDS.remove(objAt);
					treeListDS.sync();
				}
			}
		}
	},
	ajaxPostDataForAddClass  : function(form){
		console.log("Binding submit form data...");
		var formData = new FormData(form[0]); 
		  kendoKipHelperInstance.closeMeasuredWindow();
		  kendoKipHelperInstance.popupWarning("Adding class to selected group...", "Success");
		  console.log("data>>" + JSON.stringify(formData));
		  var url = $("#measuredWindow popup_itemGroups_Add_Class_editor_Form").attr('action');
		  console.log("URL >> " + url);
	  		 $.ajax({
	  		    url: url,
	  		    type: 'POST',
	  		    data: formData,
	  		    contentType : false,
	  		    processData: false
	  		}).done(function(data){
	  			var json = jQuery.parseJSON(data);
	  			kendoKipHelperInstance.popupWarning("Successfully added class!", "Success");
	  		}).fail(function (data){
	  			kendoKipHelperInstance.popupWarning(data.statusText + "(" + data.status + ")", "Error");
	  		});
		console.log("Posting data for add class form");
	},
	showPopupToDeleteAddress : function(message, msgType){
		kendoKipHelperInstance.popupWarning(message, msgType);
	},
	loadAllChildNodes : function(){
		/* treeList seems to load only groups and not corresponding children */
		console.log("Loading children...");
		var treeList = $("#itmGroupTreeList").data("kendoTreeList");
		var treeListDS = treeList.dataSource;
		treeListDS.fetch();
		var total = treeListDS.total();
		console.log("total in Datasource >>  " + total);
		treeListDS.fetch().then(function() {
		    for(var i=0; i<total ; i++){
		    	var root = treeListDS.at(i);
		    	var hasChildren = root.get("hasChildren");
		    	console.log("hasChildren" >> hasChildren);
		    	if(hasChildren){
		    		treeListDS.load(root);
		    	}
			}
		 });
		treeList.refresh();
	},
	addNewGroup : function(){
		this.treeList.addRow();
	},
	getItemClassWithGroupId : function(id){
		var itemGroup = new ItemGroupModel({
			"id" : 0, 
			"parent" : id, 
			"name" : "",
			"description" : "", 
			"statText" : "", 
			"groupCode" :  "C", 
			"parentId": id
		});
		return itemGroup;
	},
	refreshAndReadTreeList : function(){
		var treeList_ = $("#itmGroupTreeList").data("kendoTreeList");
		treeList_.dataSource.read();
		treeList_.refresh();
	},
	showPopupToSave : function(){
		
	},
	
	addClassToGivenId : function(id){
		var groupName = $("#itmGroupTreeList").data("kendoTreeList").dataSource.get(id).get("name");
		var templateHTML = $("#popup_itemGroups_Add_Class_editor").html();
		templateHTML = templateHTML.replace("#=data.parentIdText#", id);
		templateHTML = templateHTML.replace("#=data.parentId#", id);
		templateHTML = templateHTML.replace("#=data.groupCode#", "C");  
        kendoKipHelperInstance.showPercentMeasuredOKCancelTitledPopup(templateHTML, "Add Class To Group (" + groupName + ")", 50, 30 );
        $("#measuredWindow popup_itemGroups_Add_Class_editor_Form" ).submit(function(e){
        	e.preventDefault();
        	itemGroupVM.ajaxPostDataForAddClass($(this));
        });
	},
	onEditKendoTreeList : function(e){
		console.log(JSON.stringify(e)); 
		$(e.container).parent().css({
	        width: '700px',
	        height: '800px'
	    });
	    e.container.find("input[name=name]").attr("maxlength", 10);
	},
	createKendoTreeList : function(){
		console.log("Creating tree list...");
		var vm = this;
		$("#itmGroupTreeList").kendoTreeList({ 
			toolbar : kendo.template($("#itemGroupToolBarTemplate").html()),
			dataSource : itemGroupTreeListDS,
			height: 500,
			edit: vm.onEditKendoTreeList,
			 messages: {
				 loading: "Loading Item Groups..."
			 },
			 filterable: {
			     extra: false
			 },
			 pageable :false,
	        columns: [	
						{ field:'id', title:'ID', width:75 , template : "#=getItemGroupIdIcon()#", filterable: false },
						{ field:'name', title:'Group Name', width:250, filterable: true},
						{ field:'description', title:'Description', width:250, filterable: true },
						{ field:'statText', title:'Stats Text', width:250, filterable: true },
						{ "name": "Add Classs", "title": "Add","width": "85", template : "#=getNewAddChildClasTemplate()#", filterable: false,resizable: false  },
						{ command: [{ name: "edit", text: "Edit"}], title:"Edit",width:80 ,filterable: false },
						{ "name": "Delete", "title": "Delete","width": "80", template : "#=getItemGroupDeleteTemplate()#", filterable: false,resizable: false  }
	         ],
	          editable: {
					mode: "popup",
					template: kendo.template($("#popup_itemGroups_editor").html())
			  }
		});
		console.log("Successfully created TreeList");
		this.treeList = $("#itmGroupTreeList").data("kendoTreeList");
		//this.loadAllChildNodes();
	}
});


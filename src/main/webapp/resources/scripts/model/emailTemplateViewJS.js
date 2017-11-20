var kendoKipHelperInstance;

var kendoEditorVM = kendo.observable({
	
	isVisible : true,
	html : null,
	
	beforeInit : function(){
		console.log("Before initializing kendoEditor VM...");
		kendoKipHelperInstance = new kendoKipHelper();
		kendoKipHelperInstance.init();
		console.log("Preparing to initialized hunterAdminClientUserVM...");
	},
	
	init : function(){
		console.log("Initializing kendoEditor VM...");
		this.beforeInit();
		kendo.bind($("#kendoEditorVM"), kendoEditorVM);
		this.afterInit();
	},
	
	afterInit : function(){
		console.log("After initializing kendoEditor VM...");
	},
	onChange: function() {
        console.log("event :: change (" + kendo.htmlEncode(this.get("html")) + ")");
    },
    saveCurrentEditorContent : function(){
    	console.log("Saving contents... \n " + kendo.htmlEncode(this.get("html") ));
    },
    clearCurrentEditorContent : function(){
    	this.set("html", "");
    },
    viewCurrentEditorContent : function(){
    	$("#emailTemplateProcessed").html(this.get("html")); 
    },
    displayAllCurrentTemplates : function(){
		kendoKipHelperInstance.showBackUpPercentMeasuredOKCancelTitledPopup("", "Hunter Email Templates", "40", "60");
    },
    getTemplateHTML : function(){
    	var html = this.get("html");
    	$("#emailTemplateProcessed").text(html);
    }
	
	
});

$("document").ready(function(){
	kendoEditorVM.init();
});
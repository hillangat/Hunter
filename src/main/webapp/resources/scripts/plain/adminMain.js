var kendoKipHelperInstance = null;

var adminMainVM = kendo.observable({
	
	hunterMainTabstrip : null,
	
	init : function(){
		console.log("Initializing hunterAdminMain VM...");
		this.beforeInit();
		kendo.bind($("#hunterAdminMainCover"), adminMainVM);
		this.afterInit();
		console.log("Successfully initialized hunterAdminMain VM!!"); 
	},
	beforeInit : function(){
		kendoKipHelperInstance = new kendoKipHelper();
		kendoKipHelperInstance.init();
	},
	afterInit : function(){
		this.createHunterMainTabStrips();
	},
	createHunterMainTabStrips : function(){
		 var hunterMainTabstrip_ = $("#hunterAdminMainTab").kendoTabStrip({
            animation: { 
           	 open: { effects: "fadeIn"} 
            },
            contentLoad: function(e){
            	
            },
            activate: function(e){
            	 
            },
            show : function(e) {
            	registerNavigation("My Hunter", $(e.item).find("> .k-link").text());
            },
            contentUrls: [
                          '/Hunter/admin/action/templates/coreConfigInfoTab',
                          '/Hunter/admin/action/templates/hunterClients',
                          '/Hunter/admin/action/templates/userInforTab',
                          '/Hunter/admin/action/templates/taskConfigTab',
                          '/Hunter/admin/action/templates/hunterCacheTab'
                      ]
        }).data("kendoTabStrip");
		 adminMainVM.set("hunterMainTabstrip", hunterMainTabstrip_);
	}
	
});

$("document").ready(function(){
	adminMainVM.init();
});
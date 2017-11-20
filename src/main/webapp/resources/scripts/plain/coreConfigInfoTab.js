
$("document").ready(function(){
	
	var hunterMainTabstrip_ = $("#hunterMainCoreConfigTab").kendoTabStrip({
        animation: { 
       	 open: { effects: "fadeIn"} 
        },
        show : function(e) {
        	registerNavigation("My Hunter", $(e.item).find("> .k-link").text());
        },
        contentUrls: [
                      '/Hunter/admin/action/templates/roles',
                      '/Hunter/admin/action/templates/msgAttachments'
                  ]
    }).data("kendoTabStrip");
		 
		 setTimeout(function(){
			 hunterMainTabstrip_.select(0);
		 }, 300);
	
});
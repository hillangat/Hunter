
$("document").ready(function(){
	
	var hunterMainTabstrip_ = $("#taskConfigTab").kendoTabStrip({
        animation: { 
       	 open: { effects: "fadeIn"} 
        },
        show : function(e){
        	registerNavigation("My Hunter", $(e.item).find("> .k-link").text());
        },
        contentUrls: [
                      '/Hunter/admin/action/templates/emailTemplatesObjTab',
                      '/Hunter/admin/action/templates/templateTextEditor'
                  ]
    }).data("kendoTabStrip");
		 setTimeout(function(){
			 hunterMainTabstrip_.select(0);
		 }, 300);
	
});
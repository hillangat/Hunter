<script src="/Hunter/static/resources/scripts/model/emailTemplateViewJS.js"></script>

<div id="kendoEditorVM" >

<div style="width:96%;margin-left:2%;padding:10px" id='emailEditTemplateCover' >
	<table style="width:100%;table-layout:fixed;border:1px solid #B8D5EB;border-radius:5px;">
		<tr style="height:100%">
			<td style="width:50%;height:450px">
				<div style="width:100%;height:450px;border-radius:5px">
					<textarea style="height:100%;width:100%;position:relative" 
						data-role='editor' 
						data-bind="value : html, visible : isVisible"
						data-tools=
								"[
								    'bold',
									'italic',
									'underline',
									'strikethrough',
									'superscript',
									'subscript',
									'justifyCenter',
									'justifyLeft',
									'justifyRight',
									'justifyFull',
									'insertUnorderedList',
									'insertOrderedList',
									'indent',
									'outdent',
									'createLink',
									'unlink',
									'insertImage',
									'insertFile',
									'insertHtml',
									'fontName',
									'fontNameInherit',
									'fontSize',
									'fontSizeInherit',
									'formatBlock',
									'formatting',
									'style',
									'viewHtml',
									'emptyFolder',
									'uploadFile',
									'orderBy',
									'orderBySize',
									'orderByName',
									'invalidFileType',
									'deleteFile',
									'overwriteFile',
									'directoryNotFound',
									'imageWebAddress',
									'imageAltText',
									'fileWebAddress',
									'fileTitle',
									'linkWebAddress',
									'linkText',
									'linkToolTip',
									'linkOpenInNewWindow',
									'dialogInsert',
									'dialogUpdate',
									'dialogCancel',
									'dialogCancel',
									'createTable',
									'addColumnLeft',
									'addColumnRight',
									'addRowAbove',
									'addRowBelow',
									'deleteRow',
									'deleteColumn'
                                   
                                   ]"
					>asdfasdf</textarea>
				</div>
			</td>
		</tr>
	</table>
	<div style="border:1px solid #ccd9e4;border-radius:5px;vertical-align:middle;padding:15px;margin-top:2px;" class='k-header' >
		<button class='k-button' data-bind="events: { click : viewCurrentEditorContent }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"  ><span class="k-icon k-i-search"></span>View</button>
		<button class='k-button' data-bind="events: { click : saveCurrentEditorContent }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"  ><span class="k-icon k-i-tick"></span> Save</button>
		<button class='k-button' data-bind="events: { click : clearCurrentEditorContent }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-cancel"></span>Clear</button>
		<button class='k-button' data-bind="events: { click : clearCurrentEditorContent }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-arrow-w"></span>Close</button>
		<button class='k-button' data-bind="events: { click : displayAllCurrentTemplates }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-funnel "></span>Templates</button>
		<button class='k-button' data-bind="events: { click : getTemplateHTML }" style="background-color:rgb(212,239,249);border : 1px solid rgb(120,186,210);"><span class="k-icon k-i-funnel "></span>HTML</button>
	</div>
	
</div>

</div>

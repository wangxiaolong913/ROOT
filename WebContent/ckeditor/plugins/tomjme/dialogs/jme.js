var JMEditor = {};

//var JMEditor = parent.JMEditor || {};
JMEditor = {
		versionCode : 5,
		versionName : "V0.9.5",
		ckEditor : CKEDITOR,
		jmeBasePath : "",
		//defaultFontSize : "20px",
		isEmpty : function(elementId){
			return ($("#" + elementId).html()+"").replace(/(<[^>]*>|\s|&nbsp;)/ig,"").length < 1;
		},
		html : function(elementId){
			//alert(elementId);
			return $("#" + elementId).html();
		}
};


CKEDITOR.dialog.add( 'jmeDialog', function( editor ) {
	var jme_fid = "math_frame_" + editor.id ;
   
	return {
        title: "JMEditor " + JMEditor.versionName,
        minWidth: 500,
        minHeight: 300,
        contents: [
            {
                id: 'tab-basic',
                label: 'Editor',
                elements: [
                    {
                    	//mathquill-editable ¿É±à¼­
                    	//mathquill-rendered-math ÒÑ¾­äÖÈ¾
                    	//mathquill-embedded-latex Ö»×ö¾²Ì¬äÖÈ¾
                        type: 'html',
                        //CKEDITOR.basePath
                        //jmeditor1.0/ckeditor/plugins/jme/dialogs/mathdialog.html
                        html: '<div style="width:500px;height:300px;"><iframe id="' + jme_fid + '" style="width:500px;height:300px;" frameborder="no" src="' + CKEDITOR.basePath + 'plugins/jme/dialogs/mathdialog.html"></iframe></div>'
                    }   
                ]
            }
        ],
        onShow : function(){
        	//$("#jme-math",getIFrameDOM("math_frame")).mathquill("focus");
        },
        onOk: function() {
        	var thedoc = getIFrameDOM(jme_fid);
			var style = '<link href="' + JMEditor_BasePath + 'mathquill-0.9.1/mathquill.css" rel="stylesheet" type="text/css" />';
			//alert(editor.document.getHead().getHtml());
			if(editor.document.getHead().getHtml().indexOf("mathquill.css") < 0){
				editor.document.appendStyleSheet( JMEditor_BasePath + 'mathquill-0.9.1/mathquill.css' );
			}
			//alert(1);
			/*
			if($("#cke_2_contents>iframe",parent.document.body).contents().find("head").html().indexOf("mathquill.css") < 0 ){
				$("#cke_2_contents>iframe",parent.document.body).contents().find("head").append(style);
			}
			*/
			//alert($("#cke_2_contents>iframe",parent.document.body).contents().find("head").html());
        	
			var str = $("#jme-math",thedoc).html().replace('<span class="textarea"><textarea></textarea></span>',"") ;
			var mathHTML = '<span class="mathquill-rendered-math" style="font-size:' + JMEditor.defaultFontSize + ';" >' + str + '</span><span>&nbsp;</span>';
			//alert(mathHTML);
            editor.insertHtml(mathHTML);
			return;			
        }
    };
});

function getIFrameDOM(fid){
	var fm = getIFrame(fid);
	return fm.document||fm.contentDocument;
}
function getIFrame(fid){
	return document.frames ? document.frames[fid] : document.getElementById(fid);
}

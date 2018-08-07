(function() {  
    CKEDITOR.dialog.add("tomjme",   
    function(a) {  
        return {  
            title: "tomjme",  
            minWidth: "500px",  
            minHeight:"500px",  
            contents: [{  
                id: "tab1",  
                label: "",  
                title: "",  
                expand: true,  
                width: "500px",  
                height: "500px",  
                padding: 0,  
                elements: [{  
                    type: "html",  
                    style: "width:500px;height:500px",  
                    html: '<div style="width:500px;height:300px;"><iframe id="tomjmeiframe" style="width:500px;height:300px;" frameborder="no" src="' + CKEDITOR.basePath + 'plugins/tomjme/dialogs/mathdialog.html"></iframe></div>'
                }]  
            }],  
            onOk: function() {  
				var thedoc = getIFrameDOM("tomjmeiframe");
				var JMEBASEPATH = CKEDITOR.basePath + 'plugins/tomjme/';
				//alert(111);
				//console.log(CKEDITOR.document.getHead().getHtml());

				if(CKEDITOR.document.getHead().getHtml().indexOf("mathquill.css") < 0){
					CKEDITOR.document.appendStyleSheet( JMEBASEPATH + 'dialogs/mathquill-0.9.1/mathquill.css' );
				}
				
				var str = $("#jme-math",thedoc).html().replace('<span class="textarea"><textarea></textarea></span>',"") ;
				var mathHTML = '<span class="mathquill-rendered-math" style="font-size:14px;" >' + str + '</span><span>&nbsp;</span>';
				//alert(mathHTML);
				var element = CKEDITOR.dom.element.createFromHtml(mathHTML);
				a.insertElement( element );
				//a.insertHtml(mathHTML);
				return;
            }  
        }  
    })  
})();  

function getIFrameDOM(fid){
	var fm = getIFrame(fid);
	return fm.document||fm.contentDocument;
}

function getIFrame(fid){
	return document.frames ? document.frames[fid] : document.getElementById(fid);
}
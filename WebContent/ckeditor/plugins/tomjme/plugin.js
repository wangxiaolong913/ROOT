(function() {  
    CKEDITOR.plugins.add("tomjme", {  
        requires: ["tomjme"],  
        init: function(a) {  
            a.addCommand("tomjme", new CKEDITOR.dialogCommand("tomjme"));  
            a.ui.addButton("tomjme", {  
                label: "tomjme",//调用dialog时显示的名称  
                command: "tomjme",  
                icon: this.path + "icon.png"//在toolbar中的图标  
   
            });  
            CKEDITOR.dialog.add("tomjme", this.path + "dialogs/tomjme.js")  
   
        }  
   
    })  
   
})();  
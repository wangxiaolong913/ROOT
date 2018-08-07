<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.news.add" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<link rel="stylesheet" type="text/css" href="inc/js/uploadify/uploadify.css" />  
	<script type="text/javascript" src="inc/js/uploadify/jquery.uploadify.min.js"></script>

	<link rel="stylesheet" href="inc/js/jquery-validation-engine/css/validationEngine.jquery.css" />
	<script src="inc/js/jquery-validation-engine/js/jquery.validationEngine.js"></script>
	
	<tomtag:Util action="lang" data="zh_CN">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-zh_CN.js"></script>
	</tomtag:Util>

	<tomtag:Util action="lang" data="en">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-en.js"></script>
	</tomtag:Util>

	<link rel="stylesheet" href="inc/js/colorpicker/css/jquery.cxcolor.css" />
	<script src="inc/js/colorpicker/js/jquery.cxcolor.min.js"></script>

	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<script type="text/javascript">

		var editor_content = null;

		window.onload = function(){

			jQuery('#form_news_add').validationEngine();
			editor_content = CKEDITOR.replace('n_content'); 

			tm_uploadify('btn_photo', '<tomtag:Message key="txt.other.operations.browse" />', '${tm_base}', function(data){
				if(data.code == "y"){
					$(".tm_img_preview").show();
					$("#n_photo").val(data.name);
					$(".tm_img_preview img").attr("src", '${tm_base}' + data.name);
				}
			});

			$('#n_title_color').cxColor();

		}

	</script>
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.news.add" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.news.add" /></h1>
                <span><tomtag:Message key="txt.sys.news.addtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/news/add.thtml" class="on"><tomtag:Message key="txt.sys.news.add" /></a>
				<a href="system/news/list.thtml"><tomtag:Message key="txt.sys.news.list" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="system/news/save.do" method="post" id="form_news_add">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
					<tr>
                        <th width="120"><tomtag:Message key="txt.sys.news.fields.title" /> : </th>
                        <td colspan="3">
							<input type="text" name="n_title" class="validate[required] tm_txt" size="80" maxlength="50" />
							<input id="n_title_color" name="n_title_color" type="text" class="input_cxcolor" readonly>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.news.fields.photo" /> : </th>
                        <td colspan="3">
							<div class="tm_img_preview" style="display:none"><img src="" style="cursor:pointer" onclick="tm_showPic(this.src)" /><a href="javascript:;" onclick="tm_removePhoto('n_photo')"></a></div>
							<input type="button" id="btn_photo" />
							<input type="hidden" name="n_photo" id="n_photo" />
						</td>
                    </tr>
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.news.fields.category" /> : </th>
                        <td width="40%">
							<select class="tm_select" name="n_classid" style="min-width:200px">
								<c:forEach var="category" items="${categories}">
								<option value="${category.c_id}">${category.c_name}</option>
								</c:forEach>
							</select>
						</td>
						<th width="120"><tomtag:Message key="txt.sys.news.fields.status" /> : </th>
                        <td width="40%">
							<select class="tm_select" name="n_status" style="min-width:200px">
                                <option value="1"><tomtag:Message key="txt.other.status.open" /></option>
                                <option value="0"><tomtag:Message key="txt.other.status.close" /></option>
                            </select>
							<span class="tm_tip"></span>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.news.fields.top" /> : </th>
                        <td>
							<select class="tm_select" name="n_totop" style="min-width:200px">
                                <option value="0"><tomtag:Message key="txt.sys.news.fields.top.n" /></option>
                                <option value="1"><tomtag:Message key="txt.sys.news.fields.top.y" /></option>
                            </select>
						</td>
						<th><tomtag:Message key="txt.sys.news.fields.visit" /> : </th>
                        <td>
							<input type="text" name="n_visit" class="validate[required,custom[integer]] tm_txt" size="50" maxlength="8" value="1" />
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.news.fields.author" /> : </th>
                        <td>
							<input type="text" name="n_author" class="tm_txt" size="50" maxlength="20" />
						</td>
						<th><tomtag:Message key="txt.sys.news.fields.from" /> : </th>
                        <td>
							<input type="text" name="n_newsfrom" class="tm_txt" size="50" maxlength="30" />
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.news.fields.content" /> : </th>
                        <td colspan="3">
							<textarea name="n_content" rows="5" cols="40" class="validate[required]"></textarea>
						</td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td colspan="3">
                        	<button class="tm_btn tm_btn_primary" type="submit"><tomtag:Message key="txt.other.operations.submit" /></button>
							<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>

			<input type="hidden" name="n_poster" value="${sessionScope.SEN_USERNAME}" />
			</form>
        </div>
        
        
    </div>

</body>
</html>

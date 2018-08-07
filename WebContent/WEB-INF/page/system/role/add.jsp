<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.role.add" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<link rel="stylesheet" href="inc/js/jquery-validation-engine/css/validationEngine.jquery.css" />
	<script src="inc/js/jquery-validation-engine/js/jquery.validationEngine.js"></script>
	
	<tomtag:Util action="lang" data="zh_CN">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-zh_CN.js"></script>
	</tomtag:Util>

	<tomtag:Util action="lang" data="en">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-en.js"></script>
	</tomtag:Util>

	<script type="text/javascript">
		$(document).ready(function() {
			jQuery('#form_role_add').validationEngine();
		});

		function tmModuleSelector(obj, level){
			var is_me_checked = $(obj).is(':checked') ;

			if(level == 1) {
				if(is_me_checked){
					$(obj).parent().parent().nextAll().find("input").prop("checked", true);
				}else{
					$(obj).parent().parent().nextAll().find("input").prop("checked", false);
				}
			}else if(level == 2) {
				if(is_me_checked){
					$(obj).parent().parent().parent().parent().find("legend").eq(0).find("input").prop("checked", true);//上级
					$(obj).parent().parent().parent().find("input").prop("checked", true);//下级

				}else{
					$(obj).parent().parent().parent().find("input").prop("checked", false);//下级

					var hasChecked = false;
					$(obj).parent().parent().parent().parent().find(".fieldset_b").find("input").each(function(){
						var ss = $(this).is(':checked');
						if(ss){
							hasChecked = true;
						}
					});
					if(!hasChecked){
						$(obj).parent().parent().parent().parent().find("input").prop("checked", false);//上级
					}
				}
			}else if(level == 3) {
				if(is_me_checked){
					$(obj).parent().parent().find("legend").find("input").prop("checked", true);//上级
					$(obj).parent().parent().parent().find("legend").eq(0).find("input").prop("checked", true);//上上级
				}else{
					var hasChecked = false;
					//上级
					$(obj).parent().parent().find(".tm_pr_c").each(function(){
						var ss = $(this).is(':checked');
						if(ss){
							hasChecked = true;
						}
					});
					if(!hasChecked){
						$(obj).parent().parent().find(".tm_pr_b").prop("checked", false);//上级
					}
					
					//上上级
					hasChecked = false;
					$(obj).parent().parent().parent().find(".tm_pr_b").each(function(){
						var ss = $(this).is(':checked');
						if(ss){
							hasChecked = true;
						}
					});
					if(!hasChecked){
						$(obj).parent().parent().parent().find(".tm_pr_a").prop("checked", false);//上级
					}
				}
			}
		}

	</script>


	<style>
		fieldset{border:solid 1px #eee;}
		.fieldset_a{margin:0 0 15px 0;}
		.fieldset_a legend{font-weight:bold}
		.fieldset_b{margin:0 0 5px 0;}
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.role.add" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.role.add" /></h1>
                <span><tomtag:Message key="txt.sys.role.addnavtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/role/add.thtml" class="on"><tomtag:Message key="txt.sys.role.add" /></a>
				<a href="system/role/list.thtml"><tomtag:Message key="txt.sys.role.list" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="system/role/save.do" method="post" id="form_role_add">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.role.fields.rolename" /> : </th>
                        <td>
							<input type="text" name="r_name" class="validate[required] tm_txt" size="50" maxlength="50" />
							<span class="tm_required">*</span> 
							<span class="tm_tip"><tomtag:Message key="txt.sys.role.fields.rolename.tip" /></span> 
						</td>
                    </tr>
                    <tr>
                        <th><tomtag:Message key="txt.sys.role.fields.status" /> : </th>
                        <td>
                            <select class="tm_select" name="r_status" style="min-width:150px">
                                <option value="1"><tomtag:Message key="txt.other.status.normal" /></option>
                                <option value="-1"><tomtag:Message key="txt.other.status.lock" /></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th valign="top"><tomtag:Message key="txt.sys.role.fields.privilege" /> : </th>
                        <td>
							<c:forEach var="module" items="${modules}">
							<fieldset class="fieldset_a">
								<legend>
									<label><input type="checkbox" name="r_privilege" class="tm_pr_a" value="${module.code}" onclick="tmModuleSelector(this,1)" />${module.text}</label>
								</legend>
								<c:forEach var="function" items="${module.list}">
								<fieldset class="fieldset_b">
									<legend>
										<label><input type="checkbox" name="r_privilege" class="tm_pr_b" value="${function.code}" onclick="tmModuleSelector(this,2)" />${function.text}</label>
									</legend>
									<c:forEach var="func" items="${function.list}">
										<label><input type="checkbox" name="r_privilege" class="tm_pr_c" value="${func.code}" onclick="tmModuleSelector(this,3)" />${func.text}</label>
									</c:forEach>
								</fieldset>
								</c:forEach>
							</fieldset>
							</c:forEach>
                        </td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<button class="tm_btn tm_btn_primary" type="submit"><tomtag:Message key="txt.other.operations.submit" /></button>
							<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>
			</form>
        </div>
        
        
    </div>

</body>
</html>

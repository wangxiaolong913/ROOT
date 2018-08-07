<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.profile.update" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<link rel="stylesheet" type="text/css" href="skins/default/other.css" />
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
	
	<script type="text/javascript">
		$(document).ready(function() { 
			tm_uploadify('btn_photo','<tomtag:Message key="txt.other.operations.browse" />', '${tm_base}', function(data){
				if(data.code == "y"){
					$(".tm_img_preview").show();
					$("#a_photo").val(data.name);
					$(".tm_img_preview img").attr("src", '${tm_base}' + data.name);
				}
			});
			tm_bindPasswordLevelChecker("a_userpass");
		});

		var tmProfile = {
			doUpdate : function(){
				var formcheck = $("#form_admin_load").validationEngine('validate');
				if(formcheck){
					var wcm = window.confirm('<tomtag:Message key="message.user.profile.confirm" />');
					if(!wcm){
						return;
					}
					
					$("#form_admin_load").attr("action","${tm_base}system/profile/update.do");
					$("#form_admin_load").submit();
				}else{
					return false;
				}
			}
		};
	</script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.user.profile.update" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.profile.update" /></h1>
                <span><tomtag:Message key="txt.user.profile.updatetip" /></span>
            </div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form method="post" id="form_admin_load">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.admin.fields.username" /> : </th>
                        <td>
							<input type="text" name="a_username" class="validate[required] tm_txt tm_bg_readonly" size="50" readonly="readonly" maxlength="30" value="${admin.a_username}" />
							<span class="tm_required">*</span> 
							<span class="tm_tip"><tomtag:Message key="txt.sys.admin.fields.username.tip" /></span> 
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.userpass" /> : </th>
                        <td>
							<input type="password" id="a_userpass" name="a_userpass" class="tm_txt" size="50" maxlength="30" />
							<span class="tm_tip"><tomtag:Message key="txt.sys.admin.fields.userpass.tip_nochange" /></span>
							
							<div id="tm_level" class="pw-strength">
								<div class="pw-bar"></div>
								<div class="pw-bar-on"></div>
								<div class="pw-txt">
									<span><tomtag:Message key="message.other.password.weak" /></span>
									<span><tomtag:Message key="message.other.password.middle" /></span>
									<span><tomtag:Message key="message.other.password.strong" /></span>
								</div>
							</div>

						</td>
                    </tr>
                    <tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.role" /> : </th>
                        <td>${admin.r_name}</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.realname" /> : </th>
                        <td><input type="text" name="a_realname" class="tm_txt" size="50" maxlength="30" value="${admin.a_realname}" /></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.photo" /> : </th>
                        <td>
							<c:choose>
								<c:when test="${not empty admin.a_photo}">
									<div class="tm_img_preview"><img src="${tm_base}${admin.a_photo}" style="cursor:pointer" onclick="tm_showPic(this.src)" /><a href="javascript:;" onclick="tm_removePhoto('a_photo')"></a></div>
									<input type="button" id="btn_photo" />
									<input type="hidden" name="a_photo" id="a_photo" value="${admin.a_photo}" />
								</c:when>
								<c:otherwise>
									<div class="tm_img_preview" style="display:none"><img src="" style="cursor:pointer" onclick="tm_showPic(this.src)" /><a href="javascript:;" onclick="tm_removePhoto('a_photo')"></a></div>
									<input type="button" id="btn_photo" />
									<input type="hidden" name="a_photo" id="a_photo" />
								</c:otherwise>
							</c:choose>
							
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.phone" /> : </th>
                        <td><input type="text" name="a_phone" class="tm_txt" size="50" maxlength="30" value="${admin.a_phone}" /></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.email" /> : </th>
                        <td><input type="text" name="a_email" class="tm_txt" size="50" maxlength="30" value="${admin.a_email}" /></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.status" /> : </th>
                        <td>
							<c:choose>
								<c:when test="${admin.a_status == '1'}"><tomtag:Message key="txt.other.status.normal" /></c:when>
								<c:when test="${admin.a_status == '0'}"><b><tomtag:Message key="txt.other.status.forcheck" /></b></c:when>
								<c:when test="${admin.a_status == '-1'}"><b><tomtag:Message key="txt.other.status.lock" /></b></c:when>
							</c:choose>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.remark" /> : </th>
                        <td><input type="text" name="a_remark" class="tm_txt" size="50" value="${admin.a_remark}" /></td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<button class="tm_btn tm_btn_primary" type="button" onclick="tmProfile.doUpdate();"><tomtag:Message key="txt.other.operations.submit" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>

			</form>
        </div>
        
        
    </div>

</body>
</html>

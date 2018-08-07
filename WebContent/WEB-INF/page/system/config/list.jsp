<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.config.list" /></title>
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

	<script type="text/javascript">
		$(document).ready(function() {
			jQuery('#form_config').validationEngine();
			
			tm_uploadify('btn_photo', '<tomtag:Message key="txt.other.operations.browse" />', '${tm_base}', function(data){
				if(data.code == "y"){
					$(".tm_img_preview").show();
					$("#sys_background").val(data.name);
					$(".tm_img_preview img").attr("src", '${tm_base}' + data.name);
				}
			});
			
			var sys_access_control = "${config.sys_access_control}";
			if(sys_access_control == "close"){
				$("#tr_access_ips").hide();
			}else{
				$("#tr_access_ips").show();
			}
		});

		function accessControl(obj){
			if($(obj).val() == "close"){
				$("#tr_access_ips").hide();
			}else{
				$("#tr_access_ips").show();
			}
		}

	</script>

	<style>
		#tr_access_ips{display:none}
		.tm_config_title{background:#f5f5f5; margin:20px 0 0px 0; line-height:35px; padding-left:10px}

		.tm_param_list{border:solid 1px #eee;}
		.tm_param_list tbody tr th{background:#fff !important; border:none; border-bottom:solid 1px #eee}
		.tm_param_list tbody tr td{border:none; border-bottom:solid 1px #eee}
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.config.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.config.list" /></h1>
                <span><tomtag:Message key="txt.sys.config.listtip" /></span>
            </div>
        </div>
        
        
		<div class="tm_container">
			<form action="system/config/update.do" method="post" id="form_config">
        	<table border="0" width="90%" class="tm_param_list">
            	<tbody>
					<tr>
						<th width="200"><tomtag:Message key="txt.sys.config.fields.sitename" /></th>
						<td>
							<input type="text" name="sys_sitename" class="validate[required] tm_txt" size="50" maxlength="50" value="${config.sys_sitename}" />
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.sys.config.fields.allow_register" /></th>
						<td>
							<select class="validate[required] tm_select" name="sys_allow_register" style="min-width:250px;">
								<option value="forbidden" <c:if test="${config.sys_allow_register == 'forbidden'}">selected</c:if>><tomtag:Message key="txt.other.status.forbidden" /></option>
                            </select>
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.sys.config.fields.allow_login" /></th>
						<td>
							<select class="validate[required] tm_select" name="sys_allow_login" style="min-width:250px;">
								<option value="allow" <c:if test="${config.sys_allow_login == 'allow'}">selected</c:if>><tomtag:Message key="txt.other.status.allow" /></option>
								<option value="forbidden" <c:if test="${config.sys_allow_login == 'forbidden'}">selected</c:if>><tomtag:Message key="txt.other.status.forbidden" /></option>
                            </select>
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.sys.config.fields.allow_exam" /></th>
						<td>
							<select class="validate[required] tm_select" name="sys_allow_exam" style="min-width:250px;">
								<option value="allow" <c:if test="${config.sys_allow_exam == 'allow'}">selected</c:if>><tomtag:Message key="txt.other.status.allow" /></option>
								<option value="forbidden" <c:if test="${config.sys_allow_exam == 'forbidden'}">selected</c:if>><tomtag:Message key="txt.other.status.forbidden" /></option>
                            </select>
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.sys.config.fields.allow_test" /></th>
						<td>
							<select class="validate[required] tm_select" name="sys_allow_test" style="min-width:250px;">
								<option value="allow" <c:if test="${config.sys_allow_test == 'allow'}">selected</c:if>><tomtag:Message key="txt.other.status.allow" /></option>
								<option value="forbidden" <c:if test="${config.sys_allow_test == 'forbidden'}">selected</c:if>><tomtag:Message key="txt.other.status.forbidden" /></option>
                            </select>
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.sys.config.fields.access_control" /></th>
						<td>
							<select class="validate[required] tm_select" name="sys_access_control" style="min-width:250px;" onchange="accessControl(this)">
								<option value="close" <c:if test="${config.sys_access_control == 'close'}">selected</c:if>><tomtag:Message key="txt.sys.config.fields.access_control.close" /></option>
								<option value="whitelist" <c:if test="${config.sys_access_control == 'whitelist'}">selected</c:if>><tomtag:Message key="txt.sys.config.fields.access_control.whitelist_on" /></option>
								<option value="blacklist" <c:if test="${config.sys_access_control == 'blacklist'}">selected</c:if>><tomtag:Message key="txt.sys.config.fields.access_control.blacklist_on" /></option>
                            </select>
						</td>
					</tr>
					<tr id="tr_access_ips">
						<th><tomtag:Message key="txt.sys.config.fields.access_ips" /></th>
						<td>
							<textarea name="sys_access_ips" rows="5" cols="80" class="tm_txtx">${config.sys_access_ips}</textarea>
							<br/>
							<div style="color:#aaa; line-height:25px"><tomtag:Message key="txt.sys.config.fields.access_ips.tip" /></div>
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.sys.config.fields.lang" /></th>
						<td>
							<select class="validate[required] tm_select" name="sys_lang" style="min-width:250px;">
								<option value="zh_CN" <c:if test="${config.sys_lang == 'zh_CN'}">selected</c:if>><tomtag:Message key="txt.sys.config.fields.lang.cn" /></option>
                            </select>
						</td>
					</tr>
					
				</tbody>
            </table>

			<div style="text-align:center; margin:20px 0;">
				<button class="tm_btn tm_btn_primary" type="submit"><tomtag:Message key="txt.other.operations.submit" /></button>
				<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
			</div>

			</form>
		</div>
        
        
    </div>

</body>
</html>

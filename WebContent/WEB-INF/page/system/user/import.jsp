<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.user.add" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	
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

			jQuery('#form_user_import').validationEngine();

		});
	</script>

	<style>
		.tm_batch_help{margin:0 0 20px 0; line-height:20px; color:#999;}
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.user.add" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.user.import" /></h1>
                <span><tomtag:Message key="txt.sys.user.importtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/user/add.thtml"><tomtag:Message key="txt.sys.user.add" /></a>
				<a href="system/user/list.thtml"><tomtag:Message key="txt.sys.user.list" /></a>
				<a href="system/user/import.thtml" class="on"><tomtag:Message key="txt.sys.user.import" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="system/user/batchImport.do" method="post" enctype="multipart/form-data" id="form_user_import">
			<div class="tm_batch_help">
				<div>
					<img src="skins/images/icos/point_yellow.png" align="absmiddle" /> 
					<tomtag:Message key="txt.sys.user.import.help" />
				</div>
				<div>
					<img src="skins/images/icos/point_green.png" align="absmiddle" /> 
					<a href="files/template/template_users.zip" target="_blank"><tomtag:Message key="txt.other.operations.gettemplate" /></a> 
					<tomtag:Message key="txt.other.operations.template_tip" />
				</div>

			</div>
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    
                    <tr>
                        <th><tomtag:Message key="txt.sys.user.fields.branch" /> : </th>
                        <td>
                            <select class="validate[required] tm_select" name="u_branchid" style="width:200px">
								<option value=""><tomtag:Message key="txt.other.operations.choose" /></option>
								<c:forEach var="branch" items="${branches}">
                                <option value="${branch.b_id}">${branch.b_name}</option>
                                </c:forEach>
                            </select>
							<span class="tm_required">*</span> 
							<span class="tm_tip"><tomtag:Message key="txt.sys.user.fields.branch.tip" /></span> 
                        </td>
                    </tr>
                    <tr>
                        <th><tomtag:Message key="txt.sys.user.fields.position" /> : </th>
                        <td>
                            <select class="validate[required] tm_select" name="u_positionid" style="width:200px">
								<option value=""><tomtag:Message key="txt.other.operations.choose" /></option>
								<c:forEach var="postion" items="${postions}">
                                <option value="${postion.p_id}">${postion.p_name}</option>
                                </c:forEach>
                            </select>
							<span class="tm_required">*</span> 
							<span class="tm_tip"><tomtag:Message key="txt.sys.user.fields.position.tip" /></span>
                        </td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.user.fields.file" /> : </th>
                        <td>
							<input type="file" name="u_file" class="validate[required]" />
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

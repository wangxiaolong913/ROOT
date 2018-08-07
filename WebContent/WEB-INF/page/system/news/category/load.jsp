<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="message.sys.news.category.update" /></title>
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
			
			jQuery('#form_category_load').validationEngine();
			
		});
	</script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="message.sys.news.category.update" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.news.category.update" /></h1>
                <span><tomtag:Message key="txt.sys.news.category.updatetip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/news/category/add.thtml"><tomtag:Message key="txt.sys.news.category.add" /></a>
				<a href="system/news/category/list.thtml"><tomtag:Message key="txt.sys.news.category.list" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="system/news/category/update.do" method="post" id="form_category_load"><table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.news.category.fields.name" /> : </th>
                        <td>
							<input type="text" name="c_name" class="validate[required] tm_txt" size="50" maxlength="50" value="${category.c_name}" />
							<span class="tm_required">*</span> 
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.news.category.fields.order" /> : </th>
                        <td>
                            <input type="text" name="c_orderid" class="validate[required,custom[integer]] tm_txt" size="5" value="${category.c_orderid}" />
							<span class="tm_required">*</span> 
							<span class="tm_tip"><tomtag:Message key="txt.sys.news.category.fields.order.tip" /></span> 
                        </td>
                    </tr>
                    <tr>
                        <th><tomtag:Message key="txt.sys.news.category.fields.remark" /> : </th>
                        <td>
                            <input type="text" name="c_remark" class="tm_txt" size="50" maxlength="50" value="${category.c_remark}" />
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
			
			<input type="hidden" name="c_pid" value="0" />
			<input type="hidden" name="c_id" value="${category.c_id}" />

			</form>
        </div>
        
        
    </div>

</body>
</html>

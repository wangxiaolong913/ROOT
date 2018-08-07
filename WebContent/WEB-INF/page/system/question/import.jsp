<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.question.add" /></title>
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
			jQuery('#form_txt_import').validationEngine();
			jQuery('#form_excel_import').validationEngine();
		});
		
		function makeButtonLabelTxt(obj){
			var formcheck = $("#form_txt_import").validationEngine('validate');
			if(formcheck){
				$(obj).text('<tomtag:Message key="message.sys.question.import.ing" />');
				$(obj).attr("disabled", true);
				$('#form_txt_import').submit();
			}
		}

		function makeButtonLabelExcel(obj){
			var formcheck = $("#form_excel_import").validationEngine('validate');
			if(formcheck){
				$(obj).text('<tomtag:Message key="message.sys.question.import.ing" />');
				$(obj).attr("disabled", true);
				$('#form_excel_import').submit();
			}
		}

	</script>

	<style>
		.tm_batch_help{margin:0 0 20px 0; line-height:20px; color:#999}
		h2{font-size:14px; font-weight:bold; line-height:30px; background:#f5f5f5; border-bottom:solid 2px #eee; padding-left:5px}
	</style>
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.question.import" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.question.import" /></h1>
                <span><tomtag:Message key="txt.sys.question.importtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/question/add.thtml"><tomtag:Message key="txt.sys.question.add" /></a>
				<a href="system/question/list.thtml"><tomtag:Message key="txt.sys.question.list" /></a>
				<a href="system/question/import.thtml" class="on"><tomtag:Message key="txt.sys.question.import" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">


			<h2><tomtag:Message key="txt.other.operations.import.txt" /></h2>
			<form action="system/question/batchImportTxt.do" method="post" enctype="multipart/form-data" id="form_txt_import">
			<div class="tm_batch_help">
				<div>
					<img src="skins/images/icos/point_yellow.png" align="absmiddle" /> 
					<tomtag:Message key="txt.sys.question.import.txthelp" />
				</div>
				<div>
					<img src="skins/images/icos/point_green.png" align="absmiddle" /> 
					<a href="files/template/template_questions_txt.zip" target="_blank"><tomtag:Message key="txt.other.operations.gettemplate.txt" /></a> 
					<tomtag:Message key="txt.other.operations.template_tip" />
				</div>
			</div>
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
					<tr>
                        <th width="120"><tomtag:Message key="txt.sys.question.fields.dbname" /> : </th>
                        <td>
							<select name="q_dbid" class="validate[required] tm_select" style="min-width:200px">
								<option value=""><tomtag:Message key="txt.sys.question.fields.dbname" /></option>
								<c:forEach var="qdb" items="${qdbs}">
								<option value="${qdb.d_id}">${qdb.d_name}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.question.fields.file" /> : </th>
                        <td>
							<input type="file" name="q_file" class="validate[required]" />
						</td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td colspan="3">
                        	<button class="tm_btn tm_btn_primary"  type="button" onclick="javascript:makeButtonLabelTxt(this)"><tomtag:Message key="txt.other.operations.submit" /></button>
							<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>
			<input type="hidden" name="q_poster" value="${sessionScope.SEN_USERNAME}" />
			</form>







			<h2><tomtag:Message key="txt.other.operations.import.excel" /></h2>
			<form action="system/question/batchImportExcel.do" method="post" enctype="multipart/form-data" id="form_excel_import">
			<div class="tm_batch_help">
				<div>
					<img src="skins/images/icos/point_yellow.png" align="absmiddle" /> 
					<tomtag:Message key="txt.sys.question.import.excelhelp" />
				</div>
				<div>
					<img src="skins/images/icos/point_green.png" align="absmiddle" /> 
					<a href="files/template/template_questions_excel.zip" target="_blank"><tomtag:Message key="txt.other.operations.gettemplate.excel" /></a> 
					<tomtag:Message key="txt.other.operations.template_tip" />
				</div>
			</div>
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
					<tr>
                        <th width="120"><tomtag:Message key="txt.sys.question.fields.dbname" /> : </th>
                        <td>
							<select name="q_dbid" class="validate[required] tm_select" style="min-width:200px">
								<option value=""><tomtag:Message key="txt.sys.question.fields.dbname" /></option>
								<c:forEach var="qdb" items="${qdbs}">
								<option value="${qdb.d_id}">${qdb.d_name}</option>
								</c:forEach>
							</select>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.question.fields.file" /> : </th>
                        <td>
							<input type="file" name="q_file" class="validate[required]" />
						</td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td colspan="3">
                        	<button class="tm_btn tm_btn_primary"  type="button" onclick="javascript:makeButtonLabelExcel(this)"><tomtag:Message key="txt.other.operations.submit" /></button>
							<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>
			<input type="hidden" name="q_poster" value="${sessionScope.SEN_USERNAME}" />
			</form>




        </div>
        
        
    </div>

</body>
</html>

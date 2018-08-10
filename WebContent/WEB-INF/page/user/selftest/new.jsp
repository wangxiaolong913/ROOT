<%@ page language="java" import="java.util.List, java.util.Map" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.selftest" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/wdatepicker/WdatePicker.js" type="text/javascript"></script>
	
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<style>
		.tm_section_setting{}
		.tm_section_table{border-collapse:collapse; margin:0 0 20px 0; border:solid 1px #eee;}
		.tm_section_table tr td{border:solid 1px #eee !important;}
		.tm_section_table tr th{border:none !important; background:#eee; }
		.tm_section_table tr th a{margin:0 auto;}

		.tm_section_template{display:none}

		.tm_usertip{margin:20px 0 20px 0; border:solid 1px #fa0; background:#ffd; padding:10px; width:500px}
	</style>

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
			tmPaper.addSection();
			jQuery('#form_paper_fastadd').validationEngine();
		});

		var tmPaper = {
			addSection : function(){
				var temp = $(".tm_section_template").html();
				$(".tm_section_setting").append(temp);
			}, 
			
			removeSection : function(obj){
				$(obj).parent().parent().parent().parent().remove()
			}
		};

		function tm_fun_disabled(){
			alert('<tomtag:Message key="message.other.function_disabled" />');
			return false;
		}

	</script>
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.user.selftest" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.selftest" /></h1>
                <span><tomtag:Message key="txt.user.selftest.newtip" /></span>
            </div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="user/selftest/newdetail.thtml" method="post" id="form_paper_fastadd">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
					<colgroup>
						<col width="15%"></col>
						<col width="85%"></col>
					</colgroup>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.name" /> : </th>
                        <td><input type="text" name="p_name" class="validate[required] tm_txt" size="50" style="width:400px" maxlength="30" /></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.duration" /> : </th>
                        <td><input type="text" name="p_duration" class="validate[required,custom[integer],min[1],max[120]] tm_txt tm_width200" size="20" maxlength="3" /> <tomtag:Message key="txt.other.units.minute" /></td>
                    </tr>
                    <tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.sectionsettings" /> : </th>
                        <td>
							<div style="margin:0 0 5px 0;">
								<input type='button' class='tm_btn' value='<tomtag:Message key="txt.sys.paper.detailconfig.addsection" />' onclick='tmPaper.addSection();' />
							</div>

							<div class="tm_section_setting"></div>

							<div class="tm_usertip">
								<tomtag:Message key="txt.user.selftest.usertip" />
							</div>

						</td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<c:choose>
								<c:when test="${sys_allow_test == 'allow'}">
									<button class="tm_btn tm_btn_primary" type="submit"><tomtag:Message key="txt.other.operations.submit" /></button>
									<button class="tm_btn" type="button" onclick="javascript:history.go(-1)"><tomtag:Message key="txt.other.operations.cancel" /></button>
								</c:when>
								<c:otherwise>
									<button class="tm_btn" type="button" onclick="tm_fun_disabled();"><tomtag:Message key="txt.other.operations.submit" /></button>
									<button class="tm_btn" type="button" onclick="javascript:history.go(-1)"><tomtag:Message key="txt.other.operations.cancel" /></button>
								</c:otherwise>
							</c:choose>
							
                        </td>
                    </tr>
                </tfoot>
            </table>

			</form>
        </div>       
    </div>

	<!-- tm_section_template -->
	<div class="tm_section_template">
		<table border="0" cellpadding="5" cellspacing="0" class="tm_section_table">
			<tr>
				<td>
					<input type="text" name="p_section_names" class="validate[required] tm_txt" style="width:400px" size="50" maxlength="20" placeholder='<tomtag:Message key="txt.sys.paper.detailconfig.sectionname" />' />
					&nbsp;
					<input type="hidden" name="p_section_remarks" class="tm_txt tm_width300" size="50" maxlength="50" />
				</td>
				<th width="50" align="center" rowspan="2">
					<a href="javascript:;" onclick="tmPaper.removeSection(this);" class="tm_ico_delete"></a>
				</th>
			</tr>
			<tr>
				<td>
					<select name="p_dbids" class="validate[required] tm_select" style="min-width:150px">
						<option value=""><tomtag:Message key="txt.sys.paper.fastadd.seldb" /></option>
						<c:forEach var="qdb" items="${qdbs}">
						<option value="${qdb.d_id}">${qdb.d_name}</option>
						</c:forEach>
					</select>

					<select name="p_qtypes" class="validate[required] tm_select" style="min-width:100px">
						<option value=""><tomtag:Message key="txt.sys.paper.fastadd.seltype" /></option>
						<option value="1"><tomtag:Message key="txt.other.questiontype.single" /></option>
						<option value="2"><tomtag:Message key="txt.other.questiontype.multiple" /></option>
						<option value="3"><tomtag:Message key="txt.other.questiontype.judgment" /></option>
						<option value="4"><tomtag:Message key="txt.other.questiontype.blank" /></option>
					</select>

					<select name="p_levels" class="validate[required] tm_select" style="min-width:100px">
						<option value=""><tomtag:Message key="txt.sys.paper.fastadd.sellevel" /></option>
						<option value="1"><tomtag:Message key="txt.other.hard.lev1" /></option>
						<option value="2"><tomtag:Message key="txt.other.hard.lev2" /></option>
						<option value="3" selected><tomtag:Message key="txt.other.hard.lev3" /></option>
						<option value="4"><tomtag:Message key="txt.other.hard.lev4" /></option>
						<option value="5"><tomtag:Message key="txt.other.hard.lev5" /></option>
					</select>

					<tomtag:Message key="txt.sys.paper.fastadd.qnums" /> : 
					<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" size="3" name="p_qnums" value="5" />

					<tomtag:Message key="txt.sys.paper.fastadd.perscore" /> : 
					<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" size="3" name="p_scores" value="1" />
				</td>
			</tr>
		</table>
	</div>
	<!-- /tm_section_template -->

</body>
</html>

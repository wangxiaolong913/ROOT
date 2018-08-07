<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.paper.fastadd" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>
	<script src="inc/js/wdatepicker/WdatePicker.js" type="text/javascript"></script>
	<style>
		.tm_section_setting{}
		.tm_section_table{border-collapse:collapse; margin:0 0 20px 0; border:solid 1px #eee;}
		.tm_section_table tr td{border:solid 1px #eee !important;}
		.tm_section_table tr th{border:none !important; background:#eee; }
		.tm_section_table tr th a{margin:0 auto;}

		.tm_section_template{display:none}
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
			},

			type : {
				'0' : "<tomtag:Message key="txt.sys.paper.fields.papertype.common.tip" />",
				'1' : "<font color=\"red\"><tomtag:Message key="txt.sys.paper.fields.papertype.random.tip" /></font>"
			},
			changeTypeTip : function(val){
				$("#tm_span_papertype_tip").html(tmPaper.type[val]);
			}
		};

	</script>
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.paper.fastadd" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.paper.fastadd" /></h1>
                <span><tomtag:Message key="txt.sys.paper.fastaddtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/paper/add.thtml"><tomtag:Message key="txt.sys.paper.add" /></a>
				<a href="system/paper/fastadd.thtml" class="on"><tomtag:Message key="txt.sys.paper.fastadd" /></a>
				<a href="system/paper/list.thtml"><tomtag:Message key="txt.sys.paper.list" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="system/paper/fastsave.do" method="post" id="form_paper_fastadd">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
					<colgroup>
						<col width="15%"></col>
						<col width="35%"></col>
						<col width="15%"></col>
						<col width="35%"></col>
					</colgroup>
                    <tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.name" /> : </th>
                        <td colspan="3"><input type="text" name="p_name" class="validate[required] tm_txt" size="50" maxlength="50" style="width:500px" /></td>
                    </tr>
                    <tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.category" /> : </th>
                        <td>
							<select class="validate[required] tm_select" name="p_cid" style="min-width:200px">
								<option value=""><tomtag:Message key="txt.other.operations.choose" /></option>
								<c:forEach var="category" items="${categorys}">
								<option value="${category.c_id}">${category.c_name}</option>
								</c:forEach>
							</select>
						</td>
						<th><tomtag:Message key="txt.sys.paper.fields.status" /> : </th>
                        <td>
							<select class="tm_select" name="p_status" style="min-width:200px">
                                <option value="1"><tomtag:Message key="txt.other.status.open" /></option>
                                <option value="0"><tomtag:Message key="txt.other.status.close" /></option>
                            </select>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.start" /> : </th>
                        <td><input type="text" name="p_starttime" class="validate[required] tm_txt tm_datepicker tm_width200" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" /></td>
						<th><tomtag:Message key="txt.sys.paper.fields.end" /> : </th>
                        <td><input type="text" name="p_endtime" class="validate[required] tm_txt tm_datepicker tm_width200" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" /></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.resultshowtime" /> : </th>
                        <td><input type="text" name="p_showtime" class="validate[required] tm_txt tm_datepicker tm_width200" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" /></td>
						<th><tomtag:Message key="txt.sys.paper.fields.duration" /> : </th>
                        <td><input type="text" name="p_duration" class="validate[required,custom[integer],min[1]] tm_txt tm_width200" size="20" maxlength="4" /> <tomtag:Message key="txt.other.units.minute" /></td>
                    </tr>
                    
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.showkey" /> : </th>
                        <td>
                        	<select class="tm_select" name="p_showkey" style="min-width:200px">
                                <option value="1"><tomtag:Message key="txt.other.status.yes" /></option>
                                <option value="0"><tomtag:Message key="txt.other.status.no" /></option>
                            </select>
                        </td>
						<th><tomtag:Message key="txt.sys.paper.fields.showmode" /> : </th>
                        <td>
                        	<select class="tm_select" name="p_showmode" style="min-width:200px">
                                <option value="1"><tomtag:Message key="txt.sys.paper.fields.showmode.whole" /></option>
                                <option value="2"><tomtag:Message key="txt.sys.paper.fields.showmode.single" /></option>
                            </select>
                        </td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.papertype" /> : </th>
                        <td>
							<select class="tm_select" name="p_papertype" style="min-width:200px" onchange="tmPaper.changeTypeTip(this.value)">
                                <option value="0"><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></option>
                                <option value="1"><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></option>
                            </select>
							<span class="tm_tip" id="tm_span_papertype_tip"><tomtag:Message key="txt.sys.paper.fields.papertype.common.tip" /></span>
						</td>
						<th><tomtag:Message key="txt.sys.paper.fields.ordertype" /> : </th>
                        <td>
							<select class="tm_select" name="p_question_order" style="min-width:200px">
                                <option value="0"><tomtag:Message key="txt.sys.paper.fields.ordertype.origin" /></option>
                                <option value="1"><tomtag:Message key="txt.sys.paper.fields.ordertype.random" /></option>
                            </select>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.total" /> : </th>
                        <td><input type="text" name="p_total_score" class="tm_txt tm_bg_readonly tm_width200" size="20" maxlength="4" value="0" readonly /></td>
						<th><tomtag:Message key="txt.sys.paper.fields.passscore" /> : </th>
                        <td><input type="text" name="p_pass_score" class="validate[required,custom[integer],min[0]] tm_txt tm_width200" size="20" maxlength="4" value="0" /></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.remark" /> : </th>
                        <td colspan="3">
							<textarea name="p_remark" rows="5" cols="80" class="tm_txtx" style="width:500px"></textarea>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.sectionsettings" /> : </th>
                        <td colspan="3">
							<div style="margin:0 0 5px 0;">
								<input type='button' class='tm_btn' value='<tomtag:Message key="txt.sys.paper.detailconfig.addsection" />' onclick='tmPaper.addSection();' />
							</div>

							<div class="tm_section_setting"></div>

						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.link_branch" /> : </th>
                        <td colspan="3">
							<c:forEach var="branch" items="${branches}">
							<label><input type="checkbox" name="ln_bid" value="${branch.b_id}" />${branch.b_name}</label>&nbsp;
							</c:forEach>
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

			<input type="hidden" name="p_poster" value="${sessionScope.SEN_USERNAME}" />
			<input type="hidden" name="p_modifyor" value="${sessionScope.SEN_USERNAME}" />
			</form>
        </div>
        
        
    </div>

	<!-- tm_section_template -->
	<div class="tm_section_template">
		<table border="0" cellpadding="5" cellspacing="0" class="tm_section_table">
			<tr>
				<td>
					<input type="text" name="p_section_names" class="validate[required] tm_txt tm_width200" size="50" maxlength="20" placeholder='<tomtag:Message key="txt.sys.paper.detailconfig.sectionname" />' />
					&nbsp;
					<input type="text" name="p_section_remarks" class="validate[required] tm_txt tm_width300" size="50" maxlength="30" placeholder='<tomtag:Message key="txt.sys.paper.detailconfig.sectionremark" />' />
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
						<option value="5"><tomtag:Message key="txt.other.questiontype.essay" /></option>
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
					<input type="text" class="validate[required,custom[integer],min[1],max[500]] tm_txt" maxlength="3" size="3" name="p_qnums" value="1" />

					<tomtag:Message key="txt.sys.paper.fastadd.perscore" /> : 
					<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" maxlength="2" size="3" name="p_scores" value="0" />
				</td>
			</tr>
		</table>
	</div>
	<!-- /tm_section_template -->

</body>
</html>

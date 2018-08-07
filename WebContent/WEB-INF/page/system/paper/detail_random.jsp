<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.paper" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<link rel="stylesheet" type="text/css" href="inc/js/jquery-ui/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" href="inc/js/pagination/pagination.css" />
    
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/wdatepicker/WdatePicker.js" type="text/javascript"></script>
    <script src="inc/js/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
    <script src="inc/js/pagination/pagination.js" type="text/javascript"></script>

	<style>
		.tm_qscore{
			background-color:#fff; border:1px solid #ccc;
			height:15px; padding:3px 5px; font-size:12px; line-height:15px; color:#555;
			vertical-align:middle; -webkit-border-radius:3px; -moz-border-radius:3px; border-radius:3px;
		}
		.tm_position_adjustment{
			float:left; margin-right:10px; margin-top:3px;
		}
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
		
		var var_sectionId = 1;
		
		$(document).ready(function() {
			tmPaper.pageInit();
			jQuery('#form_paper_detail').validationEngine();
		});

		var tmPaper = {
			uiInit : function(){
				$( ".tm_adm_paper_body_section" ).sortable({connectWith: ".tm_adm_paper_body_section"}).disableSelection();

				//绑定计算分数的方法
				$("input[name='p_scores']").unbind("change");
				$("input[name='p_scores']").bind("change", function(){
					tmPaper.countScore();
				});

				$("input[name='p_qnums']").unbind("change");
				$("input[name='p_qnums']").bind("change", function(){
					tmPaper.countScore();
				});

			},
			
			addSection : function(){
				var_sectionId++;
				//设置新值
				$("#tm_template_section input[name='p_section_ids']").val(var_sectionId);
				var html = $("#tm_template_section").html();
				$("#paper_sections").append(html);
				tmPaper.uiInit();
			},

			toggleSection : function(obj){
				var pobj = $(obj).parent().parent().parent();
				var current_ico = $(obj).attr("class");
				
				if(pobj){
					if(current_ico.indexOf("tm_ico_max")>-1){
						$(obj).removeClass("tm_ico_max");
						$(obj).addClass("tm_ico_min");
						pobj.children("dd").slideUp();
					}else{
						$(obj).removeClass("tm_ico_min");
						$(obj).addClass("tm_ico_max");
						pobj.children("dd").slideDown();
					}
				}
			},
			
			removeSection : function(obj){
				var pobj = $(obj).parent().parent().parent().parent();
				if(pobj.is("li")){
					pobj.remove();
				}else{
					pobj.parent().remove();
				}
				tmPaper.countScore();
			},

			expandSection : function(){
				$(".tm_adm_paper_body_section_dl dd ").slideDown();
				$(".tm_ico_min").removeClass("tm_ico_min").addClass("tm_ico_max");
			},

			shrinkSection : function(){
				$(".tm_adm_paper_body_section_dl dd ").slideUp();
				$(".tm_ico_max").removeClass("tm_ico_max").addClass("tm_ico_min");
			},
			
			countScore : function(){
				var totalscore = 0, passscore = 0;
				
				$("input[name='p_qnums']").each(function(i,o){
					var _nums = parseInt($(this).val());
					var _score = parseInt($(this).parent().children("input[name='p_scores']").val());

					if(!isNaN(_nums) && !isNaN(_score)){
						var _xscore = _nums * _score;
						totalscore += _xscore;
					}

				});

				passscore = Math.ceil(0.6 * totalscore);
				$("input[name='p_total_score']").val(totalscore);
				$("input[name='p_pass_score']").val(passscore);
			},

			pageInit : function(){
				tmPaper.uiInit();
			}
		};

	</script>
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="system/paper/list.thtml"><tomtag:Message key="txt.sys.paper.list" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.paper.detailconfig" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.paper.detailconfig" /></h1>
                <span><tomtag:Message key="txt.sys.paper.detailconfigtip" /></span>
            </div>
        </div>
        
        <br/>
        <div class="tm_container">

			<table border="0" width="100%" cellpadding="0" style="max-width:1200px">
				<tr>
					<!-- left -->
					<td valign="top">
						<form action="system/paper/updateDetail.do" method="post" id="form_paper_detail">
                    	<div class="tm_adm_paper">
                            <div class="tm_adm_paper_head">
                                <h1>${paper.p_name}</h1>
								<h2 style="background:#ffa; padding:5px 0;">
									<b><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></b> &nbsp;
									<tomtag:Message key="txt.sys.paper.fields.papertype.random.tip" />
								</h2>
                                <h2>
									<b><tomtag:Message key="txt.sys.paper.fields.timesetting" /></b> : ${paper.p_starttime} -- ${paper.p_endtime} &nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.duration" /></b> : ${paper.p_duration} <tomtag:Message key="txt.other.units.minute" />
								</h2>
								<h3>
									<b><tomtag:Message key="txt.sys.paper.fields.total" /></b> : 
									<input type="text" class="tm_txt" name="p_total_score" value="${paper.p_total_score}" readonly style="background:#eee" /> 
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.passscore" /></b> : 
									<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" name="p_pass_score" maxlength="4" value="${paper.p_pass_score}" /> 
								</h3>
                            </div>
							<div class="tm_adm_paper_tool">
								<button class="tm_btn" type="button" onclick="tmPaper.addSection()"><tomtag:Message key="txt.sys.paper.detailconfig.addsection" /></button>
								<button class="tm_btn" type="button" onclick="tmPaper.countScore()"><tomtag:Message key="txt.sys.paper.detailconfig.countscore" /></button>
							</div>
                            <div class="tm_adm_paper_body">
								<c:choose>
								<c:when test="${empty paperx }">  
									<ul class="tm_adm_paper_body_section" id="paper_sections">
										<li>
											<dl class="tm_adm_paper_body_section_dl">
												<dt>
													<span class="section_title"><tomtag:Message key="txt.sys.paper.detailconfig.sectionname" />：<input type="text" name="p_section_names" maxlength="30" class="validate[required] tm_txt" size="50" /></span>
													<span class="section_tools">
														<a href="javascript:void(0);" onclick="javascript:tmPaper.toggleSection(this);" class="tm_ico_delete tm_position_adjustment"></a>
													</span>
													<input type="hidden" name="p_section_ids" value="1" />
												</dt>
												<dt><tomtag:Message key="txt.sys.paper.detailconfig.sectionremark" />：<input type="text" class="validate[required] tm_txt" size="50" name="p_section_remarks" maxlength="30" /></dt>
												<dt>
													<tomtag:Message key="txt.sys.paper.fields.sectionsettings" /> :
													<span class="tm_section_setting">
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
														
														&nbsp;
														<tomtag:Message key="txt.sys.paper.fastadd.qnums" /> : 
														<input type="text" class="validate[required,custom[integer],min[1],max[500]] tm_txt" size="3" name="p_qnums" maxlength="3" value="1" />
														
														&nbsp;
														<tomtag:Message key="txt.sys.paper.fastadd.perscore" /> : 
														<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" size="3" name="p_scores" maxlength="2" value="0" />
													</span>
												</dt>
											</dl>
										</li>
									</ul>
								</c:when>

								<c:otherwise>
									<script>
										var_sectionId = 0;
									</script>
									<ul class="tm_adm_paper_body_section" id="paper_sections">
										<c:forEach var="section" items="${paperx.sections}">
										<li>
											<dl class="tm_adm_paper_body_section_dl">
												<dt>
													<span class="section_title">
														<tomtag:Message key="txt.sys.paper.detailconfig.sectionname" /> : 
														<input type="text" name="p_section_names" maxlength="30" class="validate[required] tm_txt" size="50" value="${section.name}" />
													</span>
													<span class="section_tools">
														<a href="javascript:void(0);" onclick="javascript:tmPaper.removeSection(this);" class="tm_ico_delete tm_position_adjustment"></a>
													</span>
													<input type="hidden" name="p_section_ids" value="${section.id}" />
												</dt>
												<dt>
													<tomtag:Message key="txt.sys.paper.detailconfig.sectionremark" /> : 
													<input type="text" class="validate[required] tm_txt" size="50" name="p_section_remarks" maxlength="30" value="${section.remark}" />
												</dt>
												<dt>
													<tomtag:Message key="txt.sys.paper.fields.sectionsettings" /> :
													<span class="tm_section_setting">
														<select name="p_dbids" class="validate[required] tm_select" style="min-width:150px">
															<option value=""><tomtag:Message key="txt.sys.paper.fastadd.seldb" /></option>
															<c:forEach var="qdb" items="${qdbs}">
															<option value="${qdb.d_id}" <c:if test="${section.rdbid == qdb.d_id}">selected</c:if>>${qdb.d_name}</option>
															</c:forEach>
														</select>

														<select name="p_qtypes" class="validate[required] tm_select" style="min-width:100px">
															<option value=""><tomtag:Message key="txt.sys.paper.fastadd.seltype" /></option>
															<option value="1" <c:if test="${section.rtype == '1'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.single" /></option>
															<option value="2" <c:if test="${section.rtype == '2'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.multiple" /></option>
															<option value="3" <c:if test="${section.rtype == '3'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.judgment" /></option>
															<option value="4" <c:if test="${section.rtype == '4'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.blank" /></option>
															<option value="5" <c:if test="${section.rtype == '5'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.essay" /></option>
														</select>

														<select name="p_levels" class="validate[required] tm_select" style="min-width:100px">
															<option value=""><tomtag:Message key="txt.sys.paper.fastadd.sellevel" /></option>
															<option value="1" <c:if test="${section.rlevel == '1'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev1" /></option>
															<option value="2" <c:if test="${section.rlevel == '2'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev2" /></option>
															<option value="3" <c:if test="${section.rlevel == '3'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev3" /></option>
															<option value="4" <c:if test="${section.rlevel == '4'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev4" /></option>
															<option value="5" <c:if test="${section.rlevel == '5'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev5" /></option>
														</select>
														
														&nbsp;
														<tomtag:Message key="txt.sys.paper.fastadd.qnums" /> : 
														<input type="text" class="validate[required,custom[integer],min[1],max[500]] tm_txt" size="3" name="p_qnums" maxlength="3" value="${section.rnum}" />
															
														&nbsp;
														<tomtag:Message key="txt.sys.paper.fastadd.perscore" /> : 
														<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" size="3" name="p_scores" maxlength="2" value="${section.rscore}" />
													</span>
												</dt>
											</dl>
										</li>
										<script>
											var_sectionId++;
										</script>
										</c:forEach>
									</ul>
								</c:otherwise>
								</c:choose>
                            </div>
                            <!-- /tm_adm_paper_body -->
                            
                            <div class="tm_adm_paper_foot">
                            	<button class="tm_btn tm_btn_primary" type="submit"><tomtag:Message key="txt.other.operations.submit" /></button>
								<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                            </div>

							<input type="hidden" name="pid" value="${paper.p_id}" />
							<input type="hidden" name="p_name" value="${paper.p_name}" />
							<input type="hidden" name="p_status" value="${paper.p_status}" />
							<input type="hidden" name="p_starttime" value="${paper.p_starttime}" />
							<input type="hidden" name="p_endtime" value="${paper.p_endtime}" />
							<input type="hidden" name="p_duration" value="${paper.p_duration}" />
							<input type="hidden" name="p_showtime" value="${paper.p_showtime}" />
							<input type="hidden" name="p_question_order" value="${paper.p_question_order}" />
							<input type="hidden" name="p_papertype" value="${paper.p_papertype}" />
							<input type="hidden" name="p_remark" value="${paper.p_remark}" />

							<input type="hidden" name="p_showkey" value="${paper.p_showkey}" />
							<input type="hidden" name="p_showmode" value="${paper.p_showmode}" />

							<c:forEach var="plink" items="${links}">
							<input type="hidden" name="ln_bid" value="${plink.ln_buid}" />
							</c:forEach>

							<input type="hidden" name="p_modifyor" value="${sessionScope.SEN_USERNAME}" />

                        </div>
						</form>
					</td><!-- /left -->

				</tr>
			</table>

        	
			
        </div>
        
        
    </div>


	<div id="tm_template_section" style="display:none">
		<li>
			<dl class="tm_adm_paper_body_section_dl">
				<dt>
					<span class="section_title"><tomtag:Message key="txt.sys.paper.detailconfig.sectionname" />：<input type="text" name="p_section_names" maxlength="30" class="validate[required] tm_txt" size="50" /></span>
					<span class="section_tools">
						<a href="javascript:void(0);" onclick="javascript:tmPaper.removeSection(this);" class="tm_ico_delete tm_position_adjustment"></a>
					</span>
					<input type="hidden" name="p_section_ids" value="1" />
					</dt>
					<dt><tomtag:Message key="txt.sys.paper.detailconfig.sectionremark" />：<input type="text" class="validate[required] tm_txt" size="50" name="p_section_remarks" maxlength="30" /></dt>
					<dt>
						<tomtag:Message key="txt.sys.paper.fields.sectionsettings" /> :
						<span class="tm_section_setting">
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
							<input type="text" class="validate[required,custom[integer],min[1],max[500]] tm_txt" size="3" name="p_qnums" maxlength="3" value="1" />

							<tomtag:Message key="txt.sys.paper.fastadd.perscore" /> : 
							<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" size="3" name="p_scores" maxlength="2" value="0" />
						</span>
					</dt>
				</dl>
		</li>
	</div>

</body>
</html>

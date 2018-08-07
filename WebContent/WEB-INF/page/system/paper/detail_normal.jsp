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
		
		var pager = null;
		var var_sectionId = 1;
		
		$(document).ready(function() {
			tmPaper.pageInit();
			pager = new Pagination("listnav");
			tmPaper.searchQuestions(1);
			jQuery('#form_paper_detail').validationEngine();
		});
		
		//分页回调
		function pagination_data() {
			tmPaper.searchQuestions(pager.getIndexPage());
		}

		var tmPaper = {
			uiInit : function(){
				$( ".tm_adm_questionlist" ).unbind("sortstop");

				$( ".tm_adm_questionlist" ).sortable({connectWith: ".tm_adm_questionlist"}).disableSelection();
				$( ".tm_adm_questionlist" ).bind('sortstop', function(event, ui) {
					//当前拖动的对象的父对象
					var questionUL = ui["item"].parent();
					//获取父对象的章节ID
					var section_id = questionUL.data("sectionid");
					//alert(section_id);

					//批量对下级空间更改命名
					questionUL.find("input[name^='p_question_scores_']").attr("name","p_question_scores_"+section_id);
					questionUL.find("input[name^='p_question_types_']").attr("name","p_question_types_"+section_id);
					questionUL.find("input[name^='p_question_ids_']").attr("name","p_question_ids_"+section_id);
					questionUL.find("input[name^='p_question_cnts_']").attr("name","p_question_cnts_"+section_id);
				});   
				$( ".tm_adm_paper_body_section" ).sortable({connectWith: ".tm_adm_paper_body_section"}).disableSelection();

				//绑定计算分数的方法
				$(".tm_qscore").unbind("change");
				$(".tm_qscore").bind("change", function(){
					tmPaper.countScore();
				});

			},
			
			addSection : function(){
				var_sectionId++;
				var html = [];
				html.push('<li>');
				html.push('	<dl class="tm_adm_paper_body_section_dl">');
				html.push('  <dt>');
				html.push('    <span class="section_title">');
				html.push('		 <tomtag:Message key="txt.sys.paper.detailconfig.sectionname" /> : ');
				html.push('		 <input type="text" name="p_section_names" class="validate[required] tm_txt" size="50" />');
				html.push('	   </span>');
				html.push('    <span class="section_tools">');
				html.push('      <a href="javascript:void(0);" onclick="javascript:tmPaper.toggleSection(this);" class="tm_ico_max tm_position_adjustment"></a>');
				html.push('      <a href="javascript:void(0);" onclick="javascript:tmPaper.removeSection(this);" class="tm_ico_delete tm_position_adjustment"></a>');
				html.push('    </span>');
				html.push('    <input type="hidden" name="p_section_ids" value="'+var_sectionId+'" />');
				html.push('  </dt>');
				html.push('  <dt>');
				html.push('		<tomtag:Message key="txt.sys.paper.detailconfig.sectionremark" /> : ');
				html.push('		<input type="text" class="validate[required] tm_txt" size="50" name="p_section_remarks" />');
				html.push('  </dt>');
				html.push('  <dd>');
				html.push('     <ul class="tm_adm_questionlist" data-sectionid="'+var_sectionId+'"></ul>');
				html.push('  </dd>');
				html.push(' </dl>');
				html.push('</li>');
				$("#paper_sections").append(html.join(""));
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
				$(".tm_adm_paper_body_section .tm_qscore").each(function(i,o){
					var score = parseInt($(this).val());
					totalscore += score;
					
				});
				passscore = Math.ceil(0.6 * totalscore);
				$("input[name='p_total_score']").val(totalscore);
				$("input[name='p_pass_score']").val(passscore);
			},

			removeItem : function(obj){
				var pobj = $(obj).parent().parent().parent().parent();
				if(pobj.is("li")){
					pobj.remove();
				}else{
					pobj.parent().remove();
				}
				tmPaper.countScore();
			},
			
			
			searchQuestions : function(pageid){
				var q_type = $("select[name='q_type']").val();
				var q_dbid = $("select[name='q_dbid']").val();
				var q_level = $("select[name='q_level']").val();
				var q_content = $("input[name='q_content']").val();
				if("关键词" == q_content || "Keywords" == q_content){
					q_content = "";
				}
				$("#question_list").empty();
				var surl = "system/question/query.thtml";
				$.getJSON(surl, {epage:pageid, q_type:q_type, q_dbid:q_dbid, q_level:q_level, q_content:q_content, t:Math.random()}, function(data){
					if(data){
						$.each(data["data"]["dataList"],function(idx, itm){
							var content = itm["q_content"];
							if(content && content.length>32) content = content.substring(0,32) + "..";
		
							var arrline = new Array();
							arrline.push('<li><table class="question_item">');
							arrline.push('<tr>');
							arrline.push('<td class="td1">');
							arrline.push('	<tomtag:Message key="txt.sys.paper.detailconfig.quescore" /> : ');
							arrline.push('	<input type="text" name="p_question_scores_" class="validate[required,custom[integer],min[1]] tm_qscore" value="0" size="1" maxlength="2" />');
							arrline.push('</td>');
							arrline.push('<td class="td2">');
							arrline.push(content);
							arrline.push('<input type="hidden" name="p_question_types_" value="'+itm["q_type"]+'" />');
							arrline.push('<input type="hidden" name="p_question_ids_" value="'+itm["q_id"]+'" />');
							arrline.push('<input type="hidden" name="p_question_cnts_" value="'+content+'" />');
							arrline.push('</td>');
							arrline.push('<td class="td3"><a href="javascript:void(0);" onclick="javascript:tmPaper.removeItem(this);" class="tm_ico_delete"></a></td>');
							arrline.push('</tr>');
							arrline.push('</table></li>');
							$("#question_list").append(arrline.join(""));
						});
						tmPaper.uiInit();
		
						var totalrows = parseInt(data["data"]["totalRowsCount"]);
						var perpage = parseInt(data["data"]["pageSize"]);
						var current_page = parseInt(data["data"]["currentPageNo"]);
						var total_pages = parseInt(data["data"]["totalPageCount"]);
						
						try{
							pager.setStep(1);
							pager.setTotalNum(totalrows);
							pager.setMaxNum(perpage);
							pager.setIndexPage(current_page);
							pager.initPage("pager");
						}catch(e){
							alert(e.message);
							alert(e.description);
						}
						
					}
				});
			},

			pageInit : function(){
				
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

			<table border="0" width="100%" cellpadding="0">
				<tr>
					<!-- left -->
					<td valign="top">
						<form action="system/paper/updateDetail.do" method="post" id="form_paper_detail">
                    	<div class="tm_adm_paper">
                            <div class="tm_adm_paper_head">
                                <h1>${paper.p_name}</h1>
								<h2 style="background:#ddd; padding:5px 0;">
									<b><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></b> &nbsp;
									<tomtag:Message key="txt.sys.paper.fields.papertype.common.tip" />
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
									<input type="text" class="validate[required,custom[integer],min[1]] tm_txt" name="p_pass_score" value="${paper.p_pass_score}" /> 
								</h3>
                            </div>
							<div class="tm_adm_paper_tool">
								<button class="tm_btn" type="button" onclick="tmPaper.addSection()"><tomtag:Message key="txt.sys.paper.detailconfig.addsection" /></button>
								<button class="tm_btn" type="button" onclick="tmPaper.expandSection()"><tomtag:Message key="txt.other.operations.expand" /></button>
								<button class="tm_btn" type="button" onclick="tmPaper.shrinkSection()"><tomtag:Message key="txt.other.operations.shrink" /></button>
								<button class="tm_btn" type="button" onclick="tmPaper.countScore()"><tomtag:Message key="txt.sys.paper.detailconfig.countscore" /></button>
							</div>
                            <div class="tm_adm_paper_body">
								<c:choose>
								<c:when test="${empty paperx }">  
									<ul class="tm_adm_paper_body_section" id="paper_sections">
										<li>
											<dl class="tm_adm_paper_body_section_dl">
												<dt>
													<span class="section_title">
														<tomtag:Message key="txt.sys.paper.detailconfig.sectionname" /> : 
														<input type="text" name="p_section_names" class="validate[required] tm_txt" size="50" />
													</span>
													<span class="section_tools">
														<a href="javascript:void(0);" onclick="javascript:tmPaper.toggleSection(this);" class="tm_ico_max tm_position_adjustment"></a>
													</span>
													<input type="hidden" name="p_section_ids" value="1" />
												</dt>
												<dt>
													<tomtag:Message key="txt.sys.paper.detailconfig.sectionremark" /> :
													<input type="text" class="validate[required] tm_txt" size="50" name="p_section_remarks" />
												</dt>
												<dd>
													<ul class="tm_adm_questionlist" data-sectionid="1"></ul>
												</dd>
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
														<input type="text" name="p_section_names" class="validate[required] tm_txt" size="50" value="${section.name}" />
													</span>
													<span class="section_tools">
														<a href="javascript:void(0);" onclick="javascript:tmPaper.toggleSection(this);" class="tm_ico_max tm_position_adjustment"></a>
														<a href="javascript:void(0);" onclick="javascript:tmPaper.removeSection(this);" class="tm_ico_delete tm_position_adjustment"></a>
													</span>
													<input type="hidden" name="p_section_ids" value="${section.id}" />
												</dt>
												<dt>
													<tomtag:Message key="txt.sys.paper.detailconfig.sectionremark" /> : 
													<input type="text" class="validate[required] tm_txt" size="50" name="p_section_remarks" value="${section.remark}" />
												</dt>
												<dd>
													<ul class="tm_adm_questionlist" data-sectionid="${section.id}">
														<c:forEach var="question" items="${section.questions}">
														<li><table class="question_item">
															<tr>
																<td class="td1">
																	<tomtag:Message key="txt.sys.paper.detailconfig.quescore" /> : 
																	<input type="text" name="p_question_scores_${section.id}" class="validate[required,custom[integer],min[1]] tm_qscore" maxlength="2" value="${question.score}" size="1" />
																</td>
																<td class="td2">
																	${question.content}
																	<input type="hidden" name="p_question_types_${section.id}" value="${question.type}" />
																	<input type="hidden" name="p_question_ids_${section.id}" value="${question.id}" />
																	<input type="hidden" name="p_question_cnts_${section.id}" value="${question.content}" />
																</td>
																<td class="td3"><a href="javascript:void(0);" onclick="javascript:tmPaper.removeItem(this);" class="tm_ico_delete"></a></td>
															</tr>
														</table></li>
														</c:forEach>
													</ul>
												</dd>
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

					<td width="10">&nbsp;</td>
					<!--  right-->
					<td width="450" valign="top">
						<div class="tm_question_pannel">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%" cellspacing="0">
                                <tr>
                                    <th><tomtag:Message key="txt.sys.paper.detailconfig.addtip" /></th>
                                </tr>
                                <tr>
                                    <td>
                                        <select name="q_type" class="tm_select" style="width:90px">
                                            <option value=""><tomtag:Message key="txt.sys.question.fields.type" /></option>
                                            <option value="1"><tomtag:Message key="txt.other.questiontype.single" /></option>
                                            <option value="2"><tomtag:Message key="txt.other.questiontype.multiple" /></option>
                                            <option value="3"><tomtag:Message key="txt.other.questiontype.judgment" /></option>
                                            <option value="4"><tomtag:Message key="txt.other.questiontype.blank" /></option>
                                            <option value="5"><tomtag:Message key="txt.other.questiontype.essay" /></option>
                                        </select>
                                        <select name="q_dbid" class="tm_select" style="width:120px">
                                            <option value=""><tomtag:Message key="txt.sys.question.fields.dbname" />..</option>
                                            <c:forEach var="qdb" items="${qdbs}">
                                            	<option value="${qdb.d_id}">${qdb.d_name}</option>
                                            </c:forEach>
                                        </select>
                                        <select name="q_level" class="tm_select" style="width:90px">
                                            <option value="" selected><tomtag:Message key="txt.sys.question.fields.level" /></option>
                                            <option value="1"><tomtag:Message key="txt.other.hard.lev1" /></option>
                                            <option value="2"><tomtag:Message key="txt.other.hard.lev2" /></option>
                                            <option value="3"><tomtag:Message key="txt.other.hard.lev3" /></option>
                                            <option value="4"><tomtag:Message key="txt.other.hard.lev4" /></option>
                                            <option value="5"><tomtag:Message key="txt.other.hard.lev5" /></option>
                                        </select>
                                        
                                        <input type="text" class="tm_txt" name="q_content" size="20" style="width:80px" value='<tomtag:Message key="txt.other.operations.keyword" />' />
                                        <input type="image" src="skins/images/icos/search.png" onclick="tmPaper.searchQuestions(1)" class="btn_search" style="vertical-align:middle" />
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">
                                        <!-- list -->
                                        <ul class="tm_adm_questionlist" id="question_list"></ul>
                                        <!-- list -->
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center"><div id="listnav"></div></td>
                                </tr>
                            </table>
                        </div>
                        <!-- /tm_question_pannel -->
					</td>
					<!--  /right-->
				</tr>
			</table>

        	
			
        </div>
        
        
    </div>




</body>
</html>

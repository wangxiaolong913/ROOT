<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.analysis.paper" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />

	<style>
		.div-key-container{margin:5px 25px; border:dotted 1px #ddd; padding:5px;}
		.div-key-container span{font-weight:bold}
		.div-score-maker{margin-top:5px;; text-align:center}
		.tm_user_score{background:#eee}

		.tm_qks{font-size:14px; text-decoration:underline; color:#00a; display:block; line-height:30px; font-weight:bold}
		.tm_qks:hover{background:#fff}

		.tm_question_tools{list-style:none; padding:0; margin:0; }
		.tm_question_tools li{list-style:none; padding:0 5px; margin:0; background:#eee; text-align:left; line-height:35px}

	</style>
    
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
    
	<script type="text/javascript">

		var tm_pid = "${paper.id}";


		$(document).ready(function() {
			tmUserPaper.initPaper();
			$(".tm_paper_section h1").click(function(){
				$(this).parent().children(".tm_paper_question").toggle();
			});
			tm_resetPosition();

		});

		function tm_resetPosition(){
			var nw = $(".tm_paper_head").width() + 45;
			$("#div_processor").css("left",nw + "px");
		}

		$(window).resize(function(){
			tm_resetPosition();
		});
		 
		$(window).scroll(function(){
			var tp = $(window).scrollTop();
			if(tp > 240){
				$("#div_processor").css("top", "20px");
			}else{
				var ntp = 240 - tp;
				$("#div_processor").css("top", ntp + "px");
			}
		});
	
		
		var tmUserPaper = {
			initPaper : function(){
				
			},
			
			moveToQuestion : function(qid){
				var thetop = $("#quick-Q-" + qid).offset().top;
				$("html:not(:animated),body:not(:animated)").animate({ scrollTop: thetop}, 500);
			}
		};
		
		
	</script>
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="system/analysis/paper/index.thtml"><tomtag:Message key="txt.sys.analysis.paper" /></a> <span class="divider">&gt;</span></li>
				<li class="active">${paper.name}</li>
			</ul>
		</div>
        
        <div class="tm_container">

			<table border="0" width="100%" cellpadding="0" style="min-width:860px;">
				<tr>
					<!-- left -->
					<td valign="top">
                    	<div class="tm_paper">
                            <div class="tm_paper_head">
                                <h1>${paper.name}</h1>
								<h2 style="background:#ddd; padding:5px 0; font-size:14px; font-weight:bold">
									<tomtag:Message key="txt.sys.paper.history.fields.paperinfo" />
								</h2>
                                <h2>
									<b><tomtag:Message key="txt.sys.paper.fields.timesetting" /></b> : 
									<fmt:formatDate value="${paper.starttime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" /> -- 
									<fmt:formatDate value="${paper.endtime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" /> 

									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.duration" /></b> : ${paper.duration} <tomtag:Message key="txt.other.units.minute" />
									
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.total" /></b> : ${paper.totalscore}
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.passscore" /></b> : ${paper.passscore}
								</h2>
                            </div>

                            <div class="tm_paper_body">
								
								<c:set value="0" var="varQuestionId"/>

                            	<c:forEach var="section" items="${paper.sections}">
                            	<div class="tm_paper_section">
                                	<h1>${section.name}</h1>
                                    <h2>${section.remark}</h2>
                                    
                                    <c:forEach var="question" items="${section.questions}">
									<c:set var="v_questionid" value="Q-${question.id}" />
									<c:set var="varQuestionId" value="${varQuestionId+1}"></c:set>
									<span class="span-quick-nav" data-qid="${question.id}" id="quick-Q-${question.id}"></span>

                                    <table border="0" cellpadding="0" cellspacing="0" class="tm_paper_question" style="table-layout:fixed;">
                                    	<thead>
                                        	<tr>
                                            	<th valign="top" class="tm_question_lineheight"><cite>${varQuestionId}</cite></th>
                                                <td class="tm_question_lineheight">
													<c:choose>
														<c:when test="${question.type == 4}">
															<tomtag:Util action="formatBlank" data="${question.content}" ext=" ________ "></tomtag:Util>
														</c:when>
														<c:otherwise>   
															${question.content}
														</c:otherwise> 
													</c:choose>
												</td>
												<td width="120" valign="top">
													<ul class="tm_question_tools">
														<li>
														<c:choose>
															<c:when test="${empty detail[v_questionid] }">  
																<tomtag:Message key="txt.sys.analysis.paper.accuracy" />:0%
															</c:when>
															<c:otherwise>
																<tomtag:Message key="txt.sys.analysis.paper.accuracy" />:<fmt:formatNumber value="${detail[v_questionid]}" pattern="0.00"/>%
															</c:otherwise>
														</c:choose>
														</li>
													</ul>
												</td>

                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td colspan="3">
													<c:choose>
														<c:when test="${question.type == '1'}">
															<ul>
															<c:forEach var="option" items="${question.options}">
																<li><label>${option.alisa} . ${option.text}</label></li>
															</c:forEach>
															</ul>
															<div class="div-key-container">
																<span><tomtag:Message key="txt.user.history.fields.standkey" /> :</span> ${question.key}
															</div>
														</c:when>

														<c:when test="${question.type == '2'}">
															<ul>
															<c:forEach var="option" items="${question.options}">
																<li><label>${option.alisa} . ${option.text}</label></li>
															</c:forEach>
															</ul>
															<div class="div-key-container">
																<span><tomtag:Message key="txt.user.history.fields.standkey" /> :</span> ${question.key}
															</div>
														</c:when>

														<c:when test="${question.type == '3'}">
															<div class="div-key-container">
																<span><tomtag:Message key="txt.user.history.fields.standkey" /> :</span> ${question.key}
															</div>
														</c:when>

														<c:when test="${question.type == '4'}">
															<div class="div-key-container">
																<span><tomtag:Message key="txt.user.history.fields.standkey" /> :</span> 
																<c:forEach var="blank" items="${question.blanks}">
																${blank.value}
																</c:forEach>
															</div>
														</c:when>

														<c:when test="${question.type == '5'}">
															<div class="div-key-container">
																<span><tomtag:Message key="txt.user.history.fields.standkey" /> :</span> ${question.key}
															</div>
														</c:when>
													</c:choose>

                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </c:forEach>
                                    
                                </div>
                                </c:forEach>
                                
                            </div>
                            <!-- /tm_paper_body -->
                            
                            <div class="tm_adm_paper_foot">
								<button class="tm_btn tm_btn_primary" type="button" onclick="history.go(-1)"><tomtag:Message key="txt.other.operations.back" /></button>
                            </div>


                        </div>
					</td><!-- /left -->

					<td width="10">&nbsp;</td>

					<!--  right-->
					<td width="220" valign="top">
						
					</td>
					<!--  /right-->
				</tr>
			</table>

        	
			
        </div>
        
        
    </div>


	<div id="div_processor">
		<div id="div_processor_timer" style="margin-top:5px;"></div>
		<div id="div_processor_fastto">
			<c:set value="0" var="varQuestionId"/>
	        <c:forEach var="section" items="${paper.sections}">
			<dl>
				<dt>${section.name}</dt>
				<dd>
					<c:forEach var="question" items="${section.questions}">
					<c:set var="varQuestionId" value="${varQuestionId+1}"></c:set>
						<a href="javascript:void(0);" onclick="tmUserPaper.moveToQuestion('${question.id}')" id="fast_${question.id}">${varQuestionId}</a>
					</c:forEach>
					<div class="tm_clear"></div>
				</dd>
			</dl>
			</c:forEach>
		</div>
		<div id="div_processor_ops">
			<button class="tm_btn tm_btn_primary" type="button" onclick="history.go(-1)"><tomtag:Message key="txt.other.operations.back" /></button>
		</div>
	</div>


</body>
</html>

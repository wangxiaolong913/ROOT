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
	<title><tomtag:Message key="txt.user.paper" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />

	<style>
		.div-key-container{margin:5px 25px; border:dotted 1px #ddd; padding:5px;}
		.div-key-container span{font-weight:bold}
		.div-score-maker{margin-top:5px;; text-align:center}
		.tm_user_score{background:#eee}

		.tm_qks{font-size:14px; text-decoration:underline; color:#00a; display:block; line-height:30px; font-weight:bold}
		.tm_qks:hover{background:#fff}
		
		#divsetter{position:absolute;border:none; padding:3px; display:none; background:#fff; border:solid 2px #eee; }
		.tm_lnk_score{display:block; float:left; padding:2px; border:solid 1px #f00; margin-right:2px; margin-bottom:2px; 
			width:17px; height:17px; text-align:center; text-decoration:none; background:#fff}
		.tm_lnk_score:hover{ background:#f00; color:#fff; font-weight:bold}

		.tm_question_tools{list-style:none; padding:0; margin:0; }
		.tm_question_tools li{list-style:none; padding:0; margin:0; width:40px; float:left; background:#eee; text-align:center; height:35px}
		.tm_question_tools li img{margin-top:8px;}
		.tm_question_tools li span{line-height:30px;margin:2px 2px 0 0; display:block}

	</style>
    
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
    
	<script type="text/javascript">

		var tm_pid = "${paper.id}";
		var tm_arr_markers = [];

		$(document).ready(function() {
			tmUserPaper.initPaper();
			$(".tm_paper_section h1").click(function(){
				$(this).parent().children(".tm_paper_question").toggle();
			});
			tm_resetPosition();

			//隐藏小操作面板
			$("body").click(function(){
				$("#divsetter").hide();
			});

			$("#div_processor").css("top", "20px");
		});

		function tm_resetPosition(){
			var nw = $(".tm_paper_head").width() + 45;
			$("#div_processor").css("left",nw + "px");
		}

		$(window).resize(function(){
			tm_resetPosition();
		});
		 
		$(window).scroll(function(){
			/**
			var tp = $(window).scrollTop();
			if(tp > 240){
				$("#div_processor").css("top", "20px");
			}else{
				var ntp = 240 - tp;
				$("#div_processor").css("top", ntp + "px");
			}
			**/
		});
	
		
		var tmUserPaper = {
			initPaper : function(){
				$.each(tm_arr_markers, function(idx, item){
					if(item["value"]){
					
					}else{
						$("#fast_" + item["name"]).prop("class", "wrong");
					}
				});
			},


			moveToQuestion : function(qid){
				var thetop = $("#quick-Q-" + qid).offset().top;
				$("html:not(:animated),body:not(:animated)").animate({ scrollTop: thetop}, 500);
			}
		};


		function tm_buildSetter(eid, qid, score, obj){
			var offset = $(obj).offset();
			var the_top = offset.top + 30;
			var the_left = offset.left - 2;

			var btns = '';
			btns += "<div class='tm_container' style='line-height:25px'><tomtag:Message key='txt.sys.paper.history.setscore' /></div>";

			btns += "<div class='tm_container'>";
			for(var i=0; i<=eval(score); i++){
				btns += '<a href="javascript:;" onclick="tm_setScore(\''+eid+'\',\''+qid+'\','+i+');" class="tm_lnk_score">'+i+'</a> ';
			}
			btns += "</div>";
			btns += "<div class='tm_container'></div>";
			
			$("#divsetter").css("width","100px");
			$("#divsetter").css("height","auto");
			$("#divsetter").css("top",the_top+"px");
			$("#divsetter").css("left",the_left+"px");
			$("#divsetter").html(btns);
			$("#divsetter").show();
		}
		
		function tm_setScore(eid, qid, score){
			$.ajax({
				type: "POST",
				url: "${tm_base}system/paper/history/check.do",
				data: {"eid":eid, "qid":qid, "score":score, "t":Math.random()},
				dataType: "json",
				success: function(ret){
					if("ok" == ret["code"]){
						var current_obj = $(".tm_qks[qid="+qid+"]");
						var old_score = current_obj.text();
						var total_score = $("#tm_span_score").text();
						var new_score = eval(total_score - old_score + score);

						current_obj.html(score);
						$("#tm_span_score").html(new_score);

						alert('<tomtag:Message key="message.other.op_ok" />');

					}else{
						alert('<tomtag:Message key="message.other.systembusy" />');
					}
				},
				error:function(){
					alert('<tomtag:Message key="message.other.systembusy" />');
				}
			}); 
		}

		function tm_closeDetail(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index); 
		}
		
	</script>
  </head>
  
<body>

	<div class="tm_main">
        
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
									
								</h2>
								<h2>
									<b><tomtag:Message key="txt.sys.paper.fields.total" /></b> : ${paper.totalscore}
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.passscore" /></b> : ${paper.passscore}
								</h2>
								<h2 style="background:#ddd; padding:5px 0; font-size:14px; font-weight:bold">
									<tomtag:Message key="txt.sys.paper.history.fields.userinfo" />
								</h2>
								<h2>
									<b><tomtag:Message key="txt.sys.paper.history.fields.examuser" /></b> : ${user.u_username} (${user.u_realname})

									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.history.fields.userno" /></b> : ${user.u_no}
								</h2>
								<h2>
									<b><tomtag:Message key="txt.sys.paper.history.fields.starttime" /></b> : 
									<fmt:formatDate value="${detail.e_starttime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
									
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.history.fields.endtime" /></b> : 
									<fmt:formatDate value="${detail.e_endtime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
									
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.history.fields.timecost" /></b> : 
									<c:set var="interval" value="${detail.e_endtime.time - detail.e_starttime.time}" />
									<fmt:formatNumber value= "${interval/1000/60}" pattern= "#0" />
									<tomtag:Message key="txt.other.units.minute" /> 
									
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.history.fields.score" /></b> : <span id="tm_span_score">${detail.e_score}</span>
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
												<td width="85" valign="top">
													<ul class="tm_question_tools">
														<li>
															<c:choose>
															<c:when test="${question.type == '5'}">
																<img src='skins/images/help.png' width='20' />
																<c:choose>
																	<c:when test="${check[v_questionid] > 0}">
																		<script type="text/javascript">
																			tm_arr_markers.push({"name":"${question.id}","value":true});
																		</script>
																	</c:when>
																	<c:otherwise>
																		<script type="text/javascript">
																			tm_arr_markers.push({"name":"${question.id}","value":false});
																		</script>
																	</c:otherwise> 
																</c:choose>

															</c:when>
															<c:otherwise>   
																<c:choose>
																	<c:when test="${check[v_questionid] > 0}">
																		<img src='skins/images/success.png' width='20' />
																		<script type="text/javascript">
																			tm_arr_markers.push({"name":"${question.id}","value":true});
																		</script>
																	</c:when>
																	<c:otherwise>
																		<img src='skins/images/error.png' width='20' />
																		<script type="text/javascript">
																			tm_arr_markers.push({"name":"${question.id}","value":false});
																		</script>
																	</c:otherwise> 
																</c:choose>
															</c:otherwise> 
														</c:choose>
														</li>
														<li>
															<span><a href="javascript:void(0);" class="tm_qks" qid="${v_questionid}" onmouseover="tm_buildSetter('${detail.e_id}', '${v_questionid}', '${question.score}', this);">${check[v_questionid]}</a></span>
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
																<span><tomtag:Message key="txt.user.history.fields.standkey" /> :</span>
																<tomtag:Util action="formatJudgmentAnswer" data="${question.key}"></tomtag:Util>
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

													<div class="div-key-container tm_userkey">
														<span><tomtag:Message key="txt.user.history.fields.userkey" /> :</span> 

														<c:choose>
															<c:when test="${question.type == '2'}">
																${fn:replace(data[v_questionid], '`', '')}
															</c:when>
															<c:when test="${question.type == '3'}">
																	<tomtag:Util action="formatJudgmentAnswer" data="${data[v_questionid]}"></tomtag:Util>
																</c:when>
															<c:when test="${question.type == '4'}">
																${fn:replace(data[v_questionid], '`', ' ')}
															</c:when>
															<c:otherwise>
																${data[v_questionid]}
															</c:otherwise> 
														</c:choose>

													</div>
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
								<button class="tm_btn tm_btn_primary" type="button" onclick="tm_closeDetail()"><tomtag:Message key="txt.other.operations.close" /></button>
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
			<button class="tm_btn tm_btn_primary" type="button" onclick="tm_closeDetail()"><tomtag:Message key="txt.other.operations.close" /></button>
		</div>
	</div>

	<div id="divsetter"></div>


</body>
</html>

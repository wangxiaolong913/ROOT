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
		.div-key-container{margin:5px 25px; border:dotted 0px #ddd; padding:0px;}
		.div-key-container span{font-weight:bold; clear:both}

		.div-key-container fieldset{border:solid 1px #ddd; margin-top:5px}
		.div-key-container fieldset legend{font-weight:bold}
		.div-key-container fieldset p{display:inline}

		.tm_question_tools{list-style:none; padding:0; margin:0; }
		.tm_question_tools li{list-style:none; padding:0; margin:0; width:40px; float:left; background:#eee; text-align:center; height:35px}
		.tm_question_tools li img{margin-top:8px;}
		.tm_question_tools li span{line-height:30px;}
		.tm_question_tools li span a{color:#00a; text-decoration:underline}

	</style>
    
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>
    
	<script type="text/javascript">

		var tm_pid = "${paper.id}";
		var tm_uid = "${sessionScope.SEN_USERID}";
		var tm_layerid = 0;
		var tm_arr_markers = [];


		$(document).ready(function() {
			tmUserPaper.initPaper();
			$(".tm_paper_section h1").click(function(){
				$(this).parent().children(".tm_paper_question").toggle();
			});
			tm_resetPosition();
			tmUserDataCache.removeCache(tm_uid, tm_pid);
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
	
		//创建弹出层
		function makeAddForm(qid, userkey){
			var tm_html_options = [];
			tm_html_options.push('<select class="tm_select" style="min-width:200px" name="c_tid">');
			<c:forEach var="category" items="${categories}">
			tm_html_options.push('	<option value="${category.t_id}">${category.t_name}</option>');
			</c:forEach>
			tm_html_options.push('<select>');

			var html = [];
			html.push('<div style="margin:10px">');
			html.push('	<table width="100%" cellpadding="5" border="0" class="tm_table_form">');
			html.push('	<tr>');
			html.push('		<th width="100"><tomtag:Message key="txt.user.collection.fields.typename" /></th>');
			html.push('		<td>'+tm_html_options+'</td>');
			html.push('	</tr>');
			html.push('	<tr>');
			html.push('		<th><tomtag:Message key="txt.user.collection.fields.remark" /></th>');
			html.push('		<td><input type="text" class="tm_txt" size="50" maxlength="50" name="c_remark" /></td>');
			html.push('	</tr>');
			html.push('	<tr>');
			html.push('		<th></th>');
			html.push('		<td><button class="tm_btn tm_btn_primary" type="button" onclick="tmUserPaper.doAddFav();"><tomtag:Message key="txt.other.operations.submit" /></button></td>');
			html.push('	</tr>');
			html.push('	</table>');
			html.push('</div>');
			html.push('<input type="hidden" name="c_qid" value="'+qid+'" />');
			html.push('<input type="hidden" name="c_userkey" value="'+userkey+'" />');
			return html.join("");
		}
		

		
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
			},
			
			addFavorite : function(qid, userkey){
				tm_layerid = layer.open({
					title: '<tomtag:Message key="txt.user.history.addfav_title" />',
					type: 1,
					area: ['500px', '250px'],
					shadeClose: true,
					content: makeAddForm(qid, userkey)
				});
			},
			
			doAddFav : function(){
				var c_remark = $("input[name='c_remark']").val();
				var c_tid = $("select[name='c_tid']").val();
				var c_qid = $("input[name='c_qid']").val();
				var c_userkey = $("input[name='c_userkey']").val();
				$.ajax({
					type: "POST",
					url: "${tm_base}user/collection/add.do",
					data: {"c_tid":c_tid, "c_uid":tm_uid, "c_qid":c_qid, "c_userkey":c_userkey, "c_remark":c_remark, "t":Math.random()},
					dataType: "json",
					success: function(ret){
						if(ret["code"] == "ok"){
							alert('<tomtag:Message key="message.other.op_ok" />');
						}else if(ret["code"] == "has"){
							alert('<tomtag:Message key="message.other.hasdata" />');
						}else{
							alert('<tomtag:Message key="message.other.op_failed" />');
						}
						layer.close(tm_layerid);
					},
					error:function(){
						alert('<tomtag:Message key="message.other.systembusy" />');
					}
				}); 
			}
		};

		//本地缓存操作
		var tmUserDataCache = {
			support : function(){
				if(window.localStorage){
					return true;
				}else{
					return false;
				}
			},
			removeCache : function(uid, pid){
				if(!tmUserDataCache.support()){
					return;
				}
				var cacheKey = "C" + uid + pid;
				localStorage.removeItem(cacheKey);
			}
		};
		
	</script>
  </head>
  
<body>

	<c:set var="var_nowDate">  
		<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyyMMddHHmmss" type="date"/>  
	</c:set>  
	<c:set var="var_showDate">  
		<fmt:formatDate value="${paper_basic.showtime}" pattern="yyyyMMddHHmmss" type="date"/>  
	</c:set> 
	<c:set var="var_showScore" value="${var_nowDate>=var_showDate}"></c:set>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="user/paper/history.thtml"><tomtag:Message key="txt.user.history" /></a> <span class="divider">&gt;</span></li>
				<li class="active">${paper_basic.name}</li>
			</ul>
		</div>
        
        <div class="tm_container">

			<table border="0" width="100%" cellpadding="0" style="min-width:860px;">
				<tr>
					<!-- left -->
					<td valign="top">
                    	<div class="tm_paper">
                            <div class="tm_paper_head">
                                <h1>${paper_basic.name}</h1>
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
								<h2 style="background:#ddd; padding:5px 0; font-size:14px; font-weight:bold">
									<tomtag:Message key="txt.sys.paper.history.fields.userinfo" />
								</h2>
								<h2>
									<b><tomtag:Message key="txt.sys.paper.history.fields.starttime" /></b> : 
									<fmt:formatDate value="${detail.e_starttime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
									
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.history.fields.endtime" /></b> : 
									<fmt:formatDate value="${detail.e_endtime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
									
									<c:if test="${var_showScore == true}">
										<br/><br/>
										<b><tomtag:Message key="txt.sys.paper.history.fields.timecost" /></b> : 
										<c:set var="interval" value="${detail.e_endtime.time - detail.e_starttime.time}" />
										<span class="tm_label"><fmt:formatNumber value= "${interval/1000/60}" pattern= "#0" /></span>
										<tomtag:Message key="txt.other.units.minute" /> 

										&nbsp;&nbsp;
										<b><tomtag:Message key="txt.sys.paper.history.fields.score" /></b> : <span id="tm_span_score" class="tm_label">${detail.e_score}</span>
									</c:if>
									
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
												<td width="125" valign="top">
													<c:if test="${var_showScore == true}">
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
															<span><b>${check[v_questionid]}</b></span>
														</li>
														<li><span><a href="javascript:void(0);" 
														onclick="javascript:tmUserPaper.addFavorite('${question.id}', '${data[v_questionid]}');"><tomtag:Message key="txt.user.history.addfav" /></a></span></li>
													</ul>
													</c:if>
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

															<c:if test="${var_showScore == true && paper_basic.showKey == true}">
															<div class="div-key-container">
																 <fieldset>
																	<legend><tomtag:Message key="txt.user.history.fields.standkey" /> :</legend>
																	<p>${question.key}</p>
																 </fieldset>
																 <fieldset>
																	<legend><tomtag:Message key="txt.sys.question.fields.resolve" /> :</legend>
																	<p>${question.ext}</p>
																 </fieldset>
															</div>
															</c:if>
														</c:when>

														<c:when test="${question.type == '2'}">
															<ul>
															<c:forEach var="option" items="${question.options}">
																<li><label>${option.alisa} . ${option.text}</label></li>
															</c:forEach>
															</ul>

															<c:if test="${var_showScore == true && paper_basic.showKey == true}">
															<div class="div-key-container">
																 <fieldset>
																	<legend><tomtag:Message key="txt.user.history.fields.standkey" /> :</legend>
																	<p>${question.key}</p>
																 </fieldset>
																 <fieldset>
																	<legend><tomtag:Message key="txt.sys.question.fields.resolve" /> :</legend>
																	<p>${question.ext}</p>
																 </fieldset>
															</div>
															</c:if>
														</c:when>

														<c:when test="${question.type == '3'}">
															<c:if test="${var_showScore == true && paper_basic.showKey == true}">
															<div class="div-key-container">
																<fieldset>
																	<legend><tomtag:Message key="txt.user.history.fields.standkey" /> :</legend>
																	<p><tomtag:Util action="formatJudgmentAnswer" data="${question.key}"></tomtag:Util></p>
																 </fieldset>
																 <fieldset>
																	<legend><tomtag:Message key="txt.sys.question.fields.resolve" /> :</legend>
																	<p>${question.ext}</p>
																 </fieldset>
															</div>
															</c:if>
														</c:when>

														<c:when test="${question.type == '4'}">
															<c:if test="${var_showScore == true && paper_basic.showKey == true}">
															<div class="div-key-container">
																<fieldset>
																	<legend><tomtag:Message key="txt.user.history.fields.standkey" /> :</legend>
																	<p><c:forEach var="blank" items="${question.blanks}">
																		${blank.value}
																		</c:forEach>
																	</p>
																 </fieldset>
																 <fieldset>
																	<legend><tomtag:Message key="txt.sys.question.fields.resolve" /> :</legend>
																	<p>${question.ext}</p>
																 </fieldset>
															</div>
															</c:if>
														</c:when>

														<c:when test="${question.type == '5'}">
															<c:if test="${var_showScore == true && paper_basic.showKey == true}">
															<div class="div-key-container">
																<fieldset>
																	<legend><tomtag:Message key="txt.user.history.fields.standkey" /> :</legend>
																	<p>${question.key}</p>
																 </fieldset>
																 <fieldset>
																	<legend><tomtag:Message key="txt.sys.question.fields.resolve" /> :</legend>
																	<p>${question.ext}</p>
																 </fieldset>
															</div>
															</c:if>
														</c:when>
													</c:choose>
														
													
													<div class="div-key-container tm_userkey">
														<fieldset>
															<legend><tomtag:Message key="txt.user.history.fields.userkey" /> :</legend>
															<p>
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
															</p>
														</fieldset>
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

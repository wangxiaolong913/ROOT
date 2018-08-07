<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.paper" /></title>
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

		var tm_maxtime = 0;
		var tm_timer = null;
		var tm_pid = "${paper.id}";
		var tm_uid = "${sessionScope.SEN_USERID}";
		var tm_page_confirm = false;


		$(document).ready(function() {
			tm_resetPosition();
			tmUserPaper.initPaper();
			$(".tm_paper_section h1").click(function(){
				$(this).parent().children(".tm_paper_question").toggle();
			});
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
				var stimer = "<span class='tm_label'>00:00:00</span>";
				$("#div_processor_timer").html(stimer);

				//获取剩余时间(分钟)
				tmUserPaper.getLeftTime();
				
				//绑定输入提示
				tmUserPaper.bindQuickTip();

				//自动加载本地缓存
				tmLoadUserPaperCache(tm_uid, tm_pid);
			},

			submitPaper : function(){
				var wcm = window.confirm('<tomtag:Message key="txt.user.paper.start.submit" />');
				if(!wcm){
					return;
				}

				var formcheck = $("#form_paper_detail").validationEngine('validate', {showOneMessage: true});
				if(formcheck){
					$(".tm_btn").attr("disabled","disabled");
					$(".tm_btn").val('<tomtag:Message key="txt.user.paper.tips.submiting" />');

					window.onbeforeunload = null;
					tm_page_confirm = true;
					
					$("#form_paper_detail").attr("action","${tm_base}user/paper/submitPaper.do");
					$("#form_paper_detail").submit();


				}else{
					var wcm = window.confirm('<tomtag:Message key="message.user.paper.submit.notfinish" />');
					if(!wcm){
						return;
					}
					
					$(".tm_btn").attr("disabled","disabled");
					$(".tm_btn").val('<tomtag:Message key="txt.user.paper.tips.submiting" />');

					window.onbeforeunload = null;
					tm_page_confirm = true;
					
					$("#form_paper_detail").attr("action","${tm_base}user/paper/submitPaper.do");
					$("#form_paper_detail").submit();
				}
				
			},

			moveToQuestion : function(qid){
				var thetop = $("#quick-Q-" + qid).offset().top;
				$("html:not(:animated),body:not(:animated)").animate({ scrollTop: thetop}, 500);
			},
			
			countDown : function(){
				var tm_msg;
				if(tm_maxtime>0){

					tm_msg = "<span class='tm_label'>"+ tm_fn_formatSeconds(tm_maxtime) + "</span>";
					//alert(tm_msg);
					if(tm_maxtime <= 30){
						var ss = '<tomtag:Message key="txt.user.paper.tips.submiting" />';
						tm_msg += "<br/><font color='red'><b>"+ss+"</b></font>";
					}

					$("#div_processor_timer").html(tm_msg);

					if(tm_maxtime == 5*60) {
						alert('<tomtag:Message key="txt.user.paper.tips.five_minute_warn" />');   
					}

					--tm_maxtime;

				}else{
					tm_page_confirm = true;
					clearInterval(tm_timer);   
					$("#div_processor_timer").html('<tomtag:Message key="txt.user.paper.tips.time_is_up" />');
					$("form").attr("action","${tm_base}user/paper/submitPaper.do");
					$("form").submit();
				}
			},

			getLeftTime : function(){
				$.ajax({
					type: "POST",
					url: "${tm_base}user/paper/get_left_time.do",
					data: {"pid":tm_pid, "t":Math.random()},
					dataType: "json",
					success: function(ret){
						tm_maxtime = parseInt(ret["data"]);
						if(tm_maxtime == -9){
							alert('<tomtag:Message key="txt.user.paper.tips.get_left_time_error" />');
							top.location.reload();
						}else{
							tm_timer = setInterval("tmUserPaper.countDown()",1000); 
						}
					},
					error:function(){
						alert('<tomtag:Message key="txt.user.paper.tips.get_left_time_error" />');
						top.location.reload();
					}
				}); 
			},

			bindQuickTip : function(){
				//选择题绑定
				$(".qk-choice").click(function(){
					var thename = $(this).attr("name");
					var theqid = $(this).data("qid");
					var chval = "";

					$.each($('input[name='+thename+']:checked'), function(idx, item){
						chval += $(this).val();
					});

					//增加到本地缓存
					tmUserDataCache.addCache(tm_uid, tm_pid, theqid, "choice", chval);

					if(baseutil.isNull(chval)){
						$("#fast_"+theqid).prop("class","");
					}else{
						$("#fast_"+theqid).prop("class","finished");
					}

				});
				//填空题绑定
				$(".qk-blank").blur(function(){
					var thename = $(this).attr("name");
					var theqid = $(this).data("qid");
					var charrval = [];

					$.each($('input[name='+thename+']'), function(idx, item){
						charrval.push($(this).val());
					});

					//增加到本地缓存
					tmUserDataCache.addCache(tm_uid, tm_pid, theqid, "blank", charrval);

					if(tm_checker_blanker_filled(thename)){
						$("#fast_"+theqid).prop("class","finished");
					}else{
						$("#fast_"+theqid).prop("class","");
					}

				});
				//问答题绑定
				$(".qk-txt").blur(function(){
					var thename = $(this).attr("name");
					var theqid = $(this).data("qid");
					var chval = $(this).val();

					//增加到本地缓存
					tmUserDataCache.addCache(tm_uid, tm_pid, theqid, "essay", chval);
					
					if(baseutil.isNull(chval)){
						$("#fast_"+theqid).prop("class","");
					}else{
						$("#fast_"+theqid).prop("class","finished");
					}

				});
			}
		};

		
		//填空题的输入判断
		function tm_checker_blanker_filled(n){
			var len = $("input[name='"+n+"']").length;
			var mylen = 0;
			$("input[name='"+n+"']").each(function(){
				var chval = $(this).val();
				if(baseutil.isNull(chval)){

				}else{
					mylen ++;
				}
			});
			return len == mylen;
		}
		
		//本地缓存操作
		var tmUserDataCache = {
			support : function(){
				try{
					if(window.localStorage){
						return true;
					}else{
						return false;
					}
				}catch(e){
					return false;
				}
				
			},

			addCache : function(uid, pid, qid, qtype, val){
				if(!tmUserDataCache.support()){
					return;
				}
				var cacheData = tmUserDataCache.getCache(uid, pid);
				var cacheKey = "C" + uid + pid;
				var cacheJson = [];

				try{
					if(!baseutil.isNull(cacheData)){
						cacheJson = JSON.parse(cacheData);
					}

					$(cacheJson).each(function(idx, item){
						var _name = "Q-" + qid;
						if(_name == item["name"]){
							cacheJson.splice(idx, 1);
						}
					});
					cacheJson.push({"name" : "Q-" + qid, "type" : qtype, "value" : val});
					
					var strCacheData = JSON.stringify(cacheJson);
					localStorage.setItem(cacheKey, strCacheData);

				}catch(e){
					//BROWSER DOESN'T SUPPORTED
				}

			},

			getCache : function(uid, pid){
				if(!tmUserDataCache.support()){
					return;
				}
				var cacheKey = "C" + uid + pid;
				return localStorage.getItem(cacheKey);
			}
		};

		
		//加载本地缓存
		function tmLoadUserPaperCache(uid, pid){
			var cacheData = tmUserDataCache.getCache(uid, pid);
			if(baseutil.isNull(cacheData)){
				return;
			}
			try{
				var cacheJson = JSON.parse(cacheData);
				$.each(cacheJson, function(idx, item){

					if(item["type"]=="blank"){
						$("input[name='"+item["name"]+"']").each(function(ii, iblank){
							$(this).val(item["value"][ii]);
						});

					}else if(item["type"]=="choice"){
						$("input[name='"+item["name"]+"']").val(item["value"].split(""));

					}else if(item["type"]=="essay"){
						$("textarea[name='"+item["name"]+"']").val(item["value"]);
					}
					
					try{
						var theqid = item["name"].replace("Q-","");
						$("#fast_"+theqid).prop("class","finished");
					}catch(e){}
					
				});
				
			}catch(e){
				//BROWSER DOESN'T SUPPORTED
			}
			
		}

		function tm_checkUnsubmit(){
			if(tm_page_confirm == false){ 
				return '<tomtag:Message key="message.other.paper_unsubmit" />';
			}
		}

		document.oncontextmenu= function(){return false;}

	</script>
  </head>
  
<body onbeforeunload="return tm_checkUnsubmit();" oncontextmenu="return false" >

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="user/paper/list.thtml"><tomtag:Message key="txt.user.paper" /></a> <span class="divider">&gt;</span></li>
				<li class="active">${paper.name}</li>
			</ul>
		</div>
        
        <div class="tm_container">

			<table border="0" width="100%" cellpadding="0" style="min-width:860px;">
				<tr>
					<!-- left -->
					<td valign="top">
						<form method="post" id="form_paper_detail">
                    	<div class="tm_paper">
                            <div class="tm_paper_head">
                                <h1>${paper.name}</h1>
								<h2 style="background:#ddd; padding:5px 0;">
									${paper.remark}
								</h2>
                                <h2>
									<b><tomtag:Message key="txt.sys.paper.fields.timesetting" /></b> : 
									<fmt:formatDate value="${paper.starttime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" /> -- 
									<fmt:formatDate value="${paper.endtime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" /> &nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.duration" /></b> : ${paper.duration} <tomtag:Message key="txt.other.units.minute" />
								</h2>
								<h3>
									<b><tomtag:Message key="txt.sys.paper.fields.total" /></b> : ${paper.totalscore}
									&nbsp;&nbsp;
									<b><tomtag:Message key="txt.sys.paper.fields.passscore" /></b> : ${paper.passscore}
								</h3>
                            </div>

                            <div class="tm_paper_body">
								
								<c:set value="0" var="varQuestionId"/>

                            	<c:forEach var="section" items="${paper.sections}">
                            	<div class="tm_paper_section">
                                	<h1>${section.name}</h1>
                                    <h2>${section.remark}</h2>
                                    
                                    <c:forEach var="question" items="${section.questions}">
									<c:set var="varQuestionId" value="${varQuestionId+1}"></c:set>
									<span class="span-quick-nav" data-qid="${question.id}" id="quick-Q-${question.id}"></span>

                                    <table border="0" cellpadding="0" cellspacing="0" class="tm_paper_question" style="table-layout:fixed;">
                                    	<thead>
                                        	<tr>
                                            	<th valign="top" class="tm_question_lineheight"><cite>${varQuestionId}</cite></th>
                                                <td class="tm_question_lineheight">
													<c:choose>
														<c:when test="${question.type == 4}">
															<tomtag:Util action="formatBlank" data="${question.content}" 
																ext=" <input type='text' name='Q-${question.id}' data-qid='${question.id}' class='validate[required] tm_txt qk-blank'> "></tomtag:Util>
														</c:when>
														<c:otherwise>   
															${question.content}
														</c:otherwise> 
													</c:choose>
												</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<tr>
                                            	<td colspan="2">
                                                <c:choose>
                                                    <c:when test="${question.type == 1}">
                                                        <ul>
                                                        <c:forEach var="option" items="${question.options}">
                                                            <li><label><input type="radio" class="validate[required] qk-choice" value="${option.alisa}" data-qid="${question.id}" name="Q-${question.id}" />
																${option.alisa} . ${option.text}</label></li>
                                                        </c:forEach>
                                                        </ul>
                                                    </c:when>
                                                    <c:when test="${question.type == 2}">
                                                        <ul>
                                                        <c:forEach var="option" items="${question.options}">
                                                            <li><label><input type="checkbox" class="validate[required] qk-choice" value="${option.alisa}" data-qid="${question.id}" name="Q-${question.id}" />
																${option.alisa} . ${option.text}</label></li>
                                                        </c:forEach>
                                                        </ul>
                                                    </c:when>
                                                    <c:when test="${question.type == 3}">
                                                        <ul>
															<li><label><input type="radio" class="validate[required] qk-choice" value="Y" data-qid="${question.id}" name="Q-${question.id}" />
																<tomtag:Message key="txt.sys.question.tips.yes" /></label></li>
															<li><label><input type="radio" class="validate[required] qk-choice" value="N" data-qid="${question.id}" name="Q-${question.id}" />
																<tomtag:Message key="txt.sys.question.tips.no" /></label></li>
                                                        </ul>
                                                    </c:when>
                                                    <c:when test="${question.type == 5}">
														<div class="tm_marleft20"><textarea rows="5" cols="80" data-qid="${question.id}" name="Q-${question.id}" class="validate[required] tm_txtx qk-txt"></textarea></div>
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
                            	<button class="tm_btn tm_btn_primary" type="button" onclick="tmUserPaper.submitPaper();"><tomtag:Message key="txt.other.operations.submit" /></button>
								<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                            </div>

							<input type="hidden" name="e_pid" value="${paper.id}" />
							<input type="hidden" name="e_uid" value="${sessionScope.SEN_USERID}" />

                        </div>
						</form>
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
			<input type="button" value='<tomtag:Message key="txt.other.operations.submit" />' class="tm_btn" onclick="tmUserPaper.submitPaper();" />
		</div>
	</div>


</body>
</html>

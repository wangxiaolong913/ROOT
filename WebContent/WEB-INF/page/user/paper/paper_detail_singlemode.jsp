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
		var tm_current_fake_qid = 1;
		var tm_total_questions = 0;


		$(document).ready(function() {
			tmUserPaper.navTo(1);
			tmUserPaper.initPaper();
		});

		var tmUserPaper = {
			navNext : function(){
				tm_current_fake_qid++;
				if(tm_current_fake_qid > tm_total_questions){
					tm_current_fake_qid = tm_total_questions;
				}
				tmUserPaper.navTo(tm_current_fake_qid);
			},
			navPrevious : function(){
				tm_current_fake_qid--;
				if(tm_current_fake_qid<=0){
					tm_current_fake_qid = 1;
				}
				tmUserPaper.navTo(tm_current_fake_qid);
			},
			navTo : function(fake_qid){
				$(".tm_hide_questions").hide();
				$("#qes_" + fake_qid).show();
				tm_current_fake_qid = fake_qid;

				$("#div_processor_fasttox dl dd a").each(function(idx, item){
					if($(this).data("fqid") == fake_qid){
						$(this).addClass("actived");
					}else{
						$(this).removeClass("actived");
					}
				});
			},

			initPaper : function(){
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

				var formcheck = $("#form_paper_detail").validationEngine('validate',{validateNonVisibleFields:true, showPrompts:false});
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

	<style>
		.tm_mbox{padding:10px; border:solid 1px #eee; clear:both; margin-top:30px; width:100%;}
		.tm_mbox tr th{width:50px; }
		.tm_mbox tr th cite{font-size:14px; background:#368ee0; color:#fff; font-style:normal; line-height:25px; width:50px; display:block; text-align:center;}
		.tm_mbox tr td{padding-left:20px;}

		.tm_mbox_question{line-height:25px;}
		.tm_mbox_question p{margin:0;}
		.tm_mbox_options{margin:10px 0;}
		.tm_mbox_options ul{margin:0; padding:0; list-style:none;}
		.tm_mbox_options ul li{line-height:25px;}

		.tm_mnavigator{ text-align:left; margin:20px 0 0 0;}

		.tm_hide_questions{display:none;}


		.tm_mpanel{padding:10px; border:solid 1px #eee; clear:both; width:95%;}
		#div_processor_fasttox{}
		#div_processor_fasttox dl{margin:0 0 5px 0;}
		#div_processor_fasttox dt{font-weight:bold; background:#eee; line-height:25px; padding-left:5px; font-size:12px;}
		#div_processor_fasttox dd{line-height:25px; padding:5px 0; margin:0;}

		#div_processor_fasttox a{ color:#000; display:block; float:left; padding:2px; border:solid 1px #ddd; background:#fff; 
			width:20px; margin-right:2px; margin-bottom:2px; text-decoration:none; text-align:center; font-size:12px; line-height:20px;}
		#div_processor_fasttox a:hover{ background:#fa0}
		#div_processor_fasttox a.finished{ background:#3c0; color:#fff}
		#div_processor_fasttox a.actived{ border:solid 1px #f90; color:#f00}

		#div_processor_ops{ text-align:center; padding:10px 0 0 0; border-top:solid 1px #eee; margin-top:20px;}
	</style>
  </head>
  
<body onbeforeunload="return tm_checkUnsubmit();" oncontextmenu="return false">

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="user/paper/list.thtml"><tomtag:Message key="txt.user.paper" /></a> <span class="divider">&gt;</span></li>
				<li class="active">${paper.name}</li>
			</ul>
		</div>
        
        <div class="tm_container">

        	<form method="post" id="form_paper_detail">
			<table border="0" width="100%" cellpadding="0" style="min-width:860px;">
				<tr>
					<!-- left -->
					<td valign="top">
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
                        </div>

                        <c:set value="0" var="varQuestionId"/>
                        <c:forEach var="section" items="${paper.sections}">
                        	<c:forEach var="question" items="${section.questions}">
							<c:set var="varQuestionId" value="${varQuestionId+1}"></c:set>

	                        <!-- questions-start -->
	                        <table class="tm_mbox tm_hide_questions" cellpadding="0" cellspacing="0" id="qes_${varQuestionId}">
	                        	<tr>
	                        		<th valign="top"><cite>${varQuestionId}</cite></th>
	                        		<td valign="top">
	                        			<div class="tm_mbox_question">
											<c:choose>
												<c:when test="${question.type == 4}">
													<tomtag:Util action="formatBlank" data="${question.content}" 
														ext=" <input type='text' name='Q-${question.id}' data-qid='${question.id}' class='validate[required] tm_txt qk-blank'> "></tomtag:Util>
												</c:when>
												<c:otherwise>   
														${question.content}
												</c:otherwise> 
											</c:choose>
										</div>
										<div class="tm_mbox_options">
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
													<div><textarea rows="5" cols="80" data-qid="${question.id}" name="Q-${question.id}" class="validate[required] tm_txtx qk-txt"></textarea></div>
	                                            </c:when>
	                                        </c:choose>
										</div><!-- /tm_mbox_options -->
	                        		</td>
	                        	</tr>
	                        </table>
	                        <!-- questions-end -->
							</c:forEach>
						</c:forEach>


						<script>tm_total_questions = eval(${varQuestionId});</script>

						<div class="tm_container">
							<div class="tm_mnavigator">
								<button class="tm_btn" type="button" onclick="javascript:tmUserPaper.navPrevious();"><tomtag:Message key="txt.user.paper.start.pre" /></button>
								<button class="tm_btn" type="button" onclick="javascript:tmUserPaper.navNext();"><tomtag:Message key="txt.user.paper.start.next" /></button>
							</div>
						</div>


					</td><!-- /left -->

					<td width="10">&nbsp;</td>
						
					<!--  right-->
					<td width="350" valign="top">
						<div class="tm_mpanel">
							<div id="div_processor_timer" style="margin:5px 0 10px 0; text-align:center;">
								<span class='tm_label'>00:00:00</span>
							</div>
							<div id="div_processor_fasttox">
								<c:set value="0" var="varQuestionId"/>
	                        	<c:forEach var="section" items="${paper.sections}">
	                        		<dl>
	                        			<dt>${section.name}</dt>
	                        			<dd>
	                        				<c:forEach var="question" items="${section.questions}">
												<c:set var="varQuestionId" value="${varQuestionId+1}"></c:set>
												<a href="javascript:void(0);" onclick="tmUserPaper.navTo('${varQuestionId}')" data-fqid="${varQuestionId}" id="fast_${question.id}">${varQuestionId}</a>
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
					</td>
					<!--  /right-->
				</tr>
			</table>

			<input type="hidden" name="e_pid" value="${paper.id}" />
			<input type="hidden" name="e_uid" value="${sessionScope.SEN_USERID}" />

			</form>

			

        	
			
        </div>
        
        
    </div>




</body>
</html>

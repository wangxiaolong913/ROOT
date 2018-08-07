<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>welcome</title>
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script type="text/javascript" src="inc/js/jquery.js"></script>
	<style>
		h2{font-size:14px; margin:20px 0 10px 0;}
		.tm_param_list a{color:#000}
		.tm_param_list a:hover{color:#f00}

		.tm_blocker{float:left; width:50%;min-width:450px}
		.tm_blocker2{float:left; width:800px;}
	</style>
</head>
<body>
	
    <div class="tm_main" style="min-width:1000px">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><tomtag:Message key="txt.other.navhome" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
            	<h1><tomtag:Message key="txt.other.navhome.welcome" /></h1>
                <span><tomtag:Message key="txt.other.navhome.welcome_tip" /></span>
            </div>
        </div>

		<c:if test="${sessionScope.SEN_USERTYPE == '1'}">

			<div class="tm_container">
				<div class="tm_blocker">
					<h2><tomtag:Message key="txt.other.welcome.news" /></h2>
					<table border="0" width="98%" class="tm_news_list">
						<tbody>
							<c:forEach var="news" items="${newslist}">
							<tr>
								<td>[<a href="news/list.thtml?n_classid=${news.n_classid}">${news.c_name}</a>] <a href="news/detail.thtml?id=${news.n_id}">${news.n_title}</a></td>
								<th width="120">${news.n_createdate}</th>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="tm_blocker">
					<h2><tomtag:Message key="txt.other.welcome.statistics" /></h2>
					<table border="0" width="98%" class="tm_param_list">
						<tbody>
							<tr>
								<th><tomtag:Message key="txt.other.welcome.statistics.user" /></th>
								<td>
									<tomtag:Message key="txt.other.status.normal" /> : <tomtag:Util action="convertEmpty" data="${sysparams.user['1']}" ext="0" /> , 
									<tomtag:Message key="txt.other.status.forcheck" /> : <tomtag:Util action="convertEmpty" data="${sysparams.user['0']}" ext="0" /> , 
									<tomtag:Message key="txt.other.status.lock" /> : <tomtag:Util action="convertEmpty" data="${sysparams.user['-1']}" ext="0" />
								</td>
							</tr>
							<tr>
								<th><tomtag:Message key="txt.other.welcome.statistics.qdb" /></th>
								<td>
									<tomtag:Message key="txt.other.welcome.statistics.qdb.nums" /> : <tomtag:Util action="convertEmpty" data="${sysparams.qdb['qdb']}" ext="0" /> , 
									<tomtag:Message key="txt.other.welcome.statistics.question.nums" /> : <tomtag:Util action="convertEmpty" data="${sysparams.qdb['question']}" ext="0" />
								</td>
							</tr>
							<tr>
								<th><tomtag:Message key="txt.other.welcome.statistics.question" /></th>
								<td>
									<tomtag:Message key="txt.other.questiontype.single" /> : <tomtag:Util action="convertEmpty" data="${sysparams.question['1']}" ext="0" />  , 
									<tomtag:Message key="txt.other.questiontype.multiple" /> : <tomtag:Util action="convertEmpty" data="${sysparams.question['2']}" ext="0" /> , 
									<tomtag:Message key="txt.other.questiontype.judgment" /> : <tomtag:Util action="convertEmpty" data="${sysparams.question['3']}" ext="0" /> , 
									<tomtag:Message key="txt.other.questiontype.blank" /> : <tomtag:Util action="convertEmpty" data="${sysparams.question['4']}" ext="0" /> , 
									<tomtag:Message key="txt.other.questiontype.essay" /> : <tomtag:Util action="convertEmpty" data="${sysparams.question['5']}" ext="0" />
								</td>
							</tr>
							<tr>
								<th><tomtag:Message key="txt.other.welcome.statistics.paper" /></th>
								<td>
									<tomtag:Message key="txt.other.welcome.statistics.paper.nums" /> : ${sysparams.paper["total"]} , 
									<tomtag:Message key="txt.other.welcome.statistics.paper.ing" /> : ${sysparams.paper["ing"]} , 
									<tomtag:Message key="txt.other.welcome.statistics.paper.finish" /> : ${sysparams.paper["finish"]}
								</td>
							</tr>
							<tr>
								<th><tomtag:Message key="txt.other.welcome.statistics.lession" /></th>
								<td>
									<tomtag:Message key="txt.other.welcome.statistics.lession.nums" /> : ${sysparams.lession["courses"]}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="tm_container"></div>
			
			
			
			<%
			float fFreeMemory=(float)Runtime.getRuntime().freeMemory();
			float fTotalMemory=(float)Runtime.getRuntime().totalMemory();
			float fUsedMemory = fTotalMemory - fFreeMemory;
			float fPercent=fFreeMemory/fTotalMemory*100;
			%>
			<h2><tomtag:Message key="txt.other.welcome.env" /></h2>
			<table width="100%" border="0" class="tm_param_list">
				<tbody>
					<tr>
						<th width="150"><tomtag:Message key="txt.other.welcome.env.name" /></th>
						<td><%=System.getProperty("java.runtime.name") %> <%=System.getProperty("java.runtime.version") %></td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.other.welcome.env.jvm" /></th>
						<td>
							<div style="width:400px; height:5px; background:#f00; font-size:1px">
								<div style="float:right; background:#0f0; width:<%=(int)(fPercent*4)%>px; font-size:1px; height:5px"></div>
							</div>
							<%=fUsedMemory/1024/1024%>M / <%=fTotalMemory/1024/1024%>M 
						</td>
					</tr>
				</tbody>
			</table>
			
			<h2><tomtag:Message key="txt.other.welcome.server" /></h2>
			<table width="100%" border="0" class="tm_param_list">
				<tbody>
					<tr>
						<th width="150"><tomtag:Message key="txt.other.welcome.server.name" /></th>
						<td><%= request.getServerName() %> [<%=request.getRemoteAddr()%>]</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.other.welcome.server.os" /></th>
						<td><%=System.getProperty("os.name") %> , <%=System.getProperty("sun.arch.data.model")%> Bit , <%=System.getProperty("user.country")%> , <%=System.getProperty("user.language")%></td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.other.welcome.server.userdir" /></th>
						<td><%=System.getProperty("user.dir") %></td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.other.welcome.server.deploy" /></th>
						<td><%=System.getProperty("tomexam.root")%></td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.other.welcome.server.time" /></th>
						<td><%=new java.util.Date()%> , <%=System.getProperty("user.timezone")%></td>
					</tr>
				</tbody>
			</table>

			<h2><tomtag:Message key="txt.other.welcome.soft" /></h2>
			<table width="100%" border="0" class="tm_param_list">
				<tbody>
					<tr>
						<th width="150"><tomtag:Message key="txt.other.welcome.soft.version" /></th>
						<td>
							<tomtag:Util action="version" />
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.other.welcome.soft.contract" /></th>
						<td>
							<a href="mailto:admin@tomexam.com"><tomtag:Message key="txt.other.welcome.soft.contract.email" /></a>
							-
							<a href="http://www.tomexam.com" target="_blank"><tomtag:Message key="txt.other.welcome.soft.contract.site" /></a>
							-
							<a href="http://t.sina.com.cn/tomexam" target="_blank"><tomtag:Message key="txt.other.welcome.soft.contract.weibo" /></a>
							-
							<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=43014034&site=qq&menu=yes"><tomtag:Message key="txt.other.welcome.soft.contract.qq" /></a>
						</td>
					</tr>
					<tr>
						<th><tomtag:Message key="txt.other.welcome.soft.use_help" /></th>
						<td>
							<a href="http://www.tomexam.com/link/?m=manual" target="_blank"><tomtag:Message key="txt.other.welcome.soft.use_help.manual" /></a>
							-
							<a href="http://www.tomexam.com/link/?m=advice" target="_blank"><tomtag:Message key="txt.other.welcome.soft.use_help.advice" /></a>
						</td>
					</tr>
				</tbody>
			</table>
		</c:if>




		<c:if test="${sessionScope.SEN_USERTYPE == '0'}">

			<script src="inc/js/layer/layer.js" type="text/javascript"></script>

			<script type="text/javascript">
				var tm = {
					startExam : function(obj, pid){
						var _tr = $(obj).parent().parent();
						var _p_name = _tr.children("td").eq(0).text();
						var _p_time = _tr.children("td").eq(2).text();
						var _p_totalscore = _tr.children("td").eq(4).text();
						var _p_passscore = _tr.children("td").eq(5).text();
						

						var html = [];
						html.push('<div style="margin:20px">');
						html.push('	<p style="line-height:20px"><tomtag:Message key="txt.user.paper.start.confirm" /></p>');
						
						html.push('	<table style="margin:0 auto; width:350px" border="0" cellpadding="0" cellspacing="0">');
						html.push('	<tr>');
						html.push('		<td width="80"><img src="skins/images/paper_pencil.png" width="60" /></td>');
						html.push('		<td><p><b><tomtag:Message key="txt.sys.paper.fields.name" /></b>：'+_p_name+'<p>');
						html.push('			<p><b><tomtag:Message key="txt.sys.paper.fields.duration" /></b>：'+_p_time+'<p>');
						html.push('			<p><b><tomtag:Message key="txt.sys.paper.fields.total" /></b>：'+_p_totalscore+'<p>');
						html.push('			<p><b><tomtag:Message key="txt.sys.paper.fields.passscore" /></b>：'+_p_passscore+'<p>');
						html.push('		</td>');
						html.push('	</tr>');
						html.push('</table>');

						html.push('<p style="text-align:center; margin-top:30px">');
						html.push('<button class="tm_btn tm_btn_primary" type="button" onclick="tm.joinExam(\''+pid+'\')"><tomtag:Message key="txt.other.operations.ok" /></button>');
						html.push('</p>');

						html.push('</div>');

						layer.open({
						  type: 1,
						  title: '<tomtag:Message key="txt.user.paper.start" />',
						  shadeClose: true,
						  shade: 0.8,
						  area: ['450px', '310px'],
						  content: html.join("")
						}); 

						return false;
					},
					joinExam : function(pid){
						window.location.href="${tm_base}user/paper/paper_detail.thtml?pid="+pid;
					}
				};
			</script>

			<div class="tm_container">
				<div class="tm_blocker2">
					<h2><tomtag:Message key="txt.other.welcome.news" /></h2>
					<table width="100%" cellpadding="10" border="0" class="tm_news_list">
						<thead>
							<tr>
								<th style="background:#f5f5f5; text-align:left; border-bottom:solid 1px #ddd"><tomtag:Message key="txt.sys.news.fields.title" /></th>
								<th style="background:#f5f5f5; border-bottom:solid 1px #ddd"><tomtag:Message key="txt.sys.news.fields.postdate" /></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty newslist }">
									<tr>
										<td colspan="2" style="text-align:center"><tomtag:Message key="message.user.news.nonews" /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="news" items="${newslist}">
									<tr>
										<td>[<a href="news/list.thtml?n_classid=${news.n_classid}">${news.c_name}</a>] <a href="news/detail.thtml?id=${news.n_id}">${news.n_title}</a></td>
										<td width="150">${news.n_createdate}</td>
									</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							
						</tbody>
					</table>
				</div>
			</div>


			<div class="tm_container">
				<div class="tm_blocker2">
					<h2><tomtag:Message key="txt.other.welcome.exam" /></h2>
					<table width="100%" cellpadding="10" border="0" class="tm_table_list">
						<thead>
							<tr>
								<th><tomtag:Message key="txt.sys.paper.fields.name" /></th>
								<th><tomtag:Message key="txt.sys.paper.fields.timesetting" /></th>
								<th><tomtag:Message key="txt.sys.paper.fields.duration" /></th>
								<th><tomtag:Message key="txt.sys.paper.fields.papertype" /></th>
								<th><tomtag:Message key="txt.sys.paper.fields.total" /></th>
								<th><tomtag:Message key="txt.sys.paper.fields.passscore" /></th>
								<th><tomtag:Message key="txt.other.operations" /></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty sysexams }">
									<tr>
										<td colspan="7"><tomtag:Message key="message.user.paper.noexam" /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="paper" items="${sysexams}">
									<tr>
										<td>${paper.p_name}</td>
										<td>
											${paper.p_starttime}<br/>${paper.p_endtime}
										</td>
										<td><span class="tm_label">${paper.p_duration}</span> <tomtag:Message key="txt.other.units.minute" /></td>
										<td>
											<c:choose>
												<c:when test="${paper.p_papertype == '0'}"><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></c:when>
												<c:when test="${paper.p_papertype == '1'}"><b><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></b></c:when>
											</c:choose>
										</td>
										<td><span class="tm_label">${paper.p_total_score}</span></td>
										<td><span class="tm_label">${paper.p_pass_score}</span></td>
										<td>
											<a href="javascript:void(0)" onclick="javascript:tm.startExam(this,'${paper.p_id}');"><tomtag:Message key="txt.user.paper.start" /></a>
										</td>
									</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>

			<div class="tm_container">
				<div class="tm_blocker2">
					<h2><tomtag:Message key="txt.other.welcome.recentexam" /></h2>
					<table width="100%" cellpadding="10" border="0" class="tm_table_list">
						<thead>
							<tr>
								<th><tomtag:Message key="txt.sys.paper.fields.name" /></th>
								<th><tomtag:Message key="txt.sys.paper.fields.duration" /></th>
								<th><tomtag:Message key="txt.user.history.fields.timecost" /></th>
								<th><tomtag:Message key="txt.user.history.fields.examtime" /></th>
								<th><tomtag:Message key="txt.user.history.fields.userscore" /></th>
								<th><tomtag:Message key="txt.other.operations" /></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty sysexamhistory }">
									<tr>
										<td colspan="6"><tomtag:Message key="message.user.paper.nohistory" /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="paper" items="${sysexamhistory}">
									<tr>
										<td>${paper.p_name}</td>
										<td><span class="tm_label">${paper.p_duration}</span> <tomtag:Message key="txt.other.units.minute" /></td>
										<td><span class="tm_label">${paper.e_timecost}</span> <tomtag:Message key="txt.other.units.minute" /></td>
										<td>
											${paper.e_starttime}<br/>${paper.e_endtime}
										</td>
										<td>
											<c:choose>
												<c:when test="${paper.showscore == 'y'}">
													<span class="tm_label">${paper.e_score}</span>
													<c:choose>
														<c:when test="${paper.e_score >= paper.p_pass_score}"><font color="green"><tomtag:Message key="txt.user.history.fields.examstatus.passed" /></font></c:when>
														<c:otherwise><font color="red"><b><tomtag:Message key="txt.user.history.fields.examstatus.notpass" /></b></font></c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<font color="gray"><b><tomtag:Message key="txt.user.history.fields.examstatus.noshow" /></b></font>
												</c:otherwise>
											</c:choose>
											
										</td>
										<td>
											<c:choose>
												<c:when test="${paper.showscore == 'y'}">
													<a href="user/paper/history_detail.thtml?eid=${paper.e_id}&pid=${paper.e_pid}"><tomtag:Message key="txt.user.history.fields.detail" /></a>
												</c:when>
												<c:otherwise>
													<font color="gray">--</font>
												</c:otherwise>
											</c:choose>
											
										</td>
									</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>

			<div class="tm_container"></div>

		</c:if>
        
        
    </div>
	
	<p>&nbsp;</p>

</body>
</html>



<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/fmt" prefix= "fmt" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.paper.history" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<style>
		.tm_lnk_more{}
		.tm_div_more{position:absolute; top:30px; right:5px; display:none; z-index:10}
		.tm_ul_more{list-style:none; padding:0; margin:5px 0; width:100px; background:#fff; border:solid 1px #eee;}
		.tm_ul_more li{border-bottom:solid 1px #eee}
		.tm_ul_more li:last-child{border-bottom:none;}
		.tm_ul_more li a{display:block; line-height:25px; text-decoration:none}
		.tm_ul_more li a:hover{background:#eee}
	</style>

	<script>
		
		$(document).ready(function() { 

			$(".tm_table_list tbody tr").mouseover(function(){
				$(this).attr("style","background:#f5f5f5");
			});
			$(".tm_table_list tbody tr").mouseout(function(){
				$(this).attr("style","background:#ffffff");
			});

		}); 


		function deleteRecordComfirm(){
			var v = window.confirm('<tomtag:Message key="message.other.reallydelete" />');
			if(v){
				v = window.confirm('<tomtag:Message key="message.other.confirmagain" />');
			}
			return v;
		}

		function doDownLoadHistory(pid){
			$.ajax({
				type: "POST",
				url: "${tm_base}system/paper/history/export.do",
				data: {"pid":pid, "t":Math.random()},
				dataType: "json",
				success: function(ret){
					if("ok" == ret["code"]){
						window.open("${tm_base}files/export/" + ret["data"]);
					}else if("nodata" == ret["code"]){
						alert('<tomtag:Message key="message.other.export_nodata" />');
					}else{
						alert('<tomtag:Message key="message.other.export_error" />');
					}
				},
				error:function(){
					alert('<tomtag:Message key="message.other.systembusy" />');
				}
			}); 
		}

		function loadHistoryDetail(pid, eid, uid){
			if(baseutil.isNull(pid) || baseutil.isNull(eid) || baseutil.isNull(uid)){
				alert("缺少参数. Parameters Required.");
				return;
			}
			layer.open({
				title: '<tomtag:Message key="txt.sys.paper.history.marking" />',
				type: 2,
				area: ['900px', '600px'],
				maxmin:true,
				shadeClose: true,
				content: "${tm_base}system/paper/history/detail.thtml?pid="+pid+"&eid="+eid+"&uid="+uid
			});
		}
	</script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="system/paper/list.thtml"><tomtag:Message key="txt.sys.paper.list" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.paper.history" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<table width="500" cellpadding="5" border="0" class="tm_table_form">
				<tr>
					<th width="100"><tomtag:Message key="txt.sys.paper.fields.name" /></th>
					<td>
						${paper.p_name}

						<c:choose>
							<c:when test="${paper.p_papertype == '0'}">(<tomtag:Message key="txt.sys.paper.fields.papertype.common" />)</c:when>
							<c:when test="${paper.p_papertype == '1'}">(<b><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></b>)</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th><tomtag:Message key="txt.sys.paper.fields.timesetting" /></th>
					<td>${paper.p_starttime} -- ${paper.p_endtime}</td>
				</tr>
				<tr>
					<th><tomtag:Message key="txt.sys.paper.fields.duration" /></th>
					<td><span class="tm_label">${paper.p_duration}</span> <tomtag:Message key="txt.other.units.minute" /></td>
				</tr>
				<tr>
					<th><tomtag:Message key="txt.sys.paper.fields.total" /></th>
					<td><span class="tm_label">${paper.p_total_score}</span> &nbsp; (<tomtag:Message key="txt.sys.paper.fields.passscore" />:${paper.p_pass_score}) </td>
				</tr>
			</table>
        </div>
        
        
        <div class="tm_container">
			<form action="system/paper/history/list.thtml" method="get">
			<div class="tm_searchbox">
				<table border="0" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							&nbsp;
							<tomtag:Message key="txt.other.operations.keyword" /> :
							<input type="text" name="keywords" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='keywords' />" /> &nbsp;

							<select class="tm_select" name="orderby" style="min-width:100px">
								<option value="NORMAL"><tomtag:Message key="txt.sys.paper.history.orderby" /></option>
								<option value="SCORE_DESC" <c:if test="${param.orderby == 'SCORE_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.score_desc" /></option>
								<option value="SCORE_ASC" <c:if test="${param.orderby == 'SCORE_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.score_asc" /></option>
								<option value="COST_DESC" <c:if test="${param.orderby == 'COST_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.cost_desc" /></option>
								<option value="COST_ASC" <c:if test="${param.orderby == 'COST_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.cost_asc" /></option>
								<option value="ENDTIME_DESC" <c:if test="${param.orderby == 'ENDTIME_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.end_desc" /></option>
								<option value="ENDTIME_ASC" <c:if test="${param.orderby == 'ENDTIME_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.end_asc" /></option>
							</select> &nbsp;

							<input type="hidden" name="pid" value="${param.pid}" />
							
							<select class="validate[required] tm_select" name="branchid" style="width:100px">
								<option value=""><tomtag:Message key="txt.sys.user.fields.branch" /></option>
								<c:forEach var="branch" items="${branches}">
                                <option value="${branch.b_id}" <c:if test="${param.branchid == branch.b_id}">selected</c:if>>${branch.b_name}</option>
                                </c:forEach>
                            </select> &nbsp;


							<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
						</td>
						<td align="right">
							<c:set var="tm_total_records" value="${progress.testing+progress.wait+progress.checked}"></c:set>
							<c:set var="tm_checked_percent" value="${progress.checked * 100 /tm_total_records}"></c:set>

							<c:if test="${tm_total_records==0}">
								<c:set var="tm_checked_percent" value="0"></c:set>
							</c:if>

							<!--
							${progress.testing},
							${progress.wait},
							${progress.checked}
							-->
							
							<table width="230" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td><tomtag:Message key="txt.sys.paper.history.progress" /></td>
									<td>
										<div style="width:100px; height:15px; background:#ddd; font-size:1px">
											<div style="float:left; background:#0f0; width:${tm_checked_percent}px; font-size:1px; height:15px"></div>
										</div>
									</td>
									<td><fmt:formatNumber value="${tm_checked_percent}" pattern= "#0.00" />%</td>
									<td><a href="javascript:void(0);" onclick="javascript:window.location.reload();"><img src="skins/images/icos/reload.png" align="absmiddle"/></a></td>
								</tr>
							</table>
						</td>
						<td align="right" width="220">
							<img src="skins/images/icos/excel.png" align="absmiddle" border="0" />
							<a href="javascript:void(0);" onclick="doDownLoadHistory('${paper.p_id}');"><tomtag:Message key="txt.sys.paper.history.excel" /></a>
							&nbsp;
							<img src="skins/images/icos/eraser.png" align="absmiddle" border="0" />
							<a href="system/paper/history/clear.do?pid=${paper.p_id}" onclick="return deleteRecordComfirm();"><tomtag:Message key="txt.sys.paper.history.clear" /></a>
							&nbsp;
						</td>
					</tr>
				</table>
				
			</div>
	
			<jsp:useBean id="nowDate" class="java.util.Date" />
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.user.fields.username" /></th>
                        <th><tomtag:Message key="txt.sys.user.fields.branch" /></th>
						<th><tomtag:Message key="txt.sys.user.fields.no" /></th>
                    	<th><tomtag:Message key="txt.sys.paper.history.fields.examtime" /></th>
                        <th><tomtag:Message key="txt.sys.paper.history.fields.timecost" /></th>
                        <th><tomtag:Message key="txt.sys.paper.history.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.paper.history.fields.score" /></th>
                        <th><tomtag:Message key="txt.sys.paper.history.fields.source" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="exam" items="${page.dataList}">

					<c:set var="itimeleft" value="1" />
                	<tr>
						<td>${exam.u_username}<br/>${exam.u_realname}</td>
						<td>${exam.b_name}</td>
						<td>${exam.u_no} </td>
						<td>
							${exam.e_starttime}<br/>
							<c:choose>
								<c:when test="${exam.e_status == '2'}">
									${exam.e_endtime}
								</c:when>
								<c:when test="${exam.e_status == '1'}">
									${exam.e_endtime}
								</c:when>
								<c:otherwise>
									<font color="gray">--</font>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${empty exam.e_timecost}">
									<c:set var="interval" value="${nowDate.time - exam.e_starttime.time}" />
									<c:set var="itimeleft" value="${paper.p_duration - interval/1000/60}" />
									
									<c:choose>
										<c:when test="${itimeleft<0}"><font color="red"><tomtag:Message key="txt.sys.paper.history.fields.timeout" /></font></c:when>
										<c:otherwise>
											<span class="tm_label" style="color:#aaa"><fmt:formatNumber value= "${interval/1000/60}" pattern= "#0.0" /></span> 
											<tomtag:Message key="txt.other.units.minute" />
										</c:otherwise>
									</c:choose>
									
								</c:when>
								<c:otherwise>
									<span class="tm_label">${exam.e_timecost}</span> 
									<tomtag:Message key="txt.other.units.minute" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${itimeleft<0}"><font color="red"><tomtag:Message key="txt.sys.paper.history.fields.timeout" /></font></c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${exam.e_status == '0'}"><font color="gray"><tomtag:Message key="txt.sys.paper.history.fields.status.s0" /></font></c:when>
										<c:when test="${exam.e_status == '1'}"><i><tomtag:Message key="txt.sys.paper.history.fields.status.s1" /><i></c:when>
										<c:when test="${exam.e_status == '2'}"><tomtag:Message key="txt.sys.paper.history.fields.status.s2" /></c:when>
										<c:otherwise><font color="red"><tomtag:Message key="txt.sys.paper.history.fields.status.sx1" /></font></c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>

							
						</td>
						<td>
							<c:choose>
								<c:when test="${exam.e_status == '2'}">
									<c:choose>
										<c:when test="${exam.e_score >= paper.p_pass_score}"><span class="tm_label">${exam.e_score}</span></c:when>
										<c:otherwise><span class="tm_label" style="color:red">${exam.e_score}</span></c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<font color="gray">--</font>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${exam.e_ip}</td>
                        <td>
							<c:choose>
								<c:when test="${exam.e_status == '2'}">
									<a href="javascript:void(0)" onclick="javascript:loadHistoryDetail('${paper.p_id}','${exam.e_id}','${exam.e_uid}');"><tomtag:Message key="txt.other.operations.detail" /></a> |
									<a href="system/paper/history/delete.do?eid=${exam.e_id}&pid=${paper.p_id}&uid=${exam.e_uid}" onclick="return deleteRecordComfirm();"><tomtag:Message key="txt.other.operations.delete" /></a>
								</c:when>
								<c:otherwise>
									<font color="gray"><tomtag:Message key="txt.other.operations.detail" /></font> |
									<a href="system/paper/history/delete.do?eid=${exam.e_id}&pid=${paper.p_id}&uid=${exam.e_uid}" onclick="return deleteRecordComfirm();"><tomtag:Message key="txt.other.operations.delete" /></a>
								</c:otherwise>
							</c:choose>
						</td>
                    </tr>
					</c:forEach>
                </tbody>
            </table>
			</form>
			<table width="100%" cellpadding="10" border="0" class="tm_table_list">
			      <tfoot>
                	<tr>
                    	<td>${foot}</td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        
    </div>

</body>
</html>

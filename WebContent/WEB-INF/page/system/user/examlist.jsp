<%@ page language="java" pageEncoding="utf-8" %>
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
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<script>
		function loadHistoryDetail(pid, eid, uid){
			window.parent.loadHistoryDetail(pid, eid, uid);
		}

		function deleteRecordComfirm(){
			var v = window.confirm('<tomtag:Message key="message.other.reallydelete" />');
			if(v){
				v = window.confirm('<tomtag:Message key="message.other.confirmagain" />');
			}
			return v;
		}

		window.onload = function(){
			$(".tm_table_list tbody tr").mouseover(function(){
				$(this).attr("style","background:#f5f5f5");
			});
			$(".tm_table_list tbody tr").mouseout(function(){
				$(this).attr("style","background:#ffffff");
			});
		}
	</script>

  </head>
  
<body>

	<div class="tm_main">
        
        <div class="tm_container">
			
			<div style="margin:0 0 20px 0">
				<b>${user.u_username} (${user.u_realname})</b> <tomtag:Message key="txt.sys.user.examlist" />
			</div>
			
			<jsp:useBean id="nowDate" class="java.util.Date" />
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.name" /></th>
						<th><tomtag:Message key="txt.sys.paper.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.duration" /></th>
                    	<th><tomtag:Message key="txt.user.history.fields.timecost" /></th>
                    	<th><tomtag:Message key="txt.user.history.fields.examtime" /></th>
                        <th><tomtag:Message key="txt.user.history.fields.userscore" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.total" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="paper" items="${page.dataList}">
					<c:set var="itimeleft" value="1" />
					<c:set var="interval" value="${nowDate.time - paper.e_starttime.time}" />
					<c:set var="itimeleft" value="${paper.p_duration - interval/1000/60}" />
                	<tr>
						<td>${paper.p_name}</td>
						<td>
							<c:choose>
								<c:when test="${paper.e_status == '0'}">
									<c:choose>
										<c:when test="${itimeleft<0}"><font color="red"><tomtag:Message key="txt.sys.paper.history.fields.timeout" /></font></c:when>
										<c:otherwise>
											<font color="gray"><tomtag:Message key="txt.sys.paper.history.fields.status.s0" /></font>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:when test="${paper.e_status == '1'}"><i><tomtag:Message key="txt.sys.paper.history.fields.status.s1" /><i></c:when>
								<c:when test="${paper.e_status == '2'}"><tomtag:Message key="txt.sys.paper.history.fields.status.s2" /></c:when>
								<c:otherwise><font color="red"><tomtag:Message key="txt.sys.paper.history.fields.status.sx1" /></font></c:otherwise>
							</c:choose>
						</td>
                    	<td>${paper.p_duration}</td>
						<td>${paper.e_timecost}</td>
						<td>
							${paper.e_starttime}<br/>
							<c:choose>
								<c:when test="${empty paper.e_endtime}">--</c:when>
								<c:otherwise>${paper.e_endtime}</c:otherwise>
							</c:choose>
						</td>
						<td>
							<span class="tm_label"></span>
							<c:choose>
								<c:when test="${paper.e_score >= paper.p_pass_score}"><font color="green">${paper.e_score}</font></c:when>
								<c:otherwise><font color="red"><b>${paper.e_score}</b></font></c:otherwise>
							</c:choose>
						</td>
						<td>${paper.p_total_score}</td>
						<td>
							<c:choose>
								<c:when test="${paper.e_status == '1' || paper.e_status == '2'}">
									<a href="javascript:void(0)" onclick="javascript:loadHistoryDetail('${paper.e_pid}','${paper.e_id}','${paper.e_uid}');"><tomtag:Message key="txt.other.operations.detail" /></a> |
								</c:when>
								<c:otherwise>
									<font color="gray"><tomtag:Message key="txt.other.operations.detail" /></font> |
								</c:otherwise>
							</c:choose>
							<a href="system/paper/history/delete.do?eid=${paper.e_id}&pid=${paper.e_pid}&uid=${paper.e_uid}&nonav=1" onclick="return deleteRecordComfirm();"><tomtag:Message key="txt.other.operations.delete" /></a>
						</td>
                    </tr>
					</c:forEach>
                </tbody>
                <tfoot>
                	<tr>
                    	<td colspan="8">
                        	${foot}
                        </td>
                    </tr>
                </tfoot>
            </table>

        </div>
        
        
    </div>

</body>
</html>

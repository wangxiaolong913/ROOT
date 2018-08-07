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
	<title><tomtag:Message key="txt.sys.user.testlist" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>
	<script src="inc/js/wdatepicker/WdatePicker.js" type="text/javascript"></script>


	<script>
		function deleteRecordComfirm(){
			var v = window.confirm('<tomtag:Message key="message.other.reallydelete" />');
			if(v){
				v = window.confirm('<tomtag:Message key="message.other.confirmagain" />');
			}
			return v;
		}

		function loadHistoryDetail(tid, uid){
			window.parent.loadSelfTestHistoryDetail(tid, uid);
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
				<b>${user.u_username} (${user.u_realname})</b> <tomtag:Message key="txt.sys.user.testlist" />
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.user.fields.username" /></th>
                        <th><tomtag:Message key="txt.sys.user.fields.branch" /></th>
						<th><tomtag:Message key="txt.sys.user.fields.no" /></th>
                    	<th><tomtag:Message key="txt.sys.paper.history.fields.examtime" /></th>
                        <th><tomtag:Message key="txt.sys.paper.history.fields.timecost" /></th>
                        <th><tomtag:Message key="txt.sys.paper.history.fields.score" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="test" items="${page.dataList}">
                	<tr>
						<td>${test.u_username}<br/>${test.u_realname}</td>
						<td>${test.b_name}</td>
						<td>${test.u_no} </td>
						<td>${test.t_testdate}</td>
						<td>
							<span class="tm_label">${test.t_timecost}</span> 
							<tomtag:Message key="txt.other.units.minute" />
						</td>
						<td>
							<c:choose>
								<c:when test="${test.t_userscore >= test.t_totalscore*0.6}"><span class="tm_label">${test.t_userscore}</span></c:when>
								<c:otherwise><span class="tm_label" style="color:red">${test.t_userscore}</span></c:otherwise>
							</c:choose>
							/${test.t_totalscore}
						</td>
                        <td>
							<a href="javascript:void(0)" onclick="javascript:loadHistoryDetail('${test.t_tid}','${test.t_uid}');"><tomtag:Message key="txt.other.operations.detail" /></a> |
							<a href="system/selftest/delete.do?tid=${test.t_tid}" onclick="return deleteRecordComfirm();"><tomtag:Message key="txt.other.operations.delete" /></a>
						</td>
                    </tr>
					</c:forEach>
                </tbody>
            </table>
			
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

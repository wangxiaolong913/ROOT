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
	<title><tomtag:Message key="txt.sys.selftest.list" /></title>
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
			if(baseutil.isNull(tid) || baseutil.isNull(uid)){
				alert("缺少参数. Parameters Required.");
				return;
			}
			layer.open({
				title: '<tomtag:Message key="txt.other.operations.examlist" />',
				type: 2,
				area: ['900px', '600px'],
				maxmin:true,
				shadeClose: true,
				content: "${tm_base}system/selftest/detail.thtml?tid="+tid+"&uid="+uid
			});
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
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.selftest.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.selftest.list" /></h1>
                <span><tomtag:Message key="txt.sys.selftest.listtip" /></span>
            </div>
        </div>
        
        
        <div class="tm_container">
			<form action="system/selftest/list.thtml" method="get">
			<div class="tm_searchbox">
				<tomtag:Message key="txt.sys.user.fields.username" /> :
				<input type="text" name="ut_keywords" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='ut_keywords' />" /> &nbsp;

				<select class="validate[required] tm_select" name="ut_branchid" style="width:100px">
					<option value=""><tomtag:Message key="txt.sys.user.fields.branch" /></option>
					<c:forEach var="branch" items="${branches}">
					<option value="${branch.b_id}" <c:if test="${param.ut_branchid == branch.b_id}">selected</c:if>>${branch.b_name}</option>
					</c:forEach>
				</select> &nbsp;

				<select class="tm_select" name="ut_orderby" style="min-width:100px">
					<option value="NORMAL"><tomtag:Message key="txt.sys.paper.history.orderby" /></option>
					<option value="SCORE_DESC" <c:if test="${param.ut_orderby == 'SCORE_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.score_desc" /></option>
					<option value="SCORE_ASC" <c:if test="${param.ut_orderby == 'SCORE_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.score_asc" /></option>
					<option value="COST_DESC" <c:if test="${param.ut_orderby == 'COST_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.cost_desc" /></option>
					<option value="COST_ASC" <c:if test="${param.ut_orderby == 'COST_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.cost_asc" /></option>
					<option value="ENDTIME_DESC" <c:if test="${param.ut_orderby == 'ENDTIME_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.end_desc" /></option>
					<option value="ENDTIME_ASC" <c:if test="${param.ut_orderby == 'ENDTIME_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.paper.history.orderby.end_asc" /></option>
				</select> &nbsp;

				<tomtag:Message key="txt.sys.selftest.fields.testtime" /> :
				<input type="text" name="ut_startdate" class="validate[required] tm_txt tm_datepicker tm_width150" value="<tomtag:Util action='param' data='ut_startdate' />" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" />
				-
				<input type="text" name="ut_enddate" class="validate[required] tm_txt tm_datepicker tm_width150" value="<tomtag:Util action='param' data='ut_enddate' />" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" />
				&nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
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

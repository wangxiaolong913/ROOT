<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.history" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	
	<script type="text/javascript">
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
				<li class="active"><tomtag:Message key="txt.user.history" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.history" /></h1>
                <span><tomtag:Message key="txt.user.history.listtip" /></span>
            </div>
        </div>
        
        <div class="tm_container">
			<form action="user/paper/history.thtml" method="get">
			<div class="tm_searchbox">
				<tomtag:Message key="txt.sys.paper.fields.name" /> :
				<input type="text" name="p_name" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='p_name' />" /> &nbsp;

				<tomtag:Message key="txt.sys.paper.fields.category" /> :
				<select class="tm_select" name="p_cid" style="min-width:150px">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<c:forEach var="category" items="${categorys}">
					<option value="${category.c_id}" <c:if test="${param.p_cid == category.c_id}">selected</c:if>>${category.c_name}</option>
					</c:forEach>
				</select> &nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.name" /></th>
						<th><tomtag:Message key="txt.sys.paper.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.duration" /></th>
                    	<th><tomtag:Message key="txt.user.history.fields.timecost" /></th>
                    	<th><tomtag:Message key="txt.user.history.fields.examtime" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.papertype" /></th>
                        <th><tomtag:Message key="txt.user.history.fields.userscore" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.passscore" />/<tomtag:Message key="txt.sys.paper.fields.total" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="paper" items="${page.dataList}">
                	<tr>
						<td>${paper.p_name}</td>
						<td>
							<c:choose>
								<c:when test="${paper.e_status == '1'}"><font color="red"><tomtag:Message key="txt.user.history.fields.status.forcheck" /></font></c:when>
								<c:when test="${paper.e_status == '2'}"><tomtag:Message key="txt.user.history.fields.status.checked" /></c:when>
							</c:choose>
						</td>
                    	<td>${paper.p_duration} <tomtag:Message key="txt.other.units.minute" /></td>
						<td><b>${paper.e_timecost}</b> <tomtag:Message key="txt.other.units.minute" /></td>
						<td>
							${paper.e_starttime}<br/>${paper.e_endtime}
						</td>
						<td>
							<c:choose>
								<c:when test="${paper.p_papertype == '0'}"><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></c:when>
								<c:when test="${paper.p_papertype == '1'}"><b><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></b></c:when>
							</c:choose>
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
						<td>${paper.p_pass_score} / ${paper.p_total_score}</td>
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

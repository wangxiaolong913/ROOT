<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.selfhistory" /></title>
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
				<li class="active"><tomtag:Message key="txt.user.selfhistory" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.selfhistory" /></h1>
                <span><tomtag:Message key="txt.user.selfhistory.listtip" /></span>
            </div>
        </div>
        
        <div class="tm_container">
			<form action="user/selftest/history.thtml" method="get">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.user.selfhistory.fields.papername" /></th>
                    	<th><tomtag:Message key="txt.user.selfhistory.fields.totalscore" /></th>
                        <th><tomtag:Message key="txt.user.selfhistory.fields.userscore" /></th>
                        <th><tomtag:Message key="txt.user.selfhistory.fields.testdate" /></th>
                        <th><tomtag:Message key="txt.user.selfhistory.fields.timecost" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="paper" items="${page.dataList}">
                	<tr>
						<td>${paper.t_name}</td>
						<td><span class="tm_label">${paper.t_totalscore}</span></td>
						<td><span class="tm_label">${paper.t_userscore}</span></td>
						<td>${paper.t_testdate}</td>
						<td><span class="tm_label">${paper.t_timecost}</span></td>
                        <td>
							<a href="user/selftest/historydetail.thtml?id=${paper.t_tid}"><tomtag:Message key="txt.other.operations.view" /></a>
							<a href="user/selftest/delete.do?id=${paper.t_tid}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
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

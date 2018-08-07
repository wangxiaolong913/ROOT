<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.log.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/wdatepicker/WdatePicker.js" type="text/javascript"></script>

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
				<li class="active"><tomtag:Message key="txt.sys.log.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.log.list" /></h1>
                <span><tomtag:Message key="txt.sys.log.listtip" /></span>
            </div>
        </div>
        
        
        <div class="tm_container">
			<form action="system/log/list.thtml" method="get">
			<div class="tm_searchbox">
				<tomtag:Message key="txt.sys.log.fields.user" /> :
				<input type="text" name="l_username" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='l_username' />" /> &nbsp;

				<tomtag:Message key="txt.sys.log.fields.action" /> :
				<input type="text" name="l_action_name" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='l_action_name' />" /> &nbsp;
				
				<tomtag:Message key="txt.sys.log.fields.logdate" /> :
				<input type="text" name="l_startdate" class="validate[required] tm_txt tm_datepicker tm_width200" value="<tomtag:Util action='param' data='l_startdate' />" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" />
				-
				<input type="text" name="l_enddate" class="validate[required] tm_txt tm_datepicker tm_width200" value="<tomtag:Util action='param' data='l_enddate' />" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" />

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
						<th><tomtag:Message key="txt.sys.log.fields.user" /></th>
                    	<th><tomtag:Message key="txt.sys.log.fields.usertype" /></th>
                        <th><tomtag:Message key="txt.sys.log.fields.action" /></th>
                        <th><tomtag:Message key="txt.sys.log.fields.url" /></th>
                        <th><tomtag:Message key="txt.sys.log.fields.ip" /></th>
                        <th><tomtag:Message key="txt.sys.log.fields.logdate" /></th>
                        <th><tomtag:Message key="txt.sys.log.fields.desc" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="log" items="${page.dataList}">
                	<tr>
                    	<td>${log.l_username}</td>
						<td>
							<c:choose>
								<c:when test="${log.l_usertype == 0}"><tomtag:Message key="txt.other.login.usertype.user" /></c:when>
								<c:when test="${log.l_usertype == 1}"><tomtag:Message key="txt.other.login.usertype.admin" /></c:when>
							</c:choose>
						</td>
						<td>${log.l_action_name}</td>
						<td style="text-align:left" title="${log.l_url}">
							<c:choose>
								<c:when test="${fn:length(log.l_url)>60 }">${fn:substring(log.l_url ,0,60)}...</c:when>
								<c:otherwise>${log.l_url}</c:otherwise>
							</c:choose>
						</td>
						<td>${log.l_ip}</td>
						<td>${log.l_logdate}</td>
						<td>${log.l_logdesc}</td>
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

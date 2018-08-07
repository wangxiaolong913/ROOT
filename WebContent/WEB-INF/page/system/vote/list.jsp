<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.vote.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.vote.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.vote.list" /></h1>
                <span><tomtag:Message key="txt.sys.vote.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/vote/add.thtml"><tomtag:Message key="txt.sys.vote.add" /></a>
				<a href="system/vote/list.thtml" class="on"><tomtag:Message key="txt.sys.vote.list" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.vote.fields.title" /></th>
                    	<th><tomtag:Message key="txt.sys.vote.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.vote.fields.start" /></th>
                        <th><tomtag:Message key="txt.sys.vote.fields.end" /></th>
                    	<th><tomtag:Message key="txt.sys.vote.fields.totaluser" /></th>
                    	<th><tomtag:Message key="txt.sys.vote.fields.creator" /></th>
                    	<th><tomtag:Message key="txt.sys.vote.fields.modifier" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="vote" items="${page.dataList}">
                	<tr>
                    	<td>${vote.v_title}</td>
						<td>
							<c:choose>
								<c:when test="${vote.v_status == 0}">
								   <i><tomtag:Message key="txt.other.status.forcheck" /></i>
								</c:when>
								<c:when test="${vote.v_status == 1}">
								   <tomtag:Message key="txt.other.status.normal" />
								</c:when>
								<c:when test="${vote.v_status == -1}">
									<font color="red"><tomtag:Message key="txt.other.status.lock" /></font>
								</c:when>
							</c:choose>
						</td>
						<td>${vote.v_starttime}</td>
						<td>${vote.v_endtime}</td>
                        <td>${vote.v_totaluser}</td>
                        <td>${vote.v_poster}<br/>${vote.v_createdate}</td>
                        <td>${vote.v_modifyor}<br/>${vote.v_modifydate}</td>
                        <td>
							<a href="system/vote/detail.thtml?id=${vote.v_id}"><tomtag:Message key="txt.sys.vote.viewdata" /></a>
							|
							<a href="system/vote/load.thtml?id=${vote.v_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							|
							<a href="system/vote/delete.do?id=${vote.v_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
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

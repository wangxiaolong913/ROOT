<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.role.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<style>
		.tm_table_list tbody tr td{line-height:30px}
	</style>

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
				<li class="active"><tomtag:Message key="txt.sys.role.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.role.list" /></h1>
                <span><tomtag:Message key="txt.sys.role.listnavtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/role/add.thtml"><tomtag:Message key="txt.sys.role.add" /></a>
				<a href="system/role/list.thtml" class="on"><tomtag:Message key="txt.sys.role.list" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.role.fields.rolename" /></th>
                    	<th><tomtag:Message key="txt.sys.role.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.role.fields.createdate" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="role" items="${page.dataList}">
                	<tr>
                    	<td>${role.r_name}</td>
                        <td>
							<c:choose>
								<c:when test="${role.r_status == 1}">
								   <tomtag:Message key="txt.other.status.normal" />
								</c:when>
								<c:when test="${role.r_status == -1}">
									<font color="red"><tomtag:Message key="txt.other.status.lock" /></font>
								</c:when>
							</c:choose>
						</td>
                        <td>${role.r_createdate}</td>
                        <td>
							<a href="system/role/load.thtml?id=${role.r_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							|
							<a href="system/role/delete.do?id=${role.r_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
						</td>
                    </tr>
					</c:forEach>
                </tbody>
                <tfoot>
                	<tr>
                    	<td colspan="4">
                        	${foot}
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        
    </div>

</body>
</html>

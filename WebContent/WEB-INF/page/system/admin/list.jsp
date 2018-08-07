<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.admin.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

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
				<li class="active"><tomtag:Message key="txt.sys.admin.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.admin.list" /></h1>
                <span><tomtag:Message key="txt.sys.admin.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/admin/add.thtml"><tomtag:Message key="txt.sys.admin.add" /></a>
				<a href="system/admin/list.thtml" class="on"><tomtag:Message key="txt.sys.admin.list" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
			<form action="system/admin/list.thtml" method="get">
			<div class="tm_searchbox">
				<tomtag:Message key="txt.sys.admin.fields.username" />:
				<input type="text" name="a_username" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='a_username' />" /> &nbsp;

				<tomtag:Message key="txt.sys.admin.fields.realname" />:
				<input type="text" name="a_realname" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='a_realname' />" /> &nbsp;
				
				<tomtag:Message key="txt.sys.admin.fields.role" />:
				<select class="tm_selects" name="a_roleid">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<c:forEach var="role" items="${roles}">
					<option value="${role.r_id}" <c:if test="${param.a_roleid == role.r_id}">selected</c:if>>${role.r_name}</option>
					</c:forEach>
				</select> &nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.admin.fields.username" /></th>
                    	<th><tomtag:Message key="txt.sys.admin.fields.realname" /></th>
                        <th><tomtag:Message key="txt.sys.admin.fields.photo" /></th>
                        <th><tomtag:Message key="txt.sys.admin.fields.role" /></th>
                    	<th><tomtag:Message key="txt.sys.admin.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.admin.fields.lastlogin" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="admin" items="${page.dataList}">
                	<tr>
                    	<td>${admin.a_username}</td>
						<td>${admin.a_realname}</td>
						<td>
							<c:choose>
								<c:when test="${not empty admin.a_photo}">
									<img src="${tm_base}${admin.a_photo}" class="tm_img_preview_m" style="cursor:pointer" onclick="tm_showPic(this.src)" />
								</c:when>
							</c:choose>
						</td>
						<td>${admin.a_rolename}</td>
                        <td>
							<c:choose>
								<c:when test="${admin.a_status == 0}">
								   <i><tomtag:Message key="txt.other.status.forcheck" /></i>
								</c:when>
								<c:when test="${admin.a_status == 1}">
								   <tomtag:Message key="txt.other.status.normal" />
								</c:when>
								<c:when test="${admin.a_status == -1}">
									<font color="red"><tomtag:Message key="txt.other.status.lock" /></font>
								</c:when>
							</c:choose>
						</td>
                        <td>${admin.a_lastlogin}</td>
                        <td>
							<a href="system/admin/load.thtml?id=${admin.a_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							|
							<a href="system/admin/delete.do?id=${admin.a_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
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

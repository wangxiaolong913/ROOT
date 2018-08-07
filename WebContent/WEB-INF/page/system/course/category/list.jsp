<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.course.category.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>
	
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
				<li class="active"><tomtag:Message key="txt.sys.course.category.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.course.category.list" /></h1>
                <span><tomtag:Message key="txt.sys.course.category.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/course/category/add.thtml"><tomtag:Message key="txt.sys.course.category.add" /></a>
				<a href="system/course/category/list.thtml" class="on"><tomtag:Message key="txt.sys.course.category.list" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.course.category.fields.name" /></th>
                        <th><tomtag:Message key="txt.sys.course.category.fields.courses" /></th>
                        <th><tomtag:Message key="txt.sys.course.category.fields.logo" /></th>
                        <th><tomtag:Message key="txt.sys.course.category.fields.createtime" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="category" items="${page.dataList}">
                	<tr>
                    	<td>${category.ca_name}</td>
                    	<td><span class="tm_label">${category.c_totalcourse}</span></td>
						<td>
							<c:choose>
								<c:when test="${not empty category.ca_logo}">
									<img src="${tm_base}${category.ca_logo}" class="tm_img_preview_m" style="cursor:pointer" onclick="tm_showPic(this.src)" />
								</c:when>
							</c:choose>
						</td>
                        <td>${category.ca_postdate}</td>
                        <td>
							<a href="system/course/category/load.thtml?id=${category.ca_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							|
							<a href="system/course/category/delete.do?id=${category.ca_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
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

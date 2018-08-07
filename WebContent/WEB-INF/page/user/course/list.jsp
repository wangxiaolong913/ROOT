<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.learn.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<script type="text/javascript">
		
	</script>

	<style>
		.tm_image_container{clear:both;}

		.tm_image_container ul{list-style:none}
		.tm_image_container ul li{float:left; width:200px; height:300px; text-align:center; margin:20px 20px 0 0; overflow:hidden; }
		.tm_image_container ul li a{display:block; color:#000; text-decoration:none; border:solid 1px #eee}
		.tm_image_container ul li a:hover{color:#f00; border:solid 1px #efefef}
		.tm_image_container ul li a div{margin:5px; width:190px; height:250px; border:none; overflow:hidden}
		.tm_image_container ul li a div img{margin:0px; height:250px; border:none}
		.tm_image_container ul li a p{line-height:30px; background:#eee; margin:0 0 0 0; white-space:nowrap;}
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.user.learn.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.learn.list" /></h1>
                <span><tomtag:Message key="txt.user.learn.list.listtip" /></span>
            </div>
        </div>
        
        <div class="tm_container">
			<form action="user/course/list.thtml" method="get">
			<div class="tm_searchbox">
				<tomtag:Message key="txt.user.learn.fields.name" /> :
				<input type="text" name="c_name" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='c_name' />" /> &nbsp;

				<tomtag:Message key="txt.user.learn.fields.category" /> :
				<select class="validate[required] tm_select" name="c_caid" style="min-width:150px">
					<option value=""><tomtag:Message key="txt.other.operations.choose" /></option>
					<c:forEach var="category" items="${categories}">
					<option value="${category.ca_id}" <c:if test="${param.c_caid == category.ca_id}">selected</c:if>>${category.ca_name}</option>
					</c:forEach>
				</select> &nbsp;

				<tomtag:Message key="txt.user.learn.fields.teacher" /> :
				<select class="validate[required] tm_select" name="c_tid" style="min-width:150px">
					<option value=""><tomtag:Message key="txt.other.operations.choose" /></option>
					<c:forEach var="teacher" items="${teachers}">
					<option value="${teacher.t_id}" <c:if test="${param.c_tid == teacher.t_id}">selected</c:if>>${teacher.t_name}</option>
					</c:forEach>
                </select> &nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>
			
			<div class="tm_image_container">
				<ul>
					<c:forEach var="course" items="${page.dataList}">
						<c:set var="coursename" value="${fn:substring(course.c_name, 0, 12)}" />
						<c:choose>
							<c:when test="${not empty course.c_logo}">
								<li><a href="user/course/detail.thtml?id=${course.c_id}" title="${course.c_name}"><div><img src="${tm_base}${course.c_logo}" /></div><p>${coursename}</p></a></li>
							</c:when>
							<c:otherwise>
								<li><a href="user/course/detail.thtml?id=${course.c_id}" title="${course.c_name}"><div><img src="skins/images/image_placeholder.png" /></div><p>${coursename}</p></a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
				<div class="tm_container"></div>
			</div>
        	
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

<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.learn.my" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<script type="text/javascript">
		
	</script>

	<style>
		.tm_image_container{clear:both;}

		.tm_image_container ul{list-style:none}
		.tm_image_container ul li{float:left; width:200px; height:330px; text-align:center; margin:20px 20px 0 0}
		.tm_image_container ul li a{display:block; color:#000; text-decoration:none; border:solid 1px #eee}
		.tm_image_container ul li a:hover{color:#f00; border:solid 1px #efefef}
		.tm_image_container ul li a div{margin:5px; width:190px; height:250px; border:none; overflow:hidden}
		.tm_image_container ul li a div img{margin:0px; height:250px; border:none}
		.tm_image_container ul li a p{line-height:30px; background:#eee; margin:10px 0 0 0;}
		.tm_image_container ul li a span{clear:both; line-height:30px;}
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.user.learn.my" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.learn.my" /></h1>
                <span><tomtag:Message key="txt.user.learn.my.listtip" /></span>
            </div>
        </div>
        
        <div class="tm_container">
			<div class="tm_image_container">
				<ul>
					<c:forEach var="course" items="${page.dataList}">
						<c:set var="coursename" value="${fn:substring(course.c_name, 0, 12)}" />
						<c:choose>
							<c:when test="${not empty course.c_logo}">
								<li><a href="user/course/detail.thtml?id=${course.p_cid}" title="${course.c_name}"><div><img src="${tm_base}${course.c_logo}" /></div><p>${coursename}</p><span>${course.p_startdate}</span></a></li>
							</c:when>
							<c:otherwise>
								<li><a href="user/course/detail.thtml?id=${course.p_cid}" title="${course.c_name}"><div><img src="skins/images/image_placeholder.png" /></div><p>${coursename}</p><span>${course.p_startdate}</span></a></li>
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

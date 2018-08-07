<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.course.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		function tm_learnRecord(id){
			layer.open({
				type: 2,
				title: '<tomtag:Message key="txt.sys.course.users" />',
				shadeClose: true,
				shade: 0.8,
				area: ['650px', '480px'],
				content: 'system/course/learnrecord.thtml?id=' + id
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
				<li class="active"><tomtag:Message key="txt.sys.course.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.course.list" /></h1>
                <span><tomtag:Message key="txt.sys.course.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/course/add.thtml"><tomtag:Message key="txt.sys.course.add" /></a>
				<a href="system/course/list.thtml" class="on"><tomtag:Message key="txt.sys.course.list" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.course.fields.name" /></th>
                        <th><tomtag:Message key="txt.sys.course.fields.status" /></th>
                    	<th><tomtag:Message key="txt.sys.course.fields.logo" /></th>
                        <th><tomtag:Message key="txt.sys.course.fields.category" /></th>
                        <th><tomtag:Message key="txt.sys.course.fields.teacher" /></th>
                        <th><tomtag:Message key="txt.sys.course.fields.type" /></th>
                        <th><tomtag:Message key="txt.sys.course.fields.publish" /></th>
                        <th><tomtag:Message key="txt.sys.course.fields.modify" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="course" items="${page.dataList}">
                	<tr>
                    	<td>${course.c_name}</td>
						<td>
							<c:choose>
								<c:when test="${course.c_status == '0'}"><font color="red"><tomtag:Message key="txt.other.status.close" /></font></c:when>
								<c:when test="${course.c_status == '1'}"><tomtag:Message key="txt.other.status.open" /></c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty course.c_logo}">
									<img src="${tm_base}${course.c_logo}" class="tm_img_preview_m" style="cursor:pointer" onclick="tm_showPic(this.src)" />
								</c:when>
							</c:choose>
						</td>
                        <td>${course.ca_name}</td>
                        <td>${course.t_name}</td>
						<td>
							<c:choose>
								<c:when test="${course.c_type == '0'}">
								   <tomtag:Message key="txt.sys.course.fields.type.elective" />
								</c:when>
								<c:when test="${course.c_type == '1'}">
								   <tomtag:Message key="txt.sys.course.fields.type.must" />
								</c:when>
							</c:choose>
						</td>
                        <td>${course.c_poster}<br/>${course.c_postdate}</td>
                        <td>${course.c_modifier}<br/>${course.c_modifydate}</td>
                        <td>
							<a href="javascript:void(0);" onclick="javascript:tm_learnRecord('${course.c_id}');"><tomtag:Message key="txt.sys.course.users" /></a>
							|
							<a href="system/course/load.thtml?id=${course.c_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							|
							<a href="system/course/delete.do?id=${course.c_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
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

<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.analysis.common.choosepaper" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".tm_papers").mouseover(function(){
				$(this).attr("style","cursor:pointer;background:#eee");
			});

			$(".tm_papers").mouseout(function(){
				$(this).attr("style","cursor:pointer;background:#fff");
			});

			$(".tm_papers").click(function(){
				var pid = $(this).data("pid");
				var pname = $(this).data("pname");

				$('#e_papername', window.parent.document).val(pname);
				$('#pid', window.parent.document).val(pid);

				var index = parent.layer.getFrameIndex(window.name); 
				parent.layer.close(index);
			});
			
		});
	</script>
	

  </head>
  
<body>

	<div class="tm_main">
    	
        <div class="tm_container">
			<form action="system/analysis/paperlist.thtml" method="get">
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

				<tomtag:Message key="txt.sys.paper.fields.status" /> :
				<select name="p_status" class="tm_select">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<option value="1" <c:if test="${param.p_status == '1'}">selected</c:if>><tomtag:Message key="txt.other.status.open" /></option>
					<option value="0" <c:if test="${param.p_status == '0'}">selected</c:if>><tomtag:Message key="txt.other.status.close" /></option>
				</select> &nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.name" /></th>
						<th><tomtag:Message key="txt.sys.paper.fields.status" /></th>
                    	<th><tomtag:Message key="txt.sys.paper.fields.timesetting" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.papertype" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.total" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.passscore" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="paper" items="${page.dataList}">
                	<tr data-pid="${paper.p_id}" data-pname="${paper.p_name}" class="tm_papers">
						<td>${paper.p_name}</td>
						<td>
							<c:choose>
								<c:when test="${paper.p_status == '0'}"><font color="red"><tomtag:Message key="txt.other.status.close" /></font></c:when>
								<c:when test="${paper.p_status == '1'}"><tomtag:Message key="txt.other.status.open" /></c:when>
							</c:choose>
						</td>
                    	<td>
							${paper.p_starttime}<br/>${paper.p_endtime}
						</td>
						<td>
							<c:choose>
								<c:when test="${paper.p_papertype == '0'}"><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></c:when>
								<c:when test="${paper.p_papertype == '1'}"><b><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></b></c:when>
							</c:choose>
						</td>
						<td>${paper.p_total_score}</td>
						<td>${paper.p_pass_score}</td>
                    </tr>
					</c:forEach>
                </tbody>
                <tfoot>
                	<tr>
                    	<td colspan="6">
                        	${foot}
                        </td>
                    </tr>
                </tfoot>
            </table>
			</form>
        </div>
        
        
    </div>

</body>
</html>

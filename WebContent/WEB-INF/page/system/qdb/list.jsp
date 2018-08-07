<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.qdb.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<script type="text/javascript">
		function tmShowAnalyse(dbid, qnums){
			if(baseutil.isNull(dbid)){
				alert("缺少参数. Parameters Required.");
				return;
			}
			layer.open({
				title: '<tomtag:Message key="txt.sys.qdb.analyse" />',
				type: 2,
				area: ['600px', '500px'],
				maxmin:false,
				shadeClose: true,
				content: "${tm_base}system/qdb/analyse.thtml?id="+dbid+"&qnums="+qnums
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
				<li class="active"><tomtag:Message key="txt.sys.qdb.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.qdb.list" /></h1>
                <span><tomtag:Message key="txt.sys.qdb.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/qdb/add.thtml"><tomtag:Message key="txt.sys.qdb.add" /></a>
				<a href="system/qdb/list.thtml" class="on"><tomtag:Message key="txt.sys.qdb.list" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.qdb.fields.name" /></th>
                        <th><tomtag:Message key="txt.sys.qdb.fields.logo" /></th>
                    	<th><tomtag:Message key="txt.sys.qdb.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.qdb.fields.nums" /></th>
                        <th><tomtag:Message key="txt.sys.qdb.fields.creator" /></th>
                        <th><tomtag:Message key="txt.sys.qdb.fields.modifier" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="qdb" items="${page.dataList}">
                	<tr>
                    	<td>${qdb.d_name}</td>
						<td>
							<c:choose>
								<c:when test="${not empty qdb.d_logo}">
									<img src="${tm_base}${qdb.d_logo}" class="tm_img_preview_m" style="cursor:pointer" onclick="tm_showPic(this.src)" />
								</c:when>
							</c:choose>
						</td><td>
							<c:choose>
								<c:when test="${qdb.d_status == 0}">
								   <font color="red"><tomtag:Message key="txt.other.status.close" /></font>
								</c:when>
								<c:when test="${qdb.d_status == 1}">
								   <tomtag:Message key="txt.other.status.open" />
								</c:when>
							</c:choose>
						</td>
						<td><a href="system/question/list.thtml?q_dbid=${qdb.d_id}" class="tm_label">${qdb.d_nums}</a></td>
						<td>${qdb.d_poster}<br/>${qdb.d_createdate}</td>
						<td>${qdb.d_modifyor}<br/>${qdb.d_modifydate}</td>
                        <td>
							<a href="system/qdb/load.thtml?id=${qdb.d_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							|
							<a href="javascript:void(0)" onclick="javascript:tmShowAnalyse('${qdb.d_id}','${qdb.d_nums}')"><tomtag:Message key="txt.sys.qdb.analyse" /></a>
							|
							<a href="system/qdb/delete.do?id=${qdb.d_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
						</td>
                    </tr>
					</c:forEach>
                </tbody>
                <tfoot>
                	<tr>
                    	<td colspan="7">
                        	${foot}
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        
    </div>

</body>
</html>

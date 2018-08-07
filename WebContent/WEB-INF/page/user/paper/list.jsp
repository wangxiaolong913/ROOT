<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.paper" /></title>
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

		var tm = {
			startExam : function(obj, pid){
				var _tr = $(obj).parent().parent();
				var _p_name = _tr.children("td").eq(0).text();
				var _p_time = _tr.children("td").eq(2).text();
				var _p_totalscore = _tr.children("td").eq(4).text();
				var _p_passscore = _tr.children("td").eq(5).text();
				

				var html = [];
				html.push('<div style="margin:20px">');
				html.push('	<p style="line-height:20px"><tomtag:Message key="txt.user.paper.start.confirm" /></p>');
				
				html.push('	<table style="margin:0 auto; width:350px" border="0" cellpadding="0" cellspacing="0">');
				html.push('	<tr>');
				html.push('		<td width="80"><img src="skins/images/paper_pencil.png" width="60" /></td>');
				html.push('		<td><p><b><tomtag:Message key="txt.sys.paper.fields.name" /></b>：'+_p_name+'<p>');
				html.push('			<p><b><tomtag:Message key="txt.sys.paper.fields.duration" /></b>：'+_p_time+'<p>');
				html.push('			<p><b><tomtag:Message key="txt.sys.paper.fields.total" /></b>：'+_p_totalscore+'<p>');
				html.push('			<p><b><tomtag:Message key="txt.sys.paper.fields.passscore" /></b>：'+_p_passscore+'<p>');
				html.push('		</td>');
				html.push('	</tr>');
				html.push('</table>');

				html.push('<p style="text-align:center; margin-top:30px">');
				html.push('<button class="tm_btn tm_btn_primary" type="button" onclick="tm.joinExam(\''+pid+'\',this)"><tomtag:Message key="txt.other.operations.ok" /></button>');
				html.push('</p>');

				html.push('</div>');

				layer.open({
				  type: 1,
				  title: '<tomtag:Message key="txt.user.paper.start" />',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['450px', '310px'],
				  content: html.join("")
				}); 

				return false;
			},
			joinExam : function(pid, _me){
				$(_me).text('<tomtag:Message key="message.system.please_wait" />');
				$(_me).attr("disabled", true);
				setTimeout(function(){
					window.location.href="${tm_base}user/paper/paper_detail.thtml?pid="+pid;
				}, 3000);
				
			}
		};
	</script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.user.paper" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.paper" /></h1>
                <span><tomtag:Message key="txt.user.paper.listtip" /></span>
            </div>
        </div>
        
        <div class="tm_container">
			<form action="user/paper/list.thtml" method="get">
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

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>
			
			<!-- 当前日期 -->
			<c:set var="var_nowDate">  
				<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyyMMddHHmmss" type="date"/>  
			</c:set>  
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.paper.fields.name" /></th>
                    	<th><tomtag:Message key="txt.sys.paper.fields.timesetting" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.duration" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.papertype" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.total" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.passscore" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.creator" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="paper" items="${page.dataList}">
					<!-- 当前试卷的截止日期 -->	
					<c:set var="var_paperEndDate">  
						<fmt:formatDate value="${paper.p_endtime}" pattern="yyyyMMddHHmmss" type="date"/>  
					</c:set> 
					<c:set var="var_paperIsEnded" value="${var_nowDate>=var_paperEndDate}"></c:set>

                	<tr>
						<td>${paper.p_name}</td>
                    	<td>
							${paper.p_starttime}<br/>${paper.p_endtime}
						</td>
						<td><span class="tm_label">${paper.p_duration}</span> <tomtag:Message key="txt.other.units.minute" /></td>
						<td>
							<c:choose>
								<c:when test="${paper.p_papertype == '0'}"><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></c:when>
								<c:when test="${paper.p_papertype == '1'}"><b><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></b></c:when>
							</c:choose>
						</td>
						<td><span class="tm_label">${paper.p_total_score}</span></td>
						<td><span class="tm_label">${paper.p_pass_score}</span></td>
						<td>${paper.p_poster}<br/>${paper.p_createdate}</td>
                        <td>
							<c:choose>
								<c:when test="${var_paperIsEnded == true}">
									<font color="gray"><tomtag:Message key="txt.user.paper.start.ended" /></font>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${paper.e_status == '0'}">
											<a href="javascript:void(0)" onclick="javascript:tm.startExam(this,'${paper.p_id}');"><font color="red"><tomtag:Message key="txt.user.paper.start.goon" /></font></a>
										</c:when>
										<c:otherwise>
											<a href="javascript:void(0)" onclick="javascript:tm.startExam(this,'${paper.p_id}');"><tomtag:Message key="txt.user.paper.start" /></a>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
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

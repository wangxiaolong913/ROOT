<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.paper.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<style>
		.tm_lnk_more{}
		.tm_div_more{position:absolute; top:30px; right:5px; display:none; z-index:10}
		.tm_ul_more{list-style:none; padding:0; margin:5px 0; width:100px; background:#fff; border:solid 1px #eee;}
		.tm_ul_more li{border-bottom:solid 1px #eee}
		.tm_ul_more li:last-child{border-bottom:none;}
		.tm_ul_more li a{display:block; line-height:25px; text-decoration:none}
		.tm_ul_more li a:hover{background:#eee}
	</style>
	
	<script type="text/javascript">
		$(document).ready(function() { 
			$(".tm_lnk_more").hover(function() { 
				$(this).next('.tm_div_more').css('display', 'block'); 
			}, function() { 
				$(this).next('.tm_div_more').css('display', 'none'); 
			}); 

			$(".tm_div_more").hover(function() { 
				$(this).css('display', 'block'); 
			}, function() { 
				$(this).css('display', 'none'); 
			}); 

			$(".tm_table_list tbody tr").mouseover(function(){
				$(this).attr("style","background:#f5f5f5");
			});
			$(".tm_table_list tbody tr").mouseout(function(){
				$(this).attr("style","background:#ffffff");
			});

		}); 

		function tm_fn_exportword(pid){
			$.ajax({
				type: "POST",
				url: "${tm_base}system/paper/export-word.do",
				data: {"pid":pid, "t":Math.random()},
				dataType: "json",
				success: function(ret){
					if("ok" == ret["code"]){
						window.open("${tm_base}files/export/" + ret["data"]);
					}else if("no_right" == ret["code"]){
						alert(ret["data"]);
					}else{
						alert('<tomtag:Message key="message.other.export_error" />');
					}
				},
				error:function(){
					alert('<tomtag:Message key="message.other.systembusy" />');
				}
			}); 
		}

		function tm_fn_doConfirmCopyPaper(pname, pid){
			var html = [];
			html.push('<div style="margin:20px">');
			html.push('	<p><tomtag:Message key="txt.sys.paper.copy.tip" /></p>');
			html.push('	<p><input type="text" id="tm_paper_copy_form_pname" value="'+pname+'" class="tm_txt" size="50" maxlength="50" style="width:240px" /></p>');
			html.push('	<p><button class="tm_btn tm_btn_primary" type="button" onclick="tm_fn_doCopyPaper(\''+pid+'\')"><tomtag:Message key="txt.other.operations.submit" /></button></p>');
			html.push('</div>');

			layer.open({
			  type: 1,
			  title: '<tomtag:Message key="txt.sys.paper.copy" />',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['300px', '200px'],
			  content: html.join("")
			}); 
		}

		function tm_fn_doCopyPaper(pid){
			var _pname = $("#tm_paper_copy_form_pname").val();
			$.ajax({
				type: "POST",
				url: "${tm_base}system/paper/clone-paper.do",
				data: {"pid":pid, "pname":_pname, "t":Math.random()},
				dataType: "json",
				success: function(ret){
					if("ok" == ret["code"]){
						alert('<tomtag:Message key="message.other.op_ok" />');
						window.location.reload();
					}else{
						alert(ret["data"]);
					}
				},
				error:function(){
					alert('<tomtag:Message key="message.other.systembusy" />');
				}
			}); 
		}

		

		<tomtag:Util action="lang" data="zh_CN">
			function tm_fn_exportword_unsupport(){
				alert("随机试卷不支持导出成WORD文档");
				return;
			}
		</tomtag:Util>

		<tomtag:Util action="lang" data="en">
			function tm_fn_exportword_unsupport(){
				alert("Random papers are not supported for export to WORD documents");
				return;
			}
		</tomtag:Util>
	</script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.paper.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.paper.list" /></h1>
                <span><tomtag:Message key="txt.sys.paper.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/paper/add.thtml"><tomtag:Message key="txt.sys.paper.add" /></a>
				<a href="system/paper/fastadd.thtml"><tomtag:Message key="txt.sys.paper.fastadd" /></a>
				<a href="system/paper/list.thtml" class="on"><tomtag:Message key="txt.sys.paper.list" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
			<form action="system/paper/list.thtml" method="get">
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

				<tomtag:Message key="txt.sys.paper.fields.papertype" /> :
				<select class="tm_select" name="p_papertype" style="min-width:150px">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<option value="0" <c:if test="${param.p_papertype == '0'}">selected</c:if>><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></option>
					<option value="1" <c:if test="${param.p_papertype == '1'}">selected</c:if>><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></option>
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
                        <th><tomtag:Message key="txt.sys.paper.fields.duration" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.papertype" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.total" /></th>
                        <th><tomtag:Message key="txt.sys.paper.history.fields.users" /></th>
                        <th><tomtag:Message key="txt.sys.paper.fields.creator" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="paper" items="${page.dataList}">
                	<tr>
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
						<td><span class="tm_label">${paper.p_duration}</span> <tomtag:Message key="txt.other.units.minute" /></td>
						<td>
							<c:choose>
								<c:when test="${paper.p_papertype == '0'}"><tomtag:Message key="txt.sys.paper.fields.papertype.common" /></c:when>
								<c:when test="${paper.p_papertype == '1'}"><b><tomtag:Message key="txt.sys.paper.fields.papertype.random" /></b></c:when>
							</c:choose>
						</td>
						<td><span class="tm_label">${paper.p_total_score}</span></td>
						<td><a href="system/paper/history/list.thtml?pid=${paper.p_id}" class="tm_label">${paper.total_users}</a> <tomtag:Message key="txt.other.units.ren" /></td>
						<td>${paper.p_poster}<br/>${paper.p_createdate}</td>
                        <td style="position:relative; width:200px">
							<a href="system/paper/detail.thtml?pid=${paper.p_id}"><tomtag:Message key="txt.other.operations.config" /></a> |
							<a href="system/paper/load.thtml?pid=${paper.p_id}"><tomtag:Message key="txt.other.operations.modify" /></a> |
							<a href="system/paper/history/list.thtml?pid=${paper.p_id}"><tomtag:Message key="txt.other.operations.examlist" /></a> |
							
							<a href="javascript:void(0);" class="tm_lnk_more"><tomtag:Message key="txt.other.operations.more" /></a>
							<div class="tm_div_more">
								<ul class="tm_ul_more">
									<c:choose>
										<c:when test="${paper.p_papertype == '0'}">
											<li><a href="javascript:void(0);" onclick="tm_fn_exportword('${paper.p_id}')"><tomtag:Message key="txt.other.operations.export_word" /></a></li>
										</c:when>
										<c:when test="${paper.p_papertype == '1'}">
											<li><a href="javascript:void(0);" onclick="tm_fn_exportword_unsupport();" disabled="true" style="color:#ccc"><tomtag:Message key="txt.other.operations.export_word" /></a></li>
										</c:when>
									</c:choose>
									
									<li><a href="javascript:void(0);" onclick="tm_fn_doConfirmCopyPaper('${paper.p_name}','${paper.p_id}');"><tomtag:Message key="txt.sys.paper.copy" /></a></li>
									<li><a href="system/paper/delete.do?pid=${paper.p_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a></li>
								</ul>
							</div>
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

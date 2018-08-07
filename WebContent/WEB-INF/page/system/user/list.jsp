<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.user.list" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<link rel="stylesheet" href="inc/js/jquery-validation-engine/css/validationEngine.jquery.css" />
	<script src="inc/js/jquery-validation-engine/js/jquery.validationEngine.js"></script>
	
	<tomtag:Util action="lang" data="zh_CN">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-zh_CN.js"></script>
	</tomtag:Util>

	<tomtag:Util action="lang" data="en">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-en.js"></script>
	</tomtag:Util>
	
	<script type="text/javascript">

		$(document).ready(function() { 
			$(".tm_table_list tbody tr").mouseover(function(){
				$(this).attr("style","background:#f5f5f5");
			});
			$(".tm_table_list tbody tr").mouseout(function(){
				$(this).attr("style","background:#ffffff");
			});
		});

		var tmBatch = {
			toggleUser : function(obj){
				$("input[name='b_uids']").prop("checked", $(obj).prop("checked"));
			},
			batchopChange : function(obj){
				$("#batch_span").empty();
				if($(obj).val() == "batch_branch"){
					$("select[name=u_branchid]").clone().prependTo("#batch_span"); 
					$("#batch_span select").attr("class","tm_select validate[required]");
					$("#batch_span select").attr("name","b_branchid");

				}else if($(obj).val() == "batch_status"){
					$("select[name=u_status]").clone().prependTo("#batch_span");
					$("#batch_span select").attr("class","tm_select validate[required]");
					$("#batch_span select").attr("name","b_status");
				}
			}
		};

		function tm_batchSubmit(){
			var formcheck = $("#form_user_batch").validationEngine('validate');
			if(formcheck){
				var wcm = window.confirm('<tomtag:Message key="txt.sys.user.batch.confirm" />');
				if(wcm){
					$("#form_user_batch").attr("action","${tm_base}system/user/batchop.do");
					$("#form_user_batch").submit();
				}
			}
		}

		/**
		 * 试卷列表
		 **/
		function tmShowExamList(uid){
			layer.open({
				type: 2,
				title: '<tomtag:Message key="txt.sys.user.examlist" />',
				shadeClose: true,
				maxmin:true,
				shade: 0.8,
				area: ['900px', '600px'],
				content: '${tm_base}system/user/examlist.thtml?id=' + uid
			}); 
		}

		/**
		 * 试卷详情
		 **/
		function loadHistoryDetail(pid, eid, uid){
			if(baseutil.isNull(pid) || baseutil.isNull(eid) || baseutil.isNull(uid)){
				alert("缺少参数. Parameters Required.");
				return;
			}
			layer.open({
				title: '<tomtag:Message key="txt.sys.user.examlist.paper_detail" />',
				type: 2,
				area: ['900px', '650px'],
				maxmin:true,
				shadeClose: true,
				content: "${tm_base}system/paper/history/detail.thtml?pid="+pid+"&eid="+eid+"&uid="+uid
			});
		}

		/**
		 * 自测列表
		 **/
		function tmShowSelfTestList(uid){
			layer.open({
				type: 2,
				title: '<tomtag:Message key="txt.sys.user.testlist" />',
				shadeClose: true,
				maxmin:true,
				shade: 0.8,
				area: ['900px', '600px'],
				content: '${tm_base}system/user/selftestlist.thtml?id=' + uid
			}); 
		}

		
		/**
		 * 自测详情
		 **/
		function loadSelfTestHistoryDetail(tid, uid){
			if(baseutil.isNull(tid) || baseutil.isNull(uid)){
				alert("缺少参数. Parameters Required.");
				return;
			}
			layer.open({
				title: '<tomtag:Message key="txt.sys.user.testlist.test_detail" />',
				type: 2,
				area: ['900px', '650px'],
				maxmin:true,
				shadeClose: true,
				content: "${tm_base}system/selftest/detail.thtml?tid="+tid+"&uid="+uid
			});
		}
		
	</script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.user.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.user.list" /></h1>
                <span><tomtag:Message key="txt.sys.user.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/user/add.thtml"><tomtag:Message key="txt.sys.user.add" /></a>
				<a href="system/user/list.thtml" class="on"><tomtag:Message key="txt.sys.user.list" /></a>
				<a href="system/user/import.thtml"><tomtag:Message key="txt.sys.user.import" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
			<form action="system/user/list.thtml" method="get">
			<div class="tm_searchbox">
				<tomtag:Message key="txt.sys.user.fields.username" />:
				<input type="text" name="u_username" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='u_username' />" /> &nbsp;

				<tomtag:Message key="txt.sys.user.fields.realname" />:
				<input type="text" name="u_realname" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='u_realname' />" /> &nbsp;
				
				<tomtag:Message key="txt.sys.user.fields.no" />:
				<input type="text" name="u_no" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='u_no' />" /> &nbsp;
				
				<select class="tm_select" name="u_status">
					<option value=""><tomtag:Message key="txt.sys.user.fields.status" /></option>
					<option value="1" <c:if test="${param.u_status == '1'}">selected</c:if>><tomtag:Message key="txt.other.status.normal" /></option>
					<option value="0" <c:if test="${param.u_status == '0'}">selected</c:if>><tomtag:Message key="txt.other.status.forcheck" /></option>
					<option value="-1" <c:if test="${param.u_status == '-1'}">selected</c:if>><tomtag:Message key="txt.other.status.lock" /></option>
				</select> &nbsp;
				
				<select class="tm_selects" name="u_branchid">
					<option value=""><tomtag:Message key="txt.sys.user.fields.branch" /></option>
					<c:forEach var="branch" items="${branches}">
					<option value="${branch.b_id}" <c:if test="${param.u_branchid == branch.b_id}">selected</c:if>>${branch.b_name}</option>
					</c:forEach>
				</select> &nbsp;

				<select class="tm_select" name="u_orderby" style="min-width:100px">
					<option value="NORMAL"><tomtag:Message key="txt.sys.user.fields.orderby" /></option>
					<option value="USERNAME_ASC" <c:if test="${param.u_orderby == 'USERNAME_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.user.fields.orderby.username_asc" /></option>
					<option value="USERNAME_DESC" <c:if test="${param.u_orderby == 'USERNAME_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.user.fields.orderby.username_desc" /></option>
					<option value="UNO_ASC" <c:if test="${param.u_orderby == 'UNO_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.user.fields.orderby.uno_asc" /></option>
					<option value="UNO_DESC" <c:if test="${param.u_orderby == 'UNO_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.user.fields.orderby.uno_desc" /></option>
					<option value="REGDATE_ASC" <c:if test="${param.u_orderby == 'REGDATE_ASC'}">selected</c:if>><tomtag:Message key="txt.sys.user.fields.orderby.regdate_asc" /></option>
					<option value="REGDATE_DESC" <c:if test="${param.u_orderby == 'REGDATE_DESC'}">selected</c:if>><tomtag:Message key="txt.sys.user.fields.orderby.regdate_desc" /></option>
				</select> &nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>
			</form>

			<form method="post" id="form_user_batch">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
						<th width="30"><input type="checkbox" name="tm_toggle" onclick="javascript:tmBatch.toggleUser(this)" /></th>
                        <th><tomtag:Message key="txt.sys.user.fields.username" /></th>
                    	<th><tomtag:Message key="txt.sys.user.fields.realname" /></th>
                        <th><tomtag:Message key="txt.sys.user.fields.no" /></th>
                        <th><tomtag:Message key="txt.sys.user.fields.photo" /></th>
                        <th><tomtag:Message key="txt.sys.user.fields.branch" /></th>
                    	<th><tomtag:Message key="txt.sys.user.fields.status" /></th>
                        <th><tomtag:Message key="txt.sys.user.fields.lastlogin" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="user" items="${page.dataList}">
                	<tr>
						<td><input type="checkbox" name="b_uids" value="${user.u_id}" /></td>
                    	<td>${user.u_username}</td>
						<td>${user.u_realname}</td>
						<td>${user.u_no}</td>
						<td>
							<c:choose>
								<c:when test="${not empty user.u_photo}">
									<img src="${tm_base}${user.u_photo}" class="tm_img_preview_m" style="cursor:pointer" onclick="tm_showPic(this.src)" />
								</c:when>
							</c:choose>
						</td>
						<td>${user.u_branchname}<br/>${user.p_name}</td>
                        <td>
							<c:choose>
								<c:when test="${user.u_status == '0'}">
								   <i><tomtag:Message key="txt.other.status.forcheck" /></i>
								</c:when>
								<c:when test="${user.u_status == '1'}">
								   <tomtag:Message key="txt.other.status.normal" />
								</c:when>
								<c:when test="${user.u_status == '-1'}">
									<font color="red"><tomtag:Message key="txt.other.status.lock" /></font>
								</c:when>
							</c:choose>
						</td>
                        <td>
							<tomtag:Message key="txt.sys.user.fields.logintimes" />:${user.u_logintimes}<br/>${user.u_lastlogin}</td>
                        <td>
							<div class="tm_div_operations">
							<a href="javascript:void(0);" onclick="javascript:tmShowExamList('${user.u_id}');"><tomtag:Message key="txt.sys.user.examlist" /></a>
							|
							<a href="system/user/load.thtml?id=${user.u_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							</div>
							<div class="tm_div_operations">
							<a href="javascript:void(0);" onclick="javascript:tmShowSelfTestList('${user.u_id}');"><tomtag:Message key="txt.sys.user.testlist" /></a>
							|
							<a href="system/user/delete.do?id=${user.u_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
							</div>
						</td>
                    </tr>
					</c:forEach>
                </tbody>
            </table>

			<div style="clear:both; margin:20px 0 0 0; height:30px; position:relative; ">
				<div style="height:30px; position:absolute; top:0px; left:0px; z-index:100; ">
					<select name="b_op" class="tm_select validate[required]" onchange="tmBatch.batchopChange(this)">
						<option value="">=<tomtag:Message key="txt.sys.user.batch" />=</option>
						<option value="batch_delete"><tomtag:Message key="txt.sys.user.batch.delete" /></option>
						<option value="batch_status"><tomtag:Message key="txt.sys.user.batch.audit" /></option>
						<option value="batch_branch"><tomtag:Message key="txt.sys.user.batch.move" /></option>
					</select>
					<span id="batch_span"></span>
					<input type="button" onclick="javascript:tm_batchSubmit();" class="tm_btn" value='<tomtag:Message key="txt.other.operations.submit" />' />
				</div>
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

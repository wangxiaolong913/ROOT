<%@ page language="java" pageEncoding="utf-8" import="com.tom.util.BaseUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.question.list" /></title>
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

	<script>
		window.onload = function(){
			$(".tm_table_list tbody tr").mouseover(function(){
				$(this).attr("style","background:#f5f5f5");
			});
			$(".tm_table_list tbody tr").mouseout(function(){
				$(this).attr("style","background:#ffffff");
			});
		}




	</script>

	<style>
		.tm_question_preview{margin:20px;}
		.tm_question_preview h2{font-size:14px; background:#eee; margin:10px 0 0 0; line-height:25px; padding-left:10px}
		.tm_question_preview div{line-height:25px; margin:0 10px; }
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.question.list" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.question.list" /></h1>
                <span><tomtag:Message key="txt.sys.question.listtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/question/add.thtml"><tomtag:Message key="txt.sys.question.add" /></a>
				<a href="system/question/list.thtml" class="on"><tomtag:Message key="txt.sys.question.list" /></a>
				<a href="system/question/import.thtml"><tomtag:Message key="txt.sys.question.import" /></a>
			</div>
        </div>
        
        
        <div class="tm_container">
			<form action="system/question/list.thtml" method="get">
			<div class="tm_searchbox">
				<tomtag:Message key="txt.other.operations.keyword" /> :
				<input type="text" name="q_content" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='q_content' />" /> &nbsp;
				
				<tomtag:Message key="txt.sys.question.fields.dbname" /> :
				<select class="tm_selects" name="q_dbid">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<c:forEach var="qdb" items="${qdbs}">
					<option value="${qdb.d_id}" <c:if test="${param.q_dbid == qdb.d_id}">selected</c:if>>${qdb.d_name}</option>
					</c:forEach>
				</select> &nbsp;
				
				<tomtag:Message key="txt.sys.question.fields.type" /> :
				<select name="q_type" class="tm_select">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<option value="1" <c:if test="${param.q_type == '1'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.single" /></option>
					<option value="2" <c:if test="${param.q_type == '2'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.multiple" /></option>
					<option value="3" <c:if test="${param.q_type == '3'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.judgment" /></option>
					<option value="4" <c:if test="${param.q_type == '4'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.blank" /></option>
					<option value="5" <c:if test="${param.q_type == '5'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.essay" /></option>
				</select> &nbsp;
				
				<tomtag:Message key="txt.sys.question.fields.level" /> :
				<select name="q_level" class="tm_select">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<option value="1" <c:if test="${param.q_level == '1'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev1" /></option>
					<option value="2" <c:if test="${param.q_level == '2'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev2" /></option>
					<option value="3" <c:if test="${param.q_level == '3'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev3" /></option>
					<option value="4" <c:if test="${param.q_level == '4'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev4" /></option>
					<option value="5" <c:if test="${param.q_level == '5'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev5" /></option>
				</select> &nbsp;

				<tomtag:Message key="txt.sys.question.fields.status" /> :
				<select name="q_status" class="tm_select">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<option value="1" <c:if test="${param.q_status == '1'}">selected</c:if>><tomtag:Message key="txt.other.status.open" /></option>
					<option value="0" <c:if test="${param.q_status == '0'}">selected</c:if>><tomtag:Message key="txt.other.status.close" /></option>
				</select> &nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.question.fields.dbname" /></th>
						<th><tomtag:Message key="txt.sys.question.fields.type" /></th>
                    	<th><tomtag:Message key="txt.sys.question.fields.status" /></th>
                        <th style="width:350px"><tomtag:Message key="txt.sys.question.fields.content" /></th>
                        <th><tomtag:Message key="txt.sys.question.fields.creator" /></th>
                        <th><tomtag:Message key="txt.sys.question.fields.modifier" /></th>
                        <th style="width:100px"><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="question" items="${page.dataList}">
                	<tr>
						<td>${question.q_qdbname}</td>
                    	<td><tomtag:Util action="getQuestionType" data="${question.q_type}" /></td>
						<td>
							<c:choose>
								<c:when test="${question.q_status == 0}"><font color="red"><tomtag:Message key="txt.other.status.close" /></font></c:when>
								<c:when test="${question.q_status == 1}"><tomtag:Message key="txt.other.status.open" /></c:when>
							</c:choose>
						</td>
						<td width="40%" style="text-align:left">
							<tomtag:Util action="html2txt" data="${question.q_content}" ext="30" />
						</td>
						<td>${question.q_poster}<br/>${question.q_createdate}</td>
						<td>${question.q_modifyor}<br/>${question.q_modifydate}</td>
                        <td>
							<a href="system/question/load.thtml?qid=${question.q_id}"><tomtag:Message key="txt.other.operations.modify" /></a>
							|
							<a href="system/question/delete.do?qid=${question.q_id}" onclick="return window.confirm('<tomtag:Message key="message.other.reallydelete" />');"><tomtag:Message key="txt.other.operations.delete" /></a>
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

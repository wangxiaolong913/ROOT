<%@ page language="java" pageEncoding="utf-8"  %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>系统消息 - System Message</title>
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="tm_message">
	<thead>
		<tr>
			<th colspan="2"><tomtag:Message key="message.other.sysmessage" /></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td width="70" style="text-align:center">
				<c:choose>
					<c:when test="${message.success}">
						<img src="skins/images/success.png" />
					</c:when>
					<c:otherwise>
						<img src="skins/images/error.png" />
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<div class="tm_message_msg">${message.message }</div>
				<div class="tm_message_btns">
					<%
					String pre_link = String.valueOf(request.getHeader("Referer"));
					String usertype = String.valueOf(session.getAttribute("SEN_USERTYPE"));
					if(pre_link != null && pre_link.length()>0 && "1".equals(usertype)){
						out.println("<a href='"+pre_link+"' class='tm_btn tm_btn_primary' id='tm_lnk_back'>立即返回 (8)</a> ");
					}
					%>
					<c:forEach var="link" items="${message.urls }">
						<a href="${link.url }" <c:if test="${link.top}">target="_top"</c:if> class="tm_btn">${link.title }</a> 
					</c:forEach>
				</div>
			</td>
		</tr>
	</tbody>
</table>


<script>
	var tm_seconds_left = 8;
	var tm_inter = null;

	window.onload = function () {
		var tm_lnk_back = document.getElementById("tm_lnk_back");
		if(tm_lnk_back){
			$("#tm_lnk_back").focus();
			tm_inter = setInterval("tm_countdown()", 1000);
		}
	};

	function tm_countdown(){
		tm_seconds_left --;
		if(tm_seconds_left < 0){
			window.location.href = $("#tm_lnk_back").prop("href");
		}else{
			$("#tm_lnk_back").html("立即返回 ("+tm_seconds_left+")");
		}
	}
</script>

</body>
</html>



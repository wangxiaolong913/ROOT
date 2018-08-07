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
				<img src="skins/images/error.png" />
			</td>
			<td>
				<div class="tm_message_msg"><tomtag:Message key="message.other.no_privelege" /></div>
				<div class="tm_message_btns">
					<a href="javascript:void(0);" onclick="history.go(-1);" class='tm_btn tm_btn_primary'><tomtag:Message key="message.other.goback" /></a>
					&nbsp;
					<a href="login.thtml" target="_parent" class='tm_btn'><tomtag:Message key="message.other.relogin" /></a>
				</div>
			</td>
		</tr>
	</tbody>
</table>

</body>
</html>



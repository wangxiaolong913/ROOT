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
			<td width="70" style="text-align:center" valign="top">
				<img src="skins/images/heart.png" style="margin-top:50px" />
			</td>
			<td>
				<div style="padding:0 20px 20px 0; line-height:20px">
					<b>感谢使用TomExam网络考试系统免费版</b><br/>
					欢迎您捐助，帮助我们更好的发展，您可以获得如下功能：
				</div>

				<div style="padding:0 20px 20px 0; line-height:20px">
					<ul style="margin:0 0 0 30px">
						<li>批量导入试题</li>
						<li>快速创建试卷</li>
						<li>在线学习功能</li>
						<li>支持用户数提升至200人</li>
					</ul>
				</div>

				<div style="padding:20px 20px 20px 20px;">
					<img src="skins/images/donation50.jpg" width="100" />
					<p>微信扫描二维码，捐助50元</p>
				</div>
			</td>
		</tr>
	</tbody>
</table>

</body>
</html>



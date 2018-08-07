<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>about</title>
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script type="text/javascript" src="inc/js/jquery.js"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<style>
		.tm_table_about{margin:100px auto; width:800px;}
		.tm_table_about tr th{width:200px; background:#fff; text-align:left}
		.tm_table_about tr td{width:600px; background:#fff; padding:10px 0 80px 0;}
		.tm_table_about tr td h1{font-family:"Microsoft Yahei"}
		.tm_table_about tr td p{font-size:13px; line-height:1.5em}

		.tm_register{clear:both; margin:20px 0; line-height:20px; border:dotted 1px #eee; padding:5px}
		.tm_register textarea{margin:10px 0}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){ 
			
		}); 
	</script>
</head>
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li>关于软件</li>
			</ul>
		</div>

	</div>

	
    <table border="0" cellpadding="0" cellspacing="10" class="tm_table_about">
		<tr>
			<th valign="top">
				<img src="skins/images/logo_max.png" width="150" style="margin-top:20px" />
			</th>
			<td valign="top">
				<h1>TomExam网络考试系统</h1>
				<p>感谢您选择TomExam产品，希望我们的努力能为您提供一个高效快速和强大的在线考试解决方案。</p>
				<p>TomExam是一款基于JAVA与MYSQL开发的网络考试系统。它可以稳定、顺畅的运行在Windows与Linux平台上。您可以通过它快捷方便的创建题库，发布试卷，组织考试，由系统自动批改，并对考试结果进行数据分析。高度的可配置性和灵活性使得它可以被应用于很多领域。</p>
				<br/>
				<p>
					<div><b>版本信息</b>：<tomtag:Util action="version" /> </div>
				</p>
				<p><b>系统状态</b>：${total_users} / ${auth_users} 用户数</p>
				<p><b>版权信息</b>：Copyright TomExam.com 2010-2016 All Right Reserved</p>
				<p><b>使用权限</b>：本计算机程序受到著作权法保护。未经授权而擅自修改、复制和传播本程序（或其中的任何部分），将受到严厉的民事和刑事制裁，并将在法律许可的最大限度内受到起诉。</p>
				<p><b>官网地址</b>：http://www.tomexam.com</p>
				<p><b>电子信箱</b>：admin@tomexam.com</p>
			</td>
		</tr>
	</table>

</body>
</html>



<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>

<!DOCTYPE html>
<html>
<head>
	<base href="${tm_base}" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Util action="getConfig" data="sys_sitename" /></title>
	<link rel="shortcut icon" href="favicon.ico" />

	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
</head>
  
<frameset rows="40,*"  frameborder="no" border="0" framespacing="0">
	<frame src="common/head.thtml" noresize="noresize" frameborder="no" name="top" scrolling="no" marginwidth="0" marginheight="0" target="main" />
	<frameset cols="200,*" id="mainframe" frameborder="no" border="0" framespacing="0">
		<frame src="common/menu.thtml" name="menu" id="menu" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" target="main" />
		<frame src="common/welcome.thtml" name="main" id="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" target="_self" />
	</frameset>
</frameset>

<noframes>
	<body>您的浏览器不支持框架。</body>
</noframes>

</html>

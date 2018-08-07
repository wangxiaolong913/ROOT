<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.other.welcome.news" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
    
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>

	<style>
		h1{ font-size:24px; line-height:20px; text-align:center;}
		h2{ font-size:12px; font-weight:normal; margin:10px 0; line-height:30px;  text-align:center}
		.tm_news_content{ border-top:solid 1px #eee; border-bottom:solid 1px #eee; padding:10px; margin-top:30px; clear:both;}
	</style>
	
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="news/list.thtml?n_classid=${news.n_classid}">${news.c_name}</a> <span class="divider">&gt;</span></li>
				<li class="active">${news.n_title}</li>
			</ul>
		</div>
        
        <div class="tm_container">

			<table border="0" width="100%" cellpadding="0" style="min-width:800px;">
				<tr>
					<!-- left -->
					<td valign="top">
						<h1>${news.n_title}</h1>
						<h2>
							<b><tomtag:Message key="txt.sys.news.fields.postdate" /></b>: ${news.n_createdate} &nbsp;
							<b><tomtag:Message key="txt.sys.news.fields.visit" /></b>: ${news.n_visit}
						</h2>
						<div class="tm_news_content">
							${news.n_content}
						</div>
						
						<p></p>

					</td><!-- /left -->

					<td width="10">&nbsp;</td>

					<!--  right-->
					<td width="220" valign="top">
						
					</td>
					<!--  /right-->
				</tr>
			</table>
			
        </div>
        
        
    </div>



</body>
</html>

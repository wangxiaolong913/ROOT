<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		.tm_news_link{ text-decoration:none;}
		.tm_news_link:hover{color:#f00;}
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><tomtag:Message key="txt.other.welcome.news" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<form action="news/list.thtml" method="get">
        	<div class="tm_searchbox">
				<tomtag:Message key="txt.other.operations.keyword" /> :
				<input type="text" name="n_title" class="tm_txts" size="10" maxlength="20" value="<tomtag:Util action='param' data='n_title' />" /> &nbsp;
				
				<tomtag:Message key="txt.sys.news.fields.category" /> :
				<select class="tm_selects" name="n_classid">
					<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
					<c:forEach var="category" items="${categories}">
					<option value="${category.c_id}" <c:if test="${param.n_classid == category.c_id}">selected</c:if>>${category.c_name}</option>
					</c:forEach>
				</select> &nbsp;

				<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.news.fields.title" /></th>
						<th><tomtag:Message key="txt.sys.news.fields.category" /></th>
                        <th><tomtag:Message key="txt.sys.news.fields.visit" /></th>
                        <th><tomtag:Message key="txt.sys.news.fields.creator" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="news" items="${page.dataList}">
                	<tr>
						<td style="text-align:left">
							<a href="news/detail.thtml?id=${news.n_id}" class="tm_news_link">${news.n_title}</a>
						</td>
                    	<td>${news.c_name}</td>
						<td>${news.n_visit}</td>
						<td>${news.n_poster}<br/>${news.n_createdate}</td>
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

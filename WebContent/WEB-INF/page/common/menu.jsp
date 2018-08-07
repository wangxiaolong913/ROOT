<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>menu</title>
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	
	<script type="text/javascript" src="inc/js/jquery.js"></script>
	<script type="text/javascript">
		function tm_toggle_menu(obj){
			$(obj).parent().siblings().toggle();
		}
		function tm_mark_current_menuitem(){
			
		}

		$(document).ready(function() { 
			$(".tm_menu_item ul li a").click(function(){
				$(".tm_menu_item ul li").removeClass("itemon");
				$(this).parent().addClass("itemon");
			});
			
		});

		document.oncontextmenu= function(){return false;}

	</script>

	<style>
		.tmc_menu_qdb{background:url('skins/images/icos/menu_qdb.png') no-repeat 8px 8px;}
		.tmc_menu_paper{background:url('skins/images/icos/menu_paper.png') no-repeat 8px 8px;}
		.tmc_menu_analysis{background:url('skins/images/icos/menu_analysis.png') no-repeat 8px 8px;}
		.tmc_menu_user{background:url('skins/images/icos/menu_users.png') no-repeat 8px 8px;}
		.tmc_menu_interaction{background:url('skins/images/icos/menu_interaction.png') no-repeat 8px 8px;}
		.tmc_menu_system{background:url('skins/images/icos/menu_system.png') no-repeat 8px 8px;}
		.tmc_menu_learn{background:url('skins/images/icos/menu_learn.png') no-repeat 8px 8px;}

		
		.tmc_menu_test{background:url('skins/images/icos/menu_test.png') no-repeat 8px 8px;}
		.tmc_menu_self{background:url('skins/images/icos/menu_self.png') no-repeat 8px 8px;}
		.tmc_menu_account{background:url('skins/images/icos/menu_account.png') no-repeat 8px 8px;}
	</style>
</head>
<body oncontextmenu="return false">

	<div class="tm_menu">
    	
		<c:if test="${sessionScope.SEN_USERTYPE == '1'}">
		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_qdb"><tomtag:Message key="txt.other.menu.admin.questions" /></a></h2>
			<ul>
				<tomtag:Authority authcode="P-QDB-ADD" placeholder="" ><li><a href="system/qdb/add.thtml" target="main"><tomtag:Message key="txt.sys.qdb.add" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-QDB" placeholder="" ><li><a href="system/qdb/list.thtml" target="main"><tomtag:Message key="txt.sys.qdb.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="P-QES-ADD" placeholder="" ><li><a href="system/question/add.thtml" target="main"><tomtag:Message key="txt.sys.question.add" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="P-QES-IMPORT" placeholder="" ><li><a href="system/question/import.thtml" target="main"><tomtag:Message key="txt.sys.question.import" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-QES" placeholder="" ><li><a href="system/question/list.thtml" target="main"><tomtag:Message key="txt.sys.question.list" /></a></li></tomtag:Authority>
			</ul>
		</div>
				
		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_paper"><tomtag:Message key="txt.other.menu.admin.papers" /></a></h2>
			<ul>
				<tomtag:Authority authcode="P-PAPER-ADD" placeholder="" ><li><a href="system/paper/add.thtml" target="main"><tomtag:Message key="txt.sys.paper.add" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="P-PAPER-ADD" placeholder="" ><li><a href="system/paper/fastadd.thtml" target="main"><tomtag:Message key="txt.sys.paper.fastadd" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-PAPER" placeholder="" ><li><a href="system/paper/list.thtml" target="main"><tomtag:Message key="txt.sys.paper.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-PAPER-CATE" placeholder="" ><li><a href="system/paper/category/list.thtml" target="main"><tomtag:Message key="txt.sys.paper.category.list" /></a></li></tomtag:Authority>
			</ul>
		</div>

		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_paper"><tomtag:Message key="txt.other.menu.admin.usertest" /></a></h2>
			<ul style="display:none">
				<tomtag:Authority authcode="P-SELFTEST-RECORDS" placeholder="" ><li><a href="system/selftest/list.thtml" target="main"><tomtag:Message key="txt.sys.selftest.list" /></a></li></tomtag:Authority>
			</ul>
		</div>

		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_analysis"><tomtag:Message key="txt.other.menu.admin.analysis" /></a></h2>
			<ul style="display:none">
				<tomtag:Authority authcode="P-ANA-PAPER" placeholder="" ><li><a href="system/analysis/paper/index.thtml" target="main"><tomtag:Message key="txt.sys.analysis.paper" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="P-ANA-EXAM" placeholder="" ><li><a href="system/analysis/exam/index.thtml" target="main"><tomtag:Message key="txt.sys.analysis.exam" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="P-ANA-SCORE" placeholder="" ><li><a href="system/analysis/score/index.thtml" target="main"><tomtag:Message key="txt.sys.analysis.score" /></a></li></tomtag:Authority>
			</ul>
		</div>

		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_learn"><tomtag:Message key="txt.other.menu.admin.learn" /></a></h2>
			<ul>
				<tomtag:Authority authcode="F-COURSE" placeholder="" ><li><a href="system/course/list.thtml" target="main"><tomtag:Message key="txt.sys.course.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-COURSE-CATEGORY" placeholder="" ><li><a href="system/course/category/list.thtml" target="main"><tomtag:Message key="txt.sys.course.category.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-COURSE-TEACHER" placeholder="" ><li><a href="system/course/teacher/list.thtml" target="main"><tomtag:Message key="txt.sys.course.teacher.list" /></a></li></tomtag:Authority>
			</ul>
		</div>
			
		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_user"><tomtag:Message key="txt.other.menu.admin.users" /></a></h2>
			<ul style="display:none">
				<tomtag:Authority authcode="F-USERS" placeholder="" ><li><a href="system/user/list.thtml" target="main"><tomtag:Message key="txt.sys.user.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-BRANCH" placeholder="" ><li><a href="system/branch/list.thtml" target="main"><tomtag:Message key="txt.sys.branch.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-UPOSITION" placeholder="" ><li><a href="system/userposition/list.thtml" target="main"><tomtag:Message key="txt.sys.userposition.list" /></a></li></tomtag:Authority>
			</ul>
		</div>
			
		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_interaction"><tomtag:Message key="txt.other.menu.admin.interaction" /></a></h2>
			<ul style="display:none">
				<tomtag:Authority authcode="F-NEWS" placeholder="" ><li><a href="system/news/list.thtml" target="main"><tomtag:Message key="txt.sys.news" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-NEWSCATE" placeholder="" ><li><a href="system/news/category/list.thtml" target="main"><tomtag:Message key="txt.sys.news.category" /></a></li></tomtag:Authority>
			</ul>
		</div>

		<div class="tm_menu_item clearfix">
			<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_system"><tomtag:Message key="txt.other.menu.admin.system" /></a></h2>
			<ul style="display:none">
				<tomtag:Authority authcode="F-SYS" placeholder="" ><li><a href="system/config/list.thtml" target="main"><tomtag:Message key="txt.sys.config.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-ADMIN" placeholder="" ><li><a href="system/admin/list.thtml" target="main"><tomtag:Message key="txt.sys.admin.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="F-ROLE" placeholder="" ><li><a href="system/role/list.thtml" target="main"><tomtag:Message key="txt.sys.role.list" /></a></li></tomtag:Authority>
				<tomtag:Authority authcode="P-SYS-LOG" placeholder="" ><li><a href="system/log/list.thtml" target="main"><tomtag:Message key="txt.sys.log.list" /></a></li></tomtag:Authority>
				<li><a href="common/about.thtml" target="main"><tomtag:Message key="txt.other.menu.about" /></a></li>
			</ul>
		</div>
		</c:if>


		<c:if test="${sessionScope.SEN_USERTYPE == '0'}">
		<div class="tm_menu_item clearfix">
        	<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_test"><tomtag:Message key="txt.other.menu.user.myexams" /></a></h2>
            <ul>
				<li><a href="user/paper/list.thtml" target="main"><tomtag:Message key="txt.user.paper" /></a></li>
				<li><a href="user/paper/history.thtml" target="main"><tomtag:Message key="txt.user.history" /></a></li>
            </ul>
        </div>

		<div class="tm_menu_item clearfix">
        	<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_self"><tomtag:Message key="txt.other.menu.user.myselftests" /></a></h2>
            <ul>
				<li><a href="user/selftest/new.thtml" target="main"><tomtag:Message key="txt.user.selftest" /></a></li>
				<li><a href="user/selftest/history.thtml" target="main"><tomtag:Message key="txt.user.selfhistory" /></a></li>
            </ul>
        </div>

		<div class="tm_menu_item clearfix">
        	<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_learn"><tomtag:Message key="txt.other.menu.user.learning" /></a></h2>
            <ul>
				<li><a href="user/course/list.thtml" target="main"><tomtag:Message key="txt.user.learn.list" /></a></li>
				<li><a href="user/course/my.thtml" target="main"><tomtag:Message key="txt.user.learn.my" /></a></li>
            </ul>
        </div>

		<div class="tm_menu_item clearfix">
        	<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_account"><tomtag:Message key="txt.other.menu.user.userinfo" /></a></h2>
            <ul>
				<li><a href="user/collection/list.thtml" target="main"><tomtag:Message key="txt.user.collection" /></a></li>
				<li><a href="user/profile.thtml" target="main"><tomtag:Message key="txt.user.profile" /></a></li>
            </ul>
        </div>
		</c:if>
        
    </div>


</body>
</html>



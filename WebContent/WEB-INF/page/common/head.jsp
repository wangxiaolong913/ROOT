<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>head</title>
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script type="text/javascript" src="inc/js/jquery.js"></script>
	<script type="text/javascript">
		var tm_var_menustatus = 0;
		function tm_switch_menu(){
			if(tm_var_menustatus==0){
				tm_var_menustatus = 1;
				$('#mainframe', window.parent.document).attr("cols","0,*");
			}else{
				tm_var_menustatus = 0;
				$('#mainframe', window.parent.document).attr("cols","200,*");
			}
			
		}

		var tm = {
			logout : function(){
				if(window.confirm('<tomtag:Message key="txt.other.menu.logout.tip" />')){
					$.ajax({
						type: "POST",
						url: "${tm_base}common/logout.do",
						data: "t=" + Math.random(),
						success: function(msg){
							top.location.href = "${tm_base}logout.thtml";
						},
						error : function(){
							top.location.href = "${tm_base}logout.thtml";
						}
					});
				}
			}
		};

		var tm_activemenu = function(obj){
			$(obj).parent().parent().children("li").attr("class","");
			$(obj).parent().attr("class","active");
		}

	</script>
</head>
<body>

    <div class="tm_head">
    
        <div class="tm_head_logo"><a href="http://www.tomexam.com" target="_blank"><img src="skins/images/logo.png" /></a></div>
        <div class="tm_head_switch"><a href="javascript:void(0);" onclick="tm_switch_menu()"><img src="skins/images/ico_lines.png" /></a></div>

		<c:if test="${sessionScope.SEN_USERTYPE == '1'}">
			<div class="tm_head_menu">
				<ul>
					
				</ul>
			</div>

			<div class="tm_head_tools">
				<img src="skins/images/icos/ico_account.png" align="absmiddle"/> 
				<span style="cursor:pointer" title='<tomtag:Message key="txt.sys.user.fields.username" /> : ${sessionScope.SEN_USERNAME} &#10<tomtag:Message key="txt.sys.user.fields.realname" /> : ${admin.a_realname}  '>
					${sessionScope.SEN_USERNAME} (${admin.a_realname})
				</span> |
				<a href="system/profile.thtml" target="main"><tomtag:Message key="txt.other.account" /></a> |
				<a href="http://www.tomexam.com/link/?m=feedback" target="_blank"><tomtag:Message key="message.other.feedback" /></a> |
				<a href="javascript:void(0);" onclick="return tm.logout();"><tomtag:Message key="txt.other.menu.logout" /></a>
			</div>
		</c:if>


		<c:if test="${sessionScope.SEN_USERTYPE == '0'}">
			<div class="tm_head_menu">
				<ul>
				</ul>
			</div>

			<div class="tm_head_tools">
				<img src="skins/images/icos/ico_account.png" align="absmiddle" /> 
				<span style="cursor:pointer" title='<tomtag:Message key="txt.sys.user.fields.username" /> : ${sessionScope.SEN_USERNAME} &#10<tomtag:Message key="txt.sys.user.fields.realname" /> : ${user.u_realname}  &#10<tomtag:Message key="txt.sys.user.fields.no" /> : ${user.u_no} &#10<tomtag:Message key="txt.sys.user.fields.lastlogin" /> : ${user.u_lastlogin}'>
					${sessionScope.SEN_USERNAME} (${user.u_realname})
				</span> |
				<a href="user/profile.thtml" target="main"><tomtag:Message key="txt.other.account" /></a> |
				<a href="javascript:void(0);" onclick="return tm.logout();"><tomtag:Message key="txt.other.menu.logout" /></a>
			</div>
		</c:if>
        
        
    </div>

	<iframe frameBorder="0" scrolling="no" src="inc/heartbeat.jsp" style="width:1px; height:1px; display:none"></iframe>

</body>
</html>



<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html>
<html>
<head>
	<base href="${tm_base}" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.other.login.title" /> - <tomtag:Util action="version" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/jquery.md5.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	
	<style>
		.tm_login_body{
			background:url('skins/images/backgrounds/001.jpg');
			background-size:cover;
			-moz-background-size:cover;
			background-repeat:no-repeat;
		}
		.tm_login_container{ width:500px; margin:200px auto; clear:both}
		.tm_login_title{
			height:80px;
			margin:10px 0 15px 0;
			background:#fff;
			text-align:center;
			border-bottom:solid 1px #eee;
		}
		.tm_login_title img{
			height:50px;
		}
		.tm_login_title span{
			font-size:25px; 
			line-height:80px;
			font-family:'Microsoft Yahei',Tahoma, Geneva, 'Simsun';
		}
		.tm_login_form{ 
			width:100%; 
			height:320px;
			clear:both; 
			-moz-border-radius:8px;
			-webkit-border-radius:8px;
			border-radius:8px;
			padding:1px;
			background:#fff;
		}
		.tm_login_table{ width:400px; margin:20px auto;}
		.tm_login_table tr th{ width:100px;}
		.tm_login_table tr td{ width:300px; text-align:left}

		.tm_login_title_table{ width:400px; margin:0px auto;}
		.tm_login_title_table tr th{ width:100px;}
		.tm_login_title_table tr td{ width:300px; text-align:left}
		
		.tm_login_foot{ width:100%; line-height:20px; text-align:center; clear:both; margin:20px 0}
		
		
		html { overflow: hidden; } 
		body { overflow: hidden; } 
	</style>
	<script type="text/javascript">
		$(document).ready(function(){ 
			$("input[name='usercode']").keydown(function(e){ 
				var curKey = e.which; 
				if(curKey == 13){
					tm.doLogin(); 
				} 
			}); 
		}); 

		var tm = {
			reloadImgcode : function(){
				$("#img_verifycode").attr("src", "${tm_base}inc/checkcode.jsp?t=" + Math.random());
			},
			doReset : function(){
				$("input[name='username']").val('');
				$("input[name='userpass']").val('');
				$("input[name='usercode']").val('');
			},
			doLogin : function(){
				var username = $("input[name='username']").val();
				var userpass = $("input[name='userpass']").val();
				var usercode = $("input[name='usercode']").val();
				var usertype = $("select[name='usertype']").val();
				if(baseutil.isEmpty(username)){
					alert('<tomtag:Message key="txt.other.login.msg.username_empty" />');
					return;
				}
				if(baseutil.isEmpty(userpass)){
					alert('<tomtag:Message key="txt.other.login.msg.password_empty" />');
					return;
				}
				if(baseutil.isEmpty(usercode)){
					alert('<tomtag:Message key="txt.other.login.msg.usercode_empty" />');
					return;
				}

				$(".tm_btn_primary").text('<tomtag:Message key="txt.other.login.login" />...');
				
				$.ajax({
					type: "POST",
					url: "${tm_base}common/login.do",
					dataType: "json",
					data: {username:username, userpass:userpass, usertype:usertype, usercode:usercode, t:Math.random()},
					success: function(data){
						if(data){
							var ret_code = data["code"];
							var ret_msg = data["message"];

							if(ret_code == 1 || ret_code == "1"){
								window.location="${tm_base}index.thtml";
							}else{
								alert(ret_msg);
								$(".tm_btn_primary").text('<tomtag:Message key="txt.other.login.login" />');
								return;
							}
						}
					},
					error: function(){
						$(".tm_btn_primary").text('<tomtag:Message key="txt.other.login.login" />');
						alert('<tomtag:Message key="message.other.systembusy" />');
					}
				}); 

			}
		};
	</script>
  </head>
  
<body class="tm_login_body">

	<div class="tm_login_container">
    	<div class="tm_login_form">
			<div class="tm_login_title">
				<table border="0" cellpadding="0" cellspacing="0" class="tm_login_title_table">
					<tr>
						<th><img src="skins/images/logo_max.png" align="absmiddle" /></th>
						<td><span><tomtag:Util action="getConfig" data="sys_sitename" /></span></td>
					</tr>
				</table>
			</div>
            <table border="0" cellpadding="5" cellspacing="0" class="tm_login_table">
                <tr>
                    <th><tomtag:Message key="txt.other.login.username" /></th>
                    <td><input type="text" class="tm_txt" name="username" maxlength="20" value="" style="width:255px" /></td>
                </tr>
                <tr>
                    <th><tomtag:Message key="txt.other.login.password" /></th>
                    <td><input type="password" class="tm_txt" name="userpass" maxlength="20" value="" style="width:255px" /></td>
                </tr>
                <tr>
                    <th><tomtag:Message key="txt.other.login.usertype" /></th>
                    <td>
						<select class="tm_select" name="usertype">
							<option value="0"><tomtag:Message key="txt.other.login.usertype.user" /></option>
							<option value="1"><tomtag:Message key="txt.other.login.usertype.admin" /></option>
						</select>
						&nbsp;
                        <input type="text" class="tm_txt" name="usercode" maxlength="4" value=""  style="width:50px" />
						&nbsp;
                        <img src="inc/checkcode.jsp" align="absmiddle" id="img_verifycode" onclick="tm.reloadImgcode()" style="cursor:pointer" />
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
						<div style="margin-top:10px">
							<button type="button" class="tm_btn tm_btn_primary" style="width:50%" onclick="tm.doLogin();"><tomtag:Message key="txt.other.login.login" /></button>
							<button type="button" class="tm_btn" onclick="tm.doReset();" style="width:40%"><tomtag:Message key="txt.other.login.reset" /></button>
						</div>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td></td>
                </tr>
            </table>
        </div>
		
		<div class="tm_login_foot">
			<div><tomtag:Message key="txt.other.login.browsertip" /></div>
			<div>Copyright &copy; TomExam.com  All Right Reserved 2009-2016</div>
		</div>
	</div>

</body>
</html>

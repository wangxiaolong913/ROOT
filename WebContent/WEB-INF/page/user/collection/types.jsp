<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.collection" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
    <link rel="stylesheet" type="text/css" href="inc/js/pagination/pagination.css" />

	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>


	<link rel="stylesheet" href="inc/js/jquery-validation-engine/css/validationEngine.jquery.css" />
	<script src="inc/js/jquery-validation-engine/js/jquery.validationEngine.js"></script>
	
	<tomtag:Util action="lang" data="zh_CN">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-zh_CN.js"></script>
	</tomtag:Util>

	<tomtag:Util action="lang" data="en">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-en.js"></script>
	</tomtag:Util>

	<style>
		.tm_type_name:hover{cursor:pointer; background:url('skins/images/icos/pencil.png') no-repeat; background-position:center right}
		.tm_name_input{display:none}
	</style>

	<script type="text/javascript">

		$(document).ready(function() {
			$(".tm_type_name").click(function(){
				$(this).find(".tm_name_word").hide();
				$(this).find(".tm_name_input").show();
				$(this).find(".tm_name_input").focus();
			});

			$(".tm_name_input").change(function(){
				var val = $(this).val();
				var id = $(this).parent().data("tid");

				$(this).parent().data("tname", val);
				$(this).prev().html(val);

				tmCollection.doUpdateType(id, val);

				$(this).hide();
				$(this).prev().show();
			});

			$(".tm_name_input").blur(function(){
				$(this).hide();
				$(this).prev().show();
			});

		});

		var tmCollection = {
			
			doAddType : function(){
				var formcheck = $("#form_type_add").validationEngine('validate');
				if(formcheck){
					var t_name = $("#t_name").val();
					$.ajax({
						type: "POST",
						url: "${tm_base}user/collection/addtype.do",
						data: {"t_name":t_name, "t":Math.random()},
						dataType: "json",
						success: function(ret){
							if(ret["code"] == "ok"){
								window.location.reload();
							}else if(ret["code"] == "maxlimit"){
								alert('<tomtag:Message key="message.user.collection.type.add.maxlimit" />');
							}else{
								alert('<tomtag:Message key="message.other.op_failed" />');
							}
						},
						error:function(){
							alert('<tomtag:Message key="message.other.systembusy" />');
						}
					}); 
				}else{
					return false;
				}
			},

			doDelType : function(id){
				var _confirm =  window.confirm('<tomtag:Message key="message.other.reallydelete" />');
				if(_confirm){
					$.ajax({
						type: "POST",
						url: "${tm_base}user/collection/deletetype.do",
						data: {"id":id, "t":Math.random()},
						dataType: "json",
						success: function(ret){
							if(ret["code"] == "ok"){
								window.location.reload();
							}else if(ret["code"] == "hasdata"){
								alert('<tomtag:Message key="message.user.collection.type.delete.hasdata" />');
							}else{
								alert('<tomtag:Message key="message.other.op_failed" />');
							}
						},
						error:function(){
							alert('<tomtag:Message key="message.other.systembusy" />');
						}
					}); 
					
				}
			},

			doUpdateType : function(id, tname){
				$.ajax({
					type: "POST",
					url: "${tm_base}user/collection/updatetype.do",
					data: {"id":id, "t_name":tname, "t":Math.random()},
					dataType: "json",
					success: function(ret){
						if(ret["code"] == "ok"){
							
						}else{
							alert('<tomtag:Message key="message.other.op_failed" />');
						}
					},
					error:function(){
						alert('<tomtag:Message key="message.other.systembusy" />');
					}
				}); 
			}

		};

	</script>

  </head>
  
<body>

	<div class="tm_main">
        
        <div class="tm_container">
        	<div class="tm_navbuttons">
				<form id="form_type_add" method="post">
				<tomtag:Message key="txt.user.collection.type.fields.name" /> :
				<input type="text" id="t_name" class="validate[required] tm_txt" maxlength="20" data-prompt-position="bottomRight:-30" />
				<button class="tm_btns" type="button" onclick="tmCollection.doAddType();"><tomtag:Message key="message.user.collection.type.add" /></button>
				</form>
			</div>
        </div>

        <div class="tm_container">
        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
				<thead>
                	<tr>
                        <th><tomtag:Message key="txt.user.collection.type.fields.name" /></th>
                    	<th><tomtag:Message key="txt.user.collection.type.fields.count" /></th>
                        <th><tomtag:Message key="txt.other.operations" /></th>
                    </tr>
                </thead>
				<tbody>
				<c:forEach var="type" items="${types}">
				<tr>
					<td data-tid="${type.t_id}" data-tname="${type.t_name}" class="tm_type_name">
						<span class="tm_name_word">${type.t_name}</span>
						<input type="text" class="tm_txt tm_name_input" size="10" maxlength="20" value="${type.t_name}" />
					</td>
					<td>${type.total}</td>
					<td><a href="javascript:void(0)" onclick="tmCollection.doDelType('${type.t_id}')"><tomtag:Message key="txt.other.operations.delete" /></a></td>
				</tr>
				</c:forEach>
				</tbody>
			</table>

			<div style="margin:10px 0; color:#999; text-align:left">
				<img src="skins/images/icos/point_yellow.png" align="absmiddle" />
				<tomtag:Message key="txt.user.collection.type.add.tip" />
			</div>

        </div>
        
    </div>

</body>
</html>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.question.update" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
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

	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<script type="text/javascript">

		var editor_content = null;
		var editor_resolve = null;

		window.onload = function(){

			jQuery('#form_question_load').validationEngine();

			editor_content = CKEDITOR.replace('q_content'); 
			editor_resolve = CKEDITOR.replace('q_resolve', {
				toolbar : 'Basic'
			});

			tmQuestion.formInit('${question.q_type}');

			$("#tm_question_status_tip").html(tm_var_question_status[${question.q_status}]);
		}

		//空格数
		var var_Blanks = 0;

		var tmQuestion = {
			formInit : function(qtype){
				var json = eval(${json});

				if(qtype == 1){
					tmQuestion.formInitSingle(json);
				}else if(qtype == 2){
					tmQuestion.formInitMultiple(json);
				}else if(qtype == 3){
					tmQuestion.formInitJudgment(json);
				}else if(qtype == 4){
					tmQuestion.formInitBlank(json);
				}else if(qtype == 5){
					tmQuestion.formInitEssay(json);
				}
			},
			
			formInitSingle : function(json){
				var html = [];
				var btnval = '<tomtag:Message key="txt.sys.question.tips.addoption" />';
				html.push("<div><input type='button' class='tm_btn' value='"+btnval+"' onclick='tmQuestion.addOption(1)' /></div>");

				var tm_options = json["options"];
				html.push('<table class="tm_question_options" align="left">');
				$(tm_options).each(function(i, ele){
					html.push('<tr>');
					html.push('	<td width="80" id="tm_option_' + ele["alisa"] + '"><tomtag:Message key="txt.sys.question.tips.option" />' + ele["alisa"] + '&nbsp;');
					html.push('	<input type="radio" class="validate[required]" name="q_key" value="' + ele["alisa"] + '" /></td>');
					html.push('	<th><textarea rows="3" name="q_options" class="tm_txtx validate[required]">' + ele["text"] + '</textarea></th>');
					html.push('	<td width="50"><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td>');
					html.push('</tr>');
				}); 
				html.push('</table>');

				$("#tm_body").html(html.join(""));
				$("#tm_title").html('<tomtag:Message key="txt.sys.question.tips.optionsetting" /> : ');
				$("input[name='q_key']").val(json["key"].split(''));
			},

			formInitMultiple : function(json){
				var html = [];
				var btnval = '<tomtag:Message key="txt.sys.question.tips.addoption" />';
				html.push("<div><input type='button' class='tm_btn' value='"+btnval+"' onclick='tmQuestion.addOption(2)' /></div>");

				var tm_options = json["options"];
				html.push('<table class="tm_question_options" align="left">');
				$(tm_options).each(function(i, ele){
					html.push('<tr>');
					html.push('	<td width="80" id="tm_option_' + ele["alisa"] + '"><tomtag:Message key="txt.sys.question.tips.option" />' + ele["alisa"] + '&nbsp;');
					html.push('	<input type="checkbox" class="validate[required]" name="q_key" value="' + ele["alisa"] + '" /></td>');
					html.push('	<th><textarea rows="3" name="q_options" class="tm_txtx validate[required]">' + ele["text"] + '</textarea></th>');
					html.push('	<td width="50"><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td>');
					html.push('</tr>');
				}); 
				html.push('</table>');

				$("#tm_body").html(html.join(""));
				$("#tm_title").html('<tomtag:Message key="txt.sys.question.tips.optionsetting" /> : ');
				$("input[name='q_key']").val(json["key"].split(''));
			},

			formInitJudgment : function(json){
				var html = [];
				html.push('<label style="line-height:30px"><input type="radio" class="validate[required]" name="q_key" value="Y" /><tomtag:Message key="txt.sys.question.tips.yes" /></label>');
				html.push('<br/>');
				html.push('<label style="line-height:30px"><input type="radio" class="validate[required]" name="q_key" value="N" /><tomtag:Message key="txt.sys.question.tips.no" /></label>');

				$("#tm_body").html(html.join(""));
				$("#tm_title").html('<tomtag:Message key="txt.sys.question.tips.key" /> : ');
				$("input[name='q_key']").val(json["key"].split(''));
			},
			
			formInitBlank : function(json){
				var html = [];
				html.push('<div>');
				html.push(' <input type="button" class="tm_btn" value="<tomtag:Message key="txt.sys.question.tips.addblank" />" onclick="tmQuestion.addBlank();" />');
				html.push(' <input type="checkbox" value="Y" name="q_iscomplex" /><tomtag:Message key="txt.sys.question.tips.complex" />');
				html.push(' <input type="text" class="validate[required]" style="clear:both; width:1px; height:1px; border:none;" id="tm_blank_marker" value="" />');
				html.push('</div>');
				html.push('<table class="tm_question_options" align="left">');
				html.push('<tr><td id="tm_question_blanks"></td></tr>');
				html.push('</table>');
				$("#tm_body").html(html.join(""));

				/******** AddBlank ********/
				$(json["blanks"]).each(function(i, ele){
					var _qblankid = parseInt(ele["id"]);
					if(_qblankid>=var_Blanks){
						var_Blanks = _qblankid;
					}

					var html = [];
					html.push('<div class="tm_question_blank">');
					html.push('<span>' + ele["id"] + ' : </span>');
					html.push('<input name="q_blankids" type="hidden" value="'+ele["id"]+'" />');
					html.push('<input name="q_blanks" type="input" class="validate[required] tm_txt" maxlength="30" class="txt" value="' + ele["value"] + '" />');
					html.push('<a href="javascript:;" data-blankid="' + ele["id"] + '" class="tm_ico_delete" onclick="tmQuestion.removeBlank(this)"></a>');
					html.push('</div>');
					$("#tm_question_blanks").append(html.join(""));
					$("#tm_blank_marker").val("HASVALUE");
				});
				/******** AddBlank *******/

				$("#tm_title").html('<tomtag:Message key="txt.sys.question.tips.optionsetting" /> : ');

				var v_complex = json["complex"] ? "Y" : "N";
				$("input[name='q_iscomplex']").val(v_complex.split(""));
			},
			
			formInitEssay : function(json){
				var html = [];
				html.push('<div><textarea name="q_key" rows="5" class="validate[required] tm_txtx" style="width:98%">'+json["key"]+'</textarea></div>');
				
				$("#tm_body").html(html.join(""));
				$("#tm_title").html('<tomtag:Message key="txt.sys.question.tips.key" /> : ');
			},
			
			/*************** Opreations ************/
			addOption : function(qtype){
				var tm_options = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
				for(var i=0; i<tm_options.length; i=i+1){
					var this_value = tm_options[i];
					//不存在就添加
					if(!document.getElementById("tm_option_" + this_value)){
						var html = [];
						
						html.push('<tr>');
						html.push('	<td width="80" id="tm_option_' + this_value + '"><tomtag:Message key="txt.sys.question.tips.option" />' + this_value + '&nbsp;');

						if(1 == qtype){
							html.push('	<input type="radio" class="validate[required]" name="q_key" value="' + this_value + '" /></td>');
						}else if(2 == qtype){
							html.push('	<input type="checkbox" class="validate[required]" name="q_key" value="' + this_value + '" /></td>');
						}
						
						html.push('	<th><textarea rows="3" name="q_options" class="tm_txtx validate[required]"></textarea></th>');
						html.push('	<td width="50"><a href="javascript:;" onclick="$(this).parent().parent().remove()" class="tm_ico_delete"></a></td>');
						html.push('</tr>');

						$(".tm_question_options").append(html.join(""));
						
						break;
					}
				}
			},

			addBlank : function(){
				var_Blanks ++;
				var html = [];
				html.push('<div class="tm_question_blank">');
				html.push('<span>' + var_Blanks + ' : </span>');
				html.push('<input name="q_blankids" type="hidden" value="'+var_Blanks+'" />');
				html.push('<input name="q_blanks" type="input" class="validate[required] tm_txt" maxlength="30" class="txt" />');
				html.push('<a href="javascript:;" data-blankid="'+var_Blanks+'" class="tm_ico_delete" onclick="tmQuestion.removeBlank(this)"></a>');
				html.push('</div>');

				editor_content.insertHtml("[BlankArea"+var_Blanks+"]");
				$("#tm_question_blanks").append(html.join(""));

				$("#tm_blank_marker").val('HASVALUE');
			},

			removeBlank : function(obj){
				var question_content = editor_content.getData();
				$(obj).parent().remove();
				var blankid = $(obj).attr("data-blankid");
				question_content = question_content.replace("[BlankArea"+blankid+"]","");
				editor_content.setData(question_content);

				var _blanks = $("input[name='q_blanks']").length;
				if(_blanks<=0){
					$("#tm_blank_marker").val("");
				}
			}



		};

		var tm_var_question_status = {
			'1' : '<tomtag:Message key="txt.sys.question.fields.status.open" />',
			'0' : '<font color=\'red\'><tomtag:Message key="txt.sys.question.fields.status.close" /></font>'
		};

		function tm_question_status_change(obj){
			$("#tm_question_status_tip").html(tm_var_question_status[obj.value]);
		}

	</script>
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.question.update" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.question.update" /></h1>
                <span><tomtag:Message key="txt.sys.question.updatetip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/question/add.thtml"><tomtag:Message key="txt.sys.question.add" /></a>
				<a href="system/question/list.thtml"><tomtag:Message key="txt.sys.question.list" /></a>
				<a href="system/question/import.thtml"><tomtag:Message key="txt.sys.question.import" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="system/question/update.do" method="post" id="form_question_load">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.question.fields.type" /> : </th>
                        <td width="40%">
							<select name="q_type_show" class="tm_select" style="min-width:200px; color:#bbb" disabled="disabled">
								<option value="1" <c:if test="${question.q_type == '1'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.single" /></option>
								<option value="2" <c:if test="${question.q_type == '2'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.multiple" /></option>
								<option value="3" <c:if test="${question.q_type == '3'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.judgment" /></option>
								<option value="4" <c:if test="${question.q_type == '4'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.blank" /></option>
								<option value="5" <c:if test="${question.q_type == '5'}">selected</c:if>><tomtag:Message key="txt.other.questiontype.essay" /></option>
							</select>
							<input type="hidden" name="q_type" value="${question.q_type}" />
						</td>
						<th width="120"><tomtag:Message key="txt.sys.question.fields.level" /> : </th>
                        <td width="40%">
							<select name="q_level" class="tm_select" style="min-width:200px">
								<option value="1" <c:if test="${question.q_level == '1'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev1" /></option>
								<option value="2" <c:if test="${question.q_level == '2'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev2" /></option>
								<option value="3" <c:if test="${question.q_level == '3'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev3" /></option>
								<option value="4" <c:if test="${question.q_level == '4'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev4" /></option>
								<option value="5" <c:if test="${question.q_level == '5'}">selected</c:if>><tomtag:Message key="txt.other.hard.lev5" /></option>
							</select>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.question.fields.dbname" /> : </th>
                        <td>
							<select name="q_dbid" class="validate[required] tm_select" style="min-width:200px">
								<option value=""><tomtag:Message key="txt.sys.question.fields.dbname" /></option>
								<c:forEach var="qdb" items="${qdbs}">
								<option value="${qdb.d_id}" <c:if test="${question.q_dbid == qdb.d_id}">selected</c:if>>${qdb.d_name}</option>
								</c:forEach>
							</select>
						</td>
						<th><tomtag:Message key="txt.sys.question.fields.from" /> : </th>
                        <td>
							<input type="text" name="q_from" class="tm_txt tm_width200" size="50" maxlength="20" value="${question.q_from}" />
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.question.fields.status" /> : </th>
                        <td>
							<select class="tm_select" name="q_status" style="min-width:200px" onchange="tm_question_status_change(this)">
                                <option value="1" <c:if test="${question.q_status == '1'}">selected</c:if>><tomtag:Message key="txt.other.status.open" /></option>
                                <option value="0" <c:if test="${question.q_status == '0'}">selected</c:if>><tomtag:Message key="txt.other.status.close" /></option>
                            </select>
							<span class="tm_tip" id="tm_question_status_tip"></span>
						</td>
						<th></th>
                        <td></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.question.fields.content" /> : </th>
                        <td colspan="3">
							<textarea name="q_content" rows="5" cols="40">${question.q_content}</textarea>
						</td>
                    </tr>

					<tr>
                        <th id="tm_title"><tomtag:Message key="txt.sys.question.tips.optionsetting" /> : </th>
                        <td colspan="3" id="tm_body"></td>
                    </tr>

					<tr>
                        <th><tomtag:Message key="txt.sys.question.fields.resolve" /> : </th>
                        <td colspan="3">
							<textarea name="q_resolve" rows="5" cols="40" class="tm_txtx">${question.q_resolve}</textarea>
						</td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td colspan="3">
                        	<button class="tm_btn tm_btn_primary" type="submit"><tomtag:Message key="txt.other.operations.submit" /></button>
							<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>
			
			<input type="hidden" name="q_id" value="${question.q_id}" />
			<input type="hidden" name="q_modifyor" value="${sessionScope.SEN_USERNAME}" />
			</form>
        </div>
        
        
    </div>

</body>
</html>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.vote.add" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<link rel="stylesheet" type="text/css" href="skins/default/other.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/wdatepicker/WdatePicker.js" type="text/javascript"></script>

	<link rel="stylesheet" href="inc/js/jquery-validation-engine/css/validationEngine.jquery.css" />
	<script src="inc/js/jquery-validation-engine/js/jquery.validationEngine.js"></script>
	
	<tomtag:Util action="lang" data="zh_CN">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-zh_CN.js"></script>
	</tomtag:Util>

	<tomtag:Util action="lang" data="en">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-en.js"></script>
	</tomtag:Util>
	
	<script type="text/javascript">
		$(document).ready(function() {
			jQuery('#form_vote_add').validationEngine();
		});

		function tm_removeVoteItem(obj){
			var pobj = $(obj).parent();
			if(pobj.is("div")){
				pobj.remove();
			}
		}

		function tm_addVoteItem(obj){
			var pobj = $(obj).parent();
			if(pobj.is("td")){
				var html = [];
				html.push('<div class="tm_vote_option">');
				html.push('<input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_option" /> ');
				html.push('<a href="javascript:void(0);" onclick="javascript:tm_removeVoteItem(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>');
				html.push('</div>');
				pobj.append(html.join(""));
			}
				
		}

		function tm_removeVote(obj){
			var pobj = $(obj).parent();
			if(pobj.is("div")){
				pobj.remove();
			}
		}

		function tm_addVote(obj){
			var html = $("#tm_vote_template").html();
			$(obj).prev().append(html);
		}

	</script>

	<style>
		.tm_vote{margin:10px 0 10px 0; position:relative; width:600px; clear:both;}
		.tm_vote_option{line-height:30px; margin:10px 0;}
		.tm_vote_delete{ position:absolute; right:10px; top:10px}
		.tm_vote_add_option{text-decoration:none; color:#00f;}
		.tm_vote_add_option:hover{color:#f00}
	</style>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.vote.add" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.vote.add" /></h1>
                <span><tomtag:Message key="txt.sys.vote.addtip" /></span>
            </div>
			<div class="tm_navbuttons">
				<a href="system/vote/add.thtml" class="on"><tomtag:Message key="txt.sys.vote.add" /></a>
				<a href="system/vote/list.thtml"><tomtag:Message key="txt.sys.vote.list" /></a>
			</div>
        </div>
        
        <br/>
        <div class="tm_container">
			<form action="system/vote/save.do" method="post" id="form_vote_add">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.vote.fields.title" /> : </th>
                        <td>
							<input type="text" name="v_title" class="validate[required] tm_txt" size="50" maxlength="30" />
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.vote.fields.body" /> : </th>
                        <td>
							<textarea class="tm_txtx" name="v_body" rows="8" cols="51"></textarea>
						</td>
                    </tr>
                    <tr>
                        <th><tomtag:Message key="txt.sys.vote.fields.start" /> : </th>
                        <td><input type="text" name="v_starttime" class="validate[required] tm_txt tm_datepicker tm_width200" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" /></td>
                    </tr>
					<tr>
						<th><tomtag:Message key="txt.sys.vote.fields.end" /> : </th>
                        <td><input type="text" name="v_endtime" class="validate[required] tm_txt tm_datepicker tm_width200" size="50" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" readonly="true" /></td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.vote.fields.status" /> : </th>
                        <td>
							<select class="tm_select" name="v_status" style="min-width:200px;">
                                <option value="1"><tomtag:Message key="txt.other.status.open" /></option>
                                <option value="-1"><tomtag:Message key="txt.other.status.close" /></option>
                            </select>
						</td>
                    </tr>
					<tr>
                        <th><tomtag:Message key="txt.sys.vote.fields.setting" /> : </th>
                        <td id="tm_votes">

							<div class="tm_vote">
								<table border="0" width="100%" class="tm_param_list">
									<tbody>
										<tr>
											<th width="100"><tomtag:Message key="txt.sys.vote.fields.itemtitle" /> : </th>
											<td><input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_title" /></td>
										</tr>
										<tr>
											<th><tomtag:Message key="txt.sys.vote.fields.type" /> : </th>
											<td>
												<label><input type="radio" name="v_item_type" value="1" checked="checked" /><tomtag:Message key="txt.other.questiontype.single" /></label>
												<label><input type="radio" name="v_item_type" value="2" /><tomtag:Message key="txt.other.questiontype.multiple" /></label>
											</td>
										</tr>
										<tr>
											<th><tomtag:Message key="txt.sys.vote.fields.options" /> : </th>
											<td>
												<a href="javascript:void(0);" onclick="javascript:tm_addVoteItem(this);" class="tm_vote_add_option"><img src="skins/images/icos/add.png" align="absmiddle" /> <tomtag:Message key="txt.sys.vote.additem" /></a>
												<div class="tm_vote_option">
													<input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_option" />
												</div>
												<div class="tm_vote_option">
													<input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_option" />
													<a href="javascript:void(0);" onclick="javascript:tm_removeVoteItem(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>
												</div>
												<div class="tm_vote_option">
													<input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_option" />
													<a href="javascript:void(0);" onclick="javascript:tm_removeVoteItem(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>
												</div>
												
											</td>
										</tr>
									</tbody>
								</table>
							</div>

							<button class="tm_btn" type="button" onclick="javascript:tm_addVote(this);" style="margin:10px 0;"><tomtag:Message key="txt.sys.vote.add" /></button>

						</td>
                    </tr>
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<button class="tm_btn tm_btn_primary" type="submit"><tomtag:Message key="txt.other.operations.submit" /></button>
							<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>

			<input type="hidden" name="v_poster" value="${sessionScope.SEN_USERNAME}" />
			<input type="hidden" name="v_modifyor" value="${sessionScope.SEN_USERNAME}" />

			</form>
        </div>
        
        
    </div>



<div style="display:none" id="tm_vote_template">
	<div class="tm_vote">
		<table border="0" width="100%" class="tm_param_list">
			<tbody>
				<tr>
					<th width="100"><tomtag:Message key="txt.sys.vote.fields.itemtitle" /> : </th>
					<td><input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_title" /></td>
				</tr>
				<tr>
					<th><tomtag:Message key="txt.sys.vote.fields.type" /> : </th>
					<td>
						<label><input type="radio" name="v_item_type" value="1" checked="checked" /><tomtag:Message key="txt.other.questiontype.single" /></label>
						<label><input type="radio" name="v_item_type" value="2" /><tomtag:Message key="txt.other.questiontype.multiple" /></label>
					</td>
				</tr>
				<tr>
					<th><tomtag:Message key="txt.sys.vote.fields.options" /> : </th>
					<td>
						<a href="javascript:void(0);" onclick="javascript:tm_addVoteItem(this);" class="tm_vote_add_option"><img src="skins/images/icos/add.png" align="absmiddle" /> <tomtag:Message key="txt.sys.vote.additem" /></a>
						<div class="tm_vote_option">
							<input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_option" />
						</div>
						<div class="tm_vote_option">
							<input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_option" />
							<a href="javascript:void(0);" onclick="javascript:tm_removeVoteItem(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>
						</div>
						<div class="tm_vote_option">
							<input type="text" class="validate[required] tm_txt" size="50" maxlength="50" name="v_item_option" />
							<a href="javascript:void(0);" onclick="javascript:tm_removeVoteItem(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<a href="javascript:void(0);" class="tm_vote_delete" onclick="javascript:tm_removeVote(this);"><img src="skins/images/icos/delete.png" /></a>
	</div>
</div>


</body>
</html>

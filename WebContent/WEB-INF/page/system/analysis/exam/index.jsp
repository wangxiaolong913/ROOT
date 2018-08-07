<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.analysis.exam" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<style>
		.tm_param_list tbody tr th{ text-align:center; font-weight:bold; line-height:25px}
		.tm_param_list caption{line-height:40px; font-size:14px; text-align:left; font-weight:bold; font-family:Tahoma, Geneva, '宋体';}
		.tm_param_list{margin-bottom:10px}
		#tm_tr_result{display:none}
	</style>

	<link rel="stylesheet" href="inc/js/jquery-validation-engine/css/validationEngine.jquery.css" />
	<script src="inc/js/jquery-validation-engine/js/jquery.validationEngine.js"></script>
	
	<tomtag:Util action="lang" data="zh_CN">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-zh_CN.js"></script>
	</tomtag:Util>

	<tomtag:Util action="lang" data="en">
	<script src="inc/js/jquery-validation-engine/js/languages/jquery.validationEngine-en.js"></script>
	</tomtag:Util>

	<script type="text/javascript">
		function tm_showPapers(){
			layer.open({
			  type: 2,
			  title: '<tomtag:Message key="txt.sys.analysis.common.choosepaper" />',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['700px', '480px'],
			  content: 'system/analysis/paperlist.thtml'
			}); 
		}

		function tm_AnalyseExam(){
			var formcheck = $("#form_exam").validationEngine('validate');
			if(!formcheck){
				return;
			}

			var pid = $("#pid").val();
			$.ajax({
				type: "POST",
				url: "${tm_base}system/analysis/exam/analyse.do",
				data: {"pid":pid, "t":Math.random()},
				dataType: "json",
				success: function(ret){
					if("ok" == ret["code"]){
						tm_buildPage(ret["data"]);
					}else{
						alert('<tomtag:Message key="message.other.systembusy" />');
					}
				},
				error:function(){
					alert('<tomtag:Message key="message.other.systembusy" />');
				}
			}); 
		}

		function tm_buildPage(data){
			$("#tm_maxscore").html(data["e_max_score"]);
			$("#tm_maxscore").append(" &nbsp; - &nbsp; " + tm_getUserData("max_score_user", data["userdata"]));

			$("#tm_minscore").html(data["e_min_score"]);
			$("#tm_minscore").append(" &nbsp; - &nbsp; " + tm_getUserData("min_score_user", data["userdata"]));

			$("#tm_avgscore").html(data["e_avg_score"]);

			$("#tm_passed").html(data["e_pass_user_num"]);
			$("#tm_totaluser").html(data["e_total_user_num"]);
			$("#tm_shouldcount").html(data["e_should_user_num"]);

			var v_passrate = eval(data["e_pass_user_num"]/data["e_total_user_num"]) * 100;
			var v_joinrate = eval(data["e_total_user_num"]/data["e_should_user_num"]) * 100;
			var v_absentuser = eval(data["e_should_user_num"] - data["e_total_user_num"]);
			
			$("#tm_passed_rate").html(v_passrate.toFixed(2) + "%");
			$("#tm_join_rate").html(v_joinrate.toFixed(2) + "%");
			$("#tm_absentuser").html("<font color='red'>"+v_absentuser+"</font>");


			if(baseutil.isNull(data["e_first_submit"])){
				$("#tm_first_submit").html("--");
			}else{
				$("#tm_first_submit").html(data["e_first_submit"]);
				$("#tm_first_submit").append(" &nbsp; - &nbsp; " + tm_getUserData("first_submit_user", data["userdata"]));
			}

			if(baseutil.isNull(data["e_last_submit"])){
				$("#tm_last_submit").html("--");
			}else{
				$("#tm_last_submit").html(data["e_last_submit"]);
				$("#tm_last_submit").append(" &nbsp; - &nbsp; " + tm_getUserData("last_submit_user", data["userdata"]));
			}

			if(baseutil.isNull(data["e_max_cost"])){
				$("#tm_maxcost").html("--");
			}else{
				$("#tm_maxcost").html(data["e_max_cost"]);
				$("#tm_maxcost").append(' <tomtag:Message key="txt.other.units.minute" />');
				$("#tm_maxcost").append(" &nbsp; - &nbsp; " + tm_getUserData("max_cost_user", data["userdata"]));
			}

			if(baseutil.isNull(data["e_min_cost"])){
				$("#tm_mincost").html("--");
			}else{
				$("#tm_mincost").html(data["e_min_cost"]);
				$("#tm_mincost").append(' <tomtag:Message key="txt.other.units.minute" />');
				$("#tm_mincost").append(" &nbsp; - &nbsp; " + tm_getUserData("min_cost_user", data["userdata"]));
			}

			$("#tm_tr_result").show();
		}

		
		function tm_getUserData(key , jsonarray){
			var _html = "";
			if(jsonarray){
				$.each(jsonarray, function(idx, item){
					if(key == item["e_dataname"]){
						_html = "" + item["u_username"] + " (" + item["u_realname"] + ")";
					}
				});
			}
			return _html;
		}

	</script>
	
  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.sys.analysis.exam" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.sys.analysis.exam" /></h1>
                <span><tomtag:Message key="txt.sys.analysis.exam.tip" /></span>
            </div>
        </div>
        
        
        <div class="tm_container">
			<form id="form_exam">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody id="tm_data_body">
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.analysis.common.choosepaper" /> : </th>
                        <td>
							<input type="text" id="e_papername" class="validate[required] tm_txt tm_bg_readonly" size="50" maxlength="30" readonly="readonly" />
							<input type="hidden" id="pid" />
							<a href="javascript:void(0);" onclick="tm_showPapers();"><img src="skins/images/icos/search.png" align="absmiddle" /></a>
						</td>
                    </tr>
					<tr id="tm_tr_result">
                        <th><tomtag:Message key='txt.sys.analysis.common.result' /> : </th>
                        <td>
							<table border="0" width="100%" class="tm_param_list tm_result">
								<caption><tomtag:Message key="txt.sys.analysis.exam.fields.title_score" /></caption>
								<colgroup>
									<col width="100"></col>
									<col width="40%"></col>
									<col width="100"></col>
									<col width="40%"></col>
								</colgroup>
								<tbody>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.maxscore" /></th>
										<td id="tm_maxscore"></td>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.minscore" /></th>
										<td id="tm_minscore"></td>
									</tr>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.avgscore" /></th>
										<td id="tm_avgscore"></td>
										<th></th>
										<td></td>
									</tr>
								</tbody>
							</table>

							<table border="0" width="100%" class="tm_param_list tm_result">
								<caption><tomtag:Message key="txt.sys.analysis.exam.fields.title_user" /></caption>
								<colgroup>
									<col width="100"></col>
									<col width="40%"></col>
									<col width="100"></col>
									<col width="40%"></col>
								</colgroup>
								<tbody>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.passed" /></th>
										<td id="tm_passed"></td>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.totaluser" /></th>
										<td id="tm_totaluser"></td>
									</tr>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.passed_rate" /></th>
										<td id="tm_passed_rate"></td>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.join_rate" /></th>
										<td id="tm_join_rate"></td>
									</tr>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.shouldcount" /></th>
										<td id="tm_shouldcount"></td>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.absentuser" /></th>
										<td id="tm_absentuser"></td>
									</tr>
								</tbody>
							</table>

							<table border="0" width="100%" class="tm_param_list tm_result">
								<caption><tomtag:Message key="txt.sys.analysis.exam.fields.title_time" /></caption>
								<colgroup>
									<col width="100"></col>
									<col width="90%"></col>
								</colgroup>
								<tbody>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.first_submit" /></th>
										<td id="tm_first_submit"></td>
									</tr>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.last_submit" /></th>
										<td id="tm_last_submit"></td>
									</tr>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.maxcost" /></th>
										<td id="tm_maxcost"></td>
									</tr>
									<tr>
										<th><tomtag:Message key="txt.sys.analysis.exam.fields.mincost" /></th>
										<td id="tm_mincost"></td>
									</tr>
								</tbody>
							</table>
							
							<p>&nbsp;</p>

						</td>
                    </tr>
                </tbody>
				<tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<button class="tm_btn tm_btn_primary" type="button" onclick="javascript:tm_AnalyseExam();"><tomtag:Message key="txt.other.operations.analyse" /></button>
							<button class="tm_btn" type="button" onclick="javascript:history.go(-1);"><tomtag:Message key="txt.other.operations.cancel" /></button>
                        </td>
                    </tr>
                </tfoot>
            </table>
			</form>
        </div>
        
        
    </div>

</body>
</html>

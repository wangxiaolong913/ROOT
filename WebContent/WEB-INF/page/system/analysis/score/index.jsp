<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.analysis.score" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>
	<script src="inc/js/chart/highcharts.js" type="text/javascript"></script>

	<style>
		.tm_param_list tbody tr th{ text-align:center; font-weight:bold; line-height:25px}
		#tm_tr_result{display:none}
		#tm_score_pie{width:600px}
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

		function tm_AnalyseScore(){
			var formcheck = $("#form_score").validationEngine('validate');
			if(!formcheck){
				return;
			}

			var pid = $("#pid").val();
			$.ajax({
				type: "POST",
				url: "${tm_base}system/analysis/score/analyse.do",
				data: $("#form_score").serialize(),
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
			var chartdata = new Array();
			if(data){
				$.each(data, function(idx, item){
					var _name = item["s_from"] + "-" + item["s_to"] + " (<tomtag:Message key='txt.sys.analysis.score.fields.unit' />"+item["s_total"]+")";
					var _d = {
						name : _name,
						y : item["s_total"]
					};
					chartdata.push(_d);
				});
			}

			$('#tm_score_pie').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false,
					type: 'pie'
				},
				title: {
					text: '<tomtag:Message key="txt.sys.analysis.score.fields.chartitle" />',
					style: {"color":"#000000"}
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: true,
							format: '<b>{point.name}</b> : {point.percentage:.1f} %',
							style: {
								color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
							}
						}
					}
				},
				series: [{
					name: '<tomtag:Message key="txt.sys.analysis.score.fields.percent" />',
					colorByPoint: true,
					data: chartdata
				}]
			});


			$("#tm_tr_result").show();
		}

		function tm_removeRow(obj){
			var pobj = $(obj).parent().parent();
			if(pobj.is("tr")){
				pobj.remove();
			}
		}

		function tm_addRow(){
			var html = [];
			html.push('<tr>');
			html.push('	<td>');
			html.push('	<tomtag:Message key="txt.sys.analysis.score.fields.from" />');
			html.push('	<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_from" value="0" />');
			html.push('	<tomtag:Message key="txt.sys.analysis.score.fields.to" />');
			html.push('	<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_to" value="100" />');
			html.push('	<a href="javascript:void(0);" onclick="javascript:tm_removeRow(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>');
			html.push('	</td>');
			html.push('</tr>');

			$("#tm_rows").append(html.join(""));
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
				<h1><tomtag:Message key="txt.sys.analysis.score" /></h1>
                <span><tomtag:Message key="txt.sys.analysis.score.tip" /></span>
            </div>
        </div>
        
        
        <div class="tm_container">
			<form id="form_score">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody id="tm_data_body">
                    <tr>
                        <th width="120"><tomtag:Message key="txt.sys.analysis.common.choosepaper" /> : </th>
                        <td>
							<input type="text" id="e_papername" class="validate[required] tm_txt tm_bg_readonly" size="50" maxlength="30" readonly="readonly" />
							<input type="hidden" id="pid" name="pid" />
							<a href="javascript:void(0);" onclick="tm_showPapers();"><img src="skins/images/icos/search.png" align="absmiddle" /></a>
						</td>
                    </tr>
					<tr id="tm_tr_setting">
						<th><tomtag:Message key="txt.sys.analysis.score.fields.settings" /> : </th>
						<td>
							<table border="0" width="100%" class="tm_param_list">
								<tbody id="tm_rows">
									<tr>
										<td>
											<tomtag:Message key="txt.sys.analysis.score.fields.from" />
											<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_from" value="0" />
											<tomtag:Message key="txt.sys.analysis.score.fields.to" />
											<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_to" value="59" />
										</td>
									</tr>
									<tr>
										<td>
											<tomtag:Message key="txt.sys.analysis.score.fields.from" />
											<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_from" value="60" />
											<tomtag:Message key="txt.sys.analysis.score.fields.to" />
											<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_to" value="79" />
											<a href="javascript:void(0);" onclick="javascript:tm_removeRow(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>
										</td>
									</tr>
									<tr>
										<td>
											<tomtag:Message key="txt.sys.analysis.score.fields.from" />
											<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_from" value="80" />
											<tomtag:Message key="txt.sys.analysis.score.fields.to" />
											<input type="text" class="validate[required,custom[integer],min[0]] tm_txt" size="3" name="s_to" value="100" />
											<a href="javascript:void(0);" onclick="javascript:tm_removeRow(this);"><img src="skins/images/icos/delete.png" align="absmiddle" /></a>
										</td>
									</tr>
								</tbody>
							</table>

							<button class="tm_btn" type="button" onclick="javascript:tm_addRow();" style="margin:10px 0;"><tomtag:Message key="txt.sys.analysis.score.addcondition" /></button>
						</td>
                    </tr>
					<tr id="tm_tr_result">
						<th>
							
						</th>
						<td>
							<div id="tm_score_pie"></div>
						</td>
                    </tr>
                </tbody>
				<tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<button class="tm_btn tm_btn_primary" type="button" onclick="javascript:tm_AnalyseScore();"><tomtag:Message key="txt.other.operations.analyse" /></button>
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

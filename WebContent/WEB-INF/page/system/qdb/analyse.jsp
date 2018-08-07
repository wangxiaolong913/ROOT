<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.qdb.analyse" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>
	<script src="inc/js/chart/highcharts.js" type="text/javascript"></script>

	<style>
		.tm_chart_message{width:100%; margin:50px auto; padding:20px 0; line-height:2em; border:solid 1px #f0f0f0; text-align:center; background:#f5f5f5}
		#tm_chart_container{width:500px; height:250px; margin:40px auto 0 auto;}
	</style>

	
	<script type="text/javascript">

		function tm_buildPage(data){
			var chartdata = new Array();
			if(data){
				$.each(data, function(idx, item){
					var _d = {
						name : getQuestionTypeName(item["q_type"]) + item["q_nums"] + "题",
						y : item["q_nums"]
					};
					chartdata.push(_d);
				});
			}

			$('#tm_chart_container').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false,
					type: 'pie'
				},
				title: {
					text: '',
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


		}

		function getQuestionTypeName(type_id){
			if("1" == type_id){
				return '<tomtag:Util action="getQuestionType" data="1"></tomtag:Util>';
			}else if("2" == type_id){
				return '<tomtag:Util action="getQuestionType" data="2"></tomtag:Util>';
			}else if("3" == type_id){
				return '<tomtag:Util action="getQuestionType" data="3"></tomtag:Util>';
			}else if("4" == type_id){
				return '<tomtag:Util action="getQuestionType" data="4"></tomtag:Util>';
			}else if("5" == type_id){
				return '<tomtag:Util action="getQuestionType" data="5"></tomtag:Util>';
			}
			return "UNKNOWN";
		}

		
	</script>
	
  </head>
  
<body>

	<div class="tm_main">
		
		<c:choose>
			<c:when test="${empty qdb}">
				<div class="tm_chart_message">题库不存在<br/>Data Does Not Exist.</div>
			</c:when>
			<c:otherwise>
				<div class="tm_container">
					<table width="100%" cellpadding="5" border="0" class="tm_table_form">
						<tr>
							<th style="width:100px"><b><tomtag:Message key="txt.sys.qdb.fields.name" /></b></th>
							<td>${qdb.d_name}</td>
						</tr>
						<tr>
							<th><b><tomtag:Message key="txt.sys.qdb.fields.status" /></b></th>
							<td>
								<c:choose>
									<c:when test="${qdb.d_status == 0}">
									   <font color="red"><tomtag:Message key="txt.other.status.close" /></font>
									</c:when>
									<c:when test="${qdb.d_status == 1}">
									   <tomtag:Message key="txt.other.status.open" />
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th><b><tomtag:Message key="txt.sys.qdb.fields.nums" /></b></th>
							<td>${param.qnums}</td>
						</tr>
					</table>
				</div>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${empty info_question}">
				<div class="tm_chart_message">题库无数据<br/>No Statistical Data.</div>
			</c:when>
			<c:otherwise>
				<div class="tm_container">
					<div id="tm_chart_container"></div>
				</div>
				<script>
					var info_question = eval('${info_question}');
					tm_buildPage(info_question);
					</script>
			</c:otherwise>
		</c:choose>


        
        
    </div>

	


</body>
</html>

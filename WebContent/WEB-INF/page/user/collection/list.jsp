<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

    <style>
    	.tm_collection{margin: 10px 10px; position: relative;}
    	.tm_collection p{ text-align:left; margin: 0 0; padding: 0 40px 0 0; white-space:normal; }
    	.tm_collection h1{ text-align:left; margin: 0 0; padding: 0 40px 0 0; font-weight: normal; font-size: 12px;}
    	.tm_collection h2{ text-align:left; margin: 0 0; padding: 0 40px 0 0; font-weight: normal; font-size: 12px;}
    	.tm_collection h5{ text-align:left; margin: 0 0; padding: 0 0; font-weight: normal; font-size: 12px; position: absolute; top: 0; right: 5px}
    	.tm_collection h5 a{display: block; width: 16px; height: 16px; background: url('skins/images/icos/delete.png') no-repeat;}
		.tm_collection hr{height:1px;border:none;border-top:1px dashed #eee;}
		.tm_collection div{text-align:left; color:#aaa; line-height:20px;}

		.tm_collection ul{margin:5px 0; padding:0; list-style:none; text-align:left}
		.tm_collection ul li{}
		.tm_collection ul li p{display:inline}

    </style>

	<script type="text/javascript">

		$(document).ready(function() {

		});

		var tmCollection = {
			doDelCollection : function(cid){
				var _confirm =  window.confirm('<tomtag:Message key="message.other.reallydelete" />');
				if(_confirm){
					$.ajax({
						type: "POST",
						url: "${tm_base}user/collection/delete.do",
						data: {"id":cid, "t":Math.random()},
						dataType: "json",
						success: function(ret){
							if(ret["code"] == "ok"){
								window.location.reload();
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


			doClearCollection : function(cid){
				var _confirm =  window.confirm('<tomtag:Message key="message.other.reallydelete" />');
				if(_confirm){
					$.ajax({
						type: "POST",
						url: "${tm_base}user/collection/clear.do",
						data: {"t":Math.random()},
						dataType: "json",
						success: function(ret){
							if(ret["code"] == "ok"){
								window.location.reload();
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

			showTypes : function(){
				layer.open({
				  type: 2,
				  title: '<tomtag:Message key="txt.user.collection.type" />',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['650px', '480px'],
				  content: 'user/collection/types.thtml'
				}); 
			}
		};

	</script>

  </head>
  
<body>

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.user.collection" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.collection" /></h1>
                <span><tomtag:Message key="txt.user.collection.listtip" /></span>
            </div>
        </div>

		<div class="tm_container">
        	<div class="tm_navbuttons">
				<a href="javascript:void(0);"onclick="tmCollection.doClearCollection();"><tomtag:Message key="txt.user.collection.clear" /></a>
				<a href="javascript:void(0);"onclick="tmCollection.showTypes();"><tomtag:Message key="txt.user.collection.type" /></a>
			</div>
        </div>
        
        <div class="tm_container">
        	<table width="100%" cellpadding="0" border="0">
        		<tr>
        			<td valign="top">
						
						<form action="user/collection/list.thtml" method="get">
						<div style="padding:8px; border:solid 1px #eee;">
							<tomtag:Message key="txt.user.collection.type" /> :
							<select class="tm_selects" name="c_tid">
								<option value=""><tomtag:Message key="txt.other.operations.all" /></option>
								<c:forEach var="type" items="${types}">
								<option value="${type.t_id}" <c:if test="${param.c_tid == type.t_id}">selected</c:if>>${type.t_name}</option>
								</c:forEach>
							</select> &nbsp;
							<button class="tm_btns" type="submit"><tomtag:Message key="txt.other.operations.search" /></button>
						</div>

        				<table width="100%" border="0" class="tm_table_list">
			                <tbody>
								<c:forEach var="collection" items="${page.dataList}">
								<tr>
									<td>
										<div class="tm_collection">
											<c:choose>
												<c:when test="${empty collection.q_data}">
													<p><s><tomtag:Message key="message.other.question_deleted" /></s></p>
												</c:when>
												<c:otherwise>
													<p>${collection.q_content}</p>
												</c:otherwise> 
											</c:choose>
											
											<c:choose>
												<c:when test="${collection.q_type == '1' || collection.q_type == '2'}">
													<ul>
														<tomtag:Util action="formatChooseOptions" data="${collection.q_data}"></tomtag:Util>
													</ul>
													<h1><b><tomtag:Message key="txt.user.history.fields.standkey" /> : </b>${fn:replace(fn:replace(collection.q_key, '`', ''),',','')}</h1>
													<h2><b><tomtag:Message key="txt.user.history.fields.userkey" /> : </b>${fn:replace(fn:replace(collection.c_userkey, '`', ''),',','')}</h2>
												</c:when>
												<c:when test="${collection.q_type == '3'}">
													<h1><b><tomtag:Message key="txt.user.history.fields.standkey" /> : </b><tomtag:Util action="formatJudgmentAnswer" data="${collection.q_key}"></tomtag:Util></h1>
													<h2><b><tomtag:Message key="txt.user.history.fields.userkey" /> : </b><tomtag:Util action="formatJudgmentAnswer" data="${collection.c_userkey}"></tomtag:Util></h2>
												</c:when>
												<c:when test="${collection.q_type == '4'}">
													<h1><b><tomtag:Message key="txt.user.history.fields.standkey" /> : </b>${fn:replace(fn:replace(collection.q_key, '`', ' '),',',' ')}</h1>
													<h2><b><tomtag:Message key="txt.user.history.fields.userkey" /> : </b>${fn:replace(fn:replace(collection.c_userkey, '`', ' '),',',' ')}</h2>
												</c:when>
												<c:otherwise>
													<h1><b><tomtag:Message key="txt.user.history.fields.standkey" /> : </b>${collection.q_key}</h1>
													<h2><b><tomtag:Message key="txt.user.history.fields.userkey" /> : </b>${collection.c_userkey}</h2>
												</c:otherwise> 
											</c:choose>
											
											<h5><a href="javascript:void(0);" onclick="tmCollection.doDelCollection('${collection.c_id}')"></a></h5>
											<hr/>
											<div>${collection.c_creatdate}</div>
											<div>${collection.c_remark}</div>
										</div>
									</td>
								</tr>
								</c:forEach>
							</tbody>
			            </table>
						</form>

						<table width="100%" cellpadding="10" border="0" class="tm_table_list">
							<tfoot>
								<tr>
									<td>
										${foot}
									</td>
								</tr>
							</tfoot>
						</table>

        			</td>
        		</tr>
        	</table>
        </div>
        
        
    </div>

</body>
</html>

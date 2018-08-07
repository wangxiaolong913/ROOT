<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.sys.course.users" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<script type="text/javascript">
		window.onload = function(){
			$(".tm_table_list tbody tr").mouseover(function(){
				$(this).attr("style","background:#f5f5f5");
			});
			$(".tm_table_list tbody tr").mouseout(function(){
				$(this).attr("style","background:#ffffff");
			});
		}

		function doConfirmClearHistory(cid){
			var v = window.confirm('<tomtag:Message key="txt.sys.course.history.clear.confirm" />');
			if(v){
				doClearHistory(cid);
			}
		}

		function doClearHistory(cid){
			layer.load();
			$.ajax({
				type: "POST",
				url: "${tm_base}system/course/clear_learnrecord.do",
				data: {"cid":cid, "t":Math.random()},
				dataType: "json",
				success: function(ret){
					layer.closeAll('loading');
					if("ok" == ret["code"]){
						alert(ret["msg"]);
						window.location.reload();
					}else{
						alert(ret["msg"]);
					}
				},
				error:function(){
					layer.closeAll('loading');
					alert('<tomtag:Message key="message.other.systembusy" />');
				}
			}); 
		}
	</script>

  </head>
  
<body>

	<div class="tm_main">

        <div class="tm_container">
    	
			<div style="margin:0 0 20px 0">
				<table border="0" width="100%">
					<tr>
						<td style="text-align:left">【${course.c_name}】<tomtag:Message key="txt.sys.course.users" /></td>
						<td style="text-align:right">
							<button class="tm_btn" type="button" onclick="javascript:doConfirmClearHistory('${course.c_id}');"><tomtag:Message key="txt.sys.course.history.clear" /></button>
						</td>
					</tr>
				</table>
			</div>

        	<table width="100%" cellpadding="10" border="0" class="tm_table_list">
            	<thead>
                	<tr>
                        <th><tomtag:Message key="txt.sys.course.history.username" /></th>
                        <th><tomtag:Message key="txt.sys.course.history.branch" /></th>
                    	<th><tomtag:Message key="txt.sys.course.history.learndate" /></th>
                    </tr>
                </thead>
                <tbody>
					<c:forEach var="user" items="${page.dataList}">
                	<tr>
                    	<td>${user.u_username}(${user.u_realname})</td>
						<td>${user.b_name}</td>
						<td>${user.p_startdate}</td>
                    </tr>
					</c:forEach>
                </tbody>
            </table>

			<table width="100%" cellpadding="10" border="0" class="tm_table_list">
			    <tfoot>
                	<tr>
                    	<td>${foot}</td>
                    </tr>
                </tfoot>
            </table>

        </div>
        
        
    </div>

</body>
</html>

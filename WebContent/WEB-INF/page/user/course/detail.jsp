<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tomexam.com/taglib/tomtag" prefix="tomtag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${tm_base}" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tomtag:Message key="txt.user.learn.detail" /></title>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="skins/default/style.css" />
	<script src="inc/js/jquery.js" type="text/javascript"></script>
	<script src="inc/js/swfobject2.js" type="text/javascript"></script>
	<script src="inc/js/baseutil.js" type="text/javascript"></script>
	<script src="inc/js/jquery.media.js" type="text/javascript"></script>
	<script src="inc/js/layer/layer.js" type="text/javascript"></script>

	<script type="text/javascript">
		var var_courseid = "${course.id}";
		var var_player_height = $(window).height() - 210;
		var arrLessons = {};

		String.prototype.endWith=function(str){  
            if(str == null || str=="" || this.length==0 || str.length>this.length)  
              return false;  
            if(this.substring(this.length-str.length)==str)  
              return true;  
            else  
              return false;  
            return true;  
        }  

		String.prototype.startWith=function(str){  
            if(str==null || str=="" || this.length==0 || str.length>this.length)  
              return false;  
            if(this.substr(0,str.length)==str)  
              return true;  
            else  
              return false;  
            return true;  
        }  

		/**
		 * 0文件不存在
		 * 1文件是内网
		 * 2文件是外网
		 **/
		function getFileNetwork(file){
			if(baseutil.isNull(file)){
				return 0;
			}else{
				if(file.startWith("http")){
					return 2;
				}else{
					return 1;
				}
			}
		}

		function tm_loadlesson(chapterid, lessonid, obj){
			var _key = chapterid + "-" + lessonid;

			filetype = arrLessons[_key]["filetype"];
			filepath = arrLessons[_key]["filepath"];
			//alert(filetype);
			//alert(filepath);

			var file_network_type = getFileNetwork(filepath);

			if(file_network_type == 0){
				alert("课件不存在\n CourseWare Not Exsit!!!");
				return;
			}
			
			if("code" != filetype && file_network_type == 1){
				filepath = "${tm_base}" + filepath;
			}
			
			if("av" == filetype){
				$(".tm_media").attr("href", "inc/jwplayer/player.swf?file=" + filepath);
				$('.tm_media').media({ width:'100%', height:var_player_height, autoplay: true });

			}else if("swf" == filetype){
				$(".tm_media").attr("href", filepath);
				$('.tm_media').media({ width:'100%', height:var_player_height, autoplay: true });

			}else if("pdf" == filetype){
				$(".tm_media").attr("href", filepath);
				$('.tm_media').media({ width:'100%', height:var_player_height, autoplay: true });

			}else if("code" == filetype){
				$('.tm_media').html(filepath);
			}

			$(".tm_lessons li a").removeClass("tm_lesson_active");
			$(obj).addClass("tm_lesson_active");
			tm_courseMarker(chapterid, lessonid);
		}

		function tm_courseMarker(chapterid, lessonid){
			$.ajax({
				type: "POST",
				url: "${tm_base}user/course/coursemarker.do",
				data: {"id":var_courseid, "chapterid":chapterid, "lessonid":lessonid, "t":Math.random()},
				dataType: "json",
				success: function(ret){
					
				},
				error:function(){
					alert('<tomtag:Message key="message.other.systembusy" />');
					window.location.reload();
				}
			}); 
		}


		$(document).ready(function() {
			$(".tm_lessons li:first-child a").click();
		});

		document.oncontextmenu= function(){return false;}
	
		function tm_showTeacherInfo(){
			layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 1,
			  shadeClose: true,
			  skin: 'layui-layer-rim',
			  area: '400px', //宽高
			  content: $("#tm_teacherinfo").html()
			});
		}



	</script>

	<style>
		.tm_course_list{}
		.tm_course_list h1{font-size:18px; font-weight:bold; font-family:Tahoma, Geneva, 'Microsoft Yahei';}
		.tm_course_list h3{font-size:12px; font-weight:normal; text-align:center; padding:0 15px; text-indent:2em; color:#000}
		.tm_course_list h2{font-size:12px; font-weight:normal; text-align:left; padding:0 15px; text-indent:2em; color:#aaa}
		.tm_course_list ul{list-style:none; margin:10px 10px 30px 10px; padding:0}
		.tm_course_list ul li{margin:5px 0 0 0; float:left; width:100%; position:relative}
		.tm_course_list ul li a{display:block; background:#eee; height:50px; line-height:25px; text-decoration:none; border:solid 1px #eee }
		.tm_course_list ul li a:hover{background:#fff}
		.tm_course_list ul li a.tm_lesson_active{background:#fff}
		.tm_course_list ul li a h1{font-size:14px; margin:0; padding:0; text-align:left; padding: 0 10px;}
		.tm_course_list ul li a p{clear:both; text-align:left;margin:0; padding:0; color:#aaa; padding: 0 10px;}
		.tm_course_list ul li a em{font-style:normal; position:absolute; right:10px; top:15px}
		.tm_course_list p{clear:both}

		.tm_course_main{height:500px; background:#eee;}
		.tm_course_main_player{border:solid 1px #eee; margin:10px;}
		
	</style>

  </head>
  
<body oncontextmenu="return false">

	<div class="tm_main">
    	
		<div class="tm_container">
			<ul class="tm_breadcrumb">
				<li><a href="common/welcome.thtml"><tomtag:Message key="txt.other.navhome" /></a> <span class="divider">&gt;</span></li>
				<li><a href="user/course/list.thtml"><tomtag:Message key="txt.user.learn.list" /></a> <span class="divider">&gt;</span></li>
				<li class="active"><tomtag:Message key="txt.user.learn.detail" /></li>
			</ul>
		</div>
        
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1><tomtag:Message key="txt.user.learn.detail" /></h1>
                <span><tomtag:Message key="txt.user.learn.detail.detailtip" /></span>
            </div>
        </div>
        
        <div class="tm_container">
			
			<table width="100%" cellpadding="10" border="0" class="tm_table_list">
				<tbody>
					<tr>
						<!-- list -->
						<td width="500" valign="top" class="tm_course_list">
							<h1>${course.name}</h1>
							<h3>
								<tomtag:Message key="txt.sys.course.fields.teacher" /> : 
								<a href="javascript:void(0);" onclick="javascript:tm_showTeacherInfo();">${teacher.t_name}</a>
								&nbsp; &nbsp; 
								<tomtag:Message key="txt.sys.course.fields.stars" /> :
								<img src="skins/images/stars/star_${course.stars}.png" align="absmiddle" />
							</h3>
							<h2>${course.introduce}</h2>
							<ul class="tm_lessons">
								<c:forEach var="chapter" items="${course.chapters}">
									<c:forEach var="lesson" items="${chapter.lessons}">
									<li><a href="javascript:void(0);" onclick="javascript:tm_loadlesson('${chapter.id}','${lesson.id}', this)">
											<h1>${lesson.name}</h1>
											<p>${lesson.remark}</p>
											<em><tomtag:Message key="txt.user.learn.detail.study" /></em>
										</a>
									</li>
									<script>
										arrLessons["${chapter.id}-${lesson.id}"] = {
												"filetype" : "${lesson.filetype}",
												"filepath" : "${lesson.filepath}"
										};
									</script>
									</c:forEach>
								</c:forEach>
							</ul>
							<p>&nbsp;</p>
						</td>

						<!-- main -->
						<td valign="top" class="tm_course_main">
							<div class="tm_course_main_player"><a class="tm_media" href="#"></a></div>
						</td>
					</tr>
				</tbody>
			</table>

        </div>
        
        
    </div>



	<div id="tm_teacherinfo" style="display:none">
		<table width="100%" cellpadding="5" border="1" class="tm_table_form" style="margin:10px; width:380px">
			<tbody>
			<tr>
				<th width="100"><tomtag:Message key="txt.sys.course.teacher.fields.name" /> : </th>
				<td>${teacher.t_name}</td>
				<td rowspan="3" width="100">
					<c:choose>
					<c:when test="${not empty teacher.t_photo}">
						<img src="${tm_base}${teacher.t_photo}" style="width:100px" />
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th><tomtag:Message key="txt.sys.course.teacher.fields.phone" /> : </th>
				<td>${teacher.t_phone}</td>
			</tr>
			<tr>
				<th><tomtag:Message key="txt.sys.course.teacher.fields.email" /> : </th>
				<td>${teacher.t_email}</td>
			</tr>
			<tr>
				<th><tomtag:Message key="txt.sys.course.teacher.fields.info" /> : </th>
				<td colspan="2" height="200">${teacher.t_info}</td>
			</tr>
			</tbody>
		</table>
	</div>


</body>
</html>


package com.tom.system.service.imp;

import com.tom.model.ModelHelper;
import com.tom.model.course.Chapter;
import com.tom.model.course.Course;
import com.tom.model.course.Lesson;
import com.tom.model.system.Pagination;
import com.tom.system.dao.ICourseDao;
import com.tom.system.service.ICourseService;
import com.tom.util.BaseUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CourseService")
public class CourseServiceImp
  implements ICourseService
{
  private static final Logger logger = Logger.getLogger(CourseServiceImp.class);
  @Autowired
  private ICourseDao dao;
  
  public int addCourse(HttpServletRequest request)
  {
    try
    {
      String courseid = BaseUtil.generateId();
      Map<String, Object> course = getRequestCourse(request);
      course.put("c_id", courseid);
      course.put("c_data", buildCourse(request, courseid));
      return this.dao.addCourse(course);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteCourse(String courseid)
  {
    try
    {
      int users = this.dao.getUsers(courseid);
      if (users > 0) {
        return 2;
      }
      return this.dao.deleteCourse(courseid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getCourse(String courseid)
  {
    try
    {
      return this.dao.getCourse(courseid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination queryCourse(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryCourse(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int updateCourse(HttpServletRequest request)
  {
    try
    {
      String courseid = request.getParameter("c_id");
      Map<String, Object> course = getRequestCourse(request);
      course.put("c_id", courseid);
      course.put("c_data", buildCourse(request, courseid));
      return this.dao.updateCourse(course);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  private Map<String, Object> getRequestCourse(HttpServletRequest request)
  {
    Map<String, Object> course = new HashMap();
    course.put("c_name", request.getParameter("c_name"));
    course.put("c_logo", request.getParameter("c_logo"));
    course.put("c_tid", request.getParameter("c_tid"));
    course.put("c_caid", request.getParameter("c_caid"));
    course.put("c_score", request.getParameter("c_score"));
    course.put("c_type", request.getParameter("c_type"));
    course.put("c_introduce", request.getParameter("c_introduce"));
    course.put("c_status", request.getParameter("c_status"));
    course.put("c_stars", request.getParameter("c_stars"));
    course.put("c_poster", request.getParameter("c_poster"));
    course.put("c_modifier", request.getParameter("c_modifier"));
    return course;
  }
  
  private String buildCourse(HttpServletRequest request, String courseid)
  {
    Course course = new Course();
    try
    {
      course.setId(courseid);
      course.setName(request.getParameter("c_name"));
      course.setLogo(request.getParameter("c_logo"));
      course.setScore(BaseUtil.getInt(request.getParameter("c_score")));
      course.setType(BaseUtil.getInt(request.getParameter("c_type")));
      course.setIntroduce(request.getParameter("c_introduce"));
      course.setPostdate(new Date());
      course.setStatus(BaseUtil.getInt(request.getParameter("c_status")));
      course.setStars(BaseUtil.getInt(request.getParameter("c_stars")));
      
      String[] c_chapter_ids = request.getParameterValues("c_chapter_ids");
      String[] c_chapter_names = request.getParameterValues("c_chapter_names");
      String[] c_chapter_remarks = request.getParameterValues("c_chapter_remarks");
      if ((c_chapter_ids != null) && (c_chapter_ids.length > 0)) {
        for (int i = 0; i < c_chapter_ids.length; i++)
        {
          String chapter_id = c_chapter_ids[i];
          String chapter_name = c_chapter_names[i];
          String chapter_remark = c_chapter_remarks[i];
          
          Chapter chapter = new Chapter(chapter_id, chapter_name, chapter_remark);
          
          String[] c_lesson_ids = request.getParameterValues("c_lesson_id");
          String[] c_lesson_names = request.getParameterValues("c_lesson_name");
          String[] c_lesson_remarks = request.getParameterValues("c_lesson_remark");
          String[] c_lesson_filetype = request.getParameterValues("c_lesson_filetype");
          String[] c_lesson_filepath = request.getParameterValues("c_lesson_filepath");
          String[] c_lesson_minutes = request.getParameterValues("c_lesson_minutes");
          if ((c_lesson_names != null) && (c_lesson_names.length > 0)) {
            for (int j = 0; j < c_lesson_names.length; j++)
            {
              String lesson_id = c_lesson_ids[j];
              String lesson_name = c_lesson_names[j];
              String lesson_remark = c_lesson_remarks[j];
              String lesson_filetype = c_lesson_filetype[j];
              String lesson_filepath = c_lesson_filepath[j];
              int lesson_minute = BaseUtil.getInt(c_lesson_minutes[j]);
              if (BaseUtil.isNotEmpty(lesson_filepath)) {
                lesson_filepath = lesson_filepath.replace("\"", "'");
              }
              Lesson lesson = new Lesson(lesson_id, lesson_name, lesson_remark, lesson_filetype, lesson_filepath, lesson_minute);
              chapter.addLesson(lesson);
            }
          }
          course.addChapter(chapter);
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    String ret = ModelHelper.formatObject(course);
    return ret;
  }
  
  public Pagination queryCourseLearnRecord(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryCourseLearnRecord(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int clearLearnRecord(String cid)
  {
    try
    {
      return this.dao.clearLearnRecord(cid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return -1;
  }
}

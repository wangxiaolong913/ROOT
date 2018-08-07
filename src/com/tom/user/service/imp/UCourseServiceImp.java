package com.tom.user.service.imp;

import com.tom.model.ModelHelper;
import com.tom.model.course.Course;
import com.tom.model.system.Pagination;
import com.tom.user.dao.IUCourseDao;
import com.tom.user.service.IUCourseService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UCourseService")
public class UCourseServiceImp
  implements IUCourseService
{
  @Autowired
  private IUCourseDao dao;
  private static final Logger logger = Logger.getLogger(UCourseServiceImp.class);
  
  public Course getCourse(String courseid)
  {
    try
    {
      Map<String, Object> map = this.dao.getCourse(courseid);
      if (map != null)
      {
        String xml = String.valueOf(map.get("c_data"));
        Course course = (Course)ModelHelper.convertObject(xml);
        
        course.setTeacherid(String.valueOf(map.get("c_tid")));
        return course;
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
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
  
  public Pagination myCourseStudyHistory(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.myCourseStudyHistory(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int markCourseProgress(String uid, String courseid, String chapterid, String lessonid)
  {
    try
    {
      return this.dao.markCourseProgress(uid, courseid, chapterid, lessonid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public List<Map<String, Object>> getCourseProgress(String uid, String courseid)
  {
    try
    {
      return this.dao.getCourseProgress(uid, courseid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public int postComment(Map<String, Object> comment)
  {
    try
    {
      comment.put("c_id", BaseUtil.generateId());
      return this.dao.postComment(comment);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteComment(String uid, String commentid)
  {
    try
    {
      return this.dao.deleteComment(uid, commentid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Pagination queryComment(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryComment(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int postQuestion(Map<String, Object> question)
  {
    try
    {
      question.put("q_id", BaseUtil.generateId());
      return this.dao.postQuestion(question);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteQuestion(String uid, String questionid)
  {
    try
    {
      return this.dao.deleteQuestion(uid, questionid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Pagination queryQuestion(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryQuestion(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int addNote(Map<String, Object> note)
  {
    try
    {
      note.put("n_id", BaseUtil.generateId());
      return this.dao.addNote(note);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteNote(String uid, String noteid)
  {
    try
    {
      return this.dao.deleteNote(uid, noteid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Pagination queryNote(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryNote(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int addFavorite(String uid, String courseid)
  {
    try
    {
      return this.dao.addFavorite(uid, courseid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteFavorite(String uid, String favid)
  {
    try
    {
      return this.dao.deleteFavorite(uid, favid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Pagination queryFavorite(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryFavorite(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public List<Map<String, Object>> getAllCategories()
  {
    try
    {
      return this.dao.getAllCategories();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllTeachers()
  {
    try
    {
      return this.dao.getAllTeachers();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> getTeacher(String teacherid)
  {
    try
    {
      return this.dao.getTeacher(teacherid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
}

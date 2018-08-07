package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ICourseTeacherDao;
import com.tom.system.service.ICourseTeacherService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CourseTeacherService")
public class CourseTeacherServiceImp
  implements ICourseTeacherService
{
  private static final Logger logger = Logger.getLogger(CourseTeacherServiceImp.class);
  @Autowired
  private ICourseTeacherDao dao;
  
  public int addTeacher(Map<String, Object> teacher)
  {
    try
    {
      teacher.put("t_id", BaseUtil.generateId());
      return this.dao.addTeacher(teacher);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteTeacher(String teacherid)
  {
    try
    {
      int users = this.dao.getCourses(teacherid);
      if (users > 0) {
        return 2;
      }
      return this.dao.deleteTeacher(teacherid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
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
  
  public Pagination queryTeacher(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryTeacher(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int updateTeacher(Map<String, Object> teacher)
  {
    try
    {
      return this.dao.updateTeacher(teacher);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
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
}

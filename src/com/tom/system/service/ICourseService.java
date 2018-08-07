package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ICourseService
{
  public abstract int addCourse(HttpServletRequest paramHttpServletRequest);
  
  public abstract int deleteCourse(String paramString);
  
  public abstract int updateCourse(HttpServletRequest paramHttpServletRequest);
  
  public abstract Map<String, Object> getCourse(String paramString);
  
  public abstract Pagination queryCourse(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Pagination queryCourseLearnRecord(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int clearLearnRecord(String paramString);
}

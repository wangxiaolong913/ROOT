package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface ICourseDao
{
  public abstract int addCourse(Map<String, Object> paramMap);
  
  public abstract int deleteCourse(String paramString);
  
  public abstract int updateCourse(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getCourse(String paramString);
  
  public abstract Pagination queryCourse(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getUsers(String paramString);
  
  public abstract Pagination queryCourseLearnRecord(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int clearLearnRecord(String paramString);
}

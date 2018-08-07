package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface ICourseTeacherDao
{
  public abstract int addTeacher(Map<String, Object> paramMap);
  
  public abstract int deleteTeacher(String paramString);
  
  public abstract int updateTeacher(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getTeacher(String paramString);
  
  public abstract Pagination queryTeacher(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getCourses(String paramString);
  
  public abstract List<Map<String, Object>> getAllTeachers();
}

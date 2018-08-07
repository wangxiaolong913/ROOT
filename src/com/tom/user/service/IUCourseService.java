package com.tom.user.service;

import com.tom.model.course.Course;
import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IUCourseService
{
  public abstract Course getCourse(String paramString);
  
  public abstract Pagination queryCourse(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Pagination myCourseStudyHistory(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int markCourseProgress(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract List<Map<String, Object>> getCourseProgress(String paramString1, String paramString2);
  
  public abstract int postComment(Map<String, Object> paramMap);
  
  public abstract int deleteComment(String paramString1, String paramString2);
  
  public abstract Pagination queryComment(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int postQuestion(Map<String, Object> paramMap);
  
  public abstract int deleteQuestion(String paramString1, String paramString2);
  
  public abstract Pagination queryQuestion(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int addNote(Map<String, Object> paramMap);
  
  public abstract int deleteNote(String paramString1, String paramString2);
  
  public abstract Pagination queryNote(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int addFavorite(String paramString1, String paramString2);
  
  public abstract int deleteFavorite(String paramString1, String paramString2);
  
  public abstract Pagination queryFavorite(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<Map<String, Object>> getAllCategories();
  
  public abstract List<Map<String, Object>> getAllTeachers();
  
  public abstract Map<String, Object> getTeacher(String paramString);
}

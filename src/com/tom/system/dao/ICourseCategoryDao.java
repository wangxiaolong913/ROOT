package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface ICourseCategoryDao
{
  public abstract int addCategory(Map<String, Object> paramMap);
  
  public abstract int deleteCategory(String paramString);
  
  public abstract int updateCategory(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getCategory(String paramString);
  
  public abstract Pagination queryCategory(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getCourses(String paramString);
  
  public abstract List<Map<String, Object>> getAllCategories();
}

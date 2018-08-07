package com.tom.user.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface ICommonDao
{
  public abstract Map<String, Object> getUserByUserName(String paramString1, String paramString2);
  
  public abstract int updateUserLastLogin(String paramString1, String paramString2);
  
  public abstract Map<String, Object> getNewsCategory(String paramString);
  
  public abstract List<Map<String, Object>> getNewsCategories();
  
  public abstract Map<String, Object> getNews(String paramString);
  
  public abstract Pagination queryNews(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> StatisticUsers();
  
  public abstract Map<String, Object> StatisticQdbs();
  
  public abstract Map<String, Object> StatisticQuestions();
  
  public abstract Map<String, Object> StatisticPapers();
  
  public abstract Map<String, Object> StatisticLessions();
  
  public abstract List<Map<String, Object>> getAllNewsCategories();
  
  public abstract Map<String, Object> getUserProfile(String paramString);
  
  public abstract int updateUserProfile(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getAdminProfile(String paramString);
  
  public abstract int updateAdminProfile(Map<String, Object> paramMap);
}

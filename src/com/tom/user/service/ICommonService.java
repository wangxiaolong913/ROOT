package com.tom.user.service;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface ICommonService
{
  public abstract Map<String, Object> doLogin(String paramString1, String paramString2, String paramString3);
  
  public abstract List<Map<String, Object>> getWelcomeNewsList(int paramInt);
  
  public abstract Map<String, Object> getWelcomeStatisticsParams();
  
  public abstract List<Map<String, Object>> getWelcomeExamList(String paramString1, String paramString2, int paramInt);
  
  public abstract List<Map<String, Object>> getWelcomeExamHistoryList(String paramString1, String paramString2, int paramInt);
  
  public abstract Map<String, Object> getNews(String paramString);
  
  public abstract Pagination getNewsList(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> getNewsCategory(String paramString);
  
  public abstract List<Map<String, Object>> getAllNewsCategories();
  
  public abstract Map<String, Object> getUserProfile(String paramString);
  
  public abstract int updateUserProfile(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getAdminProfile(String paramString);
  
  public abstract int updateAdminProfile(Map<String, Object> paramMap);
  
  public abstract String getAdminRolePrivilege(String paramString);
  
  public abstract Map<String, Object> getSystemBaseConfig();
  
  public abstract int getTotalUsers();
}

package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface INewsDao
{
  public abstract int addNewsCategory(Map<String, Object> paramMap);
  
  public abstract int deleteNewsCategory(String paramString);
  
  public abstract int updateNewsCategory(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getNewsCategory(String paramString);
  
  public abstract List<Map<String, Object>> getAllNewsCategories();
  
  public abstract Pagination queryNewsCategories(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getNewsNumbers(String paramString);
  
  public abstract int addNews(Map<String, Object> paramMap);
  
  public abstract int deleteNews(String paramString);
  
  public abstract int updateNews(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getNews(String paramString);
  
  public abstract Map<String, Object> getNews4Read(String paramString);
  
  public abstract Pagination queryNews(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

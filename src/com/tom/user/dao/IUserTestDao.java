package com.tom.user.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IUserTestDao
{
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<Map<String, Object>> queryQuestions(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract int saveTestPaper(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getTestPaper(String paramString1, String paramString2);
  
  public abstract int deleteTestPaper(String paramString1, String paramString2);
  
  public abstract List<Map<String, Object>> getAllQDBS();
}

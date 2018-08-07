package com.tom.user.dao;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface IUserPaperDao
{
  public abstract Map<String, Object> getPaper(String paramString);
  
  public abstract int startExam(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getRandomPaper(String paramString1, String paramString2);
  
  public abstract int saveRandomPaper(String paramString1, String paramString2, String paramString3);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Pagination history(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> getHistoryDetail(String paramString1, String paramString2);
  
  public abstract Map<String, Object> getExamInfo(String paramString1, String paramString2);
  
  public abstract int userSubmitPaper(Map<String, Object> paramMap);
}

package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface IPaperHistoryService
{
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> getDetail(String paramString);
  
  public abstract int deleteOneDetail(String paramString1, String paramString2, String paramString3);
  
  public abstract int deleteAllDetail(String paramString);
  
  public abstract int checkOneQuestion(String paramString1, String paramString2, int paramInt);
  
  public abstract String exportHistory(String paramString);
  
  public abstract Map<String, Object> getPaperCheckProgress(String paramString);
}

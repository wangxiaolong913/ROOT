package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IPaperHistoryDao
{
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<Map<String, Object>> export(String paramString);
  
  public abstract Map<String, Object> getPaperInfo(String paramString);
  
  public abstract Map<String, Object> getDetail(String paramString);
  
  @Transactional
  public abstract int deleteOneDetail(String paramString1, String paramString2, String paramString3);
  
  @Transactional
  public abstract int deleteAllDetail(String paramString);
  
  public abstract int updateCheck(String paramString1, String paramString2, int paramInt);
  
  public abstract Map<String, Object> getPaperCheckProgress(String paramString);
}

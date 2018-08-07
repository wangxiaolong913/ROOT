package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IPaperDao
{
  @Transactional
  public abstract int addPaper(Map<String, Object> paramMap);
  
  @Transactional
  public abstract int deletePaper(String paramString);
  
  @Transactional
  public abstract int updatePaper(Map<String, Object> paramMap);
  
  public abstract int updatePaperDetail(String paramString1, int paramInt1, int paramInt2, String paramString2);
  
  public abstract Map<String, Object> getPaper(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getUserNumbers(String paramString);
  
  public abstract List<Map<String, Object>> getPaperLink(String paramString);
}

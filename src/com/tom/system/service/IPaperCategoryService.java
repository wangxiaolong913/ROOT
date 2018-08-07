package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IPaperCategoryService
{
  public abstract int addPaperCategory(Map<String, Object> paramMap);
  
  public abstract int deletePaperCategory(String paramString);
  
  public abstract int updatePaperCategory(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getPaperCategory(String paramString);
  
  public abstract List<Map<String, Object>> getAllPaperCategorys();
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

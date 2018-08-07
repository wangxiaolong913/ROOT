package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IPaperService
{
  public abstract int addPaper(Map<String, Object> paramMap);
  
  public abstract int deletePaper(String paramString);
  
  public abstract int updatePaper(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getPaper(String paramString);
  
  public abstract int updatePaperDetail(HttpServletRequest paramHttpServletRequest);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<Map<String, Object>> getPaperLink(String paramString);
  
  public abstract int fastAddPaper(HttpServletRequest paramHttpServletRequest);
  
  public abstract String doExportPaperAsWord(String paramString);
  
  public abstract int doCopyPaper(String paramString1, String paramString2);
}

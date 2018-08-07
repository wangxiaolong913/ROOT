package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAnalysisService
{
  public abstract Pagination queryPaper(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Pagination AnalyzePaperList(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> AnalyzePaperDetail(String paramString);
  
  public abstract Map<String, Object> AnalyzeExam(String paramString);
  
  public abstract List<Map<String, Object>> AnalyzeScore(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);
}

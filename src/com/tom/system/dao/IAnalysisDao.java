package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAnalysisDao
{
  public abstract Pagination queryPaper(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<Map<String, Object>> getExamHistoryList(String paramString);
  
  public abstract Pagination AnalyzePaper(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> AnalyzeExam(String paramString);
  
  public abstract List<Map<String, Object>> AnalyzeScore(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);
}

package com.tom.core.dao;

import java.util.List;
import java.util.Map;

public abstract interface ICorePaperDao
{
  public abstract Map<String, Object> getPaper(String paramString);
  
  public abstract Map<String, Object> getQuestion(String paramString);
  
  public abstract List<Map<String, Object>> getAllPapers();
  
  public abstract List<Map<String, Object>> getQuestions(String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int saveUserRandomPaper(String paramString1, String paramString2, String paramString3);
  
  public abstract Map<String, Object> getUserRandomPaper(String paramString1, String paramString2);
}

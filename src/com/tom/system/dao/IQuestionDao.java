package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IQuestionDao
{
  public abstract int addQuestion(Map<String, Object> paramMap);
  
  public abstract int importQuestions(List<Map<String, Object>> paramList);
  
  public abstract int deleteQuestion(String paramString);
  
  public abstract boolean hasBeenUsed(String paramString);
  
  public abstract int updateQuestion(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getQuestion(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<Map<String, Object>> queryQuestions(String paramString1, String paramString2, int paramInt1, int paramInt2);
}

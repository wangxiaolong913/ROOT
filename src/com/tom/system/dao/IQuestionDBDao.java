package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IQuestionDBDao
{
  public abstract int addQuestionDB(Map<String, Object> paramMap);
  
  public abstract int deleteQuestionDB(String paramString);
  
  public abstract int updateQuestionDB(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getQuestionDB(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getQuestionNums(String paramString);
  
  public abstract List<Map<String, Object>> getAllDBS();
  
  public abstract List<Map<String, Object>> CalculateQuestions(String paramString);
}

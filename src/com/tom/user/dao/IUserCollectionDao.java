package com.tom.user.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IUserCollectionDao
{
  public abstract int addCollectionType(Map<String, Object> paramMap);
  
  public abstract int deleteCollectionType(String paramString1, String paramString2);
  
  public abstract int updateCollectionType(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getCollectionType(String paramString1, String paramString2);
  
  public abstract List<Map<String, Object>> getAllCollectionType(String paramString);
  
  public abstract Pagination queryCollectionType(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getCollectionNumbers(String paramString);
  
  public abstract int addCollection(Map<String, Object> paramMap);
  
  public abstract int deleteCollection(String paramString1, String paramString2);
  
  public abstract int clearCollections(String paramString);
  
  public abstract Map<String, Object> getCollection(String paramString1, String paramString2);
  
  public abstract Pagination queryCollection(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

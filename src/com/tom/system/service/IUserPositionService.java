package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IUserPositionService
{
  public abstract int addUserPosition(Map<String, Object> paramMap);
  
  public abstract int deleteUserPosition(String paramString);
  
  public abstract int updateUserPosition(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getUserPosition(String paramString);
  
  public abstract List<Map<String, Object>> getAllPositions();
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

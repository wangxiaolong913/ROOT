package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IUserPositionDao
{
  public abstract int addUserPosition(Map<String, Object> paramMap);
  
  public abstract int deleteUserPosition(String paramString);
  
  public abstract int updateUserPosition(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getUserPosition(String paramString);
  
  public abstract List<Map<String, Object>> getAllUserPositions();
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<?> getUsers(String paramString);
  
  public abstract int getNumbers(String paramString);
}

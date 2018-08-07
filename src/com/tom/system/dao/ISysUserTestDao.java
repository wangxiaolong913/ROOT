package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface ISysUserTestDao
{
  public abstract Pagination queryUserTest(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> getUserTestDetail(String paramString1, String paramString2);
  
  public abstract int deleteUserTestDetail(String paramString);
}

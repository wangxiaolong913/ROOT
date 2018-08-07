package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IAdminDao
{
  @Transactional
  public abstract int addAdmin(Map<String, Object> paramMap);
  
  public abstract int deleteAdmin(String paramString);
  
  public abstract int updateAdminStatus(String paramString, int paramInt);
  
  public abstract int updateAdmin(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getAdmin(String paramString);
  
  public abstract Map<String, Object> getAdmin(String paramString1, String paramString2);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> getAdminByUsername(String paramString);
  
  public abstract int addAdminAddition(Map<String, Object> paramMap);
  
  public abstract int updateAdminAddtion(Map<String, Object> paramMap);
}

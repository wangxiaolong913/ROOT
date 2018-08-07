package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAdminRoleDao
{
  public abstract int addAdminRole(Map<String, Object> paramMap);
  
  public abstract int deleteAdminRole(String paramString);
  
  public abstract int updateAdminRole(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getAdminRole(String paramString);
  
  public abstract List<Map<String, Object>> getAllRoles();
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract List<?> getAdmins(String paramString);
  
  public abstract int getAdminNumbers(String paramString);
}

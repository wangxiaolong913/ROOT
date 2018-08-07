package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface IAdminService
{
  public abstract int addAdmin(Map<String, Object> paramMap);
  
  public abstract int deleteAdmin(String paramString);
  
  public abstract int deleteAdminLogic(String paramString);
  
  public abstract int recoveryAdmin(String paramString);
  
  public abstract int updateAdmin(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getAdmin(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract boolean doCheckUsernameExist(String paramString);
  
  public abstract int doLogin(String paramString1, String paramString2);
}

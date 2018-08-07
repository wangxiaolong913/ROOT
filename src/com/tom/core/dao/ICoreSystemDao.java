package com.tom.core.dao;

import java.util.List;
import java.util.Map;

public abstract interface ICoreSystemDao
{
  public abstract List<Map<String, Object>> getAllAdminRoles();
  
  public abstract int saveLog(List<List<Object>> paramList);
  
  public abstract int getTotalUsers();
  
  public abstract String getRegisterInfo();
}

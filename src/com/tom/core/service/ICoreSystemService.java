package com.tom.core.service;

import java.util.Map;

public abstract interface ICoreSystemService
{
  public abstract int loadAllAdminPrivilege();
  
  public abstract Map<String, Object> getConfig();
  
  public abstract String getConfigValue(String paramString);
  
  public abstract int doCheckSystemLogQueue();
  
  public abstract int getTotalUsers();
}

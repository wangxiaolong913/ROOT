package com.tom.system.dao;

import java.util.Map;

public abstract interface IConfigDao
{
  public abstract int updateConfig(String paramString1, String paramString2);
  
  public abstract Map<String, Object> getConfig(String paramString);
  
  public abstract int addConfig(String paramString1, String paramString2);
}

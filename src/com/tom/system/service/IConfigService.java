package com.tom.system.service;

import java.util.Map;

public abstract interface IConfigService
{
  public abstract int updateConfig(String paramString, Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getConfig(String paramString);
}

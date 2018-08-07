package com.tom.util;

import java.util.HashMap;
import java.util.Map;

public class WebApplication
{
  private static WebApplication webApplication = new WebApplication();
  private Map<String, Object> singletonMap = new HashMap();
  
  public static WebApplication getInstance()
  {
    return webApplication;
  }
  
  public <T> T getSingletonObject(Class<T> clazz)
  {
    T t = null;
    if (clazz != null)
    {
      String key = clazz.getName();
      if (this.singletonMap.containsKey(key)) {
        t = (T) this.singletonMap.get(key);
      }
      if (t == null) {
        try
        {
          t = clazz.newInstance();
          this.singletonMap.put(key, t);
        }
        catch (InstantiationException e)
        {
          e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
          e.printStackTrace();
        }
      }
    }
    return t;
  }
}

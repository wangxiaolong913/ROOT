package com.tom.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

public class CacheHelper
{
  private static Logger logger = Logger.getLogger(CacheHelper.class);
  
  public static <T> T getCache(String cacheName, String key)
  {
    try
    {
      CacheManager manager = CacheManager.getInstance();
      Cache c = manager.getCache(cacheName);
      Element result = c.get(key);
      logger.info("��������:cacheName=" + cacheName + ",key=" + key + ",result=" + result);
      if (result == null) {
        return null;
      }
      return (T)result.getObjectValue();
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
    return null;
  }
  
  public static void addCache(String cacheName, String key, Object value)
  {
    try
    {
      logger.info("��������:cacheName=" + cacheName + ",key=" + key + ",value=" + value);
      CacheManager manager = CacheManager.getInstance();
      Cache c = manager.getCache(cacheName);
      Element e = new Element(key, value);
      c.put(e);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
  }
  
  public static void updateCache(String cacheName, String key, Object value)
  {
    try
    {
      logger.info("��������:cacheName=" + cacheName + ",key=" + key + ",value=" + value);
      removeCache(cacheName, key);
      addCache(cacheName, key, value);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
  }
  
  public static void removeCache(String cacheName)
  {
    try
    {
      logger.info("��������[" + cacheName + "][*]");
      CacheManager manager = CacheManager.getInstance();
      Cache c = manager.getCache(cacheName);
      if (c != null) {
        c.removeAll();
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
  }
  
  public static void removeCache(String cacheName, String key)
  {
    try
    {
      logger.info("��������[" + cacheName + "][" + key + "]");
      CacheManager manager = CacheManager.getInstance();
      Cache c = manager.getCache(cacheName);
      if (c != null) {
        c.remove(key);
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
  }
}

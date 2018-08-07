package com.tom.core.service.imp;

import com.tom.core.TomSystemQueue;
import com.tom.core.dao.ICoreSystemDao;
import com.tom.core.service.ICoreSystemService;
import com.tom.model.system.SysLogger;
import com.tom.system.service.IConfigService;
import com.tom.util.CacheHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CoreSystemService")
public class CoreSystemServiceImp
  implements ICoreSystemService
{
  private static final Logger logger = Logger.getLogger(CoreSystemServiceImp.class);
  @Autowired
  private ICoreSystemDao dao;
  @Autowired
  private IConfigService service;
  
  public int loadAllAdminPrivilege()
  {
    try
    {
      List<Map<String, Object>> list = this.dao.getAllAdminRoles();
      if ((list != null) && (list.size() > 0)) {
        for (Map<String, Object> map : list)
        {
          String roleid = String.valueOf(map.get("r_id"));
          String privilege = String.valueOf(map.get("r_privilege"));
          CacheHelper.addCache("RoleCache", "R" + roleid, "," + privilege);
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 1;
  }
  
  public Map<String, Object> getConfig()
  {
    Map<String, Object> config = (Map)CacheHelper.getCache("ConfigCache", "base");
    if (config == null)
    {
      config = this.service.getConfig("1");
      CacheHelper.addCache("ConfigCache", "base", config);
    }
    return config;
  }
  
  public String getConfigValue(String key)
  {
    return String.valueOf(getConfig().get(key));
  }
  
  public int doCheckSystemLogQueue()
  {
    List<List<Object>> list = new ArrayList();
    try
    {
      for (int i = 0; i < 10; i++)
      {
        SysLogger log = (SysLogger)TomSystemQueue.SYSTEM_LOG_QUEUE.poll();
        if (log != null)
        {
          List<Object> ls = new ArrayList();
          ls.add(Integer.valueOf(log.getUsertype()));
          ls.add(log.getUsername());
          ls.add(log.getAction());
          ls.add(log.getUrl());
          ls.add(log.getLogdate());
          ls.add(log.getDesc());
          ls.add(log.getIp());
          
          list.add(ls);
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    int ips = list.size();
    if (ips < 1) {
      return 0;
    }
    try
    {
      return this.dao.saveLog(list);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int getTotalUsers()
  {
    try
    {
      return this.dao.getTotalUsers();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return -1;
  }
}

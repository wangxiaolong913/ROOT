package com.tom.system.service.imp;

import com.tom.model.ModelHelper;
import com.tom.system.dao.IConfigDao;
import com.tom.system.service.IConfigService;
import com.tom.util.CacheHelper;
import com.tom.web.message.MessageHelper;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ConfigService")
public class ConfigServiceImp
  implements IConfigService
{
  private static final Logger logger = Logger.getLogger(ConfigServiceImp.class);
  @Autowired
  private IConfigDao dao;
  
  public Map<String, Object> getConfig(String id)
  {
    Map<String, Object> map = null;
    try
    {
      map = this.dao.getConfig(id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    if (map == null) {
      return new HashMap();
    }
    String xml = String.valueOf(map.get("c_config"));
    Map<String, Object> config = (Map)ModelHelper.convertObject(xml);
    if (config == null) {
      return new HashMap();
    }
    return config;
  }
  
  public int updateConfig(String id, Map<String, Object> config)
  {
    try
    {
      String xml = ModelHelper.formatObject(config);
      int rows = this.dao.updateConfig(id, xml);
      if (rows > 0)
      {
        String lang = String.valueOf(config.get("sys_lang"));
        if ("en".equals(lang)) {
          MessageHelper.setLang("en");
        } else {
          MessageHelper.setLang("zh_CN");
        }
        CacheHelper.updateCache("ConfigCache", "base", config);
      }
      return rows;
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
}

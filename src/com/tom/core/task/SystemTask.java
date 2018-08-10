package com.tom.core.task;

import com.tom.core.service.ICoreSystemService;
import com.tom.core.util.SystemConnector;
import com.tom.util.Constants;
import com.tom.web.message.MessageHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemTask
{
  private static final Logger logger = Logger.getLogger(SystemTask.class);
  @Autowired
  private ICoreSystemService service;
  private boolean loaded = false;
  
  @Scheduled(fixedRate=3600000L)
  public void doSystemHeartBeat()
  {
    int users = this.service.getTotalUsers();
    if (users < 0) {
      logger.error("定时任务:用户数异常,users=" + users);
    }
  }
  
  @Scheduled(fixedRate=86400000L)
  public void doSystemSettings()
  {
    if (!this.loaded)
    {
      logger.info("系统设置:设定系统参数...");
      String lang = this.service.getConfigValue("sys_lang");
      MessageHelper.setLang(lang);
      
      logger.info("系统设置:加载系统权限...");
      this.service.loadAllAdminPrivilege();
      
      this.loaded = true;
    }
  }
  
  @Scheduled(fixedRate=5000L)
  public void doSystemLogCheck()
  {
    int rows = this.service.doCheckSystemLogQueue();
    if (rows > 0) {
      logger.info("定时任务:成功入库" + rows + "条系统日志.");
    }
  }
  
  @Scheduled(fixedRate=86400000L)
  public void doSystemConnect()
  {
    int users = this.service.getTotalUsers();
    String d = "F-MID-" + Constants.getSystemId() + "--U-" + users;
    try
    {
      SystemConnector.doConnect(d);
    }
    catch (Exception localException) {}
  }
}

package com.tom.core.util;

import com.tom.core.TomSystemQueue;
import com.tom.model.system.SysLogger;
import com.tom.util.BaseUtil;
import java.sql.Timestamp;
import java.util.Queue;

public class SystemLoggerHelper
{
  public static void Log(int usertype, String username, String action, String url, String desc, String ip)
  {
    if ("P-SYS-LOG".equals(action)) {
      return;
    }
    desc = BaseUtil.subString(desc, 200);
    url = BaseUtil.subString(url, 400);
    Timestamp date = new Timestamp(System.currentTimeMillis());
    
    SysLogger log = new SysLogger(usertype, username, action, url, desc, date, ip);
    
    TomSystemQueue.SYSTEM_LOG_QUEUE.add(log);
  }
}

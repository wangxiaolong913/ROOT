package com.tom.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class WebAppListener
  implements ServletContextListener
{
  private static Logger logger = Logger.getLogger(WebAppListener.class);
  
  public void contextInitialized(ServletContextEvent arg0)
  {
    logger.info("TomExam is Starting...");
    logger.info("SYS_SOFTVERSION:TomExam V3.0 UTF-8 ������ Build A9");
  }
  
  public void contextDestroyed(ServletContextEvent arg0)
  {
    logger.info("TomExam is Shutting down...");
  }
}

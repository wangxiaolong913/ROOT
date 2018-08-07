package com.tom.core.task;

import com.tom.core.service.ICorePaperCheckService;
import com.tom.core.service.ICorePaperService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PaperTask
{
  private static final Logger logger = Logger.getLogger(PaperTask.class);
  @Autowired
  private ICorePaperService paperService;
  @Autowired
  private ICorePaperCheckService paperCheckService;
  private boolean loaded = false;
  
  @Scheduled(fixedRate=3600000L)
  public void doLoadPapers()
  {
    if (!this.loaded)
    {
      this.paperService.loadAllPapers();
      this.loaded = true;
    }
  }
  
  @Scheduled(fixedRate=5000L)
  public void doPaperCheck()
  {
    int rows = this.paperCheckService.doCheckPaperQueue();
    if (rows > 0) {
      logger.info("��������:��������" + rows + "������.");
    }
  }
}

package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ILogDao;
import com.tom.system.service.ILogService;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LogService")
public class LogServiceImp
  implements ILogService
{
  private static final Logger logger = Logger.getLogger(LogServiceImp.class);
  @Autowired
  private ILogDao dao;
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.query(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
}

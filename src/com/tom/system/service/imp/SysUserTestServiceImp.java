package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ISysUserTestDao;
import com.tom.system.service.ISysUserTestService;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysUserTestService")
public class SysUserTestServiceImp
  implements ISysUserTestService
{
  private static final Logger logger = Logger.getLogger(SysUserTestServiceImp.class);
  @Autowired
  private ISysUserTestDao dao;
  
  public Pagination queryUserTest(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryUserTest(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public Map<String, Object> getUserTestDetail(String uid, String tid)
  {
    try
    {
      return this.dao.getUserTestDetail(uid, tid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public int deleteUserTestDetail(String tid)
  {
    try
    {
      return this.dao.deleteUserTestDetail(tid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
}

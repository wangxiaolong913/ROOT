package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IUserPositionDao;
import com.tom.system.service.IUserPositionService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserPositionService")
public class UserPositionServiceImp
  implements IUserPositionService
{
  private static final Logger logger = Logger.getLogger(UserPositionServiceImp.class);
  @Autowired
  private IUserPositionDao dao;
  
  public int addUserPosition(Map<String, Object> position)
  {
    try
    {
      position.put("p_id", BaseUtil.generateId());
      return this.dao.addUserPosition(position);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteUserPosition(String positionid)
  {
    try
    {
      int adms = this.dao.getNumbers(positionid);
      if (adms > 0) {
        return 2;
      }
      return this.dao.deleteUserPosition(positionid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateUserPosition(Map<String, Object> position)
  {
    try
    {
      return this.dao.updateUserPosition(position);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getUserPosition(String positionid)
  {
    try
    {
      return this.dao.getUserPosition(positionid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllPositions()
  {
    try
    {
      return this.dao.getAllUserPositions();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
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

package com.tom.user.service.imp;

import com.tom.model.system.Pagination;
import com.tom.user.dao.IUserCollectionDao;
import com.tom.user.service.IUserCollectionService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserCollectionService")
public class UserCollectionServiceImp
  implements IUserCollectionService
{
  private static final Logger logger = Logger.getLogger(UserCollectionServiceImp.class);
  @Autowired
  private IUserCollectionDao dao;
  
  public int addCollectionType(Map<String, Object> type)
  {
    try
    {
      return this.dao.addCollectionType(type);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteCollectionType(String uid, String id)
  {
    try
    {
      int i = this.dao.getCollectionNumbers(id);
      if (i > 0) {
        return 2;
      }
      return this.dao.deleteCollectionType(uid, id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateCollectionType(Map<String, Object> type)
  {
    try
    {
      return this.dao.updateCollectionType(type);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getCollectionType(String uid, String id)
  {
    try
    {
      return this.dao.getCollectionType(uid, id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllCollectionType(String uid)
  {
    try
    {
      return this.dao.getAllCollectionType(uid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination queryCollectionType(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryCollectionType(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int addCollection(Map<String, Object> collection)
  {
    try
    {
      collection.put("c_id", BaseUtil.generateId());
      return this.dao.addCollection(collection);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteCollection(String uid, String id)
  {
    try
    {
      return this.dao.deleteCollection(uid, id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int clearCollections(String uid)
  {
    try
    {
      return this.dao.clearCollections(uid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getCollection(String uid, String id)
  {
    try
    {
      return this.dao.getCollection(uid, id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination queryCollection(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryCollection(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
}

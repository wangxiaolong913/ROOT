package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IPaperCategoryDao;
import com.tom.system.service.IPaperCategoryService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PaperCategoryService")
public class PaperCategoryServiceImp
  implements IPaperCategoryService
{
  private static final Logger logger = Logger.getLogger(PaperCategoryServiceImp.class);
  @Autowired
  private IPaperCategoryDao dao;
  
  public int addPaperCategory(Map<String, Object> category)
  {
    try
    {
      category.put("c_id", BaseUtil.generateId());
      return this.dao.addPaperCategory(category);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deletePaperCategory(String cid)
  {
    try
    {
      int papers = this.dao.getNumbers(cid);
      if (papers > 0) {
        return 2;
      }
      return this.dao.deletePaperCategory(cid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updatePaperCategory(Map<String, Object> category)
  {
    try
    {
      return this.dao.updatePaperCategory(category);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getPaperCategory(String cid)
  {
    try
    {
      return this.dao.getPaperCategory(cid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllPaperCategorys()
  {
    try
    {
      return this.dao.getAllPaperCategorys();
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

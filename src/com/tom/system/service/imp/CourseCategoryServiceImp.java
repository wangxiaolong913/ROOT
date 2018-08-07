package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ICourseCategoryDao;
import com.tom.system.service.ICourseCategoryService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CourseCategoryService")
public class CourseCategoryServiceImp
  implements ICourseCategoryService
{
  private static final Logger logger = Logger.getLogger(CourseCategoryServiceImp.class);
  @Autowired
  private ICourseCategoryDao dao;
  
  public int addCategory(Map<String, Object> category)
  {
    try
    {
      category.put("ca_id", BaseUtil.generateId());
      return this.dao.addCategory(category);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteCategory(String categoryid)
  {
    try
    {
      int users = this.dao.getCourses(categoryid);
      if (users > 0) {
        return 2;
      }
      return this.dao.deleteCategory(categoryid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getCategory(String categoryid)
  {
    try
    {
      return this.dao.getCategory(categoryid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination queryCategory(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryCategory(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int updateCategory(Map<String, Object> category)
  {
    try
    {
      return this.dao.updateCategory(category);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public List<Map<String, Object>> getAllCategories()
  {
    try
    {
      return this.dao.getAllCategories();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
}

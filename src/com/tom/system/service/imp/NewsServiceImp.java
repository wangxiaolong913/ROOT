package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.INewsDao;
import com.tom.system.service.INewsService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("NewsService")
public class NewsServiceImp
  implements INewsService
{
  private static final Logger logger = Logger.getLogger(NewsServiceImp.class);
  @Autowired
  private INewsDao dao;
  
  public int addNewsCategory(Map<String, Object> category)
  {
    try
    {
      category.put("c_id", BaseUtil.generateId());
      return this.dao.addNewsCategory(category);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteNewsCategory(String categoryid)
  {
    try
    {
      int newss = this.dao.getNewsNumbers(categoryid);
      if (newss > 0) {
        return 2;
      }
      return this.dao.deleteNewsCategory(categoryid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateNewsCategory(Map<String, Object> category)
  {
    try
    {
      return this.dao.updateNewsCategory(category);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getNewsCategory(String categoryid)
  {
    try
    {
      return this.dao.getNewsCategory(categoryid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllNewsCategories()
  {
    try
    {
      return this.dao.getAllNewsCategories();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination queryNewsCategories(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryNewsCategories(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public int addNews(Map<String, Object> news)
  {
    try
    {
      news.put("n_id", BaseUtil.generateId());
      return this.dao.addNews(news);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteNews(String newsid)
  {
    try
    {
      return this.dao.deleteNews(newsid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateNews(Map<String, Object> news)
  {
    try
    {
      return this.dao.updateNews(news);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getNews(String newsid)
  {
    try
    {
      return this.dao.getNews(newsid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> getNews4Read(String newsid)
  {
    try
    {
      return this.dao.getNews4Read(newsid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination queryNews(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryNews(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
}

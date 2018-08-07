package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IQuestionDBDao;
import com.tom.system.service.IQuestionDBService;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("QuestionDBService")
public class QuestionDBServiceImp
  implements IQuestionDBService
{
  private static final Logger logger = Logger.getLogger(QuestionDBServiceImp.class);
  @Autowired
  private IQuestionDBDao dao;
  
  public int addQuestionDB(Map<String, Object> db)
  {
    try
    {
      db.put("d_id", BaseUtil.generateId());
      return this.dao.addQuestionDB(db);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteQuestionDB(String dbid)
  {
    try
    {
      int questions = this.dao.getQuestionNums(dbid);
      if (questions > 0) {
        return 2;
      }
      return this.dao.deleteQuestionDB(dbid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateQuestionDB(Map<String, Object> db)
  {
    try
    {
      return this.dao.updateQuestionDB(db);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getQuestionDB(String dbid)
  {
    try
    {
      return this.dao.getQuestionDB(dbid);
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
  
  public List<Map<String, Object>> getAllDBS()
  {
    try
    {
      return this.dao.getAllDBS();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> AnalyseQuestionDB(String dbid)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("info_question", this.dao.CalculateQuestions(dbid));
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return map;
  }
}

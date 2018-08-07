package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IAnalysisDao;
import com.tom.system.service.IAnalysisService;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AnalysisService")
public class AnalysisServiceImp
  implements IAnalysisService
{
  private static final Logger logger = Logger.getLogger(AnalysisServiceImp.class);
  @Autowired
  private IAnalysisDao dao;
  
  public Pagination queryPaper(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryPaper(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public Pagination AnalyzePaperList(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.AnalyzePaper(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public Map<String, Object> AnalyzePaperDetail(String pid)
  {
    try
    {
      List<Map<String, Object>> list = this.dao.getExamHistoryList(pid);
      if (list == null) {
        return new HashMap();
      }
      Map<String, Object> data = new HashMap();
      
      float totalrows = 1.0F * list.size();
      String check;
      for (Map<String, Object> map : list)
      {
        check = String.valueOf(map.get("e_check"));
        if (!BaseUtil.isEmpty(check))
        {
          JSONObject json = JSONObject.fromObject(check);
          for (Object key : json.keySet())
          {
            String skey = String.valueOf(key);
            int score = json.getInt(skey);
            if (score > 0) {
              if (data.containsKey(skey))
              {
                String _value = String.valueOf(data.get(skey));
                data.put(skey, Integer.valueOf(BaseUtil.getInt(_value) + 1));
              }
              else
              {
                data.put(skey, Integer.valueOf(1));
              }
            }
          }
        }
      }
      Map<String, Object> result = new HashMap();
      for (Object key : data.keySet())
      {
        String _key = String.valueOf(key);
        String _val = String.valueOf(data.get(_key));
        float rate = 100.0F * BaseUtil.getInt(_val) / totalrows;
        result.put(_key, Float.valueOf(rate));
      }
      return result;
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new HashMap();
  }
  
  public Map<String, Object> AnalyzeExam(String pid)
  {
    try
    {
      return this.dao.AnalyzeExam(pid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> AnalyzeScore(String[] from, String[] to, String pid)
  {
    if ((from == null) || (to == null)) {
      return null;
    }
    if ((from.length != to.length) || (from.length < 1)) {
      return null;
    }
    pid = StringEscapeUtils.escapeSql(pid);
    try
    {
      return this.dao.AnalyzeScore(from, to, pid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
}

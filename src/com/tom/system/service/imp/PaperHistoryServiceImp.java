package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IPaperHistoryDao;
import com.tom.system.service.IPaperHistoryService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.util.OfficeToolExcel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PaperHistoryService")
public class PaperHistoryServiceImp
  implements IPaperHistoryService
{
  private static final Logger logger = Logger.getLogger(PaperHistoryServiceImp.class);
  @Autowired
  private IPaperHistoryDao dao;
  
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
  
  public Map<String, Object> getDetail(String eid)
  {
    try
    {
      return this.dao.getDetail(eid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public int deleteOneDetail(String eid, String pid, String uid)
  {
    try
    {
      return this.dao.deleteOneDetail(eid, pid, uid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteAllDetail(String pid)
  {
    try
    {
      return this.dao.deleteAllDetail(pid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return -1;
  }
  
  public int checkOneQuestion(String eid, String qid, int score)
  {
    try
    {
      Map<String, Object> map = getDetail(eid);
      if (map == null) {
        return -1;
      }
      int total = 0;
      String e_check = String.valueOf(map.get("e_check"));
      JSONObject json = JSONObject.fromObject(e_check);
      json.put(qid, Integer.valueOf(score));
      for (Object ky : json.keySet())
      {
        String key = String.valueOf(ky);
        int val = json.getInt(key);
        total += val;
      }
      return this.dao.updateCheck(eid, json.toString(), total);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return -2;
  }
  
  public String exportHistory(String pid)
  {
    if (BaseUtil.isEmpty(pid)) {
      return null;
    }
    String filename = BaseUtil.generateRandomString(15) + ".xls";
    
    List<Map<String, Object>> list = null;
    Map<String, Object> paper = null;
    String paperName = "";
    try
    {
      list = this.dao.export(pid);
      paper = this.dao.getPaperInfo(pid);
      paperName = String.valueOf(paper.get("p_name"));
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    if ((list == null) || (list.isEmpty())) {
      return "-1";
    }
    List<Map<Object, Object>> ls = new ArrayList();
    Map<Object, Object> mmt = new HashMap();
    mmt.put("����������", null);
    mmt.put("��������", null);
    mmt.put("��������", null);
    mmt.put("��������", null);
    mmt.put("��������", null);
    mmt.put("��������", null);
    mmt.put("��������", null);
    mmt.put("��������", null);
    mmt.put("��������", null);
    ls.add(mmt);
    for (Map<String, Object> map : list)
    {
      Map<Object, Object> mm = new HashMap();
      mm.put("����������", String.valueOf(map.get("u_username")));
      mm.put("��������", String.valueOf(map.get("b_name")));
      mm.put("��������", String.valueOf(map.get("u_realname")));
      mm.put("��������", String.valueOf(map.get("u_no")));
      mm.put("��������", String.valueOf(map.get("e_starttime")));
      mm.put("��������", String.valueOf(map.get("e_endtime")));
      mm.put("��������", String.valueOf(map.get("e_score")));
      mm.put("��������", String.valueOf(map.get("e_timecost")));
      mm.put("��������", String.valueOf(map.get("e_ip")));
      ls.add(mm);
    }
    String separator = System.getProperty("file.separator");
    String filepath = Constants.getPhysicalPath() + separator + "files" + separator + "export" + separator + filename;
    try
    {
      OfficeToolExcel.makeExcel(filepath, paperName, ls, new int[] { 20, 20, 20, 20, 20, 20, 20, 20, 20 });
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return filename;
  }
  
  public Map<String, Object> getPaperCheckProgress(String pid)
  {
    try
    {
      return this.dao.getPaperCheckProgress(pid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
}

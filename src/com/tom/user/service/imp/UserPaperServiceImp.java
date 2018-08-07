package com.tom.user.service.imp;

import com.tom.core.TomSystemQueue;
import com.tom.core.service.ICorePaperService;
import com.tom.model.paper.Paper;
import com.tom.model.system.Pagination;
import com.tom.user.dao.IUserPaperDao;
import com.tom.user.service.IUserPaperService;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserPaperService")
public class UserPaperServiceImp
  implements IUserPaperService
{
  @Autowired
  private IUserPaperDao dao;
  @Autowired
  private ICorePaperService corePaperService;
  private static Logger logger = Logger.getLogger(UserPaperServiceImp.class);
  
  public Pagination list(Map<String, Object> params, int pagesize, int currentPageNo)
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
  
  public Paper getPaper(String pid)
  {
    logger.info(String.format("������������������pid=%s", new Object[] { pid }));
    Paper paper = this.corePaperService.getPaper(pid);
    return paper;
  }
  
  public Paper getPaper(String uid, String pid)
  {
    logger.info(String.format("��������������uid=%s��pid=%s", new Object[] { uid, pid }));
    Paper paper = this.corePaperService.getPaper(pid);
    if (paper == null) {
      return null;
    }
    if (paper.getPapertype() == 0) {
      return paper;
    }
    paper = this.corePaperService.getUserRandomPaper(uid, pid);
    if (paper == null) {
      paper = this.corePaperService.buildUserRandomPaper(uid, pid);
    }
    return paper;
  }
  
  public int startExam(Map<String, Object> info)
  {
    logger.info(String.format("������������������info=%s", new Object[] { info.toString() }));
    info.put("e_id", BaseUtil.generateId());
    try
    {
      return this.dao.startExam(info);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return -1;
  }
  
  public int submitPaper(HttpServletRequest request)
  {
    logger.info("������������...");
    
    Map<String, Object> examData = new HashMap();
    String randcode = BaseUtil.generateRandomString(10);
    try
    {
      Map<String, String[]> mapx = request.getParameterMap();
      for (Entry<String, String[]> entry : mapx.entrySet()) {
        examData.put((String)entry.getKey(), StringUtils.join((Object[])entry.getValue(), Constants.TM_SPLITER));
      }
      logger.info("������������,������:" + randcode + ",��������:" + examData);
      
      TomSystemQueue.USER_PAPER_QUEUE.add(examData);
      
      int i = this.dao.userSubmitPaper(examData);
      logger.info("������������,������:" + randcode + ",��������:" + i);
      return i;
    }
    catch (Exception e)
    {
      logger.info("����������������,������:" + randcode);
      logger.error(e);
    }
    return 0;
  }
  
  public Pagination history(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.history(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public Map<String, Object> getHistoryDetail(String uid, String eid)
  {
    try
    {
      return this.dao.getHistoryDetail(uid, eid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public int getUserPaperLeftTime(String pid, String uid)
  {
    try
    {
      Paper paper = (Paper)CacheHelper.getCache("PaperCache", "P" + pid);
      int totalSeconds = paper.getDuration() * 60;
      
      Map<String, Object> map = this.dao.getExamInfo(uid, pid);
      if (map == null) {
        return totalSeconds;
      }
      String status = String.valueOf(map.get("e_status"));
      if ("0".equals(status))
      {
        Timestamp stm = (Timestamp)map.get("e_starttime");
        long joinTime = stm.getTime();
        long nowTime = new Date().getTime();
        long passedTime = nowTime - joinTime;
        
        int passedSeconds = new Long(passedTime / 1000L).intValue();
        
        return totalSeconds - passedSeconds;
      }
      return -1;
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return -9;
  }
}

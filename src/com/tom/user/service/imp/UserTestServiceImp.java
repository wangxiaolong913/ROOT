package com.tom.user.service.imp;

import com.tom.core.service.ICorePaperCheckService;
import com.tom.core.service.ICorePaperService;
import com.tom.model.ModelHelper;
import com.tom.model.paper.Paper;
import com.tom.model.paper.PaperCheckResult;
import com.tom.model.paper.PaperSection;
import com.tom.model.paper.Question;
import com.tom.model.system.Pagination;
import com.tom.user.dao.IUserTestDao;
import com.tom.user.service.IUserTestService;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserTestService")
public class UserTestServiceImp
  implements IUserTestService
{
  private static final Logger logger = Logger.getLogger(UserTestServiceImp.class);
  @Autowired
  private IUserTestDao dao;
  @Autowired
  private ICorePaperService corePaperService;
  @Autowired
  private ICorePaperCheckService corePaperCheckService;
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
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
  
  public Paper buildTestPaper(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    String cacheName = "UserTestPaperCache";
    String cacheKey = "UTP" + uid;
    
    logger.info(String.format("����������������...uid=%s", new Object[] { uid }));
    
    Paper paper = new Paper();
    int total_score = 0;
    String p_name = request.getParameter("p_name");
    String p_duration = request.getParameter("p_duration");
    
    paper.setId("0");
    paper.setName(p_name);
    paper.setStatus(1);
    paper.setStarttime(null);
    paper.setEndtime(null);
    paper.setDuration(BaseUtil.getInt(p_duration));
    paper.setShowtime(null);
    paper.setTotalscore(0);
    paper.setPassscore(0);
    paper.setOrdertype(0);
    paper.setPapertype(2);
    paper.setRemark("");
    
    String[] p_section_names = request.getParameterValues("p_section_names");
    String[] p_section_remarks = request.getParameterValues("p_section_remarks");
    String[] p_dbids = request.getParameterValues("p_dbids");
    String[] p_qtypes = request.getParameterValues("p_qtypes");
    String[] p_qnums = request.getParameterValues("p_qnums");
    String[] p_levels = request.getParameterValues("p_levels");
    String[] p_scores = request.getParameterValues("p_scores");
    if ((p_section_names != null) && (p_section_remarks != null) && (p_dbids != null))
    {
      for (int i = 0; i < p_section_names.length; i++)
      {
        String section_name = p_section_names[i];
        String section_remark = p_section_remarks[i];
        String dbid = p_dbids[i];
        String qtype = p_qtypes[i];
        int qnums = BaseUtil.getInt(p_qnums[i]);
        int level = BaseUtil.getInt(p_levels[i]);
        int qscore = BaseUtil.getInt(p_scores[i]);
        
        PaperSection section = new PaperSection(String.valueOf(i), section_name, section_remark);
        
        List<Map<String, Object>> questions = this.dao.queryQuestions(dbid, qtype, level, qnums);
        
        List<Question> list = new ArrayList();
        for (Map<String, Object> map : questions)
        {
          String qId = String.valueOf(map.get("q_id"));
          
          Question question = this.corePaperService.getQuestion(qId);
          question.setScore(qscore);
          
          total_score += qscore;
          list.add(question);
        }
        section.setQuestions(list);
        paper.addSection(section);
      }
      paper.setTotalscore(total_score);
    }
    CacheHelper.addCache(cacheName, cacheKey, paper);
    return paper;
  }
  
  public int saveTestPaper(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    String cacheName = "UserTestPaperCache";
    String cacheKey = "UTP" + uid;
    
    logger.info(String.format("����������������...uid=%s", new Object[] { uid }));
    
    Paper paper = (Paper)CacheHelper.getCache(cacheName, cacheKey);
    if (paper == null)
    {
      logger.info(String.format("����������������...uid=%s...����������������", new Object[] { uid }));
      return 0;
    }
    Map<String, String> map = new HashMap();
    Map<String, String[]> mapx = request.getParameterMap();
    for (Entry<String, String[]> entry : mapx.entrySet())
    {
      String key = (String)entry.getKey();
      if (key.startsWith("Q-")) {
        map.put(key, StringUtils.join((Object[])entry.getValue(), Constants.TM_SPLITER));
      }
    }
    map.put("e_pid", "0");
    map.put("e_uid", uid);
    JSONObject json = JSONObject.fromObject(map);
    
    PaperCheckResult result = this.corePaperCheckService.doCheckPaper(paper, json);
    if ((result == null) || (!result.isSuccess()))
    {
      logger.error("������������������������.");
      return 0;
    }
    String t_timecost = BaseUtil.convertEmptyToSome(request.getParameter("t_timecost"), "-1");
    String t_duration = BaseUtil.convertEmptyToSome(request.getParameter("t_duration"), "-1");
    
    Map<String, Object> examData = new HashMap();
    examData.put("t_tid", BaseUtil.generateId());
    examData.put("t_name", paper.getName());
    examData.put("t_uid", uid);
    examData.put("t_totalscore", Integer.valueOf(paper.getTotalscore()));
    examData.put("t_userscore", Integer.valueOf(result.getScore()));
    examData.put("t_paper", ModelHelper.formatObject(paper));
    examData.put("t_answer", result.getUserdata().toString());
    examData.put("t_check", result.getResult().toString());
    examData.put("t_timecost", Integer.valueOf(BaseUtil.getInt(t_timecost)));
    examData.put("t_duration", Integer.valueOf(BaseUtil.getInt(t_duration)));
    try
    {
      return this.dao.saveTestPaper(examData);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getTestPaper(String uid, String testid)
  {
    try
    {
      logger.info(String.format("����������������...uid=%s, testid=%s", new Object[] { uid, testid }));
      return this.dao.getTestPaper(uid, testid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public int deleteTestPaper(String uid, String testid)
  {
    try
    {
      logger.info(String.format("����������������...uid=%s, testid=%s", new Object[] { uid, testid }));
      return this.dao.deleteTestPaper(uid, testid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public List<Map<String, Object>> getAllQDBS()
  {
    try
    {
      return this.dao.getAllQDBS();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
}

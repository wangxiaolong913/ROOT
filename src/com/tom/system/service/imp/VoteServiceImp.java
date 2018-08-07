package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IVoteDao;
import com.tom.system.service.IVoteService;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("VoteService")
public class VoteServiceImp
  implements IVoteService
{
  private static final Logger logger = Logger.getLogger(VoteServiceImp.class);
  @Autowired
  private IVoteDao dao;
  
  public int addVote(HttpServletRequest request)
  {
    try
    {
      String v_id = BaseUtil.generateId();
      Map<String, Object> vote = new HashMap();
      vote.put("v_id", v_id);
      vote.put("v_title", request.getParameter("v_title"));
      vote.put("v_body", request.getParameter("v_body"));
      vote.put("v_starttime", request.getParameter("v_starttime"));
      vote.put("v_endtime", request.getParameter("v_endtime"));
      vote.put("v_status", request.getParameter("v_status"));
      vote.put("v_data", formatRequest2Vote(request));
      vote.put("v_poster", request.getParameter("v_poster"));
      
      return this.dao.addVote(vote);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  private String formatRequest2Vote(HttpServletRequest request)
  {
    return null;
  }
  
  public int deleteVote(String id)
  {
    try
    {
      int n = this.dao.getVotedNums(id);
      if (n > 0) {
        return 2;
      }
      return this.dao.deleteVote(id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateVote(Map<String, Object> vote)
  {
    try
    {
      return this.dao.updateVote(vote);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getVote(String id)
  {
    try
    {
      return this.dao.getVote(id);
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
  
  public Pagination queryVoteLog(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.getVoteLog(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
}

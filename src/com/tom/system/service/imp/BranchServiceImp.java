package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IBranchDao;
import com.tom.system.service.IBranchService;
import com.tom.util.BaseUtil;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BranchService")
public class BranchServiceImp
  implements IBranchService
{
  private static final Logger logger = Logger.getLogger(BranchServiceImp.class);
  @Autowired
  private IBranchDao dao;
  
  public int addBranch(Map<String, Object> branch)
  {
    try
    {
      branch.put("b_id", BaseUtil.generateId());
      return this.dao.addBranch(branch);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteBranch(String branchid)
  {
    try
    {
      int adms = this.dao.getUsersNumber(branchid);
      if (adms > 0) {
        return 2;
      }
      return this.dao.deleteBranch(branchid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public List<Map<String, Object>> getAllBranchs()
  {
    try
    {
      return this.dao.getAllBranchs();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> getBranch(String branchid)
  {
    try
    {
      return this.dao.getBranch(branchid);
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
  
  public int updateBranch(Map<String, Object> branch)
  {
    try
    {
      return this.dao.updateBranch(branch);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
}

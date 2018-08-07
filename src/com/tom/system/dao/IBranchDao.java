package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IBranchDao
{
  public abstract int addBranch(Map<String, Object> paramMap);
  
  public abstract int deleteBranch(String paramString);
  
  public abstract int updateBranch(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getBranch(String paramString);
  
  public abstract List<Map<String, Object>> getAllBranchs();
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getUsersNumber(String paramString);
}

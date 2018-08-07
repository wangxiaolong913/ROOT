package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IVoteService
{
  public abstract int addVote(HttpServletRequest paramHttpServletRequest);
  
  public abstract int deleteVote(String paramString);
  
  public abstract int updateVote(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getVote(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Pagination queryVoteLog(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

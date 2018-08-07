package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface IVoteDao
{
  public abstract int addVote(Map<String, Object> paramMap);
  
  public abstract int deleteVote(String paramString);
  
  public abstract int updateVote(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getVote(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Pagination getVoteLog(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int getVotedNums(String paramString);
}

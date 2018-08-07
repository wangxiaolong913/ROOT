package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IUserDao
{
  @Transactional
  public abstract int addUser(Map<String, Object> paramMap);
  
  public abstract int deleteUser(String paramString);
  
  public abstract int updateUserStatus(String paramString, int paramInt);
  
  public abstract int updateUser(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getUser(String paramString);
  
  public abstract Map<String, Object> getUser(String paramString1, String paramString2);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> getUserByUsername(String paramString);
  
  public abstract int addUserAddition(Map<String, Object> paramMap);
  
  public abstract int updateUserAddtion(Map<String, Object> paramMap);
  
  public abstract int updateLoginInfo(String paramString);
  
  @Transactional
  public abstract int batchAddUsers(List<List<Object>> paramList1, List<List<Object>> paramList2);
  
  @Transactional
  public abstract int batchUpdateUserStatus(List<List<Object>> paramList);
  
  @Transactional
  public abstract int batchUpdateUserBranch(List<List<Object>> paramList);
  
  @Transactional
  public abstract int batchDeleteUsers(List<List<Object>> paramList);
  
  public abstract Pagination examlist(String paramString, int paramInt1, int paramInt2);
  
  public abstract int getuis();
  
  public abstract Pagination selfTestList(String paramString, int paramInt1, int paramInt2);
  
  public abstract List<Map<String, Object>> getUsersByIds(String[] paramArrayOfString);
}

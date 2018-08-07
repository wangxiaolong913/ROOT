package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public abstract interface IUserService
{
  public abstract int addUser(Map<String, Object> paramMap);
  
  public abstract int importUsers(MultipartFile paramMultipartFile, HttpServletRequest paramHttpServletRequest);
  
  public abstract int deleteUser(String paramString);
  
  public abstract int deleteUserLogic(String paramString);
  
  public abstract int recoveryUser(String paramString);
  
  public abstract int updateUser(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getUser(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract boolean doCheckUsernameExist(String paramString);
  
  public abstract int doLogin(String paramString1, String paramString2);
  
  public abstract int batchDelete(HttpServletRequest paramHttpServletRequest);
  
  public abstract int batchSetStatus(HttpServletRequest paramHttpServletRequest);
  
  public abstract int batchSetGid(HttpServletRequest paramHttpServletRequest);
  
  public abstract Pagination examlist(String paramString, int paramInt1, int paramInt2);
  
  public abstract Pagination selfTestList(String paramString, int paramInt1, int paramInt2);
}

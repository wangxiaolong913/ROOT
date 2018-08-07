package com.tom.user.service;

import com.tom.model.paper.Paper;
import com.tom.model.system.Pagination;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IUserTestService
{
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Paper buildTestPaper(HttpServletRequest paramHttpServletRequest);
  
  public abstract int saveTestPaper(HttpServletRequest paramHttpServletRequest);
  
  public abstract Map<String, Object> getTestPaper(String paramString1, String paramString2);
  
  public abstract int deleteTestPaper(String paramString1, String paramString2);
  
  public abstract List<Map<String, Object>> getAllQDBS();
}

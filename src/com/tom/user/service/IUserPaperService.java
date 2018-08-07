package com.tom.user.service;

import com.tom.model.paper.Paper;
import com.tom.model.system.Pagination;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IUserPaperService
{
  public abstract Pagination list(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Paper getPaper(String paramString);
  
  public abstract Paper getPaper(String paramString1, String paramString2);
  
  public abstract int startExam(Map<String, Object> paramMap);
  
  public abstract int submitPaper(HttpServletRequest paramHttpServletRequest);
  
  public abstract Pagination history(Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Map<String, Object> getHistoryDetail(String paramString1, String paramString2);
  
  public abstract int getUserPaperLeftTime(String paramString1, String paramString2);
}

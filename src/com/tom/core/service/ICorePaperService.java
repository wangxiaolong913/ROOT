package com.tom.core.service;

import com.tom.model.paper.Paper;
import com.tom.model.paper.Question;

public abstract interface ICorePaperService
{
  public abstract Paper getPaper(String paramString);
  
  public abstract Paper getUserRandomPaper(String paramString1, String paramString2);
  
  public abstract Paper buildUserRandomPaper(String paramString1, String paramString2);
  
  public abstract Question getQuestion(String paramString);
  
  public abstract int loadAllPapers();
}

package com.tom.core.service;

import com.tom.model.paper.Paper;
import com.tom.model.paper.PaperCheckResult;
import java.util.List;
import net.sf.json.JSONObject;

public abstract interface ICorePaperCheckService
{
  public abstract PaperCheckResult doCheckPaper(Paper paramPaper, JSONObject paramJSONObject);
  
  public abstract int doCheckPaperQueue();
  
  public abstract int doSaveUserPapers(List<PaperCheckResult> paramList);
}

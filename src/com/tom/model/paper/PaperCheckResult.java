package com.tom.model.paper;

import net.sf.json.JSONObject;

public class PaperCheckResult
{
  private boolean success;
  private String pid;
  private String uid;
  private int score;
  private JSONObject userdata;
  private JSONObject result;
  
  public PaperCheckResult()
  {
    this.success = false;
  }
  
  public PaperCheckResult(boolean success, String pid, String uid, int score, JSONObject userdata, JSONObject result)
  {
    this.success = success;
    this.pid = pid;
    this.uid = uid;
    this.score = score;
    this.userdata = userdata;
    this.result = result;
  }
  
  public boolean isSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(boolean success)
  {
    this.success = success;
  }
  
  public String getPid()
  {
    return this.pid;
  }
  
  public void setPid(String pid)
  {
    this.pid = pid;
  }
  
  public String getUid()
  {
    return this.uid;
  }
  
  public void setUid(String uid)
  {
    this.uid = uid;
  }
  
  public int getScore()
  {
    return this.score;
  }
  
  public void setScore(int score)
  {
    this.score = score;
  }
  
  public JSONObject getUserdata()
  {
    return this.userdata;
  }
  
  public void setUserdata(JSONObject userdata)
  {
    this.userdata = userdata;
  }
  
  public JSONObject getResult()
  {
    return this.result;
  }
  
  public void setResult(JSONObject result)
  {
    this.result = result;
  }
  
  public String toString()
  {
    return 
    
      "PaperCheckResult [success=" + this.success + ", pid=" + this.pid + ", uid=" + this.uid + ", score=" + this.score + ", userdata=" + this.userdata + ", result=" + this.result + "]";
  }
}

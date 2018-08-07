package com.tom.model.system;

import java.sql.Timestamp;

public class SysLogger
{
  private int usertype;
  private String username;
  private String action;
  private String url;
  private String desc;
  private Timestamp logdate;
  private String ip;
  
  public SysLogger(int usertype, String username, String action, String url, String desc, Timestamp logdate, String ip)
  {
    this.usertype = usertype;
    this.username = username;
    this.action = action;
    this.url = url;
    this.desc = desc;
    this.logdate = logdate;
    this.ip = ip;
  }
  
  public int getUsertype()
  {
    return this.usertype;
  }
  
  public void setUsertype(int usertype)
  {
    this.usertype = usertype;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUid(String username)
  {
    this.username = username;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String action)
  {
    this.action = action;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public void setDesc(String desc)
  {
    this.desc = desc;
  }
  
  public Timestamp getLogdate()
  {
    return this.logdate;
  }
  
  public void setLogdate(Timestamp logdate)
  {
    this.logdate = logdate;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
}

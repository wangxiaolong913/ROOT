package com.tom.model.system;

public class BaseUrl
{
  private String title;
  private String url;
  private boolean top;
  
  public BaseUrl(String title, String url)
  {
    this.title = title;
    this.url = url;
    this.top = false;
  }
  
  public BaseUrl(String title, String url, boolean top)
  {
    this.title = title;
    this.url = url;
    this.top = top;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public boolean isTop()
  {
    return this.top;
  }
  
  public void setTop(boolean top)
  {
    this.top = top;
  }
}

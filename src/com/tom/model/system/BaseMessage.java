package com.tom.model.system;

import java.util.ArrayList;
import java.util.List;

public class BaseMessage
{
  private boolean success;
  private String message;
  private List<BaseUrl> urls;
  
  public BaseMessage()
  {
    this.urls = new ArrayList();
  }
  
  public BaseMessage(boolean success, String message)
  {
    this.urls = new ArrayList();
    this.success = success;
    this.message = message;
  }
  
  public BaseMessage(boolean success, String message, List<BaseUrl> urls)
  {
    this.success = success;
    this.message = message;
    this.urls = urls;
  }
  
  public BaseMessage(boolean success, String message, BaseUrl url)
  {
    this.urls = new ArrayList();
    this.success = success;
    this.message = message;
    this.urls.add(url);
  }
  
  public boolean isSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(boolean success)
  {
    this.success = success;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public List<BaseUrl> getUrls()
  {
    return this.urls;
  }
  
  public void setUrls(List<BaseUrl> urls)
  {
    this.urls = urls;
  }
  
  public void addUrl(BaseUrl url)
  {
    this.urls.add(url);
  }
  
  public void addUrl(String title, String url)
  {
    this.urls.add(new BaseUrl(title, url));
  }
}

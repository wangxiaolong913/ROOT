package com.tom.util;

public class TomException
  extends Exception
{
  private static final long serialVersionUID = 2411324963018527483L;
  private int code;
  private String message;
  
  public TomException(String message)
  {
    super(message);
  }
  
  public TomException(int code, String message)
  {
    super(message);
    this.code = code;
    this.message = message;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
}

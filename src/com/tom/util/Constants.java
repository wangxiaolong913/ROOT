package com.tom.util;

import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;

public class Constants
{
  public static String SYS_IDENTIFICATION_CODE = "Tm0qsXm2016";
  public static final String SYS_SOFTVERSION = "TomExam V3.0 UTF-8 ������ Build A9";
  public static String SYS_SOFTUSER = "����";
  public static final int TM_TOTAL_USERS = 200;
  public static String LANGUAGE = "zh_CN";
  public static String TM_SPLITER = "`";
  public static String MV_MESSAGE = "MV_MESSAGE";
  public static String MV_DATA = "MV_DATA";
  public static String MV_MENUS = "MV_MENUS";
  public static String SESSION_USERNAME = "SEN_USERNAME";
  public static String SESSION_USERID = "SEN_USERID";
  public static String SESSION_USERGID = "SEN_USERGID";
  public static String SESSION_USERSTATUS = "SEN_USERSTATUS";
  public static String SESSION_PERMISSION = "SEN_PERMISSION";
  public static String SESSION_USERTYPE = "SEN_USERTYPE";
  public static String SESSION_SESSID = "SESSION_SESSID";
  
  public static String getSiteBaseUrl(HttpServletRequest request)
  {
    String site_base_url = "";
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName();
    int port = request.getServerPort();
    if (port != 80) {
      basePath = basePath + ":" + port;
    }
    basePath = basePath + path;
    if (path.endsWith("/")) {
      site_base_url = basePath;
    } else {
      site_base_url = basePath + "/";
    }
    return site_base_url;
  }
  
  public static String getPhysicalPath()
  {
    return System.getProperty("tomexam.root");
  }
  
  public static String getSystemId()
  {
    try
    {
      String a = TomNetworkHelper.getIp();
      String mc = a + "-" + getPhysicalPath() + "-" + SYS_IDENTIFICATION_CODE;
      return new TomSecurityHelper().ges(mc);
    }
    catch (Exception e)
    {
      System.out.println("����������������������" + e.getMessage());
    }
    return null;
  }
  
  public static abstract interface OPERATION
  {
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
  }
  
  public static abstract interface USER_TYPE
  {
    public static final String ADMIN = "1";
    public static final String USER = "0";
  }
  
  public static abstract interface VAR_STATUS
  {
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final int NORMAL = 1;
    public static final int WAIT_FOR_CHECK = 0;
    public static final int LOCKED = -1;
    public static final int DELETED = -9;
  }
}

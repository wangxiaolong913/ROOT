package com.tom.web.controller;

import com.tom.core.util.SystemLoggerHelper;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

public class BaseController
{
  protected Map<String, Object> getRequestData(HttpServletRequest request)
  {
    Map<String, Object> mapn = new HashMap();
    
    Map<String, String[]> map = request.getParameterMap();
    for (Entry<String, String[]> entry : map.entrySet())
    {
      String value = StringUtils.join((Object[])entry.getValue(), ",");
      String nvalue = HtmlUtils.htmlEscape(value);
      mapn.put((String)entry.getKey(), nvalue);
    }
    return mapn;
  }
  
  protected String getUserIP(HttpServletRequest request)
  {
    String strUserIp = "127.0.0.1";
    try
    {
      strUserIp = request.getHeader("X-Forwarded-For");
      if ((strUserIp == null) || (strUserIp.length() == 0) || ("unknown".equalsIgnoreCase(strUserIp))) {
        strUserIp = request.getHeader("Proxy-Client-IP");
      }
      if ((strUserIp == null) || (strUserIp.length() == 0) || ("unknown".equalsIgnoreCase(strUserIp))) {
        strUserIp = request.getHeader("WL-Proxy-Client-IP");
      }
      if ((strUserIp == null) || (strUserIp.length() == 0) || ("unknown".equalsIgnoreCase(strUserIp))) {
        strUserIp = request.getRemoteAddr();
      }
      if (strUserIp != null) {
        strUserIp = strUserIp.split(",")[0];
      } else {
        strUserIp = "127.0.0.1";
      }
      if (strUserIp.length() > 16) {
        strUserIp = "127.0.0.1";
      }
    }
    catch (Exception e)
    {
      System.err.println("��������IP����");
    }
    return strUserIp;
  }
  
  protected boolean HasPrivelege(HttpServletRequest request, String code)
  {
    HttpSession session = request.getSession();
    String roleid = String.valueOf(session.getAttribute(Constants.SESSION_USERGID));
    String username = String.valueOf(session.getAttribute(Constants.SESSION_USERNAME));
    String url = request.getRequestURI() + (
      BaseUtil.isEmpty(request.getQueryString()) ? "" : new StringBuilder("?")
      .append(request.getQueryString()).toString());
    
    boolean hasprivelege = false;
    String privelege = (String)CacheHelper.getCache("RoleCache", "R" + roleid);
    if (BaseUtil.isEmpty(privelege))
    {
      SystemLoggerHelper.Log(1, username, code, url, String.valueOf(hasprivelege), getUserIP(request));
      return false;
    }
    if (privelege.indexOf("," + code) > -1) {
      hasprivelege = true;
    }
    SystemLoggerHelper.Log(1, username, code, url, String.valueOf(hasprivelege), getUserIP(request));
    
    return hasprivelege;
  }
  
  protected ModelAndView RedirectToNoPrivelegePage()
  {
    return new ModelAndView("common/message_no_privelege");
  }
}

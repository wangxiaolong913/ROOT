package com.tom.web.filter;

import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TomFilter
  implements Filter
{
  private String[] permitUrls = null;
  
  public void init(FilterConfig config)
    throws ServletException
  {
    String permitUrls = config.getInitParameter("permitUrls");
    if (BaseUtil.isNotEmpty(permitUrls)) {
      this.permitUrls = permitUrls.split(";");
    }
  }
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;
    HttpSession session = req.getSession();
    req.setAttribute("tm_base", Constants.getSiteBaseUrl(req));
    
    boolean isPermit = isPermitUrl(req);
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String sessionid = String.valueOf(session.getAttribute(Constants.SESSION_SESSID));
    if (BaseUtil.isEmpty(uid))
    {
      if (isPermit) {
        chain.doFilter(req, res);
      } else {
        toLoginPage(req, res);
      }
    }
    else
    {
      int checkcode = checkLoginStatus(uid, sessionid);
      if (checkcode == 1)
      {
        chain.doFilter(req, res);
      }
      else
      {
        if (checkcode == 0)
        {
          session.setAttribute(Constants.SESSION_USERID, null);
          session.setAttribute(Constants.SESSION_USERNAME, null);
          session.setAttribute(Constants.SESSION_USERTYPE, null);
          session.setAttribute(Constants.SESSION_USERGID, null);
          session.setAttribute(Constants.SESSION_SESSID, null);
          
          toLoginPage(req, res);
          return;
        }
        session.setAttribute(Constants.SESSION_USERID, null);
        session.setAttribute(Constants.SESSION_USERNAME, null);
        session.setAttribute(Constants.SESSION_USERTYPE, null);
        session.setAttribute(Constants.SESSION_USERGID, null);
        session.setAttribute(Constants.SESSION_SESSID, null);
        
        toStatusExpiredPage(req, res);
        return;
      }
    }
  }
  
  public void destroy()
  {
    this.permitUrls = null;
  }
  
  private int checkLoginStatus(String uid, String sessionid)
  {
    String cache_sessionid = (String)CacheHelper.getCache("SessionCache", "U" + uid);
    if (BaseUtil.isEmpty(cache_sessionid)) {
      return 0;
    }
    if (cache_sessionid.equals(sessionid)) {
      return 1;
    }
    return -1;
  }
  
  private void toStatusExpiredPage(HttpServletRequest request, HttpServletResponse response)
  {
    String path = request.getContextPath();
    String html = "<script>top.location.href='" + path + "/common/expired.thtml';</script>";
    PrintWriter out = null;
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    try
    {
      out = response.getWriter();
      request.setCharacterEncoding("UTF-8");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    out.println(html);
    out.flush();
    out.close();
  }
  
  private void toLoginPage(HttpServletRequest request, HttpServletResponse response)
  {
    String path = request.getContextPath();
    String html = "<script>top.location.href='" + path + "/login.thtml';</script>";
    PrintWriter out = null;
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    try
    {
      out = response.getWriter();
      request.setCharacterEncoding("UTF-8");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    out.println(html);
    out.flush();
    out.close();
  }
  
  private boolean isPermitUrl(HttpServletRequest request)
  {
    boolean isPermit = false;
    String currentUrl = getCurrentURI(request);
    if ((this.permitUrls != null) && (this.permitUrls.length > 0))
    {
      String[] arrayOfString;
      int j = (arrayOfString = this.permitUrls).length;
      for (int i = 0; i < j; i++)
      {
        String url = arrayOfString[i];
        if (url.equals(currentUrl))
        {
          isPermit = true;
          break;
        }
      }
    }
    return isPermit;
  }
  
  private String getCurrentURI(HttpServletRequest request)
  {
    String path = request.getContextPath();
    String uri = request.getRequestURI();
    uri = uri.substring(path.length());
    return uri;
  }
}

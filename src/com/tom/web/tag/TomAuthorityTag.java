package com.tom.web.tag;

import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class TomAuthorityTag
  extends TagSupport
{
  private static final long serialVersionUID = -3970435142404505556L;
  private String authcode;
  private String placeholder;
  
  public int doStartTag()
    throws JspException
  {
    HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
    HttpSession session = request.getSession();
    String roleid = String.valueOf(session.getAttribute(Constants.SESSION_USERGID));
    String privelege = (String)CacheHelper.getCache("RoleCache", "R" + roleid);
    if (BaseUtil.isEmpty(privelege)) {
      return 0;
    }
    if (privelege.indexOf("," + getAuthcode()) > -1) {
      return 1;
    }
    try
    {
      JspWriter out = this.pageContext.getOut();
      out.print(getPlaceholder());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    return super.doEndTag();
  }
  
  public String getAuthcode()
  {
    return this.authcode;
  }
  
  public void setAuthcode(String authcode)
  {
    this.authcode = authcode;
  }
  
  public String getPlaceholder()
  {
    return this.placeholder;
  }
  
  public void setPlaceholder(String placeholder)
  {
    this.placeholder = placeholder;
  }
}

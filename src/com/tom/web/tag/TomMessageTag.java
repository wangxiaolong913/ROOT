package com.tom.web.tag;

import com.tom.util.BaseUtil;
import com.tom.web.message.MessageHelper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;

public class TomMessageTag
  extends TagSupport
{
  private static final long serialVersionUID = -388451739329592176L;
  private static Logger logger = Logger.getLogger(TomMessageTag.class);
  private String key;
  
  public int doStartTag()
    throws JspException
  {
    String message = MessageHelper.getMessage(this.key);
    if ((BaseUtil.isEmpty(this.key)) || (BaseUtil.isEmpty(message))) {
      return 0;
    }
    try
    {
      JspWriter out = this.pageContext.getOut();
      out.print(message);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
    return super.doStartTag();
  }
  
  public int doEndTag()
    throws JspException
  {
    return super.doEndTag();
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
}

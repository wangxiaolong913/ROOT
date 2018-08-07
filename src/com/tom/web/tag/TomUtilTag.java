package com.tom.web.tag;

import com.tom.model.ModelHelper;
import com.tom.model.paper.Option;
import com.tom.model.paper.Question;
import com.tom.model.paper.QuestionMultipleChoice;
import com.tom.model.paper.QuestionSingleChoice;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import com.tom.web.message.MessageHelper;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.HtmlUtils;

public class TomUtilTag
  extends TagSupport
{
  private static final long serialVersionUID = 7448434416105284263L;
  private String action;
  private String data;
  private String ext;
  private static Logger logger = Logger.getLogger(TomUtilTag.class);
  
  public int doStartTag()
    throws JspException
  {
    if (BaseUtil.isEmpty(this.action)) {
      return 0;
    }
    String result = "";
    if ("showdate".equalsIgnoreCase(this.action))
    {
      result = new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(System.currentTimeMillis()));
    }
    else if ("showdatex".equalsIgnoreCase(this.action))
    {
      result = new SimpleDateFormat("yyyy-MM-dd EEEE").format(Long.valueOf(System.currentTimeMillis()));
    }
    else if ("version".equalsIgnoreCase(this.action))
    {
      result = "TomExam V3.0 UTF-8 ������ Build A9";
    }
    else if ("authuser".equalsIgnoreCase(this.action))
    {
      result = Constants.SYS_SOFTUSER;
    }
    else if ("param".equalsIgnoreCase(this.action))
    {
      String pa = this.pageContext.getRequest().getParameter(this.data);
      if (BaseUtil.isNotEmpty(pa)) {
        result = BaseUtil.getChinese(pa);
      }
    }
    else if ("base".equalsIgnoreCase(this.action))
    {
      HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
      result = Constants.getSiteBaseUrl(request);
    }
    else if ("html2txt".equalsIgnoreCase(this.action))
    {
      int len = BaseUtil.getInt(getExt());
      result = StringUtils.substring(BaseUtil.Html2TextFormat(getData()), 0, len);
    }
    else if ("getQuestionType".equalsIgnoreCase(this.action))
    {
      if ("1".equals(getData())) {
        result = MessageHelper.getMessage("txt.other.questiontype.single");
      } else if ("2".equals(getData())) {
        result = MessageHelper.getMessage("txt.other.questiontype.multiple");
      } else if ("3".equals(getData())) {
        result = MessageHelper.getMessage("txt.other.questiontype.judgment");
      } else if ("4".equals(getData())) {
        result = MessageHelper.getMessage("txt.other.questiontype.blank");
      } else if ("5".equals(getData())) {
        result = MessageHelper.getMessage("txt.other.questiontype.essay");
      }
    }
    else
    {
      if ("lang".equalsIgnoreCase(this.action))
      {
        if (MessageHelper.getLang().equals(getData())) {
          return 1;
        }
        return 0;
      }
      if ("formatBlank".equalsIgnoreCase(this.action))
      {
        Pattern p = Pattern.compile("\\[BlankArea.+?]");
        Matcher m = null;
        m = p.matcher(getData());
        result = m.replaceAll(getExt());
      }
      else if ("getConfig".equalsIgnoreCase(this.action))
      {
        String key = getData();
        Map<String, Object> config = (Map)CacheHelper.getCache("ConfigCache", "base");
        if (config != null) {
          result = String.valueOf(config.get(key));
        }
      }
      else if ("convertEmpty".equalsIgnoreCase(this.action))
      {
        result = BaseUtil.convertEmptyToSome(getData(), getExt());
      }
      else if ("formatJudgmentAnswer".equalsIgnoreCase(this.action))
      {
        String answer = getData();
        if ("Y".equalsIgnoreCase(answer)) {
          result = MessageHelper.getMessage("txt.other.questiontype.judgment.yes");
        } else if ("N".equalsIgnoreCase(answer)) {
          result = MessageHelper.getMessage("txt.other.questiontype.judgment.no");
        } else {
          result = "&nbsp;";
        }
      }
      else if ("formatChooseOptions".equalsIgnoreCase(this.action))
      {
        String sq = getData();
        StringBuffer sb = new StringBuffer("");
        try
        {
          Question qes = (Question)ModelHelper.convertObject(sq);
          if ("1".equals(qes.getType()))
          {
            QuestionSingleChoice question = (QuestionSingleChoice)qes;
            for (Option option : question.getOptions()) {
              sb.append("<li>" + option.getAlisa() + " . " + option.getText() + "</li>");
            }
          }
          else if ("2".equals(qes.getType()))
          {
            QuestionMultipleChoice question = (QuestionMultipleChoice)qes;
            for (Option option : question.getOptions()) {
              sb.append("<li>" + option.getAlisa() + " . " + option.getText() + "</li>");
            }
          }
        }
        catch (Exception e)
        {
          logger.error("������������������������������������������������������������");
        }
        result = sb.toString();
      }
      else if ("unescape".equalsIgnoreCase(this.action))
      {
        result = HtmlUtils.htmlUnescape(getData());
      }
    }
    try
    {
      JspWriter out = this.pageContext.getOut();
      out.print(result);
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
  
  public void release()
  {
    super.release();
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String action)
  {
    this.action = action;
  }
  
  public String getData()
  {
    return this.data;
  }
  
  public void setData(String data)
  {
    this.data = data;
  }
  
  public String getExt()
  {
    return this.ext;
  }
  
  public void setExt(String ext)
  {
    this.ext = ext;
  }
}

package com.tom.model.system;

import com.tom.util.BaseUtil;
import com.tom.web.message.MessageHelper;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Pagination
{
  private int totalRowsCount = 0;
  private int pageSize = 10;
  private int currentPageNo = 1;
  private String spacer = "&nbsp; ";
  private boolean changesize = true;
  private List<Map<String, Object>> dataList = null;
  
  public Pagination() {}
  
  public Pagination(int currentPageNo, int pageSize)
    throws Exception
  {
    if (pageSize == 0) {
      throw new Exception("单页展示条数不得为0!");
    }
    this.currentPageNo = currentPageNo;
    this.pageSize = pageSize;
  }
  
  public Pagination(int currentPageNo, int pageSize, int totalRowsCount)
  {
    this.currentPageNo = currentPageNo;
    this.pageSize = (pageSize < 1 ? 10 : pageSize);
    this.totalRowsCount = totalRowsCount;
  }
  
  public int getTotalRowsCount()
  {
    return this.totalRowsCount;
  }
  
  public void setTotalRowsCount(int totalRowsCount)
  {
    this.totalRowsCount = totalRowsCount;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getCurrentPageNo()
  {
    return this.currentPageNo;
  }
  
  public void setCurrentPageNo(int currentPageNo)
  {
    this.currentPageNo = currentPageNo;
  }
  
  public int getTotalPageCount()
  {
    if ((this.pageSize != 0) && (this.totalRowsCount % this.pageSize == 0)) {
      return this.totalRowsCount / this.pageSize;
    }
    if ((this.pageSize != 0) && (this.totalRowsCount % this.pageSize != 0)) {
      return this.totalRowsCount / this.pageSize + 1;
    }
    return 0;
  }
  
  public List<Map<String, Object>> getDataList()
  {
    return this.dataList;
  }
  
  public void setDataList(List<Map<String, Object>> dataList)
  {
    this.dataList = dataList;
  }
  
  public void setChangesize(boolean changesize)
  {
    this.changesize = changesize;
  }
  
  public boolean getChangesize()
  {
    return this.changesize;
  }
  
  public String getNavFoot(HttpServletRequest request)
  {
    StringBuffer html = new StringBuffer();
    String currentURL = String.valueOf(request.getRequestURL());
    if (BaseUtil.isNotEmpty(request.getQueryString()))
    {
      currentURL = currentURL + "?";
      currentURL = currentURL + request.getQueryString();
      if (currentURL.indexOf("epage") > -1) {
        currentURL = currentURL.substring(0, currentURL.indexOf("epage") - 1);
      }
      if (currentURL.indexOf("epagesize") > -1) {
        currentURL = currentURL.substring(0, currentURL.indexOf("epagesize") - 1);
      }
    }
    String opflag = "";
    if (currentURL.contains("?")) {
      opflag = "&";
    } else {
      opflag = "?";
    }
    html.append("<div class=\"tm_pager_foot\">");
    if (getCurrentPageNo() <= 1)
    {
      html.append(MessageHelper.getMessage("txt.other.pager.first") + this.spacer);
    }
    else
    {
      html.append("<a href=\"" + currentURL + opflag + "epage=1&epagesize=" + getPageSize() + "\">");
      html.append(MessageHelper.getMessage("txt.other.pager.first"));
      html.append("</a>" + this.spacer);
    }
    if (getCurrentPageNo() <= 1)
    {
      html.append(MessageHelper.getMessage("txt.other.pager.pre") + this.spacer);
    }
    else
    {
      html.append("<a href=\"" + currentURL + opflag + "epage=" + (getCurrentPageNo() - 1) + "&epagesize=" + getPageSize() + "\">");
      html.append(MessageHelper.getMessage("txt.other.pager.pre"));
      html.append("</a>" + this.spacer);
    }
    if (getCurrentPageNo() >= getTotalPageCount())
    {
      html.append(MessageHelper.getMessage("txt.other.pager.next") + this.spacer);
    }
    else
    {
      html.append("<a href=\"" + currentURL + opflag + "epage=" + (getCurrentPageNo() + 1) + "&epagesize=" + getPageSize() + "\">");
      html.append(MessageHelper.getMessage("txt.other.pager.next"));
      html.append("</a>" + this.spacer);
    }
    if (getCurrentPageNo() >= getTotalPageCount())
    {
      html.append(MessageHelper.getMessage("txt.other.pager.last") + this.spacer);
    }
    else
    {
      html.append("<a href=\"" + currentURL + opflag + "epage=" + getTotalPageCount() + "&epagesize=" + getPageSize() + "\">");
      html.append(MessageHelper.getMessage("txt.other.pager.last"));
      html.append("</a>" + this.spacer);
    }
    html.append("&nbsp;");
    html.append(MessageHelper.getMessage("txt.other.pager.total") + getTotalRowsCount() + MessageHelper.getMessage("txt.other.pager.records"));
    
    html.append(" &nbsp; ");
    html.append(" <input type=\"text\" size=\"1\" maxlength=\"3\" class=\"tm_txt\" value=\"" + getCurrentPageNo() + "\" onkeyup=\"tm_fn_page_to(event,'" + currentURL + opflag + "',this.value)\"  /> ");
    html.append(" /" + getTotalPageCount() + " " + MessageHelper.getMessage("txt.other.pager.page"));
    if (getChangesize()) {
      html.append(this.spacer + buildPagesizeSelector(new StringBuilder(String.valueOf(currentURL)).append(opflag).toString()));
    }
    html.append("");
    html.append("</div>");
    
    return html.toString();
  }
  
  private String buildPagesizeSelector(String baseurl)
  {
    StringBuffer html = new StringBuffer();
    
    html.append(MessageHelper.getMessage("txt.other.pager.rowperpage"));
    html.append(" <select id=\"tm_pager_pagesize\" class=\"tm_select\" onchange=\"tm_fn_pagesize('" + baseurl + "',this.value)\">");
    html.append("<option value=\"10\" " + (getPageSize() == 10 ? "selected" : "") + ">10</option>");
    html.append("<option value=\"20\" " + (getPageSize() == 20 ? "selected" : "") + ">20</option>");
    html.append("<option value=\"50\" " + (getPageSize() == 50 ? "selected" : "") + ">50</option>");
    html.append("<option value=\"100\" " + (getPageSize() == 100 ? "selected" : "") + ">100</option>");
    html.append("</select> ");
    html.append(MessageHelper.getMessage("txt.other.pager.row"));
    
    return html.toString();
  }
}

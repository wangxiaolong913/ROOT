package com.tom.system.action;

import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.INewsService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/news"})
public class NewsController
  extends BaseController
{
  @Autowired
  private INewsService service;
  
  @RequestMapping({"/category/add.thtml"})
  public ModelAndView addCategory(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWSCATE-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    return new ModelAndView("system/news/category/add", modelMap);
  }
  
  @RequestMapping({"/category/list.thtml"})
  public ModelAndView listCategory(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-NEWSCATE")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.queryNewsCategories(null, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/news/category/list", modelMap);
  }
  
  @RequestMapping({"/category/load.thtml"})
  public ModelAndView loadCategory(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWSCATE-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String categoryid = request.getParameter("cid");
    Map<String, Object> category = this.service.getNewsCategory(categoryid);
    if (category == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.add"), "system/news/category/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.list"), "system/news/category/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("category", category);
    return new ModelAndView("system/news/category/load", modelMap);
  }
  
  @RequestMapping({"/category/save.do"})
  public ModelAndView saveCategory(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWSCATE-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.addNewsCategory(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.news.category.add", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.news.category.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.add"), "system/news/category/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.list"), "system/news/category/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/category/delete.do"})
  public ModelAndView deleteCategory(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWSCATE-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String cid = request.getParameter("cid");
    int i = this.service.deleteNewsCategory(cid);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.news.category.delete", "message.sys.success" }));
    } else if (i == 2) {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.news.category.hasNews" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.news.category.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.add"), "system/news/category/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.list"), "system/news/category/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/category/update.do"})
  public ModelAndView updateCategory(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWSCATE-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.updateNewsCategory(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.news.category.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.news.category.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.add"), "system/news/category/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.category.list"), "system/news/category/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/add.thtml"})
  public ModelAndView add(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWS-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    modelMap.put("categories", this.service.getAllNewsCategories());
    return new ModelAndView("system/news/add", modelMap);
  }
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-NEWS")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Map<String, Object> params = getRequestData(request);
    params.put("n_title", BaseUtil.getChinese(request.getParameter("n_title")));
    
    Pagination page = this.service.queryNews(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("categories", this.service.getAllNewsCategories());
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/news/list", modelMap);
  }
  
  @RequestMapping({"/load.thtml"})
  public ModelAndView load(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWS-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    Map<String, Object> news = this.service.getNews(id);
    if (news == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.add"), "system/news/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.list"), "system/news/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("categories", this.service.getAllNewsCategories());
    modelMap.put("news", news);
    return new ModelAndView("system/news/load", modelMap);
  }
  
  @RequestMapping({"/save.do"})
  public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWS-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    Map<String, Object> news = getRequestData(request);
    news.put("n_content", request.getParameter("n_content"));
    int i = this.service.addNews(news);
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.news.add", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.news.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.add"), "system/news/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.list"), "system/news/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWS-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    int i = this.service.deleteNews(id);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.news.delete", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.news.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.add"), "system/news/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.list"), "system/news/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-NEWS-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    
    Map<String, Object> news = getRequestData(request);
    news.put("n_content", request.getParameter("n_content"));
    int i = this.service.updateNews(news);
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.news.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.news.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.add"), "system/news/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.news.list"), "system/news/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

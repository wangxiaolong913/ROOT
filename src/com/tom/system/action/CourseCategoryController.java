package com.tom.system.action;

import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.ICourseCategoryService;
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
@RequestMapping({"/system/course/category"})
public class CourseCategoryController
  extends BaseController
{
  @Autowired
  private ICourseCategoryService service;
  
  @RequestMapping({"/add.thtml"})
  public ModelAndView add(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-CATEGORY-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    return new ModelAndView("system/course/category/add", modelMap);
  }
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-COURSE-CATEGORY")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.queryCategory(getRequestData(request), BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/course/category/list", modelMap);
  }
  
  @RequestMapping({"/load.thtml"})
  public ModelAndView load(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-CATEGORY-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    Map<String, Object> category = this.service.getCategory(id);
    if (category == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.add"), "system/course/category/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.list"), "system/course/category/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("category", category);
    return new ModelAndView("system/course/category/load", modelMap);
  }
  
  @RequestMapping({"/save.do"})
  public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-CATEGORY-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.addCategory(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.category.add", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.category.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.add"), "system/course/category/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.list"), "system/course/category/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-CATEGORY-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    int i = this.service.deleteCategory(id);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.category.delete", "message.sys.success" }));
    } else if (i == 2) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.course.category.delete.hascourse"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.category.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.add"), "system/course/category/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.list"), "system/course/category/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-CATEGORY-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.updateCategory(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.category.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.category.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.add"), "system/course/category/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.category.list"), "system/course/category/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

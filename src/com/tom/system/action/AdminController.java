package com.tom.system.action;

import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IAdminRoleService;
import com.tom.system.service.IAdminService;
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
@RequestMapping({"/system/admin"})
public class AdminController
  extends BaseController
{
  @Autowired
  private IAdminService adminservice;
  @Autowired
  private IAdminRoleService roleservice;
  
  @RequestMapping({"/add.thtml"})
  public ModelAndView add(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ADMIN-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    modelMap.put("roles", this.roleservice.getAllRoles());
    return new ModelAndView("system/admin/add", modelMap);
  }
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-ADMIN")) {
      return RedirectToNoPrivelegePage();
    }
    modelMap.put("roles", this.roleservice.getAllRoles());
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Map<String, Object> params = getRequestData(request);
    if ((params != null) && (BaseUtil.isNotEmpty(request.getParameter("a_realname")))) {
      params.put("a_realname", BaseUtil.getChinese(request.getParameter("a_realname")));
    }
    Pagination page = this.adminservice.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/admin/list", modelMap);
  }
  
  @RequestMapping({"/load.thtml"})
  public ModelAndView load(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ADMIN-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    Map<String, Object> admin = this.adminservice.getAdmin(id);
    if (admin == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.add"), "system/admin/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.list"), "system/admin/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("roles", this.roleservice.getAllRoles());
    modelMap.put("admin", admin);
    return new ModelAndView("system/admin/load", modelMap);
  }
  
  @RequestMapping({"/save.do"})
  public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ADMIN-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.adminservice.addAdmin(getRequestData(request));
    if (i == 2) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.admin.add", "message.sys.success" }));
    } else if (i == 3) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.admin.userexist"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.admin.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.add"), "system/admin/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.list"), "system/admin/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ADMIN-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    int i = this.adminservice.deleteAdmin(id);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.admin.delete", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.admin.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.add"), "system/admin/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.list"), "system/admin/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ADMIN-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.adminservice.updateAdmin(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.admin.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.admin.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.add"), "system/admin/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.admin.list"), "system/admin/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

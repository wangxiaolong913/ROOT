package com.tom.system.action;

import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.system.service.IConfigService;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/config"})
public class ConfigController
  extends BaseController
{
  @Autowired
  private IConfigService service;
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-SYS-CONFIG")) {
      return RedirectToNoPrivelegePage();
    }
    modelMap.put("config", this.service.getConfig("1"));
    return new ModelAndView("system/config/list", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-SYS-CONFIG")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.updateConfig("1", getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.config.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.config.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.config.list"), "system/config/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

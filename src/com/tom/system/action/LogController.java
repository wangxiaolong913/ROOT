package com.tom.system.action;

import com.tom.model.system.Pagination;
import com.tom.system.service.ILogService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/log"})
public class LogController
  extends BaseController
{
  @Autowired
  private ILogService service;
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-SYS-LOG")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "20");
    Map<String, Object> params = getRequestData(request);
    if ((params != null) && (BaseUtil.isNotEmpty(request.getParameter("l_action_name")))) {
      params.put("l_action_name", BaseUtil.getChinese(request.getParameter("l_action_name")));
    }
    Pagination page = this.service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/log/list", modelMap);
  }
}

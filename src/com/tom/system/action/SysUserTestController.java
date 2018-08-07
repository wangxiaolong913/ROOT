package com.tom.system.action;

import com.tom.model.ModelHelper;
import com.tom.model.paper.Paper;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IBranchService;
import com.tom.system.service.ISysUserTestService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/selftest"})
public class SysUserTestController
  extends BaseController
{
  @Autowired
  private ISysUserTestService service;
  @Autowired
  private IBranchService branch_service;
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-SELFTEST-RECORDS-VIEW")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    String ut_keywords = BaseUtil.getChinese(request.getParameter("ut_keywords"));
    String ut_orderby = request.getParameter("ut_orderby");
    String ut_startdate = request.getParameter("ut_startdate");
    String ut_enddate = request.getParameter("ut_enddate");
    String ut_branchid = request.getParameter("ut_branchid");
    
    Map<String, Object> params = new HashMap();
    params.put("ut_keywords", ut_keywords);
    params.put("ut_orderby", ut_orderby);
    params.put("ut_startdate", ut_startdate);
    params.put("ut_enddate", ut_enddate);
    params.put("ut_branchid", ut_branchid);
    
    Pagination page = this.service.queryUserTest(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    
    modelMap.put("branches", this.branch_service.getAllBranchs());
    
    return new ModelAndView("system/selftest/list", modelMap);
  }
  
  @RequestMapping({"/detail.thtml"})
  public ModelAndView detail(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-SELFTEST-RECORDS-VIEW")) {
      return RedirectToNoPrivelegePage();
    }
    String uid = request.getParameter("uid");
    String tid = request.getParameter("tid");
    try
    {
      Map<String, Object> detail = this.service.getUserTestDetail(uid, tid);
      String t_paper = String.valueOf(detail.get("t_paper"));
      String t_answer = String.valueOf(detail.get("t_answer"));
      String t_check = String.valueOf(detail.get("t_check"));
      
      Paper paper = (Paper)ModelHelper.convertObject(t_paper);
      JSONObject answer = JSONObject.fromObject(t_answer);
      JSONObject check = JSONObject.fromObject(t_check);
      
      modelMap.put("detail", detail);
      modelMap.put("paper", paper);
      modelMap.put("data", answer);
      modelMap.put("check", check);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.selftest.list"), "system/selftest/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    return new ModelAndView("system/selftest/detail", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView deleteOne(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-SELFTEST-RECORDS-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String tid = request.getParameter("tid");
    int i = this.service.deleteUserTestDetail(tid);
    
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.paper.history.deleteone", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.paper.history.deleteone", "message.sys.failed" }));
    }
    modelMap.put("message", message);
    
    return new ModelAndView("common/message", modelMap);
  }
}

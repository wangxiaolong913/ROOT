package com.tom.user.action;

import com.tom.core.service.ICoreSystemService;
import com.tom.model.ModelHelper;
import com.tom.model.paper.Paper;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.user.service.IUserCollectionService;
import com.tom.user.service.IUserTestService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/user/selftest"})
public class UserTestController
  extends BaseController
{
  @Autowired
  private IUserTestService service;
  @Autowired
  private IUserCollectionService cservice;
  @Autowired
  private ICoreSystemService sysservice;
  
  @RequestMapping({"/new.thtml"})
  public ModelAndView newTest(HttpServletRequest request, ModelMap modelMap)
  {
    String sys_allow_test = this.sysservice.getConfigValue("sys_allow_test");
    modelMap.put("sys_allow_test", sys_allow_test);
    
    modelMap.put("qdbs", this.service.getAllQDBS());
    return new ModelAndView("user/selftest/new", modelMap);
  }
  
  @RequestMapping({"/newdetail.thtml"})
  public ModelAndView newTestDetail(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String ustatus = String.valueOf(session.getAttribute(Constants.SESSION_USERSTATUS));
    if (("forbidden".equals(BaseUtil.getSystemConfig("sys_allow_test"))) && (!"1".equals(ustatus)))
    {
      BaseMessage message = new BaseMessage();
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.history"), "user/paper/history.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.selfhistory"), "user/selftest/history.thtml"));
      
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.forbidden"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    Paper paper = this.service.buildTestPaper(request);
    modelMap.put("paper", paper);
    return new ModelAndView("user/selftest/newdetail", modelMap);
  }
  
  @RequestMapping({"/history.thtml"})
  public ModelAndView testHistory(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    Map<String, Object> params = new HashMap();
    params.put("t_uid", uid);
    
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    
    return new ModelAndView("user/selftest/history", modelMap);
  }
  
  @RequestMapping({"/historydetail.thtml"})
  public ModelAndView testHistoryDetail(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String id = request.getParameter("id");
    Map<String, Object> detail = this.service.getTestPaper(uid, id);
    modelMap.put("detail", detail);
    modelMap.put("categories", this.cservice.getAllCollectionType(uid));
    try
    {
      String spaper = String.valueOf(detail.get("t_paper"));
      String sanswer = String.valueOf(detail.get("t_answer"));
      String scheck = String.valueOf(detail.get("t_check"));
      Paper paper = (Paper)ModelHelper.convertObject(spaper);
      JSONObject answer = JSONObject.fromObject(sanswer);
      JSONObject check = JSONObject.fromObject(scheck);
      modelMap.put("paper", paper);
      modelMap.put("answer", answer);
      modelMap.put("check", check);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return new ModelAndView("user/selftest/historydetail", modelMap);
  }
  
  @RequestMapping({"/submit.do"})
  public ModelAndView submitTest(HttpServletRequest request, ModelMap modelMap)
  {
    int i = this.service.saveTestPaper(request);
    
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessage("message.user.paper.submit.ok"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessage("message.user.paper.submit.failed"));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.selftest"), "user/selftest/new.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.selfhistory"), "user/selftest/history.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView deleteTest(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String id = request.getParameter("id");
    int i = this.service.deleteTestPaper(uid, id);
    
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.user.selftest.delete", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.user.selftest.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.selftest"), "user/selftest/new.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.selfhistory"), "user/selftest/history.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

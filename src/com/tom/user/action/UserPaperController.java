package com.tom.user.action;

import com.tom.model.paper.Paper;
import com.tom.model.paper.PaperSection;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IPaperCategoryService;
import com.tom.user.service.IUserCollectionService;
import com.tom.user.service.IUserPaperService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
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
@RequestMapping({"/user/paper"})
public class UserPaperController
  extends BaseController
{
  @Autowired
  private IUserPaperService service;
  @Autowired
  private IUserCollectionService cservice;
  @Autowired
  private IPaperCategoryService categoryservice;
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String branchid = String.valueOf(session.getAttribute(Constants.SESSION_USERGID));
    
    Map<String, Object> params = new HashMap();
    params.put("branchid", branchid);
    params.put("uid", uid);
    params.put("p_name", BaseUtil.getChinese(request.getParameter("p_name")));
    params.put("p_cid", request.getParameter("p_cid"));
    
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.list(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
    
    return new ModelAndView("user/paper/list", modelMap);
  }
  
  @RequestMapping({"/paper_detail.thtml"})
  public ModelAndView paperDetail(HttpServletRequest request, ModelMap modelMap)
  {
    String pid = request.getParameter("pid");
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String ustatus = String.valueOf(session.getAttribute(Constants.SESSION_USERSTATUS));
    
    BaseMessage message = new BaseMessage();
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.paper"), "user/paper/list.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.history"), "user/paper/history.thtml"));
    if (("forbidden".equals(BaseUtil.getSystemConfig("sys_allow_exam"))) && (!"1".equals(ustatus)))
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.forbidden"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    Paper paper = this.service.getPaper(pid);
    if (paper == null)
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.paper_no_exist"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    if (1 != paper.getStatus())
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.closed"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    int paper_show_mode = paper.getShowMode();
    
    long nowtime = System.currentTimeMillis();
    if (paper.getEndtime().getTime() < nowtime)
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.hasfinished"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    if (paper.getStarttime().getTime() > nowtime)
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.notstart"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    boolean hasRight = false;
    String user_branchid = String.valueOf(session.getAttribute(Constants.SESSION_USERGID));
    if (paper.getDepartments() != null) {
      for (String branch : paper.getDepartments()) {
        if (user_branchid.equals(branch))
        {
          hasRight = true;
          break;
        }
      }
    }
    if (!hasRight)
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.noright"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    if ((paper.getPapertype() == 0) && (paper.getOrdertype() == 1)) {
      for (PaperSection section : paper.getSections()) {
        Collections.shuffle(section.getQuestions());
      }
    }
    if (1 == paper.getPapertype()) {
      paper = this.service.getPaper(uid, pid);
    }
    Map<String, Object> info = new HashMap();
    info.put("e_pid", pid);
    info.put("e_uid", uid);
    info.put("e_ip", getUserIP(request));
    int i = this.service.startExam(info);
    if (i == -1)
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.failed"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    if (i == 2)
    {
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.paper.start.hasjoined"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("paper", paper);
    if (paper_show_mode == 2) {
      return new ModelAndView("user/paper/paper_detail_singlemode", modelMap);
    }
    return new ModelAndView("user/paper/paper_detail", modelMap);
  }
  
  @RequestMapping({"/submitPaper.do"})
  public ModelAndView submitPaper(HttpServletRequest request, ModelMap modelMap)
  {
    BaseMessage message = null;
    int i = this.service.submitPaper(request);
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessage("message.user.paper.submit.ok"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessage("message.user.paper.submit.failed"));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.paper"), "user/paper/list.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.history"), "user/paper/history.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/history.thtml"})
  public ModelAndView history(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    Map<String, Object> params = new HashMap();
    params.put("e_uid", uid);
    params.put("p_name", BaseUtil.getChinese(request.getParameter("p_name")));
    params.put("p_cid", request.getParameter("p_cid"));
    
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.history(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
    
    return new ModelAndView("user/paper/history", modelMap);
  }
  
  @RequestMapping({"/history_detail.thtml"})
  public ModelAndView historyDetail(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String eid = request.getParameter("eid");
    String pid = request.getParameter("pid");
    
    Map<String, Object> detail = this.service.getHistoryDetail(uid, eid);
    modelMap.put("detail", detail);
    Paper paper_basic = this.service.getPaper(pid);
    Paper paper = this.service.getPaper(uid, pid);
    modelMap.put("paper_basic", paper_basic);
    modelMap.put("paper", paper);
    modelMap.put("categories", this.cservice.getAllCollectionType(uid));
    try
    {
      String sdata = String.valueOf(detail.get("e_data"));
      String scheck = String.valueOf(detail.get("e_check"));
      JSONObject data = JSONObject.fromObject(sdata);
      JSONObject check = JSONObject.fromObject(scheck);
      modelMap.put("data", data);
      modelMap.put("check", check);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return new ModelAndView("user/paper/history_detail", modelMap);
  }
  
  @RequestMapping({"/get_left_time.do"})
  public void doGetLeftTime(HttpServletRequest request, PrintWriter out)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String pid = request.getParameter("pid");
    
    int minutes = this.service.getUserPaperLeftTime(pid, uid);
    JSONObject json = new JSONObject();
    json.put("data", Integer.valueOf(minutes));
    
    out.write(json.toString());
  }
}

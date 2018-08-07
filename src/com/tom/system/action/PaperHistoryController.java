package com.tom.system.action;

import com.tom.model.paper.Paper;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IBranchService;
import com.tom.system.service.IPaperHistoryService;
import com.tom.system.service.IPaperService;
import com.tom.system.service.IUserService;
import com.tom.user.service.IUserPaperService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.io.PrintWriter;
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
@RequestMapping({"/system/paper/history"})
public class PaperHistoryController
  extends BaseController
{
  @Autowired
  private IPaperHistoryService service;
  @Autowired
  private IUserPaperService userPaperService;
  @Autowired
  private IUserService userService;
  @Autowired
  private IPaperService paperService;
  @Autowired
  private IBranchService branch_service;
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView history(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-PAPER-HISTORY-VIEW")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    String keywords = BaseUtil.getChinese(request.getParameter("keywords"));
    String orderby = request.getParameter("orderby");
    String pid = request.getParameter("pid");
    String branchid = request.getParameter("branchid");
    
    Map<String, Object> params = new HashMap();
    params.put("keywords", keywords);
    params.put("orderby", orderby);
    params.put("pid", pid);
    params.put("branchid", branchid);
    
    Pagination page = this.service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    
    modelMap.put("progress", this.service.getPaperCheckProgress(pid));
    
    Map<String, Object> paper = this.paperService.getPaper(pid);
    modelMap.put("paper", paper);
    
    modelMap.put("branches", this.branch_service.getAllBranchs());
    
    return new ModelAndView("system/paper/history/list", modelMap);
  }
  
  @RequestMapping({"/detail.thtml"})
  public ModelAndView historyDetail(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-PAPER-HISTORY-VIEW")) {
      return RedirectToNoPrivelegePage();
    }
    String uid = request.getParameter("uid");
    String pid = request.getParameter("pid");
    String eid = request.getParameter("eid");
    Map<String, Object> detail = this.service.getDetail(eid);
    Map<String, Object> user = this.userService.getUser(uid);
    
    Paper paper = this.userPaperService.getPaper(uid, pid);
    modelMap.put("detail", detail);
    modelMap.put("paper", paper);
    modelMap.put("user", user);
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
      
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.add"), "system/paper/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    return new ModelAndView("system/paper/history/detail", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView deleteOne(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-PAPER-HISTORY-REMOVE")) {
      return RedirectToNoPrivelegePage();
    }
    String eid = request.getParameter("eid");
    String pid = request.getParameter("pid");
    String uid = request.getParameter("uid");
    String nonav = request.getParameter("nonav");
    int i = this.service.deleteOneDetail(eid, pid, uid);
    
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.paper.history.deleteone", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.paper.history.deleteone", "message.sys.failed" }));
    }
    if (!"1".equals(nonav))
    {
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.history"), "system/paper/history/list.thtml?pid=" + pid));
    }
    modelMap.put("message", message);
    
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/clear.do"})
  public ModelAndView deleteAll(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-PAPER-HISTORY-REMOVE")) {
      return RedirectToNoPrivelegePage();
    }
    String pid = request.getParameter("pid");
    int i = this.service.deleteAllDetail(pid);
    
    BaseMessage message = null;
    if (i >= 0) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.paper.history.clear", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.paper.history.clear", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.history"), "system/paper/history/list.thtml?pid=" + pid));
    modelMap.put("message", message);
    
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/export.do"})
  public void doExport(HttpServletRequest request, PrintWriter out)
  {
    if (!HasPrivelege(request, "P-PAPER-HISTORY-VIEW"))
    {
      JSONObject json = new JSONObject();
      json.put("code", "err");
      json.put("data", "");
      out.write(json.toString());
      return;
    }
    String pid = request.getParameter("pid");
    String filename = this.service.exportHistory(pid);
    
    JSONObject json = new JSONObject();
    if (BaseUtil.isEmpty(filename))
    {
      json.put("code", "err");
      json.put("data", "");
    }
    else if ("-1".equals(filename))
    {
      json.put("code", "nodata");
      json.put("data", filename);
    }
    else
    {
      json.put("code", "ok");
      json.put("data", filename);
    }
    out.write(json.toString());
  }
  
  @RequestMapping({"/check.do"})
  public void doSetScore(HttpServletRequest request, PrintWriter out)
  {
    if (!HasPrivelege(request, "P-PAPER-HISTORY-CHECK"))
    {
      JSONObject json = new JSONObject();
      json.put("code", "err");
      out.write(json.toString());
      return;
    }
    String eid = request.getParameter("eid");
    String qid = request.getParameter("qid");
    String score = request.getParameter("score");
    int iscore = BaseUtil.getInt(score);
    
    int i = this.service.checkOneQuestion(eid, qid, iscore);
    JSONObject json = new JSONObject();
    json.put("code", i == 1 ? "ok" : "err");
    
    out.write(json.toString());
  }
}

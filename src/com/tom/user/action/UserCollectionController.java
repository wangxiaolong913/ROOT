package com.tom.user.action;

import com.tom.model.system.Pagination;
import com.tom.user.service.IUserCollectionService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.web.controller.BaseController;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping({"/user/collection"})
public class UserCollectionController
  extends BaseController
{
  @Autowired
  private IUserCollectionService service;
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    Map<String, Object> params = new HashMap();
    params.put("c_uid", uid);
    params.put("c_tid", request.getParameter("c_tid"));
    
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.queryCollection(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    modelMap.put("types", this.service.getAllCollectionType(uid));
    
    return new ModelAndView("user/collection/list", modelMap);
  }
  
  @RequestMapping({"/add.do"})
  public void doAddCollection(HttpServletRequest request, PrintWriter out)
  {
    int i = this.service.addCollection(getRequestData(request));
    JSONObject json = new JSONObject();
    String code = "";
    if (i == 1) {
      code = "ok";
    } else if (i == 2) {
      code = "has";
    } else {
      code = "err";
    }
    json.put("code", code);
    out.print(json.toString());
  }
  
  @RequestMapping({"/delete.do"})
  public void doDeleteCollection(HttpServletRequest request, PrintWriter out)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String id = request.getParameter("id");
    int i = this.service.deleteCollection(uid, id);
    
    JSONObject json = new JSONObject();
    json.put("code", i == 1 ? "ok" : "err");
    out.print(json.toString());
  }
  
  @RequestMapping({"/clear.do"})
  public void doClearCollection(HttpServletRequest request, PrintWriter out)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    int i = this.service.clearCollections(uid);
    
    JSONObject json = new JSONObject();
    json.put("code", i >= 0 ? "ok" : "err");
    out.print(json.toString());
  }
  
  @RequestMapping({"/types.thtml"})
  public ModelAndView doQueryCollectionTypes(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    List<Map<String, Object>> list = this.service.getAllCollectionType(uid);
    modelMap.put("types", list);
    return new ModelAndView("user/collection/types", modelMap);
  }
  
  @RequestMapping({"/addtype.do"})
  public void doAddCollectionType(HttpServletRequest request, PrintWriter out)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    Map<String, Object> params = getRequestData(request);
    params.put("t_uid", uid);
    params.put("t_id", BaseUtil.generateId());
    
    int i = this.service.addCollectionType(params);
    
    JSONObject json = new JSONObject();
    String msg = "err";
    if (i == 1) {
      msg = "ok";
    } else if (i == 2) {
      msg = "maxlimit";
    }
    json.put("code", msg);
    out.print(json.toString());
  }
  
  @RequestMapping({"/deletetype.do"})
  public void doDeleteCollectionType(HttpServletRequest request, PrintWriter out)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String id = request.getParameter("id");
    int i = this.service.deleteCollectionType(uid, id);
    
    String msg = "err";
    if (i == 1) {
      msg = "ok";
    } else if (i == 2) {
      msg = "hasdata";
    }
    JSONObject json = new JSONObject();
    json.put("code", msg);
    out.print(json.toString());
  }
  
  @RequestMapping({"/updatetype.do"})
  public void doUpdateCollectionType(HttpServletRequest request, PrintWriter out)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String id = request.getParameter("id");
    String t_name = request.getParameter("t_name");
    
    Map<String, Object> type = new HashMap();
    type.put("t_name", t_name);
    type.put("t_id", id);
    type.put("t_uid", uid);
    
    int i = this.service.updateCollectionType(type);
    
    String msg = "err";
    if (i == 1) {
      msg = "ok";
    }
    JSONObject json = new JSONObject();
    json.put("code", msg);
    out.print(json.toString());
  }
}

package com.tom.user.action;

import com.tom.core.util.SystemLoggerHelper;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.user.service.ICommonService;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.io.PrintWriter;
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
public class CommonController
  extends BaseController
{
  @Autowired
  private ICommonService service;
  
  @RequestMapping({"/login.thtml"})
  public ModelAndView login(HttpServletRequest request, ModelMap modelMap)
  {
    return new ModelAndView("login", modelMap);
  }
  
  @RequestMapping({"/index.thtml"})
  public ModelAndView index(HttpServletRequest request, ModelMap modelMap)
  {
    return new ModelAndView("index", modelMap);
  }
  
  @RequestMapping({"/common/head.thtml"})
  public ModelAndView head(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String usertype = String.valueOf(session.getAttribute(Constants.SESSION_USERTYPE));
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    if ("1".equals(usertype)) {
      modelMap.put("admin", this.service.getAdminProfile(uid));
    } else {
      modelMap.put("user", this.service.getUserProfile(uid));
    }
    return new ModelAndView("common/head", modelMap);
  }
  
  @RequestMapping({"/common/expired.thtml"})
  public ModelAndView statusExpired(HttpServletRequest request, ModelMap modelMap)
  {
    return new ModelAndView("common/message_expired", modelMap);
  }
  
  @RequestMapping({"/common/menu.thtml"})
  public ModelAndView menu(HttpServletRequest request, ModelMap modelMap)
  {
    return new ModelAndView("common/menu", modelMap);
  }
  
  @RequestMapping({"/common/welcome.thtml"})
  public ModelAndView main(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String usertype = String.valueOf(session.getAttribute(Constants.SESSION_USERTYPE));
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String branchid = String.valueOf(session.getAttribute(Constants.SESSION_USERGID));
    
    modelMap.put("newslist", this.service.getWelcomeNewsList(5));
    if ("1".equals(usertype))
    {
      modelMap.put("sysparams", this.service.getWelcomeStatisticsParams());
      
      String regs = (String)CacheHelper.getCache("ConfigCache", "register");
      modelMap.put("register", regs);
    }
    else
    {
      modelMap.put("sysexams", this.service.getWelcomeExamList(branchid, uid, 5));
      modelMap.put("sysexamhistory", this.service.getWelcomeExamHistoryList(branchid, uid, 5));
    }
    return new ModelAndView("common/welcome", modelMap);
  }
  
  @RequestMapping({"/common/about.thtml"})
  public ModelAndView about(HttpServletRequest request, ModelMap modelMap)
  {
    modelMap.put("systemid", Constants.getSystemId());
    
    modelMap.put("auth_users", Integer.valueOf(200));
    modelMap.put("total_users", Integer.valueOf(this.service.getTotalUsers()));
    
    return new ModelAndView("common/about", modelMap);
  }
  
  @RequestMapping({"/news/list.thtml"})
  public ModelAndView newsList(HttpServletRequest request, ModelMap modelMap)
  {
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    Pagination page = this.service.getNewsList(getRequestData(request), BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    modelMap.put("categories", this.service.getAllNewsCategories());
    return new ModelAndView("/common/news/list", modelMap);
  }
  
  @RequestMapping({"/news/detail.thtml"})
  public ModelAndView newsDetail(HttpServletRequest request, ModelMap modelMap)
  {
    String id = request.getParameter("id");
    Map<String, Object> news = this.service.getNews(id);
    if (news == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.paper"), "user/paper/list.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.history"), "user/paper/history.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("news", news);
    return new ModelAndView("/common/news/detail", modelMap);
  }
  
  @RequestMapping({"/vote/list.thtml"})
  public ModelAndView voteList(HttpServletRequest request, ModelMap modelMap)
  {
    return new ModelAndView("/common/vote/list", modelMap);
  }
  
  @RequestMapping({"/vote/detail.thtml"})
  public ModelAndView voteDetail(HttpServletRequest request, ModelMap modelMap)
  {
    return new ModelAndView("/common/vote/detail", modelMap);
  }
  
  @RequestMapping({"/common/login.do"})
  public void doLogin(HttpServletRequest request, PrintWriter out)
  {
    String username = request.getParameter("username");
    String usertype = request.getParameter("usertype");
    String userpass = request.getParameter("userpass");
    String usercode = request.getParameter("usercode");
    HttpSession session = request.getSession();
    String sessionCode = String.valueOf(session.getAttribute("tm_randcode"));
    JSONObject json = new JSONObject();
    String ip = getUserIP(request);
    if ("0".equals(usertype))
    {
      if ("forbidden".equals(BaseUtil.getSystemConfig("sys_allow_login")))
      {
        json.put("code", Integer.valueOf(-4));
        json.put("message", MessageHelper.getMessage("message.sys.login.forbidden"));
        out.write(json.toString());
        return;
      }
      String userip = getUserIP(request);
      String access_ips = "IP:" + BaseUtil.getSystemConfig("sys_access_ips");
      if (("whitelist".equals(BaseUtil.getSystemConfig("sys_access_control"))) && 
        (!access_ips.contains(userip)))
      {
        json.put("code", Integer.valueOf(-7));
        json.put("message", MessageHelper.getMessage("message.user.paper.start.notallowed"));
        out.write(json.toString());
        return;
      }
      if (("blacklist".equals(BaseUtil.getSystemConfig("sys_access_control"))) && 
        (access_ips.contains(userip)))
      {
        json.put("code", Integer.valueOf(-8));
        json.put("message", MessageHelper.getMessage("message.user.paper.start.notallowed"));
        out.write(json.toString());
        return;
      }
    }
    if (BaseUtil.isEmpty(sessionCode))
    {
      json.put("code", Integer.valueOf(-5));
      json.put("message", MessageHelper.getMessage("message.sys.login.sessioncode_lost"));
      out.write(json.toString());
      
      SystemLoggerHelper.Log(BaseUtil.getInt(usertype), username, "doLogin", request.getRequestURI(), "SESSION����������(session_code_lost)", ip);
      return;
    }
    if (!sessionCode.equals(usercode))
    {
      json.put("code", Integer.valueOf(-6));
      json.put("message", MessageHelper.getMessage("message.sys.login.usercode_wrong"));
      out.write(json.toString());
      
      SystemLoggerHelper.Log(BaseUtil.getInt(usertype), username, "doLogin", request.getRequestURI(), "��������������(verify_code_wrong)", ip);
      return;
    }
    Map<String, Object> map = this.service.doLogin(usertype, username, userpass);
    String scode = String.valueOf(map.get("code"));
    String msgkey = String.valueOf(map.get("msgkey"));
    int code = BaseUtil.getInt(scode);
    if (code == 1)
    {
      Object object = map.get("data");
      if (object != null)
      {
        Map<String, Object> user = (Map)object;
        
        String sessionid = BaseUtil.generateRandomString(20);
        String uid = String.valueOf(user.get("user_id"));
        
        session.setAttribute(Constants.SESSION_USERID, uid);
        session.setAttribute(Constants.SESSION_USERNAME, String.valueOf(user.get("user_name")));
        session.setAttribute(Constants.SESSION_USERTYPE, String.valueOf(user.get("user_type")));
        session.setAttribute(Constants.SESSION_USERGID, String.valueOf(user.get("user_gid")));
        session.setAttribute(Constants.SESSION_SESSID, sessionid);
        session.setAttribute(Constants.SESSION_USERSTATUS, String.valueOf(user.get("user_status")));
        
        CacheHelper.addCache("SessionCache", "U" + uid, sessionid);
        
        SystemLoggerHelper.Log(BaseUtil.getInt(usertype), username, "doLogin", request.getRequestURI(), "��������(login_success)", ip);
      }
      else
      {
        code = -9;
      }
    }
    else
    {
      SystemLoggerHelper.Log(BaseUtil.getInt(usertype), username, "doLogin", request.getRequestURI(), "��������(login_failed),username=" + username, ip);
    }
    json.put("code", Integer.valueOf(code));
    json.put("message", MessageHelper.getMessage("message.sys.login." + msgkey));
    out.write(json.toString());
  }
  
  @RequestMapping({"/common/logout.do"})
  public void doLogout(HttpServletRequest request, PrintWriter out)
  {
    HttpSession session = request.getSession();
    
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String username = String.valueOf(session.getAttribute(Constants.SESSION_USERNAME));
    String utype = String.valueOf(session.getAttribute(Constants.SESSION_USERTYPE));
    
    SystemLoggerHelper.Log(BaseUtil.getInt(utype), username, "doLogout", request.getRequestURI(), "��������(logout_success)", getUserIP(request));
    
    session.setAttribute(Constants.SESSION_USERID, null);
    session.setAttribute(Constants.SESSION_USERNAME, null);
    session.setAttribute(Constants.SESSION_USERTYPE, null);
    session.setAttribute(Constants.SESSION_USERGID, null);
    session.setAttribute(Constants.SESSION_SESSID, null);
    session.setAttribute(Constants.SESSION_USERSTATUS, null);
    CacheHelper.removeCache("SessionCache", "U" + uid);
    
    JSONObject json = new JSONObject();
    json.put("code", "y");
    out.write(json.toString());
  }
  
  @RequestMapping({"/common/relogin.do"})
  public ModelAndView doRelogin(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String username = String.valueOf(session.getAttribute(Constants.SESSION_USERNAME));
    String utype = String.valueOf(session.getAttribute(Constants.SESSION_USERTYPE));
    
    SystemLoggerHelper.Log(BaseUtil.getInt(utype), username, "doLogout", request.getRequestURI(), "��������(logout_success)", getUserIP(request));
    
    session.setAttribute(Constants.SESSION_USERID, null);
    session.setAttribute(Constants.SESSION_USERNAME, null);
    session.setAttribute(Constants.SESSION_USERTYPE, null);
    session.setAttribute(Constants.SESSION_USERGID, null);
    return new ModelAndView("login", modelMap);
  }
  
  @RequestMapping({"/user/profile.thtml"})
  public ModelAndView userProfile(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    Map<String, Object> user = this.service.getUserProfile(uid);
    if (user == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.other.account_not_exsit"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.other.account.relogin"), "common/relogin.do", true));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("user", user);
    return new ModelAndView("user/profile/profile", modelMap);
  }
  
  @RequestMapping({"/user/profile/update.do"})
  public ModelAndView updateUserProfile(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String username = String.valueOf(session.getAttribute(Constants.SESSION_USERNAME));
    
    Map<String, Object> user = getRequestData(request);
    user.put("u_id", uid);
    int i = this.service.updateUserProfile(user);
    
    SystemLoggerHelper.Log(0, username, "updateProfile", request.getRequestURI(), "������������(update_user_profile),result=" + i, getUserIP(request));
    
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.user.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.user.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.other.account.relogin"), "common/relogin.do", true));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.profile"), "user/profile.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/system/profile.thtml"})
  public ModelAndView adminProfile(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String aid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    Map<String, Object> admin = this.service.getAdminProfile(aid);
    if (admin == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.other.account_not_exsit"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.other.account.relogin"), "common/relogin.do", true));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("admin", admin);
    return new ModelAndView("system/profile/profile", modelMap);
  }
  
  @RequestMapping({"/system/profile/update.do"})
  public ModelAndView updateAdminProfile(HttpServletRequest request, ModelMap modelMap)
  {
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    String username = String.valueOf(session.getAttribute(Constants.SESSION_USERNAME));
    
    Map<String, Object> admin = getRequestData(request);
    admin.put("a_id", uid);
    int i = this.service.updateAdminProfile(admin);
    
    SystemLoggerHelper.Log(1, username, "updateProfile", request.getRequestURI(), "��������������(update_admin_profile),result=" + i, getUserIP(request));
    
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.user.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.user.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.other.account.relogin"), "common/relogin.do", true));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.profile"), "system/profile.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

package com.tom.user.action;

import com.tom.model.course.Course;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.user.service.IUCourseService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.io.PrintWriter;
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
@RequestMapping({"/user/course"})
public class UserCourseController
  extends BaseController
{
  @Autowired
  private IUCourseService service;
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView courseList(HttpServletRequest request, ModelMap modelMap)
  {
    modelMap.put("categories", this.service.getAllCategories());
    modelMap.put("teachers", this.service.getAllTeachers());
    
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Map<String, Object> params = getRequestData(request);
    params.put("c_name", BaseUtil.getChinese(request.getParameter("c_name")));
    
    Pagination page = this.service.queryCourse(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("user/course/list", modelMap);
  }
  
  @RequestMapping({"/detail.thtml"})
  public ModelAndView courseDetail(HttpServletRequest request, ModelMap modelMap)
  {
    String id = request.getParameter("id");
    
    Course course = this.service.getCourse(id);
    if (course == null)
    {
      BaseMessage message = new BaseMessage();
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.learn.list"), "user/course/list.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.user.learn.my"), "user/course/my.thtml"));
      
      message.setSuccess(false);
      message.setMessage(MessageHelper.getMessage("message.user.course.notexsit"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("course", course);
    
    Map<String, Object> teacher = this.service.getTeacher(course.getTeacherid());
    modelMap.put("teacher", teacher);
    
    return new ModelAndView("user/course/detail", modelMap);
  }
  
  @RequestMapping({"/coursemarker.do"})
  public void doMarkCourseProgress(HttpServletRequest request, PrintWriter out)
  {
    String courseid = request.getParameter("id");
    String chapterid = request.getParameter("chapterid");
    String lessonid = request.getParameter("lessonid");
    
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    JSONObject json = new JSONObject();
    int i = this.service.markCourseProgress(uid, courseid, chapterid, lessonid);
    if (i == 1) {
      json.put("code", Integer.valueOf(1));
    } else {
      json.put("code", Integer.valueOf(0));
    }
    out.write(json.toString());
  }
  
  @RequestMapping({"/my.thtml"})
  public ModelAndView myCourseList(HttpServletRequest request, ModelMap modelMap)
  {
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    HttpSession session = request.getSession();
    String uid = String.valueOf(session.getAttribute(Constants.SESSION_USERID));
    
    Map<String, Object> params = new HashMap();
    params.put("p_uid", uid);
    
    Pagination page = this.service.myCourseStudyHistory(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("user/course/my", modelMap);
  }
}

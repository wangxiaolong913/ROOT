package com.tom.system.action;

import com.tom.model.ModelHelper;
import com.tom.model.course.Course;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.ICourseCategoryService;
import com.tom.system.service.ICourseService;
import com.tom.system.service.ICourseTeacherService;
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
@RequestMapping({"/system/course"})
public class CourseController
  extends BaseController
{
  @Autowired
  private ICourseService service;
  @Autowired
  private ICourseCategoryService category_service;
  @Autowired
  private ICourseTeacherService teacher_service;
  
  @RequestMapping({"/add.thtml"})
  public ModelAndView add(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    modelMap.put("categories", this.category_service.getAllCategories());
    modelMap.put("teachers", this.teacher_service.getAllTeachers());
    return new ModelAndView("system/course/add", modelMap);
  }
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-COURSE")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.queryCourse(getRequestData(request), BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("categories", this.category_service.getAllCategories());
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/course/list", modelMap);
  }
  
  @RequestMapping({"/load.thtml"})
  public ModelAndView load(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    Map<String, Object> course = this.service.getCourse(id);
    if (course == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.add"), "system/course/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.list"), "system/course/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    try
    {
      Course coursex = (Course)ModelHelper.convertObject(String.valueOf(course.get("c_data")));
      modelMap.put("coursex", coursex);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    modelMap.put("categories", this.category_service.getAllCategories());
    modelMap.put("course", course);
    modelMap.put("teachers", this.teacher_service.getAllTeachers());
    return new ModelAndView("system/course/load", modelMap);
  }
  
  @RequestMapping({"/save.do"})
  public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.addCourse(request);
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.add", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.add"), "system/course/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.list"), "system/course/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    int i = this.service.deleteCourse(id);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.delete", "message.sys.success" }));
    } else if (i == 2) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.course.delete.hasusers"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.add"), "system/course/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.list"), "system/course/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.updateCourse(request);
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.add"), "system/course/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.list"), "system/course/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/learnrecord.thtml"})
  public ModelAndView learnRecord(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-COURSE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    Map<String, Object> params = new HashMap();
    params.put("p_cid", id);
    
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    Pagination page = this.service.queryCourseLearnRecord(params, 10, BaseUtil.getInt(epage));
    page.setChangesize(false);
    
    Map<String, Object> course = this.service.getCourse(id);
    modelMap.put("course", course);
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/course/learnrecord", modelMap);
  }
  
  @RequestMapping({"/clear_learnrecord.do"})
  public void clearLearnRecord(HttpServletRequest request, PrintWriter out)
  {
    if (!HasPrivelege(request, "P-COURSE-DELETE"))
    {
      JSONObject ret = new JSONObject();
      ret.put("code", "no_right");
      ret.put("msg", MessageHelper.getMessage("message.other.no_right"));
      out.write(ret.toString());
      return;
    }
    String cid = request.getParameter("cid");
    
    JSONObject json = new JSONObject();
    if (BaseUtil.isEmpty(cid))
    {
      json.put("code", "err");
      json.put("msg", MessageHelper.getMessage("message.system.param_lost"));
      out.write(json.toString());
      return;
    }
    int rows = this.service.clearLearnRecord(cid);
    if (rows >= 0)
    {
      json.put("code", "ok");
      json.put("msg", MessageHelper.getMessage("message.other.op_ok"));
    }
    else
    {
      json.put("code", "err");
      json.put("msg", MessageHelper.getMessage("message.other.op_failed"));
    }
    out.write(json.toString());
  }
}

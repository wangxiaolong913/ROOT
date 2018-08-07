package com.tom.system.action;

import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.ICourseTeacherService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/course/teacher"})
public class CourseTeacherController
  extends BaseController
{
  @Autowired
  private ICourseTeacherService service;
  
  @RequestMapping({"/add.thtml"})
  public ModelAndView add(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-TEACHER-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    return new ModelAndView("system/course/teacher/add", modelMap);
  }
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-COURSE-TEACHER")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.queryTeacher(getRequestData(request), BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/course/teacher/list", modelMap);
  }
  
  @RequestMapping({"/load.thtml"})
  public ModelAndView load(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-TEACHER-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    Map<String, Object> teacher = this.service.getTeacher(id);
    if (teacher == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.add"), "system/course/teacher/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.list"), "system/course/teacher/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("teacher", teacher);
    return new ModelAndView("system/course/teacher/load", modelMap);
  }
  
  @RequestMapping({"/save.do"})
  public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-TEACHER-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.addTeacher(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.teacher.add", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.teacher.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.add"), "system/course/teacher/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.list"), "system/course/teacher/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-TEACHER-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    int i = this.service.deleteTeacher(id);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.teacher.delete", "message.sys.success" }));
    } else if (i == 2) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.course.teacher.delete.hascourse"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.teacher.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.add"), "system/course/teacher/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.list"), "system/course/teacher/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-COURSE-TEACHER-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.updateTeacher(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.course.teacher.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.course.teacher.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.add"), "system/course/teacher/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.course.teacher.list"), "system/course/teacher/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

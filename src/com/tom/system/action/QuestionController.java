package com.tom.system.action;

import com.tom.model.ModelHelper;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IQuestionDBService;
import com.tom.system.service.IQuestionService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/question"})
public class QuestionController
  extends BaseController
{
  @Autowired
  private IQuestionService service;
  @Autowired
  private IQuestionDBService dbservice;
  
  @RequestMapping({"/add.thtml"})
  public ModelAndView add(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    modelMap.put("qdbs", this.dbservice.getAllDBS());
    return new ModelAndView("system/question/add", modelMap);
  }
  
  @RequestMapping({"/import.thtml"})
  public ModelAndView importx(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-IMPORT")) {
      return RedirectToNoPrivelegePage();
    }
    modelMap.put("qdbs", this.dbservice.getAllDBS());
    return new ModelAndView("system/question/import", modelMap);
  }
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-QES")) {
      return RedirectToNoPrivelegePage();
    }
    Map<String, Object> params = getRequestData(request);
    if ((params != null) && (BaseUtil.isNotEmpty(request.getParameter("q_content")))) {
      params.put("q_content", BaseUtil.getChinese(request.getParameter("q_content")));
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    modelMap.put("qdbs", this.dbservice.getAllDBS());
    return new ModelAndView("system/question/list", modelMap);
  }
  
  @RequestMapping({"/query.thtml"})
  public void listx(HttpServletRequest request, PrintWriter out)
  {
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Map<String, Object> params = getRequestData(request);
    if ((params != null) && (BaseUtil.isNotEmpty(request.getParameter("q_content")))) {
      params.put("q_content", BaseUtil.getChinese(request.getParameter("q_content")));
    }
    Pagination page = this.service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    JSONObject json = new JSONObject();
    json.put("code", page == null ? "err" : "ok");
    json.put("data", page == null ? "" : page);
    out.print(json.toString());
  }
  
  @RequestMapping({"/load.thtml"})
  public ModelAndView load(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String qid = request.getParameter("qid");
    Map<String, Object> question = this.service.getQuestion(qid);
    if (question == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.add"), "system/question/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.list"), "system/question/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("question", question);
    Object ques = ModelHelper.convertObject(String.valueOf(question.get("q_data")));
    JSONObject json = JSONObject.fromObject(ques);
    modelMap.put("json", json);
    modelMap.put("qdbs", this.dbservice.getAllDBS());
    return new ModelAndView("system/question/load", modelMap);
  }
  
  @RequestMapping({"/save.do"})
  public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.addQuestion(request);
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.question.add", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.question.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.add"), "system/question/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.list"), "system/question/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/batchImportTxt.do"})
  public ModelAndView batchImportTxt(HttpServletRequest request, @RequestParam(value="q_file", required=false) MultipartFile file, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-IMPORT")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    
    int rows = this.service.importQuestionsFromTxt(file, request);
    if (rows > 0) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.question.import", "message.sys.success" }) + " : " + rows);
    } else if (rows == -1) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.question.emptyfie"));
    } else if (rows == -9) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.question.formaterror"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.question.import", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.add"), "system/question/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.list"), "system/question/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/batchImportExcel.do"})
  public ModelAndView batchImportExcel(HttpServletRequest request, @RequestParam(value="q_file", required=false) MultipartFile file, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-IMPORT")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    
    int rows = this.service.importQuestionsFromExcel(file, request);
    if (rows > 0) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.question.import", "message.sys.success" }) + " : " + rows);
    } else if (rows == 0) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.question.emptyfie"));
    } else if (rows == -9) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.question.formaterror"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.question.import", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.add"), "system/question/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.list"), "system/question/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String qid = request.getParameter("qid");
    int i = this.service.deleteQuestion(qid);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.question.delete", "message.sys.success" }));
    } else if (i == 9) {
      message = new BaseMessage(false, MessageHelper.getMessage("message.sys.question.hasused"));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.question.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.add"), "system/question/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.list"), "system/question/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QES-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.updateQuestion(request);
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages(" ", new String[] { "message.sys.question.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages(" ", new String[] { "message.sys.question.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.add"), "system/question/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.question.list"), "system/question/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
}

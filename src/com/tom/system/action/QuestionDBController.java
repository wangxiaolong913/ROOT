package com.tom.system.action;

import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IQuestionDBService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/qdb"})
public class QuestionDBController
  extends BaseController
{
  @Autowired
  private IQuestionDBService service;
  
  @RequestMapping({"/add.thtml"})
  public ModelAndView add(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QDB-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    return new ModelAndView("system/qdb/add", modelMap);
  }
  
  @RequestMapping({"/list.thtml"})
  public ModelAndView list(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-QDB")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Pagination page = this.service.query(getRequestData(request), BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    return new ModelAndView("system/qdb/list", modelMap);
  }
  
  @RequestMapping({"/load.thtml"})
  public ModelAndView load(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QDB-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    Map<String, Object> qdb = this.service.getQuestionDB(id);
    if (qdb == null)
    {
      BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.add"), "system/qdb/add.thtml"));
      message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.list"), "system/qdb/list.thtml"));
      modelMap.put("message", message);
      return new ModelAndView("common/message", modelMap);
    }
    modelMap.put("qdb", qdb);
    return new ModelAndView("system/qdb/load", modelMap);
  }
  
  @RequestMapping({"/save.do"})
  public ModelAndView save(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QDB-ADD")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.addQuestionDB(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages("", new String[] { "message.sys.qdb.add", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages("", new String[] { "message.sys.qdb.add", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.add"), "system/qdb/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.list"), "system/qdb/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/delete.do"})
  public ModelAndView delete(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QDB-DELETE")) {
      return RedirectToNoPrivelegePage();
    }
    String id = request.getParameter("id");
    int i = this.service.deleteQuestionDB(id);
    BaseMessage message = null;
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages("", new String[] { "message.sys.qdb.delete", "message.sys.success" }));
    } else if (i == 2) {
      message = new BaseMessage(false, MessageHelper.getMessages("", new String[] { "message.sys.qdb.hasQuestions" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages("", new String[] { "message.sys.qdb.delete", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.add"), "system/qdb/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.list"), "system/qdb/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/update.do"})
  public ModelAndView update(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-QDB-UPDATE")) {
      return RedirectToNoPrivelegePage();
    }
    BaseMessage message = null;
    int i = this.service.updateQuestionDB(getRequestData(request));
    if (i == 1) {
      message = new BaseMessage(true, MessageHelper.getMessages("", new String[] { "message.sys.qdb.update", "message.sys.success" }));
    } else {
      message = new BaseMessage(false, MessageHelper.getMessages("", new String[] { "message.sys.qdb.update", "message.sys.failed" }));
    }
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.add"), "system/qdb/add.thtml"));
    message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.qdb.list"), "system/qdb/list.thtml"));
    modelMap.put("message", message);
    return new ModelAndView("common/message", modelMap);
  }
  
  @RequestMapping({"/analyse.thtml"})
  public ModelAndView analyse(HttpServletRequest request, ModelMap modelMap)
  {
    String id = request.getParameter("id");
    
    Map<String, Object> qdb = this.service.getQuestionDB(id);
    if (qdb != null)
    {
      Map<String, Object> map = this.service.AnalyseQuestionDB(id);
      if (map != null)
      {
        Object ls = map.get("info_question");
        try
        {
          JSONArray array = JSONArray.fromObject(ls);
          modelMap.put("info_question", array.size() < 1 ? null : array.toString());
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      modelMap.put("qdb", qdb);
    }
    return new ModelAndView("system/qdb/analyse", modelMap);
  }
}

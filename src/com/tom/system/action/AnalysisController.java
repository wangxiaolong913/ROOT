package com.tom.system.action;

import com.tom.core.service.ICorePaperService;
import com.tom.model.system.Pagination;
import com.tom.system.service.IAnalysisService;
import com.tom.system.service.IPaperCategoryService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/system/analysis"})
public class AnalysisController
  extends BaseController
{
  @Autowired
  private IAnalysisService service;
  @Autowired
  private ICorePaperService paperservice;
  @Autowired
  private IPaperCategoryService categoryservice;
  
  @RequestMapping({"/paperlist.thtml"})
  public ModelAndView paperlist(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "F-ANALYSIS")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    Pagination page = this.service.queryPaper(getRequestData(request), 10, BaseUtil.getInt(epage));
    page.setChangesize(false);
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
    return new ModelAndView("system/analysis/paperlist", modelMap);
  }
  
  @RequestMapping({"/paper/index.thtml"})
  public ModelAndView paper(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ANA-PAPER")) {
      return RedirectToNoPrivelegePage();
    }
    String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
    String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");
    
    Map<String, Object> params = getRequestData(request);
    params.put("p_name", BaseUtil.getChinese(request.getParameter("p_name")));
    
    Pagination page = this.service.AnalyzePaperList(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
    modelMap.put("page", page);
    modelMap.put("foot", page.getNavFoot(request));
    modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
    return new ModelAndView("system/analysis/paper/index", modelMap);
  }
  
  @RequestMapping({"/paper/detail.thtml"})
  public ModelAndView paperDetail(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ANA-PAPER")) {
      return RedirectToNoPrivelegePage();
    }
    String pid = request.getParameter("pid");
    modelMap.put("paper", this.paperservice.getPaper(pid));
    modelMap.put("detail", this.service.AnalyzePaperDetail(pid));
    return new ModelAndView("system/analysis/paper/detail", modelMap);
  }
  
  @RequestMapping({"/exam/index.thtml"})
  public ModelAndView exam(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ANA-EXAM")) {
      return RedirectToNoPrivelegePage();
    }
    return new ModelAndView("system/analysis/exam/index", modelMap);
  }
  
  @RequestMapping({"/exam/analyse.do"})
  public void examAnalyse(HttpServletRequest request, PrintWriter out)
  {
    if (!HasPrivelege(request, "P-ANA-EXAM"))
    {
      JSONObject json = new JSONObject();
      json.put("code", "err");
      json.put("message", "");
      return;
    }
    String pid = request.getParameter("pid");
    JSONObject json = new JSONObject();
    Map<String, Object> map = this.service.AnalyzeExam(pid);
    if (map == null)
    {
      json.put("code", "err");
      json.put("message", "");
    }
    else
    {
      json.put("code", "ok");
      json.put("message", "");
      json.put("data", map);
    }
    out.write(json.toString());
  }
  
  @RequestMapping({"/score/index.thtml"})
  public ModelAndView score(HttpServletRequest request, ModelMap modelMap)
  {
    if (!HasPrivelege(request, "P-ANA-SCORE")) {
      return RedirectToNoPrivelegePage();
    }
    return new ModelAndView("system/analysis/score/index", modelMap);
  }
  
  @RequestMapping({"/score/analyse.do"})
  public void scoreAnalyse(HttpServletRequest request, PrintWriter out)
  {
    if (!HasPrivelege(request, "P-ANA-SCORE"))
    {
      JSONObject json = new JSONObject();
      json.put("code", "err");
      json.put("message", "");
      return;
    }
    String pid = request.getParameter("pid");
    String[] from = request.getParameterValues("s_from");
    String[] to = request.getParameterValues("s_to");
    JSONObject json = new JSONObject();
    List<Map<String, Object>> list = this.service.AnalyzeScore(from, to, pid);
    if (list == null)
    {
      json.put("code", "err");
      json.put("message", "");
    }
    else
    {
      json.put("code", "ok");
      json.put("message", "");
      json.put("data", list);
    }
    out.write(json.toString());
  }
}

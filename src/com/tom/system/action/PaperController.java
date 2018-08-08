package com.tom.system.action;

import com.tom.model.ModelHelper;
import com.tom.model.paper.Paper;
import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IBranchService;
import com.tom.system.service.IPaperCategoryService;
import com.tom.system.service.IPaperService;
import com.tom.system.service.IQuestionDBService;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/system/paper" })
public class PaperController extends BaseController {
	@Autowired
	private IPaperService service;
	@Autowired
	private IQuestionDBService dbservice;
	@Autowired
	private IBranchService branchservice;
	@Autowired
	private IPaperCategoryService categoryservice;

	@RequestMapping({ "/add.thtml" })
	public ModelAndView add(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-ADD")) {
			return RedirectToNoPrivelegePage();
		}
		modelMap.put("branches", this.branchservice.getAllBranchs());
		modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
		return new ModelAndView("system/paper/add", modelMap);
	}

	@RequestMapping({ "/fastadd.thtml" })
	public ModelAndView fastadd(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-ADD")) {
			return RedirectToNoPrivelegePage();
		}
		modelMap.put("branches", this.branchservice.getAllBranchs());
		modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
		modelMap.put("qdbs", this.dbservice.getAllDBS());
		return new ModelAndView("system/paper/fastadd", modelMap);
	}

	@RequestMapping({ "/list.thtml" })
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "F-PAPER")) {
			return RedirectToNoPrivelegePage();
		}
		String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
		String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");

		Map<String, Object> params = getRequestData(request);
		params.put("p_name", BaseUtil.getChinese(request.getParameter("p_name")));

		Pagination page = this.service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
		modelMap.put("page", page);
		modelMap.put("foot", page.getNavFoot(request));
		modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
		return new ModelAndView("system/paper/list", modelMap);
	}

	@RequestMapping({ "/load.thtml" })
	public ModelAndView load(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-UPDATE")) {
			return RedirectToNoPrivelegePage();
		}
		String pid = request.getParameter("pid");
		Map<String, Object> paper = this.service.getPaper(pid);
		if (paper == null) {
			BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
			message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.add"), "system/paper/add.thtml"));
			message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
			modelMap.put("message", message);
			return new ModelAndView("common/message", modelMap);
		}
		modelMap.put("paper", paper);
		modelMap.put("branches", this.branchservice.getAllBranchs());
		modelMap.put("categorys", this.categoryservice.getAllPaperCategorys());
		modelMap.put("links", this.service.getPaperLink(pid));
		return new ModelAndView("system/paper/load", modelMap);
	}

	@RequestMapping({ "/detail.thtml" })
	public ModelAndView loadDetail(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-UPDATE")) {
			return RedirectToNoPrivelegePage();
		}
		String pid = request.getParameter("pid");
		Map<String, Object> paper = this.service.getPaper(pid);
		if (paper == null) {
			BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
			message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.add"), "system/paper/add.thtml"));
			message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
			modelMap.put("message", message);
			return new ModelAndView("common/message", modelMap);
		}
		modelMap.put("paper", paper);
		Paper paperx = (Paper) ModelHelper.convertObject(String.valueOf(paper.get("p_data")));
		modelMap.put("paperx", paperx);
		modelMap.put("qdbs", this.dbservice.getAllDBS());
		modelMap.put("links", this.service.getPaperLink(pid));
		String pType = String.valueOf(paper.get("p_papertype"));
		if ("0".equals(pType)) {
			return new ModelAndView("system/paper/detail_normal", modelMap);
		}
		return new ModelAndView("system/paper/detail_random", modelMap);
	}

	@RequestMapping({ "/save.do" })
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-ADD")) {
			return RedirectToNoPrivelegePage();
		}
		BaseMessage message = null;
		int i = this.service.addPaper(getRequestData(request));
		if (i == 1) {
			message = new BaseMessage(true,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.add", "message.sys.success" }));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.add", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.add"), "system/paper/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/fastsave.do" })
	public ModelAndView fastsave(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-ADD")) {
			return RedirectToNoPrivelegePage();
		}
		BaseMessage message = null;
		int i = this.service.fastAddPaper(request);
		if (i == 1) {
			message = new BaseMessage(true, MessageHelper.getMessages(" ",
					new String[] { "message.sys.paper.fastadd", "message.sys.success" }));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.fastadd", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.fastadd"), "system/paper/fastadd.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/delete.do" })
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-DELETE")) {
			return RedirectToNoPrivelegePage();
		}
		String pid = request.getParameter("pid");
		int i = this.service.deletePaper(pid);
		BaseMessage message = null;
		if (i == 1) {
			message = new BaseMessage(true,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.delete", "message.sys.success" }));
		} else if (i == 2) {
			message = new BaseMessage(false, MessageHelper.getMessage("message.sys.paper.hasUsers"));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.delete", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.add"), "system/paper/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/update.do" })
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-PAPER-UPDATE")) {
			return RedirectToNoPrivelegePage();
		}
		BaseMessage message = null;
		int i = this.service.updatePaper(getRequestData(request));
		if (i == 1) {
			message = new BaseMessage(true,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.update", "message.sys.success" }));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.update", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.add"), "system/paper/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/updateDetail.do" })
	public ModelAndView updateDetail(HttpServletRequest request, ModelMap modelMap) {

		if (!HasPrivelege(request, "P-PAPER-UPDATE")) {
			return RedirectToNoPrivelegePage();
		}
		BaseMessage message = null;
		int i = this.service.updatePaperDetail(request);
		if (i == 1) {
			message = new BaseMessage(true,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.update", "message.sys.success" }));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.paper.update", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.add"), "system/paper/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.paper.list"), "system/paper/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/export-word.do" })
	public void exportWord(HttpServletRequest request, PrintWriter out) {
		if (!HasPrivelege(request, "P-PAPER-EXPORTWORD")) {
			JSONObject ret = new JSONObject();
			ret.put("code", "no_right");
			ret.put("data", MessageHelper.getMessage("message.other.no_right"));
			out.write(ret.toString());
			return;
		}
		String pid = BaseUtil.convertEmptyToSome(request.getParameter("pid"), "1");

		String filename = this.service.doExportPaperAsWord(pid);
		JSONObject json = new JSONObject();
		if (BaseUtil.isEmpty(filename)) {
			json.put("code", "err");
			json.put("data", "");
		} else {
			json.put("code", "ok");
			json.put("data", filename);
		}
		out.write(json.toString());
	}

	@RequestMapping({ "/clone-paper.do" })
	public void clonePaper(HttpServletRequest request, PrintWriter out) {
		if (!HasPrivelege(request, "P-PAPER-CLONE")) {
			JSONObject ret = new JSONObject();
			ret.put("code", "no_right");
			ret.put("data", MessageHelper.getMessage("message.other.no_right"));
			out.write(ret.toString());
			return;
		}
		String pid = request.getParameter("pid");
		String pname = request.getParameter("pname");

		int code = 0;
		if (BaseUtil.isEmpty(pid)) {
			code = -1;
		} else if (BaseUtil.isEmpty(pname)) {
			code = -2;
		} else {
			code = this.service.doCopyPaper(pid, pname);
		}
		JSONObject json = new JSONObject();
		if (code == 1) {
			json.put("code", "ok");
			json.put("data", MessageHelper.getMessage("message.other.op_ok"));
		} else if (code == -1) {
			json.put("code", "err");
			json.put("data", MessageHelper.getMessage("message.sys.paper.copy.noid"));
		} else if (code == -2) {
			json.put("code", "err");
			json.put("data", MessageHelper.getMessage("message.sys.paper.copy.noname"));
		} else {
			json.put("code", "err");
			json.put("data", MessageHelper.getMessage("message.other.op_failed"));
		}
		out.write(json.toString());
	}
}

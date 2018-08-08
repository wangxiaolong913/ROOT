package com.tom.system.action;

import com.tom.model.system.BaseMessage;
import com.tom.model.system.BaseUrl;
import com.tom.model.system.Pagination;
import com.tom.system.service.IBranchService;
import com.tom.system.service.IUserPositionService;
import com.tom.system.service.IUserService;
import com.tom.util.BaseUtil;
import com.tom.web.controller.BaseController;
import com.tom.web.message.MessageHelper;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/system/user" })
public class UserController extends BaseController {
	@Autowired
	private IUserService user_service;
	@Autowired
	private IBranchService branch_service;
	@Autowired
	private IUserPositionService position_service;

	@RequestMapping({ "/add.thtml" })
	public ModelAndView add(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-ADD")) {
			return RedirectToNoPrivelegePage();
		}
		modelMap.put("branches", this.branch_service.getAllBranchs());
		modelMap.put("postions", this.position_service.getAllPositions());
		return new ModelAndView("system/user/add", modelMap);
	}

	@RequestMapping({ "/import.thtml" })
	public ModelAndView importx(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-IMPORT")) {
			return RedirectToNoPrivelegePage();
		}
		modelMap.put("branches", this.branch_service.getAllBranchs());
		modelMap.put("postions", this.position_service.getAllPositions());
		return new ModelAndView("system/user/import", modelMap);
	}

	@RequestMapping({ "/list.thtml" })
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "F-USERS")) {
			return RedirectToNoPrivelegePage();
		}
		modelMap.put("branches", this.branch_service.getAllBranchs());
		String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
		String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");

		Map<String, Object> params = getRequestData(request);
		if ((params != null) && (BaseUtil.isNotEmpty(request.getParameter("u_realname")))) {
			params.put("u_realname", BaseUtil.getChinese(request.getParameter("u_realname")));
		}
		Pagination page = this.user_service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
		modelMap.put("page", page);
		modelMap.put("foot", page.getNavFoot(request));
		return new ModelAndView("system/user/list", modelMap);
	}

	@RequestMapping({ "/list-query.thtml" })
	public ModelAndView listQuery(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "F-USERS")) {
			return RedirectToNoPrivelegePage();
		}
		modelMap.put("branches", this.branch_service.getAllBranchs());
		String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
		String epagesize = BaseUtil.convertEmptyToSome(request.getParameter("epagesize"), "10");

		Map<String, Object> params = getRequestData(request);
		if ((params != null) && (BaseUtil.isNotEmpty(request.getParameter("u_realname")))) {
			params.put("u_realname", BaseUtil.getChinese(request.getParameter("u_realname")));
		}
		Pagination page = this.user_service.query(params, BaseUtil.getInt(epagesize), BaseUtil.getInt(epage));
		modelMap.put("page", page);
		modelMap.put("foot", page.getNavFoot(request));
		return new ModelAndView("system/user/list-query", modelMap);
	}

	@RequestMapping({ "/load.thtml" })
	public ModelAndView load(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-UPDATE")) {
			return RedirectToNoPrivelegePage();
		}
		String id = request.getParameter("id");
		Map<String, Object> user = this.user_service.getUser(id);
		if (user == null) {
			BaseMessage message = new BaseMessage(false, MessageHelper.getMessage("message.sys.nodata"));
			message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.add"), "system/user/add.thtml"));
			message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.list"), "system/user/list.thtml"));
			modelMap.put("message", message);
			return new ModelAndView("common/message", modelMap);
		}
		modelMap.put("branches", this.branch_service.getAllBranchs());
		modelMap.put("postions", this.position_service.getAllPositions());
		modelMap.put("user", user);
		return new ModelAndView("system/user/load", modelMap);
	}

	@RequestMapping({ "/save.do" })
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-ADD")) {
			return RedirectToNoPrivelegePage();
		}
		BaseMessage message = null;
		int i = user_service.addUser(getRequestData(request));
		if (i == 2) {
			message = new BaseMessage(true,
					MessageHelper.getMessages(" ", new String[] { "message.sys.user.add", "message.sys.success" }));
		} else if (i == 3) {
			message = new BaseMessage(false, MessageHelper.getMessage("message.sys.user.userexist"));
		} else if (i == -99) {
			message = new BaseMessage(false, MessageHelper.getMessage("message.sys.user.exceeded"));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.user.add", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.add"), "system/user/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.list"), "system/user/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/batchImport.do" })
	public ModelAndView batchImport(HttpServletRequest request,
			@RequestParam(value = "u_file", required = false) MultipartFile file, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-IMPORT")) {
			return RedirectToNoPrivelegePage();
		}
		BaseMessage message = null;

		int rows = this.user_service.importUsers(file, request);
		if (rows >= 0) {
			StringBuffer msg = new StringBuffer();
			msg.append(MessageHelper.getMessage("message.sys.user.import"));
			msg.append(MessageHelper.getMessage("message.sys.success"));
			msg.append("," + MessageHelper.getMessage("txt.other.units.total") + ":");
			msg.append(rows);
			message = new BaseMessage(true, msg.toString());
		} else if (rows == -1) {
			message = new BaseMessage(false, MessageHelper.getMessage("message.sys.user.emptyfie"));
		} else if (rows == -9) {
			message = new BaseMessage(false, MessageHelper.getMessage("message.sys.user.formaterror"));
		} else if (rows == -99) {
			message = new BaseMessage(false, MessageHelper.getMessage("message.sys.user.exceeded"));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.user.import", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.add"), "system/user/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.list"), "system/user/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/delete.do" })
	public ModelAndView delete(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-DELETE")) {
			return RedirectToNoPrivelegePage();
		}
		String id = request.getParameter("id");
		int i = this.user_service.deleteUser(id);
		BaseMessage message = null;
		if (i == 1) {
			message = new BaseMessage(true,
					MessageHelper.getMessages(" ", new String[] { "message.sys.user.delete", "message.sys.success" }));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.user.delete", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.add"), "system/user/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.list"), "system/user/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/update.do" })
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-UPDATE")) {
			return RedirectToNoPrivelegePage();
		}
		BaseMessage message = null;
		int i = this.user_service.updateUser(getRequestData(request));
		if (i == 1) {
			message = new BaseMessage(true,
					MessageHelper.getMessages(" ", new String[] { "message.sys.user.update", "message.sys.success" }));
		} else {
			message = new BaseMessage(false,
					MessageHelper.getMessages(" ", new String[] { "message.sys.user.update", "message.sys.failed" }));
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.add"), "system/user/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.list"), "system/user/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/batchop.do" })
	public ModelAndView batchOperation(HttpServletRequest request, ModelMap modelMap) {
		String op = request.getParameter("b_op");

		BaseMessage message = null;
		if ("batch_delete".equals(op)) {
			if (!HasPrivelege(request, "P-USER-DELETE")) {
				return RedirectToNoPrivelegePage();
			}
			int rows = this.user_service.batchDelete(request);
			if (rows >= 0) {
				StringBuffer msg = new StringBuffer();
				msg.append(MessageHelper.getMessage("message.sys.user.batch.delete"));
				msg.append(MessageHelper.getMessage("message.sys.success"));
				msg.append("," + MessageHelper.getMessage("txt.other.units.total") + ":");
				msg.append(rows);
				message = new BaseMessage(true, msg.toString());
			} else {
				message = new BaseMessage(false, MessageHelper.getMessages(" ",
						new String[] { "message.sys.user.batch.delete", "message.sys.failed" }));
			}
		} else if ("batch_status".equals(op)) {
			if (!HasPrivelege(request, "P-USER-UPDATE")) {
				return RedirectToNoPrivelegePage();
			}
			int rows = this.user_service.batchSetStatus(request);
			if (rows >= 0) {
				StringBuffer msg = new StringBuffer();
				msg.append(MessageHelper.getMessage("message.sys.user.batch.update"));
				msg.append(MessageHelper.getMessage("message.sys.success"));
				msg.append("," + MessageHelper.getMessage("txt.other.units.total") + ":");
				msg.append(rows);
				message = new BaseMessage(true, msg.toString());
			} else {
				message = new BaseMessage(false, MessageHelper.getMessages(" ",
						new String[] { "message.sys.user.batch.update", "message.sys.failed" }));
			}
		} else if ("batch_branch".equals(op)) {
			if (!HasPrivelege(request, "P-USER-UPDATE")) {
				return RedirectToNoPrivelegePage();
			}
			int rows = this.user_service.batchSetGid(request);
			if (rows >= 0) {
				StringBuffer msg = new StringBuffer();
				msg.append(MessageHelper.getMessage("message.sys.user.batch.update"));
				msg.append(MessageHelper.getMessage("message.sys.success"));
				msg.append("," + MessageHelper.getMessage("txt.other.units.total") + ":");
				msg.append(rows);
				message = new BaseMessage(true, msg.toString());
			} else {
				message = new BaseMessage(false, MessageHelper.getMessages(" ",
						new String[] { "message.sys.user.batch.update", "message.sys.failed" }));
			}
		}
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.add"), "system/user/add.thtml"));
		message.addUrl(new BaseUrl(MessageHelper.getMessage("txt.sys.user.list"), "system/user/list.thtml"));
		modelMap.put("message", message);
		return new ModelAndView("common/message", modelMap);
	}

	@RequestMapping({ "/examlist.thtml" })
	public ModelAndView examlist(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-EXAMVIEW")) {
			return RedirectToNoPrivelegePage();
		}
		String id = request.getParameter("id");
		String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
		Pagination page = this.user_service.examlist(id, 10, BaseUtil.getInt(epage));
		page.setChangesize(false);

		modelMap.put("page", page);
		modelMap.put("foot", page.getNavFoot(request));
		modelMap.put("user", this.user_service.getUser(id));
		return new ModelAndView("system/user/examlist", modelMap);
	}

	@RequestMapping({ "/selftestlist.thtml" })
	public ModelAndView selfTestList(HttpServletRequest request, ModelMap modelMap) {
		if (!HasPrivelege(request, "P-USER-EXAMVIEW")) {
			return RedirectToNoPrivelegePage();
		}
		String id = request.getParameter("id");
		String epage = BaseUtil.convertEmptyToSome(request.getParameter("epage"), "1");
		Pagination page = this.user_service.selfTestList(id, 10, BaseUtil.getInt(epage));
		page.setChangesize(false);

		modelMap.put("page", page);
		modelMap.put("foot", page.getNavFoot(request));
		modelMap.put("user", this.user_service.getUser(id));
		return new ModelAndView("system/user/selftestlist", modelMap);
	}
}

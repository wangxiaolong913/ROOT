package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IUserDao;
import com.tom.system.service.IUserService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.util.Md5Util;
import com.tom.util.OfficeToolExcel;
import com.tom.util.WebApplication;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("UserService")
public class UserServiceImp implements IUserService {
	private static final Logger logger = Logger.getLogger(UserServiceImp.class);
	@Autowired
	private IUserDao dao;

	public int addUser(Map<String, Object> user) {
		try {
			int uis = this.dao.getuis();
//			if (!((Md5Util) WebApplication.getInstance().getSingletonObject(Md5Util.class)).hpashed(uis)) {
//				return -99;
//			}
		} catch (Exception e) {
			logger.error(e);
			try {
				String u_username = (String) user.get("u_username");
				Map<String, Object> userchk = this.dao.getUserByUsername(u_username);
				if (userchk != null) {
					return 3;
				}
				String userpass = String.valueOf(user.get("u_userpass"));
				String salt = BaseUtil.generateRandomString(10);
				if (BaseUtil.isNotEmpty(userpass)) {
					// String password = ((Md5Util)
					// WebApplication.getInstance().getSingletonObject(Md5Util.class))
					// .getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + userpass + salt);
					Md5Util md = new Md5Util();
					String password = md.string2MD5(new String(userpass));
					user.put("u_userpass", password);
				}
				user.put("u_id", BaseUtil.generateId());
				user.put("u_salt", salt);

				return this.dao.addUser(user);
			} catch (Exception ep) {
				logger.error(ep);
			}
		}
		return 0;
	}

	public int importUsers(MultipartFile file, HttpServletRequest request) {
		if (file == null) {
			return -1;
		}
		try {
			File tmpFile = File.createTempFile("TEMPFILE" + System.currentTimeMillis(), ".tmp");
			file.transferTo(tmpFile);

			List<Map> list = OfficeToolExcel.readExcel(tmpFile,
					new String[] { "USERNO", "USERNAME", "USERPASS", "REALNAME", "EMAIL", "MOBI", "REMARK" });
			if ((list == null) || (list.size() < 1)) {
				return -1;
			}
			try {
				int uis = this.dao.getuis() + list.size();
//				if (!((Md5Util) WebApplication.getInstance().getSingletonObject(Md5Util.class)).hpashed(uis)) {
//					return -99;
//				}
			} catch (Exception ex) {
				logger.error(ex);

				String u_branchid = request.getParameter("u_branchid");
				String u_positionid = request.getParameter("u_positionid");

				List<List<Object>> users = new ArrayList();
				List<List<Object>> users_ex = new ArrayList();
				int row = 0;
				for (Map map : list) {
					row++;
					if (row >= 2) {
						String PWD = String.valueOf(map.get("USERPASS"));
						String salt = BaseUtil.generateRandomString(10);
						// String password = ((Md5Util)
						// WebApplication.getInstance().getSingletonObject(Md5Util.class))
						// .getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + PWD + salt);
						Md5Util md = new Md5Util();
						String password = md.string2MD5(new String(PWD));
						String uid = BaseUtil.generateId();

						Object[] user_obj = { uid, String.valueOf(map.get("USERNAME")), password, "", u_branchid,
								String.valueOf(map.get("REALNAME")), Integer.valueOf(0),
								String.valueOf(map.get("USERNO")), String.valueOf(map.get("MOBI")),
								String.valueOf(map.get("EMAIL")), "0", "", String.valueOf(map.get("REMARK")), salt };

						Object[] userex_obj = { uid, u_positionid };

						List<Object> user = Arrays.asList(user_obj);
						List<Object> userex = Arrays.asList(userex_obj);

						users.add(user);
						users_ex.add(userex);
					}
				}
				tmpFile.delete();

				return this.dao.batchAddUsers(users, users_ex);
			}
			return -9;
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}

	public int deleteUser(String id) {
		try {
			return this.dao.deleteUser(id);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int deleteUserLogic(String id) {
		try {
			return this.dao.updateUserStatus(id, -9);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int recoveryUser(String id) {
		try {
			return this.dao.updateUserStatus(id, 1);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public boolean doCheckUsernameExist(String username) {
		try {
			Map<String, Object> map = this.dao.getUserByUsername(username);
			return map != null;
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

	public int doLogin(String username, String userpass) {
		try {
			Map<String, Object> user = this.dao.getUserByUsername(username);
			if (user == null) {
				return 10;
			}
			String salt = String.valueOf(user.get("u_salt"));
			String userpass_db = String.valueOf(user.get("u_userpass"));
			String u_id = String.valueOf(user.get("u_id"));
			// String userpass_xn = ((Md5Util)
			// WebApplication.getInstance().getSingletonObject(Md5Util.class))
			// .getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + userpass + salt);
			Md5Util md = new Md5Util();
			String userpass_xn = md.string2MD5(new String(userpass));
			if (!userpass_db.equals(userpass_xn)) {
				return 11;
			}
			this.dao.updateLoginInfo(u_id);
			return 1;
		} catch (Exception e) {
			logger.error(e);
		}
		return 19;
	}

	public Map<String, Object> getUser(String id) {
		try {
			return this.dao.getUser(id);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo) {
		try {
			return this.dao.query(params, pagesize, currentPageNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return new Pagination();
	}

	public int updateUser(Map<String, Object> user) {
		try {
			String u_userpass = (String) user.get("u_userpass");
			String u_id = (String) user.get("u_id");
			if (BaseUtil.isNotEmpty(u_userpass)) {
				Map<String, Object> usr = this.dao.getUser(u_id);
				String salt = "";
				if (usr != null) {
					salt = (String) usr.get("u_salt");
				}
				// String password = ((Md5Util)
				// WebApplication.getInstance().getSingletonObject(Md5Util.class))
				// .getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + u_userpass + salt);
				Md5Util md = new Md5Util();
				String password = md.string2MD5(new String(u_userpass));
				user.put("u_userpass", password);
			}
			return this.dao.updateUser(user);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int batchDelete(HttpServletRequest request) {
		List<List<Object>> users = new ArrayList();

		String[] uids = request.getParameterValues("b_uids");
		if ((uids == null) || (uids.length < 1)) {
			return 0;
		}
		String[] arrayOfString1;
		int j = (arrayOfString1 = uids).length;
		for (int i = 0; i < j; i++) {
			String uid = arrayOfString1[i];
			Object[] ouser = { uid };

			List<Object> user = Arrays.asList(ouser);
			users.add(user);
		}
		try {
			return this.dao.batchDeleteUsers(users);
		} catch (Exception e) {
			logger.error(e);
		}
		return -1;
	}

	public int batchSetGid(HttpServletRequest request) {
		List<List<Object>> users = new ArrayList();

		String[] uids = request.getParameterValues("b_uids");
		String b_branchid = request.getParameter("b_branchid");
		if ((uids == null) || (uids.length < 1)) {
			return 0;
		}
		String[] arrayOfString1;
		int j = (arrayOfString1 = uids).length;
		for (int i = 0; i < j; i++) {
			String uid = arrayOfString1[i];
			Object[] ouser = { b_branchid, uid };

			List<Object> user = Arrays.asList(ouser);
			users.add(user);
		}
		try {
			return this.dao.batchUpdateUserBranch(users);
		} catch (Exception e) {
			logger.error(e);
		}
		return -1;
	}

	public int batchSetStatus(HttpServletRequest request) {
		List<List<Object>> users = new ArrayList();

		String[] uids = request.getParameterValues("b_uids");
		String b_status = request.getParameter("b_status");
		if ((uids == null) || (uids.length < 1)) {
			return 0;
		}
		String[] arrayOfString1;
		int j = (arrayOfString1 = uids).length;
		for (int i = 0; i < j; i++) {
			String uid = arrayOfString1[i];
			Object[] ouser = { b_status, uid };

			List<Object> user = Arrays.asList(ouser);
			users.add(user);
		}
		try {
			return this.dao.batchUpdateUserStatus(users);
		} catch (Exception e) {
			logger.error(e);
		}
		return -1;
	}

	public Pagination examlist(String uid, int pagesize, int currentPageNo) {
		try {
			return this.dao.examlist(uid, pagesize, currentPageNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return new Pagination();
	}

	public Pagination selfTestList(String uid, int pagesize, int currentPageNo) {
		try {
			return this.dao.selfTestList(uid, pagesize, currentPageNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return new Pagination();
	}
}

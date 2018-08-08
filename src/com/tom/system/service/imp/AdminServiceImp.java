package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IAdminDao;
import com.tom.system.service.IAdminService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.util.Md5Util;
import com.tom.util.WebApplication;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminService")
public class AdminServiceImp implements IAdminService {
	private static final Logger logger = Logger.getLogger(AdminServiceImp.class);
	@Autowired
	private IAdminDao dao;

	public int addAdmin(Map<String, Object> admin) {
		try {
			String a_username = (String) admin.get("a_username");
			Map<String, Object> adminchk = this.dao.getAdminByUsername(a_username);
			if (adminchk != null) {
				return 3;
			}
			String userpass = String.valueOf(admin.get("a_userpass"));
			String salt = BaseUtil.generateRandomString(10);
			if (BaseUtil.isNotEmpty(userpass)) {
				// String password =
				// ((Md5Util)WebApplication.getInstance().getSingletonObject(Md5Util.class)).getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE
				// + userpass + salt);
				Md5Util md = new Md5Util();
				String password = md.string2MD5(new String(userpass));
				admin.put("a_userpass", password);
			}
			admin.put("a_id", BaseUtil.generateId());
			admin.put("a_salt", salt);

			return this.dao.addAdmin(admin);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int deleteAdmin(String id) {
		try {
			return this.dao.deleteAdmin(id);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int deleteAdminLogic(String id) {
		try {
			return this.dao.updateAdminStatus(id, -9);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int recoveryAdmin(String id) {
		try {
			return this.dao.updateAdminStatus(id, 1);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int updateAdmin(Map<String, Object> admin) {
		try {
			String a_userpass = (String) admin.get("a_userpass");
			String a_id = (String) admin.get("a_id");
			if (BaseUtil.isNotEmpty(a_userpass)) {
				Map<String, Object> adm = this.dao.getAdmin(a_id);
				String salt = "";
				if (adm != null) {
					salt = (String) adm.get("a_salt");
				}
				// String password = ((Md5Util)
				// WebApplication.getInstance().getSingletonObject(Md5Util.class))
				// .getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + a_userpass + salt);
				Md5Util md = new Md5Util();
				String password = md.string2MD5(new String(a_userpass));
				admin.put("a_userpass", password);
			}
			return this.dao.updateAdmin(admin);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public Map<String, Object> getAdmin(String id) {
		try {
			return this.dao.getAdmin(id);
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

	public boolean doCheckUsernameExist(String username) {
		try {
			Map<String, Object> map = this.dao.getAdminByUsername(username);
			return map != null;
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

	public int doLogin(String username, String userpass) {
		try {
			Map<String, Object> user = this.dao.getAdminByUsername(username);
			if (user == null) {
				return 10;
			}
			String salt = String.valueOf(user.get("a_salt"));
			String userpass_db = String.valueOf(user.get("a_userpass"));
			String a_id = String.valueOf(user.get("a_id"));
			// String userpass_xn = ((Md5Util)
			// WebApplication.getInstance().getSingletonObject(Md5Util.class))
			// .getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + userpass + salt);
			Md5Util md = new Md5Util();
			String userpass_xn = md.string2MD5(new String(userpass));
			if (!userpass_db.equals(userpass_xn)) {
				return 11;
			}
			Map<String, Object> addtion = new HashMap();
			addtion.put("a_id", a_id);
			this.dao.updateAdminAddtion(addtion);

			return 1;
		} catch (Exception e) {
			logger.error(e);
		}
		return 19;
	}
}

package com.tom.user.service.imp;

import com.tom.core.service.ICoreSystemService;
import com.tom.model.system.Pagination;
import com.tom.system.dao.IAdminRoleDao;
import com.tom.system.service.IConfigService;
import com.tom.user.dao.ICommonDao;
import com.tom.user.dao.IUserPaperDao;
import com.tom.user.service.ICommonService;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.util.Md5Util;
import com.tom.util.WebApplication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CommonService")
public class CommonServiceImp
  implements ICommonService
{
  @Autowired
  private ICommonDao dao;
  @Autowired
  private IUserPaperDao updao;
  @Autowired
  private ICoreSystemService core_service;
  @Autowired
  private IConfigService configService;
  @Autowired
  private IAdminRoleDao adminRoleDao;
  private static final Logger logger = Logger.getLogger(CommonServiceImp.class);
  
  public Map<String, Object> doLogin(String usertype, String username, String userpass)
  {
    logger.info(String.format("username=%susertype=%s", new Object[] { username, usertype }));
    
    Map<String, Object> ret = new HashMap();
    if ((BaseUtil.isEmpty(usertype)) || (BaseUtil.isEmpty(username)) || (BaseUtil.isEmpty(userpass)))
    {
      ret.put("code", Integer.valueOf(-4));
      ret.put("msgkey", "params_required");
      return ret;
    }
//    try
//    {
      Map<String, Object> user = this.dao.getUserByUserName(usertype, username);
      if (user == null)
      {
        ret.put("code", Integer.valueOf(-3));
        ret.put("msgkey", "user_not_exsit");
        return ret;
      }
      String status = String.valueOf(user.get("user_status"));
      if ("-1".equals(status))
      {
        ret.put("code", Integer.valueOf(-2));
        ret.put("msgkey", "user_locked");
        return ret;
      }
      String salt = String.valueOf(user.get("user_salt"));
      String old_pass = String.valueOf(user.get("user_pass"));
      String new_pass = ((Md5Util)WebApplication.getInstance().getSingletonObject(Md5Util.class)).getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + userpass + salt);
      if (!old_pass.equals(new_pass))
      {
        ret.put("code", Integer.valueOf(-1));
        ret.put("msgkey", "password_wrong");
        return ret;
      }
      String userid = String.valueOf(user.get("user_id"));
      this.dao.updateUserLastLogin(usertype, userid);
      
      ret.put("code", Integer.valueOf(1));
      ret.put("data", user);
      ret.put("msgkey", "success");
      return ret;
//    }
//    catch (Exception e)
//    {
//      logger.error(e);
//      ret.put("code", Integer.valueOf(-9));
//      ret.put("msgkey", "unknown_error");
//    }
//    return ret;
  }
  
  public List<Map<String, Object>> getWelcomeNewsList(int rows)
  {
    try
    {
      Pagination pager = this.dao.queryNews(null, 5, 1);
      if (pager != null) {
        return pager.getDataList();
      }
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> getWelcomeStatisticsParams()
  {
    Map<String, Object> map = new HashMap();
    map.put("user", this.dao.StatisticUsers());
    map.put("qdb", this.dao.StatisticQdbs());
    map.put("question", this.dao.StatisticQuestions());
    map.put("paper", this.dao.StatisticPapers());
    map.put("lession", this.dao.StatisticLessions());
    return map;
  }
  
  public List<Map<String, Object>> getWelcomeExamList(String branchid, String uid, int rows)
  {
    try
    {
      Map<String, Object> params = new HashMap();
      params.put("branchid", branchid);
      params.put("uid", uid);
      Pagination pager = this.updao.query(params, rows, 1);
      if (pager != null) {
        return pager.getDataList();
      }
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getWelcomeExamHistoryList(String branchid, String uid, int rows)
  {
    try
    {
      Map<String, Object> params = new HashMap();
      params.put("e_uid", uid);
      
      Pagination pager = this.updao.history(params, rows, 1);
      if (pager != null) {
        return pager.getDataList();
      }
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> getNews(String id)
  {
    try
    {
      return this.dao.getNews(id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination getNewsList(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.queryNews(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  public Map<String, Object> getNewsCategory(String id)
  {
    try
    {
      return this.dao.getNewsCategory(id);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllNewsCategories()
  {
    try
    {
      return this.dao.getAllNewsCategories();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> getUserProfile(String uid)
  {
    try
    {
      return this.dao.getUserProfile(uid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Map<String, Object> getAdminProfile(String aid)
  {
    try
    {
      return this.dao.getAdminProfile(aid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public int updateUserProfile(Map<String, Object> user)
  {
    if (user == null) {
      return 0;
    }
    logger.info(String.format("��������������user=%s", new Object[] { user.toString() }));
    try
    {
      String u_userpass = (String)user.get("u_userpass");
      String u_id = (String)user.get("u_id");
      if (BaseUtil.isNotEmpty(u_userpass))
      {
        Map<String, Object> usr = this.dao.getUserProfile(u_id);
        if (usr == null) {
          return -1;
        }
        String salt = (String)usr.get("u_salt");
        String password = ((Md5Util)WebApplication.getInstance().getSingletonObject(Md5Util.class)).getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + u_userpass + salt);
        user.put("u_userpass", password);
      }
      return this.dao.updateUserProfile(user);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateAdminProfile(Map<String, Object> admin)
  {
    if (admin == null) {
      return 0;
    }
    logger.info(String.format("����������������admin=%s", new Object[] { admin.toString() }));
    try
    {
      String a_userpass = (String)admin.get("a_userpass");
      String a_id = (String)admin.get("a_id");
      if (BaseUtil.isNotEmpty(a_userpass))
      {
        Map<String, Object> adm = this.dao.getAdminProfile(a_id);
        if (adm == null) {
          return -1;
        }
        String salt = (String)adm.get("a_salt");
        String password = ((Md5Util)WebApplication.getInstance().getSingletonObject(Md5Util.class)).getMD5ofStr(Constants.SYS_IDENTIFICATION_CODE + a_userpass + salt);
        admin.put("a_userpass", password);
      }
      return this.dao.updateAdminProfile(admin);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public String getAdminRolePrivilege(String roleid)
  {
    try
    {
      Map<String, Object> role = this.adminRoleDao.getAdminRole(roleid);
      return "," + String.valueOf(role.get("r_privilege"));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public Map<String, Object> getSystemBaseConfig()
  {
    return this.configService.getConfig("1");
  }
  
  public int getTotalUsers()
  {
    return this.core_service.getTotalUsers();
  }
}

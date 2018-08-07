package com.tom.user.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.user.dao.ICommonDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDaoImp
  extends BaseJdbcDAO
  implements ICommonDao
{
  public Map<String, Object> getUserByUserName(String usertype, String username)
  {
    String sql = "select * from tm_user where u_username=?";
    if ("1".equals(usertype)) {
      sql = "select * from tm_admin where a_username=?";
    }
    Map<String, Object> map = queryForMap(sql, new Object[] { username });
    if (map == null) {
      return null;
    }
    String userid = null;
    String userstatus = null;
    String usersalt = null;
    String userpass = null;
    String usergid = null;
    
    Map<String, Object> user = new HashMap();
    if ("1".equals(usertype))
    {
      userid = String.valueOf(map.get("a_id"));
      userstatus = String.valueOf(map.get("a_status"));
      usersalt = String.valueOf(map.get("a_salt"));
      userpass = String.valueOf(map.get("a_userpass"));
      usergid = String.valueOf(map.get("a_roleid"));
    }
    else
    {
      userid = String.valueOf(map.get("u_id"));
      userstatus = String.valueOf(map.get("u_status"));
      usersalt = String.valueOf(map.get("u_salt"));
      userpass = String.valueOf(map.get("u_userpass"));
      usergid = String.valueOf(map.get("u_branchid"));
    }
    user.put("user_id", userid);
    user.put("user_name", username);
    user.put("user_type", usertype);
    user.put("user_status", userstatus);
    user.put("user_salt", usersalt);
    user.put("user_pass", userpass);
    user.put("user_gid", usergid);
    
    return user;
  }
  
  public int updateUserLastLogin(String usertype, String userid)
  {
    if ("1".equals(usertype))
    {
      String sql = "update tm_admin_addition set a_logintimes=a_logintimes+1 , a_lastlogin=now() where a_id=?";
      return update(sql, new Object[] { userid });
    }
    String sql = "update tm_user_addition set u_logintimes=u_logintimes+1 , u_lastlogin=now() where u_id=?";
    return update(sql, new Object[] { userid });
  }
  
  public List<Map<String, Object>> getNewsCategories()
  {
    String sql = "select * from tm_news_cateogry order by c_createdate desc";
    return queryForList(sql);
  }
  
  public Map<String, Object> getNewsCategory(String categoryid)
  {
    String sql = "select * from tm_news_cateogry where c_id=?";
    return queryForMap(sql, new Object[] { categoryid });
  }
  
  public Map<String, Object> getNews(String newsid)
  {
    String sql = "update tm_news set n_visit=n_visit+1 where n_id=?";
    update(sql, new Object[] { newsid });
    sql = "select a.*,b.c_name from tm_news a left join tm_news_cateogry b on a.n_classid = b.c_id where a.n_id=?";
    
    return queryForMap(sql, new Object[] { newsid });
  }
  
  public Pagination queryNews(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.n_title,a.n_id, b.c_name,DATE_FORMAT(a.n_createdate,'%Y-%m-%d %T') n_createdate,a.n_visit, a.n_poster from tm_news a left join tm_news_cateogry b on a.n_classid=b.c_id where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("n_classid"))) {
      sql.append(" and a.n_classid=:n_classid");
    }
    if (BaseUtil.isNotEmpty(params.get("n_title")))
    {
      String n_title = String.valueOf(params.get("n_title"));
      params.put("n_title", BaseUtil.getChinese(n_title));
      sql.append(" and a.n_title like concat('%',:n_title,'%')");
    }
    sql.append(" order by a.n_totop desc, a.n_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public Map<String, Object> StatisticUsers()
  {
    String sql = "select u_status , count(1) u_num from tm_user group by tm_user.u_status";
    List<Map<String, Object>> list = queryForList(sql);
    if (list != null)
    {
      Map<String, Object> map = new HashMap();
      for (Map<String, Object> m : list) {
        map.put(String.valueOf(m.get("u_status")), String.valueOf(m.get("u_num")));
      }
      return map;
    }
    return null;
  }
  
  public Map<String, Object> StatisticQdbs()
  {
    String sql = "select 'qdb' wht, count(1) num from tm_question_db  union all  select 'question' wht, count(1) num from tm_question";
    
    List<Map<String, Object>> list = queryForList(sql);
    if (list != null)
    {
      Map<String, Object> map = new HashMap();
      for (Map<String, Object> m : list) {
        map.put(String.valueOf(m.get("wht")), String.valueOf(m.get("num")));
      }
      return map;
    }
    return null;
  }
  
  public Map<String, Object> StatisticQuestions()
  {
    String sql = "select q_type, count(1) q_num from tm_question group by tm_question.q_type";
    List<Map<String, Object>> list = queryForList(sql);
    if (list != null)
    {
      Map<String, Object> map = new HashMap();
      for (Map<String, Object> m : list) {
        map.put(String.valueOf(m.get("q_type")), String.valueOf(m.get("q_num")));
      }
      return map;
    }
    return null;
  }
  
  public Map<String, Object> StatisticPapers()
  {
    String sql = "select 'total' p_key, count(1) p_val from tm_paper union all select 'finish' p_key, count(1) p_val from tm_paper where tm_paper.p_endtime<now() union all select 'ing' p_key, count(1) p_val from tm_paper where tm_paper.p_endtime>now() and tm_paper.p_starttime<now()";
    
    List<Map<String, Object>> list = queryForList(sql);
    if (list != null)
    {
      Map<String, Object> map = new HashMap();
      for (Map<String, Object> m : list) {
        map.put(String.valueOf(m.get("p_key")), String.valueOf(m.get("p_val")));
      }
      return map;
    }
    return null;
  }
  
  public Map<String, Object> StatisticLessions()
  {
    String sql = "select 'courses' p_key,  count(1) p_val from tm_course";
    List<Map<String, Object>> list = queryForList(sql);
    if (list != null)
    {
      Map<String, Object> map = new HashMap();
      for (Map<String, Object> m : list) {
        map.put(String.valueOf(m.get("p_key")), String.valueOf(m.get("p_val")));
      }
      return map;
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllNewsCategories()
  {
    String sql = "select * from tm_news_cateogry order by c_createdate desc";
    return queryForList(sql);
  }
  
  public Map<String, Object> getUserProfile(String uid)
  {
    String sql = "select a.*, b.u_logintimes, b.u_lastlogin, c.p_name, d.b_name from tm_user a  left join tm_user_addition b on a.u_id=b.u_id  left join tm_user_position c on b.u_positionid = c.p_id left join tm_branch d on a.u_branchid = d.b_id where a.u_id=?";
    
    return queryForMap(sql, new Object[] { uid });
  }
  
  public Map<String, Object> getAdminProfile(String aid)
  {
    String sql = "select a.*,b.a_logintimes,b.a_lastlogin,c.r_name from tm_admin a left join tm_admin_addition b on a.a_id=b.a_id  left join tm_admin_role c on a.a_roleid = c.r_id  where a.a_id=?";
    
    return queryForMap(sql, new Object[] { aid });
  }
  
  public int updateUserProfile(Map<String, Object> user)
  {
    String sql = "";
    String userpass = String.valueOf(user.get("u_userpass"));
    if (BaseUtil.isNotEmpty(userpass)) {
      sql = "update tm_user set u_userpass=:u_userpass, u_photo=:u_photo,  u_realname=:u_realname, u_no=:u_no, u_phone=:u_phone, u_email=:u_email, u_status=0, u_address=:u_address, u_remark=:u_remark  where u_id=:u_id";
    } else {
      sql = "update tm_user set u_photo=:u_photo,  u_realname=:u_realname, u_no=:u_no, u_phone=:u_phone, u_email=:u_email, u_status=0, u_address=:u_address, u_remark=:u_remark  where u_id=:u_id";
    }
    return update(sql, user);
  }
  
  public int updateAdminProfile(Map<String, Object> admin)
  {
    String sql = "";
    String userpass = String.valueOf(admin.get("a_userpass"));
    if (BaseUtil.isNotEmpty(userpass)) {
      sql = "update tm_admin set a_userpass=:a_userpass,  a_realname=:a_realname, a_photo=:a_photo, a_phone=:a_phone, a_email=:a_email, a_remark=:a_remark  where a_id=:a_id";
    } else {
      sql = "update tm_admin set  a_realname=:a_realname, a_photo=:a_photo, a_phone=:a_phone, a_email=:a_email, a_remark=:a_remark  where a_id=:a_id";
    }
    return update(sql, admin);
  }
}

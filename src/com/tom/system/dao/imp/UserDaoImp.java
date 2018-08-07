package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IUserDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImp
  extends BaseJdbcDAO
  implements IUserDao
{
  public int addUser(Map<String, Object> user)
  {
    String sql = "insert into tm_user(u_id, u_username, u_userpass, u_photo, u_branchid, u_realname, u_score, u_no, u_phone, u_email, u_status, u_address, u_remark, u_regdate, u_salt) values(:u_id, :u_username, :u_userpass, :u_photo, :u_branchid, :u_realname, :u_score, :u_no, :u_phone, :u_email, :u_status, :u_address, :u_remark, now(), :u_salt)";
    
    int a = update(sql, user);
    
    sql = "insert into tm_user_addition(u_id, u_logintimes, u_lastlogin, u_positionid)  values(:u_id, 0, null, :u_positionid)";
    
    int b = update(sql, user);
    
    return a + b;
  }
  
  public int deleteUser(String id)
  {
    String sql = "delete from tm_user where u_id=?";
    return update(sql, new Object[] { id });
  }
  
  public int updateUserStatus(String id, int status)
  {
    String sql = "update tm_user set u_status=? where u_id=?";
    return update(sql, new Object[] { Integer.valueOf(status), id });
  }
  
  public int updateUser(Map<String, Object> user)
  {
    String sql = "";
    String userpass = String.valueOf(user.get("u_userpass"));
    if (BaseUtil.isNotEmpty(userpass)) {
      sql = "update tm_user set u_userpass=:u_userpass, u_photo=:u_photo, u_branchid=:u_branchid, u_realname=:u_realname, u_score=:u_score, u_no=:u_no, u_phone=:u_phone, u_email=:u_email, u_status=:u_status, u_address=:u_address, u_remark=:u_remark  where u_id=:u_id";
    } else {
      sql = "update tm_user set u_photo=:u_photo, u_branchid=:u_branchid, u_realname=:u_realname, u_score=:u_score, u_no=:u_no, u_phone=:u_phone, u_email=:u_email, u_status=:u_status, u_address=:u_address, u_remark=:u_remark  where u_id=:u_id";
    }
    String sqlb = "update tm_user_addition set u_positionid=:u_positionid where u_id=:u_id";
    update(sqlb, user);
    
    return update(sql, user);
  }
  
  public Map<String, Object> getUser(String id)
  {
    String sql = "select a.*, b.u_logintimes, b.u_lastlogin, c.p_name, b.u_positionid from tm_user a  left join tm_user_addition b on a.u_id=b.u_id  left join tm_user_position c on b.u_positionid = c.p_id where a.u_id=?";
    
    return queryForMap(sql, new Object[] { id });
  }
  
  public Map<String, Object> getUser(String username, String userpass)
  {
    String sql = "select * from tm_user where u_username=? and u_userpass=?";
    return queryForMap(sql, new Object[] { username, userpass });
  }
  
  public Map<String, Object> getUserByUsername(String username)
  {
    String sql = "select * from tm_user where u_username=?";
    return queryForMap(sql, new Object[] { username });
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    String orderby = String.valueOf(params.get("u_orderby"));
    if (BaseUtil.isEmpty(orderby)) {
      orderby = "REGDATE_DESC";
    }
    StringBuffer sql = new StringBuffer("select a.*, b.u_logintimes, if(isnull(b.u_lastlogin),'--',b.u_lastlogin) u_lastlogin, c.b_name u_branchname, d.p_name from tm_user a , tm_user_addition b , tm_branch c , tm_user_position d where  a.u_id = b.u_id and a.u_branchid = c.b_id and b.u_positionid = d.p_id");
    if (BaseUtil.isNotEmpty(params.get("u_username"))) {
      sql.append(" and a.u_username like concat('%',:u_username,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("u_branchid"))) {
      sql.append(" and a.u_branchid=:u_branchid");
    }
    if (BaseUtil.isNotEmpty(params.get("u_status"))) {
      sql.append(" and a.u_status=:u_status");
    }
    if (BaseUtil.isNotEmpty(params.get("u_realname"))) {
      sql.append(" and a.u_realname like concat('%',:u_realname,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("u_phone"))) {
      sql.append(" and a.u_phone like concat('%',:u_phone,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("u_email"))) {
      sql.append(" and a.u_email like concat('%',:u_email,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("u_positionid"))) {
      sql.append(" and b.u_positionid=:u_positionid");
    }
    if ("USERNAME_DESC".equals(orderby)) {
      sql.append(" order by a.u_username desc");
    } else if ("USERNAME_ASC".equals(orderby)) {
      sql.append(" order by a.u_username asc");
    } else if ("UNO_DESC".equals(orderby)) {
      sql.append(" order by a.u_no desc");
    } else if ("UNO_ASC".equals(orderby)) {
      sql.append(" order by a.u_no asc");
    } else if ("REGDATE_DESC".equals(orderby)) {
      sql.append(" order by a.u_regdate desc");
    } else if ("REGDATE_ASC".equals(orderby)) {
      sql.append(" order by a.u_regdate asc");
    } else {
      sql.append(" order by a.u_regdate desc");
    }
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int updateUserAddtion(Map<String, Object> addtion)
  {
    String sql = "update tm_user_addition set u_positionid=:u_positionid where u_id=:u_id";
    
    return update(sql, addtion);
  }
  
  public int updateLoginInfo(String uid)
  {
    String sql = "update tm_user_addition set u_logintimes=u_logintimes+1,u_lastlogin=now() where u_id=?";
    
    return update(sql, new Object[] { uid });
  }
  
  public int addUserAddition(Map<String, Object> addtion)
  {
    String sql = "insert into tm_user_addition(u_id,u_logintimes,u_lastlogin, u_positionid) values(:u_id, 0, now(), :u_positionid)";
    
    return update(sql, addtion);
  }
  
  public int batchAddUsers(List<List<Object>> users, List<List<Object>> usersex)
  {
    if ((users == null) || (users.isEmpty()) || (usersex == null) || (usersex.isEmpty())) {
      return 0;
    }
    String sql = "insert into tm_user(u_id, u_username, u_userpass, u_photo, u_branchid, u_realname, u_score, u_no, u_phone, u_email, u_status, u_address, u_remark, u_regdate, u_salt) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(),?)";
    
    int[] rows_a = batchUpdate(sql, users);
    
    sql = "insert into tm_user_addition(u_id, u_logintimes, u_lastlogin, u_positionid) values(?, 0, null, ?)";
    int[] rows_b = batchUpdate(sql, usersex);
    if ((rows_a != null) && (rows_b != null) && (rows_a.length == rows_b.length)) {
      return rows_a.length;
    }
    return 0;
  }
  
  public int batchUpdateUserStatus(List<List<Object>> users)
  {
    String sql = "update tm_user set u_status=? where u_id=?";
    int[] rows = batchUpdate(sql, users);
    return rows == null ? 0 : rows.length;
  }
  
  public int batchUpdateUserBranch(List<List<Object>> users)
  {
    String sql = "update tm_user set u_branchid=? where u_id=?";
    int[] rows = batchUpdate(sql, users);
    return rows == null ? 0 : rows.length;
  }
  
  public int batchDeleteUsers(List<List<Object>> users)
  {
    String sql = "delete from tm_user where u_id=?";
    int[] rows = batchUpdate(sql, users);
    return rows == null ? 0 : rows.length;
  }
  
  public Pagination examlist(String uid, int pagesize, int currentPageNo)
  {
    if (BaseUtil.isEmpty(uid)) {
      return new Pagination();
    }
    Map<String, Object> params = new HashMap();
    params.put("uid", uid);
    StringBuffer sql = new StringBuffer("select a.*, b.p_name, b.p_duration, b.p_total_score, b.p_pass_score, TIMESTAMPDIFF(MINUTE, a.e_starttime, a.e_endtime) e_timecost from tm_examdata a  left join tm_paper b on a.e_pid = b.p_id  where a.e_uid=:uid  order by a.e_starttime desc ");
    
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int getuis()
  {
    String sql = "select count(1) total from tm_user";
    return queryForInt(sql);
  }
  
  public Pagination selfTestList(String uid, int pagesize, int currentPageNo)
  {
    if (BaseUtil.isEmpty(uid)) {
      return new Pagination();
    }
    Map<String, Object> params = new HashMap();
    params.put("uid", uid);
    
    StringBuffer sql = new StringBuffer(256);
    sql.append(" select a.t_tid, a.t_name, a.t_duration, a.t_uid, a.t_totalscore, a.t_userscore, a.t_testdate, a.t_timecost, ");
    sql.append(" b.u_username, b.u_realname, b.u_branchid, b.u_no, c.b_name from tm_usertest a ");
    sql.append(" left join tm_user b on a.t_uid = b.u_id ");
    sql.append(" left join tm_branch c on b.u_branchid = c.b_id where a.t_uid=:uid ");
    sql.append(" order by a.t_testdate desc");
    
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public List<Map<String, Object>> getUsersByIds(String[] uids)
  {
    String sql = "select a.*, b.u_logintimes, b.u_lastlogin, c.p_name, b.u_positionid from tm_user a  left join tm_user_addition b on a.u_id=b.u_id  left join tm_user_position c on b.u_positionid = c.p_id where a.u_id in ";
    
    String xuids = "('" + StringUtils.join(uids, "','") + "')";
    return queryForList(sql + xuids);
  }
}

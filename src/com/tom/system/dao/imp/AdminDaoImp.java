package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IAdminDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDaoImp
  extends BaseJdbcDAO
  implements IAdminDao
{
  public int addAdmin(Map<String, Object> admin)
  {
    String sql = "insert into tm_admin(a_id,a_username,a_userpass,a_roleid,a_realname, a_photo,a_phone,a_email,a_status,a_createdate,a_salt,a_remark) values(:a_id,:a_username,:a_userpass,:a_roleid,:a_realname, :a_photo,:a_phone,:a_email,:a_status,now(),:a_salt,:a_remark)";
    
    int a = update(sql, admin);
    
    sql = "insert into tm_admin_addition(a_id,a_logintimes,a_lastlogin)  values(:a_id,0,null)";
    
    int b = update(sql, admin);
    
    return a + b;
  }
  
  public int deleteAdmin(String id)
  {
    String sql = "delete from tm_admin where a_id=?";
    return update(sql, new Object[] { id });
  }
  
  public int updateAdminStatus(String id, int status)
  {
    String sql = "update tm_admin set a_status=? where a_id=?";
    return update(sql, new Object[] { Integer.valueOf(status), id });
  }
  
  public int updateAdmin(Map<String, Object> admin)
  {
    String sql = "";
    String userpass = String.valueOf(admin.get("a_userpass"));
    if (BaseUtil.isNotEmpty(userpass)) {
      sql = "update tm_admin set a_userpass=:a_userpass,  a_roleid=:a_roleid, a_realname=:a_realname, a_photo=:a_photo, a_phone=:a_phone, a_email=:a_email, a_status=:a_status, a_remark=:a_remark  where a_id=:a_id";
    } else {
      sql = "update tm_admin set  a_roleid=:a_roleid, a_realname=:a_realname, a_photo=:a_photo, a_phone=:a_phone, a_email=:a_email, a_status=:a_status, a_remark=:a_remark  where a_id=:a_id";
    }
    return update(sql, admin);
  }
  
  public Map<String, Object> getAdmin(String id)
  {
    String sql = "select a.*,b.a_logintimes,b.a_lastlogin from tm_admin a left join tm_admin_addition b on a.a_id=b.a_id where a.a_id=?";
    return queryForMap(sql, new Object[] { id });
  }
  
  public Map<String, Object> getAdmin(String username, String userpass)
  {
    String sql = "select * from tm_admin where a_username=? and a_userpass=?";
    return queryForMap(sql, new Object[] { username, userpass });
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*,b.a_logintimes, if(isnull(b.a_lastlogin),'--',b.a_lastlogin) a_lastlogin, c.r_name a_rolename from tm_admin a left join tm_admin_addition b on a.a_id = b.a_id left join tm_admin_role c on a.a_roleid = c.r_id where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("a_username"))) {
      sql.append(" and a.a_username like concat('%',:a_username,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("a_roleid"))) {
      sql.append(" and a.a_roleid=:a_roleid");
    }
    if (BaseUtil.isNotEmpty(params.get("a_realname"))) {
      sql.append(" and a.a_realname like concat('%',:a_realname,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("a_phone"))) {
      sql.append(" and a.a_phone like concat('%',:a_phone,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("a_email"))) {
      sql.append(" and a.a_email like concat('%',:a_email,'%')");
    }
    sql.append(" order by a.a_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public Map<String, Object> getAdminByUsername(String username)
  {
    String sql = "select * from tm_admin where a_username=?";
    return queryForMap(sql, new Object[] { username });
  }
  
  public int addAdminAddition(Map<String, Object> addtion)
  {
    String sql = "insert into tm_admin_addition(a_id,a_logintimes,a_lastlogin) values(:a_id,0,now())";
    
    return update(sql, addtion);
  }
  
  public int updateAdminAddtion(Map<String, Object> addtion)
  {
    String sql = "update tm_admin_addition set a_logintimes=a_logintimes+1,a_lastlogin=now() where a_id=:a_id";
    
    return update(sql, addtion);
  }
}

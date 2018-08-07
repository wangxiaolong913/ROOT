package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IAdminRoleDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRoleDaoImp
  extends BaseJdbcDAO
  implements IAdminRoleDao
{
  public int addAdminRole(Map<String, Object> role)
  {
    String sql = "insert into tm_admin_role(r_id,r_name,r_status,r_createdate,r_privilege) values(:r_id,:r_name,:r_status,now(),:r_privilege)";
    
    return update(sql, role);
  }
  
  public int deleteAdminRole(String roleid)
  {
    String sql = "delete from tm_admin_role where r_id=?";
    return update(sql, new Object[] { roleid });
  }
  
  public int updateAdminRole(Map<String, Object> role)
  {
    String sql = "update tm_admin_role set r_name=:r_name,r_privilege=:r_privilege,r_status=:r_status where r_id=:r_id";
    return update(sql, role);
  }
  
  public Map<String, Object> getAdminRole(String roleid)
  {
    String sql = "select * from tm_admin_role where r_id=?";
    return queryForMap(sql, new Object[] { roleid });
  }
  
  public List<Map<String, Object>> getAllRoles()
  {
    String sql = "select * from tm_admin_role where r_status=1 order by r_createdate desc";
    return queryForList(sql);
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select * from tm_admin_role where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("r_name"))) {
      sql.append("and r_name=:r_name");
    }
    sql.append("order by r_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public List<?> getAdmins(String roleid)
  {
    String sql = "select * from tm_admin where a_roleid=?";
    return queryForList(sql, new Object[] { roleid });
  }
  
  public int getAdminNumbers(String roleid)
  {
    String sql = "select count(1) total from tm_admin where a_roleid=?";
    return queryForInt(sql, new Object[] { roleid });
  }
}

package com.tom.core.dao.imp;

import com.tom.core.dao.ICoreSystemDao;
import com.tom.util.BaseJdbcDAO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CoreSystemDaoImp
  extends BaseJdbcDAO
  implements ICoreSystemDao
{
  public List<Map<String, Object>> getAllAdminRoles()
  {
    String sql = "select r_id, r_privilege from tm_admin_role where r_status=1";
    return queryForList(sql);
  }
  
  public int saveLog(List<List<Object>> list)
  {
    if ((list == null) || (list.isEmpty())) {
      return 0;
    }
    String sql = "insert into tm_system_log(l_usertype, l_username, l_action, l_url, l_logdate, l_logdesc, l_ip) values(?, ?, ?, ?, ?, ?, ?)";
    int[] rows = batchUpdate(sql, list);
    return rows == null ? 0 : rows.length;
  }
  
  public int getTotalUsers()
  {
    String sql = "select count(1) total from tm_user";
    return queryForInt(sql);
  }
  
  public String getRegisterInfo()
  {
    String sql = "select * from tm_system_config where c_id=?";
    Map<String, Object> info = queryForMap(sql, new Object[] { Integer.valueOf(2) });
    if (info != null) {
      return String.valueOf(info.get("c_config"));
    }
    return null;
  }
}

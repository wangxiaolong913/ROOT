package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IUserPositionDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserPositionDaoImp
  extends BaseJdbcDAO
  implements IUserPositionDao
{
  public int addUserPosition(Map<String, Object> position)
  {
    String sql = "insert into tm_user_position(p_id,p_name,p_remark,p_status,p_poster,p_createdate,p_modifor,p_modifydate) values(:p_id,:p_name,:p_remark,:p_status,:p_poster,now(),:p_poster,now())";
    
    return update(sql, position);
  }
  
  public int deleteUserPosition(String positionid)
  {
    String sql = "delete from tm_user_position where p_id=?";
    return update(sql, new Object[] { positionid });
  }
  
  public int updateUserPosition(Map<String, Object> position)
  {
    String sql = "update tm_user_position set p_name=:p_name,p_remark=:p_remark,p_status=:p_status,p_modifor=:p_modifor,p_modifydate=now() where p_id=:p_id";
    
    return update(sql, position);
  }
  
  public Map<String, Object> getUserPosition(String positionid)
  {
    String sql = "select * from tm_user_position where p_id=?";
    return queryForMap(sql, new Object[] { positionid });
  }
  
  public List<Map<String, Object>> getAllUserPositions()
  {
    String sql = "select * from tm_user_position where p_status=1 order by p_createdate desc";
    return queryForList(sql);
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select * from tm_user_position where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("p_name"))) {
      sql.append(" and p_name like concat('%',:p_name,'%')");
    }
    sql.append("order by p_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public List<?> getUsers(String positionid)
  {
    String sql = "select * from tm_user_addition where u_positionid=?";
    return queryForList(sql, new Object[] { positionid });
  }
  
  public int getNumbers(String positionid)
  {
    String sql = "select count(1) total from tm_user_addition where u_positionid=?";
    return queryForInt(sql, new Object[] { positionid });
  }
}

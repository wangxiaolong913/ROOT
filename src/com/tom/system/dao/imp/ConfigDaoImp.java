package com.tom.system.dao.imp;

import com.tom.system.dao.IConfigDao;
import com.tom.util.BaseJdbcDAO;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigDaoImp
  extends BaseJdbcDAO
  implements IConfigDao
{
  public Map<String, Object> getConfig(String id)
  {
    String sql = "select * from tm_system_config where c_id=?";
    return queryForMap(sql, new Object[] { id });
  }
  
  public int updateConfig(String id, String config)
  {
    String sql = "update tm_system_config set c_config=? where c_id=?";
    return update(sql, new Object[] { config, id });
  }
  
  public int addConfig(String id, String config)
  {
    String sql = "insert into tm_system_config(c_id, c_config) values(?,?)";
    return update(sql, new Object[] { id, config });
  }
}

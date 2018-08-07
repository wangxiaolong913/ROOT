package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ILogDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import com.tom.web.message.MessageHelper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImp
  extends BaseJdbcDAO
  implements ILogDao
{
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    String lang = MessageHelper.getLang();
    
    StringBuffer sql = null;
    if ("zh_CN".equals(lang)) {
      sql = new StringBuffer("select a.*,c.f_cname l_action_name from tm_system_log a left join tm_system_function_lib c on a.l_action = c.f_code where 1=1 ");
    } else {
      sql = new StringBuffer("select a.*,c.f_ename l_action_name from tm_system_log a left join tm_system_function_lib c on a.l_action = c.f_code where 1=1 ");
    }
    if (BaseUtil.isNotEmpty(params.get("l_action_name"))) {
      if ("zh_CN".equals(lang)) {
        sql.append(" and c.f_cname = :l_action_name");
      } else {
        sql.append(" and c.f_ename = :l_action_name");
      }
    }
    if (BaseUtil.isNotEmpty(params.get("l_username"))) {
      sql.append(" and a.l_username like concat('%',:l_username,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("l_startdate"))) {
      sql.append(" and a.l_logdate >= :l_startdate");
    }
    if (BaseUtil.isNotEmpty(params.get("l_enddate"))) {
      sql.append(" and a.l_logdate<= :l_enddate");
    }
    sql.append(" order by a.l_id desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
}

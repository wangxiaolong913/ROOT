package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ISysUserTestDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserTestDaoImp
  extends BaseJdbcDAO
  implements ISysUserTestDao
{
  public Pagination queryUserTest(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    String orderby = String.valueOf(params.get("ut_orderby"));
    if (BaseUtil.isEmpty(orderby)) {
      orderby = "ENDTIME_DESC";
    }
    StringBuffer sql = new StringBuffer(256);
    sql.append("select a.t_tid, a.t_name, a.t_duration, a.t_uid, a.t_totalscore, a.t_userscore, a.t_testdate, a.t_timecost, ");
    sql.append("b.u_username, b.u_realname, b.u_branchid, b.u_no, c.b_name from tm_usertest a ");
    sql.append("left join tm_user b on a.t_uid = b.u_id ");
    sql.append("left join tm_branch c on b.u_branchid = c.b_id where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("ut_keywords"))) {
      sql.append(" and (\t b.u_username like concat('%',:ut_keywords,'%') or  b.u_realname like concat('%',:ut_keywords,'%')) ");
    }
    if (BaseUtil.isNotEmpty(params.get("ut_branchid"))) {
      sql.append(" and b.u_branchid=:ut_branchid ");
    }
    if (BaseUtil.isNotEmpty(params.get("ut_startdate"))) {
      sql.append(" and a.t_testdate >= :ut_startdate");
    }
    if (BaseUtil.isNotEmpty(params.get("ut_enddate"))) {
      sql.append(" and a.t_testdate<= :ut_enddate");
    }
    if ("SCORE_DESC".equals(orderby)) {
      sql.append(" order by a.t_userscore desc");
    } else if ("SCORE_ASC".equals(orderby)) {
      sql.append(" order by a.t_userscore asc");
    } else if ("COST_DESC".equals(orderby)) {
      sql.append(" order by a.t_timecost desc");
    } else if ("COST_ASC".equals(orderby)) {
      sql.append(" order by a.t_timecost asc");
    } else if ("ENDTIME_DESC".equals(orderby)) {
      sql.append(" order by a.t_testdate desc");
    } else if ("ENDTIME_ASC".equals(orderby)) {
      sql.append(" order by a.t_testdate asc");
    }
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public Map<String, Object> getUserTestDetail(String uid, String tid)
  {
    String sql = "select a.*, b.u_username, b.u_realname, b.u_branchid, b.u_no from tm_usertest a left join tm_user b on a.t_uid = b.u_id where a.t_uid=? and a.t_tid=?";
    
    return queryForMap(sql, new Object[] { uid, tid });
  }
  
  public int deleteUserTestDetail(String tid)
  {
    String sql = "delete from tm_usertest where t_tid=?";
    return update(sql, new Object[] { tid });
  }
}

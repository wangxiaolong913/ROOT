package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IPaperHistoryDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PaperHistoryDaoImp
  extends BaseJdbcDAO
  implements IPaperHistoryDao
{
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    String orderby = String.valueOf(params.get("orderby"));
    if (BaseUtil.isEmpty(orderby)) {
      orderby = "ENDTIME_DESC";
    }
    StringBuffer sql = new StringBuffer("select a.*, b.u_username, b.u_realname, b.u_no, TIMESTAMPDIFF(MINUTE, a.e_starttime, a.e_endtime) e_timecost, c.b_name, b.u_branchid  from tm_examdata a  left join tm_user b on a.e_uid=b.u_id  left join tm_branch c on b.u_branchid = c.b_id  where a.e_pid=:pid ");
    if (BaseUtil.isNotEmpty(params.get("keywords"))) {
      sql.append(" and (\t\t\t\tb.u_username like concat('%',:keywords,'%') or \t\t\tb.u_realname like concat('%',:keywords,'%') or \t\t\tb.u_no like concat('%',:keywords,'%') \t) ");
    }
    if (BaseUtil.isNotEmpty(params.get("branchid"))) {
      sql.append(" and b.u_branchid=:branchid ");
    }
    if ("SCORE_DESC".equals(orderby)) {
      sql.append(" order by e_score desc");
    } else if ("SCORE_ASC".equals(orderby)) {
      sql.append(" order by e_score asc");
    } else if ("COST_DESC".equals(orderby)) {
      sql.append(" order by e_timecost desc");
    } else if ("COST_ASC".equals(orderby)) {
      sql.append(" order by e_timecost asc");
    } else if ("ENDTIME_DESC".equals(orderby)) {
      sql.append(" order by e_endtime desc");
    } else if ("ENDTIME_ASC".equals(orderby)) {
      sql.append(" order by e_endtime asc");
    }
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public List<Map<String, Object>> export(String pid)
  {
    StringBuffer sql = new StringBuffer("select a.*, b.u_username, b.u_realname, b.u_no,  TIMESTAMPDIFF(MINUTE, a.e_starttime, a.e_endtime) e_timecost, c.b_name  from tm_examdata a  left join tm_user b on a.e_uid=b.u_id  left join tm_branch c on b.u_branchid = c.b_id  where a.e_pid=? and a.e_status=2  order by e_endtime desc");
    
    return queryForList(sql.toString(), new Object[] { pid });
  }
  
  public Map<String, Object> getPaperInfo(String pid)
  {
    String sql = "select * from tm_paper where p_id=?";
    return queryForMap(sql, new Object[] { pid });
  }
  
  public Map<String, Object> getDetail(String eid)
  {
    String sql = "select * from tm_examdata where e_id=?";
    return queryForMap(sql, new Object[] { eid });
  }
  
  public int deleteOneDetail(String eid, String pid, String uid)
  {
    String sqla = "delete from tm_examdata where e_id=?";
    int a = update(sqla, new Object[] { eid });
    
    String sqlb = "delete from tm_paper_random where r_uid=? and r_pid=?";
    update(sqlb, new Object[] { uid, pid });
    
    return a;
  }
  
  public int deleteAllDetail(String pid)
  {
    String sqla = "delete from tm_examdata where e_pid=?";
    String sqlb = "delete from tm_paper_random where r_pid=?";
    int a = update(sqla, new Object[] { pid });
    
    update(sqlb, new Object[] { pid });
    return a;
  }
  
  public int updateCheck(String eid, String content, int score)
  {
    String sql = "update tm_examdata set e_score=?, e_check=? where e_id=?";
    return update(sql, new Object[] { Integer.valueOf(score), content, eid });
  }
  
  public Map<String, Object> getPaperCheckProgress(String pid)
  {
    Map<String, Object> map = new HashMap();
    map.put("testing", Integer.valueOf(0));
    map.put("wait", Integer.valueOf(0));
    map.put("checked", Integer.valueOf(0));
    
    String sql = "select e_status ,count(1) e_nums from tm_examdata where e_pid=? group by e_status";
    List<Map<String, Object>> list = queryForList(sql, new Object[] { pid });
    if (list != null) {
      for (Map<String, Object> m : list)
      {
        String key = String.valueOf(m.get("e_status"));
        String val = String.valueOf(m.get("e_nums"));
        if ("0".equals(key)) {
          key = "testing";
        } else if ("1".equals(key)) {
          key = "wait";
        } else if ("2".equals(key)) {
          key = "checked";
        }
        map.put(key, val);
      }
    }
    return map;
  }
}

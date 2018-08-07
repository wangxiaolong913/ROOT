package com.tom.user.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.user.dao.IUserPaperDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserPaperDaoImp
  extends BaseJdbcDAO
  implements IUserPaperDao
{
  public Map<String, Object> getPaper(String pid)
  {
    String sql = "select * from tm_paper where p_id = ? and p_status = 1";
    return queryForMap(sql, new Object[] { pid });
  }
  
  public int startExam(Map<String, Object> info)
  {
    String sql = "select * from tm_examdata where e_pid=:e_pid and e_uid=:e_uid";
    Map<String, Object> map = queryForMap(sql, info);
    if (map == null)
    {
      sql = "insert into tm_examdata(e_id,e_pid,e_uid,e_starttime,e_ip,e_score,e_status) values(:e_id,:e_pid,:e_uid,now(),:e_ip,0,0)";
      
      return update(sql, info);
    }
    String status = String.valueOf(map.get("e_status"));
    if ("0".equals(status)) {
      return 0;
    }
    return 2;
  }
  
  public Map<String, Object> getRandomPaper(String uid, String pid)
  {
    String sql = "select * from tm_paper_random where r_pid=? and r_uid=?";
    return queryForMap(sql, new Object[] { pid, uid });
  }
  
  public int saveRandomPaper(String uid, String pid, String paperData)
  {
    String sql = "insert into tm_paper_random (r_pid, r_uid, r_detail, r_createdate)  values(?, ?, ?, now())";
    
    return update(sql, new Object[] { pid, uid, paperData });
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    StringBuffer sql = new StringBuffer(256);
    sql.append("select * from tm_paper p where p.p_id in(");
    sql.append("  select n.ln_pid from tm_paper_link_branch n where n.ln_buid = :branchid and n.ln_pid not in (");
    sql.append("    select d.e_pid from tm_examdata d where d.e_status!=0 and d.e_uid = :uid");
    sql.append("  )) ");
    if (BaseUtil.isNotEmpty(params.get("p_name"))) {
      sql.append(" and p.p_name like concat('%',:p_name,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("p_cid"))) {
      sql.append(" and p.p_cid=:p_cid");
    }
    sql.append(" order by p.p_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public Pagination history(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    StringBuffer sql = new StringBuffer(256);
    sql.append("select a.e_id, a.e_pid, a.e_starttime, a.e_endtime, a.e_score, a.e_ip,  TIMESTAMPDIFF(MINUTE, a.e_starttime, a.e_endtime) e_timecost , a.e_status, ");
    sql.append(" b.p_name, b.p_starttime, b.p_endtime, b.p_duration, b.p_total_score, b.p_pass_score, b.p_papertype,  ");
    sql.append(" (CASE WHEN p_showtime >= now() THEN 'n' ELSE 'y' END) showscore ");
    sql.append(" from tm_examdata a");
    sql.append(" left join tm_paper b on a.e_pid = b.p_id");
    sql.append(" where a.e_status!=0 and a.e_uid=:e_uid ");
    if (BaseUtil.isNotEmpty(params.get("p_name"))) {
      sql.append(" and b.p_name like concat('%',:p_name,'%') ");
    }
    if (BaseUtil.isNotEmpty(params.get("p_cid"))) {
      sql.append(" and b.p_cid=:p_cid ");
    }
    sql.append(" order by a.e_endtime desc");
    
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public Map<String, Object> getHistoryDetail(String uid, String eid)
  {
    String sql = "select * from tm_examdata where e_status=2 and e_uid=? and e_id=?";
    return queryForMap(sql, new Object[] { uid, eid });
  }
  
  public Map<String, Object> getExamInfo(String uid, String pid)
  {
    String sql = "select * from tm_examdata where e_uid=? and e_pid=?";
    return queryForMap(sql, new Object[] { uid, pid });
  }
  
  public int userSubmitPaper(Map<String, Object> info)
  {
    String sql = "update tm_examdata set e_endtime=now(),e_status=1 where e_uid=:e_uid and e_pid=:e_pid";
    return update(sql, info);
  }
}

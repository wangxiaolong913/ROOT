package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IAnalysisDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisDaoImp
  extends BaseJdbcDAO
  implements IAnalysisDao
{
  public Pagination queryPaper(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select * from tm_paper p where p.p_status=1 ");
    if (BaseUtil.isNotEmpty(params.get("p_name"))) {
      sql.append(" and p.p_name like concat('%',:p_name,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("p_cid"))) {
      sql.append(" and p.p_cid=:p_cid");
    }
    sql.append(" order by p.p_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public List<Map<String, Object>> getExamHistoryList(String pid)
  {
    String sql = "select e_uid, e_starttime, e_endtime, e_score, e_check from tm_examdata where e_pid=? order by e_score desc";
    return queryForList(sql, new Object[] { pid });
  }
  
  public Pagination AnalyzePaper(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer(256);
    sql.append("select");
    sql.append(" p.p_id, p.p_name, p.p_starttime, p.p_endtime, p.p_duration, p.p_total_score, p.p_pass_score, p.p_papertype , ");
    sql.append(" ifnull(e.e_max_score,0) e_max_score, ifnull(e.e_min_score,0) e_min_score, FORMAT(ifnull(e.e_avg_score,0),2) e_avg_score, ");
    sql.append(" (select count(1) from tm_examdata where e_pid=p.p_id and e_score>=p.p_pass_score) pass_user , ");
    sql.append(" (select count(1) from tm_examdata where e_pid=p.p_id) total_user ");
    sql.append(" from tm_paper p  ");
    sql.append(" left join (select e_pid, max(e_score) e_max_score, min(e_score) e_min_score, avg(e_score) e_avg_score from tm_examdata group by e_pid) e ");
    sql.append(" on p.p_id = e.e_pid ");
    sql.append(" where p.p_status=1 ");
    if (BaseUtil.isNotEmpty(params.get("p_name"))) {
      sql.append(" and p.p_name like concat('%',:p_name,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("p_cid"))) {
      sql.append(" and p.p_cid=:p_cid");
    }
    if (BaseUtil.isNotEmpty(params.get("p_papertype"))) {
      sql.append(" and p.p_papertype=:p_papertype");
    }
    sql.append(" order by p.p_createdate desc");
    
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public Map<String, Object> AnalyzeExam(String pid)
  {
    int passscore = queryForInt("select p_pass_score from tm_paper where p_id=? ", new Object[] { pid });
    
    StringBuffer sql = new StringBuffer(256);
    sql.append("select e_pid, ");
    sql.append("max(e.e_score) e_max_score, ");
    sql.append("min(e.e_score) e_min_score, ");
    sql.append("FORMAT(avg(e.e_score),2) e_avg_score,");
    
    sql.append("count(1) e_total_user_num,");
    sql.append("count(case when e.e_score>=? then 1 else null end ) e_pass_user_num,");
    sql.append("(select count(1) from tm_user where u_branchid in (select ln_buid from tm_paper_link_branch where ln_pid=e.e_pid)) e_should_user_num, ");
    
    sql.append("DATE_FORMAT(max(e.e_endtime),'%Y-%m-%d %T') e_last_submit,");
    sql.append("DATE_FORMAT(min(e.e_endtime),'%Y-%m-%d %T') e_first_submit,");
    sql.append("FORMAT(max(UNIX_TIMESTAMP(e.e_endtime) - UNIX_TIMESTAMP(e.e_starttime))/60,1) e_max_cost,");
    sql.append("FORMAT(min(UNIX_TIMESTAMP(e.e_endtime) - UNIX_TIMESTAMP(e.e_starttime))/60,1) e_min_cost ");
    
    sql.append("from tm_examdata e where e.e_pid = ?");
    
    Map<String, Object> result = queryForMap(sql.toString(), new Object[] { Integer.valueOf(passscore), pid });
    
    StringBuffer sqluser = new StringBuffer(256);
    sqluser.append("select userdata.*, tm_user.u_username, tm_user.u_realname from ( ");
    sqluser.append("(select 'max_score_user' e_dataname, e_uid from tm_examdata where e_pid = ? order by e_score desc limit 0,1) ");
    sqluser.append("union all ");
    sqluser.append("(select 'min_score_user' e_dataname, e_uid from tm_examdata where e_pid = ? order by e_score asc limit 0,1) ");
    sqluser.append("union all ");
    sqluser.append("(select 'last_submit_user' e_dataname, e_uid from tm_examdata where e_pid = ? order by e_endtime desc limit 0,1) ");
    sqluser.append("union all ");
    sqluser.append("(select 'first_submit_user' e_dataname, e_uid from tm_examdata where e_pid = ? order by e_endtime asc limit 0,1) ");
    sqluser.append("union all ");
    sqluser.append("(select 'max_cost_user' e_dataname, e_uid from tm_examdata where e_pid = ? order by e_endtime-e_starttime desc limit 0,1) ");
    sqluser.append("union all ");
    sqluser.append("(select 'min_cost_user' e_dataname, e_uid from tm_examdata where e_pid = ? order by e_endtime-e_starttime asc limit 0,1) ");
    sqluser.append(") userdata ");
    sqluser.append("left join tm_user on userdata.e_uid = tm_user.u_id");
    
    List<Map<String, Object>> lis = queryForList(sqluser.toString(), new Object[] { pid, pid, pid, pid, pid, pid });
    
    result.put("userdata", lis);
    
    return result;
  }
  
  public List<Map<String, Object>> AnalyzeScore(String[] from, String[] to, String pid)
  {
    StringBuffer sql = new StringBuffer(256);
    for (int i = 0; i < from.length; i++)
    {
      int i_from = BaseUtil.getInt(from[i]);
      int i_to = BaseUtil.getInt(to[i]);
      
      sql.append("select '" + i_from + "' s_from,'" + i_to + "' s_to,");
      sql.append("(select count(*) from tm_examdata where e_pid='" + pid + "' and e_score>=" + i_from + " and e_score<" + i_to + ") s_total ");
      if (i < from.length - 1) {
        sql.append(" union ");
      }
    }
    return queryForList(sql.toString());
  }
}

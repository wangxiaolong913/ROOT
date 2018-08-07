package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IPaperDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PaperDaoImp
  extends BaseJdbcDAO
  implements IPaperDao
{
  public int addPaper(Map<String, Object> paper)
  {
    String sql = "insert into tm_paper( p_id,p_name,p_cid,p_status,p_starttime,p_endtime,p_duration,p_showtime,p_total_score,p_pass_score, p_question_order,p_papertype,p_remark,p_data,p_poster,p_createdate,p_modifyor,p_modifydate, p_showkey, p_showmode ) values(:p_id,:p_name,:p_cid,:p_status,:p_starttime,:p_endtime,:p_duration,:p_showtime,:p_total_score,:p_pass_score, :p_question_order,:p_papertype,:p_remark,:p_data,:p_poster,now(),:p_modifyor, now(), :p_showkey, :p_showmode)";
    
    int a = update(sql, paper);
    
    String paperId = String.valueOf(paper.get("p_id"));
    String[] branchIds = paper.get("ln_bid") == null ? null : String.valueOf(paper.get("ln_bid")).split(",");
    String[] userIds = paper.get("ln_uid") == null ? null : String.valueOf(paper.get("ln_uid")).split(",");
    
    updatePaperBranchLink(paperId, branchIds, userIds);
    
    return a;
  }
  
  public int deletePaper(String pid)
  {
    String sql = "delete from tm_paper where p_id=?";
    int i = update(sql, new Object[] { pid });
    update("delete from tm_paper_link_branch where ln_pid=?", new Object[] { pid });
    return i;
  }
  
  public Map<String, Object> getPaper(String pid)
  {
    String sql = "select * from tm_paper where p_id=?";
    return queryForMap(sql, new Object[] { pid });
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select p.*, ifnull(b.total_users,0) total_users from tm_paper p ");
    sql.append(" left join (select e_pid, count(1) total_users from tm_examdata group by e_pid) b on p.p_id = b.e_pid ");
    sql.append(" where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("p_name"))) {
      sql.append(" and p.p_name like concat('%',:p_name,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("p_status"))) {
      sql.append(" and p.p_status=:p_status");
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
  
  public int updatePaper(Map<String, Object> paper)
  {
    String sql = "update tm_paper  set p_name=:p_name, p_cid=:p_cid, p_status=:p_status, p_starttime=:p_starttime, p_endtime=:p_endtime, p_duration=:p_duration,  p_showtime=:p_showtime,p_data=:p_data, p_question_order=:p_question_order ,p_papertype=:p_papertype, p_remark=:p_remark, p_modifyor=:p_modifyor, p_modifydate=now(), p_showkey=:p_showkey, p_showmode=:p_showmode  where p_id=:p_id";
    
    int a = update(sql, paper);
    
    String paperId = String.valueOf(paper.get("p_id"));
    String[] branchIds = paper.get("ln_bid") == null ? null : String.valueOf(paper.get("ln_bid")).split(",");
    String[] userIds = paper.get("ln_uid") == null ? null : String.valueOf(paper.get("ln_uid")).split(",");
    
    updatePaperBranchLink(paperId, branchIds, userIds);
    
    return a;
  }
  
  public int updatePaperDetail(String pid, int totalscore, int passscore, String data)
  {
    String sql = "update tm_paper set p_data=?, p_total_score=?, p_pass_score=? where p_id=?";
    return update(sql, new Object[] { data, Integer.valueOf(totalscore), Integer.valueOf(passscore), pid });
  }
  
  public int getUserNumbers(String pid)
  {
    String sql = "select count(1) total from tm_examdata where e_pid=?";
    return queryForInt(sql, new Object[] { pid });
  }
  
  private int updatePaperBranchLink(String paperId, String[] branchIds, String[] userIds)
  {
    String sql = "delete from tm_paper_link_branch where ln_pid = ?";
    update(sql, new Object[] { paperId });
    
    List<List<Object>> list = new ArrayList();
    String[] arrayOfString;
    int j;
    int i;
    if ((userIds != null) && (userIds.length > 0))
    {
      sql = "insert into tm_paper_link_branch(ln_pid, ln_buid, ln_type) values(?,?,0)";
      j = (arrayOfString = userIds).length;
      for (i = 0; i < j; i++)
      {
        String uid = arrayOfString[i];
        List<Object> ls = new ArrayList();
        ls.add(paperId);
        ls.add(uid);
        list.add(ls);
      }
      batchUpdate(sql, list);
    }
    if ((branchIds != null) && (branchIds.length > 0))
    {
      list = new ArrayList();
      sql = "insert into tm_paper_link_branch(ln_pid, ln_buid, ln_type) values(?,?,1)";
      j = (arrayOfString = branchIds).length;
      for (i = 0; i < j; i++)
      {
        String bid = arrayOfString[i];
        List<Object> ls = new ArrayList();
        ls.add(paperId);
        ls.add(bid);
        list.add(ls);
      }
      batchUpdate(sql, list);
    }
    return 1;
  }
  
  public List<Map<String, Object>> getPaperLink(String pid)
  {
    String sql = "select * from tm_paper_link_branch where ln_pid = ?";
    return queryForList(sql, new Object[] { pid });
  }
}

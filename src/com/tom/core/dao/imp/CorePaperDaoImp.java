package com.tom.core.dao.imp;

import com.tom.core.dao.ICorePaperDao;
import com.tom.util.BaseJdbcDAO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CorePaperDaoImp
  extends BaseJdbcDAO
  implements ICorePaperDao
{
  public Map<String, Object> getPaper(String pid)
  {
    String sql = "select * from tm_paper where p_id = ?";
    return queryForMap(sql, new Object[] { pid });
  }
  
  public List<Map<String, Object>> getAllPapers()
  {
    String sql = "select * from tm_paper where p_status = ?";
    return queryForList(sql, new Object[] { Integer.valueOf(1) });
  }
  
  public Map<String, Object> getQuestion(String qid)
  {
    String sql = "select * from tm_question where q_id = ?";
    return queryForMap(sql, new Object[] { qid });
  }
  
  public List<Map<String, Object>> getQuestions(String dbid, int qtype, int level, int rows)
  {
    String sql = "select q_id,q_dbid,q_type,q_level,q_from,q_status,tm_strip_tags(q_content) q_content  from tm_question where q_dbid=? and q_type=? and q_level=? order by rand() limit 0,?";
    
    return queryForList(sql, new Object[] { dbid, Integer.valueOf(qtype), Integer.valueOf(level), Integer.valueOf(rows) });
  }
  
  public int saveUserRandomPaper(String uid, String pid, String paperData)
  {
    String sql = "insert into tm_paper_random (r_pid, r_uid, r_detail, r_createdate)  values(?, ?, ?, now())";
    
    return update(sql, new Object[] { pid, uid, paperData });
  }
  
  public Map<String, Object> getUserRandomPaper(String uid, String pid)
  {
    String sql = "select * from tm_paper_random where r_pid=? and r_uid=?";
    return queryForMap(sql, new Object[] { pid, uid });
  }
}

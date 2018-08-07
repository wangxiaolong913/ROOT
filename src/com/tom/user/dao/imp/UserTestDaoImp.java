package com.tom.user.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.user.dao.IUserTestDao;
import com.tom.util.BaseJdbcDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserTestDaoImp
  extends BaseJdbcDAO
  implements IUserTestDao
{
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    String sql = "select * from tm_usertest where t_uid=:t_uid order by t_testdate desc";
    return queryForList(sql, params, pagesize, currentPageNo);
  }
  
  public List<Map<String, Object>> queryQuestions(String dbid, String qtype, int level, int rows)
  {
    String sql = "select q_id,q_dbid,q_type,q_level,q_from,q_status,tm_strip_tags(q_content) q_content  from tm_question where q_dbid=? and q_type=? and q_level=? and q_status=1 order by rand() limit 0,?";
    
    return queryForList(sql, new Object[] { dbid, qtype, Integer.valueOf(level), Integer.valueOf(rows) });
  }
  
  public int saveTestPaper(Map<String, Object> map)
  {
    String sql = "insert into tm_usertest(t_tid, t_name, t_uid, t_totalscore, t_userscore, t_paper, t_answer, t_check, t_testdate, t_timecost, t_duration) values(:t_tid, :t_name, :t_uid, :t_totalscore, :t_userscore, :t_paper, :t_answer, :t_check, now(), :t_timecost, :t_duration)";
    
    return update(sql, map);
  }
  
  public Map<String, Object> getTestPaper(String uid, String testid)
  {
    String sql = "select * from tm_usertest where t_uid=? and t_tid=?";
    return queryForMap(sql, new Object[] { uid, testid });
  }
  
  public int deleteTestPaper(String uid, String testid)
  {
    String sql = "delete from tm_usertest where t_uid=? and t_tid=?";
    return update(sql, new Object[] { uid, testid });
  }
  
  public List<Map<String, Object>> getAllQDBS()
  {
    String sql = "select * from tm_question_db where d_status=1 order by d_createdate";
    return queryForList(sql);
  }
}

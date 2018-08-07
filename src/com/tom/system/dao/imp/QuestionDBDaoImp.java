package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IQuestionDBDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDBDaoImp
  extends BaseJdbcDAO
  implements IQuestionDBDao
{
  public int addQuestionDB(Map<String, Object> db)
  {
    String sql = "insert into tm_question_db(d_id,d_name,d_logo,d_remark,d_status,d_poster,d_createdate,d_modifyor,d_modifydate) values(:d_id,:d_name,:d_logo,:d_remark,:d_status, :d_poster,now(),:d_modifyor,now())";
    
    return update(sql, db);
  }
  
  public int deleteQuestionDB(String dbid)
  {
    String sql = "delete from tm_question_db where d_id=?";
    return update(sql, new Object[] { dbid });
  }
  
  public int updateQuestionDB(Map<String, Object> db)
  {
    String sql = "update tm_question_db set d_name=:d_name,d_logo=:d_logo,d_remark=:d_remark,d_status=:d_status,  d_modifyor=:d_modifyor,d_modifydate=now() where d_id=:d_id";
    
    return update(sql, db);
  }
  
  public Map<String, Object> getQuestionDB(String dbid)
  {
    String sql = "select * from tm_question_db where d_id=?";
    return queryForMap(sql, new Object[] { dbid });
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*,ifnull(c.nums,0) d_nums from tm_question_db a");
    sql.append(" left join (select count(1) nums, q_dbid from tm_question b group by b.q_dbid) c on c.q_dbid = a.d_id");
    sql.append(" where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("d_name"))) {
      sql.append("and d_name like concat('%',:d_name,'%')");
    }
    sql.append(" order by a.d_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int getQuestionNums(String dbid)
  {
    String sql = "select count(1) total from tm_question where q_dbid=?";
    int rows = queryForInt(sql, new Object[] { dbid });
    return rows;
  }
  
  public List<Map<String, Object>> getAllDBS()
  {
    String sql = "select * from tm_question_db where d_status=1 order by d_createdate";
    return queryForList(sql);
  }
  
  public List<Map<String, Object>> CalculateQuestions(String dbid)
  {
    String sql = "select q_type, count(1) q_nums from tm_question where q_dbid = ? group by q_type";
    return queryForList(sql, new Object[] { dbid });
  }
}

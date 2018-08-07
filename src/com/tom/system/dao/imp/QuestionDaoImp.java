package com.tom.system.dao.imp;

import com.tom.model.paper.QBlank;
import com.tom.model.system.Pagination;
import com.tom.system.dao.IQuestionDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import com.tom.util.QuestionImportTxtHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDaoImp
  extends BaseJdbcDAO
  implements IQuestionDao
{
  public int addQuestion(Map<String, Object> question)
  {
    String sql = "insert into tm_question  (q_id,q_dbid,q_type,q_level,q_from,q_status,q_content,q_key, q_resolve,q_poster,q_createdate,q_modifyor,q_modifydate,q_data)  values(:q_id,:q_dbid,:q_type,:q_level,:q_from,:q_status,:q_content,:q_key,:q_resolve,:q_poster,now(),:q_modifyor,now(),:q_data)";
    
    return update(sql, question);
  }
  
  public int importQuestions(List<Map<String, Object>> list)
  {
    if ((list == null) || (list.isEmpty())) {
      return 0;
    }
    List<List<Object>> data = new ArrayList();
    for (Map<String, Object> map : list)
    {
      List<Object> ls = new ArrayList();
      
      String qtype = String.valueOf(map.get("q_type"));
      String qkey = String.valueOf(map.get("q_key"));
      
      ls.add(map.get("q_id"));
      ls.add(map.get("q_dbid"));
      ls.add(qtype);
      ls.add(map.get("q_level"));
      ls.add(map.get("q_from"));
      ls.add(map.get("q_status"));
      ls.add(map.get("q_content"));
      if ("2".equals(qtype))
      {
        qkey = qkey.replace(",", "");
      }
      else if ("3".equals(qtype))
      {
        qkey = (com.tom.model.ModelHelper.FLAG_VAL_JUDGMENT_YES[0].equals(qkey)) || 
          (com.tom.model.ModelHelper.FLAG_VAL_JUDGMENT_YES[1].equals(qkey)) ? "Y" : "N";
      }
      else if ("4".equals(qtype))
      {
        List<QBlank> lis = QuestionImportTxtHelper.formatBlanks(qkey);
        qkey = "";
        if (lis != null) {
          for (QBlank blank : lis) {
            qkey = qkey + "," + blank.getValue();
          }
        }
        if (qkey.startsWith(",")) {
          qkey = qkey.substring(1);
        }
      }
      ls.add(qkey);
      
      ls.add(map.get("q_resolve"));
      ls.add(map.get("q_poster"));
      ls.add(map.get("q_modifyor"));
      ls.add(map.get("q_data"));
      data.add(ls);
    }
    String sql = "insert into tm_question  (q_id,q_dbid,q_type,q_level,q_from,q_status,q_content,q_key,q_resolve, q_poster,q_createdate,q_modifyor,q_modifydate,q_data)  values(?,?,?,?,?,?,?,?,?,?,now(),?,now(),?)";
    
    int[] rows = batchUpdate(sql, data);
    return rows == null ? 0 : rows.length;
  }
  
  public int deleteQuestion(String qid)
  {
    String sql = "delete from tm_question where q_id=?";
    return update(sql, new Object[] { qid });
  }
  
  public boolean hasBeenUsed(String qid)
  {
    String sql = "select count(p_id) from tm_paper where p_data like concat('%',?,'%')";
    int rows = queryForInt(sql, new Object[] { qid });
    if (rows > 0) {
      return true;
    }
    sql = "select count(r_id) from tm_paper_random t where t.r_detail like concat('%',?,'%')";
    rows = queryForInt(sql, new Object[] { qid });
    if (rows > 0) {
      return true;
    }
    return false;
  }
  
  public int updateQuestion(Map<String, Object> question)
  {
    String sql = "update tm_question set q_dbid=:q_dbid, q_level=:q_level,  q_from=:q_from, q_status=:q_status, q_content=:q_content, q_key=:q_key,  q_resolve=:q_resolve, q_modifyor=:q_modifyor, q_modifydate=now(), q_data=:q_data  where q_id=:q_id";
    
    return update(sql, question);
  }
  
  public Map<String, Object> getQuestion(String qid)
  {
    String sql = "select * from tm_question where q_id=?";
    return queryForMap(sql, new Object[] { qid });
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.q_id, a.q_dbid, a.q_type, a.q_level, a.q_from, a.q_status, tm_strip_tags(a.q_content) q_content ,  a.q_resolve, a.q_poster, DATE_FORMAT(a.q_createdate,'%Y-%m-%d %T') q_createdate, a.q_modifyor, DATE_FORMAT(a.q_modifydate,'%Y-%m-%d %T') q_modifydate, b.d_name q_qdbname from tm_question a left join tm_question_db b on a.q_dbid = b.d_id where 1=1 ");
    
    StringBuffer count_sql = new StringBuffer("select count(1) from tm_question a where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("q_dbid")))
    {
      sql.append(" and a.q_dbid = :q_dbid ");
      count_sql.append(" and a.q_dbid = :q_dbid ");
    }
    if (BaseUtil.isNotEmpty(params.get("q_type")))
    {
      sql.append(" and a.q_type = :q_type ");
      count_sql.append(" and a.q_type = :q_type ");
    }
    if (BaseUtil.isNotEmpty(params.get("q_level")))
    {
      sql.append(" and a.q_level = :q_level ");
      count_sql.append(" and a.q_level = :q_level ");
    }
    if (BaseUtil.isNotEmpty(params.get("q_from")))
    {
      sql.append(" and a.q_from = :q_from ");
      count_sql.append(" and a.q_from = :q_from ");
    }
    if (BaseUtil.isNotEmpty(params.get("q_status")))
    {
      sql.append(" and a.q_status = :q_status ");
      count_sql.append(" and a.q_status = :q_status ");
    }
    if (BaseUtil.isNotEmpty(params.get("q_content")))
    {
      sql.append(" and a.q_content like concat('%',:q_content,'%')");
      count_sql.append(" and a.q_content like concat('%',:q_content,'%')");
    }
    sql.append(" order by a.q_createdate desc");
    return queryForList(sql.toString(), count_sql.toString(), params, pagesize, currentPageNo);
  }
  
  public List<Map<String, Object>> queryQuestions(String dbid, String qtype, int level, int rows)
  {
    String sql = "select q_id,q_dbid,q_type,q_level,q_from,q_status,tm_strip_tags(q_content) q_content  from tm_question where q_dbid=? and q_type=? and q_level=? order by rand() limit 0,?";
    
    return queryForList(sql, new Object[] { dbid, qtype, Integer.valueOf(level), Integer.valueOf(rows) });
  }
}

package com.tom.user.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.user.dao.IUserCollectionDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserCollectionDaoImp
  extends BaseJdbcDAO
  implements IUserCollectionDao
{
  public int addCollectionType(Map<String, Object> type)
  {
    String sql = "select count(t_id) from tm_user_collection_type where t_uid=? ";
    int r = queryForInt(sql, new Object[] { type.get("t_uid") });
    if (r >= 10) {
      return 2;
    }
    sql = "insert into tm_user_collection_type(t_id,t_name,t_uid,t_createdate)  values(:t_id,:t_name,:t_uid,now())";
    
    return update(sql, type);
  }
  
  public int deleteCollectionType(String uid, String id)
  {
    String sql = "delete from tm_user_collection_type where t_id=? and t_uid=?";
    return update(sql, new Object[] { id, uid });
  }
  
  public int updateCollectionType(Map<String, Object> type)
  {
    String sql = "update tm_user_collection_type set t_name=:t_name where t_id=:t_id and t_uid=:t_uid";
    return update(sql, type);
  }
  
  public Map<String, Object> getCollectionType(String uid, String id)
  {
    String sql = "select * from tm_user_collection_type where t_id=? and t_uid=?";
    return queryForMap(sql, new Object[] { id, uid });
  }
  
  public List<Map<String, Object>> getAllCollectionType(String uid)
  {
    String sql = "select a.*, ifnull(b.total,0) total from tm_user_collection_type a  left join (select c_tid,count(c_tid) total from tm_user_collection where c_uid=? group by c_tid) b  on a.t_id = b.c_tid where a.t_uid=? order by t_createdate desc";
    
    return queryForList(sql, new Object[] { uid, uid });
  }
  
  public Pagination queryCollectionType(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select * from tm_user_collection_type where t_uid=:t_uid order by t_createdate desc");
    
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int addCollection(Map<String, Object> collection)
  {
    String sql = "select count(c_id) from tm_user_collection where c_uid=? and c_qid=?";
    int r = queryForInt(sql, new Object[] { collection.get("c_uid"), collection.get("c_qid") });
    if (r > 0) {
      return 2;
    }
    sql = "insert into tm_user_collection(c_id, c_tid, c_uid, c_qid, c_creatdate, c_userkey, c_remark)  values(:c_id, :c_tid, :c_uid, :c_qid, now(), :c_userkey, :c_remark)";
    
    return update(sql, collection);
  }
  
  public int deleteCollection(String uid, String id)
  {
    String sql = "delete from tm_user_collection where c_id=? and c_uid=?";
    return update(sql, new Object[] { id, uid });
  }
  
  public int clearCollections(String uid)
  {
    String sql = "delete from tm_user_collection where c_uid=?";
    return update(sql, new Object[] { uid });
  }
  
  public Map<String, Object> getCollection(String uid, String id)
  {
    String sql = "select * from tm_user_collection where c_id=? and c_uid=?";
    return queryForMap(sql, new Object[] { id, uid });
  }
  
  public Pagination queryCollection(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    StringBuffer sql = new StringBuffer(" select a.c_id, a.c_tid, a.c_uid, a.c_qid, DATE_FORMAT(a.c_creatdate,'%Y-%m-%d %T') c_creatdate, a.c_userkey, a.c_remark, c.q_key, c.q_data, c.q_type,  ifnull(b.t_name,'default') t_name , tm_strip_tags(c.q_content) q_content from tm_user_collection a   left join tm_user_collection_type b on a.c_tid = b.t_id left join tm_question c on a.c_qid = c.q_id where a.c_uid=:c_uid ");
    if (BaseUtil.isNotEmpty(params.get("c_tid"))) {
      sql.append(" and a.c_tid=:c_tid");
    }
    sql.append(" order by a.c_creatdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int getCollectionNumbers(String typeid)
  {
    String sql = "select count(1) total from tm_user_collection where c_tid=?";
    return queryForInt(sql, new Object[] { typeid });
  }
}

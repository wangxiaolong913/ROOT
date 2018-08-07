package com.tom.user.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.user.dao.IUCourseDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UCourseDaoImp
  extends BaseJdbcDAO
  implements IUCourseDao
{
  public Map<String, Object> getCourse(String courseid)
  {
    String sql = "select * from tm_course where c_id=? and c_status='1'";
    return queryForMap(sql, new Object[] { courseid });
  }
  
  public Pagination queryCourse(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*, b.ca_name, c.t_name from tm_course a  left join tm_course_category b on a.c_caid = b.ca_id  left join tm_course_teacher c on a.c_tid = c.t_id  where a.c_status='1' ");
    if (BaseUtil.isNotEmpty(params.get("c_name"))) {
      sql.append(" and a.c_name like concat('%',:c_name,'%')");
    }
    if (BaseUtil.isNotEmpty(params.get("c_tid"))) {
      sql.append(" and a.c_tid=:c_tid");
    }
    if (BaseUtil.isNotEmpty(params.get("c_caid"))) {
      sql.append(" and a.c_caid=:c_caid");
    }
    sql.append(" order by a.c_postdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public Pagination myCourseStudyHistory(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer(128);
    sql.append(" select a.*, b.c_name, b.c_logo from(   select a.p_cid, max(a.p_startdate) p_startdate from tm_course_progress_history a   where a.p_uid=:p_uid  group by a.p_cid ) a  left join tm_course b on a.p_cid = b.c_id  order by a.p_startdate desc ");
    
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int markCourseProgress(String uid, String courseid, String chapterid, String lessonid)
  {
    String sqla = "select * from tm_course_progress_history a where a.p_cid=? and a.p_sid=? and a.p_leid=? and p_uid=?";
    Map<String, Object> lsa = queryForMap(sqla, new Object[] { courseid, chapterid, lessonid, uid });
    if (lsa == null)
    {
      String sqlb = "insert into tm_course_progress_history(p_id, p_cid, p_sid, p_leid, p_uid, p_startdate) values(?, ?, ?, ?, ?, now())";
      
      return update(sqlb, new Object[] { BaseUtil.generateId(), courseid, chapterid, lessonid, uid });
    }
    String sqlb = "update tm_course_progress_history a set a.p_enddate = now()  where a.p_cid=? and a.p_sid=? and a.p_leid=? and p_uid=?";
    
    return update(sqlb, new Object[] { courseid, chapterid, lessonid, uid });
  }
  
  public List<Map<String, Object>> getCourseProgress(String uid, String courseid)
  {
    String sql = "select * from tm_course_progress_history a where a.p_cid=? and a.p_uid=?";
    return queryForList(sql, new Object[] { courseid, uid });
  }
  
  public int postComment(Map<String, Object> comment)
  {
    String sql = "insert into tm_course_comments(c_id,c_cid,c_uid,c_content,c_postdate,c_ip,c_status) values(:c_id,:c_cid,:c_uid,:c_content,now(),:c_ip,:c_status)";
    
    return update(sql, comment);
  }
  
  public int deleteComment(String uid, String commentid)
  {
    String sql = "delete from tm_course_comments where c_id=? and c_uid=?";
    return update(sql, new Object[] { commentid, uid });
  }
  
  public Pagination queryComment(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer(128);
    sql.append("select * from tm_course_comments a where a.c_cid=:c_cid order by a.c_postdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int postQuestion(Map<String, Object> question)
  {
    String sql = "insert into tm_course_qa(q_id,q_cid,q_uid,q_question,q_postdate,q_status) values(:q_id,:q_cid,:q_uid,:q_question,now(),0)";
    
    return update(sql, question);
  }
  
  public int deleteQuestion(String uid, String questionid)
  {
    String sql = "delete from tm_course_qa where q_id=? and q_uid=?";
    return update(sql, new Object[] { questionid, uid });
  }
  
  public Pagination queryQuestion(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer(128);
    sql.append("select * from tm_course_qa a where a.q_cid=:q_cid order by a.q_postdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int addNote(Map<String, Object> note)
  {
    String sql = "insert into tm_course_note(n_id, n_cid, n_sid, n_leid, n_uid, n_content, n_postdate, n_status) values(:n_id, :n_cid, :n_sid, :n_leid, :n_uid, :n_content, now(), 1)";
    
    return update(sql, note);
  }
  
  public int deleteNote(String uid, String noteid)
  {
    String sql = "delete from tm_course_note where n_id=? and n_uid=?";
    return update(sql, new Object[] { noteid, uid });
  }
  
  public Pagination queryNote(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    StringBuffer sql = new StringBuffer(128);
    sql.append(" select * from tm_course_note a where a.n_cid=:n_cid and a.n_uid=:n_uid ");
    sql.append(" and a.n_uid=:n_uid ");
    sql.append(" order by a.q_postdate desc ");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int addFavorite(String uid, String courseid)
  {
    String sql = "insert into tm_course_favorites(f_id, f_cid, f_uid, f_favdate) values(?, ?, ?, now())";
    
    return update(sql, new Object[] { BaseUtil.generateId(), courseid, uid });
  }
  
  public int deleteFavorite(String uid, String favid)
  {
    String sql = "delete from tm_course_favorites where f_id=? and f_uid=?";
    return update(sql, new Object[] { favid, uid });
  }
  
  public Pagination queryFavorite(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer(128);
    sql.append(" select a.*, b.c_name from tm_course_favorites a ");
    sql.append(" left join tm_course b on a.f_cid = b.c_id ");
    sql.append(" where a.f_uid=:f_uid ");
    sql.append(" order by a.f_favdate desc ");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public List<Map<String, Object>> getAllCategories()
  {
    String sql = "select * from tm_course_category order by ca_postdate desc";
    return queryForList(sql);
  }
  
  public List<Map<String, Object>> getAllTeachers()
  {
    String sql = "select * from tm_course_teacher order by t_createdate desc";
    return queryForList(sql);
  }
  
  public Map<String, Object> getTeacher(String teacherid)
  {
    String sql = "select * from tm_course_teacher where t_id=?";
    return queryForMap(sql, new Object[] { teacherid });
  }
}

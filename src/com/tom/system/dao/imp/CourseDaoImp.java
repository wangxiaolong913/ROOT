package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ICourseDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImp
  extends BaseJdbcDAO
  implements ICourseDao
{
  public int addCourse(Map<String, Object> course)
  {
    String sql = "insert into tm_course(c_id,c_name,c_logo,c_tid,c_caid,c_score,c_type,c_introduce,c_postdate, c_modifydate,c_status,c_stars,c_data,c_poster,c_modifier) values(:c_id,:c_name,:c_logo,:c_tid,:c_caid,:c_score,:c_type,:c_introduce,now(), now(),:c_status,:c_stars,:c_data,:c_poster,:c_modifier)";
    
    return update(sql, course);
  }
  
  public int deleteCourse(String courseid)
  {
    String sql = "delete from tm_course where c_id=?";
    return update(sql, new Object[] { courseid });
  }
  
  public Map<String, Object> getCourse(String courseid)
  {
    String sql = "select * from tm_course where c_id=?";
    return queryForMap(sql, new Object[] { courseid });
  }
  
  public Pagination queryCourse(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*, b.ca_name, c.t_name from tm_course a  left join tm_course_category b on a.c_caid = b.ca_id  left join tm_course_teacher c on a.c_tid = c.t_id ");
    if (BaseUtil.isNotEmpty(params.get("c_name"))) {
      sql.append("and a.c_name=:c_name");
    }
    sql.append("order by a.c_postdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int updateCourse(Map<String, Object> course)
  {
    String sql = "update tm_course set c_name=:c_name,c_logo=:c_logo,c_tid=:c_tid,c_caid=:c_caid,  c_score=:c_score, c_type=:c_type, c_introduce=:c_introduce, c_modifydate=now(), c_status=:c_status, c_stars=:c_stars, c_data=:c_data, c_modifier=:c_modifier where c_id=:c_id";
    
    return update(sql, course);
  }
  
  public int getUsers(String courseid)
  {
    String sql = "select count(1) total from tm_course_progress_history where p_cid=?";
    return queryForInt(sql, new Object[] { courseid });
  }
  
  public Pagination queryCourseLearnRecord(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer(128);
    sql.append(" select a.*, b.u_id, b.u_username, b.u_photo, b.u_realname, b.u_branchid, c.b_name  from ( ");
    sql.append(" select p_uid, max(p_startdate) p_startdate from tm_course_progress_history where p_cid = :p_cid group by p_uid ");
    sql.append(" ) a  ");
    sql.append(" left join tm_user b on a.p_uid = b.u_id ");
    sql.append(" left join tm_branch c on b.u_branchid = c.b_id ");
    
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int clearLearnRecord(String cid)
  {
    String sql = "delete from tm_course_progress_history where p_cid=?";
    return update(sql, new Object[] { cid });
  }
}

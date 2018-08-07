package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ICourseTeacherDao;
import com.tom.util.BaseJdbcDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CourseTeacherDaoImp
  extends BaseJdbcDAO
  implements ICourseTeacherDao
{
  public int addTeacher(Map<String, Object> teacher)
  {
    String sql = "insert into tm_course_teacher(t_id,t_name,t_phone,t_email,t_photo,t_info,t_createdate,t_modifydate) values(:t_id,:t_name,:t_phone,:t_email,:t_photo,:t_info,now(),now())";
    
    return update(sql, teacher);
  }
  
  public int deleteTeacher(String teacherid)
  {
    String sql = "delete from tm_course_teacher where t_id=?";
    return update(sql, new Object[] { teacherid });
  }
  
  public Map<String, Object> getTeacher(String teacherid)
  {
    String sql = "select * from tm_course_teacher where t_id=?";
    return queryForMap(sql, new Object[] { teacherid });
  }
  
  public Pagination queryTeacher(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*, b.t_totalcourse from tm_course_teacher a   left join (select count(1) t_totalcourse, c_tid from tm_course group by tm_course.c_tid) b  on a.t_id = b.c_tid ");
    
    sql.append(" order by a.t_createdate desc ");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int updateTeacher(Map<String, Object> teacher)
  {
    String sql = "update tm_course_teacher set t_name=:t_name, t_phone=:t_phone,  t_email=:t_email, t_photo=:t_photo, t_info=:t_info, t_modifydate=now()  where t_id=:t_id";
    
    return update(sql, teacher);
  }
  
  public int getCourses(String teacherid)
  {
    String sql = "select count(1) total from tm_course where c_tid=?";
    return queryForInt(sql, new Object[] { teacherid });
  }
  
  public List<Map<String, Object>> getAllTeachers()
  {
    String sql = "select * from tm_course_teacher order by t_createdate desc";
    return queryForList(sql);
  }
}

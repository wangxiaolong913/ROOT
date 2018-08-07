package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.ICourseCategoryDao;
import com.tom.util.BaseJdbcDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class CourseCategoryDaoImp
  extends BaseJdbcDAO
  implements ICourseCategoryDao
{
  public int addCategory(Map<String, Object> category)
  {
    String sql = "insert into tm_course_category(ca_id, ca_name, ca_desc, ca_logo, ca_pid, ca_status, ca_order, ca_postdate) values(:ca_id, :ca_name, :ca_desc, :ca_logo, :ca_pid, :ca_status, :ca_order, now())";
    
    return update(sql, category);
  }
  
  public int deleteCategory(String categoryid)
  {
    String sql = "delete from tm_course_category where ca_id=?";
    return update(sql, new Object[] { categoryid });
  }
  
  public Map<String, Object> getCategory(String categoryid)
  {
    String sql = "select * from tm_course_category where ca_id=?";
    return queryForMap(sql, new Object[] { categoryid });
  }
  
  public Pagination queryCategory(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*, ifnull(b.c_totalcourse,0) c_totalcourse from tm_course_category a   left join (select count(1) c_totalcourse, c_caid from tm_course group by tm_course.c_caid) b  on a.ca_id = b.c_caid ");
    
    sql.append(" order by a.ca_order desc, a.ca_postdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int updateCategory(Map<String, Object> category)
  {
    String sql = "update tm_course_category set ca_name=:ca_name, ca_desc=:ca_desc, ca_logo=:ca_logo,  ca_pid=:ca_pid, ca_status=:ca_status, ca_order=:ca_order  where ca_id=:ca_id";
    
    return update(sql, category);
  }
  
  public int getCourses(String categoryid)
  {
    String sql = "select count(1) total from tm_course where c_caid=?";
    return queryForInt(sql, new Object[] { categoryid });
  }
  
  public List<Map<String, Object>> getAllCategories()
  {
    String sql = "select * from tm_course_category order by ca_postdate desc";
    return queryForList(sql);
  }
}

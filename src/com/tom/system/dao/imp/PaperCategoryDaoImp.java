package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IPaperCategoryDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PaperCategoryDaoImp
  extends BaseJdbcDAO
  implements IPaperCategoryDao
{
  public int addPaperCategory(Map<String, Object> category)
  {
    String sql = "insert into tm_paper_category(c_id,c_name,c_remark,c_status,c_poster,c_createdate,c_modifyor,c_modifydate) values(:c_id,:c_name,:c_remark,:c_status,:c_poster,now(),:c_poster,now())";
    
    return update(sql, category);
  }
  
  public int deletePaperCategory(String cid)
  {
    String sql = "delete from tm_paper_category where c_id=?";
    return update(sql, new Object[] { cid });
  }
  
  public int updatePaperCategory(Map<String, Object> category)
  {
    String sql = "update tm_paper_category set  c_name=:c_name,c_remark=:c_remark,c_status=:c_status,c_modifyor=:c_modifyor where c_id=:c_id";
    
    return update(sql, category);
  }
  
  public Map<String, Object> getPaperCategory(String cid)
  {
    String sql = "select * from tm_paper_category where c_id=?";
    return queryForMap(sql, new Object[] { cid });
  }
  
  public List<Map<String, Object>> getAllPaperCategorys()
  {
    String sql = "select * from tm_paper_category where c_status=1 order by c_createdate desc";
    return queryForList(sql);
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select * from tm_paper_category where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("c_name"))) {
      sql.append(" and c_name like concat('%',:c_name,'%')");
    }
    sql.append(" order by c_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int getNumbers(String cid)
  {
    String sql = "select count(1) total from tm_paper where p_cid=?";
    return queryForInt(sql, new Object[] { cid });
  }
}

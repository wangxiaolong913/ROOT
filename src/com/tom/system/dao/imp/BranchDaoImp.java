package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IBranchDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class BranchDaoImp
  extends BaseJdbcDAO
  implements IBranchDao
{
  public int addBranch(Map<String, Object> branch)
  {
    String sql = "insert into tm_branch(b_id,b_name,b_pid,b_remark,b_status,b_order, b_poster, b_createdate, b_modifyor, b_modifydate) values(:b_id, :b_name, :b_pid, :b_remark, :b_status, :b_order, :b_poster, now(), :b_poster, now())";
    
    return update(sql, branch);
  }
  
  public int deleteBranch(String branchid)
  {
    String sql = "delete from tm_branch where b_id=?";
    return update(sql, new Object[] { branchid });
  }
  
  public List<Map<String, Object>> getAllBranchs()
  {
    String sql = "select * from tm_branch where b_status=1";
    return queryForList(sql);
  }
  
  public Map<String, Object> getBranch(String branchid)
  {
    String sql = "select * from tm_branch where b_id=?";
    return queryForMap(sql, new Object[] { branchid });
  }
  
  public int getUsersNumber(String branchid)
  {
    String sql = "select count(1) total from tm_user where u_branchid=?";
    return queryForInt(sql, new Object[] { branchid });
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*, ifnull(b.total,0) total from tm_branch a  left join (select count(1) total, u_branchid from tm_user group by tm_user.u_branchid) b  on a.b_id = b.u_branchid  where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("b_name"))) {
      sql.append("and a.b_name=:b_name");
    }
    sql.append("order by a.b_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int updateBranch(Map<String, Object> branch)
  {
    String sql = "update tm_branch set b_name=:b_name, b_pid=:b_pid, b_remark=:b_remark, b_status=:b_status, b_order=:b_order, b_modifyor=:b_modifyor, b_modifydate=now() where b_id=:b_id";
    
    return update(sql, branch);
  }
}

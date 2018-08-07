package com.tom.core.dao.imp;

import com.tom.core.dao.ICorePaperCheckDao;
import com.tom.util.BaseJdbcDAO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CorePaperCheckDaoImp
  extends BaseJdbcDAO
  implements ICorePaperCheckDao
{
  public int saveUserPaper(List<List<Object>> list)
  {
    if ((list == null) || (list.isEmpty())) {
      return 0;
    }
    String sql = "update tm_examdata set e_data=?, e_check=?, e_score=?, e_status=2 where e_uid=? and e_pid=? and e_status=1";
    int[] rows = batchUpdate(sql, list);
    return rows == null ? 0 : rows.length;
  }
}

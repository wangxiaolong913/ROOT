package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IVoteDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class VoteDaoImp extends BaseJdbcDAO implements IVoteDao {
	public int addVote(Map<String, Object> vote) {
		String sql = "insert into tm_vote(v_id,v_title,v_body,v_starttime,v_endtime,v_status,v_data,v_poster,v_createdate) values(:v_id,:v_title,:v_body,:v_starttime,:v_endtime,:v_status,:v_data,:v_poster,now())";

		return update(sql, vote);
	}

	public int deleteVote(String id) {
		String sql = "delete from tm_vote where v_id=?";
		return update(sql, new Object[] { id });
	}

	public int updateVote(Map<String, Object> vote) {
		String sql = "update tm_vote set v_title=:v_title,v_body=:v_body,v_starttime=:v_starttime,v_endtime=:v_endtime,v_status=:v_status,v_data=:v_data,v_modifyor=:v_modifyor,v_modifydate=now() where v_id=:v_id";

		return update(sql, vote);
	}

	public Map<String, Object> getVote(String id) {
		String sql = "select * from tm_vote where v_id=?";
		return queryForMap(sql, new Object[] { id });
	}

	public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo) {
		if (params == null) {
			params = new HashMap();
		}
		StringBuffer sql = new StringBuffer("select * from tm_vote v where 1=1");
		if (BaseUtil.isNotEmpty(params.get("v_title"))) {
			sql.append(" and v.v_title like concat('%',:v_title,'%')");
		}
		sql.append(" order by v.v_createdate desc");
		return queryForList(sql.toString(), params, pagesize, currentPageNo);
	}

	public Pagination getVoteLog(Map<String, Object> params, int pagesize, int currentPageNo) {
		if (params == null) {
			params = new HashMap();
		}
		StringBuffer sql = new StringBuffer("select * from tm_vote_log v where 1=1");
		if (BaseUtil.isNotEmpty(params.get("l_vid"))) {
			sql.append(" and v.l_vid = :l_vid ");
		}
		sql.append(" order by v.v_createdate desc");
		return queryForList(sql.toString(), params, pagesize, currentPageNo);
	}

	public int getVotedNums(String voteid) {
		String sql = "select count(1) total from tm_vote_log where l_vid=?";
		return queryForInt(sql, new Object[] { voteid });
	}
}

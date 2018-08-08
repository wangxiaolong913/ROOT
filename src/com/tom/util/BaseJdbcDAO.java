package com.tom.util;

import com.tom.model.system.Pagination;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

public class BaseJdbcDAO extends NamedParameterJdbcDaoSupport {
	@Resource(name = "transactionTemplate")
	private TransactionTemplate transactionTemplate;
	private LobHandler lobHandler;

	public TransactionTemplate getTransactionTemplate() {
		return this.transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public LobHandler getLobHandler() {
		return this.lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	@Resource(name = "dataSource")
	public void setSuperDataSource(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public int update(String sql) {
		return getJdbcTemplate().update(sql);
	}

	public int update(String sql, Object[] params) {
		return getJdbcTemplate().update(sql, params);
	}

	public int update(String sql, Map<String, ?> map) {
		return getNamedParameterJdbcTemplate().update(sql, map);
	}

	public int[] batchUpdate(String[] sql) {
		return getJdbcTemplate().batchUpdate(sql);
	}

	public int[] batchUpdate(String sql, final List<List<Object>> paramsList) {
		Assert.notEmpty(paramsList, "can not do batchUpdate operation, paramsList is empty!");
		return getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				List<Object> params = (List) paramsList.get(i);
				if ((params != null) && (!params.isEmpty())) {
					for (int k = 0; k < params.size(); k++) {
						Object arg = params.get(k);
						if ((arg instanceof SqlParameterValue)) {
							SqlParameterValue paramValue = (SqlParameterValue) arg;
							StatementCreatorUtils.setParameterValue(ps, k + 1, paramValue, paramValue.getValue());
						} else {
							StatementCreatorUtils.setParameterValue(ps, k + 1, Integer.MIN_VALUE, arg);
						}
					}
				}
			}

			public int getBatchSize() {
				return paramsList.size();
			}
		});
	}

	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
		return getJdbcTemplate().batchUpdate(sql, pss);
	}

	public Map<String, Object> queryForMap(String sql) {
		try {
			return getJdbcTemplate().queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
	}

	public Map<String, Object> queryForMap(String sql, Object[] params) {
		try {
			return getJdbcTemplate().queryForMap(sql, params);
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
	}

	public Map<String, Object> queryForMap(String sql, Map<String, Object> paramMap) {
		try {
			return getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Class<T> requiredType) {
		try {
			return (T) getJdbcTemplate().queryForObject(sql, requiredType);
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Object[] params, Class<T> requiredType) {
		return (T) getJdbcTemplate().queryForObject(sql, params, requiredType);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
		return (T) getJdbcTemplate().queryForObject(sql, rowMapper);
	}

	public <T> T queryForObject(String sql, Object[] params, RowMapper<T> rowMapper) {
		return (T) getJdbcTemplate().queryForObject(sql, params, rowMapper);
	}

	public int queryForInt(String sql) {
		try {
			int i = getJdbcTemplate().queryForInt(sql);
			return getJdbcTemplate().queryForInt(sql);
		} catch (EmptyResultDataAccessException e) {
		}
		return -1;
	}

	public int queryForInt(String sql, Object[] params) {
		try {
			return getJdbcTemplate().queryForInt(sql, params);
		} catch (EmptyResultDataAccessException e) {
		}
		return -1;
	}

	public List<Map<String, Object>> queryForList(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}

	public List<Map<String, Object>> queryForList(String sql, Object[] params) {
		return getJdbcTemplate().queryForList(sql, params);
	}

	public Pagination queryForList(String sql, Map<String, Object> params, int pagesize, int currentPageNo) {
		int start_pos = (currentPageNo - 1) * pagesize;

		String querysql = sql + " LIMIT " + start_pos + "," + pagesize;
		List<Map<String, Object>> dataList = getNamedParameterJdbcTemplate().queryForList(querysql, params);

		String countsql = "SELECT COUNT(1) FROM (" + sql + ") TA";
		int totalRow = getNamedParameterJdbcTemplate().queryForInt(countsql, params);

		Pagination pagination = new Pagination(currentPageNo, pagesize, totalRow);

		pagination.setDataList(dataList);
		return pagination;
	}

	public Pagination queryForList(String query_sql, String count_sql, Map<String, Object> params, int pagesize,
			int currentPageNo) {
		int start_pos = (currentPageNo - 1) * pagesize;

		String querysql = query_sql + " LIMIT " + start_pos + "," + pagesize;
		List<Map<String, Object>> dataList = getNamedParameterJdbcTemplate().queryForList(querysql, params);

		String countsql = count_sql;
		int totalRow = getNamedParameterJdbcTemplate().queryForInt(countsql, params);

		Pagination pagination = new Pagination(currentPageNo, pagesize, totalRow);

		pagination.setDataList(dataList);
		return pagination;
	}

	public <T> List<T> queryForList(String sql, Object[] params, RowMapper<T> rowMapper) {
		return getJdbcTemplate().query(sql, params, rowMapper);
	}

	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) {
		return getJdbcTemplate().query(sql, rowMapper);
	}

	public void execute(String sql) {
		getJdbcTemplate().execute(sql);
	}

	public Object execute(String sql, PreparedStatementCallback<?> action) {
		return getJdbcTemplate().execute(sql, action);
	}
}

package com.tom.system.dao.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.INewsDao;
import com.tom.util.BaseJdbcDAO;
import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class NewsDaoImp
  extends BaseJdbcDAO
  implements INewsDao
{
  public int addNewsCategory(Map<String, Object> category)
  {
    String sql = "insert into tm_news_cateogry(c_id,c_name,c_pid,c_poster,c_orderid,c_remark,c_createdate) values(:c_id,:c_name,:c_pid,:c_poster,:c_orderid,:c_remark,now())";
    
    return update(sql, category);
  }
  
  public int deleteNewsCategory(String categoryid)
  {
    String sql = "delete from tm_news_cateogry where c_id=?";
    return update(sql, new Object[] { categoryid });
  }
  
  public int updateNewsCategory(Map<String, Object> category)
  {
    String sql = "update tm_news_cateogry set c_name=:c_name,c_pid=:c_pid,c_orderid=:c_orderid,c_remark=:c_remark where c_id=:c_id";
    
    return update(sql, category);
  }
  
  public Map<String, Object> getNewsCategory(String categoryid)
  {
    String sql = "select * from tm_news_cateogry where c_id=?";
    return queryForMap(sql, new Object[] { categoryid });
  }
  
  public List<Map<String, Object>> getAllNewsCategories()
  {
    String sql = "select * from tm_news_cateogry order by c_createdate desc";
    return queryForList(sql);
  }
  
  public Pagination queryNewsCategories(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*,ifnull(b.nums,0) c_nums  from tm_news_cateogry a left join (select count(1) nums, n_classid from tm_news group by n_classid) b on a.c_id = b.n_classid ");
    if (BaseUtil.isNotEmpty(params.get("c_name"))) {
      sql.append("and a.c_name=:c_name");
    }
    sql.append("order by c_orderid asc, a.c_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
  
  public int getNewsNumbers(String categoryid)
  {
    String sql = "select count(1) total from tm_news where n_classid=?";
    return queryForInt(sql, new Object[] { categoryid });
  }
  
  public int addNews(Map<String, Object> news)
  {
    String sql = "insert into tm_news(n_id,n_title,n_title_color,n_classid,n_content,n_status,n_totop,n_visit,n_photo,n_author,n_newsfrom,n_poster,n_createdate,n_modifyor,n_modifydate) values(:n_id,:n_title,:n_title_color,:n_classid,:n_content,:n_status,:n_totop,:n_visit,:n_photo,:n_author,:n_newsfrom,:n_poster,now(),null,null)";
    
    return update(sql, news);
  }
  
  public int deleteNews(String newsid)
  {
    String sql = "delete from tm_news where n_id=?";
    return update(sql, new Object[] { newsid });
  }
  
  public int updateNews(Map<String, Object> news)
  {
    String sql = "update tm_news set n_title=:n_title,n_title_color=:n_title_color,n_classid=:n_classid,n_content=:n_content,n_status=:n_status,n_totop=:n_totop,n_visit=:n_visit,n_photo=:n_photo,n_author=:n_author,n_newsfrom=:n_newsfrom,n_modifyor=:n_modifyor,n_modifydate=now() where n_id=:n_id";
    
    return update(sql, news);
  }
  
  public Map<String, Object> getNews(String newsid)
  {
    String sql = "select * from tm_news where n_id=?";
    return queryForMap(sql, new Object[] { newsid });
  }
  
  public Map<String, Object> getNews4Read(String newsid)
  {
    String sql = "update tm_news set n_visit=n_visit+1 where n_id=?";
    update(sql, new Object[] { newsid });
    return getNews(newsid);
  }
  
  public Pagination queryNews(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    if (params == null) {
      params = new HashMap();
    }
    StringBuffer sql = new StringBuffer("select a.*, b.c_name from tm_news a left join tm_news_cateogry b on a.n_classid=b.c_id where 1=1 ");
    if (BaseUtil.isNotEmpty(params.get("n_classid"))) {
      sql.append(" and a.n_classid=:n_classid");
    }
    if (BaseUtil.isNotEmpty(params.get("n_title"))) {
      sql.append(" and a.n_title like concat('%',:n_title,'%')");
    }
    sql.append(" order by a.n_totop desc, a.n_createdate desc");
    return queryForList(sql.toString(), params, pagesize, currentPageNo);
  }
}

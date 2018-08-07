package com.tom.system.service.imp;

import com.tom.model.system.Pagination;
import com.tom.system.dao.IAdminRoleDao;
import com.tom.system.service.IAdminRoleService;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminRoleService")
public class AdminRoleServiceImp
  implements IAdminRoleService
{
  private static final Logger logger = Logger.getLogger(AdminRoleServiceImp.class);
  @Autowired
  private IAdminRoleDao dao;
  
  public int addAdminRole(Map<String, Object> role)
  {
    try
    {
      String roleid = BaseUtil.generateId();
      role.put("r_id", roleid);
      int rows = this.dao.addAdminRole(role);
      if (rows >= 0) {
        addRoleCache(roleid);
      }
      return rows;
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int deleteAdminRole(String roleid)
  {
    try
    {
      int adms = this.dao.getAdminNumbers(roleid);
      if (adms > 0) {
        return 2;
      }
      int rows = this.dao.deleteAdminRole(roleid);
      if (rows >= 0) {
        CacheHelper.removeCache("RoleCache", "R" + roleid);
      }
      return rows;
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public int updateAdminRole(Map<String, Object> role)
  {
    try
    {
      int rows = this.dao.updateAdminRole(role);
      String roleid = String.valueOf(role.get("r_id"));
      if (rows >= 0) {
        addRoleCache(roleid);
      }
      return rows;
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return 0;
  }
  
  public Map<String, Object> getAdminRole(String roleid)
  {
    try
    {
      return this.dao.getAdminRole(roleid);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public List<Map<String, Object>> getAllRoles()
  {
    try
    {
      return this.dao.getAllRoles();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo)
  {
    try
    {
      return this.dao.query(params, pagesize, currentPageNo);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return new Pagination();
  }
  
  private void addRoleCache(String roleid)
  {
    Map<String, Object> map = getAdminRole(roleid);
    String privilege = String.valueOf(map.get("r_privilege"));
    
    CacheHelper.addCache("RoleCache", "R" + roleid, "," + privilege);
  }
}

package com.tom.model.system;

import com.tom.util.BaseUtil;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

public class Result
{
  private boolean success;
  private int code;
  private String msg;
  private Object data;
  private Map<String, Object> ext;
  
  public Result()
  {
    this.success = false;
    this.code = 0;
    this.msg = "";
  }
  
  public Result(boolean success)
  {
    this.success = success;
    this.code = 0;
    this.msg = "";
  }
  
  public Result(boolean success, int code, String msg)
  {
    this.success = success;
    this.code = code;
    this.msg = msg;
  }
  
  public Result(boolean success, int code, String msg, Object data)
  {
    this.success = success;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }
  
  public boolean isSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(boolean success)
  {
    this.success = success;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
  
  public void setMsg(String msg)
  {
    this.msg = msg;
  }
  
  public <T> T getData()
  {
    return (T)this.data;
  }
  
  public void setData(Object data)
  {
    this.data = data;
  }
  
  public void addExtData(String key, Object val)
  {
    if (this.ext == null) {
      this.ext = new HashMap();
    }
    this.ext.put(key, val == null ? "" : val);
  }
  
  public String toJSONString()
  {
    JSONObject json = new JSONObject();
    json.put("success", this.success ? "success" : "failure");
    json.put("code", Integer.valueOf(this.code));
    json.put("msg", BaseUtil.isEmpty(this.msg) ? "" : this.msg);
    json.put("ext", this.ext == null ? "" : this.ext);
    if (((getData() instanceof Pagination)) && (getData() != null))
    {
      Pagination pager = (Pagination)getData();
      json.put("total", Integer.valueOf(pager.getTotalRowsCount()));
      json.put("rows", pager.getDataList());
    }
    else
    {
      json.put("data", this.data == null ? "" : this.data);
    }
    return json.toString();
  }
}

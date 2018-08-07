package com.tom.system.dao;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface ILogDao
{
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

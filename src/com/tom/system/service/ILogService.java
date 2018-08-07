package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.Map;

public abstract interface ILogService
{
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

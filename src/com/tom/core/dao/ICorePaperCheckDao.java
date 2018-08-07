package com.tom.core.dao;

import java.util.List;

public abstract interface ICorePaperCheckDao
{
  public abstract int saveUserPaper(List<List<Object>> paramList);
}

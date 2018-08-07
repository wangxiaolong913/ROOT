package com.tom.core;

import com.tom.model.system.SysLogger;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TomSystemQueue
{
  public static Queue<Map<String, Object>> USER_PAPER_QUEUE = new ConcurrentLinkedQueue();
  public static Queue<SysLogger> SYSTEM_LOG_QUEUE = new ConcurrentLinkedQueue();
}

package com.tom.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ExecuteTimeInterceptor
  extends HandlerInterceptorAdapter
{
  private static final Logger logger = Logger.getLogger(ExecuteTimeInterceptor.class);
  private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal("StopWatch-StartTime");
  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    long beginTime = System.currentTimeMillis();
    this.startTimeThreadLocal.set(Long.valueOf(beginTime));
    return true;
  }
  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception
  {
    long endTime = System.currentTimeMillis();
    long beginTime = ((Long)this.startTimeThreadLocal.get()).longValue();
    long consumeTime = endTime - beginTime;
    if (consumeTime > 5000L) {
      logger.warn(String.format("%s cost %d millis", new Object[] { request.getRequestURI(), Long.valueOf(consumeTime) }));
    }
  }
}

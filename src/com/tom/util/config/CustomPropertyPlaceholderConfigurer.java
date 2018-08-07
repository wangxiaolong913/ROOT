package com.tom.util.config;

import com.tom.WebAppConfig;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class CustomPropertyPlaceholderConfigurer
  extends PropertyPlaceholderConfigurer
{
  protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
    throws BeansException
  {
    super.processProperties(beanFactory, props);
    for (Object key : props.keySet())
    {
      String keyStr = key.toString();
      String value = props.getProperty(keyStr);
      WebAppConfig.GLOBAL_CONFIG_PROPERTIES.put(keyStr, value);
    }
  }
}

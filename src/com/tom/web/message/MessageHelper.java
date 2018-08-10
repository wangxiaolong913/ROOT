package com.tom.web.message;

import com.tom.util.BaseUtil;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

public class MessageHelper
{
  private static Properties prop = null;
  private static String lang = "zh_CN";
  
  static
  {
    loadMessage(lang);
  }
  
  public static void setLang(String lan)
  {
    lang = lan;
    loadMessage(lang);
  }
  
  public static String getLang()
  {
    return lang;
  }
  
  public static void loadMessage(String lang)
  {
    if (BaseUtil.isEmpty(lang)) {
      lang = "zh_CN";
    }
    prop = new Properties();
    try
    {
      prop.load(MessageHelper.class.getResourceAsStream("message_" + lang + ".properties"));
      prop.load(MessageHelper.class.getResourceAsStream("message_plus_" + lang + ".properties"));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("读取国际化消息文件发生错误," + e.getMessage());
    }
  }
  
  public static String getMessage(String key)
  {
    String message = prop == null ? "" : (String)prop.get(key);
    return message;
  }
  
  public static String getMessages(String separator, String... keys)
  {
    if (keys != null)
    {
      List<String> list = new ArrayList();
      String[] arrayOfString;
      int j = (arrayOfString = keys).length;
      for (int i = 0; i < j; i++)
      {
        String key = arrayOfString[i];
        list.add(getMessage(key));
      }
      return StringUtils.join(list.toArray(), separator);
    }
    return null;
  }
  
  public static void main(String... s)
  {
    System.out.println(getMessage("system.version"));
  }
}

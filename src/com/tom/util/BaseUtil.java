package com.tom.util;

import com.tom.WebAppConfig;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class BaseUtil
{
  public static boolean isNull(Object obj)
  {
    return obj == null;
  }
  
  public static boolean isEmpty(String s)
  {
    return (s == null) || (s.trim().length() < 1) || ("null".equalsIgnoreCase(s));
  }
  
  public static boolean isEmpty(Object o)
  {
    return isEmpty(String.valueOf(o));
  }
  
  public static boolean isNotEmpty(String s)
  {
    return !isEmpty(s);
  }
  
  public static boolean isNotEmpty(Object o)
  {
    return !isEmpty(String.valueOf(o));
  }
  
  public static int getInt(String s)
  {
    try
    {
      return Integer.parseInt(s);
    }
    catch (Exception e) {}
    return 55537;
  }
  
  public static String convertNullToEmpty(String s)
  {
    return s == null ? "" : s;
  }
  
  public static String convertEmptyToSome(String s, String t)
  {
    return isEmpty(s) ? t : s;
  }
  
  public static String converEmptyToNull(String s)
  {
    return "".equals(s) ? null : s;
  }
  
  public static String characterConversion(String str)
  {
    if ((str != null) && (!"".equals(str)))
    {
      str = str.replace("'", "&#39;");
      str = str.replace("\"", "&#34;");
      str = str.replace("<", "&lt;");
      str = str.replace(">", "&gt;");
    }
    return str;
  }
  
  public static String getConfig(String key)
  {
    return (String)WebAppConfig.GLOBAL_CONFIG_PROPERTIES.get(key);
  }
  
  public static String getChinese(String s)
  {
    try
    {
      return new String(s.getBytes("ISO8859-1"), "UTF-8");
    }
    catch (Exception e) {}
    return null;
  }
  
  public static String subString(String s, int len)
  {
    if (isEmpty(s)) {
      return "";
    }
    if (s.length() >= len) {
      return s.substring(0, len);
    }
    return s;
  }
  
  public static String subStringByFrom(String s, int from)
  {
    if (isEmpty(s)) {
      return "";
    }
    try
    {
      return s.substring(from);
    }
    catch (Exception e) {}
    return "";
  }
  
  public static String generateId()
  {
    return UUID.randomUUID().toString();
  }
  
  public static String generateRandomString(int length)
  {
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++)
    {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }
  
  public static Date parseDate(String partten, String date)
  {
    try
    {
      return new SimpleDateFormat(partten).parse(date);
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static String Html2TextFormat(String inputString)
  {
    String htmlStr = inputString;
    String textStr = "";
    try
    {
      String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
      String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
      String regEx_html = "<[^>]+>";
      Pattern p_script = Pattern.compile(regEx_script, 2);
      Matcher m_script = p_script.matcher(htmlStr);
      htmlStr = m_script.replaceAll("");
      
      Pattern p_style = Pattern.compile(regEx_style, 2);
      Matcher m_style = p_style.matcher(htmlStr);
      htmlStr = m_style.replaceAll("");
      
      Pattern p_html = Pattern.compile(regEx_html, 2);
      Matcher m_html = p_html.matcher(htmlStr);
      htmlStr = m_html.replaceAll("");
      
      textStr = htmlStr;
    }
    catch (Exception e)
    {
      System.err.println("Html2Text: " + e.getMessage());
    }
    return textStr;
  }
  
  public static boolean isFileTypeAllowed(String extname)
  {
    String types = (String)WebAppConfig.GLOBAL_CONFIG_PROPERTIES.get("file.allowtypes");
    String[] arrTypes = types.split(";");
    String[] arrayOfString1;
    int j = (arrayOfString1 = arrTypes).length;
    for (int i = 0; i < j; i++)
    {
      String type = arrayOfString1[i];
      if (type.equalsIgnoreCase(extname)) {
        return true;
      }
    }
    return false;
  }
  
  public static String getSystemConfig(String key)
  {
    Map<String, Object> config = (Map)CacheHelper.getCache("ConfigCache", "base");
    if (config != null) {
      return String.valueOf(config.get(key));
    }
    return null;
  }
  
  public static String FormatBlankQuestions(String content, String input)
  {
    if ((content == null) || ("".equals(content))) {
      return "";
    }
    String result = "";
    Pattern p = Pattern.compile("\\[BlankArea.+?]");
    Matcher m = null;
    m = p.matcher(content);
    result = m.replaceAll(input);
    
    return result;
  }
  
  public static void main(String[] args)
  {
    System.out.println(isEmpty(null));
    System.out.println(isNull(""));
    
    String[] aa = new String[0];
    System.out.println(StringUtils.join(aa, ","));
    
    String[] bb = { "111", "222", "33333" };
    System.out.println(StringUtils.join(bb, ","));
    
    String[] cc = (String[])null;
    System.out.println(StringUtils.join(cc, ","));
    
    System.out.println(generateRandomString(11));
    
    String xx = StringUtils.substring(null, 0, 10);
    System.out.println(">>" + xx);
  }
}

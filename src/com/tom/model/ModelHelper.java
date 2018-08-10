package com.tom.model;

import com.thoughtworks.xstream.XStream;
import com.tom.util.BaseUtil;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

public class ModelHelper
{
  private static Logger logger = Logger.getLogger(ModelHelper.class);
  public static final String[] FLAG_CONTENT = { "[题干]", "[QUESTION]" };
  public static final String[] FLAG_TYPE = { "[类型]", "[TYPE]" };
  public static final String[] FLAG_OPTIONS = { "[选项]", "[OPTIONS]" };
  public static final String[] FLAG_KEY = { "[答案]", "[KEY]" };
  public static final String[] FLAG_RESOLVE = { "[解析]", "[RESOLVE]" };
  public static final String[] FLAG_TYPE_SINGLECHOICE = { "单选", "SINGLECHOICE" };
  public static final String[] FLAG_TYPE_MULTIPLECHOICE = { "多选", "MULTIPLECHOICE" };
  public static final String[] FLAG_TYPE_JUDGMENT = { "判断", "JUDGMENT" };
  public static final String[] FLAG_TYPE_BLANKFILL = { "填空", "BLANKFILL" };
  public static final String[] FLAG_TYPE_ESSAY = { "问答", "ESSAY" };
  public static final String[] FLAG_VAL_JUDGMENT_YES = { "正确", "YES" };
  
  @Deprecated
  public static Object _genQuestion(String xml)
  {
    if (BaseUtil.isEmpty(xml))
    {
      logger.warn("XML转换到对象发生失败,因为传入的字符串为空");
      return null;
    }
    try
    {
      XStream xstream = new XStream();
      return xstream.fromXML(xml);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static <T> T convertObject(String xml)
  {
    if (BaseUtil.isEmpty(xml))
    {
      logger.warn("XML转换到对象发生失败,因为传入的字符串为空");
      return null;
    }
    try
    {
      XStream xstream = new XStream();
      return (T)xstream.fromXML(xml);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public static String formatObject(Object obj)
  {
    try
    {
      XStream xstream = new XStream();
      return xstream.toXML(obj);
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    return null;
  }
  
  public static Map<String, Object> SimpleJSON2Map(JSONObject json)
  {
    if (json == null) {
      return null;
    }
    Map<String, Object> map = new HashMap();
    for (Object k : json.keySet()) {
      map.put(String.valueOf(k), json.get(k));
    }
    return map;
  }
  
  public static void main(String... s)
  {
    String xx = "BLANK121：曹操";
    String[] _arrblk = xx.replace("：", ":").split(":");
    System.out.println(_arrblk[0]);
    System.out.println(_arrblk[1]);
  }
  
  public static abstract interface PaperType
  {
    public static final int NORMAL = 0;
    public static final int RANDOM = 1;
  }
  
  public static abstract interface QuestionType
  {
    public static final String SINGLECHOICE = "1";
    public static final String MULTIPLECHOICE = "2";
    public static final String JUDGMENT = "3";
    public static final String BLANKFILL = "4";
    public static final String ESSAY = "5";
  }
}

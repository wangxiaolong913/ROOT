package com.tom.util;

import com.thoughtworks.xstream.XStream;
import com.tom.model.paper.Option;
import com.tom.model.paper.QBlank;
import com.tom.model.paper.QuestionBlankFill;
import com.tom.model.paper.QuestionEssay;
import com.tom.model.paper.QuestionJudgment;
import com.tom.model.paper.QuestionMultipleChoice;
import com.tom.model.paper.QuestionSingleChoice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class QuestionImportTxtHelper
{
  private static Logger logger = Logger.getLogger(QuestionImportTxtHelper.class);
  
  public static List<Map<String, Object>> QuestionConverteFromTxt(List<String> lines)
    throws TomException
  {
    if ((lines == null) || (lines.size() < 1)) {
      throw new TomException(-1, "文件内容为空");
    }
    List<Map<String, Object>> list = new ArrayList();
    try
    {
      int lineflag = 0;
      Map<String, Object> mapQuestion = new HashMap();
      for (String line : lines)
      {
        if ((line.startsWith(com.tom.model.ModelHelper.FLAG_CONTENT[0])) || (line.startsWith(com.tom.model.ModelHelper.FLAG_CONTENT[1])))
        {
          mapQuestion.put("content", line.substring(line.indexOf("]") + 1));
          lineflag = 1;
        }
        else if ((line.startsWith(com.tom.model.ModelHelper.FLAG_TYPE[0])) || (line.startsWith(com.tom.model.ModelHelper.FLAG_TYPE[1])))
        {
          String _type = line.substring(line.lastIndexOf("]") + 1);
          if ((com.tom.model.ModelHelper.FLAG_TYPE_SINGLECHOICE[0].equals(_type)) || (com.tom.model.ModelHelper.FLAG_TYPE_SINGLECHOICE[1].equals(_type))) {
            _type = "1";
          } else if ((com.tom.model.ModelHelper.FLAG_TYPE_MULTIPLECHOICE[0].equals(_type)) || (com.tom.model.ModelHelper.FLAG_TYPE_MULTIPLECHOICE[1].equals(_type))) {
            _type = "2";
          } else if ((com.tom.model.ModelHelper.FLAG_TYPE_JUDGMENT[0].equals(_type)) || (com.tom.model.ModelHelper.FLAG_TYPE_JUDGMENT[1].equals(_type))) {
            _type = "3";
          } else if ((com.tom.model.ModelHelper.FLAG_TYPE_BLANKFILL[0].equals(_type)) || (com.tom.model.ModelHelper.FLAG_TYPE_BLANKFILL[1].equals(_type))) {
            _type = "4";
          } else if ((com.tom.model.ModelHelper.FLAG_TYPE_ESSAY[0].equals(_type)) || (com.tom.model.ModelHelper.FLAG_TYPE_ESSAY[1].equals(_type))) {
            _type = "5";
          }
          mapQuestion.put("type", _type);
        }
        else if ((line.startsWith(com.tom.model.ModelHelper.FLAG_OPTIONS[0])) || (line.startsWith(com.tom.model.ModelHelper.FLAG_OPTIONS[1])))
        {
          mapQuestion.put("options", line.substring(line.lastIndexOf("]") + 1));
        }
        else if ((line.startsWith(com.tom.model.ModelHelper.FLAG_KEY[0])) || (line.startsWith(com.tom.model.ModelHelper.FLAG_KEY[1])))
        {
          mapQuestion.put("key", line.substring(line.lastIndexOf("]") + 1));
        }
        else if ((line.startsWith(com.tom.model.ModelHelper.FLAG_RESOLVE[0])) || (line.startsWith(com.tom.model.ModelHelper.FLAG_RESOLVE[1])))
        {
          mapQuestion.put("resolve", line.substring(line.lastIndexOf("]") + 1));
          lineflag = 2;
        }
        if (lineflag == 2)
        {
          lineflag = 0;
          list.add(genarateQuestionRecord(mapQuestion));
        }
      }
    }
    catch (Exception e)
    {
      throw new TomException(-9, "文件格式化错误");
    }
    return list;
  }
  
  private static Map<String, Object> genarateQuestionRecord(Map<String, Object> data)
  {
    Map<String, Object> map = new HashMap();
    
    String type = String.valueOf(data.get("type"));
    String content = String.valueOf(data.get("content"));
    map.put("q_type", type);
    map.put("q_level", "3");
    map.put("q_from", "");
    map.put("q_status", "1");
    if ("4".equals(type)) {
      content = content.replace("[BLANK", "[BlankArea");
    }
    map.put("q_content", content);
    map.put("q_key", data.get("key"));
    map.put("q_resolve", data.get("resolve"));
    
    map.put("options", data.get("options"));
    
    return map;
  }
  
  public static String genarateQuestionObject(Map<String, Object> data)
  {
    String _type = String.valueOf(data.get("q_type"));
    String _content = String.valueOf(data.get("q_content"));
    String _id = String.valueOf(data.get("q_id"));
    String _key = String.valueOf(data.get("q_key"));
    String _resolve = String.valueOf(data.get("q_resolve"));
    
    String _options = String.valueOf(data.get("options"));
    Object object = null;
    if ("1".equals(_type))
    {
      QuestionSingleChoice question = new QuestionSingleChoice();
      
      question.setId(_id);
      question.setContent(_content);
      question.setKey(_key);
      question.setOptions(formatOptions(_options));
      question.setExt(_resolve);
      
      object = question;
    }
    else if ("2".equals(_type))
    {
      QuestionMultipleChoice question = new QuestionMultipleChoice();
      
      question.setId(_id);
      question.setContent(_content);
      question.setKey(_key);
      question.setOptions(formatOptions(_options));
      question.setExt(_resolve);
      
      object = question;
    }
    else if ("3".equals(_type))
    {
      QuestionJudgment question = new QuestionJudgment();
      question.setId(_id);
      question.setContent(_content);
      
      String _ckey = "";
      if ((com.tom.model.ModelHelper.FLAG_VAL_JUDGMENT_YES[0].equals(_key)) || (com.tom.model.ModelHelper.FLAG_VAL_JUDGMENT_YES[1].equals(_key))) {
        _ckey = "Y";
      } else {
        _ckey = "N";
      }
      question.setKey(_ckey);
      question.setExt(_resolve);
      
      object = question;
    }
    else if ("4".equals(_type))
    {
      QuestionBlankFill question = new QuestionBlankFill();
      question.setId(_id);
      
      question.setContent(_content.replace("[BLANK", "[BlankArea"));
      question.setKey("");
      question.setBlanks(formatBlanks(_key));
      question.setComplex(false);
      question.setExt(_resolve);
      
      object = question;
    }
    else if ("5".equals(_type))
    {
      QuestionEssay question = new QuestionEssay();
      question.setId(_id);
      question.setContent(_content);
      question.setKey(_key);
      question.setExt(_resolve);
      
      object = question;
    }
    XStream xstream = new XStream();
    try
    {
      return xstream.toXML(object);
    }
    catch (Exception e)
    {
      logger.error("构建q_data时发生异常:" + e.getMessage());
    }
    return "";
  }
  
  private static List<Option> formatOptions(String source)
  {
    if (BaseUtil.isEmpty(source)) {
      return null;
    }
    List<Option> list = new ArrayList();
    
    Pattern pattern = Pattern.compile("\\{[a-zA-Z].+?}");
    Matcher matcher = pattern.matcher(source);
    while (matcher.find())
    {
      String soption = matcher.group();
      try
      {
        String _opt = soption.substring(1, soption.length() - 1);
        
        String _alisa = _opt.substring(0, 1);
        String _text = _opt.substring(2);
        list.add(new Option(_alisa, _text));
      }
      catch (Exception e)
      {
        logger.error("批量导入, 格式化选择项时发生一个异常,即将忽略本行数据,当前这条数据不合法," + source);
      }
    }
    return list;
  }
  
  public static List<QBlank> formatBlanks(String source)
  {
    if (BaseUtil.isEmpty(source)) {
      return null;
    }
    List<QBlank> list = new ArrayList();
    
    Pattern pattern = Pattern.compile("\\{BLANK.+?}");
    Matcher matcher = pattern.matcher(source);
    while (matcher.find())
    {
      String sblank = matcher.group();
      try
      {
        String _blk = sblank.substring(1, sblank.length() - 1);
        String[] _arrblk = _blk.replace("：", ":").split(":");
        String _name = _arrblk[0];
        String _value = _arrblk[1];
        String _sbid = _name.replace("BLANK", "");
        QBlank blank = new QBlank(BaseUtil.getInt(_sbid), _name, _value);
        list.add(blank);
      }
      catch (Exception e)
      {
        logger.error("批量导入,格式化空格项时发生一个异常,即将忽略本行数据,当前这条数据不合法," + source);
      }
    }
    return list;
  }
}

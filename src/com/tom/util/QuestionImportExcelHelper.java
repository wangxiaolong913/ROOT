package com.tom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class QuestionImportExcelHelper
{
  public static List<Map<String, Object>> QuestionConverteFromExcel(List<Map> list, String poster, String dbid)
  {
    if ((list == null) || (list.size() < 1)) {
      return null;
    }
    List<Map<String, Object>> questions = new ArrayList();
    
    int rows = 0;
    for (Map map : list)
    {
      rows++;
      if (rows >= 2)
      {
        String qtypename = String.valueOf(map.get("A"));
        if (BaseUtil.isEmpty(qtypename)) {
          break;
        }
        String qcontent = String.valueOf(map.get("B"));
        String qtype = "";
        String qkey = "";
        String qresolve = "";
        String qdata = "";
        
        Map<String, Object> question = new HashMap();
        question.put("q_id", BaseUtil.generateId());
        question.put("q_dbid", dbid);
        question.put("q_level", "3");
        question.put("q_from", "");
        question.put("q_status", "1");
        question.put("q_poster", poster);
        question.put("q_modifyor", poster);
        if ((com.tom.model.ModelHelper.FLAG_TYPE_SINGLECHOICE[0].equals(qtypename)) || (com.tom.model.ModelHelper.FLAG_TYPE_SINGLECHOICE[1].equals(qtypename)))
        {
          qtype = "1";
          qresolve = String.valueOf(map.get("K"));
          qkey = String.valueOf(map.get("J"));
          
          List<String> _options = new ArrayList();
          char alisa = 'A';
          for (char c = 'C'; c <= 'I'; c = (char)(c + '\001'))
          {
            String _s_option = String.valueOf(map.get(String.valueOf(c)));
            if (BaseUtil.isNotEmpty(_s_option))
            {
              _options.add("{" + String.valueOf(alisa).toUpperCase() + ":" + _s_option + "}");
              alisa = (char)(alisa + '\001');
            }
          }
          question.put("options", StringUtils.join(_options.toArray(), ","));
        }
        else if ((com.tom.model.ModelHelper.FLAG_TYPE_MULTIPLECHOICE[0].equals(qtypename)) || (com.tom.model.ModelHelper.FLAG_TYPE_MULTIPLECHOICE[1].equals(qtypename)))
        {
          qtype = "2";
          qresolve = String.valueOf(map.get("K"));
          qkey = String.valueOf(map.get("J"));
          
          List<String> _options = new ArrayList();
          char alisa = 'A';
          for (char c = 'C'; c <= 'I'; c = (char)(c + '\001'))
          {
            String _s_option = String.valueOf(map.get(String.valueOf(c)));
            if (BaseUtil.isNotEmpty(_s_option))
            {
              _options.add("{" + String.valueOf(alisa).toUpperCase() + ":" + _s_option + "}");
              alisa = (char)(alisa + '\001');
            }
          }
          question.put("options", StringUtils.join(_options.toArray(), ","));
        }
        else if ((com.tom.model.ModelHelper.FLAG_TYPE_JUDGMENT[0].equals(qtypename)) || (com.tom.model.ModelHelper.FLAG_TYPE_JUDGMENT[1].equals(qtypename)))
        {
          qtype = "3";
          qresolve = String.valueOf(map.get("D"));
          qkey = String.valueOf(map.get("C"));
        }
        else if ((com.tom.model.ModelHelper.FLAG_TYPE_BLANKFILL[0].equals(qtypename)) || (com.tom.model.ModelHelper.FLAG_TYPE_BLANKFILL[1].equals(qtypename)))
        {
          qtype = "4";
          qresolve = String.valueOf(map.get("I"));
          qcontent = qcontent.replace("[BLANK", "[BlankArea");
          
          List<String> _blanks = new ArrayList();
          int id = 1;
          for (char c = 'C'; c <= 'H'; c = (char)(c + '\001'))
          {
            String _s_blank = String.valueOf(map.get(String.valueOf(c)));
            if (BaseUtil.isNotEmpty(_s_blank))
            {
              _blanks.add("{BLANK" + id + ":" + _s_blank + "}");
              id++;
            }
          }
          qkey = StringUtils.join(_blanks.toArray(), ",");
        }
        else if ((com.tom.model.ModelHelper.FLAG_TYPE_ESSAY[0].equals(qtypename)) || (com.tom.model.ModelHelper.FLAG_TYPE_ESSAY[1].equals(qtypename)))
        {
          qtype = "5";
          qresolve = String.valueOf(map.get("D"));
          qkey = String.valueOf(map.get("C"));
        }
        question.put("q_content", qcontent);
        question.put("q_type", qtype);
        question.put("q_key", qkey);
        question.put("q_resolve", qresolve);
        qdata = QuestionImportTxtHelper.genarateQuestionObject(question);
        question.put("q_data", qdata);
        
        questions.add(question);
      }
    }
    return questions;
  }
}

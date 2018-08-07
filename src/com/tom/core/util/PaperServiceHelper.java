package com.tom.core.util;

import com.tom.model.paper.QBlank;
import com.tom.model.paper.QuestionBlankFill;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperServiceHelper
{
  public static int BlankFillChecker(QuestionBlankFill question, String userkey)
  {
    if ((BaseUtil.isEmpty(userkey)) || (question == null)) {
      return 0;
    }
    int score = question.getScore();
    int total = 0;
    if (question.isComplex())
    {
      Map<String, String> _map = new HashMap();
      for (QBlank blank : question.getBlanks()) {
        _map.put(blank.getValue(), "1");
      }
      String[] arrayOfString1;
      int j = (arrayOfString1 = userkey.split(Constants.TM_SPLITER)).length;
      for (int i = 0; i < j; i++)
      {
        String ukey = arrayOfString1[i];
        if (_map.containsKey(ukey.trim()))
        {
          total++;
          _map.remove(ukey);
        }
      }
    }
    else
    {
      String[] ukeys = userkey.split(Constants.TM_SPLITER);
      for (int i = 0; i < ukeys.length; i++)
      {
        String _uk = ukeys[i].trim();
        try
        {
          QBlank blank = (QBlank)question.getBlanks().get(i);
          String _key = blank.getValue();
          if (_uk.equals(_key)) {
            total++;
          }
        }
        catch (Exception e)
        {
          System.out.println("��������������������" + e.getMessage());
          e.printStackTrace();
        }
      }
    }
    float f_total = total * 1.0F;
    float f_total_qs = question.getBlanks().size() * 1.0F;
    float rate = f_total / f_total_qs;
    int getScore = (int)(score * rate);
    
    return getScore;
  }
}

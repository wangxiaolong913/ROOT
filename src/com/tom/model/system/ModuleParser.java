package com.tom.model.system;

import com.tom.web.message.MessageHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ModuleParser
{
  private static List<Module> list = null;
  
  static
  {
    loadModules();
  }
  
  public static List<Module> getModules()
  {
    return list;
  }
  
  private static void loadModules()
  {
    list = new ArrayList();
    SAXReader reader = new SAXReader();
    String lang = MessageHelper.getLang();
    try
    {
      InputStream stream = ModuleParser.class.getResourceAsStream("modules.xml");
      Document doc = reader.read(stream);
      Element root = doc.getRootElement();
      List<Element> modules = root.elements();
      for (Element module : modules)
      {
        Module M = new Module();
        M.setName(module.attributeValue("name"));
        M.setEname(module.attributeValue("ename"));
        M.setCode(module.attributeValue("code"));
        if ("zh_CN".equals(lang)) {
          M.setText(module.attributeValue("name"));
        } else {
          M.setText(module.attributeValue("ename"));
        }
        List<Element> functions = module.elements();
        for (Element function : functions)
        {
          Module F = new Module();
          F.setName(function.attributeValue("name"));
          F.setEname(function.attributeValue("ename"));
          F.setCode(function.attributeValue("code"));
          if ("zh_CN".equals(lang)) {
            F.setText(function.attributeValue("name"));
          } else {
            F.setText(function.attributeValue("ename"));
          }
          List<Element> funcs = function.elements();
          for (Element func : funcs)
          {
            Module P = new Module();
            P.setName(func.attributeValue("name"));
            P.setEname(func.attributeValue("ename"));
            P.setCode(func.attributeValue("code"));
            if ("zh_CN".equals(lang)) {
              P.setText(func.attributeValue("name"));
            } else {
              P.setText(func.attributeValue("ename"));
            }
            F.addModule(P);
          }
          M.addModule(F);
        }
        list.add(M);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void main(String... s) {}
}

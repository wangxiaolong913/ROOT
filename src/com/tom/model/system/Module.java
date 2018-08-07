package com.tom.model.system;

import java.util.ArrayList;
import java.util.List;

public class Module
{
  private String name;
  private String ename;
  private String code;
  private String text;
  private List<Module> list;
  
  public Module() {}
  
  public Module(String name, String ename, String code) {}
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getEname()
  {
    return this.ename;
  }
  
  public void setEname(String ename)
  {
    this.ename = ename;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public List<Module> getList()
  {
    return this.list;
  }
  
  public void setList(List<Module> list)
  {
    this.list = list;
  }
  
  public void addModule(Module module)
  {
    if (this.list == null) {
      this.list = new ArrayList();
    }
    this.list.add(module);
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }
}

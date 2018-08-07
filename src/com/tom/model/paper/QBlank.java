package com.tom.model.paper;

public class QBlank
{
  private int id;
  private String name;
  private String value;
  
  public QBlank() {}
  
  public QBlank(int id, String name, String value)
  {
    this.id = id;
    this.name = name;
    this.value = value;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
}

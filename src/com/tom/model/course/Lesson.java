package com.tom.model.course;

public class Lesson
{
  private String id;
  private String name;
  private String remark;
  private String filetype;
  private String filepath;
  private int minutes;
  
  public Lesson() {}
  
  public Lesson(String id, String name, String remark, String filetype, String filepath, int minutes)
  {
    this.id = id;
    this.name = name;
    this.remark = remark;
    this.filetype = filetype;
    this.filepath = filepath;
    this.minutes = minutes;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
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
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getFiletype()
  {
    return this.filetype;
  }
  
  public void setFiletype(String filetype)
  {
    this.filetype = filetype;
  }
  
  public String getFilepath()
  {
    return this.filepath;
  }
  
  public void setFilepath(String filepath)
  {
    this.filepath = filepath;
  }
  
  public int getMinutes()
  {
    return this.minutes;
  }
  
  public void setMinutes(int minutes)
  {
    this.minutes = minutes;
  }
}

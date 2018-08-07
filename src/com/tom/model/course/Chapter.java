package com.tom.model.course;

import java.util.ArrayList;
import java.util.List;

public class Chapter
{
  private String id;
  private String name;
  private String remark;
  private List<Lesson> lessons;
  
  public Chapter() {}
  
  public Chapter(String id, String name, String remark, List<Lesson> lessons)
  {
    this.id = id;
    this.name = name;
    this.remark = remark;
    this.lessons = lessons;
  }
  
  public Chapter(String id, String name, String remark)
  {
    this.id = id;
    this.name = name;
    this.remark = remark;
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
  
  public List<Lesson> getLessons()
  {
    return this.lessons;
  }
  
  public void setLessons(List<Lesson> lessons)
  {
    this.lessons = lessons;
  }
  
  public void addLesson(Lesson lesson)
  {
    if (this.lessons == null) {
      this.lessons = new ArrayList();
    }
    this.lessons.add(lesson);
  }
}

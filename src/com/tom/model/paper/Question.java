package com.tom.model.paper;

public class Question
{
  private String id;
  private String type;
  private String content;
  private String key;
  private int score;
  private String ext;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public int getScore()
  {
    return this.score;
  }
  
  public void setScore(int score)
  {
    this.score = score;
  }
  
  public String getExt()
  {
    return this.ext;
  }
  
  public void setExt(String ext)
  {
    this.ext = ext;
  }
}

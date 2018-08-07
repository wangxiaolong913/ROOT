package com.tom.model.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course
{
  private String id;
  private String name;
  private String logo;
  private int score;
  private int type;
  private String introduce;
  private Date postdate;
  private int status;
  private int stars;
  private List<Chapter> chapters;
  private String teacherid;
  
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
  
  public String getLogo()
  {
    return this.logo;
  }
  
  public void setLogo(String logo)
  {
    this.logo = logo;
  }
  
  public int getScore()
  {
    return this.score;
  }
  
  public void setScore(int score)
  {
    this.score = score;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  public String getIntroduce()
  {
    return this.introduce;
  }
  
  public void setIntroduce(String introduce)
  {
    this.introduce = introduce;
  }
  
  public Date getPostdate()
  {
    return this.postdate;
  }
  
  public void setPostdate(Date postdate)
  {
    this.postdate = postdate;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public int getStars()
  {
    return this.stars;
  }
  
  public void setStars(int stars)
  {
    this.stars = stars;
  }
  
  public List<Chapter> getChapters()
  {
    return this.chapters;
  }
  
  public void setChapters(List<Chapter> chapters)
  {
    this.chapters = chapters;
  }
  
  public void addChapter(Chapter chapter)
  {
    if (this.chapters == null) {
      this.chapters = new ArrayList();
    }
    this.chapters.add(chapter);
  }
  
  public String getTeacherid()
  {
    return this.teacherid;
  }
  
  public void setTeacherid(String teacherid)
  {
    this.teacherid = teacherid;
  }
}

package com.tom.model.paper;

import java.util.ArrayList;
import java.util.List;

public class PaperSection
{
  private String name;
  private String remark;
  private String id;
  private List<Question> questions;
  private int rnum;
  private int rtype;
  private int rlevel;
  private String rdbid;
  private int rscore;
  
  public PaperSection() {}
  
  public PaperSection(String id, String name, String remark)
  {
    this.id = id;
    this.name = name;
    this.remark = remark;
  }
  
  public PaperSection(String name, String remark, List<Question> questions)
  {
    this.name = name;
    this.remark = remark;
    this.questions = questions;
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
  
  public List<Question> getQuestions()
  {
    return this.questions;
  }
  
  public void setQuestions(List<Question> questions)
  {
    this.questions = questions;
  }
  
  public void addQuestion(Question question)
  {
    if (this.questions == null) {
      this.questions = new ArrayList();
    }
    this.questions.add(question);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public int getRnum()
  {
    return this.rnum;
  }
  
  public void setRnum(int rnum)
  {
    this.rnum = rnum;
  }
  
  public int getRtype()
  {
    return this.rtype;
  }
  
  public void setRtype(int rtype)
  {
    this.rtype = rtype;
  }
  
  public int getRlevel()
  {
    return this.rlevel;
  }
  
  public void setRlevel(int rlevel)
  {
    this.rlevel = rlevel;
  }
  
  public String getRdbid()
  {
    return this.rdbid;
  }
  
  public void setRdbid(String rdbid)
  {
    this.rdbid = rdbid;
  }
  
  public int getRscore()
  {
    return this.rscore;
  }
  
  public void setRscore(int rscore)
  {
    this.rscore = rscore;
  }
}

package com.tom.model.paper;

import java.util.ArrayList;
import java.util.List;

public class QuestionBlankFill
  extends Question
{
  private List<QBlank> blanks;
  private boolean isComplex = false;
  
  public QuestionBlankFill(QuestionBlankFill question)
  {
    setId(question.getId());
    setType(question.getType());
    setContent(question.getContent());
    setKey(question.getKey());
    setScore(question.getScore());
    setExt(question.getExt());
    
    setBlanks(question.getBlanks());
    setComplex(question.isComplex());
  }
  
  public QuestionBlankFill()
  {
    setType("4");
  }
  
  public List<QBlank> getBlanks()
  {
    return this.blanks;
  }
  
  public void setBlanks(List<QBlank> blanks)
  {
    this.blanks = blanks;
  }
  
  public void addBlank(int id, String key, String value)
  {
    if (this.blanks == null) {
      this.blanks = new ArrayList();
    }
    this.blanks.add(new QBlank(id, key, value));
  }
  
  public boolean isComplex()
  {
    return this.isComplex;
  }
  
  public void setComplex(boolean isComplex)
  {
    this.isComplex = isComplex;
  }
}

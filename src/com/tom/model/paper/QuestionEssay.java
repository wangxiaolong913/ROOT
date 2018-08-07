package com.tom.model.paper;

public class QuestionEssay
  extends Question
{
  public QuestionEssay(QuestionEssay question)
  {
    setId(question.getId());
    setType(question.getType());
    setContent(question.getContent());
    setKey(question.getKey());
    setScore(question.getScore());
    setExt(question.getExt());
  }
  
  public QuestionEssay()
  {
    setType("5");
  }
}

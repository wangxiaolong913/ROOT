package com.tom.model.paper;

public class QuestionJudgment
  extends Question
{
  public QuestionJudgment(QuestionJudgment question)
  {
    setId(question.getId());
    setType(question.getType());
    setContent(question.getContent());
    setKey(question.getKey());
    setScore(question.getScore());
    setExt(question.getExt());
  }
  
  public QuestionJudgment()
  {
    setType("3");
  }
}

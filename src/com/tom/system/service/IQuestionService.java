package com.tom.system.service;

import com.tom.model.system.Pagination;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public abstract interface IQuestionService
{
  public abstract int addQuestion(HttpServletRequest paramHttpServletRequest);
  
  public abstract int importQuestionsFromTxt(MultipartFile paramMultipartFile, HttpServletRequest paramHttpServletRequest);
  
  public abstract int importQuestionsFromExcel(MultipartFile paramMultipartFile, HttpServletRequest paramHttpServletRequest);
  
  public abstract int deleteQuestion(String paramString);
  
  public abstract int updateQuestion(HttpServletRequest paramHttpServletRequest);
  
  public abstract Map<String, Object> getQuestion(String paramString);
  
  public abstract Pagination query(Map<String, Object> paramMap, int paramInt1, int paramInt2);
}

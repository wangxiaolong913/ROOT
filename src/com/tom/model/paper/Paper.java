package com.tom.model.paper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paper
{
  private String id;
  private String name;
  private int status;
  private Date starttime;
  private Date endtime;
  private int duration;
  private Date showtime;
  private int totalscore;
  private int passscore;
  private int ordertype;
  private int papertype;
  private String remark;
  private List<PaperSection> sections;
  private List<String> departments;
  private boolean showKey = true;
  private int showMode = 1;
  
  public Paper() {}
  
  public Paper(Paper pp)
  {
    setId(pp.getId());
    setName(pp.getName());
    setStatus(pp.getStatus());
    setStarttime(pp.getStarttime());
    setEndtime(pp.getEndtime());
    setDuration(pp.getDuration());
    setShowtime(pp.getShowtime());
    setTotalscore(pp.getTotalscore());
    setPassscore(pp.getPassscore());
    setOrdertype(pp.getOrdertype());
    setPapertype(pp.getPapertype());
    setRemark(pp.getRemark());
    setShowKey(pp.isShowKey());
    setShowMode(pp.getShowMode());
    if (pp.getSections() != null) {
      for (PaperSection sec : pp.getSections())
      {
        PaperSection section = new PaperSection(sec.getId(), sec.getName(), sec.getRemark());
        section.setRnum(sec.getRnum());
        section.setRtype(sec.getRtype());
        section.setRlevel(sec.getRlevel());
        section.setRdbid(sec.getRdbid());
        section.setRscore(sec.getRscore());
        if (sec.getQuestions() != null) {
          for (Question ques : sec.getQuestions()) {
            section.addQuestion(ques);
          }
        }
        addSection(section);
      }
    }
    if (pp.getDepartments() != null) {
      for (String depid : pp.getDepartments()) {
        addDepartment(depid);
      }
    }
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
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public Date getStarttime()
  {
    return this.starttime;
  }
  
  public void setStarttime(Date starttime)
  {
    this.starttime = starttime;
  }
  
  public Date getEndtime()
  {
    return this.endtime;
  }
  
  public void setEndtime(Date endtime)
  {
    this.endtime = endtime;
  }
  
  public int getDuration()
  {
    return this.duration;
  }
  
  public void setDuration(int duration)
  {
    this.duration = duration;
  }
  
  public Date getShowtime()
  {
    return this.showtime;
  }
  
  public void setShowtime(Date showtime)
  {
    this.showtime = showtime;
  }
  
  public int getTotalscore()
  {
    return this.totalscore;
  }
  
  public void setTotalscore(int totalscore)
  {
    this.totalscore = totalscore;
  }
  
  public int getPassscore()
  {
    return this.passscore;
  }
  
  public void setPassscore(int passscore)
  {
    this.passscore = passscore;
  }
  
  public int getOrdertype()
  {
    return this.ordertype;
  }
  
  public void setOrdertype(int ordertype)
  {
    this.ordertype = ordertype;
  }
  
  public int getPapertype()
  {
    return this.papertype;
  }
  
  public void setPapertype(int papertype)
  {
    this.papertype = papertype;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public List<PaperSection> getSections()
  {
    return this.sections;
  }
  
  public void setSections(List<PaperSection> sections)
  {
    this.sections = sections;
  }
  
  public void addSection(PaperSection section)
  {
    if (this.sections == null) {
      this.sections = new ArrayList();
    }
    this.sections.add(section);
  }
  
  public List<String> getDepartments()
  {
    return this.departments;
  }
  
  public void setDepartments(List<String> departments)
  {
    this.departments = departments;
  }
  
  public void addDepartment(String depid)
  {
    if (this.departments == null) {
      this.departments = new ArrayList();
    }
    this.departments.add(depid);
  }
  
  public boolean isShowKey()
  {
    return this.showKey;
  }
  
  public void setShowKey(boolean showKey)
  {
    this.showKey = showKey;
  }
  
  public int getShowMode()
  {
    return this.showMode;
  }
  
  public void setShowMode(int showMode)
  {
    this.showMode = showMode;
  }
}

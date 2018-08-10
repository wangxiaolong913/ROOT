package com.tom.core.service.imp;

import com.tom.core.dao.ICorePaperDao;
import com.tom.core.service.ICorePaperService;
import com.tom.model.ModelHelper;
import com.tom.model.paper.Paper;
import com.tom.model.paper.PaperSection;
import com.tom.model.paper.Question;
import com.tom.model.paper.QuestionBlankFill;
import com.tom.model.paper.QuestionEssay;
import com.tom.model.paper.QuestionJudgment;
import com.tom.model.paper.QuestionMultipleChoice;
import com.tom.model.paper.QuestionSingleChoice;
import com.tom.util.CacheHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CorePaperService")
public class CorePaperServiceImp implements ICorePaperService {
	private static final Logger logger = Logger.getLogger(CorePaperServiceImp.class);
	@Autowired
	private ICorePaperDao dao;

	public Paper getPaper(String pid) {
		String cacheName = "PaperCache";
		String cacheKey = "P" + pid;
		Paper paper = (Paper) CacheHelper.getCache(cacheName, cacheKey);
		if (paper != null) {
			return paper;
		}
		paper = getBasicPaperFromDatabase(pid);
		if (paper == null) {
			logger.warn("PID=" + pid + "的试卷不存在");
			return null;
		}
		if (paper.getPapertype() == 0) {
			paper = buildNormalPaper(paper);
		}
		CacheHelper.addCache(cacheName, cacheKey, paper);

		return paper;
	}

	public Question getQuestion(String qid) {
		String cacheName = "QuestionCache";
		String cacheKey = "Q" + qid;

		Question question = (Question) CacheHelper.getCache(cacheName, cacheKey);
		if (question != null) {
			try {
				String questionType = question.getType();
				if ("1".equals(questionType)) {
					return new QuestionSingleChoice((QuestionSingleChoice) question);
				}
				if ("2".equals(questionType)) {
					return new QuestionMultipleChoice((QuestionMultipleChoice) question);
				}
				if ("3".equals(questionType)) {
					return new QuestionJudgment((QuestionJudgment) question);
				}
				if ("4".equals(questionType)) {
					return new QuestionBlankFill((QuestionBlankFill) question);
				}
				if (!"5".equals(questionType)) {
					return question;
				}
				return new QuestionEssay((QuestionEssay) question);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				return null;
			}
		}
		try {
			Map<String, Object> map = this.dao.getQuestion(qid);
			if (map == null) {
				logger.warn("QID=" + qid + "的试题不存在");
				return null;
			}
			String questionData = String.valueOf(map.get("q_data"));
			String questionType = String.valueOf(map.get("q_type"));
			question = (Question) ModelHelper.convertObject(questionData);
			if ("1".equals(questionType)) {
				question = (QuestionSingleChoice) question;
			} else if ("2".equals(questionType)) {
				question = (QuestionMultipleChoice) question;
			} else if ("3".equals(questionType)) {
				question = (QuestionJudgment) question;
			} else if ("4".equals(questionType)) {
				question = (QuestionBlankFill) question;
			} else if ("5".equals(questionType)) {
				question = (QuestionEssay) question;
			}
			if (question != null) {
				CacheHelper.addCache(cacheName, cacheKey, question);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return question;
	}

	public Paper getUserRandomPaper(String uid, String pid) {
		Paper xpaper = null;
		logger.info("获取学员随机试卷...pid=" + pid + ",uid=" + uid);
		try {
			Map<String, Object> map = this.dao.getUserRandomPaper(uid, pid);
			if (map == null) {
				logger.info("获取学员随机试卷...pid=" + pid + ",uid=" + uid + ",不存在");
				return null;
			}
			String paperData = String.valueOf(map.get("r_detail"));
			xpaper = (Paper) ModelHelper.convertObject(paperData);
		} catch (Exception e) {
			logger.error(e);
		}
		return xpaper;
	}

	public Paper buildUserRandomPaper(String uid, String pid) {
		Paper xpaper = null;
		Map<String, Object> map = null;

		logger.info("获取随机试卷...pid=" + pid + ",uid=" + uid);
		try {
			map = this.dao.getUserRandomPaper(uid, pid);
			if (map == null) {
				logger.info("获取随机试卷...pid=" + pid + ",uid=" + uid + ",首次创建");
				xpaper = buildRandomPaper(pid);
				int row = this.dao.saveUserRandomPaper(uid, pid, ModelHelper.formatObject(xpaper));
				logger.info("保存首次生成的试卷入库,pid=" + pid + ",uid=" + uid + ",rows=" + row);
			} else {
				logger.info("获取随机试卷...pid=" + pid + ",uid=" + uid + ",数据库获取");
				String paperData = String.valueOf(map.get("r_detail"));
				xpaper = (Paper) ModelHelper.convertObject(paperData);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return xpaper;
	}

	public int loadAllPapers() {
		int rows = 0;
		try {
			List<Map<String, Object>> list = this.dao.getAllPapers();
			if ((list != null) && (list.size() > 0)) {
				for (Map<String, Object> map : list) {
					String pid = String.valueOf(map.get("p_id"));
					Paper paper = getPaper(pid);
					if (paper != null) {
						rows++;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return rows;
	}

	private Paper getBasicPaperFromDatabase(String pid) {
		Paper paper = null;
		try {
			Map<String, Object> map = this.dao.getPaper(pid);
			if (map == null) {
				return null;
			}
			String paperData = String.valueOf(map.get("p_data"));
			paper = (Paper) ModelHelper.convertObject(paperData);
		} catch (Exception e) {
			logger.error(e);
		}
		return paper;
	}

	private Paper buildNormalPaper(Paper paper) {
		List<PaperSection> sections = paper.getSections();
		if (sections != null) {
			for (PaperSection section : sections) {
				List<Question> questions = section.getQuestions();
				if (questions != null) {
					List<Question> newQuestions = new ArrayList();
					for (Question question : questions) {
						int score = question.getScore();
						String qid = question.getId();
						question = getQuestion(question.getId());
						if (question != null) {
							question.setScore(score);
							newQuestions.add(question);
						} else {
							logger.warn("组卷需要的试题不存在:qid=" + qid);
						}
					}
					section.setQuestions(newQuestions);
				}
			}
		}
		return paper;
	}

	private Paper buildRandomPaper(String pid) {
		Paper paper = new Paper(getPaper(pid));
		List<PaperSection> sections = paper.getSections();
		if (sections != null) {
			for (PaperSection section : sections) {
				List<Question> questions = getRandomQuestions(section);
				section.setQuestions(questions);
			}
		}
		return paper;
	}

	private List<Question> getRandomQuestions(PaperSection section) {
		List<Question> questions = new ArrayList();
		try {
			List<Map<String, Object>> list = this.dao.getQuestions(section.getRdbid(), section.getRtype(),
					section.getRlevel(), section.getRnum());
			if ((list != null) && (list.size() > 0)) {
				for (Map<String, Object> map : list) {
					String qid = String.valueOf(map.get("q_id"));
					Question question = getQuestion(qid);

					question.setScore(section.getRscore());
					questions.add(question);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return questions;
	}
}

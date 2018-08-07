package com.tom.system.service.imp;

import com.thoughtworks.xstream.XStream;
import com.tom.model.paper.Option;
import com.tom.model.paper.QuestionBlankFill;
import com.tom.model.paper.QuestionEssay;
import com.tom.model.paper.QuestionJudgment;
import com.tom.model.paper.QuestionMultipleChoice;
import com.tom.model.paper.QuestionSingleChoice;
import com.tom.model.system.Pagination;
import com.tom.system.dao.IQuestionDao;
import com.tom.system.service.IQuestionService;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.OfficeToolExcel;
import com.tom.util.QuestionImportExcelHelper;
import com.tom.util.QuestionImportTxtHelper;
import com.tom.util.TomException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("QuestionService")
public class QuestionServiceImp implements IQuestionService {
	private static final Logger logger = Logger.getLogger(QuestionServiceImp.class);
	@Autowired
	private IQuestionDao dao;

	public int addQuestion(HttpServletRequest request) {
		Map<String, Object> question = new HashMap();

		String qtype = request.getParameter("q_type");
		String qid = BaseUtil.generateId();
		question.put("q_id", qid);
		question.put("q_dbid", request.getParameter("q_dbid"));
		question.put("q_type", qtype);
		question.put("q_level", request.getParameter("q_level"));
		question.put("q_from", request.getParameter("q_from"));
		question.put("q_status", request.getParameter("q_status"));
		question.put("q_content", request.getParameter("q_content"));

		String qkey = request.getParameter("q_key");
		if ("4".equals(qtype)) {
			qkey = StringUtils.join(request.getParameterValues("q_blanks"), ",");
		}
		question.put("q_key", qkey);
		question.put("q_resolve", request.getParameter("q_resolve"));
		question.put("q_poster", request.getParameter("q_poster"));
		question.put("q_modifyor", request.getParameter("q_modifyor"));
		question.put("q_data", buildQuestion(qid, request));
		try {
			return this.dao.addQuestion(question);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int importQuestionsFromTxt(MultipartFile file, HttpServletRequest request) {
		List<Map<String, Object>> list = new ArrayList();

		String q_poster = request.getParameter("q_poster");
		String q_dbid = request.getParameter("q_dbid");
		try {
			List<Map<String, Object>> data = QuestionImportTxtHelper.QuestionConverteFromTxt(getFileContent(file));
			if ((data != null) && (data.size() > 0)) {
				for (Map<String, Object> map : data) {
					map.put("q_id", BaseUtil.generateId());
					map.put("q_dbid", q_dbid);
					map.put("q_poster", q_poster);
					map.put("q_modifyor", q_poster);
					map.put("q_data", QuestionImportTxtHelper.genarateQuestionObject(map));
					list.add(map);
				}
			}
		} catch (TomException e1) {
			logger.error(e1.getMessage());
			return e1.getCode();
		} catch (Exception e2) {
			logger.error(e2.getMessage());
			return -2;
		}
		try {
			return this.dao.importQuestions(list);
		} catch (Exception e) {
			logger.error(e);
		}
		return -1;
	}

	private List<String> getFileContent(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	public int importQuestionsFromExcel(MultipartFile file, HttpServletRequest request) {
		String q_poster = request.getParameter("q_poster");
		String q_dbid = request.getParameter("q_dbid");
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("TEMPFILE" + System.currentTimeMillis(), ".tmp");
			file.transferTo(tmpFile);

			List<Map> list = OfficeToolExcel.readExcel(tmpFile,
					new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K" });
			if ((list == null) || (list.size() < 1)) {
				return 0;
			}
			List<Map<String, Object>> questions = null;
			try {
				questions = QuestionImportExcelHelper.QuestionConverteFromExcel(list, q_poster, q_dbid);
			} catch (Exception e1) {
				logger.error(e1);
				return -9;
			}
			if ((questions == null) || (questions.size() < 1)) {
				return 0;
			}
			try {
				return this.dao.importQuestions(questions);
			} catch (Exception e) {
				logger.error(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return -1;
		} finally {
			try {
				tmpFile.delete();
			} catch (Exception localException6) {
			}
		}
		return 0;
	}

	public int deleteQuestion(String qid) {
		try {
			boolean hasUsed = this.dao.hasBeenUsed(qid);
			if (hasUsed) {
				return 9;
			}
			int rows = this.dao.deleteQuestion(qid);
			if (rows > 0) {
				CacheHelper.removeCache("QuestionCache", "Q" + qid);
			}
			return rows;
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int updateQuestion(HttpServletRequest request) {
		Map<String, Object> question = new HashMap();

		String qtype = request.getParameter("q_type");
		String qid = request.getParameter("q_id");
		question.put("q_id", qid);
		question.put("q_dbid", request.getParameter("q_dbid"));

		question.put("q_level", request.getParameter("q_level"));
		question.put("q_from", request.getParameter("q_from"));
		question.put("q_status", request.getParameter("q_status"));
		question.put("q_content", request.getParameter("q_content"));

		String qkey = request.getParameter("q_key");
		if ("4".equals(qtype)) {
			qkey = StringUtils.join(request.getParameterValues("q_blanks"), ",");
		}
		question.put("q_key", qkey);
		question.put("q_resolve", request.getParameter("q_resolve"));
		question.put("q_poster", request.getParameter("q_poster"));
		question.put("q_modifyor", request.getParameter("q_modifyor"));
		question.put("q_data", buildQuestion(qid, request));
		try {
			int rows = this.dao.updateQuestion(question);
			if (rows > 0) {
				CacheHelper.removeCache("QuestionCache", "Q" + qid);
			}
			return rows;
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public Map<String, Object> getQuestion(String qid) {
		try {
			return this.dao.getQuestion(qid);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public Pagination query(Map<String, Object> params, int pagesize, int currentPageNo) {
		try {
			return this.dao.query(params, pagesize, currentPageNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return new Pagination();
	}

	private String buildQuestion(String qid, HttpServletRequest request) {
		String result = "";

		XStream xstream = new XStream();
		String type = request.getParameter("q_type");
		String content = request.getParameter("q_content");
		String key = request.getParameter("q_key");
		String resolve = request.getParameter("q_resolve");
		String[] arrayOfString1;
		int j;
		int i;
		if ("1".equals(type)) {
			QuestionSingleChoice question = new QuestionSingleChoice();
			if (request.getParameterValues("q_options") != null) {
				char alisa = 'A';
				j = (arrayOfString1 = request.getParameterValues("q_options")).length;
				for (i = 0; i < j; i++) {
					String option = arrayOfString1[i];
					question.addOption(new Option(String.valueOf(alisa), option));
					alisa = (char) (alisa + '\001');
				}
			}
			question.setId(qid);
			question.setContent(content);
			question.setKey(key);
			question.setExt(resolve);

			result = xstream.toXML(question);
		} else if ("2".equals(type)) {
			QuestionMultipleChoice question = new QuestionMultipleChoice();
			if (request.getParameterValues("q_options") != null) {
				char alisa = 'A';
				j = (arrayOfString1 = request.getParameterValues("q_options")).length;
				for (i = 0; i < j; i++) {
					String option = arrayOfString1[i];
					question.addOption(new Option(String.valueOf(alisa), option));
					alisa = (char) (alisa + '\001');
				}
			}
			question.setId(qid);
			question.setContent(content);
			key = StringUtils.join(request.getParameterValues("q_key"), "");
			question.setKey(key);
			question.setExt(resolve);

			result = xstream.toXML(question);
		} else if ("3".equals(type)) {
			QuestionJudgment question = new QuestionJudgment();
			question.setId(qid);
			question.setContent(content);
			question.setKey(key);
			question.setExt(resolve);

			result = xstream.toXML(question);
		} else if ("4".equals(type)) {
			QuestionBlankFill question = new QuestionBlankFill();
			String[] qids = request.getParameterValues("q_blankids");
			String[] qblanks = request.getParameterValues("q_blanks");
			for (i = 0; i < qids.length; i++) {
				int iqid = BaseUtil.getInt(qids[i]);
				String sqblank = qblanks[i];
				question.addBlank(iqid, "BLANK" + iqid, sqblank);
			}
			question.setComplex("Y".equalsIgnoreCase(request.getParameter("q_iscomplex")));
			question.setId(qid);
			question.setContent(content);
			question.setKey(key);
			question.setExt(resolve);

			result = xstream.toXML(question);
		} else if ("5".equals(type)) {
			QuestionEssay question = new QuestionEssay();
			question.setId(qid);
			question.setContent(content);
			question.setKey(key);
			question.setExt(resolve);

			result = xstream.toXML(question);
		}
		return result;
	}
}

package com.tom.system.service.imp;

import com.thoughtworks.xstream.XStream;
import com.tom.core.service.ICorePaperService;
import com.tom.model.ModelHelper;
import com.tom.model.paper.Paper;
import com.tom.model.paper.PaperSection;
import com.tom.model.paper.Question;
import com.tom.model.system.Pagination;
import com.tom.system.dao.IPaperDao;
import com.tom.system.dao.IQuestionDao;
import com.tom.system.service.IPaperService;
import com.tom.util.BaseUtil;
import com.tom.util.CacheHelper;
import com.tom.util.Constants;
import com.tom.util.OfficeToolWord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PaperService")
public class PaperServiceImp implements IPaperService {
	private static final Logger logger = Logger.getLogger(PaperServiceImp.class);
	@Autowired
	private IPaperDao dao;
	@Autowired
	private IQuestionDao qdao;
	@Autowired
	private ICorePaperService paperservice;

	public int addPaper(Map<String, Object> paper) {
		try {
			String pid = BaseUtil.generateId();
			paper.put("p_id", pid);
			paper.put("p_data", "");
			return this.dao.addPaper(paper);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int deletePaper(String pid) {
		try {
			int n = this.dao.getUserNumbers(pid);
			if (n > 0) {
				return 2;
			}
			int rows = this.dao.deletePaper(pid);
			if (rows >= 0) {
				CacheHelper.removeCache("PaperCache", "P" + pid);
			}
			return rows;
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public Map<String, Object> getPaper(String pid) {
		try {
			return this.dao.getPaper(pid);
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

	public int updatePaper(Map<String, Object> paper) {
		try {
			String pid = String.valueOf(paper.get("p_id"));
			paper.put("p_data", reBuilPaperData(paper));
			int rows = this.dao.updatePaper(paper);
			if (rows >= 0) {
				CacheHelper.removeCache("PaperCache", "P" + pid);
			}
			return rows;
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int updatePaperDetail(HttpServletRequest request) {
		String pid = request.getParameter("pid");
		int totalscore = BaseUtil.getInt(request.getParameter("p_total_score"));
		int passscore = BaseUtil.getInt(request.getParameter("p_pass_score"));
		String paperData = buildPaper(pid, request);
		try {
			int rows = this.dao.updatePaperDetail(pid, totalscore, passscore, paperData);
			if (rows >= 0) {
				CacheHelper.removeCache("PaperCache", "P" + pid);
			}
			return rows;
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	private String reBuilPaperData(Map<String, Object> paper) {
		String result = "";
		if (paper == null) {
			return "";
		}
		Map<String, Object> map = null;
		try {
			map = this.dao.getPaper(String.valueOf(paper.get("p_id")));
		} catch (Exception e) {
			logger.error(e);
		}
		if (map == null) {
			return "";
		}
		try {
			String xml = String.valueOf(map.get("p_data"));
			Paper paperModel = (Paper) ModelHelper.convertObject(xml);
			if (paper != null) {
				paperModel.setName(String.valueOf(paper.get("p_name")));
				paperModel.setStatus(BaseUtil.getInt(String.valueOf(paper.get("p_status"))));
				paperModel
						.setStarttime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", String.valueOf(paper.get("p_starttime"))));
				paperModel.setEndtime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", String.valueOf(paper.get("p_endtime"))));
				paperModel.setDuration(BaseUtil.getInt(String.valueOf(paper.get("p_duration"))));
				paperModel.setShowtime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", String.valueOf(paper.get("p_showtime"))));

				paperModel.setOrdertype(BaseUtil.getInt(String.valueOf(paper.get("p_question_order"))));
				paperModel.setPapertype(BaseUtil.getInt(String.valueOf(paper.get("p_papertype"))));
				paperModel.setRemark(String.valueOf(paper.get("p_remark")));

				String branchids = String.valueOf(paper.get("ln_bid"));
				if (BaseUtil.isEmpty(branchids)) {
					branchids = "";
				}
				paperModel.setDepartments(Arrays.asList(branchids.split(",")));

				paperModel.setShowKey("1".equals(String.valueOf(paper.get("p_showkey"))));
				paperModel.setShowMode(BaseUtil.getInt(String.valueOf(paper.get("p_showmode"))));

				result = ModelHelper.formatObject(paperModel);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	private String buildPaper(String pid, HttpServletRequest request) {
		Paper paper = new Paper();
		String result = "";
		XStream xstream = new XStream();
		int paperType = BaseUtil.getInt(request.getParameter("p_papertype"));
		try {
			paper.setId(pid);
			paper.setName(request.getParameter("p_name"));
			paper.setStatus(BaseUtil.getInt(request.getParameter("p_status")));
			paper.setStarttime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", request.getParameter("p_starttime")));
			paper.setEndtime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", request.getParameter("p_endtime")));
			paper.setDuration(BaseUtil.getInt(request.getParameter("p_duration")));
			paper.setShowtime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", request.getParameter("p_showtime")));
			paper.setTotalscore(BaseUtil.getInt(request.getParameter("p_total_score")));
			paper.setPassscore(BaseUtil.getInt(request.getParameter("p_pass_score")));
			paper.setOrdertype(BaseUtil.getInt(request.getParameter("p_question_order")));
			paper.setPapertype(paperType);
			paper.setRemark(request.getParameter("p_remark"));

			String[] branchids = request.getParameterValues("ln_bid");
			if ((branchids != null) && (branchids.length > 0)) {
				paper.setDepartments(Arrays.asList(branchids));
			}
			paper.setShowKey("1".equals(request.getParameter("p_showkey")));
			paper.setShowMode(BaseUtil.getInt(request.getParameter("p_showmode")));

			String[] p_section_ids = request.getParameterValues("p_section_ids");
			String[] p_section_names = request.getParameterValues("p_section_names");
			String[] p_section_remarks = request.getParameterValues("p_section_remarks");

			String[] p_dbids = request.getParameterValues("p_dbids");
			String[] p_qtypes = request.getParameterValues("p_qtypes");
			String[] p_qnums = request.getParameterValues("p_qnums");
			String[] p_levels = request.getParameterValues("p_levels");
			String[] p_scores = request.getParameterValues("p_scores");
			if ((p_section_names != null) && (p_section_names.length > 0)) {
				for (int i = 0; i < p_section_names.length; i++) {
					String sectionId = p_section_ids[i];
					String sectionName = p_section_names[i];
					String sectionRemark = p_section_remarks[i];

					String sectionDBId = "0";
					String sectionQtype = "0";
					String sectionQnums = "0";
					String sectionQlevel = "0";
					String sectionQScore = "0";
					if (Objects.nonNull(p_dbids)) {
						sectionDBId = p_dbids[i];
					}
					if (Objects.nonNull(p_qtypes)) {
						sectionQtype = p_qtypes[i];
					}
					if (Objects.nonNull(p_qnums)) {
						sectionQnums = p_qnums[i];
					}
					if (Objects.nonNull(p_levels)) {
						sectionQlevel = p_levels[i];
					}
					if (Objects.nonNull(p_scores)) {
						sectionQScore = p_scores[i];
					}
					// String sectionQtype = p_qtypes[i] == null ? "0" : p_qtypes == null ? "0" :
					// p_qtypes[i];
					// String sectionQnums = p_qnums[i] == null ? "0" : p_qnums == null ? "0" :
					// p_qnums[i];
					// String sectionQlevel = p_levels[i] == null ? "0" : p_levels == null ? "0" :
					// p_levels[i];
					// String sectionQScore = p_scores[i] == null ? "0" : p_scores == null ? "0" :
					// p_scores[i];

					PaperSection section = new PaperSection(sectionId, sectionName, sectionRemark);
					if (paperType == 0) {
						String[] p_question_ids = request.getParameterValues("p_question_ids_" + sectionId);
						String[] p_question_types = request.getParameterValues("p_question_types_" + sectionId);
						String[] p_question_scores = request.getParameterValues("p_question_scores_" + sectionId);
						String[] p_question_cnts = request.getParameterValues("p_question_cnts_" + sectionId);
						if ((p_question_ids != null) && (p_question_ids.length > 0)) {
							for (int j = 0; j < p_question_ids.length; j++) {
								String question_id = p_question_ids[j];
								String question_type = p_question_types[j];
								String question_score = p_question_scores[j];
								String p_question_cnt = p_question_cnts[j];

								Question question = new Question();
								question.setId(question_id);
								question.setType(question_type);
								question.setScore(BaseUtil.getInt(question_score));
								question.setContent(p_question_cnt);

								section.addQuestion(question);
							}
						}
					} else {
						section.setRdbid(sectionDBId);
						section.setRlevel(BaseUtil.getInt(sectionQlevel));
						section.setRnum(BaseUtil.getInt(sectionQnums));
						section.setRscore(BaseUtil.getInt(sectionQScore));
						section.setRtype(BaseUtil.getInt(sectionQtype));
					}
					paper.addSection(section);
				}
			}
			result = xstream.toXML(paper);
		} catch (Exception e) {
			logger.error(e);
		}

		return result;

	}

	public List<Map<String, Object>> getPaperLink(String pid) {
		try {
			return this.dao.getPaperLink(pid);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public int fastAddPaper(HttpServletRequest request) {
		String p_id = BaseUtil.generateId();
		String p_name = request.getParameter("p_name");
		String p_status = request.getParameter("p_status");
		String p_cid = request.getParameter("p_cid");
		String p_starttime = request.getParameter("p_starttime");
		String p_endtime = request.getParameter("p_endtime");
		String p_duration = request.getParameter("p_duration");
		String p_showtime = request.getParameter("p_showtime");

		String p_passscore = request.getParameter("p_pass_score");
		String p_questionorder = request.getParameter("p_question_order");
		int p_papertype = BaseUtil.getInt(request.getParameter("p_papertype"));
		String p_remark = request.getParameter("p_remark");
		String p_poster = request.getParameter("p_poster");
		String p_modifyor = request.getParameter("p_modifyor");

		String p_showkey = request.getParameter("p_showkey");
		String p_showmode = request.getParameter("p_showmode");

		Paper paper = new Paper();
		int total_score = 0;
		int pass_score = 0;
		try {
			paper.setId(p_id);
			paper.setName(p_name);
			paper.setStatus(BaseUtil.getInt(p_status));
			paper.setStarttime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", p_starttime));
			paper.setEndtime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", p_endtime));
			paper.setDuration(BaseUtil.getInt(p_duration));
			paper.setShowtime(BaseUtil.parseDate("yyyy-MM-dd HH:mm", p_showtime));

			paper.setOrdertype(BaseUtil.getInt(p_questionorder));
			paper.setPapertype(p_papertype);
			paper.setRemark(p_remark);

			paper.setShowKey("1".equals(p_showkey));
			paper.setShowMode(BaseUtil.getInt(p_showmode));

			String[] branchids = request.getParameterValues("ln_bid");
			if ((branchids != null) && (branchids.length > 0)) {
				paper.setDepartments(Arrays.asList(branchids));
			}
			String[] p_section_names = request.getParameterValues("p_section_names");
			String[] p_section_remarks = request.getParameterValues("p_section_remarks");
			String[] p_dbids = request.getParameterValues("p_dbids");
			String[] p_qtypes = request.getParameterValues("p_qtypes");
			String[] p_qnums = request.getParameterValues("p_qnums");
			String[] p_levels = request.getParameterValues("p_levels");
			String[] p_scores = request.getParameterValues("p_scores");
			if ((p_section_names != null) && (p_section_remarks != null) && (p_dbids != null)) {
				for (int i = 0; i < p_section_names.length; i++) {
					String section_name = p_section_names[i];
					String section_remark = p_section_remarks[i];
					String dbid = p_dbids[i];
					String qtype = p_qtypes[i];
					int qnums = BaseUtil.getInt(p_qnums[i]);
					int level = BaseUtil.getInt(p_levels[i]);
					int qscore = BaseUtil.getInt(p_scores[i]);

					PaperSection section = new PaperSection(String.valueOf(i), section_name, section_remark);
					if (p_papertype == 0) {
						List<Map<String, Object>> questions = this.qdao.queryQuestions(dbid, qtype, level, qnums);

						List<Question> list = new ArrayList();
						for (Map<String, Object> map : questions) {
							String qType = String.valueOf(map.get("q_type"));
							String qId = String.valueOf(map.get("q_id"));
							String qContent = String.valueOf(map.get("q_content"));

							Question question = new Question();
							question.setId(qId);
							question.setType(qType);
							question.setScore(qscore);
							question.setContent(BaseUtil.subString(qContent, 25));

							total_score += qscore;

							list.add(question);
						}
						section.setQuestions(list);
					} else {
						section.setRdbid(dbid);
						section.setRlevel(level);
						section.setRnum(qnums);
						section.setRscore(qscore);
						section.setRtype(BaseUtil.getInt(qtype));

						total_score += qnums * qscore;
					}
					paper.addSection(section);
				}
			}
			paper.setTotalscore(total_score);
			pass_score = BaseUtil.getInt(p_passscore) > 0 ? BaseUtil.getInt(p_passscore)
					: (int) Math.floor(total_score * 0.6D);
			paper.setPassscore(pass_score);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
		String paperData = null;
		XStream xstream = new XStream();
		try {
			paperData = xstream.toXML(paper);
		} catch (Exception e1) {
			logger.error("������������������" + e1.getMessage());
		}
		Map<String, Object> data = new HashMap();
		data.put("p_id", p_id);
		data.put("p_name", p_name);
		data.put("p_cid", p_cid);
		data.put("p_status", p_status);
		data.put("p_starttime", p_starttime);
		data.put("p_endtime", p_endtime);
		data.put("p_duration", p_duration);
		data.put("p_showtime", p_showtime);
		data.put("p_total_score", Integer.valueOf(total_score));
		data.put("p_pass_score", Integer.valueOf(pass_score));
		data.put("p_question_order", p_questionorder);
		data.put("p_papertype", Integer.valueOf(p_papertype));
		data.put("p_remark", p_remark);
		data.put("p_data", paperData);
		data.put("p_poster", p_poster);
		data.put("p_modifyor", p_modifyor);

		data.put("p_showkey", p_showkey);
		data.put("p_showmode", p_showmode);

		data.put("ln_bid", StringUtils.join(request.getParameterValues("ln_bid"), ","));
		data.put("ln_uid", StringUtils.join(request.getParameterValues("ln_uid"), ","));
		try {
			return this.dao.addPaper(data);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public String doExportPaperAsWord(String pid) {
		if (BaseUtil.isEmpty(pid)) {
			return null;
		}
		Paper paper = this.paperservice.getPaper(pid);
		if (paper == null) {
			return null;
		}
		String filename = BaseUtil.generateRandomString(10) + ".doc";
		String separator = System.getProperty("file.separator");
		String filepath = Constants.getPhysicalPath() + separator + "files" + separator + "export" + separator
				+ filename;
		try {
			OfficeToolWord.makePaperDoc(filepath, paper);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return filename;
	}

	public int doCopyPaper(String pid, String pname) {
		Map<String, Object> map = getPaper(pid);
		if (map == null) {
			return -1;
		}
		List<String> ln_bid = new ArrayList();
		List<String> ln_uid = new ArrayList();
		try {
			List<Map<String, Object>> links = this.dao.getPaperLink(pid);
			if ((links != null) && (links.size() > 0)) {
				for (Map<String, Object> link : links) {
					String lntype = String.valueOf(link.get("ln_type"));
					String lnbuid = String.valueOf(link.get("ln_buid"));
					if ("1".equals(lntype)) {
						ln_bid.add(lnbuid);
					} else {
						ln_uid.add(lnbuid);
					}
				}
			}
		} catch (Exception e1) {
			logger.error(e1);
		}
		try {
			String new_pid = BaseUtil.generateId();
			map.put("p_id", new_pid);
			map.put("p_name", pname);

			String p_data = String.valueOf(map.get("p_data"));
			if (BaseUtil.isNotEmpty(p_data)) {
				try {
					Paper paper = (Paper) ModelHelper.convertObject(p_data);
					paper.setId(new_pid);
					map.put("p_data", ModelHelper.formatObject(paper));
				} catch (Exception e1) {
					logger.error("��������������������������p_data����");
					e1.printStackTrace();
				}
			}
			map.put("ln_bid", ln_bid.isEmpty() ? null : StringUtils.join(ln_bid.toArray(), ","));
			map.put("ln_uid", ln_uid.isEmpty() ? null : StringUtils.join(ln_uid.toArray(), ","));
			return this.dao.addPaper(map);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}
}

package com.tom.core.service.imp;

import com.tom.core.TomSystemQueue;
import com.tom.core.dao.ICorePaperCheckDao;
import com.tom.core.service.ICorePaperCheckService;
import com.tom.core.service.ICorePaperService;
import com.tom.core.util.PaperServiceHelper;
import com.tom.model.paper.Paper;
import com.tom.model.paper.PaperCheckResult;
import com.tom.model.paper.PaperSection;
import com.tom.model.paper.Question;
import com.tom.model.paper.QuestionBlankFill;
import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.util.FileHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CorePaperCheckService")
public class CorePaperCheckServiceImp implements ICorePaperCheckService {
	private static final Logger logger = Logger.getLogger(CorePaperCheckServiceImp.class);
	@Autowired
	private ICorePaperCheckDao dao;
	@Autowired
	private ICorePaperService corePaperService;

	public PaperCheckResult doCheckPaper(Paper paper, JSONObject json) {
		if (paper == null) {
			logger.error("自动批改:试卷对象不存在.");
			return null;
		}
		if (json == null) {
			logger.error("自动批改:用户答案不存在.");
			return null;
		}
		if ((paper.getSections() == null) || (paper.getSections().size() < 1)) {
			logger.error("自动批改:试卷不完整.");
			return null;
		}
		JSONObject check = new JSONObject();

		int totalScore = 0;
		try {
			Iterator localIterator2;
			for (Iterator localIterator1 = paper.getSections().iterator(); localIterator1
					.hasNext(); localIterator2.hasNext()) {
				PaperSection section = (PaperSection) localIterator1.next();
				if ((section == null) || (section.getQuestions() == null) || (section.getQuestions().size() <= 0)) {
					break ;
				}
				localIterator2 = section.getQuestions().iterator();
//				continue;
				Question question = (Question) localIterator2.next();
				if (question != null) {
					String qType = question.getType();
					String userkey = "";
					try {
						userkey = json.getString("Q-" + question.getId());
					} catch (Exception e) {
						logger.error("自动批改:获取用户答案失败,答案不存在,可能没有作答." + e.getMessage());
					}
					int userScore = 0;
					if (("1".equals(qType)) || ("2".equals(qType)) || ("3".equals(qType))) {
						String _key = BaseUtil.isEmpty(question.getKey()) ? ""
								: question.getKey().replace(",", "").replace(Constants.TM_SPLITER, "").replace(" ", "");

						String _userkey = userkey == null ? "" : userkey.replace(Constants.TM_SPLITER, "");
						if (_key.equals(_userkey)) {
							userScore = question.getScore();
						}
					} else if ("4".equals(qType)) {
						QuestionBlankFill _question = (QuestionBlankFill) question;
						userScore = PaperServiceHelper.BlankFillChecker(_question, userkey);
					}
					check.put("Q-" + question.getId(), Integer.valueOf(userScore));

					totalScore += userScore;
				}
			}
			String pid = json.getString("e_pid");
			String uid = json.getString("e_uid");
			JSONObject userdata = json;
			userdata.remove("e_pid");
			userdata.remove("e_uid");

			PaperCheckResult result = new PaperCheckResult();
			result.setResult(check);
			result.setScore(totalScore);
			result.setPid(pid);
			result.setUid(uid);
			result.setUserdata(userdata);
			result.setSuccess(true);

			logger.info("自动批改完成,结果对象:" + result.toString());

			return result;
		} catch (Exception e) {
			logger.error("自动批改:批改时发生异常." + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public int doSaveUserPapers(List<PaperCheckResult> list) {
		if ((list == null) || (list.size() < 1)) {
			logger.warn("自动批改:保存用户试卷,目标队列为空.");
			return 0;
		}
		List<List<Object>> userpapers = new ArrayList();
		for (PaperCheckResult result : list) {
			List<Object> ls = new ArrayList();

			ls.add(result.getUserdata() == null ? "" : result.getUserdata().toString());
			ls.add(result.getResult() == null ? "" : result.getResult().toString());
			ls.add(Integer.valueOf(result.getScore()));
			ls.add(result.getUid());
			ls.add(result.getPid());

			userpapers.add(ls);
		}
		try {
			return this.dao.saveUserPaper(userpapers);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	public int doCheckPaperQueue() {
		List<Map<String, Object>> list = null;
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = (Map) TomSystemQueue.USER_PAPER_QUEUE.poll();
			if (map != null) {
				if (list == null) {
					list = new ArrayList();
				}
				list.add(map);
			}
		}
		if ((list == null) || (list.size() < 1)) {
			return 0;
		}
		logger.info("批量批改:获取到[" + list.size() + "]份答卷数据");
		List<PaperCheckResult> results = new ArrayList();
		for (Map<String, Object> map : list) {
			String pid = String.valueOf(map.get("e_pid"));
			String uid = String.valueOf(map.get("e_uid"));
			if ((BaseUtil.isEmpty(pid)) || (BaseUtil.isEmpty(uid))) {
				logger.error("批量批改:用户或试卷信息丢失.pid=" + pid + ",uid=" + uid);
			} else {
				Paper paper = this.corePaperService.getPaper(pid);
				if (paper == null) {
					logger.error("批量批改:试卷不存在!!!.pid=" + pid + ",uid=" + uid);
				} else {
					if (1 == paper.getPapertype()) {
						paper = this.corePaperService.getUserRandomPaper(uid, pid);
					}
					if (paper == null) {
						logger.error("批量批改:随机试卷不存在!!!.pid=" + pid + ",uid=" + uid);
					} else {
						JSONObject json = JSONObject.fromObject(map);

						PaperCheckResult result = doCheckPaper(paper, json);
						if ((result != null) && (result.isSuccess())) {
							results.add(result);
						} else {
							logger.error("自动批改发生异常,答卷被存到磁盘文件, map = " + map);
							try {
								String filename = pid + "_" + uid + ".dat";
								String separator = System.getProperty("file.separator");
								String basepath = separator + "files" + separator + "data" + separator;
								String filepath = Constants.getPhysicalPath() + basepath + filename;

								FileHelper.doWriteFile(filepath, json.toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		if ((results != null) && (results.size() > 0)) {
			return doSaveUserPapers(results);
		}
		return 0;
	}
}

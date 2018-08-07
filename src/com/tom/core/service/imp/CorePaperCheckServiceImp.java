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
			logger.error("��������:��������������.");
			return null;
		}
		if (json == null) {
			logger.error("��������:��������������.");
			return null;
		}
		if ((paper.getSections() == null) || (paper.getSections().size() < 1)) {
			logger.error("��������:����������.");
			return null;
		}
		JSONObject check = new JSONObject();

		int totalScore = 0;
		try {
			Iterator localIterator2;
			label417: for (Iterator localIterator1 = paper.getSections().iterator(); localIterator1
					.hasNext(); localIterator2.hasNext()) {
				PaperSection section = (PaperSection) localIterator1.next();
				if ((section == null) || (section.getQuestions() == null) || (section.getQuestions().size() <= 0)) {
					break label417;
				}
				localIterator2 = section.getQuestions().iterator();
				continue;
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

			logger.info("������������������������" + result.toString());

			return result;
		} catch (Exception e) {
			logger.error("��������:��������������." + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public int doSaveUserPapers(List<PaperCheckResult> list) {
		if ((list == null) || (list.size() < 1)) {
			logger.warn("��������:��������������������������.");
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
		logger.info("��������:������[" + list.size() + "]����������");
		List<PaperCheckResult> results = new ArrayList();
		for (Map<String, Object> map : list) {
			String pid = String.valueOf(map.get("e_pid"));
			String uid = String.valueOf(map.get("e_uid"));
			if ((BaseUtil.isEmpty(pid)) || (BaseUtil.isEmpty(uid))) {
				logger.error("��������:������������������.pid=" + pid + ",uid=" + uid);
			} else {
				Paper paper = this.corePaperService.getPaper(pid);
				if (paper == null) {
					logger.error("��������:����������!!!.pid=" + pid + ",uid=" + uid);
				} else {
					if (1 == paper.getPapertype()) {
						paper = this.corePaperService.getUserRandomPaper(uid, pid);
					}
					if (paper == null) {
						logger.error("��������:��������������!!!.pid=" + pid + ",uid=" + uid);
					} else {
						JSONObject json = JSONObject.fromObject(map);

						PaperCheckResult result = doCheckPaper(paper, json);
						if ((result != null) && (result.isSuccess())) {
							results.add(result);
						} else {
							logger.error("������������������������������������, map = " + map);
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

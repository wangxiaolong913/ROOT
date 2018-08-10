package com.tom.util;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.field.RtfPageNumber;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.tom.model.paper.Option;
import com.tom.model.paper.Paper;
import com.tom.model.paper.PaperSection;
import com.tom.model.paper.Question;
import com.tom.model.paper.QuestionMultipleChoice;
import com.tom.model.paper.QuestionSingleChoice;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OfficeToolWord {
	private static String TECH_INFO = "";
	private static String FOOT_INFO = "";

	private void makeWordDocument(String path, String WTITLE) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		RtfWriter2.getInstance(document, new FileOutputStream(path));
		document.open();

		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

		Font titleFont = new Font(bfChinese, 14.0F, 1);

		Font subTitleFont = new Font(bfChinese, 11.0F, 1);

		Font contextFont = new Font(bfChinese, 10.0F, 0);

		Font headerFooterFont = new Font(bfChinese, 9.0F, 0);

		Table header = new Table(2);
		header.setBorder(0);
		header.setWidth(100.0F);

		Paragraph address = new Paragraph(TECH_INFO);
		address.setFont(headerFooterFont);
		Cell cell01 = new Cell(address);
		cell01.setBorder(0);
		header.addCell(cell01);

		Paragraph date = new Paragraph("生成日期: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		date.setAlignment(2);
		date.setFont(headerFooterFont);
		cell01 = new Cell(date);
		cell01.setBorder(0);
		header.addCell(cell01);
		document.setHeader(new RtfHeaderFooter(header));

		Table footer = new Table(2);
		footer.setBorder(0);
		footer.setWidth(100.0F);

		Paragraph company = new Paragraph(FOOT_INFO);
		company.setFont(headerFooterFont);
		Cell cell02 = new Cell(company);
		cell02.setBorder(0);
		footer.addCell(cell02);

		Paragraph pageNumber = new Paragraph("第 ");
		pageNumber.add(new RtfPageNumber());
		pageNumber.add(new Chunk(" 页"));
		pageNumber.setAlignment(2);
		pageNumber.setFont(headerFooterFont);
		cell02 = new Cell(pageNumber);
		cell02.setBorder(0);
		footer.addCell(cell02);

		document.setFooter(new RtfHeaderFooter(footer));

		Paragraph title = new Paragraph(WTITLE);
		title.setAlignment(1);
		title.setFont(titleFont);
		document.add(title);
		for (int i = 0; i < 5; i++) {
			Paragraph subTitle = new Paragraph(i + 1 + "、标题" + (i + 1));
			subTitle.setFont(subTitleFont);
			subTitle.setSpacingBefore(10.0F);
			subTitle.setFirstLineIndent(0.0F);
			document.add(subTitle);
			for (int j = 0; j < 3; j++) {
				String contextString = j + 1 + "."
						+ "iText是一个能够快速产生PDF文件的java类库。iText的java类对于那些要产生包含文本,表格,图形的只读文档是很有用的";
				Paragraph context = new Paragraph(contextString);
				context.setAlignment(0);
				context.setFont(contextFont);
				context.setSpacingBefore(10.0F);
				context.setFirstLineIndent(0.0F);
				document.add(context);
				for (short k = 0; k < 4; k = (short) (k + 1)) {
					char enString = 'A';
					String answerString = (char) (enString + k) + "." + "表格,图形的只读文档是很有用的.";
					Paragraph answer = new Paragraph(answerString);
					answer.setAlignment(0);
					answer.setFont(contextFont);
					answer.setSpacingBefore(10.0F);
					answer.setFirstLineIndent(20.0F);
					document.add(answer);
				}
			}
		}
		document.close();
	}

	public static void makePaperDoc(String path, Paper paper) throws Exception {
		if (path == null) {
			throw new Exception("目标路径为空");
		}
		if (paper == null) {
			throw new Exception("试卷不存在");
		}
		Document document = new Document(PageSize.A4);
		RtfWriter2.getInstance(document, new FileOutputStream(path));
		document.open();

		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);

		Font titleFont = new Font(bfChinese, 14.0F, 1);
		Font subTitleFont = new Font(bfChinese, 11.0F, 1);
		Font contextFont = new Font(bfChinese, 10.0F, 0);
		Font headerFooterFont = new Font(bfChinese, 9.0F, 0);

		Table header = new Table(2);
		header.setBorder(0);
		header.setWidth(100.0F);

		Paragraph address = new Paragraph(TECH_INFO);
		address.setFont(headerFooterFont);
		Cell cell01 = new Cell(address);
		cell01.setBorder(0);
		header.addCell(cell01);

		Paragraph date = new Paragraph("生成日期: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		date.setAlignment(2);
		date.setFont(headerFooterFont);
		cell01 = new Cell(date);
		cell01.setBorder(0);
		header.addCell(cell01);
		document.setHeader(new RtfHeaderFooter(header));

		Table footer = new Table(2);
		footer.setBorder(0);
		footer.setWidth(100.0F);

		Paragraph company = new Paragraph(FOOT_INFO);
		company.setFont(headerFooterFont);
		Cell cell02 = new Cell(company);
		cell02.setBorder(0);
		footer.addCell(cell02);

		Paragraph pageNumber = new Paragraph("第 ");
		pageNumber.add(new RtfPageNumber());
		pageNumber.add(new Chunk(" 页"));
		pageNumber.setAlignment(2);
		pageNumber.setFont(headerFooterFont);
		cell02 = new Cell(pageNumber);
		cell02.setBorder(0);
		footer.addCell(cell02);

		document.setFooter(new RtfHeaderFooter(footer));

		Paragraph title = new Paragraph(paper.getName());
		title.setAlignment(1);
		title.setFont(titleFont);
		document.add(title);

		List<PaperSection> LIST_SECTIONS = paper.getSections();
		int rownbr = 0;
		if ((LIST_SECTIONS != null) && (LIST_SECTIONS.size() > 0)) {
			Iterator localIterator2;
			for (Iterator localIterator1 = LIST_SECTIONS.iterator(); localIterator1.hasNext(); localIterator2
					.hasNext()) {
				PaperSection section = (PaperSection) localIterator1.next();
				Paragraph subTitle = new Paragraph(section.getName() + "," + section.getRemark() + ",共"
						+ (section.getQuestions() == null ? 0 : section.getQuestions().size()) + "题");
				subTitle.setFont(subTitleFont);
				subTitle.setSpacingBefore(10.0F);
				subTitle.setFirstLineIndent(0.0F);
				document.add(subTitle);

				List<Question> LIST_QUESTIONS = section.getQuestions();
				if ((LIST_QUESTIONS == null) || (LIST_QUESTIONS.size() <= 0)) {
					break;
				}
				if ("1".equals(Integer.valueOf(paper.getOrdertype()))) {
					Collections.shuffle(LIST_QUESTIONS);
				}
				localIterator2 = LIST_QUESTIONS.iterator(); 
//				continue;
				Question question = (Question)localIterator2.next();
		        String qtype = question.getType();
		        rownbr++;
		        
		        String question_content = "";
		        if ("4".equals(qtype)) {
		          question_content = BaseUtil.FormatBlankQuestions(
		            question.getContent(), "_______");
		        } else {
		          question_content = question.getContent();
		        }
		        question_content = BaseUtil.Html2TextFormat(question_content);
		        
		        Paragraph context = new Paragraph("第" + rownbr + "题,分值:(" + question.getScore() + ")\n" + question_content);
		        context.setAlignment(0);
		        context.setFont(contextFont);
		        context.setSpacingBefore(10.0F);
		        context.setFirstLineIndent(0.0F);
		        document.add(context);
		        
		        StringBuffer OPTIONS = new StringBuffer("");
		        if ("1".equals(qtype))
		        {
		          QuestionSingleChoice _question = (QuestionSingleChoice)question;
		          List<Option> LIST_OPTIONS = _question.getOptions();
		          if ((LIST_OPTIONS != null) && (LIST_OPTIONS.size() > 0)) {
		            for (Option option : LIST_OPTIONS) {
		              OPTIONS.append(option.getAlisa() + " : " + option.getText() + "\n");
		            }
		          }
		        }
		        else if ("2".equals(qtype))
		        {
		          QuestionMultipleChoice _question = (QuestionMultipleChoice)question;
		          List<Option> LIST_OPTIONS = _question.getOptions();
		          if ((LIST_OPTIONS != null) && (LIST_OPTIONS.size() > 0)) {
		            for (Option option : LIST_OPTIONS) {
		              OPTIONS.append(option.getAlisa() + " : " + option.getText() + "\n");
		            }
		          }
		        }
		        else if ("3".equals(qtype))
		        {
		          OPTIONS.append("正确       错误");
		        }
		        else if ((!"4".equals(qtype)) && ("5".equals(qtype)))
		        {
		          OPTIONS.append("请答题\n\n\n");
		        }
		        Paragraph options = new Paragraph(OPTIONS.toString());
		        options.setAlignment(0);
		        options.setFont(contextFont);
		        options.setSpacingBefore(10.0F);
		        options.setFirstLineIndent(0.0F);
		        document.add(options);
		      }
		    }
		    document.close();
		  }
		}

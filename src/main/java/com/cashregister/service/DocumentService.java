package com.cashregister.service;

import java.io.FileOutputStream;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import com.cashregister.dto.DeviceDTO;

@Service
@Transactional
public class DocumentService extends BaseService {

	public String generateDocument(DeviceDTO deviceDTO) throws Exception {
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun titleRun = title.createRun();
		titleRun.setText("Build Your REST API with Spring");
		titleRun.setColor("009933");
		titleRun.setBold(true);
		titleRun.setFontFamily("Courier");
		titleRun.setFontSize(20);

		XWPFParagraph subTitle = document.createParagraph();
		subTitle.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun subTitleRun = subTitle.createRun();
		subTitleRun.setText("from HTTP fundamentals to API Mastery");
		subTitleRun.setColor("00CC44");
		subTitleRun.setFontFamily("Courier");
		subTitleRun.setFontSize(16);
		subTitleRun.setTextPosition(20);
		subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);

		FileOutputStream out = new FileOutputStream("D://asd.docx");
		document.write(out);
		out.close();
		document.close();

		XWPFDocument doc = new XWPFDocument(OPCPackage.open("D://asd.docx"));
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					System.out.println(r);
					if (text != null && text.contains("REST")) {
						text = text.replace("REST", "RESTCHE");
						r.setText(text, 0);
					}
				}
			}
		}
		doc.write(new FileOutputStream("D://output.docx"));
		return "Document is created!";
	}

}

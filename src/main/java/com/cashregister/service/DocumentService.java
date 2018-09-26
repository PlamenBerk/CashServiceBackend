package com.cashregister.service;

import java.io.FileOutputStream;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import com.cashregister.dto.DeviceDTO;
import com.cashregister.model.Device;

@Service
@Transactional
public class DocumentService extends BaseService {

	private static final String DOC_TEMPLATE = "D://asd.docx";
	private static final String DOC_OUTPUT_PATH = "";

	public String generateDocument(DeviceDTO deviceDTO, Integer deviceId, String docType) throws Exception {

		switch (docType) {
		case "contract":

			break;

		default:
			break;
		}

		Device device = getEm().find(Device.class, deviceId);

		XWPFDocument doc = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE));
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
		doc.close();

		return "Document is created!";
	}

}

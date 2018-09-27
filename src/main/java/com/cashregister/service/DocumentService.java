package com.cashregister.service;

import java.io.FileOutputStream;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import com.cashregister.dto.DocumentDTO;
import com.cashregister.model.Device;

@Service
@Transactional
public class DocumentService extends BaseService {

	private static final String DOC_TEMPLATE = "D://CashRegisterDocs/doc-template.docx";
	private static final String DOC_OUTPUT_PATH = "";

	public String generateDocument(DocumentDTO documentDTO) throws Exception {

		Device device = getEm().find(Device.class, Integer.valueOf(documentDTO.getDeviceId()));

		switch (documentDTO.getDocType()) {
		case "contract":
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE));
			XWPFDocument docTemplate = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE));
			for (XWPFParagraph p : doc.getParagraphs()) {
				List<XWPFRun> runs = p.getRuns();
				if (runs != null) {
					for (XWPFRun r : runs) {
						String text = r.getText(0);
						System.out.println(r);
						if (text != null && text.contains("clientName")) {
							text = text.replace("clientName", device.getSite().getClient().getName());
							r.setText(text, 0);
						}
						if (text != null && text.contains("clientBulstat")) {
							text = text.replace("clientBulstat", device.getSite().getClient().getBulstat());
							r.setText(text, 0);
						}
						if (text != null && text.contains("clientAddress")) {
							text = text.replace("clientAddress", device.getSite().getClient().getAddress());
							r.setText(text, 0);
						}
						if (text != null && text.contains("clientManagerName")) {
							text = text.replace("clientManagerName",
									device.getSite().getClient().getManager().getName());
							r.setText(text, 0);
						}
						if (text != null && text.contains("deviceModel")) {
							text = text.replace("deviceModel", device.getDeviceModel().getModel());
							r.setText(text, 0);
						}
						if (text != null && text.contains("deviceNum")) {
							text = text.replace("deviceNum", device.getDeviceNumPostfix());
							r.setText(text, 0);
						}
						if (text != null && text.contains("fiscalNum")) {
							text = text.replace("fiscalNum", device.getFiscalNumPostfix());
							r.setText(text, 0);
						}
					}
				}
			}

			doc.write(new FileOutputStream("D://output.docx"));
			Runtime.getRuntime().exec("cmd /c start D:/output.docx /K ");
			doc = docTemplate; // save da original template
			doc.close();

			break;

		case "protocol":

			break;

		case "certificate":

			break;

		default:
			break;
		}

		return "Document is created!";
	}

}

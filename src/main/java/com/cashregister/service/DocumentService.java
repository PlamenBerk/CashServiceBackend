package com.cashregister.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.cashregister.config.FileStructureOrganizer;
import com.cashregister.dto.DocumentDTO;
import com.cashregister.model.Device;
import com.cashregister.model.Document;

@Service
@Transactional
public class DocumentService extends BaseService {

	private static final int NEXT_YEAR = 1;
	private static final String DOC_TEMPLATE = "D://CashRegisterDocs/doc-template.docx";

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

			String docPath = FileStructureOrganizer.CURRENT_FOLDER_LOCATION;
			String docName = device.getSite().getClient().getName() + "_" + device.getDeviceModel().getManufacturer()
					+ "_" + device.getDeviceModel().getModel() + ".docx";

			doc.write(new FileOutputStream(docPath + "/" + docName));

			saveDocInDB(docPath, docName);

			Runtime.getRuntime().exec("cmd /c start " + docPath + "/" + docName + " /K ");

			doc = docTemplate; // save the original template
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

	private void saveDocInDB(String docPath, String docName) throws Exception {
		Document dbDocument = new Document();
		dbDocument.setDocPath(docPath);
		dbDocument.setDocumentName(docName);
		LocalDate startDate = LocalDate.now();
		dbDocument.setStartDate(startDate);
		dbDocument.setEndDate(startDate.plusYears(NEXT_YEAR));
		getEm().persist(dbDocument);
	}

	public List<Document> getExpiredDocuments(LocalDate localStartDate, LocalDate localEndDate) {
		List<Document> listDocs = getEm().createNamedQuery("getExpiredBetweenDates", Document.class)
				.setParameter("startDate", localStartDate).setParameter("endDate", localEndDate).getResultList();
		return listDocs;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public InputStream loadFileAsIs(Integer docId) throws URISyntaxException, FileNotFoundException, Exception {
		Document doc = getEm().find(Document.class, docId);

		File f = new File(doc.getDocPath() + "\\" + doc.getDocumentName());
		InputStream stream = new FileInputStream(f);
		return stream;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Resource loadFileAsResource(Integer docId) throws URISyntaxException, FileNotFoundException, Exception {
		Document doc = getEm().find(Document.class, docId);

		File f = new File(doc.getDocPath() + "asd\\" + doc.getDocumentName());
		URI u = f.toURI();
		Resource resource = new UrlResource(u);
		if (resource.exists()) {
			return resource;
		} else {
			throw new FileNotFoundException("File not found " + doc.getDocumentName());
		}

	}
}

package com.cashregister.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.cashregister.config.FileStructureOrganizer;
import com.cashregister.dto.CertificateDTO;
import com.cashregister.dto.DocumentDTO;
import com.cashregister.model.Device;
import com.cashregister.model.Document;

@Service
@Transactional
public class DocumentService extends BaseService {

	private static final int NEXT_SIX_MONTHS = 6;
	private static final int NEXT_YEAR = 1;
	private static final String DOC_TEMPLATE = "D://CashRegisterDocs/doc-template.docx";
	private static final String DOC_TEMPLATE_CERT = "D://CashRegisterDocs/svidetelstvo_template.docx";
	// private String DOC_TEMPLATE_DEBIAN =
	// "/home/plamendanielpics/cashregister/doc-template.docx";

	// private String DOC_TEMPLATE_DEBIAN =
	// "/home/plamendanielpics/cashregister/svidetelstvo_template.docx";

	public Resource generateDocument(DocumentDTO documentDTO) throws Exception {

		Device device = getEm().find(Device.class, Integer.valueOf(documentDTO.getDeviceId()));

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
						text = text.replace("clientManagerName", device.getSite().getClient().getManager().getName());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceModel")) {
						text = text.replace("deviceModel", device.getDeviceModel().getModel());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceNum")) {
						text = text.replace("deviceNum",
								device.getDeviceModel().getDeviceNumPrefix() + device.getDeviceNumPostfix());
						r.setText(text, 0);
					}
					if (text != null && text.contains("fiscalNum")) {
						text = text.replace("fiscalNum",
								device.getDeviceModel().getFiscalNumPrefix() + device.getFiscalNumPostfix());
						r.setText(text, 0);
					}
					if (text != null && text.contains("contractNumber")) {
						text = text.replace("contractNumber", String.valueOf(documentDTO.getContractNumber()));
						r.setText(text, 0);
					}
					if (text != null && text.contains("contractDateHeader")) {
						text = text.replace("contractDateHeader", documentDTO.getFromDate());
						r.setText(text, 0);
					}
					if (text != null && text.contains("dateFrom")) {
						text = text.replace("dateFrom", documentDTO.getFromDate());
						r.setText(text, 0);
					}
					if (text != null && text.contains("dateTo")) {
						text = text.replace("dateTo", documentDTO.getToDate());
						r.setText(text, 0);
					}
					if (text != null && text.contains("price")) {
						text = text.replace("price", documentDTO.getPrice());
						r.setText(text, 0);
					}
					if (text != null && text.contains("currentDate")) {
						text = text.replace("currentDate", documentDTO.getFromDate());
						r.setText(text, 0);
					}
				}
			}
		}

		String docPath = FileStructureOrganizer.CURRENT_FOLDER_LOCATION;
		String docName = "contract_" + device.getSite().getClient().getName() + "_"
				+ device.getDeviceModel().getManufacturer() + "_" + device.getDeviceModel().getModel() + "_"
				+ device.getDeviceModel().getDeviceNumPrefix() + device.getDeviceNumPostfix() + "_" + LocalDate.now()
				+ ".docx";

		doc.write(new FileOutputStream(docPath + "/" + docName));

		saveDocInDB(docPath, docName, documentDTO.getSelectedValueValidy(), documentDTO.getContractNumber(), device);

		doc = docTemplate; // save the original template
		doc.close();

		File f = new File(docPath + "/" + docName);
		URI u = f.toURI();
		Resource resource = new UrlResource(u);

		if (resource.exists()) {
			return resource;
		} else {
			throw new FileNotFoundException("File not found " + docName);
		}

	}

	public Resource generateDocumentCertificate(CertificateDTO certificateDTO)
			throws FileNotFoundException, IOException, InvalidFormatException {
		Device device = getEm().find(Device.class, Integer.valueOf(certificateDTO.getDeviceId()));

		Document docFromDB = getEm().createNamedQuery("getDocumentByDeviceId", Document.class)
				.setParameter("pDeviceId", device.getId()).getSingleResult();

		XWPFDocument doc = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_CERT));
		XWPFDocument docTemplate = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_CERT));
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					if (text != null && text.contains("currDate")) {
						LocalDate localDate = LocalDate.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						String formattedStringDate = localDate.format(formatter);
						text = text.replace("currDate", formattedStringDate);
						r.setText(text, 0);
					}
					if (text != null && text.contains("svidNum")) {
						text = text.replace("svidNum", certificateDTO.getCertNumber());
						r.setText(text, 0);
					}
					if (text != null && text.contains("EIK")) {
						text = text.replace("EIK", device.getSite().getClient().getBulstat());
						r.setText(text, 0);
					}
					if (text != null && text.contains("EGN")) {
						text = text.replace("EGN", device.getSite().getClient().getEGN());
						r.setText(text, 0);
					}
					if (text != null && text.contains("clientName")) {
						text = text.replace("clientName", device.getSite().getClient().getName());
						r.setText(text, 0);
					}
					if (text != null && text.contains("clientAddress")) {
						text = text.replace("clientAddress", device.getSite().getClient().getAddress());
						r.setText(text, 0);
					}
					if (text != null && text.contains("clientManagerName")) {
						text = text.replace("clientManagerName", device.getSite().getClient().getManager().getName());
						r.setText(text, 0);
					}
					if (text != null && text.contains("siteName")) {
						text = text.replace("siteName", device.getSite().getName());
						r.setText(text, 0);
					}
					if (text != null && text.contains("siteAddress")) {
						text = text.replace("siteAddress", device.getSite().getAddress());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceModel")) {
						text = text.replace("deviceModel", device.getDeviceModel().getModel());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceTemplateCert")) {
						text = text.replace("deviceTemplateCert", device.getDeviceModel().getCertificate());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceNumber")) {
						text = text.replace("deviceNumber",
								device.getDeviceModel().getDeviceNumPrefix() + device.getDeviceNumPostfix());
						r.setText(text, 0);
					}
					if (text != null && text.contains("fiscalNumber")) {
						text = text.replace("fiscalNumber",
								device.getDeviceModel().getFiscalNumPrefix() + device.getFiscalNumPostfix());
						r.setText(text, 0);
					}
					if (text != null && text.contains("contractD")) {
						LocalDate date = docFromDB.getStartDate();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						String formattedStringDate = date.format(formatter);
						text = text.replace("contractD", formattedStringDate);
						r.setText(text, 0);
					}
					if (text != null && text.contains("contractNumber")) {
						text = text.replace("contractNumber", docFromDB.getDocNumber());
						r.setText(text, 0);
					}
				}
			}
		}

		String docPath = FileStructureOrganizer.CURRENT_FOLDER_LOCATION;
		String docName = "certificate_" + device.getSite().getClient().getName() + "_"
				+ device.getDeviceModel().getManufacturer() + "_" + device.getDeviceModel().getModel() + "_"
				+ device.getDeviceModel().getDeviceNumPrefix() + device.getDeviceNumPostfix() + "_" + LocalDate.now()
				+ ".docx";

		doc.write(new FileOutputStream(docPath + "/" + docName));

		doc = docTemplate; // save the original template
		doc.close();

		File f = new File(docPath + "/" + docName);
		URI u = f.toURI();
		Resource resource = new UrlResource(u);

		if (resource.exists()) {
			return resource;
		} else {
			throw new FileNotFoundException("File not found " + docName);
		}
	}

	private void saveDocInDB(String docPath, String docName, int valid, String contractNumber, Device device)
			throws Exception {
		Document dbDocument = new Document();
		dbDocument.setDevice(device);
		dbDocument.setDocNumber(contractNumber);
		dbDocument.setDocPath(docPath);
		dbDocument.setDocumentName(docName);
		LocalDate startDate = LocalDate.now();
		dbDocument.setStartDate(startDate);
		if (valid == 6) {
			dbDocument.setEndDate(startDate.plusMonths(NEXT_SIX_MONTHS));
		} else {
			dbDocument.setEndDate(startDate.plusYears(NEXT_YEAR));
		}
		getEm().persist(dbDocument);
	}

	public List<Document> getExpiredDocuments(LocalDate localStartDate, LocalDate localEndDate) {
		List<Document> listDocs = getEm().createNamedQuery("getExpiredBetweenDates", Document.class)
				.setParameter("startDate", localStartDate).setParameter("endDate", localEndDate).getResultList();
		return listDocs;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Resource loadFileAsResource(Integer docId) throws URISyntaxException, FileNotFoundException, Exception {
		Document doc = getEm().find(Document.class, docId);

		File f = new File(doc.getDocPath() + "/" + doc.getDocumentName());
		System.out.println("Try to open " + f.getPath() + " ::: " + f.getAbsolutePath());
		URI u = f.toURI();
		Resource resource = new UrlResource(u);
		if (resource.exists()) {
			return resource;
		} else {
			throw new FileNotFoundException("File not found " + doc.getDocumentName());
		}

	}

}

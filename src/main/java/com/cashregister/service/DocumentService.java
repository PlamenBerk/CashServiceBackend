package com.cashregister.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
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
import com.cashregister.dto.ProtocolDTO;
import com.cashregister.model.Device;
import com.cashregister.model.Document;

@Service
@Transactional
public class DocumentService extends BaseService {

	private static final int NEXT_SIX_MONTHS = 6;
	private static final int NEXT_YEAR = 1;

	// private static final String DOC_TEMPLATE =
	// "D://CashRegisterDocs/doc-template.docx";
	// private static final String DOC_TEMPLATE_CERT =
	// "D://CashRegisterDocs/svidetelstvo_template.docx";
	// private static final String DOC_TEMPLATE_PROTOCOL =
	// "D://CashRegisterDocs/protocol_template.docx";
	// private String DOC_TEMPLATE_REQUEST =
	// "D://CashRegisterDocs/request_template.docx";

	// for debian
	private String DOC_TEMPLATE = "/home/plamendanielpics/cashregister/doc-template.docx";
	private String DOC_TEMPLATE_CERT = "/home/plamendanielpics/cashregister/svidetelstvo_template.docx";
	private String DOC_TEMPLATE_PROTOCOL = "/home/plamendanielpics/cashregister/protocol_template.docx";
	private String DOC_TEMPLATE_REQUEST = "/home/plamendanielpics/cashregister/protocol_request.docx";

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
						String clientBulstatOrEGN = device.getSite().getClient().getBulstat().length() < 3
								? device.getSite().getClient().getEGN()
								: device.getSite().getClient().getBulstat();
						text = text.replace("clientBulstat", clientBulstatOrEGN);
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
						text = text.replace("contractDateHeader", documentDTO.getFromDate().toString());
						r.setText(text, 0);
					}
					if (text != null && text.contains("dateFrom")) {
						text = text.replace("dateFrom", documentDTO.getFromDate().toString());
						r.setText(text, 0);
					}
					if (text != null && text.contains("dateTo")) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						LocalDate endDate = LocalDate.parse(documentDTO.getFromDate(), formatter);
						if (documentDTO.getSelectedValueValidy() == 6) {
							endDate = endDate.plusMonths(NEXT_SIX_MONTHS);
						} else {
							endDate = endDate.plusYears(NEXT_YEAR);
						}
						text = text.replace("dateTo", endDate.format(formatter));
						r.setText(text, 0);
					}
					if (text != null && text.contains("price")) {
						text = text.replace("price", documentDTO.getPrice());
						r.setText(text, 0);
					}
					if (text != null && text.contains("currentDate")) {
						text = text.replace("currentDate", documentDTO.getFromDate().toString());
						r.setText(text, 0);
					}
				}
			}
		}

		String docPath = FileStructureOrganizer.CURRENT_FOLDER_LOCATION;
		String docName = "contract_" + device.getSite().getClient().getName() + "_"
				+ device.getDeviceModel().getManufacturer() + "_" + device.getDeviceModel().getModel() + "_"
				+ device.getDeviceModel().getDeviceNumPrefix() + device.getDeviceNumPostfix() + "_" + LocalDate.now()
				+ "_" + String.valueOf(System.currentTimeMillis()) + ".docx";

		doc.write(new FileOutputStream(docPath + "/" + docName));

		saveDocInDB(docPath, docName, documentDTO.getSelectedValueValidy(), documentDTO.getContractNumber(), device,
				documentDTO.getFromDate());

		doc = docTemplate; // save the original template
		doc.close();
		docTemplate.close();

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

		Document docFromDB = getEm().createNamedQuery("getDocumentByDeviceId", Document.class).setMaxResults(1)
				.setParameter("pDeviceId", device.getId()).getSingleResult();

		XWPFDocument doc = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_CERT));
		XWPFDocument docTemplate = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_CERT));
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					if (text != null && text.contains("currDate")) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
						LocalDate date = LocalDate.parse(certificateDTO.getFromDateStr(), formatter);
						text = text.replace("currDate", date.format(formatter));
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
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
				+ "_" + String.valueOf(System.currentTimeMillis()) + ".docx";

		doc.write(new FileOutputStream(docPath + "/" + docName));

		doc = docTemplate; // save the original template
		doc.close();
		docTemplate.close();

		if (device.getDateOfUsage() == null) {
			device.setDateOfUsage(LocalDate.now());
		}

		if (device.getIsNewFiscalNum()) {
			device.setDateOfUsage(LocalDate.now());
			device.setIsNewFiscalNum(false);
		}

		File f = new File(docPath + "/" + docName);
		URI u = f.toURI();
		Resource resource = new UrlResource(u);

		if (resource.exists()) {
			return resource;
		} else {
			throw new FileNotFoundException("File not found " + docName);
		}
	}

	public Resource generateDocumentProtocol(ProtocolDTO protocolDTO) throws InvalidFormatException, IOException {
		Device device = getEm().find(Device.class, Integer.valueOf(protocolDTO.getDeviceId()));

		XWPFDocument doc = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_PROTOCOL));
		XWPFDocument docTemplate = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_PROTOCOL));
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
					if (text != null && text.contains("currenttime")) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
						LocalTime time = LocalTime.now();
						String f = formatter.format(time);
						text = text.replace("currenttime", f);
						r.setText(text, 0);
					}
					if (text != null && text.contains("egnorbulstat")) {
						String egnOrBulstat = device.getSite().getClient().getBulstat().length() < 3
								? device.getSite().getClient().getEGN()
								: device.getSite().getClient().getBulstat();
						text = text.replace("egnorbulstat", egnOrBulstat);
						r.setText(text, 0);
					}
					if (text != null && text.contains("clientName")) {
						text = text.replace("clientName", device.getSite().getClient().getName());
						r.setText(text, 0);
					}
					if (text != null && text.contains("clientaddress")) {
						text = text.replace("clientaddress", device.getSite().getClient().getAddress());
						r.setText(text, 0);
					}
					if (text != null && text.contains("managerName")) {
						text = text.replace("managerName", device.getSite().getClient().getManager().getName());
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
					if (text != null && text.contains("sitePhone")) {
						text = text.replace("sitePhone", device.getSite().getPhone());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceManuf")) {
						text = text.replace("deviceManuf", device.getDeviceModel().getManufacturer());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceModel")) {
						text = text.replace("deviceModel", device.getDeviceModel().getModel());
						r.setText(text, 0);
					}
					if (text != null && text.contains("devCert")) {
						text = text.replace("devCert", device.getDeviceModel().getCertificate());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceModelManuf")) {
						text = text.replace("deviceModelManuf", device.getDeviceModel().getManufacturer());
						r.setText(text, 0);
					}
					if (text != null && text.contains("EIKDEVICEMODEL")) {
						text = text.replace("EIKDEVICEMODEL", device.getDeviceModel().getEik());
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
					if (text != null && text.contains("freeText")) {
						text = text.replace("freeText", protocolDTO.getReason());
						r.setText(text, 0);
					}
					if (text != null && text.contains("deviceDateCreation")) {
						// LocalDate localDate = device.getDateOfUsage(); tova shte vaji samo za novi
						// aparati
						LocalDate localDate = device.getNapDate();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						String formattedStringDate = localDate.format(formatter);
						text = text.replace("deviceDateCreation", formattedStringDate);
						r.setText(text, 0);
					}
					if (text != null && text.contains("justPrice")) {
						text = text.replace("justPrice", protocolDTO.getPrice());
						r.setText(text, 0);
					}
					if (text != null && text.contains("aPrice")) {
						text = text.replace("aPrice", protocolDTO.getAprice());
						r.setText(text, 0);
					}
					if (text != null && text.contains("bPrice")) {
						text = text.replace("bPrice", protocolDTO.getBprice());
						r.setText(text, 0);
					}
					if (text != null && text.contains("vPrice")) {
						text = text.replace("vPrice", protocolDTO.getVprice());
						r.setText(text, 0);
					}
					if (text != null && text.contains("gPrice")) {
						text = text.replace("gPrice", protocolDTO.getGprice());
						r.setText(text, 0);
					}
					if (text != null && text.contains("TDD")) {
						text = text.replace("TDD", device.getSite().getClient().getTDD());
						r.setText(text, 0);
					}
					if (text != null && text.contains("TDD")) {
						text = text.replace("TDD", device.getSite().getClient().getTDD());
						r.setText(text, 0);
					}
					if (text != null && text.contains("managerName")) {
						text = text.replace("managerName", device.getSite().getClient().getManager().getName());
						r.setText(text, 0);
					}

				}
			}
		}

		String docPath = FileStructureOrganizer.CURRENT_FOLDER_LOCATION;
		String docName = "protocol_" + device.getSite().getClient().getName() + "_"
				+ device.getDeviceModel().getManufacturer() + "_" + device.getDeviceModel().getModel() + "_"
				+ device.getDeviceModel().getDeviceNumPrefix() + device.getDeviceNumPostfix() + "_" + LocalDate.now()
				+ "_" + String.valueOf(System.currentTimeMillis()) + ".docx";

		doc.write(new FileOutputStream(docPath + "/" + docName));

		doc = docTemplate; // save the original template
		doc.close();
		docTemplate.close();

		File f = new File(docPath + "/" + docName);
		URI u = f.toURI();
		Resource resource = new UrlResource(u);

		if (resource.exists()) {
			return resource;
		} else {
			throw new FileNotFoundException("File not found " + docName);
		}
	}

	public Resource generateDocumentRequest(String deviceId) throws InvalidFormatException, IOException {
		Device device = getEm().find(Device.class, Integer.valueOf(deviceId));

		XWPFDocument doc = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_REQUEST));
		XWPFDocument docTemplate = new XWPFDocument(OPCPackage.open(DOC_TEMPLATE_REQUEST));
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					if (text != null && text.contains("managerName")) {
						text = text.replace("managerName", device.getSite().getClient().getManager().getName());
						r.setText(text, 0);
					}
					if (text != null && text.contains("egnorbulstat")) {
						String egnOrBulstat = device.getSite().getClient().getBulstat().length() < 3
								? device.getSite().getClient().getEGN()
								: device.getSite().getClient().getBulstat();
						text = text.replace("egnorbulstat", egnOrBulstat);
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
					if (text != null && text.contains("deviceCert")) {
						text = text.replace("deviceCert", device.getDeviceModel().getCertificate());
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
						Document document = getEm().createNamedQuery("getContractWithMaxID", Document.class)
								.setParameter("pDeviceId", Integer.valueOf(deviceId)).setMaxResults(1)
								.getSingleResult();

						text = text.replace("contractNumber", document.getDocNumber());
						r.setText(text, 0);
					}
					if (text != null && text.contains("contractDate")) {
						LocalDate localDate = LocalDate.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						String formattedStringDate = localDate.format(formatter);
						text = text.replace("contractDate", formattedStringDate);
						r.setText(text, 0);
					}
				}
			}
		}

		String docPath = FileStructureOrganizer.CURRENT_FOLDER_LOCATION;
		String docName = "request_" + device.getSite().getClient().getName() + "_"
				+ device.getDeviceModel().getManufacturer() + "_" + device.getDeviceModel().getModel() + "_"
				+ device.getDeviceModel().getDeviceNumPrefix() + device.getDeviceNumPostfix() + "_" + LocalDate.now()
				+ "_" + String.valueOf(System.currentTimeMillis()) + ".docx";

		doc.write(new FileOutputStream(docPath + "/" + docName));

		doc = docTemplate; // save the original template
		doc.close();
		docTemplate.close();

		File f = new File(docPath + "/" + docName);
		URI u = f.toURI();
		Resource resource = new UrlResource(u);

		if (resource.exists()) {
			return resource;
		} else {
			throw new FileNotFoundException("File not found " + docName);
		}
	}

	private void saveDocInDB(String docPath, String docName, int valid, String contractNumber, Device device,
			String fromDate) throws Exception {
		Document dbDocument = new Document();
		dbDocument.setDevice(device);
		dbDocument.setDocNumber(contractNumber);
		dbDocument.setDocPath(docPath);
		dbDocument.setDocumentName(docName);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate startDate = LocalDate.parse(fromDate, formatter);
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

	public Resource rewriteDocument(DocumentDTO documentDTO, int docId) throws Exception {
		Document doc = getEm().find(Document.class, docId);
		Resource resource = null;
		try {
			doc.setIsRewrited(Boolean.TRUE);
			documentDTO.setDeviceId(doc.getDevice().getId().toString());
			resource = generateDocument(documentDTO);
		} catch (Exception e) {
			doc.setIsRewrited(Boolean.FALSE);
		}

		return resource;
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

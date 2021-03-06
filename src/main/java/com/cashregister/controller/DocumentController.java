package com.cashregister.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.dto.CertificateDTO;
import com.cashregister.dto.DocumentDTO;
import com.cashregister.dto.ProtocolDTO;
import com.cashregister.model.Document;
import com.cashregister.service.DocumentService;

@CrossOrigin()
@RestController
@RequestMapping("/document-management")
public class DocumentController {

	@Autowired
	private DocumentService docService;

	@RequestMapping(value = "/document", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resource> generateDocument(@RequestBody DocumentDTO documentDTO) throws Exception {

		Resource resource = docService.generateDocument(documentDTO);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value = "/document-cert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resource> generateDocumentCert(@RequestBody CertificateDTO certificateDTO) throws Exception {

		Resource resource = docService.generateDocumentCertificate(certificateDTO);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value = "/document-protocol", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resource> generateDocumentProtocol(@RequestBody ProtocolDTO protocolDTO) throws Exception {

		Resource resource = docService.generateDocumentProtocol(protocolDTO);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value = "/document-request/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resource> generateDocumentRequest(@PathVariable("deviceId") String deviceId)
			throws Exception {

		Resource resource = docService.generateDocumentRequest(deviceId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value = "/document/{docId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resource> downloadFile(@PathVariable("docId") Integer docId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Resource resource = docService.loadFileAsResource(docId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value = "/document", method = RequestMethod.GET)
	public ResponseEntity<?> getExpiredDocuments(@RequestParam("docStartDate") String startDate,
			@RequestParam("docEndDate") String endDate) throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localStartDate = LocalDate.parse(startDate, formatter);
		LocalDate localEndDate = LocalDate.parse(endDate, formatter);

		return new ResponseEntity<List<Document>>(docService.getExpiredDocuments(localStartDate, localEndDate),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/expired-document/{docId}", method = RequestMethod.GET)
	public ResponseEntity<?> getExpiredDocuments(@PathVariable("docId") Integer docId) throws Exception {
		return new ResponseEntity<String>(docService.removeExpiredDocument(docId), HttpStatus.OK);
	}

	@RequestMapping(value = "/document/{documentId}", method = RequestMethod.POST)
	public ResponseEntity<?> rewriteDocumetn(@RequestBody DocumentDTO documentDTO,
			@PathVariable("documentId") Integer documentId) throws Exception {
		Resource resource = docService.rewriteDocument(documentDTO, documentId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}

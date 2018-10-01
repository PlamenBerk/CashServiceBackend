package com.cashregister.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.cashregister.dto.DocumentDTO;
import com.cashregister.model.Document;
import com.cashregister.service.DocumentService;

@CrossOrigin()
@RestController
@RequestMapping("/document-management")
public class DocumentController {

	@Autowired
	private DocumentService docService;

	@RequestMapping(value = "/document", method = RequestMethod.POST)
	public ResponseEntity<?> generateDocument(@RequestBody DocumentDTO documentDTO) throws Exception {
		return new ResponseEntity<String>(docService.generateDocument(documentDTO), HttpStatus.OK);
	}

	@RequestMapping(value = "/document", method = RequestMethod.GET)
	public ResponseEntity<?> getExpiredDocuments(@RequestParam("docStartDate") String startDate,
			@RequestParam("docEndDate") String endDate) throws Exception {

		LocalDate localStartDate = LocalDate.parse(startDate);
		LocalDate localEndDate = LocalDate.parse(endDate);

		return new ResponseEntity<List<Document>>(docService.getExpiredDocuments(localStartDate, localEndDate),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/document/{docId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<Resource> downloadFile(@PathVariable("docId") Integer docId, HttpServletRequest request)
			throws Exception {

		// Load file as Resource
		Resource resource = docService.loadFileAsResource(docId);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			System.err.println("error");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "text/plain";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}

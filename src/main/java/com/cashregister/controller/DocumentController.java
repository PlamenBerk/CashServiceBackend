package com.cashregister.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

}

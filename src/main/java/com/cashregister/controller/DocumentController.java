package com.cashregister.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

		String date = "16/08/2016";

		LocalDate localDate = LocalDate.parse(date, formatter);
		return new ResponseEntity<String>("hahaha", HttpStatus.OK);
	}

}

package com.cashregister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.dto.DocumentDTO;
import com.cashregister.service.DocumentService;

@CrossOrigin()
@RestController
@RequestMapping("/document-management")
public class DocumentController {

	@Autowired
	private DocumentService docService;

	@RequestMapping(value = "/document/{deviceId}/{docType}", method = RequestMethod.POST)
	public ResponseEntity<?> generateDocument(@RequestBody DocumentDTO documentDTO,
			@PathVariable("deviceId") Integer deviceId, @PathVariable("docType") String docType) throws Exception {
		return new ResponseEntity<String>(docService.generateDocument(null, deviceId, docType), HttpStatus.OK);
	}

}

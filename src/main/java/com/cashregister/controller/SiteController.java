package com.cashregister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.service.SiteService;

@CrossOrigin
@RestController
@RequestMapping("/site-management")
public class SiteController {

	@Autowired
	private SiteService siteService;

	@RequestMapping(value = "/site", method = RequestMethod.GET)
	public ResponseEntity<?> getSitesByClietnId(@RequestParam("clientId") int clientId) throws Exception {
		return new ResponseEntity<>(siteService.getSitesByClientId(clientId), HttpStatus.OK);
	}

}

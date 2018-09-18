package com.cashregister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.dto.SiteDTO;
import com.cashregister.model.Site;
import com.cashregister.service.SiteService;

@CrossOrigin
@RestController
@RequestMapping("/site-management")
public class SiteController {

	@Autowired
	private SiteService siteService;

	@RequestMapping(value = "/site", method = RequestMethod.GET)
	public ResponseEntity<?> getSitesByClietnId(@RequestParam("clientId") int clientId) throws Exception {
		return new ResponseEntity<List<Site>>(siteService.getSitesByClientId(clientId), HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{clientId}", method = RequestMethod.POST)
	public ResponseEntity<?> addSiteForClient(@RequestBody SiteDTO siteDTO, @PathVariable("clientId") Integer clientId)
			throws Exception {
		return new ResponseEntity<Site>(siteService.newSiteForClient(siteDTO, clientId), HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<?> updateClient(@RequestBody SiteDTO siteDTO, @PathVariable("siteId") Integer siteId)
			throws Exception {
		return new ResponseEntity<Site>(siteService.updateSite(siteDTO, siteId), HttpStatus.OK);
	}

}

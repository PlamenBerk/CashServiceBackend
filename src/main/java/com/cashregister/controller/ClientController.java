package com.cashregister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.dto.ClientManagerWrapperDTO;
import com.cashregister.service.ClientService;

@RestController
@RequestMapping("/client-management")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/client", method = RequestMethod.POST)
	public ResponseEntity<?> addNewClient(@RequestBody ClientManagerWrapperDTO clientManagerWrapper) throws Exception {
		return new ResponseEntity<>(clientService.saveClient(clientManagerWrapper), HttpStatus.OK);
	}

}

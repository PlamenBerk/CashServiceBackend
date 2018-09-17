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
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.dto.ClientManagerWrapperDTO;
import com.cashregister.model.Client;
import com.cashregister.service.ClientService;

@CrossOrigin()
@RestController
@RequestMapping("/client-management")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/client", method = RequestMethod.POST)
	public ResponseEntity<?> addNewClient(@RequestBody ClientManagerWrapperDTO clientManagerWrapper) throws Exception {
		return new ResponseEntity<>(clientService.saveClient(clientManagerWrapper), HttpStatus.OK);
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public ResponseEntity<?> getAllClients() throws Exception {
		return new ResponseEntity<List<Client>>(clientService.getAllClients(), HttpStatus.OK);
	}

	@RequestMapping(value = "/client/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<?> updateClient(@RequestBody ClientManagerWrapperDTO clientManagerWrapper,
			@PathVariable("id") Integer id) throws Exception {
		return new ResponseEntity<Client>(clientService.updateClient(clientManagerWrapper, id), HttpStatus.OK);
	}

}

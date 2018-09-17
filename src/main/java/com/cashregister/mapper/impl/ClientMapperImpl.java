package com.cashregister.mapper.impl;

import org.springframework.stereotype.Component;

import com.cashregister.dto.ClientDTO;
import com.cashregister.mapper.ClientMapper;
import com.cashregister.model.Client;

@Component
public class ClientMapperImpl implements ClientMapper {

	@Override
	public Client clientDTOtoClient(ClientDTO clientDTO) {
		if (clientDTO == null) {
			return null;
		}

		Client client = new Client();
		client.setName(clientDTO.getName());
		client.setAddress(clientDTO.getAddress());
		client.setBulstat(clientDTO.getBulstat());
		client.setComment(clientDTO.getComment());
		client.setEGN(clientDTO.getEGN());
		client.setTDD(clientDTO.getTDD());

		return client;
	}
}

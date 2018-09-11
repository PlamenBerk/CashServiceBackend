package com.cashregister.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashregister.dto.ClientManagerWrapperDTO;
import com.cashregister.mapper.ClientMapper;
import com.cashregister.mapper.ManagerMapper;
import com.cashregister.model.Client;
import com.cashregister.model.Manager;

@Service
public class ClientService extends BaseService {

	@Autowired
	private ClientMapper clientMapper;

	@Autowired
	private ManagerMapper managerMapper;

	public ClientManagerWrapperDTO saveClient(ClientManagerWrapperDTO clientManagerWrapper) throws Exception {
		Client client = clientMapper.clientDTOtoClient(clientManagerWrapper.getClientDTO());
		Manager manager = managerMapper.managerDTOtoManager(clientManagerWrapper.getManagerDTO());
		client.setManager(manager);
		getEm().persist(client);
		return clientManagerWrapper;
	}

}

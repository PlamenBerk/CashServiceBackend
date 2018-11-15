package com.cashregister.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.cashregister.dto.ClientManagerWrapperDTO;
import com.cashregister.mapper.ClientMapper;
import com.cashregister.mapper.ManagerMapper;
import com.cashregister.model.Client;
import com.cashregister.model.Manager;

@Service
@Transactional
public class ClientService extends BaseService {

	@Autowired
	private ClientMapper clientMapper;

	@Autowired
	private ManagerMapper managerMapper;

	public Client saveClient(ClientManagerWrapperDTO clientManagerWrapper) throws Exception {
		Client client = clientMapper.clientDTOtoClient(clientManagerWrapper.getClientDTO());
		Manager manager = managerMapper.managerDTOtoManager(clientManagerWrapper.getManagerDTO());
		client.setManager(manager);
		getEm().persist(client);
		return client;
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public List<Client> getAllClients() throws Exception {
		return getEm().createNamedQuery("getAllClients", Client.class).setParameter("isAcive", Boolean.TRUE)
				.getResultList();
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Client deleteClient(Integer id) {
		Client client = getEm().find(Client.class, id);
		client.setActive(false);
		return client;
	}

	public Client updateClient(ClientManagerWrapperDTO clientManagerWrapper, Integer id) throws Exception {
		Client client = getEm().find(Client.class, id);
		client.setName(clientManagerWrapper.getClientDTO().getName());
		client.setAddress(clientManagerWrapper.getClientDTO().getAddress());
		client.setBulstat(clientManagerWrapper.getClientDTO().getBulstat());
		client.setComment(clientManagerWrapper.getClientDTO().getComment());
		client.setEGN(clientManagerWrapper.getClientDTO().getEGN());
		client.setTDD(clientManagerWrapper.getClientDTO().getTDD());

		Manager editedManager = getEm().find(Manager.class, client.getManager().getId());
		editedManager.setName(clientManagerWrapper.getManagerDTO().getName());
		editedManager.setPhone(clientManagerWrapper.getManagerDTO().getPhone());

		client.setManager(editedManager);
		return client;
	}

}

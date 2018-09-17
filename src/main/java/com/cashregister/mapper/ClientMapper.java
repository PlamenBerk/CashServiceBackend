package com.cashregister.mapper;

import com.cashregister.dto.ClientDTO;
import com.cashregister.model.Client;

public interface ClientMapper {
	Client clientDTOtoClient(ClientDTO clientDTO);
}

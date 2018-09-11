package com.cashregister.mapper;

import org.mapstruct.Mapper;

import com.cashregister.dto.ClientDTO;
import com.cashregister.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
	Client clientDTOtoClient(ClientDTO clientDTO);
}

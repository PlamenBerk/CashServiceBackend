package com.cashregister.mapper;

import org.mapstruct.Mapper;

import com.cashregister.dto.ManagerDTO;
import com.cashregister.model.Manager;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
	Manager managerDTOtoManager(ManagerDTO managerDTO);
}

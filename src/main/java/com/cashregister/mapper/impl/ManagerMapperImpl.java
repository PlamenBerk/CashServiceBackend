package com.cashregister.mapper.impl;

import org.springframework.stereotype.Component;

import com.cashregister.dto.ManagerDTO;
import com.cashregister.mapper.ManagerMapper;
import com.cashregister.model.Manager;

@Component
public class ManagerMapperImpl implements ManagerMapper {

	@Override
	public Manager managerDTOtoManager(ManagerDTO managerDTO) {
		if (managerDTO == null) {
			return null;
		}

		Manager manager = new Manager();
		manager.setName(managerDTO.getName());
		manager.setPhone(managerDTO.getPhone());

		return manager;
	}
}

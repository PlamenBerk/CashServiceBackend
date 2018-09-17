package com.cashregister.mapper;

import com.cashregister.dto.ManagerDTO;
import com.cashregister.model.Manager;

public interface ManagerMapper {
	Manager managerDTOtoManager(ManagerDTO managerDTO);
}

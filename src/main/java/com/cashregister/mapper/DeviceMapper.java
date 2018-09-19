package com.cashregister.mapper;

import com.cashregister.dto.DeviceDTO;
import com.cashregister.model.Device;

public interface DeviceMapper {
	Device deviceDTOtoDevice(DeviceDTO deviceDTO);
}

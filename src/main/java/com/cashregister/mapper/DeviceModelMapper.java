package com.cashregister.mapper;

import com.cashregister.dto.DeviceModelDTO;
import com.cashregister.model.DeviceModel;

public interface DeviceModelMapper {
	DeviceModel deviceDTOtoDevcie(DeviceModelDTO deviceModelDTO);
}

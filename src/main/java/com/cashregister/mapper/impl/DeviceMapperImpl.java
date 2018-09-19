package com.cashregister.mapper.impl;

import org.springframework.stereotype.Component;

import com.cashregister.dto.DeviceDTO;
import com.cashregister.mapper.DeviceMapper;
import com.cashregister.model.Device;

@Component
public class DeviceMapperImpl implements DeviceMapper {

	@Override
	public Device deviceDTOtoDevice(DeviceDTO deviceDTO) {
		if (deviceDTO == null) {
			return null;
		}

		Device device = new Device();
		device.setDeviceNumPostfix(deviceDTO.getDeviceNumPostfix());
		device.setFiscalNumPostfix(deviceDTO.getFiscalNumPostfix());
		device.setNapDate(deviceDTO.getNapDate());
		device.setNapNumber(deviceDTO.getNapNumber());
		device.setSIM(deviceDTO.getSIM());

		return device;
	}

}

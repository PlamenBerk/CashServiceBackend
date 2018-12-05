package com.cashregister.mapper.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		Device device = new Device();
		device.setDeviceNumPostfix(deviceDTO.getDeviceNumPostfix());
		device.setFiscalNumPostfix(deviceDTO.getFiscalNumPostfix());
		device.setNapDate(LocalDate.parse(deviceDTO.getNapDate(), formatter));
		device.setNapNumber(deviceDTO.getNapNumber());
		device.setSim(deviceDTO.getSim());
		device.setNapPhone(deviceDTO.getNapPhone());

		return device;
	}

}

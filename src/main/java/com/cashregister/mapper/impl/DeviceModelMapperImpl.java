package com.cashregister.mapper.impl;

import org.springframework.stereotype.Component;

import com.cashregister.dto.DeviceModelDTO;
import com.cashregister.mapper.DeviceModelMapper;
import com.cashregister.model.DeviceModel;

@Component
public class DeviceModelMapperImpl implements DeviceModelMapper {

	@Override
	public DeviceModel deviceDTOtoDevcie(DeviceModelDTO deviceModelDTO) {

		if (deviceModelDTO == null) {
			return null;
		}

		DeviceModel deviceModel = new DeviceModel();
		deviceModel.setCertificate(deviceModelDTO.getCertificate());
		deviceModel.setDeviceNumPrefix(deviceModelDTO.getDeviceNumPrefix());
		deviceModel.setFiscalNumPrefix(deviceModelDTO.getFiscalNumPrefix());
		deviceModel.setManufacturer(deviceModelDTO.getManufacturer());
		deviceModel.setModel(deviceModelDTO.getModel());

		return deviceModel;
	}

}

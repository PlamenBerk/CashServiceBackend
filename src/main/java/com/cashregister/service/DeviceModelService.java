package com.cashregister.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashregister.dto.DeviceModelDTO;
import com.cashregister.mapper.DeviceModelMapper;
import com.cashregister.model.DeviceModel;

@Service
@Transactional
public class DeviceModelService extends BaseService {

	@Autowired
	private DeviceModelMapper deviceModelMapper;

	public List<DeviceModel> getAllDeviceModels() {
		return getEm().createNamedQuery("getAllDeviceModels", DeviceModel.class).getResultList();
	}

	public DeviceModel addNewDevice(DeviceModelDTO deviceModelDTO) {
		DeviceModel deviceModel = deviceModelMapper.deviceDTOtoDevcie(deviceModelDTO);
		getEm().persist(deviceModel);
		return deviceModel;
	}

	public DeviceModel updateDeviceModel(DeviceModelDTO deviceModelDTO, Integer id) {
		DeviceModel deviceModel = getEm().find(DeviceModel.class, id);
		deviceModel.setCertificate(deviceModelDTO.getCertificate());
		deviceModel.setDeviceNumPrefix(deviceModelDTO.getDeviceNumPrefix());
		deviceModel.setFiscalNumPrefix(deviceModelDTO.getFiscalNumPrefix());
		deviceModel.setManufacturer(deviceModelDTO.getManufacturer());
		deviceModel.setModel(deviceModelDTO.getModel());
		deviceModel.setEik(deviceModelDTO.getEik());

		return deviceModel;
	}

}

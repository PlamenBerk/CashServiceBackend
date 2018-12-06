package com.cashregister.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.cashregister.dto.DeviceDTO;
import com.cashregister.dto.DevicePlusDeviceModelDTO;
import com.cashregister.mapper.DeviceMapper;
import com.cashregister.model.Device;
import com.cashregister.model.DeviceModel;
import com.cashregister.model.Site;

@Service
@Transactional
public class DeviceService extends BaseService {

	@Autowired
	private DeviceMapper deviceMapper;

	public DevicePlusDeviceModelDTO addNewDevice(DeviceDTO deviceDTO, int siteId, int deviceModelId) throws Exception {
		Device device = deviceMapper.deviceDTOtoDevice(deviceDTO);
		Site site = getEm().find(Site.class, siteId);
		DeviceModel deviceModel = getEm().find(DeviceModel.class, deviceModelId);

		DevicePlusDeviceModelDTO newDTO = new DevicePlusDeviceModelDTO();

		device.setSite(site);
		device.setDeviceModel(deviceModel);
		device.setIsNewFiscalNum(false);

		getEm().persist(device);

		// feature: new mapper for DevicePlusDeviceModelDTO
		newDTO.setId(device.getId());
		newDTO.setDeviceNumPostfix(device.getDeviceNumPostfix());
		newDTO.setFiscalNumPostfix(device.getFiscalNumPostfix());
		newDTO.setNapDate(device.getNapDate());
		newDTO.setNapNumber(device.getNapNumber());
		newDTO.setSimPhone(device.getSimPhone());
		newDTO.setSim(device.getSim());
		newDTO.setModelOfDevice(deviceModel.getModel());

		return newDTO;
	}

	public List<DevicePlusDeviceModelDTO> getDevicesForSite(int siteId) {
		// feature: change logic for faster performance!
		List<Device> deviceList = getEm().createNamedQuery("getDevicesForSite", Device.class)
				.setParameter("siteId", siteId).setParameter("pActive", Boolean.TRUE).getResultList();
		List<DevicePlusDeviceModelDTO> devicePlusModel = new ArrayList<>();
		for (Device device : deviceList) {
			DeviceModel deviceModel = getEm().find(DeviceModel.class, device.getDeviceModel().getId());
			DevicePlusDeviceModelDTO dto = new DevicePlusDeviceModelDTO();
			dto.setId(device.getId());
			dto.setDeviceNumPostfix(device.getDeviceNumPostfix());
			dto.setFiscalNumPostfix(device.getFiscalNumPostfix());
			dto.setNapDate(device.getNapDate());
			dto.setNapNumber(device.getNapNumber());
			dto.setSim(device.getSim());
			dto.setSimPhone(device.getSimPhone());
			dto.setModelOfDevice(deviceModel.getModel());
			deviceModel = null;

			devicePlusModel.add(dto);
		}
		return devicePlusModel;

	}

	public DevicePlusDeviceModelDTO updateDevice(DeviceDTO deviceDTO, Integer deviceId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		Device device = getEm().find(Device.class, deviceId);
		DeviceModel deviceModel = getEm().find(DeviceModel.class, device.getDeviceModel().getId());
		device.setDeviceNumPostfix(deviceDTO.getDeviceNumPostfix());

		if (!(deviceDTO.getFiscalNumPostfix().equals(device.getFiscalNumPostfix()))) {
			device.setIsNewFiscalNum(true);
		}

		device.setFiscalNumPostfix(deviceDTO.getFiscalNumPostfix());
		device.setNapDate(LocalDate.parse(deviceDTO.getNapDate(), formatter));
		device.setNapNumber(deviceDTO.getNapNumber());
		device.setSim(deviceDTO.getSim());
		device.setSimPhone(deviceDTO.getSimPhone());

		DevicePlusDeviceModelDTO dto = new DevicePlusDeviceModelDTO();
		dto.setId(device.getId());
		dto.setDeviceNumPostfix(device.getDeviceNumPostfix());
		dto.setFiscalNumPostfix(device.getFiscalNumPostfix());
		dto.setNapDate(device.getNapDate());
		dto.setNapNumber(device.getNapNumber());
		dto.setSim(device.getSim());
		dto.setSimPhone(device.getSimPhone());
		dto.setModelOfDevice(deviceModel.getModel());

		return dto;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Device deleteDevice(Integer deviceId) {
		Device device = getEm().find(Device.class, deviceId);
		device.setActive(false);
		return device;
	}

}

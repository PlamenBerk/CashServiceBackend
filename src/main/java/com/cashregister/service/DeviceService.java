package com.cashregister.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.cashregister.dto.DeviceDTO;
import com.cashregister.mapper.DeviceMapper;
import com.cashregister.model.Device;
import com.cashregister.model.DeviceModel;
import com.cashregister.model.Site;

@Service
@Transactional
public class DeviceService extends BaseService {

	@Autowired
	private DeviceMapper deviceMapper;

	public Device addNewDevice(DeviceDTO deviceDTO, int siteId, int deviceModelId) throws Exception {
		Device device = deviceMapper.deviceDTOtoDevice(deviceDTO);
		Site site = getEm().find(Site.class, siteId);
		DeviceModel deviceModel = getEm().find(DeviceModel.class, deviceModelId);

		device.setSite(site);
		device.setDeviceModel(deviceModel);
		device.setIsNewFiscalNum(false);

		getEm().persist(device);

		return device;
	}

	public List<Device> getDevicesForSite(int siteId) {
		return getEm().createNamedQuery("getDevicesForSite", Device.class).setParameter("pActive", Boolean.TRUE)
				.setParameter("siteId", siteId).getResultList();
	}

	public Device updateDevice(DeviceDTO deviceDTO, Integer deviceId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		Device device = getEm().find(Device.class, deviceId);
		device.setDeviceNumPostfix(deviceDTO.getDeviceNumPostfix());

		if (!(deviceDTO.getFiscalNumPostfix().equals(device.getFiscalNumPostfix()))) {
			device.setIsNewFiscalNum(true);
		}

		device.setFiscalNumPostfix(deviceDTO.getFiscalNumPostfix());
		device.setNapDate(LocalDate.parse(deviceDTO.getNapDate(), formatter));
		device.setNapNumber(deviceDTO.getNapNumber());
		device.setSim(deviceDTO.getSim());

		return device;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Device deleteDevice(Integer deviceId) {
		Device device = getEm().find(Device.class, deviceId);
		device.setActive(false);
		return device;
	}

}

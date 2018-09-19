package com.cashregister.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

		getEm().persist(device);

		return device;
	}

}

package com.cashregister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.dto.DeviceDTO;
import com.cashregister.model.Device;
import com.cashregister.service.DeviceService;

@CrossOrigin()
@RestController
@RequestMapping("/device-management")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = "/device/{siteId}/{deviceModelId}", method = RequestMethod.POST)
	public ResponseEntity<?> addNewDevice(@RequestBody DeviceDTO deviceDTO, @PathVariable("siteId") Integer siteId,
			@PathVariable("deviceModelId") Integer deviceModelId) throws Exception {
		return new ResponseEntity<Device>(deviceService.addNewDevice(deviceDTO, siteId, deviceModelId), HttpStatus.OK);
	}

}

package com.cashregister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashregister.dto.DeviceModelDTO;
import com.cashregister.model.DeviceModel;
import com.cashregister.service.DeviceModelService;

@CrossOrigin()
@RestController
@RequestMapping("/device-model-management")
public class DeviceModelController {

	@Autowired
	private DeviceModelService deviceModelService;

	@RequestMapping(value = "/device-model", method = RequestMethod.GET)
	public ResponseEntity<?> getAllDeviceModels() throws Exception {
		return new ResponseEntity<List<DeviceModel>>(deviceModelService.getAllDeviceModels(), HttpStatus.OK);
	}

	@RequestMapping(value = "/device-model", method = RequestMethod.POST)
	public ResponseEntity<?> addNewDeviceModels(@RequestBody DeviceModelDTO deviceModelDTO) throws Exception {
		return new ResponseEntity<DeviceModel>(deviceModelService.addNewDevice(deviceModelDTO), HttpStatus.OK);
	}

	@RequestMapping(value = "/device-model/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<?> updateClient(@RequestBody DeviceModelDTO deviceModelDTO, @PathVariable("id") Integer id)
			throws Exception {
		return new ResponseEntity<DeviceModel>(deviceModelService.updateDeviceModel(deviceModelDTO, id), HttpStatus.OK);
	}

}

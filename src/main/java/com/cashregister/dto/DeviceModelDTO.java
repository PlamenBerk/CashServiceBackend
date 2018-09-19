package com.cashregister.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class DeviceModelDTO {

	private String manufacturer;
	private String model;
	private String certificate;
	private String deviceNumPrefix;
	private String fiscalNumPrefix;

}

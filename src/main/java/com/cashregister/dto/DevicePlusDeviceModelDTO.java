package com.cashregister.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class DevicePlusDeviceModelDTO {

	private Integer id;
	private String sim;
	private String deviceNumPostfix;
	private String fiscalNumPostfix;
	private String napNumber;
	private LocalDate napDate;
	private String simPhone;
	private String modelOfDevice;

}

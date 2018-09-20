package com.cashregister.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class DeviceDTO {

	private String sim;
	private String deviceNumPostfix;
	private String fiscalNumPostfix;
	private String napNumber;
	private LocalDate napDate;

}

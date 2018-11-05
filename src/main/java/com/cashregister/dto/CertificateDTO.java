package com.cashregister.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class CertificateDTO {

	private String deviceId;
	private String fromDateStr;
	private String certNumber;

}

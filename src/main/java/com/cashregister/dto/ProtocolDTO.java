package com.cashregister.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class ProtocolDTO {

	private String deviceId;
	private String reason;
	private String price;
	private String aprice;
	private String bprice;
	private String vprice;
	private String gprice;

}

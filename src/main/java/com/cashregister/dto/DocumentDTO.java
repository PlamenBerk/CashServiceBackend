package com.cashregister.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class DocumentDTO {

	private String deviceId;
	private String docType;
	private int selectedValueValidy;
	private String contractNumber;
	private String fromDate;
	private String price;

}

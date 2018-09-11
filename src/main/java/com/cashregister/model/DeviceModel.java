package com.cashregister.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "device_model")
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class DeviceModel extends BaseModel {

	@Column
	private String manufacturer;

	@Column
	private String model;

	@Column
	private String certificate;

	@Column
	private String deviceNumPrefix;

	@Column
	private String fiscalNumPrefix;

}

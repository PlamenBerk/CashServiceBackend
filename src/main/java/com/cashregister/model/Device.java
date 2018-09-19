package com.cashregister.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Device extends BaseModel {

	@Column
	private String SIM;

	@Column
	private String deviceNumPostfix;

	@Column
	private String fiscalNumPostfix;

	@Column
	private String napNumber;

	@Column
	private LocalDate napDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id")
	private Site site;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "device_model_id")
	private DeviceModel deviceModel;

}

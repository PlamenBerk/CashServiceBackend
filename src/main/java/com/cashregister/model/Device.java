package com.cashregister.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@NamedQueries({ // nl
		@NamedQuery(name = "getDevicesForSite", query = "SELECT d FROM Device d WHERE d.active = :pActive AND d.site.id = :siteId") // nl
})
public class Device extends BaseModel {

	@Column
	private String sim;

	@Column
	private String deviceNumPostfix;

	@Column
	private String fiscalNumPostfix;

	@Column
	private String napNumber;

	@Column
	private LocalDate napDate;

	@Column
	private String simPhone;

	@Column
	private LocalDate dateOfUsage;

	@Column
	private Boolean isNewFiscalNum;

	@Column
	private Boolean active = true;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id")
	private Site site;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "device_model_id")
	private DeviceModel deviceModel;

}

package com.cashregister.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@NamedQueries({ // nl
		@NamedQuery(name = "getAllDeviceModels", query = "SELECT dm FROM DeviceModel dm") // nl
})
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

	@Column
	private String eik;

}

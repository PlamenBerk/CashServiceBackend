package com.cashregister.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@NamedQueries({ // nl
		@NamedQuery(name = "getExpiredBetweenDates", query = "SELECT d FROM Document d WHERE d.isRewrited = FALSE AND d.endDate between :startDate AND :endDate"),
		@NamedQuery(name = "getDocumentByDeviceId", query = "SELECT d FROM Document d WHERE d.device.id = :pDeviceId ORDER BY id DESC"), // nl
		@NamedQuery(name = "getContractWithMaxID", query = "SELECT d FROM Document d WHERE d.device.id = :pDeviceId ORDER BY id DESC")// nl
})
public class Document extends BaseModel {

	@Column
	private String documentName;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private String docPath;

	@Column
	private String docNumber;

	@Column(columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isRewrited;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "device_id")
	private Device device;

}

package com.cashregister.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Document extends BaseModel {

	@Column
	private String documentName;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

}

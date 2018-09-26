package com.cashregister.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Document extends BaseModel {

	@Column
	private String documentName;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private String docPath;

}

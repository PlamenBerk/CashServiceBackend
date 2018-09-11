package com.cashregister.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "client")
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Client extends BaseModel {

	@Column
	private String EGN;

	@Column
	private String name;

	@Column
	private String bulstat;

	@Column
	private String address;

	@Column
	private String TDD;

	@Column
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private Manager manager;

}

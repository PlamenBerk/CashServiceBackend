package com.cashregister.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "manager")
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Manager extends BaseModel {

	@Column
	private String name;

	@Column
	private String phone;

}

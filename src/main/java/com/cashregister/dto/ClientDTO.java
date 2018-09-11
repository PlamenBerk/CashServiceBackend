package com.cashregister.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class ClientDTO {
	private String EGN;
	private String name;
	private String bulstat;
	private String address;
	private String TDD;
	private String comment;
}

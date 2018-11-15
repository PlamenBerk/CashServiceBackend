package com.cashregister.model;

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
		@NamedQuery(name = "getAllClients", query = "SELECT c FROM Client c WHERE c.active = :isAcive") // nl
})
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

	@Column
	private Boolean active = true;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "manager_id")
	private Manager manager;

}

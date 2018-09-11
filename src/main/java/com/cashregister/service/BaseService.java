package com.cashregister.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseService {

	@PersistenceContext
	protected EntityManager em;

	public EntityManager getEm() {
		return em;
	}

}

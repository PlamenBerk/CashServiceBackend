package com.cashregister.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cashregister.model.Site;

@Service
public class SiteService extends BaseService {

	public List<Site> getSitesByClientId(int clientId) {
		return getEm().createNamedQuery("getSiteByClientId", Site.class).setParameter("clientId", clientId)
				.getResultList();
	}

}

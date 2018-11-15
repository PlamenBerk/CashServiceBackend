package com.cashregister.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.cashregister.dto.SiteDTO;
import com.cashregister.mapper.SiteMapper;
import com.cashregister.model.Client;
import com.cashregister.model.Site;

@Service
@Transactional
public class SiteService extends BaseService {

	@Autowired
	private SiteMapper siteMapper;

	public List<Site> getSitesByClientId(int clientId) throws Exception {
		return getEm().createNamedQuery("getSiteByClientId", Site.class).setParameter("pActive", Boolean.TRUE)
				.setParameter("clientId", clientId).getResultList();
	}

	public Site newSiteForClient(SiteDTO siteDTO, Integer clientId) throws Exception {
		Site site = siteMapper.siteDTOtoSite(siteDTO);
		Client client = getEm().find(Client.class, clientId);
		site.setClient(client);
		getEm().persist(site);
		return site;
	}

	public Site updateSite(SiteDTO siteDTO, Integer siteId) throws Exception {
		Site site = getEm().find(Site.class, siteId);
		site.setName(siteDTO.getName());
		site.setAddress(siteDTO.getAddress());
		site.setPhone(siteDTO.getPhone());
		return site;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Site deleteSite(Integer siteId) {
		Site site = getEm().find(Site.class, siteId);
		site.setActive(false);
		return site;
	}

}

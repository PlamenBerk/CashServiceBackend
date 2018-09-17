package com.cashregister.mapper.impl;

import org.springframework.stereotype.Component;

import com.cashregister.dto.SiteDTO;
import com.cashregister.mapper.SiteMapper;
import com.cashregister.model.Site;

@Component
public class SiteMapperImpl implements SiteMapper {

	@Override
	public Site siteDTOtoSite(SiteDTO siteDTO) {
		if (siteDTO == null) {
			return null;
		}

		Site site = new Site();
		site.setAddress(siteDTO.getAddress());
		site.setName(siteDTO.getName());
		site.setPhone(siteDTO.getPhone());

		return site;
	}
}

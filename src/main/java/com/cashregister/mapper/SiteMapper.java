package com.cashregister.mapper;

import com.cashregister.dto.SiteDTO;
import com.cashregister.model.Site;

public interface SiteMapper {
	Site siteDTOtoSite(SiteDTO siteDTO);
}

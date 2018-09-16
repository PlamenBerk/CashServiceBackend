package com.cashregister.mapper;

import org.mapstruct.Mapper;

import com.cashregister.dto.SiteDTO;
import com.cashregister.model.Site;

@Mapper(componentModel = "spring")
public interface SiteMapper {
	Site siteDTOtoSite(SiteDTO siteDTO);
}

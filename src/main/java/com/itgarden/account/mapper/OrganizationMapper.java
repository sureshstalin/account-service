package com.itgarden.account.mapper;

import com.itgarden.account.dto.OrganizationInfo;
import com.itgarden.account.entity.Organization;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface OrganizationMapper {

    OrganizationMapper INSTANCE =  Mappers.getMapper(OrganizationMapper.class);
    Organization organizationInfoToOrganization(OrganizationInfo organizationInfo);
    @InheritInverseConfiguration
    OrganizationInfo organizationToOrganizationInfo(Organization organization);
}

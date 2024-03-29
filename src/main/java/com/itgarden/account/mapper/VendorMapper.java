package com.itgarden.account.mapper;

import com.itgarden.account.dto.VendorInfo;
import com.itgarden.account.entity.Vendor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 18 / Oct / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);
    Vendor vendorInfoToVendor(VendorInfo vendorInfo);
    @InheritInverseConfiguration
    VendorInfo vendorToVendorInfo(Vendor vendor);


}

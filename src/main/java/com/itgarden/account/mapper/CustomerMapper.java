package com.itgarden.account.mapper;



/*
 * Created by Suresh Stalin on 18 / Oct / 2020.
 */

import com.itgarden.account.dto.CustomerInfo;
import com.itgarden.account.entity.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(implementationPackage = "mapper.impl")
public interface CustomerMapper {

    CustomerMapper INSTANCE =  Mappers.getMapper(CustomerMapper.class);
    Customer customerInfoToCustomer(CustomerInfo customerInfo);
    @InheritInverseConfiguration
    CustomerInfo customerToCustomerInfo(Customer customer);
}

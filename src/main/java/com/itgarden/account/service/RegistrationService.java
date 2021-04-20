package com.itgarden.account.service;

import com.itgarden.account.common.staticdata.UserType;
import com.itgarden.account.dto.BaseInfo;
import com.itgarden.account.dto.CustomerInfo;
import com.itgarden.account.dto.EmployeeInfo;
import com.itgarden.account.dto.VendorInfo;
import com.itgarden.account.exception.InvalidInputException;
import com.itgarden.account.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Created by Suresh Stalin on 20 / Oct / 2020.
 */

@Service
public class RegistrationService {

    @Autowired
    private final VendorService vendorService;

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final EmployeeService employeeService;

    public RegistrationService(VendorService vendorService, CustomerService customerService,
                               EmployeeService employeeService) {
        this.vendorService = vendorService;
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    public ResponseMessage<BaseInfo> doRegistration(BaseInfo baseInfo) throws Exception {

        ResponseMessage<BaseInfo> responseMessage = null;
        String type = baseInfo.getType();
        try {
            if (type.equalsIgnoreCase(UserType.EMPLOYEE.name())) {
                responseMessage = employeeService.save((EmployeeInfo)baseInfo);
            } else if (type.equalsIgnoreCase(UserType.CUSTOMER.name())) {
                responseMessage = customerService.save((CustomerInfo)baseInfo);
            } else if (type.equalsIgnoreCase(UserType.VENDOR.name())) {
                responseMessage = vendorService.save((VendorInfo)baseInfo);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(String.format("Invalid user type %s ", type));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return responseMessage;
    }
}


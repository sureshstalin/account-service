package com.itgarden.account.service;


import com.itgarden.account.common.CodeGenerator;
import com.itgarden.account.common.staticdata.CodeType;
import com.itgarden.account.common.staticdata.ROLES;
import com.itgarden.account.common.staticdata.UserType;
import com.itgarden.account.dto.CustomerInfo;
import com.itgarden.account.entity.Customer;
import com.itgarden.account.entity.Role;
import com.itgarden.account.exception.ResourceNotFoundException;
import com.itgarden.account.mapper.CustomerMapper;
import com.itgarden.account.messages.ResponseMessage;
import com.itgarden.account.repository.CustomerRepository;
import com.itgarden.account.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Suresh Stalin on 02 / Nov / 2020.
 */

@Service
public class CustomerService extends BaseService<CustomerInfo> {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final CodeGenerator codeGenerator;

    @Autowired
    CustomerService(CustomerRepository customerRepository, RoleRepository roleRepository, CodeGenerator codeGenerator) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public ResponseMessage findResourceByCode(String code) throws Exception {
        Customer customer = customerRepository.findByCustomerCode(code);
        CustomerInfo customerDTO = CustomerMapper.INSTANCE.customerToCustomerInfo(customer);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(customerDTO, "", "");
        return responseMessage;
    }

    @Override
    public ResponseMessage save(CustomerInfo customerInfo) {
        ResponseMessage responseMessage = null;
        try {
            Customer customer = CustomerMapper.INSTANCE.customerInfoToCustomer(customerInfo);
            Role role = roleRepository.findByName(ROLES.CUSTOMER_ROLE.name()).orElse(null);
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            customer.getUser().setRoles(roleList);
            if (customer.getId() == null) {
                customer.setCustomerCode(codeGenerator.newCode(CodeType.CUSTOMER_CODE));
            }
            if (customer.getUser().getAddressList() != null && customer.getUser().getAddressList().size() > 0) {
                customer.getUser().getAddressList().get(0).setUser(customer.getUser());
            }
            customer.getUser().setUserType(UserType.CUSTOMER.name());
            customer.getUser().setPassword(customer.getUser().getMobileNo());
            Customer newCustomer = customerRepository.save(customer);
            CustomerInfo customerDto = CustomerMapper.INSTANCE.customerToCustomerInfo(newCustomer);
            responseMessage = ResponseMessage.withResponseData(customerDto,
                    "Customer Created Successfully", "message");
//        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error while saving customer info " + e.getMessage());
            e.printStackTrace();
        }
        return responseMessage;
    }

    @Transactional
    @Override
    public ResponseMessage findResourceById(Long id) throws Exception {
        Customer customer = customerRepository.findById(id).orElse(null);
        ResponseMessage responseMessage = new ResponseMessage();
        if (customer != null) {
            CustomerInfo customerDTO = CustomerMapper.INSTANCE.customerToCustomerInfo(customer);
            responseMessage.setResponseClassType(customerDTO);
        } else {
            throw new ResourceNotFoundException("Customer not found");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        return null;
    }
}

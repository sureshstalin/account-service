package com.itgarden.account.service;


import com.itgarden.account.common.CodeGenerator;
import com.itgarden.account.common.staticdata.CodeType;
import com.itgarden.account.common.staticdata.ROLES;
import com.itgarden.account.common.staticdata.UserType;
import com.itgarden.account.dto.VendorInfo;
import com.itgarden.account.entity.BaseObject;
import com.itgarden.account.entity.Role;
import com.itgarden.account.entity.Vendor;
import com.itgarden.account.exception.ResourceNotFoundException;
import com.itgarden.account.mapper.VendorMapper;
import com.itgarden.account.messages.ResponseMessage;
import com.itgarden.account.repository.RoleRepository;
import com.itgarden.account.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
/*
 * Created by Suresh Stalin on 02 / Nov / 2020.
 */


@Service
public class VendorService extends BaseService<VendorInfo> {

    private final VendorRepository vendorRepository;
    private final RoleRepository roleRepository;
    private final CodeGenerator codeGenerator;

    @Autowired
    VendorService(VendorRepository vendorRepository, RoleRepository roleRepository, CodeGenerator codeGenerator) {
        this.vendorRepository = vendorRepository;
        this.roleRepository = roleRepository;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public ResponseMessage save(VendorInfo vendorInfo) throws Exception {
        Vendor vendor = VendorMapper.INSTANCE.vendorInfoToVendor(vendorInfo);
        Role role = roleRepository.findByName(ROLES.VENDOR_ROLE.name()).orElse(null);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        vendor.getUser().setRoles(roleList);
        if (vendor.getId() == null) {
            vendor.setVendorCode(codeGenerator.newCode(CodeType.VENDOR_CODE));
        }
        vendor.getUser().setPassword(vendor.getUser().getPassword());
        vendor.getUser().getAddressList().get(0).setUser(vendor.getUser());
        vendor.getUser().setUserType(UserType.VENDOR.name());
        BaseObject newObject = vendorRepository.save(vendor);
        VendorInfo newVendorInfo = VendorMapper.INSTANCE.vendorToVendorInfo((Vendor) newObject);
        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(newVendorInfo, "Vendor Created Successfully", "message");
        return responseMessage;
    }

    @Transactional
    @Override
    public ResponseMessage findResourceById(Long id) throws Exception {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        ResponseMessage responseMessage = new ResponseMessage();
        if (vendor != null) {
            VendorInfo vendorInfo = VendorMapper.INSTANCE.vendorToVendorInfo(vendor);
            responseMessage.setResponseClassType(vendorInfo);
        } else {
            throw new ResourceNotFoundException("Vendor is not found");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage findResourceByCode(String code) throws Exception {
        Vendor vendor = vendorRepository.findByVendorCode(code);
        ResponseMessage responseMessage = new ResponseMessage();
        if (vendor != null) {
            VendorInfo vendorInfo = VendorMapper.INSTANCE.vendorToVendorInfo(vendor);
            responseMessage.setResponseClassType(vendorInfo);
        } else {
            throw new ResourceNotFoundException("Vendor is not found");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        return null;
    }


}

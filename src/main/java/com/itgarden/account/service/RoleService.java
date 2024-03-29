package com.itgarden.account.service;

import com.itgarden.account.common.staticdata.Constants;
import com.itgarden.account.dto.UserRoleInfo;
import com.itgarden.account.entity.Role;
import com.itgarden.account.messages.ResponseMessage;
import com.itgarden.account.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
 * Created by Suresh Stalin on 23 / Nov / 2020.
 */

@Service
public class RoleService extends BaseService {

    @Autowired
    private RoleRepository roleRepository;
    @PersistenceContext
    EntityManager em;

    @Override
    public ResponseMessage findResourceById(Long id) throws Exception {
        Object[] objects = roleRepository.getSuperAdmin(id);
        UserRoleInfo userRoleInfo = null;
        if (objects.length > 0) {
            userRoleInfo = new UserRoleInfo();
            Object object[] = (Object[]) objects[0];
            userRoleInfo.setUserId(Long.parseLong(object[0].toString()));
            userRoleInfo.setRoleId(Long.parseLong(object[1].toString()));
        }
        return ResponseMessage.withResponseData(userRoleInfo, Constants.SUCCESS_STATUS, Constants.INFO_TYPE);
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        return null;
    }

    @Override
    public ResponseMessage findResourceByCode(String code) throws Exception {
        return null;
    }

    public Role findByName(String roleName) {
       return roleRepository.findByName(roleName).orElse(null);
    }

    @Override
    public ResponseMessage save(Object baseInfo) throws Exception {
        return null;
    }
}

package com.itgarden.account.mapper;



/*
 * Created by Suresh Stalin on 17 / Oct / 2020.
 */

import com.itgarden.account.dto.UserInfo;
import com.itgarden.account.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(implementationPackage = "mapper.impl")
public interface UserMapper {

    UserMapper INSTANCE =  Mappers.getMapper(UserMapper.class);
    User userInfoToUser(UserInfo userInfo);
    @InheritInverseConfiguration
    UserInfo userToUserInfo(User user);

}

package com.itgarden.account.service;



/*
 * Created by Suresh Stalin on 10 / Nov / 2020.
 */

import com.itgarden.account.messages.ResponseMessage;

abstract public class BaseService<T>{

    public abstract ResponseMessage findResourceById(Long id) throws Exception;
    public abstract ResponseMessage findResourceByCode(String code) throws Exception;
    public abstract ResponseMessage findAll() throws Exception;
    public abstract ResponseMessage save(T t) throws Exception;

//    public UserDetails getContext() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return userDetails;
//    }
}

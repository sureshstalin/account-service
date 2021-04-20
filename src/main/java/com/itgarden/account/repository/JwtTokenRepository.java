package com.itgarden.account.repository;

import com.itgarden.account.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 10 / Nov / 2020.
 */

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    JwtToken findByUserName(String userName);
}

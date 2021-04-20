package com.itgarden.account.repository;

import com.itgarden.account.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findOrganizationByorgCode(String orgCode);
}

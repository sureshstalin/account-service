package com.itgarden.account.repository;

import com.itgarden.account.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 02 / Nov / 2020.
 */

public interface VendorRepository extends JpaRepository<Vendor, Long>  {

        Vendor findByVendorCode(String vendorCode);
}

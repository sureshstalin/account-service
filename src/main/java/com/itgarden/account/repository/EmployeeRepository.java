package com.itgarden.account.repository;

import com.itgarden.account.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 20 / Oct / 2020.
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmployeeCode(String employeeCode);
}

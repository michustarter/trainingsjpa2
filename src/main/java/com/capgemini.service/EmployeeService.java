package com.capgemini.service;

import com.capgemini.types.EmployeeTO;

public interface EmployeeService {

    EmployeeTO addEmployee(EmployeeTO employeeTO);

    void deleteEmployee(EmployeeTO employeeTO);
}





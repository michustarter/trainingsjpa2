package com.capgemini.service;

import com.capgemini.types.EmployeeTO;
import com.capgemini.exceptions.EmployeeAlreadyExistsException;
import com.capgemini.exceptions.NullPersonException;

public interface EmployeeService {

    EmployeeTO addEmployee(EmployeeTO employeeTO) throws NullPersonException, EmployeeAlreadyExistsException;

    void deleteEmployee(EmployeeTO employeeTO) throws NullPersonException;

    EmployeeTO updateEmployee(EmployeeTO employeeTO) throws NullPersonException;

    EmployeeTO findEmployee(Long id);
}





package com.capgemini.service;

import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;

public interface StudentService {

    StudentTO addStudent(EmployeeTO employeeTO, StudentTO studentTO);

    void deleteStudent(EmployeeTO employeeTO, StudentTO studentTO);
}

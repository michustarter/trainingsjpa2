package com.capgemini.service.impl;

import com.capgemini.service.StudentService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public StudentTO addStudent(EmployeeTO employeeTO, StudentTO studentTO) {
        return null;
    }

    @Override
    public void deleteStudent(EmployeeTO employeeTO, StudentTO studentTO) {

    }
}

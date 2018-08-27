package com.capgemini.service;

import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.exceptions.BadGradeRangeException;
import com.capgemini.exceptions.NullPersonException;
import com.capgemini.exceptions.StudentAlreadyExistsException;

public interface StudentService {

    StudentTO addStudent(EmployeeTO employeeTO, EmployeeTO boss, int grade) throws NullPersonException,
            BadGradeRangeException, StudentAlreadyExistsException;

    void deleteStudent(StudentTO studentTO) throws NullPersonException;

    StudentTO updateStudent(StudentTO studentTO) throws NullPersonException;

    StudentTO findStudent (Long id);
}

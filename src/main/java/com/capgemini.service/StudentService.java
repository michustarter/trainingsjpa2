package com.capgemini.service;

import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.util.BadGradeRangeException;
import com.capgemini.util.NullBossException;
import com.capgemini.util.NullPersonException;

public interface StudentService {

    StudentTO addStudent(EmployeeTO employeeTO, EmployeeTO boss, int grade) throws NullPersonException, NullBossException, BadGradeRangeException;

    void deleteStudent(StudentTO studentTO) throws NullPersonException;

    StudentTO updateStudent(StudentTO studentTO) throws NullPersonException;
}

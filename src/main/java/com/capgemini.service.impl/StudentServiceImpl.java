package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.service.StudentService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.util.BadGradeRangeException;
import com.capgemini.util.NullBossException;
import com.capgemini.util.NullPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private EmployeeDao employeeDao;
    private StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(EmployeeDao employeeDao, StudentDao studentDao) {
        this.employeeDao = employeeDao;
        this.studentDao = studentDao;
    }

    @Override
    public StudentTO addStudent(EmployeeTO employeeTO, EmployeeTO bossTO, int grade) throws NullPersonException, NullBossException, BadGradeRangeException {
        if (employeeTO == null || employeeTO.getId() == null || !employeeDao.findById(employeeTO.getId()).isPresent()) {
            throw new NullPersonException("Cannot create student if employee does not exist in database!");
        }
        if (bossTO == null || bossTO.getId() == null || !employeeDao.findById(bossTO.getId()).isPresent()) {
            throw new NullBossException("Cannot create student if boss does not exist in database!");
        }
        if (grade < 1 || grade > 5) {
            throw new BadGradeRangeException("Cannot set student's grade out of range 1-5!");
        }
        EmployeeEntity bossEntity = EmployeeMapper.toEntity(bossTO);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setFirstName(employeeTO.getFirstName());
        studentEntity.setLastName(employeeTO.getLastName());
        studentEntity.setPosition(employeeTO.getPosition());
        studentEntity.setGrade(grade);
        studentEntity.setBoss(bossEntity);

        studentEntity = studentDao.save(studentEntity);

        StudentTO studentTO = StudentMapper.toTO(studentEntity);

        return studentTO;
    }


    @Override
    public void deleteStudent(StudentTO studentTO) throws NullPersonException {
        if (studentTO == null || studentTO.getId() == null || !studentDao.findById(studentTO.getId()).isPresent()) {
            throw new NullPersonException("Cannot delete non-existent student!");
        }
        List<EmployeeEntity> employees = employeeDao.findAll();
        EmployeeEntity employeeEntity = employees.stream()
                .filter(e -> e.getStudent().getId() == studentTO.getId())
                .collect(Collectors.toList())
                .get(0);
        employeeEntity.setStudent(null);
        employeeDao.save(employeeEntity);

        studentDao.delete(StudentMapper.toEntity(studentTO));
    }

    @Override
    public StudentTO updateStudent(StudentTO studentTO) throws NullPersonException {
        if (studentTO == null) { //nie spr czy studentTO istnieje w bazie danych, jesli nie istineje to go po prostu dodam
            throw new NullPersonException("Cannot update student with empty data!");
        }
        StudentEntity studentEntity = StudentMapper.toEntity(studentTO);
        studentEntity = studentDao.save(studentEntity);
        studentTO = StudentMapper.toTO(studentEntity);


        List<EmployeeEntity> employees = employeeDao.findAll();
        StudentTO finalStudentTO = studentTO;
        EmployeeEntity employeeEntity = employees.stream()
                .filter(e -> e.getStudent().getId() == finalStudentTO.getId())
                .collect(Collectors.toList())
                .get(0);
        employeeEntity.setStudent(studentEntity);
        employeeDao.save(employeeEntity);

        return studentTO;
    }
}
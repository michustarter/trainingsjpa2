package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.service.EmployeeService;
import com.capgemini.service.StudentService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.exceptions.BadGradeRangeException;
import com.capgemini.exceptions.NullPersonException;
import com.capgemini.exceptions.StudentAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private EmployeeDao employeeDao;
    private StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(EmployeeDao employeeDao, StudentDao studentDao) {
        this.employeeDao = employeeDao;
        this.studentDao = studentDao;
    }

    @Override
    @Transactional(readOnly = false)
    public StudentTO addStudent(EmployeeTO employeeTO, EmployeeTO bossTO, int grade) throws NullPersonException,
            BadGradeRangeException, StudentAlreadyExistsException {

        if (employeeTO == null || employeeTO.getId() == null/* || !employeeDao.findById(employeeTO.getId()).isPresent()*/) {
            throw new NullPersonException("Cannot create student if employee does not exist in database!");
        }
        if (employeeTO.getStudentId() != null) {
            throw new StudentAlreadyExistsException("This student already exists in database!");
        }
        if (bossTO == null || bossTO.getId() == null /*|| !employeeDao.findById(bossTO.getId()).isPresent()*/) {
            throw new NullPersonException("Cannot create student if boss does not exist in database!");
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

        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeEntity.setStudent(studentEntity);
        employeeDao.save(employeeEntity);

        //to musi być !
        employeeTO=EmployeeMapper.toTO(employeeEntity);


        StudentTO studentTO = StudentMapper.toTO(studentEntity);

        return studentTO;
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteStudent(StudentTO studentTO) throws NullPersonException {
        if (studentTO == null || studentTO.getId() == null) {
            throw new NullPersonException("Cannot delete non-existent student!");
        }
        StudentEntity studentEntity = StudentMapper.toEntity(studentTO);

        EmployeeEntity employeeEntity = employeeDao.findByStudent(studentEntity);
        employeeEntity.setStudent(null);
        employeeDao.save(employeeEntity);

        studentDao.delete(StudentMapper.toEntity(studentTO));
    }

    @Override
    @Transactional(readOnly = false)
    public StudentTO updateStudent(StudentTO studentTO) throws NullPersonException {
        if (studentTO == null) { //nie spr czy studentTO istnieje w bazie danych, jesli nie istineje to go po prostu dodam
            throw new NullPersonException("Cannot update student with empty data!");
        }
        StudentEntity studentEntity = StudentMapper.toEntity(studentTO);
        EmployeeEntity boss = employeeDao.findById(studentTO.getBossId()).get();
        studentEntity.setBoss(boss);
        studentEntity = studentDao.save(studentEntity);
        studentTO = StudentMapper.toTO(studentEntity);

        EmployeeEntity employeeEntity=employeeDao.findByStudent(studentEntity);
        employeeEntity.setStudent(studentEntity);
        employeeDao.save(employeeEntity);

        return studentTO;
    }

    @Override
    public StudentTO findStudent(Long id) {
        Optional<StudentEntity> found = studentDao.findById(id);
        boolean check = found.isPresent();
        if (check) {
            return StudentMapper.toTO(found.get());
        }
        return null;
    }
}


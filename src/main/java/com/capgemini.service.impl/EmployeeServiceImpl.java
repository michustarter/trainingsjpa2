package com.capgemini.service.impl;


import com.capgemini.dao.EmployeeDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.service.EmployeeService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.exceptions.EmployeeAlreadyExistsException;
import com.capgemini.exceptions.NullPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = false)
    public EmployeeTO addEmployee(EmployeeTO employeeTO) throws NullPersonException, EmployeeAlreadyExistsException {

        if (employeeTO == null) {
            throw new NullPersonException("Cannot add employee to database with empty data!");
        }
        //isPresent dodane zamiast != null
        if (employeeTO.getId() != null /*|| employeeDao.findById(employeeTO.getId()).isPresent()*/) {
            throw new EmployeeAlreadyExistsException("This employee already exists in database!");
        }
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeEntity = employeeDao.save(employeeEntity);
        employeeTO = EmployeeMapper.toTO(employeeEntity);

        return employeeTO;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteEmployee(EmployeeTO employeeTO) throws NullPersonException {
        // zakładam, że eTO ma ID, jelsi nie ma ID -> to nie istnieje w bazie danych
        if (employeeTO==null) {
            throw new NullPersonException("Cannot delete non-existent employee!");
        }
        // eTO zakł że ma ID wiec tutaj już tego nie sprawdzam w ifie że != null
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeDao.deleteById(employeeEntity.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public EmployeeTO updateEmployee(EmployeeTO employeeTO) throws NullPersonException {
        if (employeeTO == null) { //nie spr czy emplTO istnieje w bazie danych, jesli nie istineje to go po prostu dodam
            throw new NullPersonException("Cannot update employee with empty data!");
        }
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeEntity = employeeDao.save(employeeEntity);
        employeeTO = EmployeeMapper.toTO(employeeEntity);

        return employeeTO;
    }

    @Override
    public EmployeeTO findEmployee(Long id) {
        Optional<EmployeeEntity> found = employeeDao.findById(id);
        boolean check = found.isPresent();
        if (check) {
            return EmployeeMapper.toTO(found.get());
        }
        return null;
    }
}
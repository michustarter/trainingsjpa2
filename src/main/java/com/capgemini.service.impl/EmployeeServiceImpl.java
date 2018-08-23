package com.capgemini.service.impl;


import com.capgemini.dao.EmployeeDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.service.EmployeeService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.util.EmployeeAlreadyExistsException;
import com.capgemini.util.NullPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao=employeeDao;
    }


    @Override
    @Transactional
    public EmployeeTO addEmployee(EmployeeTO employeeTO) {

        if(employeeTO == null) {
            throw new NullPersonException("Cannot add employee to database with empty data!");
        }
        //isPresent dodane zamiast != null
        if(employeeTO.getId()!=null || employeeDao.findById(employeeTO.getId()).isPresent()) {
            throw new EmployeeAlreadyExistsException("Employee already exists in database!");
        }
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeEntity=employeeDao.save(employeeEntity);
        employeeTO=EmployeeMapper.toTO(employeeEntity);

        return employeeTO;
    }

    @Override
    public void deleteEmployee(EmployeeTO employeeTO) {
        // zakładam, że eTO ma ID, jelsi nie ma ID -> to nie istnieje w bazie danych
        if(employeeTO.getId()==null || !employeeDao.findById(employeeTO.getId()).isPresent()) {
            throw new NullPersonException("Cannot delete non-existent employee!");

        }
        // eTO zakł że ma ID wiec tutaj już tego nie sprawdzam w ifie że != nulla
            EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
            employeeDao.deleteById(employeeEntity.getId());

    }
}
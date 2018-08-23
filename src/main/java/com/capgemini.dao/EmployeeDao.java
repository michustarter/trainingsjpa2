package com.capgemini.dao;

import com.capgemini.domain.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EmployeeDao extends CrudRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAll();

}
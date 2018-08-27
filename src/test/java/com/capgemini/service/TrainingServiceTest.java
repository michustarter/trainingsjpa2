package com.capgemini.service;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.exceptions.EmployeeAlreadyExistsException;
import com.capgemini.exceptions.NullPersonException;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.types.EmployeeTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest {




    

}



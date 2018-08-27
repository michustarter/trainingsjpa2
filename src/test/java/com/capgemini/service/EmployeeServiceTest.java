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
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Transactional
    public void testShouldAddNewEmployee() throws NullPersonException, EmployeeAlreadyExistsException {

        //given
        EmployeeTO newEmployeeTO = employeeService.addEmployee(createEmployeeTO());

        //when
        EmployeeTO foundEmployee = employeeService.findEmployee(newEmployeeTO.getId());

        // then
        assertThat(foundEmployee, equalTo(newEmployeeTO));
    }


    @Test(expected = NullPersonException.class)
    @Transactional
    public void testShouldThrownNullPersonExceptionWhenAddNullEmployee() throws NullPersonException, EmployeeAlreadyExistsException {
        //given
        EmployeeTO newEmployeeTO = null;

        //when
        EmployeeTO addedEmployeeTO = employeeService.addEmployee(newEmployeeTO);
    }

    @Test(expected = EmployeeAlreadyExistsException.class)
    @Transactional
    public void testShouldThrownEmployeeAlreadyExistsExceptionWhenAddDuplicateEmployee() throws NullPersonException, EmployeeAlreadyExistsException {
        //given
        EmployeeTO newEmployeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO duplicateEmployee = newEmployeeTO;

        //when
        employeeService.addEmployee(duplicateEmployee);
    }

    @Test
    @Transactional
    public void testShouldDeleteEmployee() throws NullPersonException, EmployeeAlreadyExistsException {
        //given
        EmployeeTO removeEmployeeTO = employeeService.addEmployee(createEmployeeTO());

        //when
        employeeService.deleteEmployee(removeEmployeeTO);
        EmployeeTO foundEmployee = employeeService.findEmployee(removeEmployeeTO.getId());

        //then
        assertNull(foundEmployee);
    }

    @Test(expected = NullPersonException.class)
    @Transactional
    public void testShouldThrownNullPersonExceptionWhenDeleteNullEmployee() throws NullPersonException, EmployeeAlreadyExistsException {
        //given
        EmployeeTO removeEmployeeTO = null;

        //when
        employeeService.deleteEmployee(removeEmployeeTO);

    }


    @Test
    @Transactional
    public void testShouldUpdateEmployee() throws NullPersonException, EmployeeAlreadyExistsException {
        //given
        EmployeeTO updateEmployeeTO = employeeService.addEmployee(createEmployeeTO());
        updateEmployeeTO.setFirstName("Janek");
        updateEmployeeTO.setLastName("Zgrzanek");

        //when
        employeeService.updateEmployee(updateEmployeeTO);
        EmployeeTO afterUpdateEmployee=employeeService.findEmployee(updateEmployeeTO.getId());

        // then
        assertEquals(afterUpdateEmployee.getFirstName(),updateEmployeeTO.getFirstName());
        assertEquals(afterUpdateEmployee.getLastName(),updateEmployeeTO.getLastName());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldThrownOptimisticLockingException() throws NullPersonException, EmployeeAlreadyExistsException {

        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());

        //when
        employeeTO.setFirstName("Wojtek");
        employeeService.updateEmployee(employeeTO);

        employeeTO.setFirstName("Dawid");
        employeeService.updateEmployee(employeeTO);
    }
    @Test
    @Transactional
    public void testShouldFindEmployee() throws NullPersonException, EmployeeAlreadyExistsException {
        //given
        EmployeeTO newEmployeeTO = employeeService.addEmployee(createEmployeeTO());

        //when
        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(newEmployeeTO);

        // then
        assertThat(newEmployeeTO, equalTo(employeeService.findEmployee(employeeEntity.getId())));
    }

    @Test
    @Transactional
    public void testShouldReturnNullIfDoesNotFindEmployee() {

        //when
         EmployeeTO foundEmployee = employeeService.findEmployee(2L);

        // then
        assertThat(foundEmployee, equalTo(null));
    }






















    private static EmployeeTO createEmployeeTO() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Kazimierz")
                .withLastName("Wielki")
                .withPosition("manager")
                .withTrainerId(1L)
                .withStudentId(1L)
                .build();

    }
}
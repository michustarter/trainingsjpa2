package com.capgemini.service;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.exceptions.BadGradeRangeException;
import com.capgemini.exceptions.EmployeeAlreadyExistsException;
import com.capgemini.exceptions.NullPersonException;
import com.capgemini.exceptions.StudentAlreadyExistsException;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
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
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;
    @Autowired
    private EmployeeService employeeService;

    @Test
    @Transactional
    public void testShouldAddNewStudent() throws NullPersonException, EmployeeAlreadyExistsException,
            BadGradeRangeException, StudentAlreadyExistsException {
        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 3;

        StudentTO newStudentTO = studentService.addStudent(employeeTO, bossTO, grade);

        //when
        StudentTO foundStudent = studentService.findStudent(newStudentTO.getId());

        // then
        assertThat(foundStudent, equalTo(newStudentTO));
    }

    @Test(expected = NullPersonException.class)
    @Transactional
    public void testShouldThrownNullPersonExceptionWhenEmployeeIsNull() throws NullPersonException,
            EmployeeAlreadyExistsException, BadGradeRangeException, StudentAlreadyExistsException {

        //given
        EmployeeTO employeeTO = null;
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 3;

        //when
        StudentTO newStudentTO = studentService.addStudent(employeeTO, bossTO, grade);

    }

    @Test(expected = StudentAlreadyExistsException.class)
    @Transactional
    public void testShouldThrownStudentAlreadyExistsExceptionWhenAddDuplicateStudent() throws NullPersonException,
            EmployeeAlreadyExistsException, BadGradeRangeException, StudentAlreadyExistsException {

        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 3;

        StudentTO newStudentTO = studentService.addStudent(employeeTO, bossTO, grade);
        employeeTO = employeeService.findEmployee(employeeTO.getId());

        //when
        StudentTO duplicate = studentService.addStudent(employeeTO, bossTO, grade);

    }

    @Test(expected = NullPersonException.class)
    @Transactional
    public void testShouldThrownNullPersonExceptionWhenBossIsNUll() throws NullPersonException, EmployeeAlreadyExistsException,
            BadGradeRangeException, StudentAlreadyExistsException {
        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = null;
        int grade = 3;

        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeTO = EmployeeMapper.toTO(employeeEntity);

        StudentTO newStudentTO = studentService.addStudent(employeeTO, bossTO, grade);

    }

    @Test(expected = BadGradeRangeException.class)
    @Transactional
    public void testShouldThrownBadGradeRangeExceptionWhenGradeIsInvalid() throws NullPersonException, EmployeeAlreadyExistsException,
            BadGradeRangeException, StudentAlreadyExistsException {

        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 7;

        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeTO = EmployeeMapper.toTO(employeeEntity);

        EmployeeEntity bossEntity = EmployeeMapper.toEntity(bossTO);
        bossTO = EmployeeMapper.toTO(bossEntity);

        StudentTO newStudentTO = studentService.addStudent(employeeTO, bossTO, grade);
    }

    @Test
    @Transactional
    public void testShouldDeleteStudent() throws NullPersonException, EmployeeAlreadyExistsException,
            BadGradeRangeException, StudentAlreadyExistsException {

        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 3;

        StudentTO newStudentTO = studentService.addStudent(employeeTO, bossTO, grade);

        //when
        studentService.deleteStudent(newStudentTO);
        StudentTO foundStudent = studentService.findStudent(newStudentTO.getId());

        //then
        assertNull(foundStudent);
    }

    @Test(expected = NullPersonException.class)
    @Transactional
    public void testShouldThrownNullPersonExceptionWhenDeleteNullStudent() throws NullPersonException {
        //given
        StudentTO newStudentTO = null;

        //when
        studentService.deleteStudent(newStudentTO);

    }

    @Test
    @Transactional
    public void testShouldUpdateStudent() throws NullPersonException, BadGradeRangeException,
            StudentAlreadyExistsException, EmployeeAlreadyExistsException {

        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 3;
        StudentTO studentTO = studentService.addStudent(employeeTO, bossTO, grade);

        studentTO.setFirstName("Tomasz");
        studentTO.setLastName("Jarucki");

        //when
        studentTO = studentService.updateStudent(studentTO);
        StudentTO afterUpdateStudent = studentService.findStudent(studentTO.getId());

        // then
        assertEquals(afterUpdateStudent.getFirstName(), studentTO.getFirstName());
        assertEquals(afterUpdateStudent.getLastName(), studentTO.getLastName());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldThrownOptimisticLockingException() throws NullPersonException, EmployeeAlreadyExistsException,
            BadGradeRangeException, StudentAlreadyExistsException {

        //given

        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 3;
        StudentTO studentTO = studentService.addStudent(employeeTO, bossTO, grade);

        //when
        studentTO.setFirstName("Jakub");
        studentService.updateStudent(studentTO);

        studentTO.setFirstName("Jacek");
        studentService.updateStudent(studentTO);

    }

    @Test
    @Transactional
    public void testShouldFindStudent() throws NullPersonException, EmployeeAlreadyExistsException, BadGradeRangeException,
            StudentAlreadyExistsException {

        //given
        EmployeeTO employeeTO = employeeService.addEmployee(createEmployeeTO());
        EmployeeTO bossTO = employeeService.addEmployee(createBossTO());
        int grade = 3;
        StudentTO studentTO = studentService.addStudent(employeeTO, bossTO, grade);

        //when
        StudentEntity studentEntity = StudentMapper.toEntity(studentTO);

        // then
        assertThat(studentTO, equalTo(studentService.findStudent(studentEntity.getId())));
    }

    @Test
    @Transactional
    public void testShouldReturnNullIfDoesNotFindStudent() {

        //when
        StudentTO studentTO = studentService.findStudent(2L);

        // then
        assertThat(studentTO, equalTo(null));
    }


    private static EmployeeTO createEmployeeTO() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Kazimierz")
                .withLastName("Wielki")
                .withPosition("manager")
                .build();
    }

    private static EmployeeTO createBossTO() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Janusz")
                .withLastName("Mały")
                .withPosition("boss")
                .build();
    }


}

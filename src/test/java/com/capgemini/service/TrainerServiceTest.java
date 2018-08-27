package com.capgemini.service;

import com.capgemini.exceptions.IncorrectTrainerException;
import com.capgemini.exceptions.NullPersonException;
import com.capgemini.exceptions.TrainerAlreadyExistsException;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.TrainerTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainerServiceTest {

    @Autowired
    private StudentService studentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TrainerService trainerService;

    @Test
    @Transactional
    public void testShouldAddNewTrainer() throws NullPersonException, TrainerAlreadyExistsException {

        //given
        TrainerTO trainerTO = trainerService.addTrainer(createEmployeeTO());

        //when
        TrainerTO foundTrainer = trainerService.findTrainer(trainerTO.getId());

        // then
        assertThat(foundTrainer, equalTo(trainerTO));
    }

    @Test
    @Transactional
    public void testShouldAddNewExternalTrainer() throws NullPersonException, IncorrectTrainerException {

        //given
        TrainerTO trainerTO = trainerService.addExternalTrainer(createExternalTrainerTO());

        //when
        TrainerTO foundTrainer = trainerService.findTrainer(trainerTO.getId());

        // then
        assertThat(foundTrainer, equalTo(trainerTO));
    }

    @Test
    @Transactional
    public void testShouldDeleteTrainer() throws NullPersonException, TrainerAlreadyExistsException {

        //given
        TrainerTO trainerTO = trainerService.addTrainer(createEmployeeTO());

        //when
        trainerService.deleteTrainer(trainerTO);
        TrainerTO foundTrainer = trainerService.findTrainer(trainerTO.getId());

        //then
        assertNull(foundTrainer);
    }

    @Test
    @Transactional
    public void testShouldDeleteExternalTrainer() throws NullPersonException, IncorrectTrainerException {

        //given
        TrainerTO trainerTO = trainerService.addExternalTrainer(createExternalTrainerTO());

        //when
        trainerService.deleteTrainer(trainerTO);
        TrainerTO foundTrainer = trainerService.findTrainer(trainerTO.getId());

        //then
        assertNull(foundTrainer);
    }

    @Test
    @Transactional
    public void testShouldUpdateTrainer() throws NullPersonException, TrainerAlreadyExistsException {

        //given
        TrainerTO trainerTO = trainerService.addTrainer(createEmployeeTO());

        trainerTO.setFirstName("Tomasz");
        trainerTO.setLastName("Jarucki");

        //when
        trainerTO = trainerService.updateTrainer(trainerTO);
        TrainerTO afterUpdateTrainer = trainerService.findTrainer(trainerTO.getId());

        // then
        assertEquals(afterUpdateTrainer.getFirstName(), trainerTO.getFirstName());
        assertEquals(afterUpdateTrainer.getLastName(), trainerTO.getLastName());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldThrownOptimisticLockingException() throws NullPersonException, TrainerAlreadyExistsException {

        //given
        TrainerTO trainerTO = trainerService.addTrainer(createEmployeeTO());


        //when
        trainerTO.setFirstName("Tomasz");
        trainerTO = trainerService.updateTrainer(trainerTO);

        trainerTO.setLastName("Jarucki");
        trainerTO = trainerService.updateTrainer(trainerTO);

    }


    private static EmployeeTO createEmployeeTO() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Kazimierz")
                .withLastName("Wielki")
                .withPosition("manager")
                .build();
    }

    private static TrainerTO createExternalTrainerTO() {

        return new TrainerTO.TrainerTOBuilder()
                .withFirstName("Karol")
                .withLastName("Pajor")
                .withPosition("manager")
                .withCompanyName("ABB")
                .build();

    }

}


package com.capgemini.service;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.dao.TrainingDao;
import com.capgemini.exceptions.*;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TrainingServiceTest {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private TrainingDao trainingDao;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainingService trainingService;

    @Test
    @Transactional
    public void testShouldAddNewTraining() throws BadGradeRangeException, EmployeeAlreadyExistsException,
            StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException, TrainerAlreadyExistsException,
            NullTrainingException, TrainingAlreadyExistsException, NullIdException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        //when
        TrainingTO foundTraining = trainingService.findTraining(trainingTO.getId());

        // then
        assertThat(foundTraining, equalTo(trainingTO));
    }

    @Test
    @Transactional
    public void testShouldAssignTrainerToTraining() throws BadGradeRangeException, EmployeeAlreadyExistsException,
            StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException, TrainerAlreadyExistsException,
            NullTrainingException, TrainingAlreadyExistsException, TrainerIsAlreadyAssignedException,
            TrainerCannotBeAStudentException {
        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());
        TrainerTO trainerTO = trainerService.addTrainer(createEmployeeTO4());
        //when
        trainingTO = trainingService.assignTrainerToTraining(trainingTO, trainerTO);

        // then
        assertTrue(trainingTO.getTrainersId().contains(trainerTO.getId()));
    }

    @Test(expected = TrainerCannotBeAStudentException.class)
    @Transactional
    public void testShouldThrownExceptionWhenAssignedTrainerToTrainingIsStudent() throws BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, NullTrainingException, TrainingAlreadyExistsException, TrainerIsAlreadyAssignedException,
            TrainerCannotBeAStudentException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());
        TrainerTO trainerTO = trainerService.addTrainer(createEmployeeTO1());

        //when
        trainingTO = trainingService.assignTrainerToTraining(trainingTO, trainerTO);

    }

    @Test(expected = TrainerIsAlreadyAssignedException.class)
    @Transactional
    public void testShouldThrownExceptionWhenAssignedTrainerIsAlreadyAssignedToTraining() throws BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, NullTrainingException, TrainingAlreadyExistsException, TrainerIsAlreadyAssignedException,
            TrainerCannotBeAStudentException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());
        TrainerTO trainerTO = trainerService.addTrainer(createEmployeeTO3());
        //when
        trainingTO = trainingService.assignTrainerToTraining(trainingTO, trainerTO);


    }

    @Test
    @Transactional
    public void testShouldAssignStudentToTraining() throws NullPersonException, BadGradeRangeException,
            StudentAlreadyExistsException, TrainerAlreadyExistsException, EmployeeAlreadyExistsException,
            IncorrectTrainerException, NullTrainingException, TrainingAlreadyExistsException,
            StudentIsAlreadyAssignedException, TrainerCannotBeAStudentException, InvalidConditionsException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        EmployeeTO employeeTO5 = employeeService.addEmployee(createEmployeeTO5());
        EmployeeTO bossTO5 = employeeService.addEmployee(createBossTO5());

        StudentTO studentTO5 = studentService.addStudent(employeeTO5, bossTO5, 5);

        //when
        trainingTO = trainingService.assignStudentToTraining(trainingTO, studentTO5);

        // then
        assertTrue(trainingTO.getStudentsId().contains(studentTO5.getId()));
    }

    @Test(expected = TrainerCannotBeAStudentException.class)
    @Transactional
    public void testShouldThrownExceptionWhenAssignedStudentToTrainingIsTrainer() throws BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, NullTrainingException, TrainingAlreadyExistsException, TrainerCannotBeAStudentException,
            InvalidConditionsException, StudentIsAlreadyAssignedException {
        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());
        EmployeeTO employeeTO3 = employeeService.addEmployee(createEmployeeTO3());
        EmployeeTO bossTO5 = employeeService.addEmployee(createBossTO5());

        StudentTO studentTO3 = studentService.addStudent(employeeTO3, bossTO5, 5);

        //when
        trainingTO = trainingService.assignStudentToTraining(trainingTO, studentTO3);

    }

    @Test(expected = StudentIsAlreadyAssignedException.class)
    @Transactional
    public void testShouldThrownExceptionWhenAssignedStudentIsAlreadyAssignedToTraining() throws BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, NullTrainingException, TrainingAlreadyExistsException, TrainerCannotBeAStudentException,
            InvalidConditionsException, StudentIsAlreadyAssignedException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        EmployeeTO employeeTO1 = employeeService.addEmployee(createEmployeeTO1());
        EmployeeTO bossTO1 = employeeService.addEmployee(createBossTO1());

        StudentTO studentTO1 = studentService.addStudent(employeeTO1, bossTO1, 3);

        //when
        trainingTO = trainingService.assignStudentToTraining(trainingTO, studentTO1);

    }

    @Test
    @Transactional
    public void testShouldUpdateTraining() throws BadGradeRangeException, EmployeeAlreadyExistsException,
            StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException, TrainerAlreadyExistsException,
            NullTrainingException, NullIdException, TrainingAlreadyExistsException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());
        trainingTO.setTitle("changed title");
        trainingTO.setAmount(777);

        //when
        trainingTO = trainingService.updateTraining(trainingTO);
        TrainingTO afterUpdateTraining = trainingService.findTraining(trainingTO.getId());

        // then
        assertEquals(afterUpdateTraining.getTitle(), trainingTO.getTitle());
        assertTrue(afterUpdateTraining.getAmount() == trainingTO.getAmount());

    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void testShouldThrownOptimisticLockingException() throws BadGradeRangeException, EmployeeAlreadyExistsException,
            StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException, TrainerAlreadyExistsException,
            NullTrainingException, TrainingAlreadyExistsException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        //when
        trainingTO.setTitle("Title1");
        trainingService.updateTraining(trainingTO);

        trainingTO.setTitle("Title2");
        trainingService.updateTraining(trainingTO);

    }

    @Test
    @Transactional
    public void testShouldDeleteTraining() throws NullTrainingException, NullIdException, BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, TrainingAlreadyExistsException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        //when
        trainingService.deleteTraining(trainingTO);
        TrainingTO foundTraining = trainingService.findTraining(trainingTO.getId());

        //then
        assertNull(foundTraining);
    }

    @Test
    @Transactional
    public void testShouldFindTrainersFromTraining() throws NullTrainingException, BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, TrainingAlreadyExistsException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        //when
        List<TrainerTO> trainers = trainingService.findTrainers(trainingTO);

        //then
        assertTrue(!trainers.isEmpty());
    }

    @Test
    @Transactional
    public void testShouldFindStudentsFromTraining() throws NullTrainingException, BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, TrainingAlreadyExistsException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        //when
        List<StudentTO> students = trainingService.findStudents(trainingTO);

        //then
        assertTrue(!students.isEmpty());
    }

    @Test
    @Transactional
    public void testShouldFindTrainingByKeyWord() throws NullTrainingException, BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, TrainingAlreadyExistsException, NoKeyWordException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        //when
        List<TrainingTO> trainings = trainingService.searchTrainingsByKeyWord("excel");

        //then
        assertTrue(!trainings.isEmpty());
        assertTrue(trainings.get(0).equals(trainingTO));
    }

    @Test
    @Transactional
    public void testShouldSumNumberOfHoursTrainingsForTrainer() throws NullTrainingException, BadGradeRangeException,
            EmployeeAlreadyExistsException, StudentAlreadyExistsException, IncorrectTrainerException, NullPersonException,
            TrainerAlreadyExistsException, TrainingAlreadyExistsException {

        //given
        TrainingTO trainingTO = trainingService.addTraining(createTraining());

        Long trainerId = trainingTO.getTrainersId().get(0);


        //when
        int sum = trainingService.sumHoursFromTrainerTrainingsInCurrentYear(trainerId);

        //then
        assertTrue(sum == trainingTO.getNumberOfHours());
    }


    private TrainingTO createTraining() throws NullPersonException, EmployeeAlreadyExistsException,
            BadGradeRangeException, StudentAlreadyExistsException, TrainerAlreadyExistsException, IncorrectTrainerException {

        List<String> keyWords = new ArrayList<>();
        keyWords.add("budzetowanie");
        keyWords.add("excel");
        keyWords.add("kwerendy");

        List<Long> studentsId = new ArrayList<>();

        EmployeeTO employeeTO1 = employeeService.addEmployee(createEmployeeTO1());
        EmployeeTO bossTO1 = employeeService.addEmployee(createBossTO1());

        EmployeeTO employeeTO2 = employeeService.addEmployee(createEmployeeTO2());
        EmployeeTO bossTO2 = employeeService.addEmployee(createBossTO2());

        StudentTO studentTO1 = studentService.addStudent(employeeTO1, bossTO1, 3);
        StudentTO studentTO2 = studentService.addStudent(employeeTO2, bossTO2, 4);

        studentsId.add(studentTO1.getId());
        studentsId.add(studentTO2.getId());

        List<Long> trainersId = new ArrayList<>();

        TrainerTO trainerTO1 = trainerService.addTrainer(createEmployeeTO3());

        TrainerTO trainerTO3 = trainerService.addExternalTrainer(createExternalTrainerTO1());

        trainersId.add(trainerTO1.getId());
        trainersId.add(trainerTO3.getId());

        TrainingTO trainingTO = new TrainingTO("training", "internal", "technical",
                Date.valueOf("2018-04-04"), Date.valueOf("2018-06-06"),
                150, 500, keyWords, studentsId, trainersId);

        return trainingTO;
    }


    private static EmployeeTO createEmployeeTO1() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Kazimierz")
                .withLastName("Wielki")
                .withPosition("manager")
                .build();
    }

    private static EmployeeTO createBossTO1() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Janusz")
                .withLastName("Mały")
                .withPosition("boss")
                .build();
    }

    private static EmployeeTO createEmployeeTO2() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Wlodzimierz")
                .withLastName("Sredni")
                .withPosition("manager")
                .build();
    }

    private static EmployeeTO createBossTO2() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Jaroslaw")
                .withLastName("Masa")
                .withPosition("boss")
                .build();
    }

    private static EmployeeTO createEmployeeTO5() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Dawid")
                .withLastName("Salomon")
                .withPosition("accountant")
                .build();
    }

    private static EmployeeTO createBossTO5() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Katarzyna")
                .withLastName("Niebieska")
                .withPosition("boss")
                .build();
    }

    private static EmployeeTO createEmployeeTO3() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Kazimierz")
                .withLastName("Osada")
                .withPosition("manager")
                .build();
    }

    private static TrainerTO createExternalTrainerTO1() {

        return new TrainerTO.TrainerTOBuilder()
                .withFirstName("Janina")
                .withLastName("Bajor")
                .withPosition("boss")
                .withCompanyName("ABB")
                .build();
    }

    private static EmployeeTO createEmployeeTO4() {
        return new EmployeeTO.EmployeeTOBuilder()
                .withFirstName("Piotr")
                .withLastName("Jaros")
                .withPosition("beginner")
                .build();
    }

}



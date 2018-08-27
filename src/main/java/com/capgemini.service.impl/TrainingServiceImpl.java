package com.capgemini.service.impl;


import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.dao.TrainingDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.mappers.StudentMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.mappers.TrainingMapper;
import com.capgemini.service.TrainingService;
import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import com.capgemini.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    private EmployeeDao employeeDao;
    private StudentDao studentDao;
    private TrainerDao trainerDao;
    private TrainingDao trainingDao;

    @Autowired
    public TrainingServiceImpl(EmployeeDao employeeDao, StudentDao studentDao, TrainerDao trainerDao, TrainingDao trainingDao) {
        this.employeeDao = employeeDao;
        this.studentDao = studentDao;
        this.trainerDao = trainerDao;
        this.trainingDao = trainingDao;

    }


    @Override
    @Transactional(readOnly = false)
    public TrainingTO addTraining(TrainingTO trainingTO) throws NullTrainingException, TrainingAlreadyExistsException {
        if (trainingTO == null) {
            throw new NullTrainingException("Cannot add training to database with empty data!");
        }
        if (trainingTO.getId() != null || trainerDao.findById(trainingTO.getId()).isPresent()) {
            throw new TrainingAlreadyExistsException("This training already exists in database!");
        }

        TrainingEntity trainingEntity = TrainingMapper.toEntity(trainingTO);

        List<StudentEntity> students = new ArrayList<>();

        if (!trainingTO.getStudentsId().isEmpty()) {
            for (Long studentId : trainingTO.getStudentsId()) {
                StudentEntity student = studentDao.findById(studentId).get();
                students.add(student);
            }
        }

        List<TrainerEntity> trainers = new ArrayList<>();

        if (!trainingTO.getTrainersId().isEmpty()) {
            for (Long trainerId : trainingTO.getTrainersId()) {
                TrainerEntity trainer = trainerDao.findById(trainerId).get();
                trainers.add(trainer);
            }
        }

        trainingEntity.setStudents(students);
        trainingEntity.setTrainers(trainers);

        trainingEntity = trainingDao.save(trainingEntity);
        trainingTO = TrainingMapper.toTO(trainingEntity);


        return trainingTO;
    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO assignTrainerToTraining(TrainingTO trainingTO, TrainerTO trainerTO) throws NullTrainingException,
            TrainerIsAlreadyAssignedException, TrainerCannotBeAStudentException, NullPersonException {

        if (!trainingDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("This training does not exist!");
        }
        if (!trainerDao.findById(trainerTO.getId()).isPresent()) {
            throw new NullPersonException("This trainer does not exist!");
        }

        TrainingEntity trainingEntity = TrainingMapper.toEntity(trainingTO);
        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainerTO);

        List<EmployeeEntity> employeesAsStudents = trainingEntity.getStudents()
                .stream()
                .map(s -> employeeDao.findByStudent(s))
                .collect(Collectors.toList());

        EmployeeEntity trainerEmployee = employeeDao.findByTrainer(trainerEntity);

        if (employeesAsStudents.contains(trainerEmployee)) {
            throw new TrainerCannotBeAStudentException("This trainer is already a student in this training!");
        }

        if (trainingEntity.getTrainers().contains(trainerEntity)) {
            throw new TrainerIsAlreadyAssignedException("This trainer is already assigned to the training");
        }
        trainingEntity.getTrainers().add(trainerEntity);
        trainingEntity = trainingDao.save(trainingEntity);
        trainingTO = TrainingMapper.toTO(trainingEntity);


        return trainingTO;
    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO assignStudentToTraining(TrainingTO trainingTO, StudentTO studentTO) throws NullTrainingException,
            StudentIsAlreadyAssignedException, TrainerCannotBeAStudentException, InvalidConditionsException, NullPersonException {

        if (!trainingDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("This training does not exist!");
        }
        if (!studentDao.findById(studentTO.getId()).isPresent()) {
            throw new NullPersonException("This student does not exist!");
        }

        TrainingEntity trainingEntity = TrainingMapper.toEntity(trainingTO);
        StudentEntity studentEntity = StudentMapper.toEntity(studentTO);

        List<EmployeeEntity> employeesAsTrainers = trainingEntity.getTrainers()
                .stream()
                .map(t -> employeeDao.findByTrainer(t))
                .collect(Collectors.toList());

        EmployeeEntity studentEmployee = employeeDao.findByStudent(studentEntity);

        if (employeesAsTrainers.contains(studentEmployee)) {
            throw new TrainerCannotBeAStudentException("This trainer is already a student in this training!");
        }

        if (trainingEntity.getTrainers().contains(studentEntity)) {
            throw new StudentIsAlreadyAssignedException("This student is already assigned to the training");
        }

        int grade = studentTO.getGrade();
        int numberOfTrainings = countAllStudentTrainingsInYear(studentEntity.getId()) + 1;
        double amount = trainingTO.getAmount() + calculateStudentCostsTrainings(studentEntity.getId());

        if (!budgetValidation(amount, grade, numberOfTrainings)) {
            throw new InvalidConditionsException("Employee cannot take part in this training!");
        }

        trainingEntity.getStudents().add(studentEntity);
        trainingEntity = trainingDao.save(trainingEntity);
        trainingTO = TrainingMapper.toTO(trainingEntity);


        return trainingTO;
    }

    @Override
    @Transactional(readOnly = false)
    public TrainingTO updateTraining(TrainingTO trainingTO)
            throws /*OptimisticLockingFailureException,*/NullTrainingException {
        if (trainingTO == null) {
            throw new NullTrainingException("Cannot update training with empty data!");
        }
        TrainingEntity trainingEntity = TrainingMapper.toEntity(trainingTO);

        List<StudentEntity> students = new ArrayList<>();

        if (!trainingTO.getStudentsId().isEmpty()) {
            for (Long studentId : trainingTO.getStudentsId()) {
                StudentEntity student = studentDao.findById(studentId).get();
                students.add(student);
            }
        }

        List<TrainerEntity> trainers = new ArrayList<>();

        if (!trainingTO.getTrainersId().isEmpty()) {
            for (Long trainerId : trainingTO.getTrainersId()) {
                TrainerEntity trainer = trainerDao.findById(trainerId).get();
                trainers.add(trainer);
            }
        }

        trainingEntity.setStudents(students);
        trainingEntity.setTrainers(trainers);

        trainingEntity = trainingDao.save(trainingEntity);
        trainingTO = TrainingMapper.toTO(trainingEntity);

        return trainingTO;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteTraining(TrainingTO trainingTO) throws NullTrainingException {

        if (trainingTO == null || trainingTO.getId() == null || !trainerDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("Cannot delete non-existent training!");
        }
        TrainingEntity trainingEntity = TrainingMapper.toEntity(trainingTO);
        trainingDao.delete(trainingEntity);
    }

    @Override
    @Transactional
    public TrainingTO findTraining(Long trainingId) throws NullIdException {
        if (trainingId == null) {
            throw new NullIdException("Cannot find training by null id");
        }

        TrainingEntity trainingEntity = trainingDao.findById(trainingId).get();
        TrainingTO trainingTO = TrainingMapper.toTO(trainingEntity);

        return trainingTO;
    }

    @Override
    @Transactional
    public List<TrainingTO> findTrainings() {

        List<TrainingTO> trainigs = TrainingMapper.map2TOs(trainingDao.findAll());
        return trainigs;
    }

    @Override
    @Transactional
    public List<TrainerTO> findTrainers(TrainingTO trainingTO) throws NullTrainingException {
        if (trainingTO == null || trainingTO.getId() == null || !trainingDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("Cannot find trainers in non-existent training!");
        }
        List<TrainerTO> trainers = new ArrayList<>();

        if (!trainingTO.getTrainersId().isEmpty()) {
            for (Long trainerId : trainingTO.getTrainersId()) {
                TrainerTO trainerTO = TrainerMapper.toTO(trainerDao.findById(trainerId).get());
                trainers.add(trainerTO);
            }
        }

        return trainers;
    }

    @Override
    @Transactional
    public List<StudentTO> findStudents(TrainingTO trainingTO) throws NullTrainingException {
        if (trainingTO == null || !trainingDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("Cannot find students in non-existent training!");
        }
        List<StudentTO> students = new ArrayList<>();

        if (!trainingTO.getStudentsId().isEmpty()) {
            for (Long studentId : trainingTO.getStudentsId()) {
                StudentTO studentTO = StudentMapper.toTO(studentDao.findById(studentId).get());
                students.add(studentTO);
            }
        }

        return students;
    }

    //b
    @Override
    @Transactional
    public List<TrainingTO> searchTrainingsByKeyWord(String keyWord) throws NoKeyWordException {

        if (keyWord == null || keyWord.isEmpty()) {
            throw new NoKeyWordException("Cannot search for trainings without any keyWord!");
        }
        List<TrainingTO> trainingList = TrainingMapper.map2TOs(
                trainingDao.findByKeyWord(keyWord));

        return trainingList;
    }

    //c
    @Override
    @Transactional
    public int sumHoursFromTrainerTrainingsInCurrentYear(Long trainerId) throws NullTrainingException {

        if (trainerId == null || !trainingDao.findById(trainerId).isPresent()) {
            throw new NullTrainingException("Trainer does not exist!");
        }
        Date dateFrom = Date.valueOf("20180101");
        Date dateTo = Date.valueOf("20181231");

        int sum = trainingDao.countHoursFromAllTrainingsLeadedByTrainerInCurrentYear(trainerId, dateFrom, dateTo);
        return sum;
    }

    //d
    @Override
    @Transactional
    public int countNumberOfEmployeeTrainingsInPeriod(Long studentId, Date dateFrom, Date dateTo)
            throws InvalidOrderOfDatesException, NullPersonException {

        if (studentId == null || !studentDao.findById(studentId).isPresent()) {
            throw new NullPersonException("Cannot count number od trainings for non-existent student!");
        }

        if (dateTo.before(dateFrom)) {
            throw new InvalidOrderOfDatesException("DateTO cannot be earlier thn dateFrom!");
        }
        return trainingDao.countAllEmployeeTrainingsInGivenTimePeriod(studentId, dateFrom, dateTo);
    }

    //e
    @Override
    @Transactional
    public double calculateStudentCostsTrainings(Long studentId) throws NullPersonException {

        if (studentId == null || !studentDao.findById(studentId).isPresent()) {
            throw new NullPersonException("Cannot count costs od trainings for non-existent student!");
        }
        return trainingDao.calculateCostOfStudentTrainings(studentId);
    }

    @Override
    @Transactional
    public double calculateStudentCostsTrainingsInYear(Long studentId) throws NullPersonException {
        if (studentId == null || !studentDao.findById(studentId).isPresent()) {
            throw new NullPersonException("Cannot count costs od trainings for non-existent student!");
        }
        Date dateFrom = Date.valueOf("20180101");
        Date dateTo = Date.valueOf("20181231");

        return trainingDao.calculateCostOfStudentTrainingsInCurrentYear(studentId, dateFrom, dateTo);
    }

    @Override
    @Transactional
    public int countAllStudentTrainingsInYear(Long studentId) throws NullPersonException {
        if (studentId == null || !studentDao.findById(studentId).isPresent()) {
            throw new NullPersonException("Cannot count costs od trainings for non-existent student!");
        }
        Date dateFrom = Date.valueOf("20180101");
        Date dateTo = Date.valueOf("20181231");

        return trainingDao.countAllStudentTrainingsInCurrentYear(studentId, dateFrom, dateTo);
    }

    @Transactional
    private boolean budgetValidation(double amount, int grade, int numberOfTrainings) {
        boolean result = false;
        if ((grade <= 3 && amount <= 15000 && numberOfTrainings <= 3) || (grade >= 4 && amount <= 50000)) {
            result = true;
        }
        return result;
    }

}
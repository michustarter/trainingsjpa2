package com.capgemini.service.impl;


import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.StudentDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.dao.TrainingDao;
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
import com.capgemini.util.*;
import javassist.compiler.ast.Stmnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
    public TrainingTO assignTrainerToTraining(TrainingTO trainingTO, TrainerTO trainerTO) throws NullTrainingException,
            NullTrainerException, TrainerIsAlreadyAssignedException {

        if (!trainingDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("This training does not exist!");
        }
        if (!trainerDao.findById(trainerTO.getId()).isPresent()) {
            throw new NullTrainerException("This trainer does not exist!");
        }

        TrainingEntity training = TrainingMapper.toEntity(trainingTO);
        TrainerEntity trainer = TrainerMapper.toEntity(trainerTO);

        if (training.getTrainers().contains(trainer)) {
            throw new TrainerIsAlreadyAssignedException("This trainer is already assigned to the training");
        }
        training.getTrainers().add(trainer);
        training = trainingDao.save(training);
        trainingTO = TrainingMapper.toTO(training);


        return trainingTO;
    }

    @Override
    public TrainingTO assignStudentToTraining(TrainingTO trainingTO, StudentTO studentTO) throws NullTrainingException,
            NullStudentException, StudentIsAlreadyAssignedException {

        if (!trainingDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("This training does not exist!");
        }
        if (!studentDao.findById(studentTO.getId()).isPresent()) {
            throw new NullStudentException("This student does not exist!");
        }

        TrainingEntity training = TrainingMapper.toEntity(trainingTO);
        StudentEntity student = StudentMapper.toEntity(studentTO);

        if (training.getTrainers().contains(student)) {
            throw new StudentIsAlreadyAssignedException("This student is already assigned to the training");
        }
        training.getStudents().add(student);
        training = trainingDao.save(training);
        trainingTO = TrainingMapper.toTO(training);


        return trainingTO;
    }

    @Override
    public TrainingTO updateTraining(TrainingTO trainingTO) throws NullTrainingException {
        if (trainingTO == null) {
            throw new NullTrainingException("Cannot update training with empty data!");
        }
        TrainingEntity trainingEntity = TrainingMapper.toEntity(trainingTO);
        trainingEntity = trainingDao.save(trainingEntity);
        trainingTO = TrainingMapper.toTO(trainingEntity);

        return trainingTO;
    }

    @Override
    public void deleteTraining(TrainingTO trainingTO) throws NullTrainingException {

        if (trainingTO.getId() == null || !trainerDao.findById(trainingTO.getId()).isPresent()) {
            throw new NullTrainingException("Cannot delete non-existent training!");
        }
        TrainingEntity trainingEntity = TrainingMapper.toEntity(trainingTO);
        trainingDao.delete(trainingEntity);
    }

    @Override
    public TrainingTO findTraining(Long trainingId) throws NullIdException {
        if (trainingId == null) {
            throw new NullIdException("Cannot find training by null id");
        }

        TrainingEntity trainingEntity = trainingDao.findById(trainingId).get();
        TrainingTO trainingTO = TrainingMapper.toTO(trainingEntity);

        return trainingTO;
    }

    @Override
    public List<TrainingTO> findTrainings() {

        List<TrainingTO> trainigs = TrainingMapper.map2TOs(trainingDao.findAll());
        return trainigs;
    }

    @Override
    public List<TrainerTO> findTrainers(TrainingTO trainingTO) throws NullTrainingException {
        if (trainingTO == null || trainingTO.getId() == null) {
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
    git commit -m "added new Exceptions and new methods implementations"

    @Override
    public List<StudentTO> findStudents(TrainingTO trainingTO) throws NullTrainingException {
        if (trainingTO == null || trainingTO.getId() == null) {
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
}

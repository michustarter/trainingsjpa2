package com.capgemini.service;

import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import com.capgemini.exceptions.*;

import java.sql.Date;
import java.util.List;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO trainingTO) throws NullTrainingException, TrainingAlreadyExistsException;

    TrainingTO assignTrainerToTraining(TrainingTO trainingTO, TrainerTO trainerTO) throws NullTrainingException,
            NullPersonException, TrainerIsAlreadyAssignedException, TrainerCannotBeAStudentException, NullPersonException;

    TrainingTO assignStudentToTraining(TrainingTO training, StudentTO studentTO) throws NullTrainingException,
            NullPersonException, StudentIsAlreadyAssignedException, TrainerCannotBeAStudentException, InvalidConditionsException, NullPersonException;

    TrainingTO updateTraining(TrainingTO trainingTO) throws NullTrainingException;

    void deleteTraining(TrainingTO trainingTO) throws NullTrainingException;

    TrainingTO findTraining(Long trainingId) throws NullIdException;

    List<TrainingTO> findTrainings();

    List<TrainerTO> findTrainers(TrainingTO trainingTO) throws NullTrainingException;

    List<StudentTO> findStudents(TrainingTO trainingTO) throws NullTrainingException;

    //b
    List<TrainingTO> searchTrainingsByKeyWord(String keyWord) throws NoKeyWordException;

    //c
    int sumHoursFromTrainerTrainingsInCurrentYear(Long trainerId) throws NullTrainingException;

    //d
    int countNumberOfEmployeeTrainingsInPeriod(Long studentId, Date dateFrom, Date dateTo) throws NullPersonException,
            InvalidOrderOfDatesException, NullPersonException;

    //e
    double calculateStudentCostsTrainings(Long studentId) throws NullPersonException, NullPersonException;

    //for assignStudentToTraining
    double calculateStudentCostsTrainingsInYear(Long studentId) throws NullPersonException, NullPersonException;

    //for assignStudentToTraining
    int countAllStudentTrainingsInYear(Long studentId) throws NullPersonException, NullPersonException;
}

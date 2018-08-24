package com.capgemini.service;

import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;
import com.capgemini.util.*;

import java.util.List;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO trainingTO) throws NullTrainingException, TrainingAlreadyExistsException;

    TrainingTO assignTrainerToTraining(TrainingTO trainingTO,TrainerTO trainerTO) throws NullTrainingException, NullTrainerException, TrainerIsAlreadyAssignedException;

    TrainingTO assignStudentToTraining(TrainingTO training, StudentTO studentTO) throws NullTrainingException, NullStudentException, StudentIsAlreadyAssignedException;

    TrainingTO updateTraining(TrainingTO trainingTO) throws NullTrainingException;

    void deleteTraining(TrainingTO trainingTO) throws NullTrainingException;

    TrainingTO findTraining(Long trainingId) throws NullIdException;

    List<TrainingTO> findTrainings();

    List<TrainerTO> findTrainers(TrainingTO trainingTO) throws NullTrainingException;

    List<StudentTO> findStudents(TrainingTO trainingTO) throws NullTrainingException;


}

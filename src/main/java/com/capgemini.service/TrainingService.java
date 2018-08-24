package com.capgemini.service;

import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

import java.util.List;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO trainingTO);

    TrainingTO assignTrainerToTraining(TrainingTO trainingTO,TrainerTO trainerTO);

    TrainingTO assignStudentToTraining(TrainingTO training, StudentTO studentTO);

    TrainingTO updateTraining(TrainingTO trainingTO);

    void deleteTraining(TrainingTO trainingTO);

    TrainingTO findTraining(Long trainingId);

    List<TrainingTO> findTrainings();

    List<TrainerTO> findTrainers(TrainingTO trainingTO);

    List<StudentTO> findStudents(TrainingTO trainingTO);


}

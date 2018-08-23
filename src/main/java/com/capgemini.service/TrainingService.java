package com.capgemini.service;

import com.capgemini.types.StudentTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.types.TrainingTO;

import java.util.List;

public interface TrainingService {

    TrainingTO addTraining(TrainingTO training);

    TrainingTO addTrainerToTraining(TrainingTO training,TrainerTO trainer);

    TrainingTO addStudentToTraining(TrainingTO training, StudentTO student);

    TrainingTO updateTraining(TrainingTO training);

    void deleteTraining(TrainingTO trainingTO);

    TrainingTO findTraining(Long trainingId);

    List<TrainingTO> findTrainings();

    List<TrainerTO> findTrainers(TrainingTO training);

    List<TrainerTO> findStudents(TrainingTO training);


}

package com.capgemini.service;

import com.capgemini.mappers.TrainingMapper;
import com.capgemini.types.*;

public interface TrainerService {

    TrainerTO addTrainer(EmployeeTO employeeTO, TrainerTO trainerTO);

    void deleteTrainer(TrainerTO trainerTO);

}

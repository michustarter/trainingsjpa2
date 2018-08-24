package com.capgemini.service;

import com.capgemini.mappers.TrainingMapper;
import com.capgemini.types.*;
import com.capgemini.util.IncorrectTrainerException;
import com.capgemini.util.NullPersonException;
import com.capgemini.util.TrainerAlreadyExistsException;

public interface TrainerService {

    TrainerTO addTrainer(EmployeeTO employeeTO) throws NullPersonException, TrainerAlreadyExistsException;

    TrainerTO addExternalTrainer(TrainerTO trainerTO) throws NullPersonException, IncorrectTrainerException;

    void deleteTrainer(TrainerTO trainerTO) throws NullPersonException;

    TrainerTO updateTrainer(TrainerTO trainerTO) throws NullPersonException;

}

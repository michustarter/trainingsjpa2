package com.capgemini.service;

import com.capgemini.types.*;
import com.capgemini.exceptions.IncorrectTrainerException;
import com.capgemini.exceptions.NullPersonException;
import com.capgemini.exceptions.TrainerAlreadyExistsException;

public interface TrainerService {

    TrainerTO addTrainer(EmployeeTO employeeTO) throws NullPersonException, TrainerAlreadyExistsException;

    TrainerTO addExternalTrainer(TrainerTO trainerTO) throws NullPersonException, IncorrectTrainerException;

    void deleteTrainer(TrainerTO trainerTO) throws NullPersonException;

    TrainerTO updateTrainer(TrainerTO trainerTO) throws NullPersonException;

    TrainerTO findTrainer(Long id);

}

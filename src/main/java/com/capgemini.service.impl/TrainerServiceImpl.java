package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.service.TrainerService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.util.EmployeeAlreadyExistsException;
import com.capgemini.util.IncorrectTrainerException;
import com.capgemini.util.NullPersonException;
import com.capgemini.util.TrainerAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    private EmployeeDao employeeDao;
    private TrainerDao trainerDao;

    @Autowired
    public TrainerServiceImpl(EmployeeDao employeeDao, TrainerDao trainerDao) {
        this.employeeDao = employeeDao;
        this.trainerDao = trainerDao;
    }

    @Override
    public TrainerTO addTrainer(EmployeeTO employeeTO) throws NullPersonException, TrainerAlreadyExistsException {
        //zakładam, że employee istnieje (jeśli nie istnieje, to rzucam wyjątek z info ze musze najpierw dodac employeee

        if (employeeTO == null || employeeTO.getId()==null  || !employeeDao.findById(employeeTO.getId()).isPresent()) {
            throw new NullPersonException("Cannot create trainer if employee does not exist in database!");
        }
       if (employeeTO.getTrainerId()!= null) {
           throw new TrainerAlreadyExistsException("This trainer already exists in database!");
       }
        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setFirstName(employeeTO.getFirstName());
        trainerEntity.setLastName(employeeTO.getLastName());
        trainerEntity.setPosition(employeeTO.getPosition());
        trainerEntity.setCompanyName("");

        trainerEntity = trainerDao.save(trainerEntity);

        TrainerTO trainerTO = TrainerMapper.toTO(trainerEntity);

        return trainerTO;
    }

    @Override
    public TrainerTO addExternalTrainer(TrainerTO trainerTO) throws NullPersonException, IncorrectTrainerException {

        if (trainerTO == null) {
            throw new NullPersonException("Cannot add external trainer to database with empty data!");
        }
        if (trainerTO.getCompanyName().length() == 0) {
            throw new IncorrectTrainerException("External trainer must have a company name!");
        }

        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainerTO);
        trainerEntity = trainerDao.save(trainerEntity);

        trainerTO = TrainerMapper.toTO(trainerEntity);

        return trainerTO;
    }

    @Override
    public void deleteTrainer(TrainerTO trainerTO) throws NullPersonException { /*jesli companyName==0 to juz na bank on nalezy do jakiegos Employee
        bo musialem dodac wczesniej go metodą addTrainer, nie patrzec na mozl stworzenia samym konstruktorem bo to nie jest ok w tym przypadku */

        if (trainerTO == null || trainerTO.getId()==null || !trainerDao.findById(trainerTO.getId()).isPresent()) {
            throw new NullPersonException("Cannot delete non-existent trainer!");
        }

        if (trainerTO.getCompanyName().length() == 0) {

            List<EmployeeEntity> employees = employeeDao.findAll();
            EmployeeEntity employeeEntity = employees.stream()
                    .filter(e -> e.getTrainer().getId() == trainerTO.getId())
                    .collect(Collectors.toList())
                    .get(0);
            employeeEntity.setTrainer(null);
            employeeDao.save(employeeEntity);
        }
        trainerDao.delete(TrainerMapper.toEntity(trainerTO));
    }

    @Override
    public TrainerTO updateTrainer(TrainerTO trainerTO) throws NullPersonException {
        if (trainerTO == null) { //nie spr czy studentTO istnieje w bazie danych, jesli nie istineje to go po prostu dodam
            throw new NullPersonException("Cannot update student with empty data!");
        }
        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainerTO);
        trainerEntity = trainerDao.save(trainerEntity);
        trainerTO = TrainerMapper.toTO(trainerEntity);


        List<EmployeeEntity> employees = employeeDao.findAll();
        TrainerTO finalTrainerTO = trainerTO;
        EmployeeEntity employeeEntity = employees.stream()
                .filter(e -> e.getTrainer().getId() == finalTrainerTO.getId())
                .collect(Collectors.toList())
                .get(0);
        employeeEntity.setTrainer(trainerEntity);
        employeeDao.save(employeeEntity);

        return trainerTO;
    }
}


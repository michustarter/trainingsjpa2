package com.capgemini.service.impl;


import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.mappers.TrainingMapper;
import com.capgemini.service.TrainerService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.util.NullPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

@Service
public class TrainerServiceImpl implements TrainerService {

    private EmployeeDao employeeDao;
    private TrainerDao trainerDao;

    @Autowired
    public TrainerServiceImpl(EmployeeDao employeeDao, TrainerDao trainerDao) {
        this.employeeDao=employeeDao;
        this.trainerDao=trainerDao;
    }

    @Override
    public TrainerTO addTrainer(EmployeeTO employeeTO, TrainerTO trainerTO) {
        //zakładam, że employee istnieje (jeśli nie istnieje, to rzucam wyjątek z info ze musze najpierw dodac employeee
        if(!employeeDao.findById(employeeTO.getId()).isPresent()) {
            throw new NullPersonException("Cannot add new trainer to non-extistent employee! " +
                    "First you need to add an employee to the database");
        }
        if(trainerTO==null) {
            throw new NullPersonException("Cannot add trainer with empty data!");
        }
        TrainerEntity trainerEntity=new TrainerEntity();
        trainerEntity= TrainerMapper.toEntity(trainerTO);
        trainerEntity=trainerDao.save(trainerEntity);

        if(trainerTO.getCompanyName()==null || trainerTO.getCompanyName().isEmpty()) {
            //jesli trener zewnętrzny, nie przypisuje do employeera wtedy

            EmployeeEntity employeeEntity= EmployeeMapper.toEntity(employeeTO);
            employeeEntity.setTrainer(trainerEntity);

        }
        return trainerTO;
    }

    @Override
    public void deleteTrainer(TrainerTO trainerTO) {

        if(trainerTO==null) {
            throw new NullPersonException("Cannot delete non-existent trainer!");
        }
        if(trainerTO.getCompanyName()==null || !trainerTO.getCompanyName().isEmpty()) {
            List<EmployeeEntity> employees= new ArrayList<>();
            employees.addAll(employeeDao.findAll());

            EmployeeEntity employeeEntity=new EmployeeEntity();
            employeeEntity=employees.stream().filter(e-> e.getTrainer().getId()==trainerTO.getId()).collect(Collectors.)
          /*  znalezienie encji pracownika po ID trenera do usuniecia - czy to jednak ma ta kaskada załatwić?
            ze jak usune trenera to pole trenera w danym
                    employee ktory mial to pole = trainerTO bedzie rowne null?
*/
         //   EmployeeEntity employeeEntity= EmployeeMapper.toEntity(employeeTO);
         //   employeeEntity.setTrainer(trainerEntity);

        }



    }
}

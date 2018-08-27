package com.capgemini.service.impl;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.TrainerDao;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.TrainerMapper;
import com.capgemini.mappers.TrainingMapper;
import com.capgemini.service.TrainerService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.TrainerTO;
import com.capgemini.exceptions.IncorrectTrainerException;
import com.capgemini.exceptions.NullPersonException;
import com.capgemini.exceptions.TrainerAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Klasa serwisowa agregująca logikę biznesową dla trenera
 */
@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private EmployeeDao employeeDao;
    private TrainerDao trainerDao;

    @Autowired
    public TrainerServiceImpl(EmployeeDao employeeDao, TrainerDao trainerDao) {
        this.employeeDao = employeeDao;
        this.trainerDao = trainerDao;
    }

    /**
     * Metoda dodająca trenewa wewnętrznego
     *
     * @param employeeTO
     * @return
     * @throws NullPersonException           - gdy dodany trener jest pustym obiektem
     * @throws TrainerAlreadyExistsException - gdy dodawany trener znajduje się juz w bazie danych
     */
    @Override
    @Transactional(readOnly = false)
    public TrainerTO addTrainer(EmployeeTO employeeTO) throws NullPersonException, TrainerAlreadyExistsException {

        if (employeeTO == null) {
            throw new NullPersonException("Cannot create trainer if employee does not exist in database!");
        }
        if (employeeTO.getTrainerId() != null) {
            throw new TrainerAlreadyExistsException("This trainer already exists in database!");
        }
        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setFirstName(employeeTO.getFirstName());
        trainerEntity.setLastName(employeeTO.getLastName());
        trainerEntity.setPosition(employeeTO.getPosition());
        trainerEntity.setCompanyName("");

        trainerEntity = trainerDao.save(trainerEntity);


        EmployeeEntity employeeEntity = EmployeeMapper.toEntity(employeeTO);
        employeeEntity.setTrainer(trainerEntity);
        employeeDao.save(employeeEntity);

        TrainerTO trainerTO = TrainerMapper.toTO(trainerEntity);

        return trainerTO;
    }

    /**
     * Metoda dodająca trenera zewnętrznego
     *
     * @param trainerTO
     * @return
     * @throws NullPersonException       - gdy przekazany trener do dodania jest pustym obiektem
     * @throws IncorrectTrainerException - gdy firma zewnętrzna nie posiada nazwy (jesli nie posiada nazwy, byłby to trener wewnętrzny)
     */
    @Override
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public void deleteTrainer(TrainerTO trainerTO) throws NullPersonException {

        if (trainerTO == null) {
            throw new NullPersonException("Cannot delete non-existent trainer!");
        }
        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainerTO);

        if (trainerTO.getCompanyName().length() == 0) {
            EmployeeEntity employeeEntity = employeeDao.findByTrainer(trainerEntity);
            employeeEntity.setTrainer(null);
            employeeDao.save(employeeEntity);

        }
        trainerDao.delete(trainerEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public TrainerTO updateTrainer(TrainerTO trainerTO) throws NullPersonException {
        if (trainerTO == null) {
            throw new NullPersonException("Cannot update student with empty data!");
        }

        TrainerEntity trainerEntity = TrainerMapper.toEntity(trainerTO);
        trainerEntity = trainerDao.save(trainerEntity);
        trainerTO = TrainerMapper.toTO(trainerEntity);

        if (employeeDao.findByTrainer(trainerEntity) != null) {
            EmployeeEntity employeeEntity = employeeDao.findByTrainer(trainerEntity);
            employeeEntity.setTrainer(trainerEntity);
            employeeDao.save(employeeEntity);
        }

        return trainerTO;
    }

    @Override
    public TrainerTO findTrainer(Long id) {
        Optional<TrainerEntity> found = trainerDao.findById(id);
        boolean check = found.isPresent();
        if (check) {
            return TrainerMapper.toTO(found.get());
        }
        return null;
    }
}


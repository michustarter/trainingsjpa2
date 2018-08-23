package com.capgemini.dao;

import com.capgemini.domain.TrainerEntity;
import com.capgemini.types.TrainerTO;
import org.springframework.data.repository.CrudRepository;

public interface TrainerDao extends CrudRepository<TrainerEntity, Long> {


}
package com.capgemini.dao;

import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.CriteriaTrainingTO;

import java.util.List;

public interface TrainingQueryDao {

    List<TrainingEntity> findTrainingsByCriteria(CriteriaTrainingTO criteria);
}

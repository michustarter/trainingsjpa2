package com.capgemini.dao.impl;

import com.capgemini.dao.TrainingQueryDao;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.CriteriaTrainingTO;

import java.sql.Date;


import javax.persistence.TypedQuery;
import java.util.List;

public class TrainingDaoImpl extends AbstractDao<TrainingEntity, Long> implements TrainingQueryDao {

    @Override
    public List<TrainingEntity> findTrainingsByCriteria(CriteriaTrainingTO criteria) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select t from TrainingEntity t where ");
        boolean possibilityAppendQueryByAnd = false;

        String title = criteria.getTitle();
        String type = criteria.getType();
        Date date = criteria.getDate();
        Double amountFrom = criteria.getAmountFrom();
        Double amountTo = criteria.getAmountTo();

        if (title != null) {
            queryBuilder.append(" t.title = :title ");
            possibilityAppendQueryByAnd = true;
        }

        if (type != null) {
            if (possibilityAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append(" t.type = :type ");
            possibilityAppendQueryByAnd = true;
        }

        if (date != null) {
            if (possibilityAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append(" :date between t.dateFrom and t.dateTo ");
            possibilityAppendQueryByAnd = true;
        }

        if (amountFrom != null) {
            if (possibilityAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append(" :amountFrom >= t.amount");
            possibilityAppendQueryByAnd = true;
        }

        if (amountTo != null) {
            if (possibilityAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append(" :amountTO <= t.amount");
        }

        TypedQuery<TrainingEntity> query = entityManager.createQuery(queryBuilder.toString(), TrainingEntity.class);

        if (title != null) {
            query.setParameter("title", criteria.getTitle());
        }
        if (type != null) {
            query.setParameter("type", criteria.getType());
        }
        if (date != null) {
            query.setParameter("date", criteria.getDate());
        }
        if (amountFrom != null) {
            query.setParameter("amountFrom", criteria.getAmountFrom());
        }
        if (amountTo != null) {
            query.setParameter("amountTo", criteria.getAmountTo());
        }

        return query.getResultList();
    }
}

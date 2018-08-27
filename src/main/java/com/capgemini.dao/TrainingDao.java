package com.capgemini.dao;

import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface TrainingDao extends CrudRepository<TrainingEntity, Long> {

    List<TrainingEntity> findAll();

    List<TrainerEntity> findTrainersById(Long trainerId);

    //b
    @Query("select te from TrainingEntity te where :keyWord member of te.keyWords")
    List<TrainingEntity> findByKeyWord(@Param("keyWord") String keyWord);

    //c
    @Query("select sum(te.numberOfHours) from TrainingEntity te join te.trainers tt on tt.id = :trainer "
            + " where te.dateFrom between :dateFrom and :dateTo ")
    int countHoursFromAllTrainingsLeadedByTrainerInCurrentYear(@Param("trainer") Long id,
                                                               @Param("dateFrom") Date dateFrom,
                                                               @Param("dateTo") Date dateTo);

    //d
    @Query("select count(te.id) from TrainingEntity te join EmployeeEntity ee on ee.id = :employee " +
            " where te.dateFrom between :dateFrom and :dateTo and (ee.trainer member of te.trainers " +
            " or ee.student member of te.students)")
    int countAllEmployeeTrainingsInGivenTimePeriod(@Param("employee") Long id,
                                                   @Param("dateFrom") Date dateFrom,
                                                   @Param("dateTo") Date dateTo);

    //e
    @Query("select sum(te.amount) from TrainingEntity te join te.students ts on ts.id = :student ")
    double calculateCostOfStudentTrainings(@Param("student") Long id);


    //for assignStudentToTraining
    @Query("select sum(te.amount) from TrainingEntity te join te.students s on s.id = :student  "
            + " where te.dateFrom between :dateFrom and :dateTo")
    double calculateCostOfStudentTrainingsInCurrentYear(@Param("student") Long id,
                                                        @Param("dateFrom") Date dateFrom,
                                                        @Param("dateTo") Date dateTo);

    //for assignStudentToTraining
    @Query("select count(te.id) from TrainingEntity te join te.students s on s.id = :student "
            +  " where te.dateFrom between :dateFrom and :dateTo ")
    int countAllStudentTrainingsInCurrentYear(@Param("student") Long id,
                                             @Param("dateFrom") Date dateFrom,
                                             @Param("dateTo") Date dateTo);
}
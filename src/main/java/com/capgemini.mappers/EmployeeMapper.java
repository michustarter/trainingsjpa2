package com.capgemini.mappers;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.EmployeeTO.EmployeeTOBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class EmployeeMapper {
    public static EmployeeTO toTO(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) {
            return null;
        }

        Long trainerId = null;
        Long studentId = null;

        if (employeeEntity.getTrainer() != null) {
            trainerId = employeeEntity.getTrainer().getId();
        }
        if (employeeEntity.getStudent()!=null) {
            studentId=employeeEntity.getStudent().getId();
        }
        return new EmployeeTOBuilder()
                .withVersion(employeeEntity.getVersion())
                .withId(employeeEntity.getId())
                .withFirstName(employeeEntity.getFirstName())
                .withLastName(employeeEntity.getLastName())
                .withPosition(employeeEntity.getPosition())
                .withTrainerId(trainerId)
                .withStudentId(studentId)
                .build();

    }

    public static EmployeeEntity toEntity(EmployeeTO employeeTO) {
        if (employeeTO == null) {
            return null;
        }

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setVersion(employeeTO.getVersion());
        employeeEntity.setId(employeeTO.getId());
        employeeEntity.setFirstName(employeeTO.getFirstName());
        employeeEntity.setLastName(employeeTO.getLastName());
        employeeEntity.setPosition(employeeTO.getPosition());
        // nie daje studentsId bo nie mozna dao tutaj uzywać
        return employeeEntity;

    }

    public static List<EmployeeTO> map2TOs(List<EmployeeEntity> employeeEntities) {
        if (employeeEntities != null) {
            return employeeEntities.stream().map(EmployeeMapper::toTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<EmployeeEntity> map2Entities(List<EmployeeTO> employeeTOs) {
        if (employeeTOs != null) {
            return employeeTOs.stream().map(EmployeeMapper::toEntity).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
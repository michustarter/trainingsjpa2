package com.capgemini.mappers;

import com.capgemini.domain.StudentEntity;
import com.capgemini.types.StudentTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentTO toTO(StudentEntity studentEntity) {
        if (studentEntity == null) {
            return null;
        }

        ///
        Long bossId = null;

        if (studentEntity.getBoss() != null) {
            bossId = studentEntity.getBoss().getId();
        }

        ///
        return new StudentTO.StudentTOBuilder()
                .withVersion(studentEntity.getVersion())
                .withId(studentEntity.getId())
                .withBossId(bossId)
                .withGrade(studentEntity.getGrade())
                .withFirstName(studentEntity.getFirstName())
                .withLastName(studentEntity.getLastName())
                .withPosition(studentEntity.getPosition())
                .build();

    }

    public static StudentEntity toEntity(StudentTO studentTO) {
        if (studentTO == null) {
            return null;
        }



        StudentEntity studentEntity=  new StudentEntity();

        studentEntity.setVersion(studentTO.getVersion());
        studentEntity.setId(studentTO.getId());
       // studentEntity.setBoss(studentTO.getBossId().);  pamietac w testach !! bossa recznie ustawic !! i poodbnie inne przypadki z tymi id !! findById !
        studentEntity.setGrade(studentTO.getGrade());
        studentEntity.setFirstName(studentTO.getFirstName());
        studentEntity.setLastName(studentTO.getLastName());
        studentEntity.setPosition(studentTO.getPosition());

        return  studentEntity;
    }

    public static List<StudentTO> map2TOs(List<StudentEntity> studenteEntities) {
        if (studenteEntities != null) {
            return studenteEntities.stream().map(StudentMapper::toTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<StudentEntity> map2Entities(List<StudentTO> studentTOS) {
        if (studentTOS != null) {
            return studentTOS.stream().map(StudentMapper::toEntity).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
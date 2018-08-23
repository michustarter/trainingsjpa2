package com.capgemini.mappers;

import com.capgemini.domain.StudentEntity;
import com.capgemini.domain.TrainerEntity;
import com.capgemini.domain.TrainingEntity;
import com.capgemini.types.TrainingTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingMapper {

    public static TrainingTO toTO(TrainingEntity trainingEntity) {
        if (trainingEntity == null) {
            return null;
        }


        return new TrainingTO.TrainingTOBuilder()
                .withVersion(trainingEntity.getVersion())
                .withId(trainingEntity.getId())
                .withTitle(trainingEntity.getTitle())
                .withType(trainingEntity.getType())
                .withKind(trainingEntity.getKind())
                .withDateFrom(trainingEntity.getDateFrom())
                .withDateTo(trainingEntity.getDateTo())
                .withNumberOfHours(trainingEntity.getNumberOfHours())
                .withKeyWords(trainingEntity.getKeyWords())
                .withAmount(trainingEntity.getAmount())
                .withStudentsId(trainingEntity.getStudents()
                        .stream()
                        .map(StudentEntity::getId)
                        .collect(Collectors.toList()))
                .withTrainersId(trainingEntity.getTrainers()
                        .stream()
                        .map(TrainerEntity::getId)
                        .collect(Collectors.toList()))
                .build();


    }

    public static TrainingEntity toEntity(TrainingTO trainingTO) {
        if (trainingTO == null) {
            return null;
        }
        TrainingEntity trainingEntity = new TrainingEntity();

        trainingEntity.setVersion(trainingTO.getVersion());
        trainingEntity.setId(trainingTO.getId());
        trainingEntity.setTitle(trainingTO.getTitle());
        trainingEntity.setType(trainingTO.getType());
        trainingEntity.setKind(trainingTO.getKind());
        trainingEntity.setDateFrom(trainingTO.getDateFrom());
        trainingEntity.setDateTo(trainingTO.getDateTo());
        trainingEntity.setNumberOfHours(trainingTO.getNumberOfHours());
        trainingEntity.setKeyWords(trainingTO.getKeyWords());
        trainingEntity.setAmount(trainingTO.getAmount());
        //nie daje  setStudents bo nie moge tu przekonwertowac z IDków na obiekty Entity

        return trainingEntity;

    }

    public static List<TrainingTO> map2TOs(List<TrainingEntity> trainingEntities) {
        if (trainingEntities != null) {
            return trainingEntities.stream().map(TrainingMapper::toTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<TrainingEntity> map2Entities(List<TrainingTO> trainingTOs) {
        if (trainingTOs != null) {
            return trainingTOs.stream().map(TrainingMapper::toEntity).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
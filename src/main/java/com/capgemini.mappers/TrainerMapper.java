package com.capgemini.mappers;

import com.capgemini.domain.TrainerEntity;
import com.capgemini.types.TrainerTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainerMapper {

    public static TrainerTO toTO(TrainerEntity trainerEntity) {
        if (trainerEntity == null) {
            return null;
        }
        return new TrainerTO.TrainerTOBuilder()
                .withVersion(trainerEntity.getVersion())
                .withId(trainerEntity.getId())
                .withFirstName(trainerEntity.getFirstName())
                .withLastName(trainerEntity.getLastName())
                .withPosition(trainerEntity.getPosition())
                .withCompanyName(trainerEntity.getCompanyName())
                .build();

    }

    public static TrainerEntity toEntity(TrainerTO trainerTO) {
        if (trainerTO == null) {
            return null;
        }
        TrainerEntity trainerEntity = new TrainerEntity();

        trainerEntity.setVersion(trainerTO.getVersion());
        trainerEntity.setId(trainerTO.getId());
        trainerEntity.setFirstName(trainerTO.getFirstName());
        trainerEntity.setLastName(trainerTO.getLastName());
        trainerEntity.setPosition(trainerTO.getPosition());
        trainerEntity.setCompanyName(trainerTO.getCompanyName());

        return trainerEntity;
    }

}

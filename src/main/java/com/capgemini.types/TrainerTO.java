package com.capgemini.types;

import java.util.ArrayList;
import java.util.List;

public class TrainerTO {

    private Long id;
    private String firstName;
    private  String lastName;
    private String position;
    private String companyName;
    private List<Long> trainings;

    public TrainerTO(Long id, String firstName, String lastName, String position, String companyName, List<Long> trainings) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.companyName = companyName;
        this.trainings = trainings;
    }

    public static class TrainerTOBuilder{
        private Long id;
        private String firstName;
        private String lastName;
        private String position;
        private String companyName;
        private List<Long> trainings;

        public TrainerTOBuilder(Long id, String firstName, String lastName, String position, String companyName, List<Long> trainings) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.position = position;
            this.companyName = companyName;
            this.trainings = trainings;
        }

        public TrainerTOBuilder(){
            this.trainings=new ArrayList<>();
        }

        public TrainerTOBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public TrainerTOBuilder withFirstName(String firstName){
            this.firstName=firstName;
            return this;

        }
        public TrainerTOBuilder withLastName(String lastName){
            this.lastName=lastName;
            return this;

        }
        public TrainerTOBuilder withPosition(String position){
            this.position=position;
            return this;

        }
        public TrainerTOBuilder withCompanyName(String companyName){
            this.companyName=companyName;
            return this;

        }



    }
}

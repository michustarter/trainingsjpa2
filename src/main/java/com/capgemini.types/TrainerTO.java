package com.capgemini.types;

import java.util.Objects;

public class TrainerTO {

    private int version;
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String companyName;


    public TrainerTO(int version, Long id, String firstName, String lastName,
                     String position, String companyName) {

        this.version = version;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.companyName = companyName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static class TrainerTOBuilder {

        private int version;
        private Long id;
        private String firstName;
        private String lastName;
        private String position;
        private String companyName;

        public TrainerTOBuilder() {
            super();
        }

        public TrainerTOBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public TrainerTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TrainerTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TrainerTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TrainerTOBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public TrainerTOBuilder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }


        public TrainerTO build() {
            checkBeforeBuild(firstName, lastName, position, companyName);
            return new TrainerTO(version, id, firstName, lastName, position, companyName);
        }

        private void checkBeforeBuild(String firstName, String lastName, String position, String companyName) {
            if (firstName == null || firstName.isEmpty() || lastName == null
                    || lastName.isEmpty() || position == null ||
                    position.isEmpty() || companyName == null) { //companyName.isEmpty - true dla trenera wewn
                throw new RuntimeException("Incorrect trainer to be created");
            }

        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainerTO trainerTO = (TrainerTO) o;
        return version == trainerTO.version &&
                Objects.equals(id, trainerTO.id) &&
                Objects.equals(firstName, trainerTO.firstName) &&
                Objects.equals(lastName, trainerTO.lastName) &&
                Objects.equals(position, trainerTO.position) &&
                Objects.equals(companyName, trainerTO.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, firstName, lastName, position, companyName);
    }
}
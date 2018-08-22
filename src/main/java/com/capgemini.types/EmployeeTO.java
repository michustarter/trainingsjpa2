package com.capgemini.types;

import java.util.List;

public class EmployeeTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private List<Long> trainers;
    private List<Long> students;

    public EmployeeTO(Long id, String firstName, String lastName,
                      String position,List<Long> trainers,List<Long> students) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.trainers=trainers;
        this.students=students;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Long> getTrainers() {
        return trainers;
    }

    public List<Long> getStudents() {
        return students;
    }

    public String getPosition() { return position; }

    public static class EmployeeTOBuilder {

        private Long id;
        private String firstName;
        private String lastName;
        private String position;
        private List<Long> trainers;
        private List<Long> students;

        public EmployeeTOBuilder() {
            super();
        }

        public EmployeeTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EmployeeTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeTOBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public EmployeeTO build() {
            checkBeforeBuild(firstName, lastName,position);
            return new EmployeeTO(id, firstName, lastName,  position, trainers,students );
        }

        private void checkBeforeBuild(String firstName, String lastName, String position) {
            if (firstName == null || lastName == null || position == null) {
                throw new RuntimeException("Incorrect employee to be created");
            }

        }

    }
}
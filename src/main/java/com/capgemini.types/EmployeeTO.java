package com.capgemini.types;

import java.util.Objects;

public class EmployeeTO {

    private int version;
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private Long trainerId;
    private Long studentId;

    public EmployeeTO() {
    }

    public EmployeeTO(int version, Long id, String firstName, String lastName,
                      String position, Long trainerId, Long studentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.trainerId = trainerId;
        this.studentId = studentId;
        this.version = version;
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

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public static class EmployeeTOBuilder {

        private int version;
        private Long id;
        private String firstName;
        private String lastName;
        private String position;
        private Long trainerId;
        private Long studentId;

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

        public EmployeeTOBuilder withTrainerId(Long trainerId) {
            this.trainerId = trainerId;
            return this;
        }

        public EmployeeTOBuilder withStudentId(Long studentId) {
            this.studentId = studentId;
            return this;
        }

        public EmployeeTOBuilder withVersion(int version) {
            this.version = version;
            return this;
        }


        public EmployeeTO build() {
            checkBeforeBuild(firstName, lastName, position);
            return new EmployeeTO(version, id, firstName, lastName, position, trainerId, studentId);
        }

        private void checkBeforeBuild(String firstName, String lastName, String position) {
            if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty() || position == null || position.isEmpty()) {
                throw new RuntimeException("Incorrect employee to be created");
            }

        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTO that = (EmployeeTO) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(position, that.position) &&
                Objects.equals(trainerId, that.trainerId) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, firstName, lastName, position, trainerId, studentId);
    }
}
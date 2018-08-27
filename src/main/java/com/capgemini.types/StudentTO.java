package com.capgemini.types;

import java.util.Objects;

public class StudentTO {

    private int version;
    private int grade;
    private Long id;
    private Long bossId;
    private String firstName;
    private String lastName;
    private String position;

    public StudentTO() {
    }

    public StudentTO(int version, Long id, String firstName, String lastName,
                     String position, Long bossId, int grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.version = version;
        this.bossId = bossId;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public Long getBossId() {
        return bossId;
    }

    public int getGrade() {
        return grade;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public static class StudentTOBuilder {

        private int version;
        private int grade;
        private Long id;
        private Long bossId;
        private String firstName;
        private String lastName;
        private String position;

        public StudentTOBuilder() {
            super();
        }

        public StudentTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public StudentTOBuilder withBossId(Long bossId) {
            this.bossId = bossId;
            return this;
        }

        public StudentTOBuilder withGrade(int grade) {
            this.grade = grade;
            return this;
        }

        public StudentTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentTOBuilder withPosition(String position) {
            this.position = position;
            return this;
        }

        public StudentTOBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public StudentTO build() {
            checkBeforeBuild(firstName, lastName, position, grade, bossId);
            return new StudentTO(version, id, firstName, lastName, position, bossId, grade);
        }

        private void checkBeforeBuild(String firstName, String lastName, String position, int grade, Long bossId) {
            if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()
                    || position == null || position.isEmpty() ||
                    grade < 1 || grade > 5 || bossId == null) {
                throw new RuntimeException("Incorrect student to be created");
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentTO studentTO = (StudentTO) o;
        return version == studentTO.version &&
                grade == studentTO.grade &&
                Objects.equals(id, studentTO.id) &&
                Objects.equals(bossId, studentTO.bossId) &&
                Objects.equals(firstName, studentTO.firstName) &&
                Objects.equals(lastName, studentTO.lastName) &&
                Objects.equals(position, studentTO.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, grade, id, bossId, firstName, lastName, position);
    }
}
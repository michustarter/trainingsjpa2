package com.capgemini.types;

public class StudentTO {

    private int version;
    private Long id;
    private Long bossId;
    private int grade;
    private String firstName;
    private String lastName;
    private String position;

    public StudentTO() {
    }

    public StudentTO(int version,Long id,String firstName, String lastName,
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

    public static class StudentTOBuilder {

        private int version;
        private Long id;
        private Long bossId;
        private int grade;
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
            checkBeforeBuild(grade, bossId);
            return new StudentTO(version,id, firstName, lastName,  position, bossId, grade);
        }

        private void checkBeforeBuild(int grade, Long bossId) {
            if (grade == 0 || bossId == null) {
                throw new RuntimeException("Incorrect student to be created");
            }

        }


    }
}
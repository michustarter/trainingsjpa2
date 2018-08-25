package com.capgemini.types;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrainingTO {

    private int version;
    private Long id;
    private String title;
    private String type;
    private String kind;
    private Date dateFrom;
    private Date dateTo;
    private int numberOfHours;
    private double amount;
    private List<String> keyWords;
    private List<Long> studentsId;
    private List<Long> trainersId;

    public TrainingTO(int version, Long id, String title, String type, String kind, Date dateFrom, Date dateTo,
                      int numberOfHours, List<String> keyWords, double amount,
                      List<Long> studentsId, List<Long> trainersId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.kind = kind;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numberOfHours = numberOfHours;
        this.keyWords = keyWords;
        this.amount = amount;
        this.studentsId = studentsId;
        this.trainersId = trainersId;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Long> getStudentsId() {
        return studentsId;
    }

    public void setStudentsId(List<Long> studentsId) {
        this.studentsId = studentsId;
    }

    public List<Long> getTrainersId() {
        return trainersId;
    }

    public void setTrainersId(List<Long> trainersId) {
        this.trainersId = trainersId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static class TrainingTOBuilder {

        private int version;
        private Long id;
        private String title;
        private String type;
        private String kind;
        private Date dateFrom;
        private Date dateTo;
        private int numberOfHours;
        private double amount;
        private List<String> keyWords;
        private List<Long> studentsId;
        private List<Long> trainersId;


        public TrainingTOBuilder() {
            super();
        }

        public TrainingTOBuilder withId(Long id) {
            this.id = id;
            return this;

        }

        public TrainingTOBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TrainingTOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public TrainingTOBuilder withKind(String kind) {
            this.kind = kind;
            return this;
        }

        public TrainingTOBuilder withDateFrom(String date) {
            this.dateFrom = Date.valueOf(date);
            return this;

        }

        public TrainingTOBuilder withDateTo(String date) {
            this.dateTo = Date.valueOf(date);
            return this;
        }

        public TrainingTOBuilder withNumberOfHours(int numberOfHours) {
            this.numberOfHours = numberOfHours;
            return this;
        }

        public TrainingTOBuilder withKeyWords(List<String> keys) {
            this.keyWords = keys;
            return this;
        }

        public TrainingTOBuilder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public TrainingTOBuilder withStudentsId(List<Long> studentsId) {
            this.studentsId = studentsId;
            return this;
        }

        public TrainingTOBuilder withTrainersId(List<Long> trainersId) {
            this.trainersId = trainersId;
            return this;
        }

        public TrainingTOBuilder withVersion(int version) {
            this.version = version;
            return this;

        }


        public TrainingTO build() {
            checkBeforeBuild(title, type, kind, dateFrom, dateTo, numberOfHours, keyWords, amount);
            return new TrainingTO(version, id, title, type, kind, dateFrom, dateTo, numberOfHours,
                    keyWords, amount, studentsId, trainersId);
        }

        private void checkBeforeBuild(String title, String type, String kind, Date dateFrom, Date dateTo,
                                      Integer numberOfHours, List<String> keyWords, Double amount) {
            if (title == null || title.isEmpty() || type == null || type.isEmpty() || kind == null
                    || kind.isEmpty() || dateFrom == null || dateTo == null || numberOfHours == null
                    || keyWords == null || keyWords.isEmpty() || amount == null) {
                throw new RuntimeException("Incorrect training to be created");
            }

        }
    }

}
package com.capgemini.types;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TrainingTO {

    private Long id;
    private String title;
    private String type;
    private String kind;
    private Date dateFrom;
    private Date dateTo;
    private int numeberOfHours;
    private List<String> keyWords;
    private double amount;
    private List<Long> students;

    public TrainingTO(Long id, String title, String type, String kind, Date dateFrom, Date dateTo,
                      int numeberOfHours, List<String> keyWords, double amount, List<Long> students) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.kind = kind;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numeberOfHours = numeberOfHours;
        this.keyWords = keyWords;
        this.amount = amount;
        this.students = students;
    }

    public static class TrainingTOBuilder {

        private Long id;
        private String title;
        private String type;
        private String kind;
        private Date dateFrom;
        private Date dateTo;
        private int numeberOfHours;
        private List<String> keyWords;
        private double amount;
        private List<Long> students;

        public TrainingTOBuilder() {
            super();
        }


        public TrainingTOBuilder withId(long id) {
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

        public TrainingTOBuilder withNumeberOfHours(int numeberOfHours) {
            this.numeberOfHours = numeberOfHours;
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

        public TrainingTOBuilder withStudents(List<Long> students) {
            this.students = students;
            return this;
        }

        public TrainingTO build() {
            checkBeforeBuild(title, type, kind, dateFrom, dateTo, numeberOfHours, keyWords, amount);
            return new TrainingTO(id, title, type, kind, dateFrom, dateTo, numeberOfHours, keyWords, amount, students);
        }

        private void checkBeforeBuild(String title, String type, String kind, Date dateFrom, Date dateTo,
                                      Integer numeberOfHours, List<String> keyWords, Double amount) {
            if (title == null || type == null || kind == null || dateFrom == null || dateTo == null ||
                    numeberOfHours == null || keyWords == null || amount == null) {
                throw new RuntimeException("Incorrect training to be created");
            }

        }
    }
}
package com.capgemini.types;

import java.sql.Date;

public class CriteriaTrainingTO {

    private String title;
    private String type;
    private Date date;
    private Double amountFrom;
    private Double amountTo;

    public CriteriaTrainingTO() {
    }

    public CriteriaTrainingTO(String title, String type, Date date, Double amountFrom, Double amountTo) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(Double amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(Double amountTo) {
        this.amountTo = amountTo;
    }

    public static class CriteriaTrainingTOBuilder {

        private String title;
        private String type;
        private Date date;
        private Double amountFrom;
        private Double amountTo;

        public CriteriaTrainingTOBuilder() {
        }

        public CriteriaTrainingTOBuilder(String title, String type, Date date, Double amountFrom, Double amountTo) {
            this.title = title;
            this.type = type;
            this.date = date;
            this.amountFrom = amountFrom;
            this.amountTo = amountTo;
        }

        public CriteriaTrainingTOBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public CriteriaTrainingTOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public CriteriaTrainingTOBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public CriteriaTrainingTOBuilder withAmountFrom(Double amountFrom) {
            this.amountFrom = amountFrom;
            return this;
        }

        public CriteriaTrainingTOBuilder withAmountTo(Double amountTo) {
            this.amountTo = amountTo;
            return this;
        }

        public CriteriaTrainingTO build() {
            return new CriteriaTrainingTO(title, type, date, amountFrom, amountTo);
        }

    }
}
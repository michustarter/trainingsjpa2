package com.capgemini.types;

import java.sql.Date;

public class TrainingSearchCriteriaTO {

    private String title;
    private String type;
    private Date date;
    private Double amountFrom;
    private Double amountTo;

    public TrainingSearchCriteriaTO() {
    }

    public TrainingSearchCriteriaTO(String title, String type, Date date, Double amountFrom, Double amountTo) {
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

    public static class TrainingSearchCriteriaTOBuilder {

        private String title;
        private String type;
        private Date date;
        private Double amountFrom;
        private Double amountTo;

        public TrainingSearchCriteriaTOBuilder() {
        }

        public TrainingSearchCriteriaTOBuilder(String title, String type, Date date, Double amountFrom, Double amountTo) {
            this.title = title;
            this.type = type;
            this.date = date;
            this.amountFrom = amountFrom;
            this.amountTo = amountTo;
        }

        public TrainingSearchCriteriaTOBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withAmountFrom(Double amountFrom) {
            this.amountFrom = amountFrom;
            return this;
        }

        public TrainingSearchCriteriaTOBuilder withAmountTo(Double amountTo) {
            this.amountTo = amountTo;
            return this;
        }

        public TrainingSearchCriteriaTO buil() {
            return new TrainingSearchCriteriaTO(title, type, date, amountFrom, amountTo);
        }

    }
}
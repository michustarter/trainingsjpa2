package com.capgemini.domain;

import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TRAINING")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class TrainingEntity extends AbstractEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String kind;

    @Column(nullable = false)
    private Date dateFrom;

    @Column(nullable = false)
    private Date dateTo;

    @Column(nullable = false)
    private int numberOfHours;

    @ElementCollection
    private List<String> keyWords;

    @Column(nullable = false)
    private double amount;

    @ManyToMany
    @JoinTable(name = "student_training", joinColumns = {@JoinColumn(name = "training_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<StudentEntity> students;

    @ManyToMany
    @JoinTable(name = "trainer_training", joinColumns = {@JoinColumn(name = "training_id")},
            inverseJoinColumns = {@JoinColumn(name = "trainer_id")})
    private List<TrainerEntity> trainers;


    public TrainingEntity() {
        this.students = new ArrayList<>();
        this.trainers = new ArrayList<>();
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

    public String getDateFrom() {
        return dateFrom.toString();
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String  getDateTo() {
        return dateTo.toString();
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public List<TrainerEntity> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<TrainerEntity> trainers) {
        this.trainers = trainers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingEntity that = (TrainingEntity) o;
        return numberOfHours == that.numberOfHours &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(type, that.type) &&
                Objects.equals(kind, that.kind) &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(keyWords, that.keyWords) &&
                Objects.equals(students, that.students) &&
                Objects.equals(trainers, that.trainers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, kind, dateFrom, dateTo, numberOfHours, keyWords, amount, students, trainers);
    }
}
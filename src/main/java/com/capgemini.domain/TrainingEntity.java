package com.capgemini.domain;

import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "TRAINING")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class TrainingEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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
    private int numeberOfHours;

   @ElementCollection
    private List<String> keyWords;

    @Column(nullable = false)
    private double amount;

    @ManyToMany
    @JoinTable(name = "student_training", joinColumns = {@JoinColumn(name = "training_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private List<StudentEntity> students;


    public TrainingEntity() {
    }

    public TrainingEntity(long id, String title, String type, String kind, Date dateFrom, Date dateTo, int numeberOfHours, List<String> keyWords, double amount,List<StudentEntity> students) {

        this.id = id;
        this.title = title;
        this.type = type;
        this.kind = kind;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numeberOfHours = numeberOfHours;
        this.keyWords=keyWords;
        this.amount = amount;
        this.students = students;
    }

}
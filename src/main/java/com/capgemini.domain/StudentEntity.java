package com.capgemini.domain;


import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STUDENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({UpdateListener.class, InsertListener.class})
public class StudentEntity extends AbstractEntity  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private short grade;

    @Column
    private EmployeeEntity boss;


    public StudentEntity() {
    }

    public StudentEntity(Long id, short grade) {
        this.id = id;
        this.grade = grade;
    }


    public Long getId() {
        return id;
    }

    public short getGrade() {
        return grade;
    }

    public EmployeeEntity getBoss() {
        return boss;
    }
}
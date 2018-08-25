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


    @Version
    @Column(name="version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = false, length = 45)
    private String position;

    @Column(nullable = false)
    private int grade;

    @OneToOne
    @JoinColumn(name="boss_id", nullable = false)
    private EmployeeEntity boss;

    public StudentEntity() {
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public EmployeeEntity getBoss() {
        return boss;
    }

    public void setBoss(EmployeeEntity boss) {
        this.boss = boss;
    }
}
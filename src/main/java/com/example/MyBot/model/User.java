package com.example.MyBot.model;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "users")
//анотация Entity создает табличку в бд для хранения экземпляров этого класса. name название этой таблички
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //поле генирируется бд, анотация указыввет каким образом генерируется поле
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "register_data")
    private Timestamp registerData;

    @Column(name = "level_user")
    private int levelUser;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_goals", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "goals_id"))
    private List<Goal> goals;

    public void addGoal(Goal goal){
        this.goals.add(goal);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", userName='" + userName + '\'' +
                ", registerData=" + registerData +
                ", levelUser=" + levelUser +
                '}';
    }

    public int levelUp(){
        this.levelUser++;
        return levelUser;
    }
}

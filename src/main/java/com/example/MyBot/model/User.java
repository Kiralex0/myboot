package com.example.MyBot.model;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
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

    //это анотация говорит о том, что к одному юзеру ммогут относится многие цели
    @ManyToMany()
    @JoinTable(name = "user_to_goal", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "goal_id"))
    private Set<Goal> goals;

}

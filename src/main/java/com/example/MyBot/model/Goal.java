package com.example.MyBot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// анотация указывает на то что даноое поле заполняется бд
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_goals", joinColumns = @JoinColumn(name = "goals_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> users;


    //анотация указывает на поле, которое нужно записать в бд, но так как енам нельзя записать в бд она конвертирует его в строчку
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private StatusGoal status;

    @Column(name = "create_data")
    @CreationTimestamp // анотация дает понять фреймворку, что в это поле нужно записать дату создания объекта
    private LocalDateTime createDate;

    @Override
    public String toString() {
        return id + " " + title + " " + status + " " + createDate;
    }

    public void done(){
        this.status = StatusGoal.DONE;
    }
}

package com.example.MyBot.controller;

import com.example.MyBot.model.Goal;
import com.example.MyBot.service.GoalServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class GoalControllerImpl {

    private GoalServiceImpl serviceGoal;

    public String setGoal(Long id){
        return null;
    }

    public Long createGoal(Goal goal){
        return serviceGoal.createGoal(goal);
    }

    public Goal getGoal(Long id){
        return serviceGoal.getGoal(id);
    }

    public Goal updateGoal(Goal goal){
        return serviceGoal.updateGoal(goal);
    }
}

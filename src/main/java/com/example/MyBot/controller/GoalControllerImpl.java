package com.example.MyBot.controller;

import com.example.MyBot.model.Goal;
import com.example.MyBot.model.User;
import com.example.MyBot.service.GoalServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Controller;

import java.util.List;

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

    public List<Goal> getAllGoals(){
        return serviceGoal.getAllGoals();
    }

    public void completeGoal(@NonNull Long id){
        if (id<0){
            throw new RuntimeException();
        } else {
           serviceGoal.completeDone(id);
        }
    }
}

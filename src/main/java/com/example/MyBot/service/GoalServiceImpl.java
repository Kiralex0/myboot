package com.example.MyBot.service;

import com.example.MyBot.dao.GoalRepo;
import com.example.MyBot.model.Goal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class GoalServiceImpl {

    private final GoalRepo goalRepo;

    public Goal getGoal(Long id) {
        return  null;
    }

    public Long createGoal(Goal goal){
            return goalRepo.save(goal).getId();
    }

    public List<Goal> getAllGoals(){
        return goalRepo.findAll();
    }

    public Goal updateGoal(Goal goal){
        return null;
    }

    public void completeDone(Long id){
        Goal goal = goalRepo.findById(id).get();
        goal.done();
        goalRepo.save(goal);
    }
}

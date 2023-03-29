package com.example.MyBot.service;

import com.example.MyBot.dao.GoalRepo;
import com.example.MyBot.model.Goal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class GoalServiceImpl {

    private final GoalRepo goalRepo;

    public Goal getGoal(Long id) {
        return  null;
    }

    public Long createGoal(Goal goal){
//        if (goalRepo.findById(goal.getId()).isEmpty()){
            return goalRepo.save(goal).getId();
//        }
//        return null;
    }

    public Goal updateGoal(Goal goal){
        return null;
    }
}

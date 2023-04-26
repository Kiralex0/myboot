package com.example.MyBot.controller;

import com.example.MyBot.model.User;
import com.example.MyBot.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;


@AllArgsConstructor
@Controller
public class UserControllerImpl {

    private UserServiceImpl userService;

    public String startCommand(Message message) {
        return userService.startCommand(message);
    }

    public Long createUser(User user) {
        return userService.createUser(user);
    }

    public User getUser(Long id) {
        return userService.getUser(id);
    }

    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    public int levelUp(@NonNull Long id){
        if (id>0){
            return userService.levelUp(id);
        } else  {
            throw new RuntimeException();
        }
    }

}



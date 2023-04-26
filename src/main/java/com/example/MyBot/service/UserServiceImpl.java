package com.example.MyBot.service;

import com.example.MyBot.dao.UserRepo;
import com.example.MyBot.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class UserServiceImpl {

    private final UserRepo userRepo;


    public String startCommand(Message message) {
        registerUser(message);
        return "Добро пожаловать в MotivatorBot! " +
                " Я бот для достижения твоих желаний!" +
                " Я создан для того, чтобы ты не забывал к чему идешь и стремишься, " +
                " напиши то, что давно хотел сделать, но никак не доходили руки , " +
                " ты можешь написать свою  цель и ее промежуточные этапы. " +
                " Каждый раз, когда ты будешь достигать своей цели, ты будешь приобретать уровень." +
                " Но на этом не все, я тебя не брошу, и буду каждый день скидывать мотивирующий контент. " +
                " У каждого человека есть мечты и цели, над этим нужно просто сесть и подумать, " +
                " это может быть обычное действие, как читать книгу каждый день по часу в течении месяца. " +
                " Теперь напиши свою цель и достигай новых вершин!";

    }

    private void registerUser(Message message) {
        if ((userRepo.findById(message.getChatId())).isEmpty()) {
            Long id = message.getChatId();
            Chat chat = message.getChat();
            User user = new User();
            user.setId(id);
            user.setFirstName(chat.getFirstName());
            user.setSurname(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisterData(new Timestamp(System.currentTimeMillis()));
            createUser(user);
        }
    }

    public Long createUser(User user) {
        return userRepo.save(user).getId();
    }

    public User updateUser(User user) {
        return null;
    }

    public User getUser(Long id) {
        return userRepo.findById(id).get();
    }

    public int levelUp(Long id){
        User user = userRepo.findById(id).get();
        int level = user.levelUp();
        userRepo.save(user);
        return level;
    }
}

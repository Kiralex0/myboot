package com.example.MyBot;

import com.example.MyBot.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public class BotCommands {
    public SendMessage start(User user) {
        return null;
//        setText("Привет! Напиши свою цель");
    }

    public SendMessage setGoal(User user, String goal) {
        return  null;
//        setText("Цель успешно сохранена!");
    }

    public SendMessage done(User user) {
        return null;
//        "Сначала напишите свою цель";
//        "Поздравляем вы достигли уровня";
    }

    public SendMessage level(User user) {
        return null;
//        "Ваш текущий уровень ";
    }

}

package com.example.MyBot;

import com.example.MyBot.controller.GoalControllerImpl;
import com.example.MyBot.controller.UserControllerImpl;
import com.example.MyBot.model.Goal;
import com.example.MyBot.model.StatusGoal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

//@Data
//Анотация компонент говорит о том, что наш класс является компонентом и будет создан объект этого класса и будет помещен в спринг контекст
@Component
public class BotUi extends TelegramLongPollingBot {

    @Autowired
    private GoalControllerImpl goalController;

    @Autowired
    private UserControllerImpl userController;

    @Autowired
    private Config config;

    public BotUi(GoalControllerImpl goalController, UserControllerImpl userController, Config config) {
        this.goalController = goalController;
        this.userController = userController;
        this.config = config;

        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/start", "Запустить"));
        botCommands.add(new BotCommand("/newgoal","Поставьте новую цель"));
        botCommands.add(new BotCommand("/mygoals","Мои цели"));
        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        }catch (TelegramApiException e) {
            System.out.println("не удалось создать меню");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    private void sendMessage(String message, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("цели");
        row1.add("уровень");
        keyboardRows.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add("хрю");
        row2.add("вот");
        row2.add("мотивация");
        keyboardRows.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long id = update.getMessage().getChatId();
            String name = update.getMessage().getChat().getFirstName();
//          отправка двойной команды
            if (text.contains("/setgoal")) {
                String goalTitle = text.substring(text.indexOf(" "));
                goalController.createGoal(buildGoal(goalTitle, id));
            }



            switch (text) {
                case "/start" :
                    sendMessage(userController.startCommand(update.getMessage()), id);
                    break;

                case "/done" :
                    break;

                case "/setgoal":

                   break;
            }
        }

    }
    private Goal buildGoal(String goalTitle, Long userId){
        Goal goal = new Goal();
        goal.setTitle(goalTitle);
        goal.setStatus(StatusGoal.ACTIVE);
        return goal;
    }
}

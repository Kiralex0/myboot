package com.example.MyBot;

import com.example.MyBot.controller.GoalControllerImpl;
import com.example.MyBot.controller.UserControllerImpl;
import com.example.MyBot.model.Goal;
import com.example.MyBot.model.StatusGoal;
import com.example.MyBot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.io.File;
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
        botCommands.add(new BotCommand("/newgoal", "Поставьте новую цель"));
        botCommands.add(new BotCommand("/goals", "Мои цели"));
        botCommands.add(new BotCommand("/level", "Мой уровень"));
        botCommands.add(new BotCommand("/photo", "Мотивация"));
        botCommands.add(new BotCommand("/yes", "Да"));
        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
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

    public ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Мой уровень");
        row1.add("Мои цели");
        keyboardRows.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add("Done");
        row2.add("Новая цель");
        row2.add("Мотивация");
        keyboardRows.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getButton(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("Да");
        yesButton.setCallbackData("YES_BUTTON");
        var noButton = new InlineKeyboardButton();
        noButton.setText("Нет");
        noButton.setCallbackData("NO_BUTTON");
        rowInline.add(yesButton);
        rowInline.add(noButton);
        rowsInline.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }

    private void sendMessage(String message, Long chatId, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(getKeyboard());
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        }
    }

    public void sendPhoto(Long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile("https://zastavok.net/ts/animals/163052401063.jpg"));
        sendPhoto.setCaption("Bulba");
        try {
            execute(sendPhoto);
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
                User user = userController.getUser(id);
                if (user == null) {
                    System.out.println(user);
                }
                goalController.createGoal(buildGoal(goalTitle, user));
            }
            if (text.contains("/done")) {
                Long goalId = Long.parseLong(text.substring(text.indexOf(" ")).trim());
                goalController.completeGoal(goalId);
                userController.levelUp(id);
            }

            User user;


            switch (text) {
                case "/start":
                    sendMessage(userController.startCommand(update.getMessage()), id, getButton(id));
                    break;

                case "/goals":
                    user = userController.getUser(id);
                    List<Goal> goals = user.getGoals();
                    System.out.println(goals.size());
                    StringBuilder builder = new StringBuilder();
                    for (Goal goal : goals
                    ) {
                        builder.append(goal);
                        builder.append(System.lineSeparator());
                        builder.append("*****");
                        builder.append(System.lineSeparator());
                    }
                    sendMessage(builder.toString(), id, getButton(id));
                    break;

                case "/level":
                    user = userController.getUser(id);
                    String levelText = "Ваш уровень: " + user.getLevelUser();
                    sendMessage(levelText, id, getButton(id));
                    break;

                case "/photo":
                    sendPhoto(id);
                    break;

                case "/done" :
                    break;



                case "/setgoal":

                    break;
            }
        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            if (callBackData.equals("NO_BUTTON")) {
                String text = "no no no";
                executeEditMessageText(text, chatId, messageId);

            } else if (callBackData.equals("YES_BUTTON")) {
                String text = "yes yes yes";
                executeEditMessageText(text, chatId, messageId);

            }
        }
    }

    private Goal buildGoal(String goalTitle, User user) {
        Goal goal = new Goal();
        goal.setTitle(goalTitle);
        goal.setStatus(StatusGoal.ACTIVE);
        List<User> users = new ArrayList<>();
        user.addGoal(goal);
        users.add(user);
        goal.setUsers(users);
        return goal;
    }

    public void executeEditMessageText(String text, Long chatId, Integer messageId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setText(text);
        editMessageText.setMessageId(messageId);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {

        }

    }
}

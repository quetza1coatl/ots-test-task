package com.quetzalcoatl.otstesttask.bot;

import com.quetzalcoatl.otstesttask.rest.model.City;
import com.quetzalcoatl.otstesttask.rest.repository.CrudCityRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class OtsTravelBot extends TelegramLongPollingBot {
    private final CrudCityRepository repository;

    private static final String START = "/start";
    private static final String HELP = "/help";
    private static final String START_MESSAGE = "Бот предоставляет справочную информацию о городах." +
            " Для получения информации Вы можете ввести название города и направить боту.\n" +
            HELP + " для получения списка городов.";
    private static final String HELP_MESSAGE = "Для получения справочной информации о городе, выберите интересующий" +
            " Вас город из списка и направьте боту его название:\n";
    private static final String UNSUPPORTED_TYPE_MESSAGE = "Данный тип сообщений не поддерживается.\n" + HELP + " для справки.";
    private static final String UNSUPPORTED_CITY_MESSAGE = "Возможно, Вы неправильно написали название города.\n" +
            "Попробуйте еще раз \uD83D\uDE03\n " + HELP + " для получения списка городов.";

    public OtsTravelBot(CrudCityRepository repository) {
        this.repository = repository;
    }


    @Override
    public String getBotUsername() {
        return System.getenv("BotUserName");
    }

    @Override
    public String getBotToken() {
        return System.getenv("BotToken");
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatID = update.getMessage().getChatId();
        String response;

        if (update.hasMessage() && update.getMessage().hasText()) {
            String userRequest = update.getMessage().getText();
            switch (userRequest) {
                case START:
                    response = START_MESSAGE;
                    break;
                case HELP:
                    String listOfCities = String.join("\n", repository.findAllCityNamesOrdered());
                    response = HELP_MESSAGE + listOfCities;
                    break;
                default:
                    List<City> cities = repository.findByNameIgnoreCase(userRequest);
                    if (!cities.isEmpty()) {
                        response = cities.get(0).getDescription();
                    } else {
                        response = UNSUPPORTED_CITY_MESSAGE;
                    }
            }
        } else {
            response = UNSUPPORTED_TYPE_MESSAGE;
        }

        sendMsg(response, chatID);
    }

    private void sendMsg(String response, long chatID) {
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(response);
        try {
            execute(message);
        } catch (TelegramApiException e) {
                e.printStackTrace();
        }
    }
}

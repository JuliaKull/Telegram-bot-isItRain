package ee.kull.controller;

import ee.kull.App;
import ee.kull.constant.Stickers;
import ee.kull.service.SendMessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.Properties;

import static ee.kull.constant.Const.*;

@Log4j2
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private SendMessageService service;
    private Stickers sticker;


    @Autowired
    public Bot(SendMessageService service, Stickers sticker) {
        this.service = service;
        this.sticker = sticker;
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case START:
                    stickerSender(update, sticker.getTom());
                    executeMessage(service.sayHelloMessage(update));
                    break;
                case CITY:
                    stickerSender(update, sticker.getAskTom());
                    executeMessage(service.createAskCityMessage(update));
                    break;
                default:
                    try {
                        executeMessage(service.createWeatherMessage(update));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

    private <T extends BotApiMethod> void executeMessage(T sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.warn("The message was not sent");
        }
    }

    private void stickerSender(Update update, String stickerId) {
        String chatId = update.getMessage().getChatId().toString();
        InputFile stickerFile = new InputFile(stickerId);
        SendSticker sticker = new SendSticker(chatId, stickerFile);
        try {
            execute(sticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}

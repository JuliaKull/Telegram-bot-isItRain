package ee.kull.service;

import ee.kull.model.Model;
import ee.kull.model.Weather;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.io.IOException;
import static ee.kull.constant.Const.*;

@Service
@Log4j2
public class SendMessageService {
    private final String HELLO = " Hey, Pumpkin!";
    private final String CITYNAME = "Which city are you interested in?";
    private final ButtonService buttonService;

@Autowired
    public SendMessageService(ButtonService buttonService) {
        this.buttonService = buttonService;
    }


    public SendMessage createAskCityMessage(Update update) {
        log.info("Ask city message has been sent");
        return createSimpleMessage(update, CITYNAME);
    }


    public SendMessage createWeatherMessage(Update update) throws IOException {
        Message message = update.getMessage();
        Model model = new Model();
        log.info("Weather message has been sent");
        return createSimpleMessage(update, Weather.getWeather(message.getText(), model));
    }


    public SendMessage sayHelloMessage(Update update) {
        SendMessage  message = createSimpleMessage(update, HELLO);
        ReplyKeyboardMarkup keyboardMarkup = buttonService.setButton(buttonService.createButton(CITY));
        message.setReplyMarkup(keyboardMarkup);
        log.info("Hello message has been sent");
        return message;
    }
    private SendMessage createSimpleMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        return sendMessage;
    }
    }


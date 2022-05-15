package ee.kull.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

@Service
public class ButtonService {

    public ReplyKeyboardMarkup setButton(List<KeyboardRow> keyboardRows){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setSelective(true);
        markup.setKeyboard(keyboardRows);
        markup.setOneTimeKeyboard(true);
        return markup;}

    public List<KeyboardRow> createButton(String buttonName){
        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(buttonName));
            list.add(keyboardRow);
        return list;
    }
}

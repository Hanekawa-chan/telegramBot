import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Bot extends TelegramLongPollingBot {

    private boolean isOn = false;
    private boolean isAuthed = false;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            String id = String.valueOf(update.getMessage().getChatId());

            if (isOn) {
                if(messageText.equals("password")) {
                    send(id, "Правильный пароль :)");
                    isAuthed = true;
                    isOn = false;
                }
                else {
                    send(id,"Неправильный пароль :(");
                    isOn = false;
                }
            }
            if (messageText.equals("/hi") && !isAuthed) {
                send(id,"Привет, " + update.getMessage().getFrom().getUserName()
                        + "\n" +
                        "Пожалуйста, введите пароль");
                isOn = true;
            }
            if (isAuthed && messageText.equals("/start")) {
                while(true) {
                    Date date = new Date();
                    System.out.println(date + " Время=" + date.toString().subSequence(11,18));
                    if(date.toString().subSequence(11,19).equals("20:00:00")) {
                        send(id, "Время настало!");
                    }
                }
            }
        }
    }

    @SneakyThrows
    private void send(String id, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        execute(message);
    }
    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "testchubot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "1727229817:AAHTHiuW4qu7SFgZw59M7ZATkQ6FXTVrIqQ";
    }


}

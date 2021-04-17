import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@NoArgsConstructor
@Getter
@Setter
public class Bot extends TelegramLongPollingBot {

    private boolean isOn = false;
    private boolean isAuthed = false;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if(update.getMessage().getText().equals("/hi")) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                message.setText("Привет, " + update.getMessage().getFrom().getUserName() + "\n" +
                         "Пожалуйста, введите пароль");
                execute(message);
                isOn = true;
            }
            if(isOn) {
                if(update.getMessage().getText().equals("password")) {
                    SendMessage message = new SendMessage();
                    message.setChatId(String.valueOf(update.getMessage().getChatId()));
                    message.setText("Правильный пароль :)");
                    execute(message);
                    isAuthed = true;
                }
                else {
                    SendMessage message = new SendMessage();
                    message.setChatId(String.valueOf(update.getMessage().getChatId()));
                    message.setText("Неправильный пароль :(");
                    execute(message);
                    isOn = false;
                }
            }
        }
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

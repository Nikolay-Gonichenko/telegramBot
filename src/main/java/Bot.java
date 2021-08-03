import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Bot extends TelegramLongPollingBot {
    private final String BOT_NAME = "@QuizProgrammingBot";
    private final String BOT_TOKEN = "1940873417:AAEc6jPqnHEclVm9h87pcm_04RMhnSneo4Y";
    private final Locale locale = new Locale("ru", "RU");
    private final ResourceBundle rb = ResourceBundle.getBundle("text", locale);

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        if (update.hasMessage()){
            String message = update.getMessage().getText();
            handleText(message, update.getMessage().getChatId().toString());
        }else if (update.hasCallbackQuery()){
            handleQuery(update.getCallbackQuery(), String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        }

    }

    private void handleText(String message, String chatId) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        String[] text = message.split(" ");
        switch (text[0]) {
            case "start":
                setInline(sendMessage);
                sendMessage.setText(setString("choseLevel"));
                break;
            case "help":
                sendMessage.setText(setString("help"));
                break;
            default:
                sendMessage.setText(setString("notACommand"));
                break;
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void handleQuery(CallbackQuery callbackQuery, String chatID){
        String text = callbackQuery.getData();
        String[] checkText = text.split("");
        Game game = new Game();
        switch (checkText[0]){
            case "Level":
                game.start(Integer.parseInt(checkText[1]));
                break;
            case "true":
                game.nextQuestion();
                game.rightAnswer();
                break;
            case "false":
                game.nextQuestion();
                break;
        }
        SendMessage sendMessage = new SendMessage().setChatId(chatID);
        if (game.getCurrentQuestion()<5){
            setQuestion(sendMessage, game);
        }else{
            String end = "Игра окончена, Ваш результат: "+game.getRightAnswers()+"/5. Чтобы начать новую игру введите /start";
            sendMessage.setText(end);
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void setQuestion(SendMessage sendMessage, Game game) {
        String[] text = game.getQuestion(rb);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> line1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton().setText(text[1]);
        List<InlineKeyboardButton> line2 = new ArrayList<>();
        InlineKeyboardButton button2 = new InlineKeyboardButton().setText(text[2]);
        List<InlineKeyboardButton> line3 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton().setText(text[3]);
        setTrueFalse(button1,button2,button3,game.getCurrentQuestion());
        line1.add(button1);
        line2.add(button2);
        line3.add(button3);
        buttons.add(line1);
        buttons.add(line2);
        buttons.add(line3);
        inlineKeyboardMarkup.setKeyboard(buttons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setText(text[0]);
    }

    private void setTrueFalse(InlineKeyboardButton button1, InlineKeyboardButton button2, InlineKeyboardButton button3, int currentQuestion) {
        if (currentQuestion%3==0){
            button1.setCallbackData("false");
            button2.setCallbackData("false");
            button3.setCallbackData("true");
        }else if (currentQuestion%3==1){
            button1.setCallbackData("true");
            button2.setCallbackData("false");
            button3.setCallbackData("false");
        }else if (currentQuestion%3==2){
            button1.setCallbackData("false");
            button2.setCallbackData("true");
            button3.setCallbackData("false");
        }

    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    private void setInline(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> line1 = new ArrayList<>();
        line1.add(new InlineKeyboardButton().setText("Уровень 1").setCallbackData("Level 1"));
        List<InlineKeyboardButton> line2 = new ArrayList<>();
        line2.add(new InlineKeyboardButton().setText("Уровень 2").setCallbackData("Level 2"));
        List<InlineKeyboardButton> line3 = new ArrayList<>();
        line3.add(new InlineKeyboardButton().setText("Уровень 3").setCallbackData("Level 3"));
        buttons.add(line1);
        buttons.add(line2);
        buttons.add(line3);
        inlineKeyboardMarkup.setKeyboard(buttons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
    private String setString(String text){
        String stringToSet = rb.getString(text);
        stringToSet = new String(stringToSet.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        return stringToSet;
    }

}

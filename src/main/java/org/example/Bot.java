package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;


@Slf4j
public class Bot extends TelegramLongPollingBot {
    Http http = new Http();

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if ("/start".equals(message.getText())) {
                sendMsg(update.getMessage().getChatId().toString(), "Привет!" + "\n"
                        + "Этот бот умеет показывать рецепты алкогольных коктейлей.");
            }
            else if ("/help".equals(message.getText())) {
                sendMsg(update.getMessage().getChatId().toString(), "Бог в помощь!");
            }
            else if (message.getText().startsWith("/")) {
                Pair<String, String> result = http.cockTail(message.getText());

                sendPhoto(update.getMessage().getChatId().toString(), result.getFirst());
                sendMsg(update.getMessage().getChatId().toString(), result.getSecond());
            }
            else {
                sendMsg(update.getMessage().getChatId().toString(), "Ошибка :(");
            }
        }
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Exception: {}", e.toString());
        }

    }

    public SendPhoto sendPhotoFromUrl(String url, String chatId) throws IOException {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(new InputFile(new BufferedInputStream(new URL(url).openStream()), "imageName"));
        return sendPhotoRequest;
    }

    public synchronized void sendPhoto(String chatId, String s) {
        try {
            SendPhoto sp = sendPhotoFromUrl(s, chatId);
            execute(sp);
        } catch (TelegramApiException | IOException e) {
            log.error("Exception: ", e);
        }

    }

    @Override
    public String getBotUsername() {
        return "MyTest1_111bot";
    }

    @Override
    public String getBotToken() {
        return "1886779816:AAFpNvfEaB7p9WpmozxaPFjhm0wecdQFCDY";
    }
}

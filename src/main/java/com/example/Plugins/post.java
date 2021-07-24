package com.example.Plugins;

import java.io.File;
import java.util.List;
import com.example.Bot;
import com.example.Master;
import com.example.Helpers.configuration;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class post extends Bot implements Master {
    @Override
    public void handleRequests(Update update, String cmd) {
        if(update.getChannelPost().hasPhoto()){
            try {
                String username = configuration.username;
                String password = configuration.password;
                IGClient client = IGClient.builder().username(username).password(password).login();
                try {
                    Message m = execute(new SendMessage(chatId(update), "Downloading.."));
    
                    long start = System.currentTimeMillis();
                    List<PhotoSize> arr = update.getChannelPost().getPhoto();
    
                    PhotoSize biggSize = null;
                    for (int i = 0; i < arr.size(); i++) {
                        for (int j = 0; j < arr.size(); j++) {
                            if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                                biggSize = arr.get(i);
                        }
                    }
                    String caption = update.getChannelPost().getCaption();
                    PhotoSize photos = biggSize;
                    GetFile getFiled = new GetFile();
                    getFiled.setFileId(photos.getFileId());
    
                    long end = System.currentTimeMillis();
                    long elapsedTime = end - start;
    
                    org.telegram.telegrambots.meta.api.objects.File file;
    
                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setChatId(chatId(update));
                    editMessageText.setMessageId(m.getMessageId());
                    editMessageText.setText("Downloaded in " + elapsedTime + " milliseconds");
    
                    execute(editMessageText);
                    execute(new DeleteMessage(chatId(update), m.getMessageId()));
    
                    start = System.currentTimeMillis();
    
                    file = execute(getFiled);
                    File file2 = downloadFile(file);
                    Message m2 = execute(new SendMessage(chatId(update), "Uploading.."));
                    client.actions().timeline().uploadPhoto(file2, caption).thenAccept(response -> {
                        EditMessageText editMessageText2 = new EditMessageText();
                        editMessageText2.setChatId(chatId(update));
                        editMessageText2.setMessageId(m2.getMessageId());
                        editMessageText2.setText("Succesfully Uploaded Post");
                        try {
                            execute(editMessageText2);
                            execute(new DeleteMessage(chatId(update), m2.getMessageId()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }).join();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
    
            } catch (IGLoginException e) {
                sendMessage(update, "Incorrect Username/password");
                e.printStackTrace();
            }
        }

    }
}

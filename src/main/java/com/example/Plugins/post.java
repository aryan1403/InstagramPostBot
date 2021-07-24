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
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class post extends Bot implements Master {
    @Override
    public void handleRequests(Update update, String cmd) {
        String username = configuration.username;
        String password = configuration.password;
        try {
            IGClient client = IGClient.builder().username(username).password(password).login();
            SendMessage message = new SendMessage(update.getMessage().getChatId().toString(), "Downloading...");

            execute(message);

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

            org.telegram.telegrambots.meta.api.objects.File file;

            file = execute(getFiled);
            File file2 = downloadFile(file);

            SendMessage message52 = new SendMessage(update.getMessage().getChatId().toString(), "Uploading...");

            execute(message52);

            client.actions().timeline().uploadPhoto(file2, caption).thenAccept(response -> {
                SendMessage message526 = new SendMessage(update.getMessage().getChatId().toString(),
                        "Uploaded Successfully");

                try {
                    execute(message526);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }).join();

        } catch (IGLoginException | TelegramApiException e) {
            e.printStackTrace();
        }

    }
}

package com.example.Plugins;


import java.util.List;
import com.example.Master;
import com.example.Helpers.login;
import com.example.test.testBot;
import com.github.instagram4j.instagram4j.IGClient;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class profilepic extends testBot implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if (update.getMessage().getReplyToMessage().hasPhoto() && cmd.equalsIgnoreCase(getHandler() + "setpic")) {
            login l = new login();
            IGClient client = l.loginAccount();

            Message m;
            try {
                m = execute(new SendMessage(chatId(update), "Setting profile pic..."));

                long start = System.currentTimeMillis();
                List<PhotoSize> arr = update.getMessage().getReplyToMessage().getPhoto();

                PhotoSize biggSize = null;
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = 0; j < arr.size(); j++) {
                        if (arr.get(i).getFileSize() > arr.get(j).getFileSize())
                            biggSize = arr.get(i);
                    }
                }
                PhotoSize photos = biggSize;
                GetFile getFiled = new GetFile();
                getFiled.setFileId(photos.getFileId());

                org.telegram.telegrambots.meta.api.objects.File file;

                try {
                    file = execute(getFiled);
                    java.io.File file2 = downloadFile(file);

                    client.actions().account().setProfilePicture(file2).thenAccept(response -> {
                        long end = System.currentTimeMillis();
                        long elapsedTime = end - start;
                        EditMessageText editMessageText = new EditMessageText();
                        editMessageText.setChatId(chatId(update));
                        editMessageText.setMessageId(m.getMessageId());
                        editMessageText.setText("Profile pic updated in " + elapsedTime/1000 + " seconds");
                        try {
                            execute(editMessageText);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    });
                }

                catch (TelegramApiException e2) {
                    e2.printStackTrace();
                }
            } catch (TelegramApiException e2) {
                e2.printStackTrace();
            }
        }

    }

}

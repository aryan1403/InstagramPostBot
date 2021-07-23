package com.example.abilitiesTester;

import com.example.Master;
import com.example.test.testBot;

import org.telegram.telegrambots.meta.api.objects.Update;

public class greets extends testBot implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"start")){
            sendMessage(update, "Welcome "+update.getMessage().getFrom().getFirstName()+ " to Instagram Post Bot.");
        }
    }
}

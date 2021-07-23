package com.example.Plugins;
import com.example.Bot;
import com.example.Master;

import org.telegram.telegrambots.meta.api.objects.Update;

public class greets extends Bot implements Master {

    @Override
    public void handleRequests(Update update, String cmd) {
        if(cmd.equalsIgnoreCase(getHandler()+"start")){
            sendMessage(update, "Welcome "+update.getMessage().getFrom().getFirstName()+ " to Instagram Post Bot.");
        }
    }
}

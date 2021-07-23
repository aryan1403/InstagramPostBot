package com.example;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Master {

    public abstract void handleRequests(Update update, String cmd);
    
}
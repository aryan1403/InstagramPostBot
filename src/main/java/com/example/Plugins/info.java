package com.example.Plugins;

import java.util.concurrent.ExecutionException;
import com.example.Bot;
import com.example.Master;
import com.example.Helpers.login;
import com.github.instagram4j.instagram4j.IGClient;
import org.telegram.telegrambots.meta.api.objects.Update;

public class info extends Bot implements Master {
    @Override
    public void handleRequests(Update update, String cmd) {
        String t = update.getMessage().getText().replace(getHandler() + "info ", "");
        System.out.println(t);
        if (cmd.equalsIgnoreCase(getHandler()+"info "+t)) {
            String username = t;
            System.out.println(username);
            String info = "";
    
            login l = new login();
            IGClient client = l.loginAccount();
    
            sendMessage(update, "Getting User Info...");
    
            try {
                info += "Name : " + client.actions().users().findByUsername(username).get().getUser().getFull_name() + "\n";
                info += "Username : " + client.actions().users().findByUsername(username).get().getUser().getUsername()
                        + "\n";
                info += "Bio : " + client.actions().users().findByUsername(username).get().getUser().getBiography() + "\n";
                info += "Account type : "
                        + client.actions().users().findByUsername(username).get().getUser().getAccount_type() + "\n";
                info += "Followers : "
                        + client.actions().users().findByUsername(username).get().getUser().getFollower_count() + "\n";
                info += "Following : "
                        + client.actions().users().findByUsername(username).get().getUser().getFollowing_count() + "\n";
                info += "Extrenal Link : "
                        + client.actions().users().findByUsername(username).get().getUser().getExternal_url() + "\n";
                sendMessage(update, info);
            } catch (InterruptedException | ExecutionException e) {
                sendMessage(update, "Invalid Username");
                e.printStackTrace();
            }
            
        }
    }

}

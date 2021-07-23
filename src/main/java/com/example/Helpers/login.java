package com.example.Helpers;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;

public class login {

    public IGClient loginAccount(){
        try {
            IGClient client = IGClient.builder().username("hellion_coder").password("aryan2702").login();
            return client;
        } catch (IGLoginException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}

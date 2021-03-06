package com.example.Helpers;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

public class configuration {

    private static final Dotenv dotenv = new DotenvBuilder().ignoreIfMissing().load();

    public static String botToken = dotenv.get("botToken");
    public static String botUserName = dotenv.get("botUserName");
    public static String handler = dotenv.get("handler");
    public static String username = dotenv.get("username");
    public static String password = dotenv.get("password");
    public static long sudoid = Integer.parseInt(dotenv.get("sudoid"));
}
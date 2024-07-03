package com.ptit.qldt.models;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
    private static Dotenv dotenv = Dotenv.load();

    public static String get(String key) {
        return dotenv.get(key.toUpperCase());
    }
}

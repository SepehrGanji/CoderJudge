package com.coder.judge.DB;

public class DatabaseConfig {

    public static String getDbUrl() {
        return System.getenv("DB_URL");
    }

    public static String getDbUsername() {
        return System.getenv("DB_USER");
    }

    public static String getDbPassword() {
        return System.getenv("DB_PASSWD");
    }
}

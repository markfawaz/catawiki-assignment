package com.catawiki.config;

public final class TestConfig {
    private TestConfig() {}

    public static String baseUrl() {
        return System.getProperty("baseUrl", "https://www.catawiki.com/en/");
    }

    public static boolean headless() {
        return Boolean.parseBoolean
                (System.getProperty("headless", "false"));
    }

    public static int timeoutMs() {
        return Integer.parseInt(System.getProperty("timeoutMs", "30000"));
    }

    public static int slowMoMs() {
        return Integer.parseInt(System.getProperty("slowMoMs", "0"));
    }
}

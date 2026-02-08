package com.catawiki.core;

import com.catawiki.config.TestConfig;
import com.microsoft.playwright.*;

public final class PlaywrightFactory {
    private static Playwright playwright;
    private static Browser browser;

    private PlaywrightFactory() {}

    public static void initOnce() {
        if (playwright != null) return;

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)     // force for now
                        .setSlowMo(300)         // slow actions so you can see
        );
    }

    public static Browser browser() {
        if (browser == null) throw new IllegalStateException("Playwright not initialized");
        return browser;
    }

    public static void shutdown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        browser = null;
        playwright = null;
    }
}

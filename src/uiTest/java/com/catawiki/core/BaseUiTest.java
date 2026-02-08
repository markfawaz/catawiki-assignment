package com.catawiki.core;

import com.catawiki.config.TestConfig;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseUiTest {

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void beforeAll() {
        PlaywrightFactory.initOnce();
    }

    @AfterAll
    static void afterAll() {
        PlaywrightFactory.shutdown();
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        context = PlaywrightFactory.browser().newContext(
                new Browser.NewContextOptions().setViewportSize(1440, 900)
        );

        context.setDefaultTimeout(TestConfig.timeoutMs());
        page = context.newPage();

        // Staff-level signal: trace + screenshots for easy debugging
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        String safe = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path outDir = Paths.get("build/ui-artifacts");
        outDir.toFile().mkdirs();

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(outDir.resolve(safe + ".png"))
                .setFullPage(true));

        context.tracing().stop(new Tracing.StopOptions()
                .setPath(outDir.resolve(safe + ".zip")));

        context.close();
    }


    protected void acceptCookiesIfPresent() {
        Locator continueWithoutLink = page.getByRole(
                com.microsoft.playwright.options.AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Continue without accepting")
        ).first();

        Locator continueWithoutButton = page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Continue without accepting")
        ).first();

        Locator agree = page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(
                        java.util.regex.Pattern.compile("^Agree( and continue)?$", java.util.regex.Pattern.CASE_INSENSITIVE)
                )
        ).first();

        // Try to "catch" the banner for a short time window (headless needs this)
        try {
            continueWithoutLink.waitFor(new Locator.WaitForOptions().setTimeout(2500));
            continueWithoutLink.click();
            return;
        } catch (Exception ignored) {}

        try {
            continueWithoutButton.waitFor(new Locator.WaitForOptions().setTimeout(2500));
            continueWithoutButton.click();
            return;
        } catch (Exception ignored) {}

        try {
            agree.waitFor(new Locator.WaitForOptions().setTimeout(2500));
            agree.click();
        } catch (Exception ignored) {}
    }
}

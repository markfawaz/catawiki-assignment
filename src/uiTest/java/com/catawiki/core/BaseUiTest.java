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
        // Variant A: link at top-right of banner
        Locator continueWithoutLink = page.getByRole(
                com.microsoft.playwright.options.AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Continue without accepting")
        ).first();

        // Variant B: sometimes it’s a button
        Locator continueWithoutButton = page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Continue without accepting")
        ).first();

        // Variant C: "Agree" / "Agree and continue"
        Locator agree = page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(java.util.regex.Pattern.compile("^Agree( and continue)?$", java.util.regex.Pattern.CASE_INSENSITIVE))
        ).first();

        try {
            if (continueWithoutLink.isVisible()) {
                continueWithoutLink.click();
                return;
            }
        } catch (Exception ignored) {}

        try {
            if (continueWithoutButton.isVisible()) {
                continueWithoutButton.click();
                return;
            }
        } catch (Exception ignored) {}

        try {
            if (agree.isVisible()) {
                agree.click();
            }
        } catch (Exception ignored) {}
    }
}

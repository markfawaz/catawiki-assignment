package com.catawiki.core;

import com.catawiki.config.TestConfig;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseUiTest {

    protected BrowserContext context;
    protected Page page;

    // Reuse cookies/localStorage across runs (cookie consent, etc.)
    private static final Path STORAGE_STATE = Paths.get("build/.storageState.json");

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
        Browser.NewContextOptions opts = new Browser.NewContextOptions()
                .setViewportSize(1440, 900);

        // If we already saved consent/auth state, reuse it
        if (Files.exists(STORAGE_STATE)) {
            opts.setStorageStatePath(STORAGE_STATE);
        }

        context = PlaywrightFactory.browser().newContext(opts);
        context.setDefaultTimeout(TestConfig.timeoutMs());
        page = context.newPage();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        String safe = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path outDir = Paths.get("build/ui-artifacts");
        outDir.toFile().mkdirs();

        try {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(outDir.resolve(safe + ".png"))
                    .setFullPage(false)          // key change
                    .setTimeout(5000));          // optional: cap screenshot time
        } catch (Exception e) {
            System.err.println("WARN: screenshot failed for " + safe + " -> " + e.getMessage());
        } finally {
            try { context.close(); } catch (Exception ignored) {}
        }
    }

    /**
     * Call this right after navigating to the site.
     * It will accept cookies if needed and persist the consent so future runs skip the banner.
     */
    protected void acceptCookiesAndPersistIfNeeded() {
        boolean hasState = java.nio.file.Files.exists(STORAGE_STATE);

        // Only do cookie work if we don't already have storage state
        if (!hasState) {
            acceptCookiesIfPresent();
            STORAGE_STATE.getParent().toFile().mkdirs();
            context.storageState(new BrowserContext.StorageStateOptions().setPath(STORAGE_STATE));
        }
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

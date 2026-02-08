package com.catawiki.tests;

import com.catawiki.core.BaseUiTest;
import com.catawiki.pages.HeaderNav;
import com.catawiki.pages.HomePage;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LanguageSelectionTest extends BaseUiTest {

    @Test
    void navigatesToDESite() {
        new HomePage(page).open();
        acceptCookiesAndPersistIfNeeded();
        HeaderNav header = new HeaderNav(page);
        header.switchToDeutsch();
        assertThat(page).hasURL(Pattern.compile("https://www\\.catawiki\\.com/de.*"));
    }

    @Test
    void navigatesBackToEnSite() {
        new HomePage(page).open();
        acceptCookiesAndPersistIfNeeded();
        HeaderNav header = new HeaderNav(page);
        header.switchToDeutsch();
        header.switchToEnglish();
        assertThat(page).hasURL(Pattern.compile("https://www\\.catawiki\\.com/en.*"));
    }
}

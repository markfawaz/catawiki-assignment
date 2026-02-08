package com.catawiki.tests;

import com.catawiki.core.BaseUiTest;
import com.catawiki.pages.HeaderNav;
import com.catawiki.pages.HomePage;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NavigationTest extends BaseUiTest {

    @Test
    void navigationTabsLeadToCorrectPages() {
        new HomePage(page).open();
        acceptCookiesAndPersistIfNeeded();

        HeaderNav header = new HeaderNav(page);

        header.goForYou();
        assertThat(page).hasURL("https://www.catawiki.com/en/feed?tab_name=feeds_recommendations");

        header.goTrending();
        assertThat(page).hasURL("https://www.catawiki.com/en/feed?tab_name=feeds_popularity");

        header.goArtCategory();
        assertThat(page).hasURL(Pattern.compile("https://www\\.catawiki\\.com/en/c/85-art.*"));
    }

}

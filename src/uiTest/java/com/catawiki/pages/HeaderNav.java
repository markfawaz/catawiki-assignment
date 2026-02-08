package com.catawiki.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import java.util.regex.Pattern;

public class HeaderNav {

    private final Page page;

    public HeaderNav(Page page) {
        this.page = page;
    }


    public void goThisWeek() {
        clickTopNav("This week");
        PlaywrightAssertions.assertThat(page)
                .hasURL(Pattern.compile("https://www\\.catawiki\\.com/en.*"));
    }

    public void goForYou() {
        clickTopNav("For you");
        PlaywrightAssertions.assertThat(page)
                .hasURL("https://www.catawiki.com/en/feed?tab_name=feeds_recommendations");
    }

    public void goTrending() {
        clickTopNav("Trending");
        PlaywrightAssertions.assertThat(page)
                .hasURL("https://www.catawiki.com/en/feed?tab_name=feeds_popularity");
    }

    public void goArtCategory() {
        clickTopNav("Art");
        PlaywrightAssertions.assertThat(page)
                .hasURL(Pattern.compile("https://www\\.catawiki\\.com/en/c/85-art.*"));
    }

    private void clickTopNav(String name) {
        page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName(name)
        ).first().click();
    }



    public void openLanguageMenu() {
        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName(Pattern.compile("^EN$|^DE$|English|Deutsch", Pattern.CASE_INSENSITIVE))
        ).click();
    }

    public void switchToDeutsch() {
        openLanguageMenu();

        page.getByRole(
                AriaRole.MENUITEM,
                new Page.GetByRoleOptions().setName("Deutsch")
        ).click();

        PlaywrightAssertions.assertThat(page)
                .hasURL(Pattern.compile("https://www\\.catawiki\\.com/de.*"));
    }

    public void switchToEnglish() {
        openLanguageMenu();

        page.getByRole(
                AriaRole.MENUITEM,
                new Page.GetByRoleOptions().setName("English")
        ).click();

        PlaywrightAssertions.assertThat(page)
                .hasURL(Pattern.compile("https://www\\.catawiki\\.com/en.*"));
    }


    public SignInModal openSignInModal() {
        page.locator("#cw-header-container")
                .getByRole(
                        AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("Sign in")
                )
                .first()
                .click();

        return new SignInModal(page).assertVisible();
    }

}

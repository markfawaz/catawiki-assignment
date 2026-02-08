package com.catawiki.tests;

import com.catawiki.core.BaseUiTest;
import com.catawiki.pages.HomePage;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchAutoCompleteTest extends BaseUiTest {

    @Test
    void searchAutoCompletes() {
        new HomePage(page).open();
        acceptCookiesAndPersistIfNeeded();
        var search = page.getByTestId("search-field").first();
        search.click();
        search.fill("tra");
        // Autocomplete should appear
        assertThat(page.getByRole(AriaRole.LISTBOX)).isVisible();
    }
}

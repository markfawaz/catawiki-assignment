package com.catawiki.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class SearchResultsPage {
    private final Page page;

    public SearchResultsPage(Page page) {
        this.page = page;
    }

    public SearchResultsPage assertOpened() {
        // Playwright Java expects String or Pattern (not a predicate)
        PlaywrightAssertions.assertThat(page)
                .hasURL(java.util.regex.Pattern.compile(".*(search|q=|query).*"));
        return this;
    }

    public LotPage openSecondLot() {
        page.locator("a[href*='/l/']").nth(1).click();
        return new LotPage(page);
    }
}
